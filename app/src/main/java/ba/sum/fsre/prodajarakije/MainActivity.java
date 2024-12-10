package ba.sum.fsre.prodajarakije;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNavigationView=findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if(item.getItemId()==R.id.nav_home){
                    Intent intent=new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                    return true;
                } else if (item.getItemId()==R.id.nav_cart) {
                    return true;
                } else if (item.getItemId()==R.id.nav_profile) {
                    Intent intent=new Intent(getApplicationContext(), ProfileActivity.class);
                    startActivity(intent);
                    return true;
                }
                return false;
            }
        });
    }
}