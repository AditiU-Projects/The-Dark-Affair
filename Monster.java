//Holds information about the monster 
public class Monster extends Person {
    
    private String type;
    private int health;
    
    public Monster(String name, boolean gender, String type, int health){
        super(name, gender);
        this.type=type;
        this.health=health;
        
    }
    
    public String getType(){
        return type;
    }
    
    public int getHealth(){
        return health; 
    }
}