package hellomaterial.paulo.ribeiro.it.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.Html;
import hellomaterial.paulo.ribeiro.it.R;
import android.text.SpannableStringBuilder;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.widget.TextView;

import livroandroid.lib.utils.AndroidUtils;

/**
 * Created by paulo on 20/06/17.
 */

public class AboutDialog extends DialogFragment{

    public static void showAbout(FragmentManager fm){
        FragmentTransaction ft = fm.beginTransaction();
        Fragment prev = fm.findFragmentByTag("dialog_about");
        if(prev != null){
            ft.remove(prev);
        }
        ft.addToBackStack(null);
        new AboutDialog().show(ft,"dialog_about");
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        SpannableStringBuilder aboutBody = new SpannableStringBuilder();
        String versionName = AndroidUtils.getVersionName(getActivity());
        aboutBody.append(Html.fromHtml(getString(R.string.about_dialog_text,versionName)));

        LayoutInflater inflater = LayoutInflater.from(getActivity());
        TextView view = (TextView) inflater.inflate(R.layout.dialog_about,null);
        view.setText(aboutBody);
        view.setMovementMethod(new LinkMovementMethod());

        return new AlertDialog.Builder(getActivity()).setTitle(R.string.about_dialog_title).
                setView(view).
                setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).create();
    }
}
