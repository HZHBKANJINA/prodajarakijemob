package ba.sum.fsre.prodajarakije.models;
import com.google.firebase.firestore.PropertyName;

public class CartItem {
    String image;
    String title;
    Integer price;
    Integer quantity;

    public CartItem(){}

    public CartItem(String image, String title, Integer price, Integer quantity){
        this.image = image;
        this.title = title;
        this.price = price;
        this.quantity = quantity;
    }

    @PropertyName("image")
    public String getImage(){
        return image;
    }

    @PropertyName("image")
    public void setImage(String image){
        this.image = image;
    }

    @PropertyName("title")
    public String getTitle(){
        return title;
    }

    @PropertyName("title")
    public void setTitle(String title){
        this.title = title;
    }

    @PropertyName("price")
    public Integer getPrice(){
        return price;
    }

    @PropertyName("price")
    public void setPrice(Integer price){
        this.price = price;
    }

    @PropertyName("quantity")
    public Integer getQuantity(){
        return quantity;
    }

    @PropertyName("quantity")
    public void setQuantity(Integer quantity){
        this.quantity = quantity;
    }
}
