package com.jecnaparlament.jecnak.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import com.jecnaparlament.jecnak.activities.MainActivity;
import com.jecnaparlament.jecnak.R;
import com.jecnaparlament.jecnak.adapters.CardAdapter;
import com.jecnaparlament.jecnak.controllers.CardController;
import com.jecnaparlament.jecnak.databinding.FragmentHomeBinding;
import com.jecnaparlament.jecnak.models.Card;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private RecyclerView recyclerView;
    ArrayList<Card> cardList = null;
    private Card[] cards;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //ArrayList<Card> cardList = null;

        // TODO: 16.07.2022 - ziskej karty z mainActivity

        /*
        try {
            cardList = GetCards().execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
*/

        cardList = new CardController(
                ((MainActivity) getActivity()).getControlers().getNewsController(),
                ((MainActivity) getActivity()).getControlers().getGradeController(),
                ((MainActivity) getActivity()).getControlers().getRecordController()
        ).getCards();
        cards = new Card[cardList.size()];
        int i = 0;
        for(Card card : cardList) {
            cards[i] = card;
            i++;
        }
    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        View view = FragmentHomeBinding.inflate(inflater, container, false).getRoot();

        recyclerView = view.findViewById(R.id.recyclerViewHistory);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        recyclerView.setAdapter(new CardAdapter(view.getContext(), cards));

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