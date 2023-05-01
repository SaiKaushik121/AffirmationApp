package com.example.myaffirmationsapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;


import androidx.annotation.NonNull;

import java.util.List;

public class AffirmationAdapter extends RecyclerView.Adapter<AffirmationAdapter.ViewHolder> {
    private List<AffirmationGenerator.Affirmation> affirmationList;
    private Context context;

    public AffirmationAdapter(List<AffirmationGenerator.Affirmation> affirmationList, Context context) {
        this.affirmationList = affirmationList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.affirmation_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        AffirmationGenerator.Affirmation affirmation = affirmationList.get(position);
        holder.affirmationTextView.setText(affirmation.getText());
        holder.affirmationImageView.setImageResource(affirmation.getImageId());
    }

    @Override
    public int getItemCount() {
        return affirmationList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView affirmationTextView;
        public ImageView affirmationImageView;

        public ViewHolder(View view) {
            super(view);
            affirmationTextView = view.findViewById(R.id.affirmation_text_view);
            affirmationImageView = view.findViewById(R.id.affirmation_image_view);
        }
    }
}

