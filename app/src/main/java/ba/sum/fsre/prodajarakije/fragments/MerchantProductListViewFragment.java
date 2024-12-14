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

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import ba.sum.fsre.prodajarakije.R;
import ba.sum.fsre.prodajarakije.adapters.MerchantProductAdapter;
import ba.sum.fsre.prodajarakije.models.Merchant;
import ba.sum.fsre.prodajarakije.models.Product;

public class MerchantProductListViewFragment extends Fragment {

    private FirebaseFirestore db;
    private ListView merchantProductListView;
    private MerchantProductAdapter merchantProductAdapter;

    private List<Product> products;

    public MerchantProductListViewFragment(){
        super();
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View v=inflater.inflate(R.layout.fragment_merchant_product_list_view,container,false);

        merchantProductListView=v.findViewById(R.id.merchant_product_list_view);
        db=FirebaseFirestore.getInstance();
        products=new ArrayList<>();
        merchantProductAdapter=new MerchantProductAdapter(v.getContext(),products);
        merchantProductListView.setAdapter(merchantProductAdapter);
        getProducts();
        return v;
    }

    private void getProducts(){
        db.collection("products").get().addOnCompleteListener(task -> {
           if(task.isSuccessful()&&task.getResult()!=null){
               products.clear();
               QuerySnapshot documents=task.getResult();
               for(DocumentSnapshot document : documents){
                   String productImage=document.getString("image");
                   String productTitle=document.getString("title");
                   int productQuantity=document.getLong("quantity").intValue();
                   Merchant merchant = null;

                   Product product=new Product(productImage,productTitle,null,productQuantity,merchant);
                   products.add(product);
               }
                merchantProductAdapter.notifyDataSetChanged();
           }
        });
    }
}