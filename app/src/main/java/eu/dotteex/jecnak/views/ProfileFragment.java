package eu.dotteex.jecnak.views;

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
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.ArrayList;

import eu.dotteex.jecnak.LoginActivity;
import eu.dotteex.jecnak.MainActivity;
import eu.dotteex.jecnak.R;
import eu.dotteex.jecnak.databinding.FragmentProfileBinding;


public class ProfileFragment extends Fragment {

    private FragmentProfileBinding binding;
    ArrayList<String> data = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ImageLoader.getInstance().init(ImageLoaderConfiguration.createDefault(getActivity()));


        data = ((MainActivity) getActivity()).getControlers().getProfileController().getData();


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

}