package ba.sum.fsre.prodajarakije.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.squareup.picasso.Picasso;

import java.util.List;

import ba.sum.fsre.prodajarakije.R;
import ba.sum.fsre.prodajarakije.models.Product;

public class MerchantProductAdapter extends ArrayAdapter<Product> {
    Context context;
    List<Product> products;

    public MerchantProductAdapter(Context context,List<Product> products){
        super(context, R.layout.merchant_product_list_item,products);
        this.context=context;
        this.products=products;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent){
        if(convertView==null){
            convertView= LayoutInflater.from(this.context).inflate(R.layout.merchant_product_list_item,parent,false);
        }
        Product product=this.products.get(position);

        ImageView merchantProductImage=convertView.findViewById(R.id.merchant_product_image);
        TextView merchantProductTitle=convertView.findViewById(R.id.merchant_product_title);
        TextView merchantProductQuantity=convertView.findViewById(R.id.merchant_product_quantity);
        ImageView merchantProductEdit=convertView.findViewById(R.id.merchant_product_edit);


        merchantProductTitle.setText(product.getTitle());
        merchantProductQuantity.setText(String.valueOf(product.getQuantity()));
        Picasso.get().load(product.getImage()).into(merchantProductImage);

        merchantProductEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu=new PopupMenu(context,merchantProductEdit);
                popupMenu.getMenuInflater().inflate(R.menu.product_edit_menu,popupMenu.getMenu());

                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        if(item.getItemId()==R.id.merchant_product_update){
                            return true;
                        } else if (item.getItemId()==R.id.merchant_product_delete) {
                            return true;
                        }
                        return false;
                    }
                });
                popupMenu.show();
            }
        });



        return convertView;
    }
}
