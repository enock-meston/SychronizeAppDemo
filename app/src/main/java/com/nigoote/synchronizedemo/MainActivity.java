package com.nigoote.synchronizedemo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
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

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

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
        saveToAppSever(name,quantity,price);
        ProName.setText("");
        ProQua.setText("");
        ProPrice.setText("");
    }
//    public void saveToLocalStorage(String name,String quantity,String price){
    public void saveToAppSever(String name,String quantity,String price){

        if (checkNetworkConnection()){
            StringRequest stringRequest = new StringRequest(Request.Method.POST, DbContract.SERVER_URL,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                String Responce = jsonObject.getString("responce");
                                if (Responce.equals("OK")){
                                    saveToLocalStorage(name,quantity,price,DbContract.SYNC_STATUS_OK);
                                }else{
                                    saveToLocalStorage(name,quantity,price,DbContract.SYNC_STATUS_FAILED);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                    saveToLocalStorage(name,quantity,price,DbContract.SYNC_STATUS_FAILED);
                }
            })
            {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {

                    Map<String,String> params = new HashMap<>();
                    params.put("name",name);
                    params.put("quantity",quantity);
                    params.put("price",price);
                    return params;
                }
            }
                    ;

            Mysingleton.getInstance(MainActivity.this).addToRequestQue(stringRequest);
        }else{

            saveToLocalStorage(name,quantity,price,DbContract.SYNC_STATUS_FAILED);
        }

    }

    public boolean checkNetworkConnection(){
        ConnectivityManager connectivityManager = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return (networkInfo!= null && networkInfo.isConnected());
    }

    private void saveToLocalStorage(String name,String quantity,String price,int sync){

        DbHelper dbHelper= new DbHelper(this);
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        dbHelper.saveToLocalDatabase(name,quantity,price,sync,database);
        Toast.makeText(this, "Data saved", Toast.LENGTH_SHORT).show();
        //        ViewProductList viewProductList = new ViewProductList();
//        viewProductList.readFromLocalStorage();
        dbHelper.close();
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
            case R.id.list:
                Intent intent1 = new Intent(getApplication(),ViewProductList.class);
                startActivity(intent1);
                Toast.makeText(this, "LIST CLICKED", Toast.LENGTH_SHORT).show();
                return true;
//            case R.id.add:
//                Toast.makeText(this, "ADD CLICKED", Toast.LENGTH_SHORT).show();
//                Intent intent2 = new Intent(getApplication(),MainActivity.class);
//                startActivity(intent2);
//                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}