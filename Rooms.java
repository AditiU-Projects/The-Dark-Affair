//This class contains the information for the rooms

public class Rooms {
    private String name;
    private boolean isUnlocked; //States whether the rooms are accessible 
    private String boss;
    private String requiredItem; 

    // Constructor
    public Rooms(String name, boolean isUnlocked, String boss, String requiredItem) {
        this.name = name;
        this.isUnlocked = isUnlocked;
        this.boss = boss;
        this.requiredItem = requiredItem;
    }

    // Getters and Setters
    public String getName() {
        return name;
    }
    
    public String getBoss(){
        return boss; 
    }

    public boolean isUnlocked() {
        return isUnlocked;
    }

    public void setUnlocked(boolean isUnlocked) {
        this.isUnlocked = isUnlocked;
    }

    public String getRequiredItem() {
        return requiredItem;
    }
}