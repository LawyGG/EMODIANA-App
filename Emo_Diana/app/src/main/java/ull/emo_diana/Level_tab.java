package ull.emo_diana;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.RatingBar;

import ull.emo_diana.utilities.AppConfig;

import static ull.emo_diana.R.id.ratingBar;


public class Level_tab extends ActionBarActivity {

    private String LOG_TAG = "Level_tab";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level_tab);

        loadOldData();
        //Set listener to Rating bar
        setRatingListener();
    }

    private void loadOldData() {
        SharedPreferences prefs = getSharedPreferences(AppConfig.SH_PREF_NAME, Context.MODE_PRIVATE);
        String auxLevel = prefs.getString(AppConfig.LEVEL_TAG, null);

        if (auxLevel != null) {
            RatingBar bar = (RatingBar) findViewById(ratingBar);
            bar.setRating(Float.parseFloat(auxLevel));
        }
    }

    /**
     * function to set rating listener on change
     * so we can get the value on time
     */
    private void setRatingListener() {
        RatingBar bar = (RatingBar) findViewById(ratingBar);
        bar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
                                             @Override
                                             public void onRatingChanged(RatingBar ratingBar, float rating,
                                                                         boolean fromUser) {
                                                 Log.v(LOG_TAG, "Actual rating: " + ratingBar.getRating());

                                                 //Save selection under SharedPrefences
                                                 SharedPreferences prefs = getSharedPreferences(AppConfig.SH_PREF_NAME, Context.MODE_PRIVATE);
                                                 SharedPreferences.Editor editor = prefs.edit();
                                                 editor.putString(AppConfig.LEVEL_TAG, Float.toString(ratingBar.getRating()));
                                                 editor.commit();
                                             }
                                         }
        );
    }

    public void clickHeaderButton(View view) {
        AppConfig.clickHeaderButton(view);
    }

    public void clickControlButton(View view) {
        String actionName = null;

        if (view.getId() == R.id.next_button) {
            actionName = "android.intent.action.RECORD";
        } else if (view.getId() == R.id.back_button) {
            actionName = "android.intent.action.EMOTION";
        }

        if (actionName != null) {
            Intent intent = new Intent(actionName);
            this.startActivity(intent);
        }
    }

}