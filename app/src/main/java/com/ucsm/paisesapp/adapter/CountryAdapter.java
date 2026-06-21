package com.ucsm.paisesapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ucsm.paisesapp.R;
import com.ucsm.paisesapp.database.CountryEntity;

import android.content.Intent;
import com.ucsm.paisesapp.ui.CountryDetailActivity;

import java.util.List;

public class CountryAdapter extends RecyclerView.Adapter<CountryAdapter.ViewHolder> {

    private List<CountryEntity> list;

    private Context context;

    public CountryAdapter(Context context, List<CountryEntity> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_country, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        CountryEntity country = list.get(position);

        holder.tvCountry.setText(country.country);
        holder.tvCities.setText(country.cities);

        holder.itemView.setOnClickListener(v -> {

            Intent intent = new Intent(context, CountryDetailActivity.class);

            intent.putExtra("id", country.id);
            intent.putExtra("country", country.country);
            intent.putExtra("cities", country.cities);

            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvCountry, tvCities;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvCountry = itemView.findViewById(R.id.tvCountry);
            tvCities = itemView.findViewById(R.id.tvCities);
        }

    }
    public void updateList(List<CountryEntity> newList) {
        this.list = newList;
        notifyDataSetChanged();
    }
}