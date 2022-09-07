package com.example.myapplication.Borrow;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Model.Book;
import com.example.myapplication.Model.BorrowBook;
import com.example.myapplication.R;
import com.example.myapplication.Singleton;
import com.example.myapplication.databinding.ItemBookBorrowBinding;
import com.squareup.picasso.Picasso;

import java.util.List;

public class BorrowAdapter extends RecyclerView.Adapter<BorrowAdapter.Viewholder> {
    private List<BorrowBook> listbookborrow;

    public BorrowAdapter(List<BorrowBook> listbookborrow) {
        this.listbookborrow = listbookborrow;
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_book_borrow,parent,false);

        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {
        Picasso.get().load(listbookborrow.get(position).getImg()).into(holder.binding.img);
        holder.binding.title.setText(listbookborrow.get(position).getTitle());
        holder.binding.count.setText(String.valueOf(listbookborrow.get(position).getCount()));
        holder.binding.datestart.setText(listbookborrow.get(position).getDatestart());
        holder.binding.expirationdate.setText(listbookborrow.get(position).getExpirationdate());
        holder.binding.price.setText(String.valueOf(listbookborrow.get(position).getPrice()));

    }

    @Override
    public int getItemCount() {
        return listbookborrow.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {
        private ItemBookBorrowBinding binding;
        public Viewholder(@NonNull View itemView) {
            super(itemView);
            binding = ItemBookBorrowBinding.bind(itemView);
        }
    }
}
