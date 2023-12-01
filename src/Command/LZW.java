package Command;

import LZW.LZWMethod;
import Dictionary.Dictionary;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class LZW implements Command{
    @Override
    public void execute(){
        Scanner scanner = new Scanner(System.in);
        System.out.println(" Entering a file?\n 1. From file\n 2. From keyboard");
        int mode=scanner.nextInt();
        scanner.nextLine();
        LZWMethod lzw = new LZWMethod(new Dictionary(new ArrayList<>()));
        try {
            lzw.encodeText(mode);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            lzw.decodeText();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
