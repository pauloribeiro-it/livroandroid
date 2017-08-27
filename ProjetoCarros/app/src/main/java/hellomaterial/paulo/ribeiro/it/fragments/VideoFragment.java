package hellomaterial.paulo.ribeiro.it.fragments;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.MediaController;
import android.widget.VideoView;

import org.parceler.Parcels;

import hellomaterial.paulo.ribeiro.it.R;
import hellomaterial.paulo.ribeiro.it.domain.Carro;

/**
 * Created by paulo on 27/08/17.
 */

public class VideoFragment extends livroandroid.lib.fragment.BaseFragment {
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_video,container,false);
        VideoView videoView = (VideoView) view.findViewById(R.id.videoView);
        Carro c = getArguments().getParcelable("carro");
        if(c != null){
            videoView.setVideoURI(Uri.parse(c.urlVideo));
            videoView.setMediaController(new MediaController(getContext()));
            videoView.start();
            toast("start: "+c.urlVideo);
        }
        return view;
    }
}
