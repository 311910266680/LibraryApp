package com.example.myapplication.Home;



import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Model.Book;
import com.example.myapplication.Model.Type;
import com.example.myapplication.R;
import com.example.myapplication.Singleton;
import com.squareup.picasso.Picasso;

import java.util.List;


public class TypeAdapter extends RecyclerView.Adapter<TypeAdapter.TypeViewHolder> {
    private List<Type> mListType;
    private Context mContext;
    public TypeAdapter(List<Type> mListType, Context mContext) {
        this.mListType = mListType;
        this.mContext = mContext;
    }



    @NonNull
    @Override

    public TypeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pick,parent,false);
        return new TypeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TypeViewHolder holder, int position) {
        Type Type = mListType.get(position);
        if (Type ==null){
            return;
        }
        holder.TypeBook.setText(Type.getName());
        Picasso.get().load(Type.getImage()).into(holder.ImgType);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Singleton.getInstance().ListFilter.clear();
                for (Book book: Singleton.getInstance().ListBook){
                   if (book.getType().equals(Type.getName()) ){
                    Singleton.getInstance().ListFilter.add(book);
                   }
               }
                view.getContext().startActivity(new Intent(view.getContext(), PickActivity.class));
            }
        });

    }


    @Override
    public int getItemCount() {
        if (mListType !=null) {
            return mListType.size() ;
        }
        return 0;
    }

    public class TypeViewHolder extends RecyclerView.ViewHolder{



        private TextView TypeBook;
        private ImageView ImgType;
        public TypeViewHolder(@NonNull View itemView) {
            super(itemView);

            TypeBook = itemView.findViewById(R.id.type);
            ImgType = itemView.findViewById(R.id.imageType);

        }
    }
}
