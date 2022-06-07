package com.exweatheria.weatherapp.ui.dashboard;

import static com.exweatheria.weatherapp.PlaceModel.PlaceComparator;

import android.app.SearchManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.MenuItemCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.exweatheria.weatherapp.MainActivity;
import com.exweatheria.weatherapp.PlaceModel;
import com.exweatheria.weatherapp.PlaceRVAdapter;
import com.exweatheria.weatherapp.R;
import com.exweatheria.weatherapp.databinding.FragmentDashboardBinding;
import com.exweatheria.weatherapp.ui.home.HomeViewModel;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class DashboardFragment extends Fragment {
    private RecyclerView placesRV;
    private PlaceRVAdapter placeRVAdapter;
    private FragmentDashboardBinding binding;
    private DashboardViewModel dashboardViewModel;

    private ArrayList<PlaceModel> placesList;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);

        dashboardViewModel =
                new ViewModelProvider(this).get(DashboardViewModel.class);
        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        MainActivity activity = (MainActivity) getActivity();
        placesList = (ArrayList<PlaceModel>) activity.getAllPlaces();

        placesRV = root.findViewById(R.id.placesRV);
        placeRVAdapter = new PlaceRVAdapter(root.getContext(),placesList);
        placesRV.setLayoutManager(new LinearLayoutManager(root.getContext()));
        placesRV.setAdapter(placeRVAdapter);
        return root;
    }

    private void filter(String text){
        ArrayList<PlaceModel> filteredPlaces = new ArrayList<>();
        for (PlaceModel item: placesList){
            if (item.getPlace().toLowerCase().startsWith(text))
                filteredPlaces.add(item);
            else if(item.getState().toLowerCase().contains(text))
                filteredPlaces.add(item);
            else if(item.getLState().toLowerCase().contains(text))
                filteredPlaces.add(item);
        }
        System.out.println(filteredPlaces.size());
        placeRVAdapter.filterList(filteredPlaces);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.sort_places,menu);
        MenuItem searchVI = menu.findItem(R.id.searchPlaces);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchVI);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchView.clearFocus();
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                System.out.println("This is SEARCH");
                filter(newText.toLowerCase());
                return false;
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id==R.id.placeSort){
            Collections.sort(placesList, PlaceComparator);
            placeRVAdapter.notifyDataSetChanged();
            return true;
        }
        else if(id == R.id.stateSort){
            Collections.sort(placesList);
            placeRVAdapter.notifyDataSetChanged();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}