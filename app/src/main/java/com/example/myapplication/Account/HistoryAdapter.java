package com.example.myapplication.Account;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Home.BookAdapter;
import com.example.myapplication.Model.Book;
import com.example.myapplication.Model.BorrowBook;
import com.example.myapplication.Model.Order;
import com.example.myapplication.R;
import com.example.myapplication.Singleton;
import com.example.myapplication.databinding.ItemBookBorrowBinding;
import com.example.myapplication.databinding.ItemHistoryBinding;
import com.squareup.picasso.Picasso;

import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder> {
    private List<Order> orderList;
    private List<BorrowBook> borrowBookList;
    private Context mContext;
    private String uid;


    public HistoryAdapter(List<Order> orderList, List<BorrowBook> borrowBookList, Context mContext, String uid) {
        this.orderList = orderList;
        this.borrowBookList = borrowBookList;
        this.mContext = mContext;
        this.uid = uid;
    }

    @NonNull
    @Override

    public HistoryAdapter.HistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_history,parent,false);
        return new HistoryAdapter.HistoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryAdapter.HistoryViewHolder holder, int position) {

        for (Order order: orderList){
            if (order.getIduser().equals(uid)){
                BorrowBook bookBor = borrowBookList.get(position);
//                holder.binding.note.setText(order.getNote());
//                holder.binding.typehis.setText(order.getType());
//                holder.binding.orderday.setText(order.getDays());
                Picasso.get().load(bookBor.getBook().getImage()).into(holder.binding.img);
                holder.binding.title.setText(bookBor.getBook().getTitle());
                holder.binding.count.setText(String.valueOf(bookBor.getCount()));
                holder.binding.datestart.setText(bookBor.getDatestart());
                holder.binding.expirationdate.setText(bookBor.getExpirationdate());
                holder.binding.pricetotal.setText(String.valueOf(bookBor.getPricetotal()));
                holder.binding.tvduration.setText(String.valueOf(bookBor.getDuration()));
            }
        }
    }

    @Override
    public int getItemCount() {
        if (borrowBookList !=null) {
            return borrowBookList.size() ;
        }
        return 0;
    }

    public class HistoryViewHolder extends RecyclerView.ViewHolder{

        private ItemHistoryBinding binding;
        public HistoryViewHolder(@NonNull View itemView) {
            super(itemView);

            binding = ItemHistoryBinding.bind(itemView);

        }
    }
}
