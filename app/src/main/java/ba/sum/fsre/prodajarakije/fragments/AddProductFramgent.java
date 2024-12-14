package ba.sum.fsre.prodajarakije.fragments;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import ba.sum.fsre.prodajarakije.R;
import ba.sum.fsre.prodajarakije.models.Merchant;
import ba.sum.fsre.prodajarakije.models.Product;

public class AddProductFramgent extends Fragment {

    private ImageView addProductImage;
    EditText addProductTitleTxt, addProductPriceTxt, addProductQuantityTxt;
    Button selectProductImageBtn, addProductBtn;

    private Uri imageUri;

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseAuth mAuth = FirebaseAuth.getInstance();

    final private DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Images");
    final private StorageReference storageReference = FirebaseStorage.getInstance().getReference();

    public AddProductFramgent() {
        super();
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_add_product, container, false);

        addProductImage = v.findViewById(R.id.addProductImage);
        addProductTitleTxt = v.findViewById(R.id.addProductTitleTxt);
        addProductPriceTxt = v.findViewById(R.id.addProductPriceTxt);
        addProductQuantityTxt = v.findViewById(R.id.addProductQuantityTxt);
        selectProductImageBtn = v.findViewById(R.id.selectProductImageBtn);
        addProductBtn = v.findViewById(R.id.addProductBtn);

        String uid = mAuth.getCurrentUser().getUid();

        ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK) {
                            Intent data = result.getData();
                            imageUri = data.getData();
                            addProductImage.setImageURI(imageUri);
                        } else {
                            Toast.makeText(v.getContext(), "Slika nije odabrana", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );

        selectProductImageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                activityResultLauncher.launch(intent);
            }
        });

        addProductBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = addProductTitleTxt.getText().toString().trim();
                String price = addProductPriceTxt.getText().toString().trim();
                String quantity = addProductQuantityTxt.getText().toString().trim();

                if (imageUri == null || title.isEmpty() || price.isEmpty() || quantity.isEmpty()) {
                    Toast.makeText(v.getContext(), "Sva polja moraju biti popunjena", Toast.LENGTH_SHORT).show();
                    return;
                }

                String uid = mAuth.getCurrentUser().getUid(); // Dohvaćanje trenutnog korisnika

                // Dohvati Merchant podatke iz Firestore
                db.collection("merchants").document(uid).get().addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        String storeName = documentSnapshot.getString("storeName");

                        Merchant merchant = new Merchant(storeName, "", "", "", "", "");

                        try {
                            Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), imageUri);
                            bitmap = resizeAndRotateImage(bitmap, addProductImage);

                            ByteArrayOutputStream baos = new ByteArrayOutputStream();
                            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                            byte[] data = baos.toByteArray();

                            StorageReference fileRef = storageReference.child("Images/" + System.currentTimeMillis() + ".jpg");
                            fileRef.putBytes(data).addOnSuccessListener(taskSnapshot -> {
                                fileRef.getDownloadUrl().addOnSuccessListener(uri -> {
                                    String imageUrl = uri.toString();

                                    Product product = new Product(imageUrl, title, Integer.parseInt(price), Integer.parseInt(quantity), merchant);

                                    db.collection("products").add(product).addOnSuccessListener(documentReference -> {
                                        Toast.makeText(v.getContext(), "Proizvod uspješno dodan", Toast.LENGTH_SHORT).show();
                                    }).addOnFailureListener(e -> {
                                        Toast.makeText(v.getContext(), "Greška pri dodavanju proizvoda", Toast.LENGTH_SHORT).show();
                                    });
                                }).addOnFailureListener(e -> {
                                    Toast.makeText(v.getContext(), "Greška pri dobivanju URL-a slike", Toast.LENGTH_SHORT).show();
                                });
                            }).addOnFailureListener(e -> {
                                Toast.makeText(v.getContext(), "Greška pri spremanju slike", Toast.LENGTH_SHORT).show();
                            });
                        } catch (IOException e) {
                            e.printStackTrace();
                            Toast.makeText(v.getContext(), "Greška pri obradi slike", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(v.getContext(), "Merchant podaci nisu pronađeni.", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(e -> {
                    Toast.makeText(v.getContext(), "Greška pri dohvaćanju Merchant podataka.", Toast.LENGTH_SHORT).show();
                });
            }
        });


        return v;
    }
    private Bitmap resizeAndRotateImage(Bitmap bitmap, ImageView imageView) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        int imageViewWidth = imageView.getWidth();
        int imageViewHeight = imageView.getHeight();

        Matrix matrix = new Matrix();
        if (width > height) {
            matrix.postRotate(90);
        }

        Bitmap resizedBitmap = Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true);
        return Bitmap.createScaledBitmap(resizedBitmap, imageViewWidth, imageViewHeight, true);
    }
}
