package ba.sum.fsre.prodajarakije.fragments;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Spinner;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import ba.sum.fsre.prodajarakije.R;
import ba.sum.fsre.prodajarakije.adapters.CustomerHomeProductAdapter;
import ba.sum.fsre.prodajarakije.models.Merchant;
import ba.sum.fsre.prodajarakije.models.Product;

public class CustomerHomeFragment extends Fragment {

    private FirebaseFirestore db;

    private FirebaseAuth mAuth;

    private ListView customerHomeProductListView;

    private CustomerHomeProductAdapter customerHomeProductAdapter;

    private List<Product> products;

    private Spinner sortSpinner;

    private SearchView searchBar;

    ArrayAdapter<String> arrayAdapter;

    public CustomerHomeFragment(){
        super();
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View v=inflater.inflate(R.layout.fragment_customer_home,container,false);

        customerHomeProductListView=v.findViewById(R.id.customer_home_product_list_view);
        sortSpinner=v.findViewById(R.id.customer_home_product_sort_spinner);
        searchBar=v.findViewById(R.id.customer_home_search_bar);

        db=FirebaseFirestore.getInstance();
        products=new ArrayList<>();


        customerHomeProductAdapter=new CustomerHomeProductAdapter(v.getContext(),products);
        customerHomeProductListView.setAdapter(customerHomeProductAdapter);


        getProducts();

        searchBar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText.isEmpty()) {
                    // Ako je pretraga prazna, prikaži sve proizvode
                    customerHomeProductAdapter.clear();
                    customerHomeProductAdapter.addAll(products);
                } else {
                    // Filtriraj proizvode iz originalne liste `products`, a ne iz adaptera
                    List<Product> filteredProducts = new ArrayList<>();
                    for (Product p : products) {
                        if (p.getTitle().toLowerCase().contains(newText.toLowerCase())) {
                            filteredProducts.add(p);
                        }
                    }
                    customerHomeProductAdapter.addAll(filteredProducts);
                }
                customerHomeProductAdapter.notifyDataSetChanged();
                return true;
            }
        });


        return v;
    }

    private void getProducts(){
        db.collection("products").get().addOnCompleteListener(task -> {
            if(task.isSuccessful() && task.getResult()!=null){
                products.clear();
                QuerySnapshot documents=task.getResult();
                for(DocumentSnapshot document : documents){
                    String productImage=document.getString("image");
                    String productTitle=document.getString("title");
                    int productPrice=document.getLong("price").intValue();
                    String storeName=document.getString("storeName");
                    Merchant merchant=document.get("merchant",Merchant.class);

                    Product product=new Product(productImage,productTitle,productPrice,null,merchant);
                    products.add(product);
                }
                customerHomeProductAdapter.notifyDataSetChanged();
            }
        });
    }
}