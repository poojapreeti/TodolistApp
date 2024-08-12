package com.example.to_dolist;

import androidx.appcompat.app.AppCompatActivity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.app.AlertDialog;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Get references to UI widgets
        ListView myListView = (ListView)findViewById(R.id.myListView);
        final EditText myEditText = (EditText)findViewById(R.id.myEditText);

        // Create the array list of to-do items
        final ArrayList<String> todoItems = new ArrayList<String>();

        // Create the Array Adapter to bind the array to the list view
        final ArrayAdapter<String> aa;
        aa = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, todoItems);

        // Bind the Array Adapter to the list view
        myListView.setAdapter(aa);

        // Add a new item to the list when Enter key is pressed
        myEditText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN)
                    if ((keyCode == KeyEvent.KEYCODE_DPAD_CENTER) || (keyCode == KeyEvent.KEYCODE_ENTER)){
                        todoItems.add(0, myEditText.getText().toString());
                        aa.notifyDataSetChanged();
                        myEditText.setText("");
                        return true;
                    }
                return false;
            }
        });

        // Add functionality to delete an item when it's long-clicked
        myListView.setOnItemLongClickListener((parent, view, position, id) -> {
            // Remove the item from the list
            todoItems.remove(position);
            aa.notifyDataSetChanged();
            return true;
        });

        // Add functionality to edit an item when it's clicked
        myListView.setOnItemClickListener((parent, view, position, id) -> {
            final EditText editText = new EditText(MainActivity.this);
            editText.setText(todoItems.get(position));

            // Create an AlertDialog to edit the item
            new AlertDialog.Builder(MainActivity.this)
                    .setTitle("Edit Item")
                    .setView(editText)
                    .setPositiveButton("Save", (dialog, which) -> {
                        todoItems.set(position, editText.getText().toString());
                        aa.notifyDataSetChanged();
                    })
                    .setNegativeButton("Cancel", null)
                    .show();
        });
    }
}
