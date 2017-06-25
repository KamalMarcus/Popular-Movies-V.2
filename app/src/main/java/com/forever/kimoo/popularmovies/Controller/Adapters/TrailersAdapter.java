package com.forever.kimoo.popularmovies.Controller.Adapters;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.forever.kimoo.popularmovies.Models.Trailer;
import com.forever.kimoo.popularmovies.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by KiMoo on 30/03/2017.
 */

public class TrailersAdapter extends RecyclerView.Adapter<TrailersAdapter.TrailersHolder> {
    ArrayList<Trailer> trailersList;

    public TrailersAdapter(ArrayList<Trailer> trailersList) {
        this.trailersList = trailersList;
    }

    @Override
    public TrailersHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View row = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_trailer, parent, false);
        TrailersHolder holder = new TrailersHolder(row);
        return holder;
    }

    @Override
    public void onBindViewHolder(final TrailersHolder holder, int position) {
        final Trailer trailer = trailersList.get(position);
        Picasso.with(holder.trailerImage.getContext()).load("http://img.youtube.com/vi/" + trailer.getKey() + "/0.jpg").placeholder(R.drawable.trailer_temp).into(holder.trailerImage);
        holder.trailerName.setText(trailer.getName());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("http://www.youtube.com/watch?v=" + trailer.getKey()));
                holder.itemView.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return trailersList.size();
    }

    public class TrailersHolder extends RecyclerView.ViewHolder {
        ImageView trailerImage;
        TextView trailerName;

        public TrailersHolder(View itemView) {
            super(itemView);
            trailerImage = (ImageView) itemView.findViewById(R.id.trailer_image);
            trailerName = (TextView) itemView.findViewById(R.id.trailer_name);
        }
    }
}
