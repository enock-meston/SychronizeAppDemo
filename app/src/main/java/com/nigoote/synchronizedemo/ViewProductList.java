package com.nigoote.synchronizedemo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;

public class ViewProductList extends AppCompatActivity {
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    RecyclerAdapter adapter;
    ArrayList<Contact> arrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_product_list);

        getSupportActionBar().setTitle("Product List");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //DbHelper dbHelper= new DbHelper(this);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        adapter = new RecyclerAdapter(arrayList);
        recyclerView.setAdapter(adapter);
        readFromLocalStorage();

    }

    public void readFromLocalStorage(){
        arrayList.clear();

        DbHelper dbHelper = new DbHelper(this);
        SQLiteDatabase database= dbHelper.getReadableDatabase();

        Cursor cursor = dbHelper.readFromLocalDatabase(database);
        while (cursor.moveToNext()){
            String name=cursor.getString(cursor.getColumnIndex(DbContract.NAME));
            String quantity=cursor.getString(cursor.getColumnIndex(DbContract.QUANTITY));
            String price=cursor.getString(cursor.getColumnIndex(DbContract.PRICE));
            int sync_status =cursor.getInt(cursor.getColumnIndex(DbContract.SYNC_STATUS));

            arrayList.add(new Contact(name,quantity,price,sync_status));
        }
        adapter.notifyDataSetChanged();
        cursor.close();
        dbHelper.close();
    }
}