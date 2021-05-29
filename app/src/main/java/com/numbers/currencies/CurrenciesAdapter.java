package com.numbers.currencies;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.numbers.currencies.Database.CurrencyEntity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class CurrenciesAdapter extends RecyclerView.Adapter<CurrenciesAdapter.CurrencyHolder> {

    private MainController controller;

    private Context context;

    private ArrayList<CurrencyEntity> entities;

    private String dateString = "Last update:\n";

    public CurrenciesAdapter(Context context, MainController controller){
        this.controller = controller;
        this.context = context;
        entities = controller.getAll();
    }
    @NonNull
    @Override
    public CurrencyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CurrencyHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.currency_card_layout , parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CurrencyHolder holder, int position) {
        CurrencyEntity entity = entities.get(position);
        String label = entity.base_code + "/" + entity.target_code;
        holder.currency_pair_title.setText(label);

        holder.currency_pair_rate.setText(Double.toString(entity.rate));

        holder.currency_pair_last_update.setText(dateString + getDate(entity.last_update));

        holder.currency_pair_img.setImageResource(position % 2 == 0 ? R.drawable.dollar_symbol : R.drawable.euro_symbol);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(context, R.style.MyDialogTheme)
                        .setTitle(label + " pair?")
                        .setPositiveButton("Update", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                double rate = controller.getCurrencyRate(entity.base_code, entity.target_code);
                                long last_update = Calendar.getInstance().getTimeInMillis();
                                controller.updateCurrency(entity.base_code, entity.target_code, rate, last_update);
                                CurrencyEntity entity1 = new CurrencyEntity();
                                entity1.base_code = entity.base_code;
                                entity1.target_code = entity.target_code;
                                entity1.rate = rate;
                                entity1.last_update = last_update;
                                entities.set(position, entity1);
                                notifyItemChanged(position);
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).setNeutralButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        controller.deleteCurrency(entity.base_code, entity.target_code);
                        update();
                    }
                }).show();
            }
        });
    }


    @Override
    public int getItemCount() {
        return entities.size();
    }

    public class CurrencyHolder extends RecyclerView.ViewHolder {
        TextView currency_pair_title;

        TextView currency_pair_rate;

        TextView currency_pair_last_update;

        ImageView currency_pair_img;

        public CurrencyHolder(@NonNull View itemView) {
            super(itemView);
            currency_pair_title = itemView.findViewById(R.id.currency_pair_title);
            currency_pair_rate = itemView.findViewById(R.id.currency_pair_rate);
            currency_pair_last_update = itemView.findViewById(R.id.currency_pair_last_update);
            currency_pair_img = itemView.findViewById(R.id.currency_pair_image);
        }
    }

    public static String getDate(long milliSeconds)
    {
        String dateFormat = "dd/MM/yyyy HH:mm";
        SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliSeconds);
        return formatter.format(calendar.getTime());
    }

    public void update(){
        entities = controller.getAll();
        notifyDataSetChanged();
    }
}
