package ba.sum.fsre.prodajarakije;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import ba.sum.fsre.prodajarakije.adapters.CustomerProductAdapter;
import ba.sum.fsre.prodajarakije.models.Merchant;
import ba.sum.fsre.prodajarakije.models.Product;

public class MainActivity extends AppCompatActivity {

    private FirebaseFirestore db;
    private ListView customerProductListView;
    private CustomerProductAdapter customerProductAdapter;
    private List<Product> products;

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
                } else if (item.getItemId()==R.id.nav_login) {
                    Intent intent=new Intent(getApplicationContext(), ProfileActivity.class);
                    startActivity(intent);
                    return true;
                }
                return false;
            }
        });

        customerProductListView=findViewById(R.id.customer_product_list_view);
        db=FirebaseFirestore.getInstance();
        products=new ArrayList<>();
        customerProductAdapter=new CustomerProductAdapter(getApplicationContext(),products);
        customerProductListView.setAdapter(customerProductAdapter);
        getProducts();
    }

    private void getProducts(){
        db.collection("products").get().addOnCompleteListener(task -> {
            if (task.isSuccessful() && task.getResult()!=null){
                products.clear();
                QuerySnapshot documents=task.getResult();
                for(DocumentSnapshot document : documents){
                    String productImage=document.getString("image");
                    String productTitle=document.getString("title");
                    int productPrice=document.getLong("price").intValue();
                    Merchant merchant=null;

                    Product product=new Product(productImage,productTitle,productPrice,null,merchant);
                    products.add(product);
                }
                customerProductAdapter.notifyDataSetChanged();
            }
        });
    }
}