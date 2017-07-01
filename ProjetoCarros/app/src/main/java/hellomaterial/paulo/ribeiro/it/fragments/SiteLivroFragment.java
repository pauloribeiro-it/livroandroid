package hellomaterial.paulo.ribeiro.it.fragments;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import hellomaterial.paulo.ribeiro.it.R;

import static hellomaterial.paulo.ribeiro.it.R.color.refresh_progress_1;

public class SiteLivroFragment extends livroandroid.lib.fragment.BaseFragment {

    private static final String URL_SOBRE = "http://www.livroandroid.com.br/sobre.htm";
    private WebView webView;
    private ProgressBar progress;
    protected SwipeRefreshLayout swipeLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_site_livro, container, false);
        webView = (WebView) view.findViewById(R.id.webview);
        progress = (ProgressBar) view.findViewById(R.id.progress);
        setWebViewClient(webView);
        webView.loadUrl(URL_SOBRE);
        swipeLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeToRefresh);
        swipeLayout.setOnRefreshListener(OnRefreshListener());
        swipeLayout.setColorSchemeColors(refresh_progress_1,R.color.refresh_progress_2,R.color.refresh_progress_3);
        configJavaScript();
        return view;
    }

    private SwipeRefreshLayout.OnRefreshListener OnRefreshListener(){
        return new SwipeRefreshLayout.OnRefreshListener(){
            @Override
            public void onRefresh() {
                webView.reload();
            }
        };
    }

    private void setWebViewClient(WebView webview){
        webview.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                progress.setVisibility(View.VISIBLE);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                progress.setVisibility(View.INVISIBLE);
                swipeLayout.setRefreshing(false);
            }

            //Este método não é mais invocado, está depreciado.
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                Log.d("livroandroid,","webview url: "+url);
                if(url != null && url.endsWith("sobre.html")){
                    AboutDialog.showAbout(getFragmentManager());
                    return true;
                }
                return super.shouldOverrideUrlLoading(view,url);
            }
        });
    }

    private void configJavaScript(){
        Log.d(TAG, "configJavaScript()");
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        webView.addJavascriptInterface(new LivroAndroidInterface(),"LivroAndroid");
    }

    class LivroAndroidInterface{
        @JavascriptInterface
        public void sobre(){
            toast("Clicou na figura do livro!");
        }
    }

}
