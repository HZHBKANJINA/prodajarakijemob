package ba.sum.fsre.prodajarakije.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.squareup.picasso.Picasso;

import java.util.List;

import ba.sum.fsre.prodajarakije.R;
import ba.sum.fsre.prodajarakije.models.Product;

public class CustomerProductAdapter extends ArrayAdapter<Product> {
    Context context;
    List<Product> products;

    public CustomerProductAdapter(Context context,List<Product> products){
        super(context, R.layout.customer_product_list_item,products);
        this.context=context;
        this.products=products;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent){
        if(convertView==null){
            convertView= LayoutInflater.from(this.context).inflate(R.layout.customer_product_list_item,parent,false);
        }

        Product product=this.products.get(position);

        ImageView customerProductImage=convertView.findViewById(R.id.customer_product_image);
        TextView customerProductTitle=convertView.findViewById(R.id.customer_product_title);
        TextView customerProductPrice=convertView.findViewById(R.id.customer_product_price);

        Picasso.get().load(product.getImage()).into(customerProductImage);
        customerProductTitle.setText(product.getTitle());
        customerProductPrice.setText(String.valueOf(product.getPrice()));

        return convertView;
    }
}
