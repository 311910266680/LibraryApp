package com.example.myapplication.Home;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Model.Book;
import com.example.myapplication.R;
import com.example.myapplication.Singleton;
import com.squareup.picasso.Picasso;

import java.util.List;


public class BookAdapter extends RecyclerView.Adapter<BookAdapter.BookViewHolder> {
    private List<Book> mListBook;
    private Context mContext;
    public BookAdapter(List<Book> mListBook, Context mContext) {
        this.mListBook = mListBook;
        this.mContext = mContext;
    }
    @NonNull
    @Override

    public BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_book,parent,false);
        return new BookViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookViewHolder holder, int position) {
        Book Book = mListBook.get(position);
        if (Book ==null){
            return;
        }
//        new DownImageTask(Book.getImage(), holder.Image).execute(Book.getImage());

        Picasso.get().load(Book.getImage()).into(holder.Image);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Singleton.getInstance().getDetail(view.getContext(),Book);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (mListBook !=null) {
            return mListBook.size() ;
        }
        return 0;
    }

    public class BookViewHolder extends RecyclerView.ViewHolder{

        private ImageView Image;
        public BookViewHolder(@NonNull View itemView) {
            super(itemView);

            Image = itemView.findViewById(R.id.imageType);

        }
    }
//    class DownImageTask extends AsyncTask<String, Void, String> {
//        private String img;
//        private ImageView imageView;
//
//        public DownImageTask(String img, ImageView imageView) {
//            this.img = img;
//            this.imageView = imageView;
//        }
//
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//            Log.e("TAG","onPreExecute");
//        }
//
//        @Override
//        protected String doInBackground(String... strings) {
//            Picasso.get().load(strings[0]);
//            return null;
//        }
//
//        @Override
//        protected void onPostExecute(String string) {
//            super.onPostExecute(string);
//            Log.e("TAG","onPostExecute");
//            Picasso.get().load(string).into(imageView);
//
//        }
//    }
}
