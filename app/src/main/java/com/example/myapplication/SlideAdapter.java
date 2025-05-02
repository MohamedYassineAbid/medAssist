package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class SlideAdapter extends RecyclerView.Adapter<SlideAdapter.SlideViewHolder> {

    private Context context;
    private List<SlideItem> slideItems;

    public SlideAdapter(Context context, List<SlideItem> slideItems) {
        this.context = context;
        this.slideItems = slideItems;
    }

    @NonNull
    @Override
    public SlideViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SlideViewHolder(
                LayoutInflater.from(context).inflate(R.layout.onboarding_screen, parent, false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull SlideViewHolder holder, int position) {
        holder.bind(slideItems.get(position));
    }

    @Override
    public int getItemCount() {
        return slideItems.size();
    }

    class SlideViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        private TextView titleView;
        private TextView descriptionView;

        SlideViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.slideImage);
            titleView = itemView.findViewById(R.id.slideTitle);
            descriptionView = itemView.findViewById(R.id.slideDescription);
        }

        void bind(SlideItem slideItem) {
            imageView.setImageResource(slideItem.getImage());
            titleView.setText(slideItem.getTitle());
            descriptionView.setText(slideItem.getDescription());
        }
    }

    // Model class for slide items
    public static class SlideItem {
        private int image;
        private String title;
        private String description;

        public SlideItem(int image, String title, String description) {
            this.image = image;
            this.title = title;
            this.description = description;
        }

        public int getImage() {
            return image;
        }

        public String getTitle() {
            return title;
        }

        public String getDescription() {
            return description;
        }
    }
}