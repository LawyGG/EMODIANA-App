package ull.emo_diana;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.io.File;

import ull.emo_diana.utilities.AppConfig;
import ull.emo_diana.utilities.Compress;
import ull.emo_diana.utilities.DeleteDialog;
import ull.emo_diana.utilities.InternalFileProvider;

/**
 * Main Activity for the app, also the Home Screen
 * This will set up the app's basic directory to save data
 */
public class MainActivity extends ActionBarActivity implements DeleteDialog.DeleteDialogListener {
    private final static String LOG_TAG = "Main_act";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public void onResume() {
        super.onResume();

        //Borrar los posibles datos que queden
        SharedPreferences prefs = getSharedPreferences(AppConfig.SH_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.clear();
        editor.commit();

        Log.d(LOG_TAG, "Shared Preferences borradas");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.send_data:
                sendEmail();
                return true;
            case R.id.delete_data:
                DeleteDialog dialog = new DeleteDialog();
                dialog.show(getFragmentManager(), null);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void sendEmail() {
        try {
            final Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);

            //Explicitly only use Gmail to send
            emailIntent.setClassName("com.google.android.gm", "com.google.android.gm.ComposeActivityGmail");
            emailIntent.setType("plain/text");
            emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, AppConfig.SUBJECT);
            emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, AppConfig.MESSAGE);

            String attachPath = createCompressFolder();
            //Add the attachment by specifying a reference to our custom ContentProvider
            //and the specific file of interest
            emailIntent.putExtra(
                    Intent.EXTRA_STREAM,
                    Uri.parse("content://" + InternalFileProvider.AUTHORITY + "/"
                            + attachPath));

            this.startActivity(Intent.createChooser(emailIntent, "Sending email..."));
        } catch (Throwable t) {
            Toast.makeText(this, "Request failed try again: " + t.toString(), Toast.LENGTH_LONG).show();
        }
    }

    /**
     * function to start "Emotion evaluation" process
     *
     * @param view
     */
    public void nextActivity(View view) {
        String actionName = "android.intent.action.USER";
        Intent intent = new Intent(actionName);
        startActivity(intent);
    }

    @Override
    public void onDialogPositiveClick() {
        String dirPath = getFilesDir().getAbsolutePath();
        File appFiles = new File(dirPath);

        for (File child : appFiles.listFiles())
            deleteRecursive(child);

        Log.v(LOG_TAG, "Borrado de datos completo");
        Toast.makeText(this, "Datos borrados", Toast.LENGTH_LONG).show();
    }

    private String createCompressFolder() {
        String zipPath = getCacheDir().getPath() + File.separator + AppConfig.ZIP_FILE;
        new Compress().zipFiles(getFilesDir().getPath(), zipPath);

        return AppConfig.ZIP_FILE;
    }

    private void deleteRecursive(File fileOrDirectory) {
        Log.v(LOG_TAG, fileOrDirectory.getAbsolutePath());

        if (fileOrDirectory.isDirectory())
            for (File child : fileOrDirectory.listFiles())
                deleteRecursive(child);

        fileOrDirectory.delete();
    }

}
