package com.example.myapplication.Favorite;

import android.os.Bundle;

import androidx.appcompat.view.menu.MenuAdapter;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.myapplication.Model.Book;
import com.example.myapplication.R;
import com.example.myapplication.Singleton;
import com.example.myapplication.databinding.FragmentFavoriteBinding;

import java.util.ArrayList;
import java.util.List;

public class FavoriteFrag extends Fragment implements ClickMenu, ClickRemoveFavorite {
    private FragmentFavoriteBinding binding;
    private FavoriteAdapter favoriteAdapter;
    private List<Book> listbookfilter;
    private List<String> listmenu;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentFavoriteBinding.inflate(inflater,container,false);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(),2);
        favoriteAdapter = new FavoriteAdapter(Singleton.getListbookfavorite(),this);
        binding.rcvfavorite.setLayoutManager(gridLayoutManager);
        binding.rcvfavorite.setAdapter(favoriteAdapter);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(),RecyclerView.HORIZONTAL,false);
        getlistmenu();
        FavoriteMenuAdapter menuAdapter = new FavoriteMenuAdapter(listmenu,this);
        binding.rcvmenu.setLayoutManager(linearLayoutManager);
        binding.rcvmenu.setAdapter(menuAdapter);



        return binding.getRoot();
    }
    private void getlistmenu(){
        listmenu = new ArrayList<>();
        listmenu.add("All");
        for(int i = 0 ; i < Singleton.getListbookfavorite().size();i ++){
            if(!listmenu.contains(Singleton.getListbookfavorite().get(i).getType())){
                listmenu.add(Singleton.getListbookfavorite().get(i).getType());
            }
        }
    }

    @Override
    public void ClickChooseMenu(List<Book> listbook) {
        favoriteAdapter.UpdateCourseAdapter(listbook);
    }

    @Override
    public void clickremovefavorite(Book book) {
        for(int i = 0; i< Singleton.getListbookfavorite().size(); i++){
            if(Singleton.getListbookfavorite().get(i).getId() == book.getId()){
                Singleton.getListbookfavorite().remove(Singleton.getListbookfavorite().get(i));
            }
        }
        Toast.makeText(getContext(),"Delete Sucessful",Toast.LENGTH_LONG).show();
        favoriteAdapter.notifyDataSetChanged();
    }
}