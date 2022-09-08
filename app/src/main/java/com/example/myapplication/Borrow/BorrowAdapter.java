package com.example.myapplication.Borrow;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
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
    private ClickDialogDelete mclickdialogdelete;
    private ClickShowDialog mclickshowdialog;

    public BorrowAdapter(List<BorrowBook> listbookborrow,ClickDialogDelete clickdialogdelete,ClickShowDialog clickshowdialog) {
        this.listbookborrow = listbookborrow;
        this.mclickdialogdelete = clickdialogdelete;
        this.mclickshowdialog = clickshowdialog;
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_book_borrow,parent,false);

        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, @SuppressLint("RecyclerView") int position) {
        Picasso.get().load(listbookborrow.get(position).getImg()).into(holder.binding.img);
        holder.binding.title.setText(listbookborrow.get(position).getTitle());
        holder.binding.count.setText(String.valueOf(listbookborrow.get(position).getCount()));
        holder.binding.datestart.setText(listbookborrow.get(position).getDatestart());
        holder.binding.expirationdate.setText(listbookborrow.get(position).getExpirationdate());
        holder.binding.price.setText(String.valueOf(listbookborrow.get(position).getPrice()));
        holder.binding.tvduration.setText(String.valueOf(listbookborrow.get(position).getDuration()));

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                builder.setMessage("Do you want to delete "+listbookborrow.get(position).getTitle()+ "?");
                builder.setTitle("Alert !");
                builder.setCancelable(false);
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        mclickdialogdelete.ClickDialogdelete(listbookborrow.get(position));
                        dialog.dismiss();
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                AlertDialog alertDialog = builder.create();
                alertDialog.show();
                return false;
            }
        });

        holder.binding.icedit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mclickshowdialog.clickShowdialog(listbookborrow.get(position));
            }
        });


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
