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
import com.jecnaparlament.jecnak.activities.MainActivity;
import com.jecnaparlament.jecnak.databinding.FragmentScheduleBinding;
import com.jecnaparlament.jecnak.models.Connect;

import org.jsoup.nodes.Document;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.stream.Stream;

import javax.security.auth.login.LoginException;

public class ScheduleFragment extends Fragment {

    private FragmentScheduleBinding binding;
    private String css;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            InputStream stream = getActivity().getAssets().open("schedule.css");
            int size = stream.available();
            byte[] buffer = new byte[size];
            stream.read(buffer);
            stream.close();
            css = new String(buffer);
        } catch (IOException e) {
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = FragmentScheduleBinding.inflate(inflater, container, false).getRoot();
        setHasOptionsMenu(true);

        String timetable = MainActivity.controllers.getScheduleController().getTimetable();
        System.out.println(timetable);
        String document = "<html><head><style>"+css+"</style></head>" +
                "<body>"+timetable+"<script>document.getElementsByClassName(\"day\")[4].innerHTML = 'Pá';</script></body></html>";

        WebView webView = view.findViewById(R.id.schedule_webview);
        //webView.loadData(document, "text/html", "UTF-8");
        webView.loadDataWithBaseURL("", document, "text/html", "UTF-8", "");
        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setJavaScriptEnabled(true);

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
