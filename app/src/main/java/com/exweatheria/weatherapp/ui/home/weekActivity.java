package com.exweatheria.weatherapp.ui.home;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.exweatheria.weatherapp.Forecast;
import com.exweatheria.weatherapp.R;
import com.exweatheria.weatherapp.TemperatureAdapter;
import com.exweatheria.weatherapp.TemperatureModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class weekActivity extends AppCompatActivity {
    private TemperatureAdapter adapter;
    private RecyclerView rView;

    String longitude,latitude;
    Boolean isFah;
    private ArrayList<TemperatureModel> tempList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_week);

        getSupportActionBar().setTitle("Weather for following 7 days");
        Bundle extras;
        extras = getIntent().getExtras();
        longitude = extras.getString("longitude");
        latitude = extras.getString("latitude");
        isFah = extras.getBoolean("units");
        tempList = new ArrayList<>();
        rView = findViewById(R.id.weeksRV);
        adapter = new TemperatureAdapter(this,tempList);
        rView.setLayoutManager(new LinearLayoutManager(this));
        rView.setAdapter(adapter);

        sendRequestTommorrowIO(longitude,latitude);
    }
    private void sendRequestTommorrowIO(String longitude,String latitude){
        System.out.println("Here we Requested");
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        Forecast forecast = new Forecast();
        forecast.workinDay(latitude,longitude,"1d",isFah);
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
                            JSONArray allIntervals = data.getJSONObject("data").getJSONArray("timelines").getJSONObject(0).getJSONArray("intervals");
                            System.out.println(allIntervals.toString());
                            String degree;
                            for (int i = 0, size = allIntervals.length(); i < size; i++) {
                                JSONObject objectInArray = allIntervals.getJSONObject(i);
                                String date = objectInArray.getString("startTime");
                                JSONObject values = objectInArray.getJSONObject("values");
                                if(isFah) degree = " \u2109";
                                else degree = " \u2103";
                                String temperature = values.getString("temperature") + degree;
                                String weatherCode = values.getString("weatherCode");
                                tempList.add(new TemperatureModel(date,temperature,weatherCode,"1d"));
                            }
                            adapter.notifyDataSetChanged();
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
}