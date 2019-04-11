package Entities;
import org.json.JSONObject;

import android.util.Log;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
public class Item {
    private String id;
    private String owner;
    private String ownerID;
    private String acceptedUser;
    private String description;
    private String location;
    private String generalLocation;
    private String phone;
    private String name;
    private String email;
    private String zip;
    private String category;
    private String rated;
    private String users;
    private String messenger;
    private String img;
    private QueueInfo queueInfo;
    private OwnerInfo ownerInfo;
    private MessageInfo messageInfo;
    // Notice the empty constructor, because we need to be able to create an empty PostitNote to add
    // to our model so we can use it with our form
    public Item() {

    }
    public Item(String owner,String ownerID, String description,
                String location, String phone, String name,
                String email, String zip, String category, String img){

        this.id = "0";
        this.owner=owner;
        this.ownerID=ownerID;
        this.description = description;
        this.location = location;
        this.generalLocation = "general Location";
        this.phone = phone;
        this.name = name;
        this.email = email;
        this.zip = zip;
        this.category = category;
        this.rated = "0";
        this.acceptedUser = "0";
        this.users = "0";
        this.messenger="0";
        if(img == null) {
            this.img = "0";
        }else {
            this.img = img;
        }
    }

    public MessageInfo getMessageInfo() {
        return messageInfo;
    }

    public void setMessageInfo(MessageInfo messageInfo) {
        this.messageInfo = messageInfo;
    }

    public Item(JSONObject item) {
        try {
            this.id = item.getString("id");
            this.owner =  item.getString("owner");
            this.description = item.getString("description");
            this.location =  item.getString("location");
            this.generalLocation =  item.getString("general_location");
            this.phone =  item.getString("phone");
            this.name =  item.getString("name");
            this.email =  item.getString("email");
            this.zip =  item.getString("zip");
            this.category =  item.getString("category");
            this.rated =  item.getString("rated");
            this.acceptedUser =  item.getString("accepted_user");
            this.users =  item.getString("queue");
            this.messenger =  item.getString("messenger");
            this.img = item.getString("img");
            this.ownerID=item.getString("owner_id");
            if(item.has("queueInfo")){
                QueueInfo queueInfo = new QueueInfo(item.getJSONObject("queueInfo"));
                this.queueInfo = queueInfo;
            } else {
                QueueInfo queueInfo = new QueueInfo();
                this.queueInfo = queueInfo;
            }
                OwnerInfo ownerInfo = new OwnerInfo(item.getJSONObject("ownerInfo"));
            this.ownerInfo = ownerInfo;

            if(item.has("messageInfo")){
                MessageInfo messageInfo = new MessageInfo(item.getJSONObject("messageInfo"));
                this.messageInfo = messageInfo;
            } else {
                MessageInfo messageInfo = new MessageInfo();
                this.messageInfo = messageInfo;
            }

        } catch (Exception e){
            e.printStackTrace();
        }
    }



    public String getOwnerID() {
        return ownerID;
    }

    public void setOwnerID(String ownerID) {
        this.ownerID = ownerID;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getMessenger() {
        return this.messenger;
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

    public String getUsers() {
          return users;
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
        return zip;
    }

    public void setZipcode(String zip) {
        this.zip = zip;
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

    public QueueInfo getQueueInfo() {
        return queueInfo;
    }

    public void setQueueInfo(QueueInfo queueInfo) {
        this.queueInfo = queueInfo;
    }

    public OwnerInfo getOwnerInfo() {
        return ownerInfo;
    }

    public void setOwnerInfo(OwnerInfo ownerInfo) {
        this.ownerInfo = ownerInfo;
    }

    @Override
    public String toString() {
        return "Item{" +
                "id='" + id + '\'' +
                ", owner='" + owner + '\'' +
                ", acceptedUser='" + acceptedUser + '\'' +
                ", description='" + description + '\'' +
                ", location='" + location + '\'' +
                ", generalLocation='" + generalLocation + '\'' +
                ", phone='" + phone + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", zipcode='" + zip + '\'' +
                ", category='" + category + '\'' +
                ", rated='" + rated + '\'' +
                ", users=" + users +
                ", messenger='" + messenger + '\'' +
                ", img='" + img + '\'' +
                '}';
    }
}