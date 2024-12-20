package ba.sum.fsre.prodajarakije.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Locale;

import ba.sum.fsre.prodajarakije.R;
import ba.sum.fsre.prodajarakije.models.CartItem;
import ba.sum.fsre.prodajarakije.models.Product;

public class CustomerCartAdapter extends ArrayAdapter<CartItem> {

    private final Context context;
    private final List<CartItem> cartItems;
    private final FirebaseAuth mAuth;
    private final FirebaseFirestore db;
    private final TextView totalPriceTextView;


    public CustomerCartAdapter(Context context,List<CartItem> cartItems,TextView totalPriceTextView){
        super(context, R.layout.customer_cart_list_item,cartItems);
        this.context = context;
        this.cartItems = cartItems;
        this.mAuth = FirebaseAuth.getInstance();
        this.db = FirebaseFirestore.getInstance();
        this.totalPriceTextView = totalPriceTextView;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent){
        if (convertView==null){
            convertView= LayoutInflater.from(context).inflate(R.layout.customer_cart_list_item,parent,false);
        }
        CartItem cartItem = cartItems.get(position);


        ImageView customerCartItemImage=convertView.findViewById(R.id.customer_cart_item_image);
        TextView customerCartItemTitle=convertView.findViewById(R.id.customer_cart_item_title);
        TextView customerCartItemPrice=convertView.findViewById(R.id.customer_cart_item_price);
        Button customerCartItemDecrease=convertView.findViewById(R.id.customer_cart_product_quantity_decrease);
        EditText customerCartItemQuantityInput=convertView.findViewById(R.id.customer_cart_product_quantity_input);
        Button customerCartItemIncrease=convertView.findViewById(R.id.customer_cart_product_quantity_increase);

        Picasso.get().load(cartItem.getImage()).into(customerCartItemImage);
        customerCartItemTitle.setText(cartItem.getTitle());
        customerCartItemPrice.setText(cartItem.getPrice() + " KM");

        customerCartItemQuantityInput.setText(String.valueOf(cartItem.getQuantity()));

        customerCartItemDecrease.setOnClickListener(v->{
            int currentQuantity=Integer.parseInt(customerCartItemQuantityInput.getText().toString());
            if(currentQuantity>1){
                currentQuantity--;
                customerCartItemQuantityInput.setText(String.valueOf(currentQuantity));
                updateQuantity(cartItem, currentQuantity);
            }else{
                removeItem(cartItem,position);
            }
        });

        customerCartItemIncrease.setOnClickListener(v->{
            int currentQuantity=Integer.parseInt(customerCartItemQuantityInput.getText().toString());
            currentQuantity++;
            customerCartItemQuantityInput.setText(String.valueOf(currentQuantity));
            updateQuantity(cartItem, currentQuantity);
        });

        return convertView;
    }

    private void updateQuantity(CartItem cartItem, int quantity) {
        String uid = mAuth.getCurrentUser().getUid();
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("cartItems")
                .whereEqualTo("uid", uid)
                .whereEqualTo("title", cartItem.getTitle())
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    if (!queryDocumentSnapshots.isEmpty()) {
                        String documentId = queryDocumentSnapshots.getDocuments().get(0).getId();
                        db.collection("cartItems")
                                .document(documentId)
                                .update("quantity", quantity);
                    }
                });
    }

    private void removeItem(CartItem cartItem, int position) {
        String uid = mAuth.getCurrentUser().getUid();

        db.collection("cartItems")
                .whereEqualTo("uid", uid)
                .whereEqualTo("title", cartItem.getTitle())
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    if (!queryDocumentSnapshots.isEmpty()) {
                        String documentId = queryDocumentSnapshots.getDocuments().get(0).getId();
                        db.collection("cartItems")
                                .document(documentId)
                                .delete()
                                .addOnSuccessListener(aVoid -> {
                                    cartItems.remove(position);
                                    notifyDataSetChanged();
                                });
                    }
                });
    }

}
