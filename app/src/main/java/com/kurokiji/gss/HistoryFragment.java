package com.kurokiji.gss;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HistoryFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HistoryFragment extends Fragment {

    // TODO no se muestra el titulo cuando la lista es demasiado larga
    Retrofit retrofit;
    SuperApi api;

    ArrayList<LogEntry> logEntries = new ArrayList<LogEntry>();
    ListView logListView;
    LogAdapter adapter;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HistoryFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HistortFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HistoryFragment newInstance(String param1, String param2) {
        HistoryFragment fragment = new HistoryFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        retrofit = new Retrofit.Builder() // constructor por fases
                .baseUrl(SuperApi.SERVER_URL)
                .addConverterFactory(GsonConverterFactory.create()) // creando el conversor de JSON
                .build();
        api = retrofit.create(SuperApi.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_history, container, false);
        // Inflate the layout for this fragment
        getLogEntries();
        logListView = view.findViewById(R.id.historyListView);
       adapter = new LogAdapter(view.getContext(), R.layout.log_item, logEntries);
       logListView.setAdapter(adapter);
        return view;
    }

    public void getLogEntries(){
        api.getNewLogEntry().enqueue(new Callback<List<LogEntry>>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(Call<List<LogEntry>> call, Response<List<LogEntry>> response) {
                Log.d("RESPUESTA", "ha ido bien: " + call.toString());
                logEntries.removeAll(logEntries);
                for (LogEntry log : response.body()){
                    logEntries.add(log);
                }
                dateTitlesConfig();
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<LogEntry>> call, Throwable t) {
                Log.d("RESPUESTA", "ha ido mal: " + t.toString());
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void sortDates(){
        logEntries.sort(new StateSorter());
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void dateTitlesConfig(){
        sortDates();
        SimpleDateFormat dateFormatter = new SimpleDateFormat("dd");
        String previousDate = null;
        for (LogEntry log: logEntries){
            Long primitiveDate = log.getDate();
            String currentDay = dateFormatter.format(new Date(primitiveDate));
            if (previousDate != null){
                if(previousDate.equals(currentDay)){
                    log.setNewDate(false);
                } else {
                    log.setNewDate(true);
                }
            } else {
                log.setNewDate(true);
            }
            previousDate = currentDay;
        }
    }

}