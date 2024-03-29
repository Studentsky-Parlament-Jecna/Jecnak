package com.jecnaparlament.jecnak.fragments;

import static android.content.Context.MODE_PRIVATE;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.ArrayList;

import com.jecnaparlament.jecnak.activities.LoginActivity;
import com.jecnaparlament.jecnak.activities.MainActivity;
import com.jecnaparlament.jecnak.R;
import com.jecnaparlament.jecnak.databinding.FragmentProfileBinding;
import com.jecnaparlament.jecnak.models.Record;


public class ProfileFragment extends Fragment {

    private FragmentProfileBinding binding;
    ArrayList<String> data = new ArrayList<>();
    private ArrayList<Record> records = new ArrayList<>();


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ImageLoader.getInstance().init(ImageLoaderConfiguration.createDefault(getActivity()));

        data = MainActivity.controllers.getProfileController().getData();
        records = MainActivity.controllers.getRecordController().getRecords();
    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = FragmentProfileBinding.inflate(inflater, container, false).getRoot();
        setHasOptionsMenu(true);

        //arraylist of data

        TextView nameTextView = view.findViewById(R.id.profile_name);
        TextView birthTextView = view.findViewById(R.id.profile_birth);
        TextView adressTextView = view.findViewById(R.id.profile_adress);
        TextView classTextView = view.findViewById(R.id.profile_class);
        ImageView imageView = view.findViewById(R.id.profile_pic);
        ImageLoader.getInstance().displayImage("https://www.spsejecna.cz/"+data.get(10), imageView);
        ListView listView = view.findViewById(R.id.listViewRecords);

        nameTextView.setText(data.get(0));
        birthTextView.setText(data.get(3));
        adressTextView.setText(data.get(5));
        classTextView.setText(data.get(6));
        ArrayList<String> recordsArray = new ArrayList<>();
        //for each record in arraylist of records, add to array
        for (Record record : records) {
            recordsArray.add(record.getContent());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, recordsArray);
        listView.setAdapter(adapter);

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.default_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
    }

}