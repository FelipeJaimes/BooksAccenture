package com.example.android.booksaccenture.ui.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View.OnTouchListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.ListPopupWindow;

import com.example.android.booksaccenture.R;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MainActivity extends AppCompatActivity implements View.OnTouchListener, AdapterView.OnItemClickListener {


    private Button mButton;

    public static final String PREFS_NAME = "PingBusPrefs";
    public static final String PREFS_SEARCH_HISTORY = "SearchHistory";
    private SharedPreferences settings;
    private List<String> history;
    private ArrayAdapter adapter;
    private EditText textView;

    private ListPopupWindow lpw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.textInput);
        mButton = findViewById(R.id.button_search);

        settings = getSharedPreferences(PREFS_NAME, 0);
        history = new ArrayList<>(settings.getStringSet(PREFS_SEARCH_HISTORY, new HashSet<String>()));


        textView.setOnTouchListener(this);
        lpw = new ListPopupWindow(this);
        lpw.setAdapter(new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, history));
        lpw.setAnchorView(textView);
        lpw.setModal(true);
        lpw.setOnItemClickListener(this);

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String searchString = textView.getText().toString();
                if (searchString.matches("")) {
                    Toast.makeText(MainActivity.this, "You did not enter a search value", Toast.LENGTH_SHORT).show();
                } else {
                    addSearchInput(textView.getText().toString());
                    Intent intent = new Intent(MainActivity.this, ListActivity.class);
                    intent.putExtra("SEARCH_TERM", searchString);
                    startActivity(intent);
                }

            }
        });

    }

    private void addSearchInput(String input) {
        if (!history.contains(input)) {
            history.add(input);
        }
    }

    private void savePrefs() {
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putStringSet(PREFS_SEARCH_HISTORY, new HashSet<String>(history));
        editor.commit();
    }

    @Override
    protected void onStop() {
        super.onStop();
        savePrefs();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        String[] historyArray = history.toArray(new String[history.size()]);
        String item = historyArray[position];
        textView.setText(item);

        lpw.dismiss();
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {

        if(history.size() == 6){
            String overload = history.get(history.size() - 6);
            history.remove(overload);
        }

        lpw.show();
        return true;

    }

}
