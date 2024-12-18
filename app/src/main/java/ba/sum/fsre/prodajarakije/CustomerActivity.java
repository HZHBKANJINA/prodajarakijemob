package ba.sum.fsre.prodajarakije;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.google.android.material.navigation.NavigationView;

import ba.sum.fsre.prodajarakije.fragments.CustomerHomeFragment;
import ba.sum.fsre.prodajarakije.fragments.CustomerProfileFragment;

public class CustomerActivity extends AppCompatActivity {

    public DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer);

        Toolbar toolbar=findViewById(R.id.customer_toolbar);
        setSupportActionBar(toolbar);

        getSupportFragmentManager().beginTransaction().replace(R.id.customer_fragment_container,new CustomerProfileFragment()).commit();

        this.drawerLayout=findViewById(R.id.customer_drawer_layout);
        NavigationView navigationView=findViewById(R.id.customer_navigation_view);

        ActionBarDrawerToggle toggle=new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.open_drawer,R.string.close_drawer);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment selectedFragment=null;
                if(item.getItemId()==R.id.customer_nav_profile){
                    selectedFragment=new CustomerProfileFragment();
                } else if (item.getItemId()==R.id.customer_nav_home) {
                    selectedFragment=new CustomerHomeFragment();
                }
                assert selectedFragment != null;
                getSupportFragmentManager().beginTransaction().replace(R.id.customer_fragment_container,selectedFragment).commit();
                drawerLayout.closeDrawers();
                return false;
            }
        });
    }
}