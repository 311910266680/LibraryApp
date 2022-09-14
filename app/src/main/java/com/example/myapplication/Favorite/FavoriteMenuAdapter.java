package com.example.myapplication.Favorite;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Model.Book;
import com.example.myapplication.R;
import com.example.myapplication.databinding.ItemBookFavoriteMenuBinding;

import java.util.ArrayList;
import java.util.List;

public class FavoriteMenuAdapter extends RecyclerView.Adapter<FavoriteMenuAdapter.Viewholder> {
    private List<String> listmenu;
    private List<Book> productListfilter, listbook;
    private ClickMenu mclikcmenu;

    int selectedItem;

    public FavoriteMenuAdapter(List<String> listmenu, ClickMenu clikcmenu, List<Book> listbook) {
        this.listmenu = listmenu;
        this.listbook = listbook;
        this.mclikcmenu = clikcmenu;
        selectedItem = 0;
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_book_favorite_menu,parent,false);

        return new Viewholder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, @SuppressLint("RecyclerView") int position) {
        holder.binding.tvnamemenu.setText(listmenu.get(position).toString());
        holder.binding.tvnamemenu.setTextColor(Color.parseColor("#DCD9D9"));
        holder.binding.imvline.setVisibility(View.INVISIBLE);
        if(selectedItem == position){
            holder.binding.tvnamemenu.setTextColor(Color.parseColor("#F21E1C29"));
            holder.binding.imvline.setVisibility(View.VISIBLE);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int previousItem = selectedItem;
                selectedItem = position;
                notifyItemChanged(previousItem);
                notifyItemChanged(position);

                productListfilter = new ArrayList<>();

                for(int i = 0; i< listbook.size(); i++){
                    if(listmenu.get(position).toString().contains(listbook.get(i).getType())){
                        productListfilter.add(listbook.get(i));
                    }
                    else if(listmenu.get(position).toString() == "All"){
                        productListfilter.add(listbook.get(i));
                    }
                    mclikcmenu.ClickChooseMenu(productListfilter);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        if(listmenu ==null){
            return 0;
        }
        return listmenu.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {
        private ItemBookFavoriteMenuBinding binding;
        public Viewholder(@NonNull View itemView) {
            super(itemView);
            binding = ItemBookFavoriteMenuBinding.bind(itemView);
        }
    }
}
