package com.example.rakesh.starbucks;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class FoodDetail extends AppCompatActivity {
    ImageView imageView;
    TextView tv1,tv2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_detail);
        imageView=(ImageView)findViewById(R.id.imageView3);
        tv1=(TextView)findViewById(R.id.textView3);
        tv2=(TextView)findViewById(R.id.textView4);
        Intent intent=getIntent();
        int idnum=intent.getIntExtra("idnumber",0);
        Food food=Food.foods[idnum];
        imageView.setImageResource(food.getImg());
        imageView.setContentDescription(food.getName());
        tv1.setText(food.getName());
        tv2.setText(food.getDescription());
    }
}
