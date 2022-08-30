//package com.example.myapplication.Fragment;
//
//import android.os.Bundle;
//import android.text.Editable;
//import android.text.TextWatcher;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.EditText;
//import android.widget.Filter;
//import android.widget.Filterable;
//import android.widget.SearchView;
//
//import androidx.fragment.app.Fragment;
//import androidx.recyclerview.widget.GridLayoutManager;
//import androidx.recyclerview.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.example.myapplication.FilterAdapter;
//import com.example.myapplication.Model.Book;
//import com.example.myapplication.R;
//import com.example.myapplication.Singleton;
//
//import java.util.ArrayList;
//import java.util.List;
//
//
//public class SearchFrag extends Fragment {
//    RecyclerView recyclerView;
//    FilterAdapter adapter;
//    View view;
//    EditText ss;
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//
//        view = inflater.inflate(R.layout.fragment_search, container, false);
//        recyclerView = view.findViewById(R.id.recsearch);
//        ss = view.findViewById(R.id.ss);
//
//        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
//         adapter = new FilterAdapter(Singleton.getInstance().ListBook,getContext());
//        recyclerView.setAdapter(adapter);
//        ss.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable editable) {
//                filter(editable.toString());
//            }
//        });
//        return view;
//    }
//    private void filter(String text){
//        List<Book> itemSearchList = new ArrayList<>();
//        for(Book itemSearch : Singleton.getInstance().ListBook){
//            if(itemSearch.getTitle().toLowerCase().contains(text.toLowerCase())){
//                itemSearchList.add(itemSearch);
//            }
//        }
//        adapter.setmListFilter(itemSearchList);
//    }
//}