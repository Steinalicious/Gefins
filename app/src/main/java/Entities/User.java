package Entities;

import org.json.JSONObject;

import java.util.UUID;

public class User {


    // Declare that this attribute is the id
    private String mId;
    private String id;
    private String userName;
    private String password;
    private String phone;
    private String email;
    private String location;
    private String zipcode;
    private String stars;
    private String starsNumber;

    // Notice the empty constructor, because we need to be able to create an empty PostitNote to add
    // to our model so we can use it with our form
    public User() {
        this.mId = UUID.randomUUID().toString();
        this.id = "0";
        this.userName = "0";
        this.password = "0";
        this.phone = "0";
        this.email = "0";
        this.location = "0";
        this.zipcode = "0";
        this.stars = "0";
        this.starsNumber = "0";
    }
    public User(JSONObject json){
        try {
            this.id = json.getString("id");
            this.mId = json.getString("mId");
            this.userName = json.getString("userName");
            this.password = json.getString("password");
            this.phone = json.getString("phone");
            this.email = json.getString("email");
            this.location = json.getString("location");
            this.zipcode = json.getString("zipcode");
            this.stars = json.getString("stars");
            this.starsNumber = json.getString("starsNumber");
        } catch (Exception e){

        }
    }

    public User(String id, String userName, String password, String phone, String email,
                String location, String zipcode, String stars, String starNumber) {
        this.mId = UUID.randomUUID().toString();
        this.id = id;
        this.userName = userName;
        this.password = password;
        this.phone = phone;
        this.email = email;
        this.location = location;
        this.zipcode = zipcode;
        this.stars = stars;
        this.starsNumber = starNumber;
    }

    public String getmId() {
        return mId;
    }

    public void setmId(String mId) {
        this.mId = mId;
    }

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getLocation() {
        return location;
    }
    public void setLocation(String location) {
        this.location = location;
    }
    public String getStars() {
        return stars;
    }
    public void setStars(String stars) {
        this.stars = stars;
    }
    public String getStarsNumber() {
        return starsNumber;
    }
    public void setStarsNumber(String starsnumber) {
        this.starsNumber = starsnumber;
    }

    public void rate(int stars) {
        int a = Integer.parseInt(this.starsNumber);
        a++;
        this.starsNumber = String.valueOf(a);


        int b = Integer.parseInt(this.stars);
        b += stars;
        this.stars = String.valueOf(b);
    }

    public int getratings() {
        int a=Integer.parseInt(this.starsNumber);
        int b=Integer.parseInt(this.stars);
        if(a>=1)
            return (int)(Math.round(b*2.0/a));
        return -1;
    }
}