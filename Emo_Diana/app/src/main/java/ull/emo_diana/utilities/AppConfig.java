package ull.emo_diana.utilities;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.View;

import ull.emo_diana.R;

/**
 * Clase para cambiar las distintas configuraciones generales de la App
 * Encapsula:
 * - Constantes del SharedPreferences - usadas como etiquetas del xml resultante
 * - Metodo para cambiar de tab
 * <p/>
 * Created by Lawy on 08/06/2015.
 */
public class AppConfig {
    public static final String LOG_TAG = "AppConfig";

    //Shared Preferences variables
    public static final String SH_PREF_NAME = "Options";

    public static final String USER_TAG = "user data";
    public static final String NAME_TAG = "name";
    public static final String GENDER_TAG = "gender";
    public static final String AGE_TAG = "age";

    public static final String EVAL_TAG = "evaluation";
    public static final String EMOTION_TAG = "emotion";
    public static final String LEVEL_TAG = "level";
    public static final String WHY_TAG = "why";

    //Files variables
    public static final String XML_FILE_NAME = "data.xml";
    public final static String ZIP_FILE = "files.bak"; //Aconsejable no poner .zip o algo que Google pueda curiosear!!!
    //Variables del email
    public final static String SUBJECT = "Emodiana Datos";
    public final static String MESSAGE = "En el fichero adjunto encontrara los datos recogidos con la aplicacion.";


    /**
     * function to control clicking on bar buttons
     *
     * @param view
     */
    static public void clickHeaderButton(View view) {
        String actionName = null;
        //Get actual activity name
        Activity host = (Activity) view.getContext();
        String hostName = host.getComponentName().getClassName();

        Log.v(LOG_TAG, "Current activity: " + hostName);

        //Change to level activity
        if (view.getId() == R.id.green_1 && !hostName.contains("ull.emo_diana.User_tab")) {
            actionName = "android.intent.action.USER";
        } else if (view.getId() == R.id.yellow_2 && !hostName.contains("ull.emo_diana.Emo_tab")) {
            //Change to level activity
            actionName = "android.intent.action.EMOTION";
        } else if (view.getId() == R.id.orange_3 && !hostName.contains("ull.emo_diana.Level_tab")) {
            //Change to level activity
            actionName = "android.intent.action.LEVEL";
        } else if (view.getId() == R.id.red_4 && !hostName.contains("ull.emo_diana.Record_tab")) {
            //Change to record_why activity
            actionName = "android.intent.action.RECORD";
        } else if (view.getId() == R.id.pink_5 && !hostName.contains("ull.emo_diana.Save_tab")) {
            //Change to save activity
            actionName = "android.intent.action.SAVE";
        }

        if (actionName != null) {
            Intent intent = new Intent(actionName);
            host.startActivity(intent);
        }
    }
}
