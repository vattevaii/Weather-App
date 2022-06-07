package com.exweatheria.weatherapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.exweatheria.weatherapp.ui.home.HomeFragment;

import java.util.ArrayList;

public class PlaceRVAdapter extends RecyclerView.Adapter<PlaceRVAdapter.ViewHolder> {
    private Context context;
    private ArrayList<PlaceModel> placeList;
    public PlaceRVAdapter(Context context, ArrayList<PlaceModel> places){
        this.context  = context;
        this.placeList = places;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        // layout file for our place item
        ViewHolder viewHolder = new ViewHolder(LayoutInflater.from(context).inflate(R.layout.places_rv_item, parent,false));
        return viewHolder;
    }
    public void filterList(ArrayList<PlaceModel> filteredList){
        placeList = filteredList;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView placeTV, stateTV;

        public ViewHolder(@NonNull View itemView){
            super(itemView);
            placeTV = itemView.findViewById(R.id.placeTV);
            stateTV = itemView.findViewById(R.id.stateTV);
        }
    }

    public void onBindViewHolder(@NonNull ViewHolder holder, int position){
        PlaceModel modal = placeList.get(position);
        holder.stateTV.setText(modal.getState());
        holder.placeTV.setText(modal.getPlace());
        //set onClick listener
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavController navController = Navigation.findNavController(holder.itemView);
                Bundle bundle = new Bundle();
                bundle.putString("cityName", modal.getPlace());
                bundle.putString("Longitude", modal.getLongitude());
                bundle.putString("Latitude", modal.getLatitude());
                bundle.putString("State", modal.getLState());
                navController.navigate(R.id.dash_to_home,bundle);
                //                FragmentManager fm = ((MainActivity)context).getSupportFragmentManager();
//                fm.beginTransaction()
//                        .replace(R.id.nav_host_fragment_activity_main, HomeFragment.class,null)
//                        .setReorderingAllowed(true).addToBackStack(null).commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        return placeList.size();
    }
}
