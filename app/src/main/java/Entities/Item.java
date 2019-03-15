package Entities;
import java.util.ArrayList;
import java.util.List;
public class Item {
    private String id;
    private String owner;
    private String acceptedUser;
    private String description;
    private String location;
    private String generalLocation;
    private String phone;
    private String name;
    private String email;
    private String zipcode;
    private String category;
    private String rated;
    private List<String> users;
    private String messenger;
    private String img;
    // Notice the empty constructor, because we need to be able to create an empty PostitNote to add
    // to our model so we can use it with our form
    public Item() {

    }
    public Item(String owner, String description,
                String location, String phone, String name,
                String email, String zipcode, String category){
        this.id = "0";
        this.owner=owner;
        this.description = description;
        this.location = location;
        this.generalLocation = "general Location";
        this.phone = phone;
        this.name = name;
        this.email = email;
        this.zipcode = zipcode;
        this.category = category;
        this.rated = "0";
        this.acceptedUser = "0";
        this.users = new ArrayList<>();
        this.messenger="";
        this.img="";

    }
    public Item(String id,
                String owner,
                String acceptedUser,
                String description,
                String location,
                String generalLocation,
                String phone,
                String name,
                String email,
                String zipcode,
                List<String> users,
                String category,
                String rated) {
        this.id = id;
        this.owner=owner;
        this.description = description;
        this.location = location;
        this.generalLocation = generalLocation;
        this.phone = phone;
        this.name = name;
        this.email = email;
        this.zipcode = zipcode;
        this.category = category;
        this.rated = rated;
        this.acceptedUser = acceptedUser;
        this.users = users;
        this.messenger=messenger;
        this.img=img;
    }



    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
    public String getMessenger() {
        return messenger;
    }
    public void setMessenger(String m){
        this.messenger=m;
    }
    public void addMessage(String sender, String message){
        this.messenger=this.messenger+"/n"+"/n"+sender+"/n"+message;
    }
    public String getId() {
        return id;
    }
    public void remove(String userName) {
        remove(userName);
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getAcceptedUser() {
        return acceptedUser;
    }
    public void setAcceptedUser(String acceptedUser) {
        this.acceptedUser = acceptedUser;
    }
    public String getOwner() {
        return owner;
    }
    public void setOwner(String owner) {
        this.owner = owner;
    }
    public List<String> getUsers() {
        return users;
    }
    public void addUsers(String user) {
        this.users.add(user);
    }
    public void removeUsers(String user) {
        this.users.remove(user);
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public String getLocation() {
        return location;
    }
    public void setLocation(String location) {
        this.location = location;
    }
    public String getGeneralLocation() {
        return generalLocation;
    }
    public void setGeneralLocation(String generalLocation) {
        this.generalLocation = generalLocation;
    }
    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
    public String getItemName() {
        return name;
    }
    public void setItemName(String itemName) {
        this.name = itemName;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getZipcode() {
        return zipcode;
    }
    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }
    public String getCategory() {
        return category;
    }
    public void setCategory(String category) {
        this.category = category;
    }
    public String getRated() {
        return rated;
    }
    public void setRated(String rated) {
        this.rated = rated;
    }
}