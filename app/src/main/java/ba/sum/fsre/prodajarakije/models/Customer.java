package ba.sum.fsre.prodajarakije.models;
import com.google.firebase.firestore.PropertyName;

public class Customer {

    private String firstName;
    private String lastName;
    private String phone;
    private String address;
    private String city;

    public Customer(){}

    public Customer(String firstName, String lastName, String phone, String address, String city) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.address = address;
        this.city = city;
    }

    @PropertyName("firstName")
    public String getFirstName() {
        return firstName;
    }

    @PropertyName("firstName")
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @PropertyName("lastName")
    public String getLastName() {
        return lastName;
    }

    @PropertyName("lastName")
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @PropertyName("phone")
    public String getPhone() {
        return phone;
    }

    @PropertyName("phone")
    public void setPhone(String phone) {
        this.phone = phone;
    }

    @PropertyName("address")
    public String getAddress() {
        return address;
    }

    @PropertyName("address")

    public void setAddress(String address) {
        this.address = address;
    }

    @PropertyName("city")
    public String getCity() {
        return city;
    }

    @PropertyName("city")
    public void setCity(String city) {
        this.city = city;
    }

}
