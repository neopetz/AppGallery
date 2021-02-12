package com.example.gallery;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;

import com.ismaeldivita.chipnavigation.ChipNavigationBar;

import static com.example.gallery.R.id.menuFragment;


public class HomeGallery extends AppCompatActivity {

    ChipNavigationBar chipNavigationBar;

   @Override
   protected void onCreate(Bundle savedInstanceState) {
         super.onCreate(savedInstanceState);
         setContentView(R.layout.activity_home_gallery);

         chipNavigationBar = findViewById(R.id.bottomNavMenu);
         chipNavigationBar.setItemSelected(R.id.menuFragment,true);
       getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new homefragment()).commit();
         bottomMenu();

    }

    private void bottomMenu() {
        chipNavigationBar.setOnItemSelectedListener(new ChipNavigationBar.OnItemSelectedListener() {
            @Override
            public void onItemSelected(int i) {
                Fragment fragment = null;
                switch(i){
                    case menuFragment:
                        fragment = new homefragment();
                        break;
                    case R.id.uploadFragment:
                        fragment = new settingsfragment();
                        break;
                    case R.id.profileFragment:
                        fragment = new profilefragment();
                        break;
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,fragment).commit();
            }

        });
    }
}


