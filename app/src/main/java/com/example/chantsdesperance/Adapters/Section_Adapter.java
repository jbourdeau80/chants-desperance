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

import androidx.recyclerview.widget.RecyclerView;

import com.example.chantsdesperance.Activities.ListChantsActivity;
import com.example.chantsdesperance.Models.Section;
import com.example.chantsdesperance.R;

import org.parceler.Parcels;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

// This class is a custom RecyclerView adapter for displaying sections in a list. It includes functions for creating
// and binding view holders, as well as obtaining the item count. The adapter also defines a ViewHolder class that
// represents individual items in the list and handles binding data to the item views
public class Section_Adapter extends RecyclerView.Adapter<Section_Adapter.ViewHolder> {

    private List<Section> sections;
    Context context;

    // Initializes the adapter with the section data and context
    public Section_Adapter(Context context, List<Section> data) {
        this.sections = data;
        this.context = context;
    }

    // Inflates the item view layout and returns a new ViewHolder instance
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        view = LayoutInflater.from(context).inflate(R.layout.item_section, parent, false);
        return new ViewHolder(view);
    }

    // Binds the section data to the item views of the ViewHolder
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Section section = sections.get(position);
        holder.bind(section);
    }

    // Returns the total number of sections in the adapter
    @Override
    public int getItemCount() {
        return sections.size();
    }

    // Initializes the item views of the ViewHolder
    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivImageSection;
        TextView tvSectionName;
        RelativeLayout SectionContainer;

        public ViewHolder(View itemView) {
            super(itemView);
            ivImageSection = itemView.findViewById(R.id.ivImageSection);
            tvSectionName = itemView.findViewById(R.id.tvSectionName);
            SectionContainer = itemView.findViewById(R.id.SectionContainer);
        }

        // Binds the section data to the item views of the ViewHolder
        // Sets the section name, loads the section image, and handles item click
        public void bind(Section section) {
            tvSectionName.setText(section.getnomSection());
            try {
                InputStream stream = context.getAssets().open(section.getnomSection() + ".png");
                Drawable drawable = Drawable.createFromStream(stream, null);
                ivImageSection.setImageDrawable(drawable);
            } catch (IOException e) {
                e.printStackTrace();
            }

            SectionContainer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, ListChantsActivity.class);
                    intent.putExtra("section", Parcels.wrap(section));
                    context.startActivity(intent);
                }
            });
        }
    }
}
