package com.example.myapplication.Favorite;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Model.Book;
import com.example.myapplication.R;
import com.example.myapplication.Singleton;
import com.example.myapplication.databinding.ItemBookFavoriteBinding;
import com.squareup.picasso.Picasso;

import java.util.List;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.Viewholder> {
    private List<Book> listbookfavorite;
    private ClickRemoveFavorite mclickremovefavorite;


    public FavoriteAdapter(List<Book> listbookfavorite, ClickRemoveFavorite clickremovefavorite) {
        this.listbookfavorite = listbookfavorite;
        this.mclickremovefavorite = clickremovefavorite;
    }
    public void UpdateCourseAdapter(List<Book> bookList) {
        this.listbookfavorite = bookList;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_book_favorite,parent,false);
        return new Viewholder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, @SuppressLint("RecyclerView") int position) {
        Picasso.get().load(listbookfavorite.get(position).getImage()).into(holder.binding.imgfavorite);
        holder.binding.tvtitlefavorite.setText(listbookfavorite.get(position).getTitle());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Singleton.getInstance().getDetail(v.getContext(), listbookfavorite.get(position));
            }
        });
        holder.binding.icfavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mclickremovefavorite.clickremovefavorite(listbookfavorite.get(position));

            }
        });
    }

    @Override
    public int getItemCount() {
        if (listbookfavorite==null){
            return 0;
        }
        return listbookfavorite.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {
        private ItemBookFavoriteBinding binding;
        public Viewholder(@NonNull View itemView) {
            super(itemView);
            binding = ItemBookFavoriteBinding.bind(itemView);
        }
    }
}
