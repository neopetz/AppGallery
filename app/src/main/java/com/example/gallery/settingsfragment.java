package com.example.gallery;


import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.google.firebase.auth.FirebaseAuth;

import java.util.MissingResourceException;


public class settingsfragment extends Fragment  {


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settingsfragment, container, false);
        view.setBackgroundColor(getResources().getColor(R.color.white));




        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        preffragment Preffragment = new preffragment();
        fragmentTransaction.replace(R.id.fragthis, Preffragment);
        fragmentTransaction.commit();

        return view;

    }






}

