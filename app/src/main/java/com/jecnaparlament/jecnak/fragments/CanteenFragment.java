package com.jecnaparlament.jecnak.fragments;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.ValueCallback;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.jecnaparlament.jecnak.R;
import com.jecnaparlament.jecnak.databinding.FragmentCanteenBinding;

public class CanteenFragment extends Fragment {
    private FragmentCanteenBinding binding;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = FragmentCanteenBinding.inflate(inflater, container, false).getRoot();
        setHasOptionsMenu(true);

        SharedPreferences sh = getContext().getSharedPreferences("login", MODE_PRIVATE);
        String user = sh.getString("user", "");
        String password = sh.getString("pass", "");

        final String js = "javascript:document.getElementById('j_username').value='"+user+"';" +
                "document.getElementById('j_password').value='"+password+"';" +
                " Array.from(document.getElementsByClassName('btn btn-primary btn-login'))[0].click()";

        WebView webView = view.findViewById(R.id.canteen_webview);
        webView.loadUrl("https://objednavky.jidelnasokolska.cz/?type=mobile");
        webView.canGoBack();
        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                return false;
            }
            @Override
            public void onPageFinished(WebView view, String url) {
                view.evaluateJavascript(js, new ValueCallback<String>() {
                    @Override
                    public void onReceiveValue(String s) {

                    }
                });
            }
        });

//        webView.loadUrl("javascript:(function() { document.getElementById('#j_username').value = '" + user + "'; ;})()");
//        webView.loadUrl("javascript:(function() { document.getElementById('#j_password').value = '" + password + "'; ;})()");
//        webView.loadUrl("javascript:(function() { var z = document.getElementById('.btn-login').click(); })()");
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.default_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

}
