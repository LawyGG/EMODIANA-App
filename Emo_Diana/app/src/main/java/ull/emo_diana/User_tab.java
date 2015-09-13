package ull.emo_diana;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;

import ull.emo_diana.utilities.AppConfig;


public class User_tab extends ActionBarActivity {

    private final static String LOG_TAG = "USER";

    private final static String GIRL = "female";
    private final static String BOY = "male";

    private String user_name;
    private String user_age;
    private String user_gender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_tab);

        loadOldData();
        setTVListeners();
    }

    private void setTVListeners() {
        final TextView name_tv = (TextView) findViewById(R.id.editTextName);
        TextView age_tv = (TextView) findViewById(R.id.editTextAge);

        name_tv.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                user_name = s.toString();

                Log.v(LOG_TAG, "El nombre es: " + user_name);

                //Save selection under SharedPrefences
                SharedPreferences prefs = getSharedPreferences(AppConfig.SH_PREF_NAME, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = prefs.edit();
                editor.putString(AppConfig.NAME_TAG, user_name);
                editor.commit();
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });

        age_tv.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                user_age = s.toString();

                Log.v(LOG_TAG, "La edad es: " + user_age);

                //Save selection under SharedPrefences
                SharedPreferences prefs = getSharedPreferences(AppConfig.SH_PREF_NAME, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = prefs.edit();
                editor.putString(AppConfig.AGE_TAG, user_age);
                editor.commit();
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });
    }

    private void loadOldData() {

        SharedPreferences prefs = getSharedPreferences(AppConfig.SH_PREF_NAME, Context.MODE_PRIVATE);
        String auxName = prefs.getString(AppConfig.NAME_TAG, null);
        String auxAge = prefs.getString(AppConfig.AGE_TAG, null);
        String auxGender = prefs.getString(AppConfig.GENDER_TAG, null);

        Log.v(LOG_TAG, "SAVED-DATA: " + auxName + " " + auxAge + " " + auxGender);

        if (auxName != null) {
            TextView edT = (TextView) findViewById(R.id.editTextName);
            edT.setText("" + auxName);
        }

        if (auxAge != null) {

            TextView edT = (TextView) findViewById(R.id.editTextAge);
            edT.setText("" + auxAge);
        }

        if (auxGender!=null)
        switch (auxGender){
            case GIRL:
                int id1 = getResources().getIdentifier("radioGirl", "id", getPackageName());
                RadioButton radB1 = (RadioButton) findViewById(id1);
                radB1.setChecked(true);
                break;
            case BOY:
                int id2 = getResources().getIdentifier("radioBoy", "id", getPackageName());
                RadioButton radB2 = (RadioButton) findViewById(id2);
                radB2.setChecked(true);
                break;
        }
    }


    public void clickRadioButtons(View view){
        if (view.getId() == R.id.radioGirl) {
            user_gender = GIRL;
        } else if (view.getId() == R.id.radioBoy) {
            user_gender = BOY;
        }

        //Save selection under SharedPrefences
        SharedPreferences prefs = getSharedPreferences(AppConfig.SH_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(AppConfig.GENDER_TAG, user_gender);
        editor.commit();
    }

    public void clickHeaderButton(View view) {
        AppConfig.clickHeaderButton(view);
    }

    public void clickControlButton(View view) {

        String actionName = null;

        if (view.getId() == R.id.next_button) {
            actionName = "android.intent.action.EMOTION";
        } else if (view.getId() == R.id.back_button) {
            actionName = "android.intent.action.HOME";
        }

        if (actionName != null) {
            Intent intent = new Intent(actionName);
            this.startActivity(intent);
        }
    }
}
