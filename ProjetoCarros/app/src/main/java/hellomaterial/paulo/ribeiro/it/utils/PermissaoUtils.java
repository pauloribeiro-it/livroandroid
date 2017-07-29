package hellomaterial.paulo.ribeiro.it.utils;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by paulo on 28/07/17.
 */

public class PermissaoUtils {
    public static boolean validate(Activity activity, int requestCode, String... permissions){
        List<String> list = new ArrayList<>();
        for(String permission:permissions){
            boolean ok = ContextCompat.checkSelfPermission(activity,permission) == PackageManager.PERMISSION_GRANTED;
            if(!ok){
                list.add(permission);
            }
        }
        if(!list.isEmpty()){
            return true;
        }

        String[] newPermissions = new String[list.size()];
        list.toArray(newPermissions);
        ActivityCompat.requestPermissions(activity,newPermissions,1);
        return false;
    }
}
