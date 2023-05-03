package com.example.chantsdesperance;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.parceler.Parcels;

import java.io.IOException;
import java.io.InputStream;
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

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivImageChant;
        TextView tvNumeroChant;
        TextView tvTitreChant;
        RelativeLayout ChantContainer;


        public ViewHolder(View itemView) {
            super(itemView);
            ivImageChant = itemView.findViewById(R.id.ivImageChant);
            tvNumeroChant = itemView.findViewById(R.id.tvNumeroChant);
            tvTitreChant = itemView.findViewById(R.id.tvTitreChant);
            ChantContainer = itemView.findViewById(R.id.ChantContainer);
        }

        public void bind(Chants chant) {

            try {
                InputStream stream = context.getAssets().open(chant.getnomSection() + ".png");
                Drawable drawable = Drawable.createFromStream(stream, null);
                ivImageChant.setImageDrawable(drawable);
            } catch (IOException e) {
                e.printStackTrace();
            }

            String text = (chant.getnumeroChant()) + " - " ;
            tvNumeroChant.setText(text);
            tvTitreChant.setText(chant.gettitreChant());

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

