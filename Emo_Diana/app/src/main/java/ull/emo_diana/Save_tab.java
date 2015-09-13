package ull.emo_diana;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.util.Xml;
import android.view.View;
import android.widget.Toast;

import org.xmlpull.v1.XmlSerializer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

import ull.emo_diana.utilities.AppConfig;


public class Save_tab extends ActionBarActivity {

    final private String LOG_TAG = "Save_tab";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save_tab);
    }

    public void clickHeaderButton(View view) {
        AppConfig.clickHeaderButton(view);
    }

    public void clickControlButton(View view) {
        String actionName = "android.intent.action.RECORD";

        Intent intent = new Intent(actionName);
        this.startActivity(intent);
    }

    /**
     * funcion para guardar los datos
     * Estos se extraen del SharedPreferences y se escriben en un fichero XML
     *
     * @param view
     */
    public void saveButton(View view) {
        //Leer las preferencias
        SharedPreferences prefs = getSharedPreferences(AppConfig.SH_PREF_NAME, Context.MODE_PRIVATE);
        String name = prefs.getString(AppConfig.NAME_TAG, null);
        String age = prefs.getString(AppConfig.AGE_TAG, null);
        String gender = prefs.getString(AppConfig.GENDER_TAG, null);
        String emo = prefs.getString(AppConfig.EMOTION_TAG, null);
        String lvl = prefs.getString(AppConfig.LEVEL_TAG, null);
        String why = prefs.getString(AppConfig.WHY_TAG, null);

        //Verificar que tenemos todos los datos
        if (!(name == null) && !(age == null) && !(gender == null) && !(emo == null) && !(lvl == null) && !(why == null)) {
            File folder = createFolder();
            writeXML(folder);
        } else {
            Toast.makeText(this, "Aun faltan datos por rellenar", Toast.LENGTH_LONG).show();
        }
    }

    private File createFolder() {
        //Calculate datetime
        //La carpeta para los datos tendra el nombre de la fecha
        //As? nos aseguramos que no haya colisiones
        SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyy_HHmmss");
        String folderName = getFilesDir().getAbsolutePath() + File.separator + sdf.format(new Date());
        File folderDir = new File(folderName);
        folderDir.mkdir();

        Log.v(LOG_TAG, "Carpeta creada: " + folderDir.getAbsolutePath());

        return folderDir;
    }

    private void writeXML(File folder) {
        //Leer las preferencias
        SharedPreferences prefs = getSharedPreferences(AppConfig.SH_PREF_NAME, Context.MODE_PRIVATE);
        String name = prefs.getString(AppConfig.NAME_TAG, null);
        String age = prefs.getString(AppConfig.AGE_TAG, null);
        String gender = prefs.getString(AppConfig.GENDER_TAG, null);
        String emo = prefs.getString(AppConfig.EMOTION_TAG, null);
        String lvl = prefs.getString(AppConfig.LEVEL_TAG, null);
        String why = prefs.getString(AppConfig.WHY_TAG, null);

        //Configuracion del formato en AppConfig
        String path = folder.getAbsolutePath() + File.separator + AppConfig.XML_FILE_NAME;

        try {
            FileOutputStream xfile = new FileOutputStream(path);
            XmlSerializer xmlSerializer = Xml.newSerializer();
            StringWriter writer = new StringWriter();
            xmlSerializer.setOutput(writer);

            xmlSerializer.startDocument("UTF-8", true);

            //User data
            xmlSerializer.startTag(null, AppConfig.USER_TAG);
            //Name
            xmlSerializer.startTag(null, AppConfig.NAME_TAG);
            xmlSerializer.text(name);
            xmlSerializer.endTag(null, AppConfig.NAME_TAG);
            //Age
            xmlSerializer.startTag(null, AppConfig.AGE_TAG);
            xmlSerializer.text(age);
            xmlSerializer.endTag(null, AppConfig.AGE_TAG);
            //Gender
            xmlSerializer.startTag(null, AppConfig.GENDER_TAG);
            xmlSerializer.text(gender);
            xmlSerializer.endTag(null, AppConfig.GENDER_TAG);
            //End User Data
            xmlSerializer.endTag(null, AppConfig.USER_TAG);


            //EValidation data
            xmlSerializer.startTag(null, AppConfig.EVAL_TAG);
            //Emotion
            xmlSerializer.startTag(null, AppConfig.EMOTION_TAG);
            xmlSerializer.text(emo);
            xmlSerializer.endTag(null, AppConfig.EMOTION_TAG);
            //Level
            xmlSerializer.startTag(null, AppConfig.LEVEL_TAG);
            xmlSerializer.text(lvl);
            xmlSerializer.endTag(null, AppConfig.LEVEL_TAG);
            //Why
            xmlSerializer.startTag(null, AppConfig.WHY_TAG);
            xmlSerializer.text(why);
            xmlSerializer.endTag(null, AppConfig.WHY_TAG);
            //End User Data
            xmlSerializer.endTag(null, AppConfig.EVAL_TAG);

            xmlSerializer.endDocument();
            xmlSerializer.flush();
            String dataWrite = writer.toString();
            xfile.write(dataWrite.getBytes());
            Log.v(LOG_TAG, xfile.toString());
            xfile.close();

            Log.v(LOG_TAG, "Fichero creado");
            Toast.makeText(this, "Datos guardados", Toast.LENGTH_LONG).show();

            String actionName = "android.intent.action.HOME";
            Intent intent = new Intent(actionName);
            startActivity(intent);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
