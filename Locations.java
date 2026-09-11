import java.util.ArrayList;

public class Locations {
    private String name;
    private boolean canVisit;
    private String requirement;
    private ArrayList<String> inventory;

    // Constructor 
    public Locations(String name,  boolean canVisit, String requirement) {
        this.name = name;
        this.canVisit = canVisit;
        this.inventory = new ArrayList<>(); // List of items in this location
        this.requirement = requirement;
    }
    
    // Getters
    public String getName() {
        return name;
    }


    public ArrayList<String> getInventory() {
        return inventory;
    }
    
    public boolean getVisit(){
        return canVisit;
    }


    // Add item to inventory 
    public void addItem(String item) {
        inventory.add(item);
    }

    // Remove an item from the inventory 
    public void removeItem(int item) {
        inventory.remove(item);
    }
    
    // Get item
    public String getItem(int num) {
        return inventory.get(num);
    }
    // Gets the required item to unlock a location 
    public String getReq(){
        return requirement;
    }
    
    public void setUnlocked(boolean canVisit){
        this.canVisit = canVisit;
    }
    
    // Display inventory
    public void displayInventory() {
        System.out.println("Items in " + name + ":");
        if (inventory.isEmpty()) {
            System.out.println("No items available.");
        } else {
            for (String item : inventory) {
                System.out.println("- " + item);
            }
        }

    }

}