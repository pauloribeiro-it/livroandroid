package hellomaterial.paulo.ribeiro.it.fragments.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

/**
 * Created by paulo on 01/08/17.
 */

public class DeletarCarroDialog extends DialogFragment {
    private Callback callBack;

    public interface Callback{
        void onClickYes();
    }

    public static void show(FragmentManager fm, Callback callBack){
        FragmentTransaction ft = fm.beginTransaction();
        Fragment prev = fm.findFragmentByTag("deletar_carro");
        if(prev != null){
            ft.remove(prev);
        }
        ft.addToBackStack(null);
        DeletarCarroDialog frag = new DeletarCarroDialog();
        frag.callBack = callBack;
        frag.show(ft,"deletar_carro");
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch(which){
                    case DialogInterface.BUTTON_POSITIVE:
                        if(callBack != null){
                            callBack.onClickYes();
                        }
                        break;
                    case DialogInterface.BUTTON_NEGATIVE:
                        break;
                }
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("Deletar esse carro?");
        builder.setPositiveButton("Sim",dialogClickListener);
        builder.setNegativeButton("Não",dialogClickListener);
        return builder.create();
    }
}
