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
        String document = "<!DOCTYPE html><html><head><style>table.timetable {background-color:#af99e0 !important;}table.timetable th.day {width:30px;}table.timetable div.lesson3:not(.lessonFirst), table.timetable div.lesson2:not(.lessonFirst){border-top: solid 2px #af99e0 !important;}</style>" +
                "<link href=\"https://spsejecna.cz/layout/screen-1.15.css\" media=\"screen,projection\" rel=\"stylesheet\" type=\"text/css\" extras=\"\" />" +
                "</head><body>"+timetable+"</body></html>";

        WebView webView = view.findViewById(R.id.schedule_webview);
        webView.loadData(document, "text/html", "UTF-8");
        webView.getSettings().setUseWideViewPort(true);

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
