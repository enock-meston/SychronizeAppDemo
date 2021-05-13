package com.nigoote.synchronizedemo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText ProName;
    EditText ProQua;
    EditText ProPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ProName = (EditText) findViewById(R.id.edtname);
        ProQua = (EditText) findViewById(R.id.edtquantity);
        ProPrice = (EditText) findViewById(R.id.edtprice);
    }

    public void submitName(View view){

        String name = ProName.getText().toString();
        String quantity = ProQua.getText().toString();
        String price = ProPrice.getText().toString();
        saveToLocalStorage(name,quantity,price);
        ProName.setText("");
        ProQua.setText("");
        ProPrice.setText("");
    }

    private void saveToLocalStorage(String name,String quantity,String price){
        DbHelper dbHelper= new DbHelper(this);
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        if (checkNetworkConnection()){

        }else{
            dbHelper.saveToLocalDatabase(name,quantity,price,DbContract.SYNC_STATUS_FAILED,database);
        }
        ViewProductList viewProductList = new ViewProductList();
        viewProductList.readFromLocalStorage();
        dbHelper.close();
    }

    public boolean checkNetworkConnection(){
        ConnectivityManager connectivityManager = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return (networkInfo!= null && networkInfo.isConnected());
    }



        //this is the menu method
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.example_menu,menu);
        return true;
    }

    //this is the menu method that help to click on items
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.add:
                Toast.makeText(this, "ADD CLICKED", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.list:
                Toast.makeText(this, "LIST CLICKED", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}