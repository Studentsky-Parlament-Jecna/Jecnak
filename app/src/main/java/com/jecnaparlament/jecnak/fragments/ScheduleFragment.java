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

import java.io.IOException;

import javax.security.auth.login.LoginException;

public class ScheduleFragment extends Fragment {
    private FragmentScheduleBinding binding;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = FragmentScheduleBinding.inflate(inflater, container, false).getRoot();
        setHasOptionsMenu(true);

        String timetable = MainActivity.controllers.getScheduleController().getTimetable();
        String document = "<html><head><link href=\"https://raw.githubusercontent.com/Studentsky-Parlament-Jecna/Jecnak/main/app/src/main/assets/schedule.css\" rel=\"stylesheet\"></head>" +
                "<body>"+timetable+"<script>document.getElementsByClassName(\"day\")[4].innerHTML = 'Pá';</script></body></html>";

        WebView webView = view.findViewById(R.id.schedule_webview);
        webView.loadData(document, "text/html", "UTF-8");
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
