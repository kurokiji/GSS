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
import androidx.constraintlayout.widget.ConstraintLayout;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class LogAdapter extends ArrayAdapter {

    Context context;
    int itemLayout;
    ArrayList<LogEntry> logs;
    SimpleDateFormat dateFormatter = new SimpleDateFormat("MMMM dd");
    SimpleDateFormat timeFormatter = new SimpleDateFormat("HH:mm");

    public LogAdapter(@NonNull Context context, int itemLayout, @NonNull ArrayList<LogEntry> logs) {
        super(context, itemLayout, logs);
        this.context = context;
        this.itemLayout = itemLayout;
        this.logs = logs;
    }

    @Override
    public int getItemViewType(int position) {
        //if (logs.get(position).)
        if(logs.get(position).getIsNewDate()){
            return 0;
        } else {
            return 1;
        }

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if(convertView == null){
            // crear en el objeto un bool para marcar fechas nuevas. Antes de inflar la vista, procesas la lista con un meotodo que compruebe cada entrada y marque las fechas nuevas. depsues, dependiendo de la marca, cargas un layout u otro
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(itemLayout, parent, false);
        }

        TextView hourText = convertView.findViewById(R.id.hourTextView);
        Long primitiveDate =logs.get(position).getDate();
        String timeText = timeFormatter.format(new Date(primitiveDate));
        hourText.setText(timeText);

        TextView dateText = convertView.findViewById(R.id.dateTextView);
        ConstraintLayout dateSection = convertView.findViewById(R.id.dateSection);
        if(getItemViewType(position) == 0){
            dateText.setText(dateFormatter.format((new Date(primitiveDate))));
        } else{
            dateSection.setVisibility(View.GONE);
        }

        TextView eventText = convertView.findViewById(R.id.eventTextView);
        ImageView eventIcon = convertView.findViewById(R.id.eventIcon);

        switch (logs.get(position).getEvent()){
            case("on"):
//                eventText.setText("Sistema encendido");
                eventText.setText(dateFormatter.format((new Date(primitiveDate))));
                eventIcon.setImageResource(R.drawable.armed_list);
                break;
            case("off"):
//                eventText.setText("Sistema apagado");
                eventText.setText(dateFormatter.format((new Date(primitiveDate))));
                eventIcon.setImageResource(R.drawable.disarmed_list);
                break;
            case("alert"):
//                eventText.setText("Intrusión detectada");
                eventText.setText(dateFormatter.format((new Date(primitiveDate))));
                // TODO sumar a intrusiones para que aparezca el contador en status
                eventIcon.setImageResource(R.drawable.intrusion_list);
                eventText.setTextColor(context.getResources().getColor(R.color.my_red));
                break;
        }

        return convertView;
    }
}
