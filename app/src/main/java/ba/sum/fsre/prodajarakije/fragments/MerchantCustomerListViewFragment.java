package ba.sum.fsre.prodajarakije.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;

import ba.sum.fsre.prodajarakije.R;

public class MerchantCustomerListViewFragment extends Fragment {

    public MerchantCustomerListViewFragment(){
        super();
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState){
        View v=inflater.inflate(R.layout.fragment_merchant_product_list_view,container,false);
        return v;
    }

}