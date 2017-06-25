package com.forever.kimoo.popularmovies.Controller.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.forever.kimoo.popularmovies.Models.Review;
import com.forever.kimoo.popularmovies.R;

import java.util.ArrayList;

/**
 * Created by KiMoo on 30/03/2017.
 */

public class ReviewsAdapter extends RecyclerView.Adapter<ReviewsAdapter.ReviewsHolder> {
    ArrayList<Review> reviewsList;

    public ReviewsAdapter(ArrayList<Review> reviewsList) {
        this.reviewsList = reviewsList;
    }

    @Override
    public ReviewsHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View row = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_review, parent, false);
        ReviewsHolder holder = new ReviewsHolder(row);
        return holder;
    }

    @Override
    public void onBindViewHolder(ReviewsHolder holder, int position) {
        Review review = reviewsList.get(position);
        holder.authorTextView.setText(review.getAuthor());
        holder.contentTextView.setText(review.getContent());
    }

    @Override
    public int getItemCount() {
        return reviewsList.size();
    }

    public class ReviewsHolder extends RecyclerView.ViewHolder {
        TextView authorTextView;
        TextView contentTextView;

        public ReviewsHolder(View itemView) {
            super(itemView);
            authorTextView = (TextView) itemView.findViewById(R.id.review_author);
            contentTextView = (TextView) itemView.findViewById(R.id.review_content);
        }
    }
}
