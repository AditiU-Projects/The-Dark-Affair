import java.util.ArrayList;
// Holds information about the player
public class Person {
    
    private String name;
    private int health;
    private boolean gender;
    private ArrayList<String> inventory = new ArrayList<String>();
    
    //Constructor 
    public Person(String name, boolean gender){
        this.name = name;
        this.gender = gender;
        health = 100;
    }
    
    public String getName(){
        return name;
    }
    
    public boolean getGender(){
        return gender;
    }
    //Uses an array list to manage inventory 
    public ArrayList<String> getInventory(){
        return inventory;
    }
    
    public void addInventory(String item){
        inventory.add(item);
    }
    
    public void removeInventory(int num) {
        inventory.remove(num);
    }
}