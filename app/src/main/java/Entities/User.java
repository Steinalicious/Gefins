package Entities;

import android.util.Log;

import org.json.JSONObject;

import java.io.Serializable;
import java.util.UUID;

public class User implements Serializable{

    private static final long serialVersionUID = 21L;
    // Declare that this attribute is the id
    private String mId;
    private String id;
    private String userName;
    private String password;
    private String email;
    private String stars;
    private String starsNumber;

    // Notice the empty constructor, because we need to be able to create an empty PostitNote to add
    // to our model so we can use it with our form
    public User() {
        this.mId = UUID.randomUUID().toString();
        this.id = "0";
        this.userName = "0";
        this.password = "0";
        this.email = "0";
        this.stars = "0";
        this.starsNumber = "0";
    }
    public User(JSONObject json){
        Log.d("flo",json.toString());
        try {
            this.id = json.getString("id");
            //this.mId = json.getString("mId");
            this.userName = json.getString("username");
            this.password = json.getString("password");
            this.email = json.getString("email");
            this.stars = json.getString("stars");
            this.starsNumber = json.getString("starsNumber");
        } catch (Exception e){

        }
    }

    public User(String id, String userName, String password, String email,
                String stars, String starNumber) {
        this.mId = UUID.randomUUID().toString();
        this.id = id;
        this.userName = userName;
        this.password = password;
        this.email = email;
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


    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
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

    @Override
    public String toString() {
        return "User{" +
                "mId='" + mId + '\'' +
                ", id='" + id + '\'' +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", stars='" + stars + '\'' +
                ", starsNumber='" + starsNumber + '\'' +
                '}';
    }
}