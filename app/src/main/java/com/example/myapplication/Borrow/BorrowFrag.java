package com.example.myapplication.Borrow;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myapplication.Model.BorrowBook;
import com.example.myapplication.R;
import com.example.myapplication.Singleton;
import com.example.myapplication.databinding.FragmentBorrowBinding;
import com.squareup.picasso.Picasso;


public class BorrowFrag extends Fragment implements ClickDialogDelete,ClickShowDialog, ClickUpdateBorrow{
    private FragmentBorrowBinding binding;
    private BorrowAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentBorrowBinding.inflate(inflater,container,false);

        adapter = new BorrowAdapter(Singleton.getListBookBorrow(),this,this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL,false);
        binding.rcvborrow.setLayoutManager(linearLayoutManager);
        binding.rcvborrow.setAdapter(adapter);

        return binding.getRoot();
    }

    @Override
    public void ClickDialogdelete(BorrowBook book) {
        for (int i = 0; i< Singleton.getListBookBorrow().size(); i++){
            if(Singleton.getListBookBorrow().get(i).getId() == book.getId()){
                Singleton.getListBookBorrow().remove(Singleton.getListBookBorrow().get(i));
            }
        }
        adapter.notifyDataSetChanged();

    }

    @Override
    public void clickShowdialog(BorrowBook borrowBook) {
        Bundle bundle = new Bundle();
        bundle.putInt("id",borrowBook.getId());
        bundle.putInt("quantity",borrowBook.getCount());
        bundle.putString("datestart",borrowBook.getDatestart());
        bundle.putString("date",borrowBook.getExpirationdate());
        BorrowDialog dialog = new BorrowDialog(this);
        dialog.setArguments(bundle);

        dialog.show(getParentFragmentManager(),"ok");
    }

    @Override
    public void clickupdateborrow(int id) {
        adapter.notifyDataSetChanged();
    }
}