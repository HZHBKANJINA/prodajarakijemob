package ba.sum.fsre.prodajarakije.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
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
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import ba.sum.fsre.prodajarakije.R;
import ba.sum.fsre.prodajarakije.models.User;

public class RegisterFragment extends Fragment {

    FirebaseFirestore db;
    FirebaseAuth mAuth;

    public RegisterFragment(){
        super();
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View v=inflater.inflate(R.layout.fragment_register,container,false);

        this.mAuth=FirebaseAuth.getInstance();
        this.db=FirebaseFirestore.getInstance();

        Spinner userTypeSpinner=v.findViewById(R.id.userTypeSpinner);
        EditText emailTxt=v.findViewById(R.id.registerEmailTxt);
        EditText passwordTxt=v.findViewById(R.id.registerPasswordTxt);
        EditText passwordConfirmTxt=v.findViewById(R.id.registerConfirmPasswordTxt);

        Button registerBtn=v.findViewById(R.id.registerBtn);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.user_type_options, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        userTypeSpinner.setAdapter(adapter);



        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email=emailTxt.getText().toString();
                String password=passwordTxt.getText().toString();
                String passwordConfirm=passwordConfirmTxt.getText().toString();
                String userType=userTypeSpinner.getSelectedItem().toString();

                if(password.equals(passwordConfirm)){
                    mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                          if(task.isSuccessful()){
                              String uid = mAuth.getCurrentUser().getUid();
                              User newUser = new User(email, userType);
                              db.collection("users").document(uid).set(newUser);
                              Toast.makeText(v.getContext(),"Registracija uspješna",Toast.LENGTH_SHORT).show();
                          }else{
                                Toast.makeText(v.getContext(),"Registracija neuspješna",Toast.LENGTH_SHORT).show();
                          }
                        }
                    });
                }else {
                    Toast.makeText(v.getContext(),"Lozinke se ne podudaraju",Toast.LENGTH_SHORT).show();
                }
            }
        });

        return v;
    }
}