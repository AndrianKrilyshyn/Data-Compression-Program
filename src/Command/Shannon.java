package Command;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import ShannonFano.*;

public class Shannon implements Command{
    @Override
    public void execute(){
        Scanner scanner = new Scanner(System.in);
        System.out.println(" Entering a file?\n 1. From file\n 2. From keyboard");
        int mode=scanner.nextInt();
        scanner.nextLine();
        ShannonFano shannonFano = new ShannonFano(new ArrayList<>());
        try {
            shannonFano.createTree(mode);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        shannonFano.createCode();
        shannonFano.clearList();
        try {
            shannonFano.encodeText();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            shannonFano.decodeText();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
