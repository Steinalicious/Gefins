package Entities;

public class User {
    // Declare that this attribute is the id
    private Long id;
    private String userName;
    private String password;
    private String phone;
    private String email;
    private String location;
    private String zipcode;
    private int stars;
    private int starsNumber;
    //private int starsnumber;
    // Notice the empty constructor, because we need to be able to create an empty PostitNote to add
    // to our model so we can use it with our form
    public User() {
    }
    public User(String userName, String password, String phone, String email,
                String location, String zipcode, int stars, int starNumber) {
        this.userName = userName;
        this.password = password;
        this.phone = phone;
        this.email = email;
        this.location = location;
        this.zipcode = zipcode;
        this.stars = stars;
        this.starsNumber = starNumber;
    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
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
    public Integer getStars() {
        return stars;
    }
    public void setStars(Integer stars) {
        this.stars = stars;
    }
    public int getStarsnumber() {
        return starsNumber;
    }
    public void setStarsnumber(int starsnumber) {
        this.starsNumber = starsnumber;
    }
    public void rate(int stars) {
        this.starsNumber++;
        this.stars+=stars;
    }
    public int getratings() {
        if(this.starsNumber>=1)
            return (int)(Math.round(this.stars*2.0/this.starsNumber));
        return -1;
    }
}