package util;


import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class FileHelper {
    public static FileWriter newFilewriter() {
        String filePath = getFilePath();
        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter(filePath);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return fileWriter;
    }
    public static FileWriter newFilewriter(String path) {
        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter(path);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return fileWriter;
    }
    public static File openFile() {
        String filePath = getFilePath();
        File file = new File(filePath);
        return file;
    }
    public static File openFile(String path) {
        File file = new File(path);
        return file;
    }
    public static String getFilePath() {
        System.out.print(" ▲ Enter path to file: ");
        Scanner scanner = new Scanner(System.in);
        String filePath = scanner.nextLine();
        return filePath;

    }

}
