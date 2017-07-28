package hellomaterial.paulo.ribeiro.it.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by paulo on 27/07/17.
 */

public class PrefsUtil {

    public static boolean isCheckPushOn(final Context context){
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        return sp.getBoolean("PREF_CHECK_PUSH",false);
    }
}
