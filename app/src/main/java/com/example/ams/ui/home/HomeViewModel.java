package com.example.ams.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.auth.FirebaseAuth;

public class HomeViewModel extends ViewModel {

    private MutableLiveData<String> mText;
    FirebaseAuth mAuth;

    public HomeViewModel() {
        mAuth= FirebaseAuth.getInstance();

        mText = new MutableLiveData<>();
        mText.setValue("Welcome "+ mAuth.getCurrentUser().getEmail());
    }

    public LiveData<String> getText() {
        return mText;
    }
}