package com.example.rakesh.starbucks;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class DrinkDetail extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drink_detail);
        ImageView photo = (ImageView) findViewById(R.id.imageView2);
        TextView name = (TextView) findViewById(R.id.textView);
        TextView description = (TextView) findViewById(R.id.textView2);

        Intent intent = getIntent();
        //Get the drink from the intent
        int drinkNo = intent.getIntExtra("DRINKNO", 0);   // 0 is the default value
        //Drink drink = Drink.drinks[drinkNo];              //drink is the id number of array element selected(id can be:0 or 1 or 2)

        //photo.setImageResource(drink.getImg());           //image of selected array item will be displayed
        //photo.setContentDescription(drink.getName());  //use this to get data from java class

        //Populate the drink name
        //name.setText(drink.getName());
        //Populate the drink description
        //description.setText(drink.getDescription());
        try {                                   //to access data from database
            Helper helper = new Helper(this);
            SQLiteDatabase db = helper.getWritableDatabase();
            Cursor cursor = db.query("DRINK", new String[]{"NAME", "DESCRIPTION", "image_id", "favorite"}, "_id=?", new String[]{Integer.toString(drinkNo)}, null, null, null);
            if (cursor.moveToFirst()) {
                String nameText = cursor.getString(0);
                String descriptionText = cursor.getString(1);
                int photoId = cursor.getInt(2);
                boolean isFavorite = (cursor.getInt(3) == 1);  //1 for true, 0 for false
                CheckBox checkBox = (CheckBox) findViewById(R.id.favorite);
                checkBox.setChecked(isFavorite);   //putting boolean value in checkbox object
                photo.setImageResource(photoId);
                photo.setContentDescription(nameText);
                name.setText(nameText);
                description.setText(descriptionText);
            }
            cursor.close();
            db.close();
        } catch (SQLiteException e) {
            Toast.makeText(this, "Data not available", Toast.LENGTH_SHORT).show();
        }
    }

    public void onFavoriteClicked(View view) {   //to update Drink table's Favorite column value
        int drinkNo = getIntent().getIntExtra("DRINKNO", 0);
        new UpdateDrinkTask().execute(drinkNo);  //updating drink database favorite column using asyncTask i.e. in Background
    }
    private class UpdateDrinkTask extends AsyncTask<Integer,Void,Boolean>{
        ContentValues drinkValues;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            CheckBox checkBox = (CheckBox) findViewById(R.id.favorite);
            drinkValues = new ContentValues();
            drinkValues.put("favorite", checkBox.isChecked());  //adding checked status of checkbox (i.e. true or false)
        }

        @Override
        protected Boolean doInBackground(Integer... params) {
            int drinkNo=params[0];
            try {
                Helper helper = new Helper(DrinkDetail.this);
                SQLiteDatabase db=helper.getWritableDatabase();
                db.update("DRINK",drinkValues,"_id=?",new String[] {Integer.toString(drinkNo)});
                db.close();
                return true;
            } catch (SQLiteException e) {
                return false;
            }
        }

        @Override
        protected void onPostExecute(Boolean success) {   //here success get the result returned by doInBackground method
            super.onPostExecute(success);
            if (!success)
                Toast.makeText(DrinkDetail.this,"Database unavailable",Toast.LENGTH_SHORT).show();
        }
    }
}