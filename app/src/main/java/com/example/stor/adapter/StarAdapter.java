package com.example.stor.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.stor.R;
import com.example.stor.beans.Star;
import com.example.stor.service.StarService;
import de.hdodenhof.circleimageview.CircleImageView;
import java.util.ArrayList;
import java.util.List;

public class StarAdapter extends RecyclerView.Adapter<StarAdapter.StarViewHolder> implements Filterable {

    private List<Star> stars;
    private List<Star> starsFilter;
    private Context context;
    private NewFilter mfilter;

    public StarAdapter(Context context, List<Star> stars) {
        this.context = context;
        this.stars = stars;
        this.starsFilter = new ArrayList<>(stars);
        this.mfilter = new NewFilter(this);
    }

    @NonNull
    @Override
    public StarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.star_item, parent, false);
        final StarViewHolder holder = new StarViewHolder(v);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Charger la vue du popup
                View popup = LayoutInflater.from(context).inflate(R.layout.star_edit_item, null, false);
                final CircleImageView img = popup.findViewById(R.id.img);
                final RatingBar bar = popup.findViewById(R.id.ratingBar);
                final TextView idss = popup.findViewById(R.id.idss);

                // Récupération des valeurs actuelles de la star sélectionnée
                int position = holder.getAdapterPosition();
                Star currentStar = starsFilter.get(position);

                Glide.with(context).load(currentStar.getImg()).into(img);
                bar.setRating(currentStar.getRating());
                idss.setText(String.valueOf(currentStar.getId()));

                // Création et affichage du popup avec AlertDialog
                AlertDialog dialog = new AlertDialog.Builder(context)
                        .setTitle("Notez :")
                        .setMessage("Donner une note entre 1 et 5 :")
                        .setView(popup)
                        .setPositiveButton("Valider", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                float newRating = bar.getRating();
                                int id = Integer.parseInt(idss.getText().toString());
                                Star star = StarService.getInstance().findById(id);
                                if (star != null) {
                                    star.setRating(newRating);
                                    StarService.getInstance().update(star);
                                    notifyItemChanged(holder.getAdapterPosition());
                                }
                            }
                        })
                        .setNegativeButton("Annuler", null)
                        .create();
                dialog.show();
            }
        });

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull StarViewHolder holder, int position) {
        Star s = starsFilter.get(position);
        holder.name.setText(s.getName().toUpperCase());
        holder.rating.setRating(s.getRating());
        Glide.with(context).load(s.getImg()).into(holder.img);
    }

    @Override
    public int getItemCount() {
        return starsFilter.size();
    }

    @Override
    public Filter getFilter() {
        return mfilter;
    }

    public class StarViewHolder extends RecyclerView.ViewHolder {
        CircleImageView img;
        TextView name;
        RatingBar rating;

        public StarViewHolder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.imgStar);
            name = itemView.findViewById(R.id.tvName);
            rating = itemView.findViewById(R.id.rating);
        }
    }

    public class NewFilter extends Filter {
        public RecyclerView.Adapter mAdapter;

        public NewFilter(RecyclerView.Adapter mAdapter) {
            this.mAdapter = mAdapter;
        }

        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            List<Star> filtered = new ArrayList<>();
            if (charSequence == null || charSequence.length() == 0) {
                filtered.addAll(stars);
            } else {
                String filterPattern = charSequence.toString().toLowerCase().trim();
                for (Star p : stars) {
                    if (p.getName().toLowerCase().startsWith(filterPattern)) {
                        filtered.add(p);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filtered;
            results.count = filtered.size();
            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            starsFilter = (List<Star>) filterResults.values;
            mAdapter.notifyDataSetChanged();
        }
    }
}