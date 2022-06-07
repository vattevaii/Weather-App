package com.exweatheria.weatherapp.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.exweatheria.weatherapp.Forecast;
import com.exweatheria.weatherapp.MainActivity;
import com.exweatheria.weatherapp.R;
import com.exweatheria.weatherapp.TemperatureModel;
import com.exweatheria.weatherapp.databinding.FragmentHomeBinding;

import org.json.JSONException;
import org.json.JSONObject;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private FragmentHomeBinding binding;
    private Boolean isFahren;
    private String longitude,latitude;
    private Button todayBTN, weekBTN;
    TextView cityTV, stateTV, latTV, lonTV, currentTempTV, currentFeelTV,currentStatusTV;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        MainActivity activity = (MainActivity) getActivity();

        todayBTN = root.findViewById(R.id.gotoTodayActivity);
        weekBTN = root.findViewById(R.id.gotoWeekActivity);
        cityTV = root.findViewById(R.id.cityTV);
        stateTV = root.findViewById(R.id.stateTV);
        latTV = root.findViewById(R.id.latTV);
        lonTV = root.findViewById(R.id.lonTV);
        currentFeelTV = root.findViewById(R.id.currentFeelTV);
        currentTempTV = root.findViewById(R.id.currentTempTV);
        currentStatusTV = root.findViewById(R.id.weatherStatusTV);
        isFahren = false;
        Bundle bundle=this.getArguments();
        System.out.println(bundle!=null?"exists":"does not exist");
        if (bundle!=null) {
            cityTV.setText(bundle.getString("cityName", "No mans land"));
            longitude = bundle.getString("Longitude");
            lonTV.setText(longitude);
            latitude = bundle.getString("Latitude");
            latTV.setText(latitude);
            stateTV.setText(bundle.getString("State"));
        }else{
            cityTV.setText("Current Location");
            longitude=activity.getCurrentLongitude();
            lonTV.setText(longitude.toString());
            latitude=activity.getCurrentLatitude();
            latTV.setText(latitude.toString());
            stateTV.setText("Not available");
        }
        todayBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), TodayActivity.class);
                intent.putExtra("longitude",longitude);
                intent.putExtra("latitude",latitude);
                intent.putExtra("units", isFahren);
                view.getContext().startActivity(intent);
            }
        });
        weekBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), weekActivity.class);
                intent.putExtra("longitude",longitude);
                intent.putExtra("latitude",latitude);
                intent.putExtra("units", isFahren);
                view.getContext().startActivity(intent);
            }
        });
        sendRequestTommorrowIO(lonTV.getText().toString(),latTV.getText().toString());
        return root;
    }
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.temperature_menu, menu);
        MenuItem tempSwitch = menu.findItem(R.id.app_bar_switch);
        tempSwitch.setChecked(isFahren);
    }
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.app_bar_switch) {
            isFahren = !item.isChecked();
            item.setChecked(isFahren);
            sendRequestTommorrowIO(longitude,latitude);
        }
        return false;
    }

        private void sendRequestTommorrowIO(String longitude,String latitude){
        System.out.println("Here we Requested");
        RequestQueue requestQueue = Volley.newRequestQueue(this.getContext());
        Forecast forecast = new Forecast();
        forecast.workinDay(latitude,longitude,"current",isFahren);
        StringRequest stringRequest = new StringRequest(
                Request.Method.GET,
                forecast.requestURL(),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        System.out.println(response.toString());
                        JSONObject data= null;
                        try {
                            data = new JSONObject(response);
                            JSONObject currentData = data.getJSONObject("data").getJSONArray("timelines").getJSONObject(0);
                            JSONObject datas = currentData.getJSONArray("intervals").getJSONObject(0).getJSONObject("values");
                            System.out.println(datas.toString());
                            String degree;
                            if(isFahren) degree = " \u2109";
                            else degree = " \u2103";
                            currentTempTV.setText(datas.getString("temperature")+degree);
                            currentFeelTV.setText("Feels like "+datas.getString("temperatureApparent")+ degree);
                            currentStatusTV.setText(TemperatureModel.statusText(datas.getString("weatherCode")));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println(error.toString());
                    }
                }
        );
        requestQueue.add(stringRequest);
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public void gotoTodayActivity(View view){
        System.out.println("Today activity");
    }
    public void gotoWeekActivity(View view){
        System.out.println("Weeks Activty");
    }
}