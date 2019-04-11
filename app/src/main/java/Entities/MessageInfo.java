package Entities;

import org.json.JSONException;
import org.json.JSONObject;

public class MessageInfo {
    String id;
    String name;
    String message;
    String created;

    public MessageInfo(){
        this.id = "0";
        this.name = " ";
        this.message = " ";
        this.created = " ";
   }

    public MessageInfo(JSONObject messageInfo){
        try {
            this.id = messageInfo.getString("id");
            this.name = messageInfo.getString("name");
            this.message = messageInfo.getString("message");
            this.created = messageInfo.getString("created");
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }
}
