package ba.sum.fsre.prodajarakije.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

import ba.sum.fsre.prodajarakije.R;
import ba.sum.fsre.prodajarakije.adapters.CustomerHomeProductAdapter;
import ba.sum.fsre.prodajarakije.models.Product;

public class CustomerHomeFragment extends Fragment {

    private FirebaseFirestore db;

    private FirebaseAuth mAuth;

    private ListView customerHomeProductListView;

    private CustomerHomeProductAdapter customerHomeProductAdapter;

    private List<Product> products;

    public CustomerHomeFragment(){
        super();
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View v=inflater.inflate(R.layout.fragment_customer_home,container,false);
        return v;
    }

}