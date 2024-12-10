package ba.sum.fsre.prodajarakije;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;

import com.google.android.material.tabs.TabLayout;

import ba.sum.fsre.prodajarakije.fragments.LoginFragment;
import ba.sum.fsre.prodajarakije.fragments.RegisterFragment;

public class ProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer,new LoginFragment()).commit();

        TabLayout tabLayout=findViewById(R.id.tabLayout);

        tabLayout.addTab(tabLayout.newTab().setText("Prijava"));
        tabLayout.addTab(tabLayout.newTab().setText("Registracija"));

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                Fragment selectedFragment=null;
                switch (tab.getPosition()){
                    case 0:
                        selectedFragment=new LoginFragment();
                        break;
                    case 1:
                        selectedFragment=new RegisterFragment();
                        break;
                }
                assert selectedFragment != null;
                getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer,selectedFragment).commit();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }
}