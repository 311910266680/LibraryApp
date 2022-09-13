package com.example.myapplication.Account;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.myapplication.Login.LoginActivity;
import com.example.myapplication.databinding.FragmentAccountBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;

public class AccountFragment extends Fragment implements ClickChosepicture{


    private FirebaseAuth mAuth;
    private FragmentAccountBinding binding;
    private FirebaseAuth firebaseAuth;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        firebaseAuth = FirebaseAuth.getInstance();
        loadUserInfo();

        binding = FragmentAccountBinding.inflate(inflater,container,false);
        mAuth = FirebaseAuth.getInstance();
        binding.profileDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(),ProfileActivity.class) );
            }
        });
        binding.icpicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AccountBottomImage accountBottomImage = new AccountBottomImage(AccountFragment.this);
                accountBottomImage.show(getActivity().getSupportFragmentManager(),"show sucessful");
            }
        });
        binding.btnlogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                FirebaseUser firebaseUser= mAuth.getCurrentUser();
                if(firebaseUser==null){
                    startActivity(new Intent(getActivity(), LoginActivity.class));
                    getActivity().finish();
                }
            }
        });





        return binding.getRoot();
    }

    private void loadUserInfo() {

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
        reference.child(firebaseAuth.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String name = ""+snapshot.child("name").getValue();
                String profileImage = ""+snapshot.child("profileImage").getValue();
                binding.tvname.setText(name);
                if (!profileImage.isEmpty()){
                    Picasso.get().load(profileImage).into(binding.circleImageView);
                }

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }









    @Override
    public void Clickchosepicture(int id) {
        if(id == 6){
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType("image/*");
            galleryActivityResultLauncher.launch(intent);
        }
        else if(id == 5){
            Intent intentlive = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            liveActivityResultLauncher.launch(intentlive);
        }
    }


    private ActivityResultLauncher<Intent> galleryActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK){
                        Intent data = result.getData();
                        Uri imageUri = data.getData();
                        Intent i = new Intent(getActivity(),PictureActivityy.class);
                        i.putExtra("img",String.valueOf(imageUri));
                        mActivityResultLauncher.launch(i);
                    }
                    else {
                    }
                }
            }
    );
    private ActivityResultLauncher<Intent> liveActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK){
                        Bundle bundle = result.getData().getExtras();
                        Bitmap bitmap = (Bitmap) bundle.get("data");

                        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
                        String path = MediaStore.Images.Media.insertImage(getContext().getContentResolver(),bitmap,"Title",null);
                        Intent i = new Intent(getActivity(),PictureActivityy.class);
                        i.putExtra("img",path);
                        mActivityResultLauncher.launch(i);

                    }
                    else {
                    }
                }
            }
    );
    private ActivityResultLauncher<Intent> mActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK){
                        Intent data = result.getData();
                        String trResult = data.getStringExtra("imgresult");
                        binding.circleImageView.setImageURI(Uri.parse(trResult));

                    }
                    else {

                    }
                }
            }
    );
}