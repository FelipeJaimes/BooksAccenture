package com.example.android.booksaccenture.ui.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListPopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.booksaccenture.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.OnTouch;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {


    private Button mButton;

    public static final String PREFS_NAME = "PingBusPrefs";
    public static final String PREFS_SEARCH_HISTORY = "SearchHistory";
    private SharedPreferences settings;
    private List<String> history;
    private EditText textView;

    private ListPopupWindow lpw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        textView = findViewById(R.id.textInput);
        mButton = findViewById(R.id.button_search);

        settings = getSharedPreferences(PREFS_NAME, 0);
        history = new ArrayList<>(settings.getStringSet(PREFS_SEARCH_HISTORY, new HashSet<String>()));

        lpw = new ListPopupWindow(this);
        lpw.setAdapter(new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, history));
        lpw.setAnchorView(textView);
        lpw.setOnItemClickListener(this);

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                search();
            }
        });

        textView.setOnEditorActionListener(new EditText.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    search();
                    return true;
                }
                return false;
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
        reverseArray(historyArray);
        String item = historyArray[position];
        textView.setText(item);
        lpw.dismiss();
    }

    @OnTouch(R.id.textInput)
    public boolean onTouch(View v, MotionEvent event) {

        if (history.size() == 6) {
            String overload = history.get(history.size() - 6);
            history.remove(overload);
        }
        Collections.reverse(history);
        lpw.show();
        return false;

    }

    public void search() {

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

    public void reverseArray(String[] array) {
        for (int i = 0; i < array.length / 2; i++) {
            String temp = array[i];
            array[i] = array[array.length - i - 1];
            array[array.length - i - 1] = temp;
        }
    }

}
