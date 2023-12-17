package util;

import java.util.*;

import Command.*;

public class Menu {
    private Map<Integer,Command> commands;

    public void getMenu() {
        System.out.println("************  Menu  ************");
        System.out.println(" 1. Huffman method\n 2. LZW method\n 3. Shannon Fano method\n 4. Exit");
        System.out.println("********************************");
    }
private void setUp(){
    commands = new HashMap<>();
    commands.put(1, new Huffman());
    commands.put(2, new LZW());
    commands.put(3, new Shannon());

}
    public void execute() {
        setUp();
        Scanner scanner = new Scanner(System.in);
        int choice;
        while (true) {
            this.getMenu();
            System.out.print(" ▼ Enter number: ");
            try {
                choice = scanner.nextInt();
            }catch (InputMismatchException e){
                System.out.println(" It's not number!");
                scanner.nextLine();
                continue;
            }
            scanner.nextLine();
            switch (choice){
                case 1,2,3: commands.get(choice).execute(); break;
                case 4: System.out.println(" Bye bye"); return;
                default: System.out.println(" Wrong number!"); break;
            }
        }
    }
}
