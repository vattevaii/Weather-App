package com.exweatheria.weatherapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.exweatheria.weatherapp.ui.home.HomeFragment;

import java.util.ArrayList;

public class TemperatureAdapter extends RecyclerView.Adapter<TemperatureAdapter.ViewHolder> {
    private Context context;
    private ArrayList<TemperatureModel> tempList;
    public TemperatureAdapter(Context context, ArrayList<TemperatureModel> temps){
        this.context  = context;
        this.tempList = temps;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        // layout file for our place item
        ViewHolder viewHolder = new ViewHolder(LayoutInflater.from(context).inflate(R.layout.weather_rv_item, parent,false));
        return viewHolder;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView dateText, temperatureText,statusText;
        private ImageView stateForIt;

        public ViewHolder(@NonNull View itemView){
            super(itemView);
            dateText = itemView.findViewById(R.id.dateOrTimeTV);
            temperatureText = itemView.findViewById(R.id.temperatureOnThatTV);
            stateForIt = itemView.findViewById(R.id.stateOnthatIV);
            statusText = itemView.findViewById(R.id.stateOnThatTV);
        }
    }

    public void onBindViewHolder(@NonNull ViewHolder holder, int position){
        TemperatureModel modal = tempList.get(position);
        holder.dateText.setText(modal.getDateTime());
        holder.temperatureText.setText(modal.getTemperature());
        holder.statusText.setText(modal.getStatusText());
    }

    @Override
    public int getItemCount() {
        return tempList.size();
    }
}
