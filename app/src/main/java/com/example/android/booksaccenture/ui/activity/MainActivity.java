package com.example.android.booksaccenture.ui.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListPopupWindow;
import android.widget.Toast;

import com.example.android.booksaccenture.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnEditorAction;
import butterknife.OnTouch;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    public static final String PREFS_NAME = "PingBusPrefs";
    public static final String PREFS_SEARCH_HISTORY = "SearchHistory";

    @BindView(R.id.textInput) EditText editTextSearchTerm;
    private ListPopupWindow listPopupWindow;

    private SharedPreferences preferences;
    private List<String> listSearchHistory;
    private ArrayAdapter<String> adapterSearchWords;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        preferences = getSharedPreferences(PREFS_NAME, 0);
        listSearchHistory = new ArrayList<>(preferences.getStringSet(PREFS_SEARCH_HISTORY, new HashSet<String>()));
        adapterSearchWords = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listSearchHistory);
        initSearchList();
    }

    private void initSearchList() {
        listPopupWindow = new ListPopupWindow(this);
        listPopupWindow.setAdapter(adapterSearchWords);
        listPopupWindow.setAnchorView(editTextSearchTerm);
        listPopupWindow.setOnItemClickListener(this);
    }

    //TODO: DO NOT ADD WORDS THAT RETURNS EMPTY RESULTS
    private void addSearchInput(String input) {
        if (!listSearchHistory.contains(input)) {
            listSearchHistory.add(input);
        }
    }

    private void savePrefs() {
        SharedPreferences preferences = getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putStringSet(PREFS_SEARCH_HISTORY, new HashSet<>(listSearchHistory));
        editor.apply();
    }

    @Override
    protected void onPause() {
        listPopupWindow.dismiss();
        super.onPause();

    }

    @Override
    protected void onPostResume() {
        editTextSearchTerm.setText("");
        super.onPostResume();
    }

    @Override
    protected void onStop() {
        super.onStop();
        savePrefs();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String[] historyArray = listSearchHistory.toArray(new String[listSearchHistory.size()]);
        reverseArray(historyArray);
        String item = historyArray[position];
        editTextSearchTerm.setText(item);
        listPopupWindow.dismiss();
    }

    @OnClick(R.id.button_search)
    public void onButtonSearch() {
        searchTerm();
    }

    @OnEditorAction(R.id.textInput)
    public boolean onSearchIMEAction(int actionId) {
        if (actionId == EditorInfo.IME_ACTION_SEARCH) {
            searchTerm();
            return true;
        }
        return false;
    }

    @OnTouch(R.id.textInput)
    public boolean onEditSearchTouch() {
        if (listSearchHistory.size() == 6) {
            String overload = listSearchHistory.get(listSearchHistory.size() - 6);
            listSearchHistory.remove(overload);
        }
        Collections.reverse(listSearchHistory);
        listPopupWindow.show();
        return false;

    }

    private void searchTerm() {
        String searchString = editTextSearchTerm.getText().toString();
        if (searchString.matches("")) {
            Toast.makeText(MainActivity.this, "You did not enter a search value", Toast.LENGTH_SHORT).show();
        } else {
            addSearchInput(editTextSearchTerm.getText().toString());
            Intent intent = new Intent(MainActivity.this, ListActivity.class);
            intent.putExtra("SEARCH_TERM", searchString);
            startActivity(intent);
        }
    }

    private void reverseArray(String[] array) {
        for (int i = 0; i < array.length / 2; i++) {
            String temp = array[i];
            array[i] = array[array.length - i - 1];
            array[array.length - i - 1] = temp;
        }
    }

}
