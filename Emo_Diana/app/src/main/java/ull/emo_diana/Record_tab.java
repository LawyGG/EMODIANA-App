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
import android.widget.EditText;

import ull.emo_diana.utilities.AppConfig;


public class Record_tab extends ActionBarActivity {

    private String message;

   // private MediaRecorder mRecorder = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_tab);

        loadOldData();
        setTVListener();
    }

    private void loadOldData() {
        SharedPreferences prefs = getSharedPreferences(AppConfig.SH_PREF_NAME, Context.MODE_PRIVATE);
        String auxMsg = prefs.getString(AppConfig.WHY_TAG, null);
        Log.v(AppConfig.WHY_TAG, "Mensaje: " + message);

        if (auxMsg != null){
            EditText tv = (EditText) findViewById(R.id.messageET);
            tv.setText("" + auxMsg);
        }

    }

    private void setTVListener() {

        EditText tv = (EditText) findViewById(R.id.messageET);

        tv.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                message = s.toString();
                Log.v(AppConfig.WHY_TAG, "Mensaje: " + message);

                //Save selection under SharedPrefences
                SharedPreferences prefs = getSharedPreferences(AppConfig.SH_PREF_NAME, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = prefs.edit();
                editor.putString(AppConfig.WHY_TAG, message);
                editor.commit();
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });
    }


    public void clickHeaderButton(View view) {
        AppConfig.clickHeaderButton(view);
    }

    public void clickControlButton(View view) {
        String actionName = null;

        if (view.getId() == R.id.next_button) {
            actionName = "android.intent.action.SAVE";
        } else if (view.getId() == R.id.back_button) {
            actionName = "android.intent.action.LEVEL";
        }

        if (actionName != null) {
            Intent intent = new Intent(actionName);
            this.startActivity(intent);
        }
    }

//    private void onRecord(boolean start) {
//        if (start) {
//            startRecording();
//        } else {
//            stopRecording();
//        }
//    }
//
//    private void startRecording() {
//        mRecorder = new MediaRecorder();
//        mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
//        mRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
//
//        //Calculate datetime
//        SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyy_HHmmss");
//        String mFileName = sdf.format(new Date());
//
//        mRecorder.setOutputFile(mFileName);
//        mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
//
//        try {
//            mRecorder.prepare();
//        } catch (IOException e) {
//            Log.e("Record_tab", "prepare() failed");
//        }
//
//        mRecorder.start();
//    }
//
//    private void stopRecording() {
//        mRecorder.stop();
//        mRecorder.release();
//        mRecorder = null;
//    }
}
