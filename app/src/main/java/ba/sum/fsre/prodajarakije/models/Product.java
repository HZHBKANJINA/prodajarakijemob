package ba.sum.fsre.prodajarakije.models;

import com.google.firebase.firestore.PropertyName;

public class Product {

    private String image;
    private String title;
    private Integer price;
    private Integer quantity;
    private Merchant merchant;

    public Product(){}

    public Product(String image,String title,Integer price, Integer quantity, Merchant merchant) {
        this.image = image;
        this.title = title;
        this.price = price;
        this.quantity = quantity;
        this.merchant = merchant;
    }

    @PropertyName("image")
    public String getImage() {
        return image;
    }

    @PropertyName("image")
    public void setImage(String image) {
        this.image = image;
    }

    @PropertyName("title")
    public String getTitle() {
        return title;
    }

    @PropertyName("title")
    public void setTitle(String title) {
        this.title = title;
    }

    @PropertyName("price")
    public Integer getPrice() {
        return price;
    }

    @PropertyName("price")
    public void setPrice(Integer price) {
        this.price = price;
    }

    @PropertyName("quantity")
    public Integer getQuantity() {
        return quantity;
    }

    @PropertyName("quantity")
    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    @PropertyName("merchant")
    public Merchant getMerchant() {
        return merchant;
    }

    @PropertyName("merchant")
    public void setMerchant(Merchant merchant) {
        this.merchant = merchant;
    }

}
