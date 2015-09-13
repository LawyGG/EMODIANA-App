package ull.emo_diana;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import ull.emo_diana.utilities.AppConfig;


public class Emo_tab extends ActionBarActivity {

    ImageView current_emo_img = null;
    SharedPreferences prefs;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emo_tab);

        loadOldData();
    }

    private void loadOldData() {
        SharedPreferences prefs = getSharedPreferences(AppConfig.SH_PREF_NAME, Context.MODE_PRIVATE);
        String auxEmo = prefs.getString(AppConfig.EMOTION_TAG, null);

        if (auxEmo != null) {
            int id = getResources().getIdentifier(auxEmo, "id", getPackageName());
            View emoView = findViewById(id);
            selectImage(emoView);
        }
    }

    /**
     * This will select the image by changing its background
     *
     * @param view
     */
    public void selectImage(View view) {
        if (current_emo_img != null) {
            //change old background
            current_emo_img.setBackgroundColor(getResources().getColor(R.color.med_blue));
        }
        //store current values
        current_emo_img = (ImageView) view;
        String current_emo = getResources().getResourceEntryName(view.getId());
        //select img = change bg
        current_emo_img.setBackgroundColor(getResources().getColor(R.color.dark_blue));
        Log.v("Emotion", current_emo);

        //Save selection under SharedPrefences
        SharedPreferences prefs = getSharedPreferences(AppConfig.SH_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(AppConfig.EMOTION_TAG, current_emo);
        editor.commit();

    }

    public void clickHeaderButton(View view) {
        AppConfig.clickHeaderButton(view);
    }

    public void clickControlButton(View view) {

        String actionName = null;

        if (view.getId() == R.id.next_button) {
            actionName = "android.intent.action.LEVEL";
        } else if (view.getId() == R.id.back_button) {
            actionName = "android.intent.action.USER";
        }

        if (actionName != null) {
            Intent intent = new Intent(actionName);
            this.startActivity(intent);
        }
    }
}
