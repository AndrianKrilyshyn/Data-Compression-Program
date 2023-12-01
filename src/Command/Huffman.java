package Command;

import Huffman.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Huffman implements Command {
    @Override
    public void execute(){
        Scanner scanner = new Scanner(System.in);
        System.out.println(" Entering a file?\n 1. From file\n 2. From keyboard");
        int mode=scanner.nextInt();
        scanner.nextLine();
        NodeList nodeList = new NodeList(new ArrayList<>(), new ArrayList<>());
        if(mode==1){
            try {
                nodeList.entryListFromFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }else {
            try {
                nodeList.entryListFromKeyboard();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        nodeList.createTree();
        nodeList.createCode();
        try {
            nodeList.createDictionary();
            System.out.println(" Dictionary created");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        //nodeList.printConnections();
        try {
            nodeList.encodeText();
            System.out.println(" Text coded");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        //nodeList.printList(nodeList.getNodeList());
        try {
            nodeList.decodeText();
            System.out.println(" Text decoded");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
