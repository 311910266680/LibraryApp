package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Model.Book;

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
        if (Filter ==null){
            return;
        }
        Picasso.get().load(Filter.getImage()).into(holder.binding.imgfilter);
        holder.binding.Titlebook.setText(Filter.getTitle());
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
//        private ImageView Image;
//        private TextView titlebook;
        public FilterViewHolder(@NonNull View itemView) {
            super(itemView);
//            Image = itemView.findViewById(R.id.imgfilter);
//            titlebook = itemView.findViewById(R.id.Titlebook);

            binding = ItemListFilterBinding.bind(itemView);
        }
    }
}
