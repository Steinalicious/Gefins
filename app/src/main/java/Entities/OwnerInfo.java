package Entities;

import org.json.JSONException;
import org.json.JSONObject;

public class OwnerInfo {
    String id;
    String username;
    String email;
    String location;
    String phone;
    String zip;
    String stars;
    String star_num;

    public OwnerInfo(JSONObject ownerinfo) {
        try {
            this.id = ownerinfo.getString("id");
            this.username = ownerinfo.getString("username");
            this.email = ownerinfo.getString("email");
            this.location = ownerinfo.getString("location");
            this.phone = ownerinfo.getString("phone");
            this.zip = ownerinfo.getString("zip");
            this.stars = ownerinfo.getString("stars");
            this.star_num = ownerinfo.getString("star_num");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getStars() {
        return stars;
    }

    public void setStars(String stars) {
        this.stars = stars;
    }

    public String getStar_num() {
        return star_num;
    }

    public void setStar_num(String star_num) {
        this.star_num = star_num;
    }
}
