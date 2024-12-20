package ba.sum.fsre.prodajarakije.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import ba.sum.fsre.prodajarakije.R;
import ba.sum.fsre.prodajarakije.models.Customer;
import ba.sum.fsre.prodajarakije.models.Merchant;

public class MerchantProfileFragment extends Fragment {
    FirebaseFirestore db;
    FirebaseAuth mAuth;

    public MerchantProfileFragment(){
        super();
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View v=inflater.inflate(R.layout.fragment_merchant_profile,container,false);

        this.db=FirebaseFirestore.getInstance();
        this.mAuth=FirebaseAuth.getInstance();

        EditText merchantStoreNameTxt=v.findViewById(R.id.merchantStoreNameTxt);
        EditText merchantFirstNameTxt=v.findViewById(R.id.merchantFirstNameTxt);
        EditText merchantLastNameTxt=v.findViewById(R.id.merchantLastNameTxt);
        EditText merchantPhoneTxt=v.findViewById(R.id.merchantPhoneTxt);
        EditText merchantAddressTxt=v.findViewById(R.id.merchantAddressTxt);
        EditText merchantCityTxt=v.findViewById(R.id.merchantCityTxt);
        Button saveMerchantBtn=v.findViewById(R.id.saveMerchantBtn);

        String uid=mAuth.getCurrentUser().getUid();

        this.db.collection("merchants").document(uid).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                DocumentSnapshot documet=task.getResult();
                if(documet.exists()){
                    Merchant merchant=documet.toObject(Merchant.class);
                    merchantStoreNameTxt.setText(merchant.getStoreName());
                    merchantFirstNameTxt.setText(merchant.getFirstName());
                    merchantLastNameTxt.setText(merchant.getLastName());
                    merchantPhoneTxt.setText(merchant.getPhone());
                    merchantAddressTxt.setText(merchant.getAddress());
                    merchantCityTxt.setText(merchant.getCity());
                }
            }
        });

        saveMerchantBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String storeName=merchantStoreNameTxt.getText().toString();
                String firstName=merchantFirstNameTxt.getText().toString();
                String lastName=merchantLastNameTxt.getText().toString();
                String phone=merchantPhoneTxt.getText().toString();
                String address=merchantAddressTxt.getText().toString();
                String city=merchantCityTxt.getText().toString();

                Merchant newMerchant=new Merchant(storeName,firstName,lastName,phone,address,city);
                db.collection("merchants").document(uid).set(newMerchant);
            }
        });

        return v;
    }
}