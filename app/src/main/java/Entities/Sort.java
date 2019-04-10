package Entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Sort implements Serializable {

    private static final long serialVersionUID = 1L;
    private List<String> category;
    private List<String> location;

    public Sort(){
        category = new ArrayList<String>();
        location = new ArrayList<String>();
    }

    public List<String> getCategory() {
        return category;
    }

    public List<String> getLocation() {
        return location;
    }

    public void setCategory(List <String> a){
        category = a;
    }

    public void setLocation(List <String> a){
        location = a;
    }

    public String toString()
    {
        String a ="";
        if (!category.isEmpty()) {
            a = a + "Valdir flokkar:\n";
            for(int i=0; i<category.size();i++){
                a = a + category.get(i) + "\n";
            }
            a = a +"\n";
        }
        if (!location.isEmpty()) {
            a = a + "Valdar staðsetningar:\n";
            for(int i=0; i<location.size();i++){
                a = a + location.get(i) + "\n";
            }

        }

        return a;
    }
    public ArrayList<String> getALL(){
        ArrayList<String> a=new ArrayList<>();
        if(!category.isEmpty())
            for(int i=0; i<category.size(); i++)
                a.add(category.get(i));

        if(!location.isEmpty())
            for(int i=0; i<location.size(); i++)
                a.add(location.get(i));


        return a;

    }
    public boolean isEmpty(){
        if (category.isEmpty() && location.isEmpty())
            return true;
        return false;
    }

}
