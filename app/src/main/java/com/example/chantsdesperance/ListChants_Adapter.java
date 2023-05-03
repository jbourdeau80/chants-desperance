package com.example.chantsdesperance;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.parceler.Parcels;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ListChants_Adapter extends RecyclerView.Adapter<ListChants_Adapter.ViewHolder> {

    private List<Chants> chants;
    Context context;

    public ListChants_Adapter(Context context, List<Chants> chants) {

        this.chants = chants;
        this.context = context;
    }

    @Override
    public ListChants_Adapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_chant, parent, false);
        return new ListChants_Adapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Chants chant = chants.get(position);
        holder.bind(chant);
    }


    @Override
    public int getItemCount() {
        return chants.size();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void sortAlphabetically() {
        Collections.sort(chants, new Comparator<Chants>() {
            @Override
            public int compare(Chants c1, Chants c2) {
                return c1.gettitreChant().compareTo(c2.gettitreChant());
            }
        });
        notifyDataSetChanged();
    }
    // Method to update the data source
    @SuppressLint("NotifyDataSetChanged")
    public void updateChantList(List<Chants> chants) {
        this.chants = chants;
        Log.d("FILTERED_RESULTS", chants.toString());
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivImageChant;
        TextView tvNumeroChant;
        TextView tvTitreChant;
        TextView tvSectionChant;
        RelativeLayout ChantContainer;


        public ViewHolder(View itemView) {
            super(itemView);
            ivImageChant = itemView.findViewById(R.id.ivImageChant);
            tvNumeroChant = itemView.findViewById(R.id.tvNumeroChant);
            tvTitreChant = itemView.findViewById(R.id.tvTitreChant);
            tvSectionChant = itemView.findViewById(R.id.tvSectionChant);
            ChantContainer = itemView.findViewById(R.id.ChantContainer);
        }

        public void bind(Chants chant) {

            String text = (chant.getnumeroChant()) + " - " ;
            tvNumeroChant.setText(text);


            tvTitreChant.setText(chant.gettitreChant());

            tvSectionChant.setText(chant.getnomSection());

            ChantContainer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, ChantActivity.class);
                    intent.putExtra("chants", Parcels.wrap(chant));
                    context.startActivity(intent);

                }
            });
        }

    }


}

