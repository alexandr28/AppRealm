package com.acampdev.apprealm;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.acampdev.apprealm.Models.Item;

import java.util.UUID;

import io.realm.Realm;
import io.realm.RealmAsyncTask;
import io.realm.RealmQuery;
import io.realm.RealmResults;

public class MainActivity extends AppCompatActivity {
    EditText name,age;
    Button save, viewAll, query;
    Realm realm;
    RealmAsyncTask realmAsyncTask;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        realm= Realm.getDefaultInstance();
        name=findViewById(R.id.textName);
        age=findViewById(R.id.txtAge);
        save=findViewById(R.id.btnSave);
        viewAll=findViewById(R.id.btnViewData);
        query=findViewById(R.id.btnQuery);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // create object
                final String n = name.getText().toString();
                final  int a= Integer.valueOf(age.getText().toString());
                final String id= UUID.randomUUID().toString();
                realmAsyncTask= realm.executeTransactionAsync(new Realm.Transaction() {
                                                                  @Override
                                                                  public void execute(Realm realm) {
                                                                      Item item = realm.createObject(Item.class);
                                                                      item.setID(id);
                                                                      item.setName(n);
                                                                      item.setAge(a);
                                                                  }
                                                              }, new Realm.Transaction.OnSuccess() {
                                                                  @Override
                                                                  public void onSuccess() {
                                                                      Toast.makeText(getApplicationContext(), "save info", Toast.LENGTH_SHORT).show();
                                                                  }
                                                              }, new Realm.Transaction.OnError() {
                                                                  @Override
                                                                  public void onError(Throwable error) {
                                                                      Toast.makeText(getApplicationContext(), "Error info", Toast.LENGTH_SHORT).show();
                                                                  }
                                                              }
                );
            }
        });
        viewAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RealmResults<Item> items= realm.where(Item.class).findAll();
                display(items);
            }
        });
        query.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                query();
            }
        });

    }
    public  void  display(RealmResults<Item> items){
        StringBuilder stringBuilder= new StringBuilder();
        for(Item item:items){
            stringBuilder.append(" \n id: ").append(item.getID());
            stringBuilder.append(" \n name: ").append(item.getName());
            stringBuilder.append(" \n age: ").append(item.getAge());
        }
        Log.e("DATA",stringBuilder.toString());
    }
    public void query(){
        RealmQuery<Item> realmQuery= realm.where(Item.class);
        realmQuery.greaterThan("age",10);
        RealmResults<Item> items= realmQuery.findAll();
        display(items);
    }
}
