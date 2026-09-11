import java.util.Scanner;
import java.util.ArrayList;

/* Plot is that Dracula's wife, Lady Dracula is cheating on him with Werewolf


To win you must explore all rooms and locations and find the required items to unlock them.
Then defeat all bosses to reach Lady Dracula and win the game.

*/

/* To win defeat Frank S first (his item in library). 
Then go to the room that just unlocked and find right item. 
Then defeat Mummy. Follow this process until you reach Dracula.





/* The main controller of our game 
    controlls what stage the player is on and what input the player is giving */
    
    
public class World
{
    
    // These variables help the player navigate the environment
    static Person player;
    static ArrayList<Locations> map = new ArrayList<>();
    static ArrayList<Rooms> rooms = new ArrayList<>();
    static ArrayList<String> commands = new ArrayList<>();
    static int location = 2;
    static boolean isPlaying = true;
    static boolean win = true;
    
    public static void main(String[] args) {
    // This function controls the output to the console
        Scanner input = new Scanner(System.in);
        
        // Sets the game up for the player 
        setArrays();
        startGame();
        player = characterCreation();  // Initialize player character
        draculaGift();  // Give Dracula's gifts to the player
        addBreak();
        stageFirst();
        
        // Here the player can now explore the hotel
        addBreak();
        String ans;
        while(isPlaying){
            addBreak();
            System.out.print("What would you like to do? (/help for list of commands): ");
            ans = input.nextLine();
            
            if(ans.toLowerCase().equals("/help")){
                printArray(commands);
            } else{
                checkCommand(ans);
            }
        }
        
        if(win){
            addBreak();
            System.out.println("In the end, Dracula pays you thousands of gold coins for your troubles...");
            System.out.println("As for him, Lady Dracula imprisoned him for 500 years so he learns his lesson.");
            System.out.println("The end..");
            addBreak();
        } else {
            System.out.println("You died. Restart the game..");
        }
    }
    
    // creates the world map for the player to access
    // also creates a commands list to help the player if needed
    public static void setArrays(){
        
        // Adds these locations to the map
        Locations diningHall= new Locations("Dining Hall", true, "Grey Key");
        diningHall.addItem("Silver Fork");
        diningHall.addItem("Goblet of Blood");
        diningHall.addItem("A Skull");
        map.add(diningHall);
        
        Locations garden = new Locations("Garden", false, "Werewolf Blood");
        garden.addItem("Heart on a Tree");
        garden.addItem("Blood Key");
        garden.addItem("Golden thorn");
        map.add(garden);
    
        Locations library = new Locations("Library", true, "Golden Key");
        library.addItem("Frank S. Key");
        library.addItem("Ancient Spellbook");
        library.addItem("Cursed Bookmark");
        map.add(library);
        
        // Adds rooms that the player can visit 
        Locations roomHallway = new Locations("Rooms", true, "");
        rooms.add(new Rooms("Lady Dracula's Chamber", false, "Lady Dracula","Blood Key"));
        rooms.add(new Rooms("Werewolf's Den", false, "Werewolf","Werewolf's Spell"));
        rooms.add(new Rooms("Mummy's Tomb", false, "The Mummy","Ancient Tomb"));
        rooms.add(new Rooms("Frank S. Laboratory", false, "Frank S.", "Frank S. Key"));
        map.add(roomHallway);
    
        Locations catacombs = new Locations("Catacombs", false, "Blue Key");
        catacombs.addItem("Bone Fragment");
        catacombs.addItem("Skull Lantern");
        catacombs.addItem("Ancient Tomb");
        map.add(catacombs);
    
        Locations ballRoom = new Locations("Ball Room", false, "Yellow Key");
        ballRoom.addItem("Golden Mask");
        ballRoom.addItem("Crystal Slipper");
        ballRoom.addItem("Werewolf's Spell");
        map.add(ballRoom);
        
        //adds commands the player can use
        commands.add("(M) for Map");
        commands.add("(L) to Look around");
        commands.add("(I) to open your inventory");
        commands.add("(/C) to get a combat tutorial");
        
    }
    
    //Inatilizes inventory using an array list
    //Array list contains things the player can use throughout the game 
    public static void draculaGift() {
        player.addInventory("DragonSlayer Katana");
        player.addInventory("Golden Key");
        
        System.out.println("\nDracula bestows upon you some very special gifts. \nA monster starter pack, if you will...\n");
    
        for (String item : player.getInventory()) {
            System.out.println("- " + item);
        }
    }

    // Print ArrayList<Locations>
    public static void printLocations(ArrayList<Locations> array) {
        System.out.println();
        for (int i = 0; i < array.size(); i++) {
            System.out.println((i + 1) + ". " + array.get(i).toString());
        }
    }
    
    // prints any Array required
    // Print ArrayList<String>
    public static void printArray(ArrayList<String> array) {
        System.out.println();
        for (int i = 0; i < array.size(); i++) {
            System.out.println((i + 1) + ". " + array.get(i));
        }
    }
    
    // Displays a grid-style text-based map with locked/unlocked status and clues
    public static void displayTextMap() {
        System.out.println("\nHotel Transylvania Map:");
    
        String[][] grid = {
            {"[Dining Hall]", "[Garden]", "[Library]"},
            {"[Rooms]", "[Catacombs]", "[Ball Room]"}
        };
    
        // Replace grid entries based on the current state
        for (int row = 0; row < grid.length; row++) {
            for (int col = 0; col < grid[row].length; col++) {
                int currentIndex = row * grid[row].length + col;
    
                if (currentIndex == location) {
                    // Highlight current location
                    grid[row][col] = "[  X  ]";
                } else if (currentIndex == 3) { 
                    grid[row][col] = "[Rooms]";
                }
                System.out.print(grid[row][col] + " ");
            }
            System.out.println(); // Move to the next row
        }
    }
    
    public static void checkCommand(String ans) {
        Scanner input = new Scanner(System.in);
    
        if (ans.toUpperCase().equals("M")) {
            displayTextMap();
            System.out.println("\nYou are currently at: " + map.get(location ).getName());
            System.out.print("Update your location (Enter a number 1-" + map.size() + ", or same number to stay): ");
    
            String userInput = input.nextLine();
    
            try {
                int num = Integer.parseInt(userInput); // Convert to 0-based index
    
                if (num > 0 && num <= map.size()){
                    if (num == 4) { // Handle room selection case
                        handleRoomSelection(input);
                    } else if (map.get(num - 1).getVisit()) { // Validate visitable locations
                        location = num - 1;
                        System.out.println("You moved to: " + map.get(location).getName());
                    } else {
                        System.out.println("You cannot visit this location yet.");
                        System.out.println("Would you like to check your inventory to see if you have the item needed to unlock this room? (Y/N)");
    
                        String response = input.nextLine().toUpperCase();
                        if (response.toUpperCase().equals("Y")) {
                            checkAndUnlockLocation(num-1);
                        } else {
                            System.out.println("\nYou decided not to check your inventory. The room remains locked.");
                        }
                    }
                } else {
                    System.out.println("Invalid location. Please enter a number between 1 and " + map.size() + ".");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input! Please enter a valid number.");
            }
        } else if (ans.toUpperCase().equals("L")) {
            //Accesses array list to display items in each location 
            System.out.println("\nYou are at: " + map.get(location).getName() + "\n");
            map.get(location).displayInventory();
    
            System.out.print("To take an item, enter the number (-1 to exit): ");
            String itemInput = input.nextLine();
            
            //Validates input to allow player to grab items from different locations
            try {
                int num = Integer.parseInt(itemInput) - 1;
    
                if (num >= 0 && num < map.get(location).getInventory().size() && player.getInventory().size() < 5) {
                    String item = map.get(location).getItem(num);
                    map.get(location).removeItem(num);
                    player.addInventory(item);
                    System.out.println("You have added " + item + " to your inventory.");
                } else if (num == -2) {
                    System.out.println("Exiting without taking an item.");
                } else if (player.getInventory().size() >= 5){
                    System.out.println("Inventory is at max capacity!!");
                } else {
                    System.out.println("Invalid item number.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input! Please enter a valid number.");
            }
            //Prints out player inventory 
        } else if (ans.toUpperCase().equals("I")) {
            System.out.println("This is what you have from Dracula: ");
            printArray(player.getInventory());
    
            System.out.println("Max 5 items.");
            System.out.print("Enter a number 1-5 to delete (-1 to skip): ");
            String deleteInput = input.nextLine();
            //Checks user input when deleting an item from inventory 
            try {
                int num = Integer.parseInt(deleteInput) - 1;
    
                if (num >= 0 && num < player.getInventory().size() && num != 0) {
                    player.removeInventory(num);
                    System.out.println("Item deleted.");
                } else if (num == -2) {
                    System.out.println("No item deleted.");
                } else if (num == 0) {
                    System.out.println("Don't delete your only weapon!!");
                } else {
                    System.out.println("That item does not exist!!");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input! Please enter a valid number.");
            }
            
            //Displays combat system instructions 
        } else if (ans.toUpperCase().equals("/C")) {
            System.out.println("The combat system is a turn-based system.");
            System.out.println("You have 3 moves, and so does the enemy.");
            System.out.println("If you die, you must restart.");
            System.out.println("When you feel ready, step into a room to start a fight!!");
        } else {
            System.out.println("Invalid Command!!");
        }
    }
    
    // Helper method to handle room selection logic
    private static void handleRoomSelection(Scanner input) {
        addBreak();
        System.out.println("Select your room...");
        System.out.println();
    
    //Prints "unlocked" or "locked" depending on if you can visit the room yet
        for (int i = 0; i < rooms.size(); i++) {
            Rooms room = rooms.get(i);
            System.out.println((i + 1) + ". " + room.getName() + (room.isUnlocked() ? " (Unlocked)" : " (Locked)"));
        }
    
        System.out.print(": ");
        String roomInput = input.nextLine();
    
        try {
            int ans2 = Integer.parseInt(roomInput) - 1; // Convert to 0-based index
    
            if (ans2 >= 0 && ans2 < rooms.size()) {
                if (rooms.get(ans2).isUnlocked()) {
                    System.out.println("You enter " + rooms.get(ans2).getName() + ". It's owned by " + rooms.get(ans2).getBoss() + ".");
                    combat(rooms.get(ans2));
                } else {
                    System.out.println("This room is locked.");
                    System.out.println("Would you like to check your inventory to see if you have the item needed to unlock this room? (Y/N)");
    
                    String response = input.nextLine().toUpperCase();
                    if (response.equals("Y")) {
                        checkAndUnlockRoom(ans2);
                    } else {
                        System.out.println("\nYou decided not to check your inventory. The room remains locked.");
                    }
                }
            } else {
                System.out.println("Invalid room selection.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input! Please enter a valid room number.");
        }
    }

    // Helper method to check inventory and unlock rooms
    private static void checkAndUnlockRoom(int roomIndex) {
        System.out.println("Your inventory: " + player.getInventory());
        if (player.getInventory().contains(rooms.get(roomIndex).getRequiredItem())) {
        System.out.println("You can use the " + rooms.get(roomIndex).getRequiredItem() + " to unlock the room.");
        rooms.get(roomIndex).setUnlocked(true);
        System.out.println(rooms.get(roomIndex).getName() + " is now unlocked!");
        } else {
        System.out.println("You don't have the required item (" + rooms.get(roomIndex).getRequiredItem() + ") to unlock this room.");
        System.out.println("Dracula: I am disappointed in you " + player.getName() + ". Explore the map to find the required objects to unlock the rooms.\n I am counting on you..");
        }
    }


    // checks what you need to unlock location
    private static void checkAndUnlockLocation(int roomIndex) {
        System.out.println("Your inventory: " + player.getInventory());
        if (player.getInventory().contains(map.get(roomIndex).getReq())) {
            System.out.println("You can use the " + map.get(roomIndex).getReq() + " to unlock the room.");
            map.get(roomIndex).setUnlocked(true);
            System.out.println(map.get(roomIndex).getName() + " is now unlocked!");
        } else {
            addBreak();
            System.out.println("You don't have the required item (" + map.get(roomIndex).getReq() + ") to unlock this room.");
            System.out.println("Dracula: I am disappointed in you " + player.getName() + ". Explore the map to find the required objects to unlock the rooms.\n I am counting on you..");
        }
    }


    
    // adds break to the output making it cleaner and easier to read
    public static void addBreak(){
        System.out.println();
        System.out.println("***************************");
        System.out.println();
    }
    
    
    // combat system
    public static void combat(Rooms room){
        Scanner input = new Scanner(System.in);
        
        String boss = room.getBoss();
        ArrayList<String> health = new ArrayList<String>();
        
        for(int i = 0; i < 10; i++){
            health.add("▮" );
        }
        
        ArrayList<String> playerHealth = new ArrayList<String>();
        
        
        for(int i = 0; i < 10; i++){
            playerHealth.add("▯" );
        }
        
        
        addBreak();
        System.out.println(player.getName() +" is now in a battle with "+boss);
        System.out.println();
        ArrayList<String> attacks = new ArrayList<String>();
        attacks.add("A) Sword Slash 20 dmg |    -2 stamina");
        attacks.add("B) Defense            |    +4 stamina");
        attacks.add("C) Healing 40hp       |    -3 stamina");
        
        int max = 5;
        int stamina = max;
        
        while(true){
            
            for(int i = 0; i < attacks.size(); i++){
                System.out.println(attacks.get(i));
            }
            
            System.out.println("\nRemaning Stamina: "+stamina);
            System.out.print("Choose your options (A,B,C) : ");
            String ans = input.nextLine();
            System.out.println();
            addBreak();
            
            if(ans.toUpperCase().equals("A") && stamina >= 2){
                
                if(health.size() >= 2){
                    health.remove(0);
                    health.remove(0);
                } else {
                    health.remove(0);
                }
                
                stamina -= 2;
                System.out.print(player.getName() +": deals 20 dmg   |");
                System.out.println("Stamina: "+stamina);
                System.out.println();
            } else if(ans.toUpperCase().equals("B")){
                stamina += 4;
                if(stamina > max){
                    stamina = max;
                }
            System.out.print(player.getName() +": regens st for +4   |");
                System.out.println("    Stamina: "+stamina);
                System.out.println();
            } else if(ans.toUpperCase().equals("C") && stamina >= 3){
                
                if(playerHealth.size() < 6){
                    for(int i = 0; i < 4; i++){
                        playerHealth.add("▯");   
                    }
                } else {
                    for(int i = 0; i < 10 - playerHealth.size(); i++){
                        playerHealth.add("▯");
                    }
                }
                
                stamina -= 3;
                System.out.print(player.getName()+": heals for 40");
                System.out.println(" Stamina: "+stamina);
                System.out.println();
            
            } else if(!ans.toUpperCase().equals("A") || !ans.toUpperCase().equals("B") || !ans.toUpperCase().equals("C")){
                System.out.println("Invalid Command...Defaulting to stamina regeneration..");
                
                if(stamina < max){
                    stamina += 1;
                }
                
            } else {
                System.out.println("Out of Stamina....");
            }
            
            int ran = 1 + (int) (Math.random() * (3 - 1 + 1));
            
            if(ran == 1){
                playerHealth.remove(0);
                System.out.println(boss + ": deals 10 dmg..");
                System.out.println();
            } else if(ran == 2){
                stamina -= 2;
                
                if(stamina < 0){
                    stamina = 0;
                }
                
                System.out.println(boss + ": drains life force | -2 stamina..");
                System.out.println();
            } else if(ran == 3){
                if(health.size() < 10){
                    health.add("▮" );
                }
                
                System.out.println(boss + ": heals 10 hp..");
                System.out.println();
            }
            
            System.out.println(player.getName() + " Health: "+playerHealth);
            System.out.println(boss + " Health: "+health);
            System.out.println("****************************");
            System.out.println();
            
            if(playerHealth.size() <= 0){
                gameOver();
                isPlaying = false;
                win = false;
                break;
            } else if(health.size() <= 0){
                System.out.println("Monster defeated..");
                checkStage(boss);
                break;
            }
        }
        
    }
    
    // advances the story
    public static void checkStage(String boss){
        if(boss.equals("Frank S.")){
            stage2();
        } else if(boss.equals("The Mummy")){
            stage3();
        } else if(boss.equals("Werewolf")){
            stage4();
        } else if(boss.equals("Lady Dracula")){
            stage5();
            isPlaying = false;
        }
    }
    
    // The title screen of the game
    public static void startGame(){
        System.out.println("Welcome to...");
        
        System.out.println("▄▄▄█████▓ ██░ ██ ▓█████    ▓█████▄  ▄▄▄       ██▀███   ██ ▄█▀");
    System.out.println("▓  ██▒ ▓▒▓██░ ██▒▓█   ▀    ▒██▀ ██▌▒████▄    ▓██ ▒ ██▒ ██▄█▒ ");
    System.out.println("▒ ▓██░ ▒░▒██▀▀██░▒███      ░██   █▌▒██  ▀█▄  ▓██ ░▄█ ▒▓███▄░ ");
    System.out.println("░ ▓██▓ ░ ░▓█ ░██ ▒▓█  ▄    ░▓█▄   ▌░██▄▄▄▄██ ▒██▀▀█▄  ▓██ █▄ ");
    System.out.println("  ▒██▒ ░ ░▓█▒░██▓░▒████▒   ░▒████▓  ▓█   ▓██▒░██▓ ▒██▒▒██▒ █▄");
    System.out.println("  ▒ ░░    ▒ ░░▒░▒░░ ▒░ ░    ▒▒▓  ▒  ▒▒   ▓▒█░░ ▒▓ ░▒▓░▒ ▒▒ ▓▒");
    System.out.println("    ░     ▒ ░▒░ ░ ░ ░  ░    ░ ▒  ▒   ▒   ▒▒ ░  ░▒ ░ ▒░░ ░▒ ▒░");
    System.out.println("  ░       ░  ░░ ░   ░       ░ ░  ░   ░   ▒     ░░   ░ ░ ░░ ░ ");
    System.out.println("          ░  ░  ░   ░  ░      ░          ░  ░   ░     ░  ░   ");
    System.out.println("                            ░                                 ");
    System.out.println(" ▄▄▄        █████▒ █████▒▄▄▄       ██▓ ██▀███                ");
    System.out.println("▒████▄    ▓██   ▒▓██   ▒▒████▄    ▓██▒▓██ ▒ ██▒              ");
    System.out.println("▒██  ▀█▄  ▒████ ░▒████ ░▒██  ▀█▄  ▒██▒▓██ ░▄█ ▒              ");
    System.out.println("░██▄▄▄▄██ ░▓█▒  ░░▓█▒  ░░██▄▄▄▄██ ░██░▒██▀▀█▄               ");
    System.out.println(" ▓█   ▓██▒░▒█░   ░▒█░    ▓█   ▓██▒░██░░██▓ ▒██▒              ");
    System.out.println(" ▒▒   ▓▒█░ ▒ ░    ▒ ░    ▒▒   ▓▒█░░▓  ░ ▒▓ ░▒▓░              ");
    System.out.println("  ▒   ▒▒ ░ ░      ░       ▒   ▒▒ ░ ▒ ░  ░▒ ░ ▒░              ");
    System.out.println("  ░   ▒    ░ ░    ░ ░     ░   ▒    ▒ ░  ░░   ░               ");
    System.out.println("      ░  ░                    ░  ░ ░     ░                  ");

    System.out.println();
    
    }
    
    // character creation
    public static Person characterCreation(){
        Scanner input = new Scanner(System.in);
        System.out.print("Do you wish to have a ticket? (Y/N): ");
        String answer = input.nextLine();
        System.out.println();
        
        if(answer.toUpperCase().equals("Y")||answer.toUpperCase().equals("YES")){
            System.out.println("You won't regret it...");
        } else if(answer.toUpperCase().equals("N")||answer.toUpperCase().equals("NO")){
            System.out.println("Too bad, you are still going...");
        } else {
            System.out.println("That is not the answer I'm looking for... you don't want me to be mad now, do ya?(Y)");
            
            while(true){
                System.out.println("You are going in right?!?");
                System.out.println();
                answer = input.nextLine();
            
                if(answer.toUpperCase().equals("Y")){
                    System.out.println("Good...");
                    break;
                } else if(answer.toUpperCase().equals("N")){
                    System.out.println("Too bad, you are still going...");
                    break;
                }
            }
        }
        
        
        System.out.print("What is your name child?... ");
        String name = input.nextLine();
        
        System.out.print("I can't see you in the dark are you a boy or a girl??(B/G): ");
        String gen = input.nextLine();
        boolean gender;
        
        if(gen.toUpperCase().equals("B")){
            gender = true;
        } else if(gen.toUpperCase().equals("G")){
            gender = false;
        } else{
            System.out.println("Not the answer I expected...defaulting to boy..");
            gender = true;
        }
        
        Person person = new Person(name,gender);
        
        return person;
        
        
    }
    
    
    // first stage of the game (more like a cutscene)
    public static void stageFirst(){
        Scanner input = new Scanner(System.in);
        
        System.out.println("You have arrived at...");
        
        System.out.println("                                       ");
        System.out.println("         ████████████████████         ");
        System.out.println("         ████████████████████         ");
        System.out.println("          ██████████████████          ");
        System.out.println("          ██████████████████          ");
        System.out.println(" ████████████████████████████████████ ");
        System.out.println(" ████████████████████████████████████ ");
        System.out.println(" ██████████████  ████  ██████████████ ");
        System.out.println("  █████████████  ████  █████████████  ");
        System.out.println("  █████████████  ████  █████████████  ");
        System.out.println("  ████ ████████  ████  █████████████  ");
        System.out.println("  ██████████████████████████████████  ");
        System.out.println("  ████ ████████████████████████ ████  ");
        System.out.println("  ████ ██████ ██████████ ██████ ████  ");
        System.out.println("  ███████████ █████ ████ ███████████  ");
        System.out.println("  ███████████ ████   ███ ███████████  ");
        System.out.println(" ███     ████████████████████     ███ ");
        System.out.println(" ████████████████████████████████████ ");
        System.out.println(" ████████████████████████████████████ ");

        System.out.println();
        System.out.println("HOTEL TRANSYLVANIA...");
        System.out.println();
        System.out.print("Do you wish to enter?(Y/N)...");
        String ans = input.nextLine();
        
        if(!ans.toUpperCase().equals("Y")){
            System.out.println("A mysterious wind pushes you inside...");
        }
        
        System.out.println("This hotel is filled with monsters...");
        System.out.println("You have a mask on..If the monsters find out you are human, it won't end well...");
        
        System.out.println();
        System.out.println();
        
        
        // Encounter with Dracula and he gives you your main mission
        System.out.println("You have encountered Dracula...");
        System.out.print("Listen "+player.getName());
        System.out.println(", I called you here and you will help me!!");
        System.out.println("I suspect my wife is cheating on me!! Please find out more");
        System.out.println("I give you a map of the hotel..Input M in the console whenever you need it..DON'T DISSAPOINT ME!!");
    }
    
    //Showed when Frank S. is defeated and catacombs becomes unlocked 
    public static void stage2(){
        addBreak();
        System.out.println("                                                                           ");
        System.out.println("                                                                           ");
        System.out.println("                                                                           ");
        System.out.println("                █████████████████████████████████████████▓                ");
        System.out.println("                ▒████████████████████████████████████████                 ");
        System.out.println("                ▒████████████████████████████████████████                 ");
        System.out.println("                 ███░█████▓▓██░███▓░█████░░████████░█████                 ");
        System.out.println("                 ██▓░░███░░░█░░░██░░░███▒░░░████░░░░░███▒                 ");
        System.out.println("                 ▓█░░░░█░░░░░░░░░░░░░░░█░░░░░▓▒░░░░░░░██                  ");
        System.out.println("                  ░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░                  ");
        System.out.println("                   ░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░                   ");
        System.out.println("                   ░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░                   ");
        System.out.println("                   ░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░                   ");
        System.out.println("                   ░░░░▒███████████░░░░███████████▒░░░░                   ");
        System.out.println("                   ░░░░░▒        ░░░░░░░▒        ▒░░░░░                   ");
        System.out.println("                    ░░░     ██     ░░░░     ██     ░░░                    ");
        System.out.println("                    ░░░░          ░░░░░           ░░░░                    ");
        System.out.println("                ░░░░░░░░░░      ░░░░░░░░░░      ░░░░░░░░░░                ");
        System.out.println("                ░░▒▒░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░▒▒░░                ");
        System.out.println("                ░░░▒░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░▒░░░                ");
        System.out.println("                  ░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░                  ");
        System.out.println("                     ░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░                     ");
        System.out.println("                     ░░░░░░░  ░░░░░░░░░░░░░░  ░░░░░░░                     ");
        System.out.println("                      ░░░░░░░░░░░░░░░░░░░░░░░░░░░░▓░                      ");
        System.out.println("                ███   ░░░░░░░░░░░░░░░░░░░░░░░░░░░█░░   ███                ");
        System.out.println("                █████▓░░░░░░░░░░░░░░░░░░░░░░░░░▓░░░░▒█████                ");
        System.out.println("                ███   ░░░░░░░░░░░░░░░░░░░░░░░█░░░░░░   ███                ");
        System.out.println("                ▒▒    ░░░░░░░░░░░░░░░░░█░▒░░░░░░░░░░    ▒▒                ");
        System.out.println("                       ░░░░░░░░░░░░░░░░░░░░░░░░░░░░░                       ");
        System.out.println("                                                                           ");
        System.out.println("                                                                           ");
        
        
        System.out.println();
        System.out.println("You have bested me...take this blue key and spare me...");
        System.out.println();
        System.out.println("The Catacombs is now open..");
        addBreak();
        rooms.remove(3);
        map.get(4).setUnlocked(true);
    }
    
    //Showed when Mummy is defeated and ballroom is unlocked 
    public static void stage3(){
        addBreak();
        System.out.println("                                                             ");
        System.out.println("                                                             ");
        System.out.println("                                                             ");
        System.out.println("                     ▓████             ██                   ");
        System.out.println("                  █            █          ▓█                 ");
        System.out.println("                █                  █         █               ");
        System.out.println("              █                       ▓░      ▓              ");
        System.out.println("             █  ▒██              ███▓           █           ");
        System.out.println("            ▓         ░█  █▒                    ▓           ");
        System.out.println("           █                █                    █          ");
        System.out.println("          █                     █                ▒          ");
        System.out.println("          █     ▓█▒         ██████   ░▓██▓        ▒         ");
        System.out.println("          ░ █████           ▓████            █████▒         ");
        System.out.println("         ░███████    ░██     ████     ██     █████▒         ");
        System.out.println("         ▒███████     ▓░     ████            █████▒         ");
        System.out.println("         ▒███████▓           █████▒         ██████▒         ");
        System.out.println("         ▒    █████        ██████████░  ▓███▓░    ▒         ");
        System.out.println("         ▒             ░▒▓▓▓▓▒░    █░             ▒         ");
        System.out.println("         ▒                      █░               █▒         ");
        System.out.println("         ▒                  █                ▓░   ▒         ");
        System.out.println("         ▒            ▒██               ██                   ");
        System.out.println("         ▒█▓▓▓▓▓░                  ░▓            █          ");
        System.out.println("          █                   ▓█                 █          ");
        System.out.println("          ▒              ████████               █           ");
        System.out.println("           ██▒   ░▓████████████████████████▒░░ █            ");
        System.out.println("            █    █   ▒████████▓░              █             ");
        System.out.println("              ▓    ▓                         █              ");
        System.out.println("               █      ▓▓                   █                ");
        System.out.println("                 █         ██▒          ██                  ");
        System.out.println("                   ░█               ██░                     ");
        System.out.println("                                                             ");
        System.out.println("                                                             ");
        System.out.println("                                                             ");
        
        
        System.out.println();
        System.out.println("Don't hurt me..I am not the person you seek...take this yellow key and spare me..");
        System.out.println();
        System.out.println("The Ballroom is now open..");
        map.get(5).setUnlocked(true);
        rooms.remove(2);
    }
    
    //Showed when Werewolf is defeated and garden is now open. 
    public static void stage4(){
        addBreak();
        System.out.println("                                                             ");
        System.out.println("                                                             ");
        System.out.println("                               ░░░░ ░▓▒                     ");
        System.out.println("                             ░▒▒▓▓█▓▓▓▒░░░▒                 ");
        System.out.println("                           ░░░▒▒▓▓█▒ ▒▓▒░  ░▒░              ");
        System.out.println("       ▒                ░░░░░░▓▓█▓░░██░░▒▒▓▓▓▒▒             ");
        System.out.println("       ░░         ░░   ░░░▒▒▓█▓▓░ ▒▓██░ ░░░░░░▓▓           ");
        System.out.println("        ░░░░░░ ░░░░░░ ░░░▒▓▓▓▒   ▒████▓░  ░▒▓▒░░▓▒▒         ");
        System.out.println("       ░▒ ▒▒▒▒ ░░ ░  ░░░▒▓▒▓░░░▒███████░░░░░▒▒▒░▒▒▓▒░       ");
        System.out.println("        ░░░▓▒░░░░ ▒▒▒▓▓▓░▒██▓▓█████████▓▒▒▒▒▒▓▒▒░▒█▓▒       ");
        System.out.println("         ░░░░░░░░▒▓▓█▓▓▓██▓██████████████▓▒▓▓█▓▒░▒▓█▒▒░     ");
        System.out.println("         ░░░░░░░▒▓█▓██▒▒▒░▒▓██████████████▓▓███▒▓█▓█▒░░░░   ");
        System.out.println("         ░░░ ░▒▒▒▒▒▒▒▓▓▒░░▒░░▒████████████████▒█▓███▒░▒▓░   ");
        System.out.println("          ░ ░░▒▓▒▒▓▒▒▓▒▒▒▒░▒▒▓█████▓██████████▓▒▓█▓▓▒░█▒░   ");
        System.out.println("            ░ ▒▒▓▓▒▓░░░░░░░▓███████▓▓▒▒▓██████▓▓▓▓▒▒▒▒██▓   ");
        System.out.println("       ░▓░ ░░▓▒▓▒░▓▒▒▒▒▒▒▓▓██████████▒▒▒▓█████████▒░▒▓▓█    ");
        System.out.println("      ░▒░░▒█░░█▓▒▓▓░▓▓▓█▓███▒▒████▓█▓▓▒██████████▓▒▒▒█▒▒    ");
        System.out.println("       ░▓░▓░███████▓████▓▓█▒▒███████▓▓████████████▓▒▓█▒░    ");
        System.out.println("      ░▓▓██▓█ ▒▒█░▒▓██████████████▓███▓▓██████████▓█▓▓▒▒    ");
        System.out.println("        ░░▒ ░▒░▒ ░    ░░░░    ████▓▓████▓▓██████████▓▓▒     ");
        System.out.println("         ░▓░ ▒░░▓  ░░▒██▓█▒▒▒▓████▓▓▓▓██████████████▓▒      ");
        System.out.println("          ▒░████ ░░▓░▒█████▓██████▓▓██████████████▓▒        ");
        System.out.println("          ░██████ ░▒███████████████████████████▓▓░          ");
        System.out.println("         ███████▓░░████▓███████████████████████░            ");
        System.out.println("           ▓███████▒░█▓████████████████████▒                ");
        System.out.println("             ▒ ░▒▒█▒▓▒▓█████████████████▓▒                  ");
        System.out.println("                █▓▒▓ ▒███████████▓▓█▓▒░ ░░                  ");
        System.out.println("                ██▓▒█▓██   ███▓▓▓▒                          ");
        System.out.println("                 ▓█████░   █▓▓▒▒                            ");
        System.out.println("                  ░░▓█▓    ░                                ");
        System.out.println("                    ▒                                       ");
        System.out.println("                                                             ");
        System.out.println("                                                             ");
        
        System.out.println();
        System.out.println("Rrrr...You hurt me..Yes it was me. I love Lady Dracula. But you aren't done yet...");
        System.out.println();
        System.out.println("The Garden is now open..");
        map.get(1).setUnlocked(true);
        rooms.remove(1);
    }
    
    //Final boss. After Lady Dracula is defeated, the game ends and the player wins
    //They also find out the truth about the Dark Affair...
    public static void stage5(){
        addBreak();
         System.out.println("                                                                           ");
        System.out.println("                                                                           ");
        System.out.println("                                                                           ");
        System.out.println("                            ▓░▓██████▓▓▓▒▒▓▓▓▓                             ");
        System.out.println("                         ░▒███████████████████▓ ▒                          ");
        System.out.println("                         ███████████████████████▓                          ");
        System.out.println("                       ▓█████████████████████████▓▓                        ");
        System.out.println("                       ▓█████▒▒▒▒██████████████▓██▒                        ");
        System.out.println("                      ▓████       ░▓██████████████▓                        ");
        System.out.println("                      ████          ░███▒░░   ░██▓█                        ");
        System.out.println("                    ▓▓███░                     ░███▓▓▓                     ");
        System.out.println("                 ░▓▓▓▓███░    █                ░███▓▓▓▓▓░                  ");
        System.out.println("               ░▓▓▓▓▓▓███░     ░█          ▓   █████▓▓▓▓▓▓                 ");
        System.out.println("              ▒▓▓▓▓▓▓██ ██ ████▓░▓     ░█▒░   ░█████▓▓▓▓▓▓▓▒               ");
        System.out.println("             ▓▓▓▓▓▓▓███  ▓  ▓    ░     ░▓██▒█░█▓█████▓▓▓▓▓▓▓▒              ");
        System.out.println("           ░▓▓▓▓▓▓▓██████░   ░▓▒▒░          ░░░ ██▓███▓▓▓▓▓▓▓▒             ");
        System.out.println("           ▒▓▓▓▓▓█████████                    ▓███▓█████▓▓▓▓▓▓▒            ");
        System.out.println("          ▒▓▒▓▓█████▓█████                   █████▓▓████▓█▓▓▓▓▓▒           ");
        System.out.println("         ░▓▒▓██████▓███████                 ░█████▓▒▓████▓▒▓▓▓▓▓▒          ");
        System.out.println("        ▓█████████▓█████████    ▒     ▓    ███████▓▒▓▓██████░  ░▒░         ");
        System.out.println("     ████████████▓▓██████████▓    ▓▓▓░   ▓████████▓▒▒▒███████████▒▒▓█      ");
        System.out.println("      █████████▓▓▓█████████████        ▓███████▓▓▓█▒▒▒▓██████▓░ ███▓       ");
        System.out.println("       ██████████████████▓███████    ░ ██████▓█▓▓██▓▒▒▒▒█ ░████████        ");
        System.out.println("        ██████████████████▒██████      █████████▓▓░▒██████████████         ");
        System.out.println("        ▒█████████████████░██████░     █████▓████████████████████░         ");
        System.out.println("         ▒████████████████░███░ ██▒▒ ██░   █▓██████████████████▓▒          ");
        System.out.println("         ░▓████████████████▓      ▓▓▓░     ░██████████████████▓▓░          ");
        System.out.println("         ░▒▓▓███████████▒███░    ▒ ░ ▒     ██████████████████▒▒▒           ");
        System.out.println("          ░▒▒▒████████████▓██     ██       ████████████████▓▒▒▒░           ");
        System.out.println("           ░▒▓▒▓█████▓▓██████     ▒█      ██████▓ ▓███████▒▒▒▒░            ");
        System.out.println("            ░▒▒▒▓████████▓████           ▓███▓▒█████████▓▒▒▒▒░             ");
        System.out.println("              ▒▒▒▒▓████████▓████░     ░███████████████▓▒▒▒▒▒               ");
        System.out.println("               ░▒▒▒▒██████████████▓ █████████▓███████▒▒▒▒▒░                ");
        System.out.println("                 ░▒▒▒▓████████████████▒██████ █████▓▒▒▒▒░                  ");
        System.out.println("                   ▒▒▒▒█████████░████ ███████ ████▒▒▒▒░                    ");
        System.out.println("                      ▒▒▓█▓█████████████████▓███▓▒▒░                       ");
        System.out.println("                         ░█████████████████████░                           ");
        System.out.println("                               ░▒███████▓▒                                 ");
        System.out.println("                                                                           ");
        System.out.println("");                               
        
        System.out.println();
        System.out.println("You have bested me..but I always loved Dracula. He didn't trust me and now he and you shall pay..");
    }

//Printed when game ends 
    public static void gameOver(){
        System.out.println("*******************************");
        System.out.println();
    
        System.out.println();
        System.out.println("  ██████     ███     ██     ██  █████████ ");
        System.out.println(" ███        ███ ███   ████ █████ ███      ");
        System.out.println(" ███  ████  ███    ███ ██████████ ████████ ");
        System.out.println(" ████   ███ █████████  ███ █  ███ ███      ");
        System.out.println("    ██████  ███    ██  ███    ██  █████████ ");
        System.out.println();
        System.out.println("  ██████   ███    ██  █████████  ████████ ");
        System.out.println("███    ███ ███    ██  ███        ███    ██ ");
        System.out.println("███    ███ ████ ████  ████████   ███  ████  ");
        System.out.println("███    ███   ██████   ███        ████████  ");
        System.out.println("  ██████       █      █████████  ███  ████  ");

    }
}
