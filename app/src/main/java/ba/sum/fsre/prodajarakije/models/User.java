package ba.sum.fsre.prodajarakije.models;

import com.google.firebase.firestore.PropertyName;

public class User {

    private String email;
    private String userType;

    public User(){}

    public User(String email, String userType) {
        this.email = email;
        this.userType = userType;
    }

    @PropertyName("email")
    public String getEmail() {
        return email;
    }

    @PropertyName("email")
    public void setEmail(String email) {
        this.email = email;
    }

    @PropertyName("userType")
    public String getUserType() {
        return userType;
    }

    @PropertyName("userType")
    public void setUserType(String userType) {
        this.userType = userType;
    }
}
