package com.johnyhawkdesigns.a50_codingwithmitch_sqlite;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Ahsan on 7/18/2018.
 */

public class ListDataActivity extends AppCompatActivity {

    private static final String TAG = "ListDataActivity";

    DatabaseHelper databaseHelper;
    ListView listView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_layout);

        databaseHelper = new DatabaseHelper(this);
        listView = (ListView) findViewById(R.id.listView);

        populateListView();
    }

    /**
     * Populates list view by searching all database and display it's items in an ArrayList
     */
    private void populateListView() {
        Log.d(TAG, "populateListView: Displaying data in the ListView");

        //Get the data and append to the list
        Cursor data = databaseHelper.getData();
        ArrayList<String> listData = new ArrayList<>();

        //Iterate through our Cursor data and add each item to our ArrayList
        while (data.moveToNext()){

            //get the value from the database in column 1 which is "name" column, then add it to the ArrayList
            listData.add(data.getString(1)); //TODO: Change 1 to 0 and see if column 0 "ID" column is displayed here - Tested and yes, it shows numbers

            Log.d(TAG, "while loop: Column 0 = " + data.getString(0));
            Log.d(TAG, "while loop: Column 1 = " + data.getString(1));
        }

        //create the list adapter and set the adapter
        ListAdapter adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listData);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String name = parent.getItemAtPosition(position).toString();
                Log.d(TAG, "onItemClick: You Clicked on " + name);

                Cursor data = databaseHelper.getItemID(name); //Search data base and save the id found after search associated with that name in Cursor data
                int itemID = -1;
                while(data.moveToNext()){
                    itemID = data.getInt(0);
                }


                if(itemID > -1){
                    Log.d(TAG, "onItemClick: The ID is: " + itemID);

                    Intent editScreenIntent = new Intent(ListDataActivity.this, EditDataActivity.class);
                    editScreenIntent.putExtra("id",itemID);
                    editScreenIntent.putExtra("name",name);
                    startActivity(editScreenIntent);
                }
                else{
                    toastMessage("No ID associated with that name");
                }

            }
        });



    }


    /**
     * customizable toast
     * @param message
     */
    private void toastMessage(String message){
        Toast.makeText(this,message, Toast.LENGTH_SHORT).show();
    }

}
