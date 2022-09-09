package com.example.myapplication.Account;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.myapplication.databinding.FragmentAccountBottomsheetBinding;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class AccountBottomImage extends BottomSheetDialogFragment {


    private ClickChosepicture mclickchosepicture;
    private FragmentAccountBottomsheetBinding binding;

    public AccountBottomImage(ClickChosepicture mclickchosepicture) {
        this.mclickchosepicture = mclickchosepicture;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        binding = FragmentAccountBottomsheetBinding.inflate(inflater,container,false);

        binding.imgback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        binding.cvLiveimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mclickchosepicture.Clickchosepicture(5);
            }
        });
        binding.cvSelectimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mclickchosepicture.Clickchosepicture(6);
            }
        });

        return binding.getRoot();
    }
}
