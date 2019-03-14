package Services;
import java.util.List;
import Entities.Item;
public class ItemService {
    public ItemService() {
    }
    public void updateItem(Item item){
    }
    public Item save(Item item) {
        return item;
    }
    public void delete(Item item) {
    }
    public List<Item> findAllReverseOrder() {
        return null;
    }
    public Item findOneById(Long id) {
        return null;
    }
    public void changeAllOwndItemsName(String userName, String newName) {
       /* List<Item> items = findByuserName(userName);
        for(int i=0; i<items.size(); i++){
            items.get(i).setUserName(newName);
        }
        items = findByusers(userName);
        for(int i=0; i<items.size(); i++){
            items.get(i).getUsers().set(items.get(i).getUsers().indexOf(userName), newName);
            if(items.get(i).getAcceptedUser().equals(userName))
                items.get(i).setAcceptedUser(newName);
        }
*/
    }
    public void deleteUserLinks(String userName) {
/*
        for(int i=0; i<items.size(); i++){
            delete(items.get(i));
        }
        items = findByusers(userName);
        for(int i=0; i<items.size(); i++){
            items.get(i).getUsers().remove(userName)
        List<Item> items = findByuserName(userName);;
            if(items.get(i).getAcceptedUser().equals(userName))
                items.get(i).setAcceptedUser("");
        }
*/
    }
    public List<Item> findByOwner(String userName) {
       /* List<Item> items = repository.findByuserName(userName);
        for(int i = items.size()-1; i>=0; i--) {
            String a = items.get(i).getAuthorized();
            if(!a.equals("") && !a.equals(userName))
                items.remove(i);
        }
        return items;
*/
        return null;
    }
    public List<Item> findByusersNameInQs(String name) {
       /* List<Item> items = repository.findByusers(user);
        for(int i = items.size()-1; i>=0; i--) {
            String a = items.get(i).getAuthorized();
            if(!a.equals("") && !a.equals(user))
                items.remove(i);
        }
        return items;*/
        return null;
    }
    public List<Item> findByTextSearch(String itemname, String description) {
        //return repository.findByItemNameContainsIgnoreCaseOrDescriptionContainsIgnoreCase(itemname, description);
        return null;
    }
    public List<Item> findByZipcodesAndTags(List<String> zipcode, List<String> tag) {
        return null;
    }
}