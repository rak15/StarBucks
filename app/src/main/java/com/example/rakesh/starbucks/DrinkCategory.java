package com.example.rakesh.starbucks;

import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

public class DrinkCategory extends ListActivity {          //List activity contains only the listview in its layout
    private SQLiteDatabase db;  //We’re adding these as private variables so we can close
    private Cursor cursor;      //the database and cursor in our onDestroy() method.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);          //no need to attach layout to this list activity
        ListView listView=getListView();                   // to get list view without layout (in case of list activity)
        //ArrayAdapter<Drink> listAdapter=new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,Drink.drinks); //to get array data from Drink java file
       // listView.setAdapter(listAdapter);                  //to display array data in list view
        try{
            Helper helper=new Helper(this);
            db=helper.getReadableDatabase();
            cursor=db.query("DRINK",new String[] {"_id","NAME"},null,null,null,null,null);
            CursorAdapter cursorAdapter=new SimpleCursorAdapter(this,android.R.layout.simple_list_item_1,cursor,new String[] {"NAME"},new int[] {android.R.id.text1},0);
            listView.setAdapter(cursorAdapter);
        }catch (SQLiteException e){
            Toast.makeText(this,"Data not available",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {   //to respond on item click in list activity
        //super.onListItemClick(l, v, position, id);
        Intent intent=new Intent(DrinkCategory.this,DrinkDetail.class);
        intent.putExtra("DRINKNO", (int) id);                   //passing the array id of selected drinks array item(eg:of latte item)
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        cursor.close();            //closing the cursor and database with activity's onDestroy method bcoz cursoradapter needs cursor
        db.close();                //until the activity destroys
    }
}
