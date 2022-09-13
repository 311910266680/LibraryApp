package com.example.myapplication.Favorite;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Model.Book;
import com.example.myapplication.Model.FavoriteBook;
import com.example.myapplication.databinding.FragmentFavoriteBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class FavoriteFrag extends Fragment implements ClickMenu, ClickRemoveFavorite {
    private FragmentFavoriteBinding binding;
    private FavoriteAdapter favoriteAdapter;
    private List<FavoriteBook> listbookfavorite;
    private List<Book> listbook;
    private List<String> listmenu;
    private FirebaseAuth firebaseAuth;
    private FavoriteMenuAdapter menuAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentFavoriteBinding.inflate(inflater,container,false);
        listbookfavorite = new ArrayList<>();
        listbook = new ArrayList<>();
        listmenu = new ArrayList<>();
        listmenu.add("All");
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(),2);
        favoriteAdapter = new FavoriteAdapter(listbook,this);

        binding.rcvfavorite.setLayoutManager(gridLayoutManager);
        binding.rcvfavorite.setAdapter(favoriteAdapter);

        firebaseAuth = FirebaseAuth.getInstance();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(),RecyclerView.HORIZONTAL,false);

        menuAdapter = new FavoriteMenuAdapter(listmenu,this,listbook);
        binding.rcvmenu.setLayoutManager(linearLayoutManager);
        binding.rcvmenu.setAdapter(menuAdapter);
        getFavor();

        return binding.getRoot();
    }

    private void getFavor() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
        DatabaseReference reference1 = FirebaseDatabase.getInstance().getReference("book");

        reference.child(firebaseAuth.getUid()).child("favorite").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listbookfavorite.clear();
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    FavoriteBook favoritebook = dataSnapshot.getValue(FavoriteBook.class);
                    listbookfavorite.add(favoritebook);
                }
                reference1.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        listbook.clear();
                        for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                            Book item = dataSnapshot.getValue(Book.class);

                            for(FavoriteBook a : listbookfavorite) {
                                if (String.valueOf(item.getId()).equals(a.getBookId())) {
                                    listbook.add(item);
                                }
                            }
                            for (Book book :listbook ){
                                if(!listmenu.contains(book.getType())){
                                    listmenu.add(book.getType());
                                }
                                menuAdapter.notifyDataSetChanged();
                            }
                            favoriteAdapter.notifyDataSetChanged();

                        }


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });






    }
    @Override
    public void ClickChooseMenu(List<Book> listbook) {
        favoriteAdapter.UpdateCourseAdapter(listbook);
    }

    @Override
    public void clickremovefavorite(Book book) {
        for(int i = 0; i<listbook.size(); i++){
            if(listbook.get(i).getId() == book.getId()){
                removeFromFavorite(getContext(),String.valueOf(book.getId()));
            }
        }
    }
    public void removeFromFavorite(Context context, String bookId){
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Users");
        ref.child(firebaseAuth.getUid()).child("favorite").child(bookId).removeValue()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {

                        Toast.makeText(context, "Remove from your favorite", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(context, "Failed to remove from your favorite"+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}