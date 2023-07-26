package com.example.chantsdesperance.Adapters;

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

import com.example.chantsdesperance.Activities.ChantActivity;
import com.example.chantsdesperance.Models.Chants;
import com.example.chantsdesperance.R;

import org.parceler.Parcels;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

// This class is a RecyclerView adapter responsible for populating the items in the chants list
// It takes a list of Chants objects as data and binds the data to the corresponding views in the item layout
public class ListChants_Adapter extends RecyclerView.Adapter<ListChants_Adapter.ViewHolder> {

    private List<Chants> chants;
    Context context;

    public ListChants_Adapter(Context context, List<Chants> chants) {

        this.chants = chants;
        this.context = context;
    }

    public void setChants(List<Chants> chants) {
        this.chants = chants;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        // Inflate the item layout and create a ViewHolder
        View view;
        view = LayoutInflater.from(context).inflate(R.layout.item_chant, parent, false);
        return new ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        // Bind the chant data to the ViewHolder
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
            // Initialize the views in the item layout
            super(itemView);
            ivImageChant = itemView.findViewById(R.id.ivImageChant);
            tvNumeroChant = itemView.findViewById(R.id.tvNumeroChant);
            tvTitreChant = itemView.findViewById(R.id.tvTitreChant);
            ChantContainer = itemView.findViewById(R.id.ChantContainer);

        }

        public void bind(Chants chant) {
            // Set chant number and title
            String text = chant.getnumeroChant() + " - " + chant.gettitreChant();
            tvNumeroChant.setText(text);

            // Load section image from assets
            try {
                String filename = chant.getnomSection() + ".png";
                InputStream stream = context.getAssets().open(filename);
                Drawable drawable = Drawable.createFromStream(stream, null);
                ivImageChant.setImageDrawable(drawable);
            } catch (IOException e) {
                e.printStackTrace();
            }

            // Set click listener on container
            ChantContainer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // Open the ChantActivity when the container is clicked
                    Intent intent = new Intent(context, ChantActivity.class);
                    intent.putExtra("chants", Parcels.wrap(chant));
                    context.startActivity(intent);
                }
            });
        }
    }

}

