package com.kurokiji.gss;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class LogAdapter extends ArrayAdapter {

    Context context;
    int itemLayout;
    ArrayList<LogEntry> logs;
    SimpleDateFormat dateFormatter = new SimpleDateFormat("dd-MM-yyyy");
    SimpleDateFormat timeFormatter = new SimpleDateFormat("HH:mm");

    public LogAdapter(@NonNull Context context, int itemLayout, @NonNull ArrayList<LogEntry> logs) {
        super(context, itemLayout, logs);
        this.context = context;
        this.itemLayout = itemLayout;
        this.logs = logs;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if(convertView == null){
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(itemLayout, parent, false);
        }

        TextView hourText = convertView.findViewById(R.id.hourTextView);
        Long primitiveDate =logs.get(position).getDate();
        String timeText = timeFormatter.format(new Date(primitiveDate));
        hourText.setText(timeText);

        TextView eventText = convertView.findViewById(R.id.eventTextView);
        ImageView eventIcon = convertView.findViewById(R.id.eventIcon);

        switch (logs.get(position).getEvent()){
            case("on"):
                eventText.setText("Sistema encendido");
                eventIcon.setImageResource(R.drawable.armed_list);
                break;
            case("off"):
                eventText.setText("Sistema apagado");
                eventIcon.setImageResource(R.drawable.disarmed_list);
                break;
            case("alert"):
                eventText.setText("Intrusión detectada");
                // TODO sumar a intrusiones para que aparezca el contador en status
                eventIcon.setImageResource(R.drawable.intrusion_list);
                eventText.setTextColor(context.getResources().getColor(R.color.my_red));
                break;
        }

        return convertView;
    }
}
