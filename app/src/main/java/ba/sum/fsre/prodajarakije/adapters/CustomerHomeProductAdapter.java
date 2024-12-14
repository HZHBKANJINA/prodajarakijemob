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

import org.w3c.dom.Text;

import java.util.List;

import ba.sum.fsre.prodajarakije.R;
import ba.sum.fsre.prodajarakije.models.Product;

public class CustomerHomeProductAdapter extends ArrayAdapter<Product> {
    Context context;
    List<Product> products;

    public CustomerHomeProductAdapter(Context context,List<Product> products){
        super(context, R.layout.customer_home_list_item,products);
        this.context=context;
        this.products=products;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent){
        if(convertView==null){
            convertView= LayoutInflater.from(this.context).inflate(R.layout.customer_home_list_item,parent,false);
        }

        Product product=this.products.get(position);

        ImageView customerHomeProductImage=convertView.findViewById(R.id.customer_home_product_image);
        TextView customerHomeProductTitle=convertView.findViewById(R.id.customer_home_product_title);
        TextView customerHomeProductStoreName=convertView.findViewById(R.id.customer_home_product_storeName);
        TextView customerHomeProductPrice=convertView.findViewById(R.id.customer_home_product_price);

        Picasso.get().load(product.getImage()).into(customerHomeProductImage);
        customerHomeProductTitle.setText(product.getTitle());
        customerHomeProductStoreName.setText(product.getMerchant().getStoreName());
        customerHomeProductPrice.setText(String.valueOf(product.getPrice()));

        return convertView;
    }
}
