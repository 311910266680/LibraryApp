package com.example.myapplication.Home;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Model.Book;

import com.example.myapplication.R;
import com.example.myapplication.Singleton;
import com.example.myapplication.databinding.ItemListFilterBinding;
import com.squareup.picasso.Picasso;

import java.util.List;


public class FilterAdapter extends RecyclerView.Adapter<FilterAdapter.FilterViewHolder> {
    private List<Book> mListFilter;
    private Context mContext;
    public FilterAdapter(List<Book> mListFilter, Context mContext) {
        this.mListFilter = mListFilter;
        this.mContext = mContext;
    }

    public void setmListFilter(List<Book> mListFilter){
        this.mListFilter = mListFilter;
        notifyDataSetChanged();
    }

    @NonNull
    @Override

    public FilterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_filter,parent,false);
        return new FilterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FilterViewHolder holder, int position) {
        Book Filter = mListFilter.get(position);

        Picasso.get().load(Filter.getImage()).into(holder.binding.imgfilter);
        holder.binding.Titlebook.setText(Filter.getTitle());
        if (Filter.getQuantity()==0){
            holder.binding.status.setText("Unavailable");
            holder.binding.status.setTextColor(Color.parseColor("#ff421a"));
        }
        else {
            holder.binding.status.setText("Available");
            holder.binding.status.setTextColor(Color.parseColor("#1DE9B6"));
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Singleton.getInstance().getDetail(mContext,Filter);
            }
        });

    }


    @Override
    public int getItemCount() {
        if (mListFilter !=null) {
            return mListFilter.size() ;
        }
        return 0;
    }

    public class FilterViewHolder extends RecyclerView.ViewHolder{


        private ItemListFilterBinding binding;
        public FilterViewHolder(@NonNull View itemView) {
            super(itemView);

            binding = ItemListFilterBinding.bind(itemView);
        }
    }
}
