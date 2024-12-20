package ba.sum.fsre.prodajarakije.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

import ba.sum.fsre.prodajarakije.R;
import ba.sum.fsre.prodajarakije.adapters.CustomerCartAdapter;
import ba.sum.fsre.prodajarakije.adapters.CustomerHomeProductAdapter;
import ba.sum.fsre.prodajarakije.models.CartItem;
import ba.sum.fsre.prodajarakije.models.Product;

public class CustomerCartListViewFragment extends Fragment {

    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    private List<CartItem> cartItems;
    private CustomerCartAdapter cartAdapter;


    public CustomerCartListViewFragment(){
        super();
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.fragment_customer_cart_list_view, container, false);

        ListView customerCartListView = v.findViewById(R.id.customer_cart_list_view);
        TextView totalPriceTextView = v.findViewById(R.id.customer_cart_total_price);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        cartItems = new ArrayList<>();

        cartAdapter = new CustomerCartAdapter(getContext(), cartItems, totalPriceTextView);
        customerCartListView.setAdapter(cartAdapter);

        fetchCartItems();

        return v;
    }

    private void fetchCartItems() {
        String uid = mAuth.getCurrentUser().getUid();

        db.collection("cartItems")
                .whereEqualTo("userId", uid)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful() && task.getResult() != null) {
                        for (var document : task.getResult()) {
                            String image = document.getString("image");
                            String title = document.getString("title");
                            int price = document.getLong("price").intValue();
                            int quantity = document.getLong("quantity").intValue();

                            CartItem cartItem = new CartItem(image, title, price, quantity);
                            cartItems.add(cartItem);
                        }
                        cartAdapter.notifyDataSetChanged();
                    }
                });
    }

}