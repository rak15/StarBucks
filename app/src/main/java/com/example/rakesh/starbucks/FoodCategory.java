package com.example.rakesh.starbucks;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class FoodCategory extends ListActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_food_category);
        ListView listView=getListView();
        ArrayAdapter<Food> listadapter=new ArrayAdapter<Food>(this,android.R.layout.simple_list_item_1,Food.foods);
        listView.setAdapter(listadapter);
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        //super.onListItemClick(l, v, position, id);
        Intent intent= new Intent(FoodCategory.this,FoodDetail.class);
        intent.putExtra("idnumber",(int) id);
        startActivity(intent);
    }
}
