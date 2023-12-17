package Command;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;
import ShannonFano.*;

public class Shannon implements Command{
    private double encodeTime;
    private double decodeTime;
    @Override
    public void execute(){
        Scanner scanner = new Scanner(System.in);
        File file;
        System.out.println(" ○ Entering a file?\n 1. From file\n 2. From keyboard");
        int mode=0;
        while (true) {
            try {
                mode = scanner.nextInt();
            }catch (InputMismatchException e){
                System.out.println(" It's not number!");
                scanner.nextLine();
                continue;
            }
            if (mode == 1 || mode == 2) {
                break;
            }
            System.out.println(" Wrong number!\n Try again..");
        }
        ShannonFano shannonFano = new ShannonFano(new ArrayList<>());
        try {
            file = shannonFano.createTree(mode);
        } catch (IOException e) {
            throw new RuntimeException("### Wrong file path! ###");
        }
        shannonFano.createCode();
        shannonFano.clearList();
        long start = System.currentTimeMillis();
        try {
            file=shannonFano.encodeText(file);
            System.out.println(" **** Text coded *****");
        } catch (IOException e) {
            throw new RuntimeException("### Wrong file path! ###");
        }
        long end = System.currentTimeMillis();
        encodeTime = (end-start)/1000.0;
        start = System.currentTimeMillis();
        try {
            shannonFano.decodeText(file);
            System.out.println(" **** Text decoded *****");
        } catch (IOException e) {
            throw new RuntimeException("### Wrong file path! ###");
        }
        end = System.currentTimeMillis();
        decodeTime = (end-start)/1000.0;
        System.out.println(" Encode time: "+encodeTime);
        System.out.println(" Decode time: "+decodeTime);
    }
}
