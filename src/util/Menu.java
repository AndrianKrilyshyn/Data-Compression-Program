package util;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;

import Command.*;

public class Menu {
    private Map<Integer,Command> commands;

    public void getMenu() {
        System.out.println("************  Menu  ************");
        System.out.println(" 1. Huffman method\n 2. LZW method\n 3. Exit");
        System.out.println("********************************");
    }
private void setUp(){
    commands = new HashMap<>();
    commands.put(1, new Huffman());
    commands.put(2, new LZW());

}
    public void execute() {
        setUp();
        Scanner scanner = new Scanner(System.in);
        while (true) {
            this.getMenu();
            System.out.print(" ▼ Enter number: ");
            int choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice){
                case 1,2: commands.get(choice).execute(); break;
                case 3: System.out.println(" Bye bye"); return;
                default: System.out.println(" Error!"); break;
            }
        }
    }
}
