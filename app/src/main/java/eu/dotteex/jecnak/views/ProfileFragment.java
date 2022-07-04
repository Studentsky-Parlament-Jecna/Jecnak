package eu.dotteex.jecnak.views;

import static android.content.Context.MODE_PRIVATE;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import eu.dotteex.jecnak.LoginActivity;
import eu.dotteex.jecnak.R;
import eu.dotteex.jecnak.controllers.ProfileController;
import eu.dotteex.jecnak.databinding.FragmentProfileBinding;
import eu.dotteex.jecnak.models.Connect;


public class ProfileFragment extends Fragment {

    private FragmentProfileBinding binding;
    ArrayList<String> data = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ImageLoader.getInstance().init(ImageLoaderConfiguration.createDefault(getActivity()));


        try {
            data = new GetProfile().execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


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
        //((NetworkImageView) view.findViewById(R.id.profile_pic)).setImageUrl(data.get(10), MySingletone.getInstance(this).getImageLoader());
        ImageLoader.getInstance().displayImage("https://www.spsejecna.cz/"+data.get(10), imageView);

        nameTextView.setText(data.get(0));
        birthTextView.setText(data.get(3));
        adressTextView.setText(data.get(5));
        classTextView.setText(data.get(6));

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // TODO Add your menu entries here
        inflater.inflate(R.menu.profile_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.menu_logout) {
            SharedPreferences sharedPreferences = getActivity().getSharedPreferences("login", MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("user", null);
            editor.putString("pass", null);
            editor.commit();

            startActivity(new Intent(getActivity(), LoginActivity.class));
            return true;
        }
        return false;
    }

    private class GetProfile extends AsyncTask<Void, Void, ArrayList<String>>{

        @Override
        protected ArrayList<String> doInBackground(Void... voids) {
            SharedPreferences sh = getActivity().getSharedPreferences("login", MODE_PRIVATE);
            String user = sh.getString("user", "");
            String password = sh.getString("pass", "");
            Connect connect = null;
            try {
                connect = new Connect(user, password);
            } catch (Exception e) {
            }
            ProfileController pc = new ProfileController(connect);

            return pc.getData();
        }
    }

}