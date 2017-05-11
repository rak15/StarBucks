package com.example.rakesh.starbucks;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private SQLiteDatabase db;  //we need these 2 in onDestroy method
    private Cursor cursor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ListView listView=(ListView)findViewById(R.id.listView);            // ListView is attached here statically from layout
        AdapterView.OnItemClickListener itemClickListener=new AdapterView.OnItemClickListener(){  //to make list item clickable
            public void onItemClick(AdapterView<?> ListView, View v,int position,long id){
                if(position==0){                                                               //at 1st list item click
                    Intent intent=new Intent(MainActivity.this,DrinkCategory.class);              //start 2nd activity
                    startActivity(intent);
                }else if (position==1){
                    Intent intent=new Intent(MainActivity.this,FoodCategory.class);
                    startActivity(intent);
                }
            }
        };
        listView.setOnItemClickListener(itemClickListener);                                 // to make list item clickable
        ListView lv=(ListView)findViewById(R.id.favList);    //to display favorite drinks in list view
        try{
            Helper helper=new Helper(this);
            db=helper.getReadableDatabase();
            cursor=db.query("DRINK",new String[] {"_id","NAME"},"favorite=1",null,null,null,null);
            CursorAdapter cursorAdapter=new SimpleCursorAdapter(this,android.R.layout.simple_list_item_1,cursor,new String[] {"NAME"},new int[] {android.R.id.text1},0);
            lv.setAdapter(cursorAdapter);
        }catch (SQLiteException e){
            Toast.makeText(this,"Data not available",Toast.LENGTH_SHORT).show();
        }
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> ListView, View view, int position, long id) {
                Intent intent=new Intent(MainActivity.this,DrinkDetail.class);
                intent.putExtra("DRINKNO",(int)id);
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        try{
            Helper helper=new Helper(this);
            db=helper.getReadableDatabase();
            Cursor newCursor=db.query("DRINK",new String[] {"_id","NAME"},"favorite=1",null,null,null,null);//this will be exactly the same as above cursor
            ListView listFav=(ListView)findViewById(R.id.favList);
            CursorAdapter adapter= (CursorAdapter) listFav.getAdapter(); //get the listView adapter
            adapter.changeCursor(newCursor); //change cursor used by cursor adapter to a new one
        }catch (SQLiteException e)
        {
            Toast.makeText(this,"Data not available",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        cursor.close();
        db.close();
    }
}

