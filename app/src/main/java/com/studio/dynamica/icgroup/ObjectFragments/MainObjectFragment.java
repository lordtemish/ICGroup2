package com.studio.dynamica.icgroup.ObjectFragments;


import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.error.AuthFailureError;
import com.android.volley.error.VolleyError;
import com.android.volley.request.JsonArrayRequest;
import com.studio.dynamica.icgroup.Activities.MainActivity;
import com.studio.dynamica.icgroup.Adapters.CityRadioAdapter;
import com.studio.dynamica.icgroup.Adapters.MainObjectAdapter;
import com.studio.dynamica.icgroup.Adapters.TasktypeAdapter;
import com.studio.dynamica.icgroup.ExtraFragments.MainObjectSetInfoFragment;
import com.studio.dynamica.icgroup.Forms.MainObjectRowForm;
import com.studio.dynamica.icgroup.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class MainObjectFragment extends Fragment {
    ProgressBar progressBar;
    MainObjectAdapter mainObjectAdapter;CityRadioAdapter cityRadioAdapter;
    RecyclerView mainObjectRecycle, cityRecycler , tasktypeRecycler;
    ConstraintLayout RegionLayout;
    ImageView arrowCity;
    TextView RegionTextView;
    ArrayList<MainObjectRowForm> list;
    TextView mainObjectTitle;
    SwipeRefreshLayout refreshLayout;
    View.OnClickListener goneClick;
    HashMap<String,String> kindMap;
    HashMap<Integer,String> cities;
    List<String> cityNames, tasktypeNames;
    TasktypeAdapter taskTypeAdapter;
    boolean client;
    long time;
        boolean changed=false;
    int city=1;
    String kind="";
    public MainObjectFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_main, container, false);
        createViews(view);
        mainObjectTitle.setTypeface(Typeface.createFromAsset(getActivity().getAssets(),"fonts/AvenirNextLTPro-It.ttf"));


         list=new ArrayList<>();
        mainObjectAdapter=new MainObjectAdapter(list,getActivity());
        mainObjectAdapter.setClient(client);
        RecyclerView.LayoutManager mLayoutManager=new LinearLayoutManager(getActivity());
        mainObjectRecycle.setLayoutManager(mLayoutManager);
        mainObjectRecycle.setItemAnimator(new DefaultItemAnimator());
        mainObjectRecycle.setAdapter(mainObjectAdapter);

        cityRadioAdapter=new CityRadioAdapter(cityNames);
        cityRadioAdapter.setListeners(new ArrayList<View.OnClickListener>());
        cityRecycler.setAdapter(cityRadioAdapter);
        ((MainActivity)getActivity()).setRecyclerViewOrientation(cityRecycler,LinearLayoutManager.VERTICAL);
        ((MainActivity)getActivity()).setRecyclerViewOrientation(tasktypeRecycler,LinearLayoutManager.HORIZONTAL);
        taskTypeAdapter=new TasktypeAdapter(tasktypeNames);
        tasktypeRecycler.setAdapter(taskTypeAdapter);


        RegionTextView.setTypeface(Typeface.createFromAsset(getActivity().getAssets(),"fonts/AvenirNextLTPro-Medium.ttf"));


        goneClick=new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        };


        RegionLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showCities();
            }
        });
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                onSwipeRefresh();
            }
        });
        showCities();
        setKinds();
        return view;
    }
    private void showCities(){
        if(cityRecycler.getVisibility()==View.VISIBLE){
            cityRecycler.setVisibility(View.GONE);
            arrowCity.setImageResource(R.drawable.ic_arrowdown_green);
        }
        else{
            cityRecycler.setVisibility(View.VISIBLE);
            arrowCity.setImageResource(R.drawable.ic_arrowup_green);
        }
    }
    private void checkData(){
        if(new Date().getTime()-time>120000 || changed){
            list.clear();
            mainObjectAdapter.notifyDataSetChanged();
            String l="points/?";
            if(kind.length()>0){
                l+="kind="+kind;
                if(city>-1){
                    l+="&";
                }
            }
            if(city>-1){
                l+="location="+city;
            }
            if(cityNames.size()<1){
                getRequest("locations/");
            }
            Log.d("url",l);
            getRequest(l);

        }
        else{
            List<MainObjectRowForm> rowForms=new ArrayList<MainObjectRowForm>();
            rowForms.addAll(list);
            setPoints(rowForms);
        }
    }

    private void onSwipeRefresh(){
        refreshLayout.setRefreshing(false);
        progressBar.setVisibility(View.VISIBLE);
        checkData();
    }
    public void setPoints(List<MainObjectRowForm> list){
        this.list.clear();
        this.list.addAll(list);
        /*
        this.list.add(new MainObjectRowForm("7894561651","Temirlan",80,10,9,7));
        */
        mainObjectAdapter.notifyDataSetChanged();

        changed=false;
    }
    public void getRequest(final String url1){
        final String url=((MainActivity)getActivity()).MAIN_URL+url1;

        JsonArrayRequest getRequest= new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response){
                        progressBar.setVisibility(View.GONE);
                        Log.d("Respone", response.length()+" \n"+response.toString());
                        if(url1.contains("points")) {
                            List<MainObjectRowForm> rowForms = new ArrayList<>();
                            for (int i = 0; i < response.length(); i++) {
                                try {
                                    JSONObject jsonObject = response.getJSONObject(i);
                                    long resu=Math.round(jsonObject.getDouble("result_rate")*100);
                                    int result=Integer.parseInt(resu+"");
                                    MainObjectRowForm form=new MainObjectRowForm(jsonObject.getInt("id") + "", jsonObject.getString("name"),result , jsonObject.getInt("workers_count"), jsonObject.getInt("complaints_count"), jsonObject.getInt("tasks_count"));
                                    form.setLocation(jsonObject.getInt("location"));
                                    form.setCity(cities.get(form.getLocation()));
                                    rowForms.add(form);
                                    time=new Date().getTime();

                                } catch (Exception e) {
                                    break;
                                }
                            }
                            setPoints(rowForms);
                        }
                        else{
                            cities.clear();
                            for (int i = 0; i < response.length(); i++) {
                                try {
                                    JSONObject jsonObject = response.getJSONObject(i);

                                    cities.put(jsonObject.getInt("id"),jsonObject.getString("name"));
                                } catch (Exception e) {
                                    break;
                                }
                               setCities();
                            }
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressBar.setVisibility(View.GONE);
                        Log.d("Respone", error.getMessage()+"");
                        setPoints(new ArrayList<MainObjectRowForm>());
                        Toast.makeText(getContext(), "Проблемы соеденения", Toast.LENGTH_SHORT).show();
                    }
                }
        ){  @Override
        public Map<String, String> getHeaders() throws AuthFailureError {
            HashMap<String, String> headers = new HashMap<String, String>();
            headers.put("Accept", "application/json");
            headers.put("Content-Type", "application/json; charset=utf-8");
            headers.put("Authorization", "JWT "+((MainActivity)getActivity()).token);
            return headers;
        }};
        (((MainActivity)getActivity()).requestQueue).add(getRequest);
    }


    private void createViews(View view){
        client=((MainActivity) getActivity()).client;

        progressBar=(ProgressBar) view.findViewById(R.id.progressBar);
        refreshLayout=(SwipeRefreshLayout) view.findViewById(R.id.swipeRefreshLayout);
        mainObjectTitle=(TextView) view.findViewById(R.id.mainObjectTitle);
        mainObjectRecycle=(RecyclerView) view.findViewById(R.id.mainObjectRecycle);
        cityRecycler=(RecyclerView) view.findViewById(R.id.cityRecycler);
        tasktypeRecycler=(RecyclerView) view.findViewById(R.id.tasktypeRecycler);

        RegionLayout=(ConstraintLayout) view.findViewById(R.id.mainObjectRegionLayout);
        RegionTextView=(TextView) view.findViewById(R.id.mainObjectRegionTextView);
        arrowCity=(ImageView) view.findViewById(R.id.arrowCity);


        kindMap=new HashMap<>();
        cities=new HashMap<>();
        kindMap.put("SHOPPING_CENTER","Торговый центр");
        kindMap.put("BUSINESS_CENTER","Бизнес центр");
        kindMap.put("SMALL_OBJECT","Малый объект");
        kindMap.put("INDUSTRIAL_BASE","Промышленная база");

        List<String> cities=new ArrayList<>();
        cityNames=new ArrayList<>();
        tasktypeNames=new ArrayList<>();
        cities.add("Алматы");
        cities.add("Астана");
        cities.add("Караганды");
        cities.add("Кокшетау");
    }
    private void setVal(String s, boolean city){
        if(city){
            RegionTextView.setText(s);
        }
    }
    private void setCities(){
        List<String> values=new ArrayList<>();
        List<Integer> keys=new ArrayList<>();
        List<View.OnClickListener> listeners=new ArrayList<>();
        for(final Integer j:cities.keySet()){
            keys.add(j);
            values.add(cities.get(j));
            listeners.add(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    city=j;
                    changed=true;
                    setVal(cities.get(j),true);
                    onSwipeRefresh();

                }
            });
        }
        cityRadioAdapter.setListeners(listeners);
        cityNames.clear();
        cityNames.addAll(values);
        setCityNames();
        if(cityNames.size()>city)
        setVal(cityNames.get(city-1),true);
    }
    private void setKinds(){
        final List<String> values=new ArrayList<>();
        List<String> keys=new ArrayList<>();
        List<View.OnClickListener> listeners=new ArrayList<>();
        for(final String j:kindMap.keySet()){
            keys.add(j);
            values.add(kindMap.get(j));
            listeners.add(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    kind=j;
                    changed=true;
                    onSwipeRefresh();
                }
            });
        }
        tasktypeNames.clear();
        tasktypeNames.addAll(values);
        if(tasktypeNames.size()>0){
            kind=tasktypeNames.get(0);
        }
        taskTypeAdapter.setListeners(listeners);
        taskTypeAdapter.notifyDataSetChanged();
        onSwipeRefresh();
    }
    private void setCityNames(){
        cityNames.clear();
        for(Integer i:cities.keySet()){
            cityNames.add(cities.get(i));
        }

        cityRadioAdapter.notifyDataSetChanged();
    }
}
