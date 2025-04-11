package com.example.shiranapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;

public class SecoundFragment extends Fragment {

    public SecoundFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.motivation_secound, container, false);
    }

    public String getText() {
        return "אין דבר העומד בפני הרצון!";
    }
}
