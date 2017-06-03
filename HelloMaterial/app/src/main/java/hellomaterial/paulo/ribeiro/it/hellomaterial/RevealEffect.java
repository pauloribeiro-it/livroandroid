package hellomaterial.paulo.ribeiro.it.hellomaterial;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.os.Build;
import android.view.View;
import android.view.ViewAnimationUtils;

/**
 * Created by paulo on 02/06/17.
 */

@TargetApi(Build.VERSION_CODES.LOLLIPOP)
public class RevealEffect {

    public static void show(View view, long animDuration){
        int cx = (view.getLeft()+view.getRight())/2;
        int cy = (view.getTop()+view.getBottom())/2;

        int finalRadius = Math.max(view.getWidth(),view.getHeight());
        Animator anim = ViewAnimationUtils.createCircularReveal(view,cx,cy,0,finalRadius);
        view.setVisibility(View.VISIBLE);
        anim.setDuration(animDuration);
        anim.start();
    }

    public static void hide(final View view, long animDuration){
        int cx = (view.getLeft()+view.getRight())/2;
        int cy = (view.getTop()+view.getBottom())/2;

        int initialRadius = view.getWidth();
        Animator anim = ViewAnimationUtils.createCircularReveal(view,cx,cy,initialRadius,0);
        anim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                view.setVisibility(View.INVISIBLE);
            }
        });
        anim.setDuration(animDuration);
        anim.start();
    }
}
