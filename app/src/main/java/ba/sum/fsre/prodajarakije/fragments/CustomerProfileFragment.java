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

public class CustomerProfileFragment extends Fragment {

    FirebaseFirestore db;
    FirebaseAuth mAuth;

    public CustomerProfileFragment(){
        super();
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View v=inflater.inflate(R.layout.fragment_customer_profile,container,false);

        this.db=FirebaseFirestore.getInstance();
        this.mAuth=FirebaseAuth.getInstance();

        EditText customerFirstNameTxt=v.findViewById(R.id.customerFirstNameTxt);
        EditText customerLastNameTxt=v.findViewById(R.id.customerLastNameTxt);
        EditText customerPhoneTxt=v.findViewById(R.id.customerPhoneTxt);
        EditText customerAddressTxt=v.findViewById(R.id.customerAddressTxt);
        EditText customerCityTxt=v.findViewById(R.id.customerCityTxt);
        Button saveCustomerBtn=v.findViewById(R.id.saveCustomerBtn);

        String uid=mAuth.getCurrentUser().getUid();

        this.db.collection("customers").document(uid).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                DocumentSnapshot document=task.getResult();
                if(document.exists()){
                    Customer customer=document.toObject(Customer.class);
                    customerFirstNameTxt.setText(customer.getFirstName());
                    customerLastNameTxt.setText(customer.getLastName());
                    customerPhoneTxt.setText(customer.getPhone());
                    customerAddressTxt.setText(customer.getAddress());
                    customerCityTxt.setText(customer.getCity());
                }
            }
        });

        saveCustomerBtn.setOnClickListener(view->{
            String firstName=customerFirstNameTxt.getText().toString();
            String lastName=customerLastNameTxt.getText().toString();
            String phone=customerPhoneTxt.getText().toString();
            String address=customerAddressTxt.getText().toString();
            String city=customerCityTxt.getText().toString();

            Customer newCustomer=new Customer(firstName,lastName,phone,address,city);
            db.collection("customers").document(uid).set(newCustomer);
        });

        return v;
    }

}