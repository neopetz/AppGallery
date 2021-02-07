package com.example.gallery;

import android.content.ClipData;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;

public class uploadfragment extends Fragment {



    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_uploadfragment, container, false);

        Intent intent = new Intent(getActivity(), Upload_activity.class);
        startActivity(intent);

        return view;
    }
}










