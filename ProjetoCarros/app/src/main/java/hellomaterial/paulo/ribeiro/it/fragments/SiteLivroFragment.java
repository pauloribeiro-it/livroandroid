package hellomaterial.paulo.ribeiro.it.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ProgressBar;
import hellomaterial.paulo.ribeiro.it.R;

public class SiteLivroFragment extends Fragment {

    private static final String URL_SOBRE = "http://www.livroandroid.com.br/sobre.htm";
    private WebView webView;
    private ProgressBar progress;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_site_livro, container, false);
//        webView = (WebView) view.findViewById(R.);
        progress = (ProgressBar) view.findViewById(R.id.progress);
        return view;
    }


}
