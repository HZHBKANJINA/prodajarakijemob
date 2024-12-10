package ba.sum.fsre.prodajarakije.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import ba.sum.fsre.prodajarakije.CustomerActivity;
import ba.sum.fsre.prodajarakije.MerchantActivity;
import ba.sum.fsre.prodajarakije.R;

public class LoginFragment extends Fragment {
    FirebaseAuth mAuth;

    FirebaseFirestore db;


    public LoginFragment(){
        super();
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View v=inflater.inflate(R.layout.fragment_login,container,false);

        this.mAuth=FirebaseAuth.getInstance();
        this.db=FirebaseFirestore.getInstance();

        EditText loginEmailTxt=v.findViewById(R.id.loginEmailTxt);
        EditText loginPasswordTxt=v.findViewById(R.id.loginPasswordTxt);
        Button loginBtn=v.findViewById(R.id.loginBtn);
        
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email=loginEmailTxt.getText().toString().trim();
                String password=loginPasswordTxt.getText().toString().trim();
                if(email.isEmpty() || password.isEmpty()){
                    Toast.makeText(v.getContext(), "Unesite email i lozinku", Toast.LENGTH_SHORT).show();
                    return;
                }

                mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            FirebaseUser user=mAuth.getCurrentUser();
                            if(user!=null){
                                String uid=user.getUid();
                                db.collection("users").document(uid).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<DocumentSnapshot> task1) {
                                             if(task1.isSuccessful()){
                                                 DocumentSnapshot document=task1.getResult();
                                                 if(document.exists()){
                                                     String userType=document.getString("userType");
                                                     if(userType.equals("Trgovac")){
                                                         Intent intent=new Intent(v.getContext(), MerchantActivity.class);
                                                         startActivity(intent);
                                                     } else if (userType.equals("Kupac")) {
                                                         Intent intent=new Intent(v.getContext(), CustomerActivity.class);
                                                         startActivity(intent);
                                                     }
                                                 }else{
                                                        Toast.makeText(v.getContext(), "Korisnik nije pronadjen", Toast.LENGTH_SHORT).show();
                                                 }
                                             }else{
                                                    Toast.makeText(v.getContext(), "Greska prilikom prijave", Toast.LENGTH_SHORT).show();
                                             }
                                    }
                                });
                            }
                        }else{
                            Toast.makeText(getContext(), "Login failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        return v;
    }
    
}