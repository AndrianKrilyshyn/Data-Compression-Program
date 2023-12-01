

import util.FileHelper;
import LZW.*;
import Huffman.*;
import Dictionary.Dictionary;
import util.Menu;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;


public class Main {
    public static void main(String[] args) throws IOException {
        Menu menu = new Menu();
        menu.execute();

        //  LZW lzw = new LZW(new Dictionary(new ArrayList<>()));
        //lzw.getDictionary().printDictionary();
        //test();
        // FileOutputStream outputStream  = new FileOutputStream("D:\\data\\text.txt");
        //DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
        //lzwMethod();
        //huffmanMethod(1);
        //System.out.println(Arrays.toString(convertToByte("001110110011")));

    }
    public static void test() throws IOException {
        File file = FileHelper.openFile("G:\\data\\text.txt");
        int x = 100;
        FileWriter fileWriter =  new FileWriter(file);
        fileWriter.write(x);
        fileWriter.close();
        File file1 = FileHelper.openFile("G:\\data\\t2.txt");
        Scanner scanner = new Scanner(file);
        while (scanner.hasNextLine()){
            System.out.println(scanner.next());
        }
    }


    public static byte[] convertToByte(String code) {
        int lenCode = code.length();
        int zeroBeforecode=0;
        int byteCount = (int) Math.ceil(lenCode / 8.0);
        byte[] byteCode = new byte[byteCount*2];
        int startIndex = 0;
        for (int i = 0; i < byteCount*2; i+=2) {
            int endIndex = Math.min(startIndex + 7, lenCode);
            String byteSubstring = code.substring(startIndex, endIndex);
            for (int j = 0; j < byteSubstring.length(); j++) {
                if(byteSubstring.charAt(j)=='0'){
                    zeroBeforecode++;
                }
                else {
                    break;
                }
            }
            byteCode[i]= (byte) zeroBeforecode;
            byteCode[i+1] = (byte) Integer.parseInt(byteSubstring, 2);

            startIndex = endIndex;
            zeroBeforecode=0;
        }

        return byteCode;
    }


    public static void huffmanMethod(int mode) throws IOException {
        NodeList nodeList = new NodeList(new ArrayList<>(), new ArrayList<>());
        if(mode==1){
            nodeList.entryListFromFile();
        }else
            nodeList.entryListFromKeyboard();
        nodeList.createTree();
        nodeList.createCode();
        //nodeList.createDictionary();
        nodeList.printConnections();
        nodeList.encodeText();
        nodeList.printList(nodeList.getNodeList());
        nodeList.decodeText();


    }
    public static void lzwMethod(int mode) throws IOException {
        LZWMethod lzw = new LZWMethod(new Dictionary(new ArrayList<>()));
        lzw.encodeText(mode);
        lzw.decodeText();


    }
}


 /*   public static byte[] convertToByte(String code) {
        int lenCode = code.length();
        int byteCount = (int) Math.ceil(lenCode / 8.0);
        byte[] byteCode = new byte[byteCount];

        int startIndex = 0;
        for (int i = 0; i < byteCount; i++) {
            int endIndex = Math.min(startIndex + 7, lenCode);
            String byteSubstring = code.substring(startIndex, endIndex);
            byteCode[i] = (byte) Integer.parseInt(byteSubstring, 2);

            startIndex = endIndex;
        }

        return byteCode;
    }*/

 /*   public void decodeText() throws IOException {
        System.out.println("Binary input file: ");
        File inputFile = new File("D:\\data\\CourseworkRes.bin");
        System.out.println("Decoded output file: ");
        FileWriter fileWriter = new FileWriter("D:\\data\\CourseworkDecode.txt");

        FileInputStream fileInputStream = new FileInputStream(inputFile);
        DataInputStream dataInputStream = new DataInputStream(fileInputStream);
        String byteString = "";
        while (true) {
            byte byteCode;
            try {
                byteCode = dataInputStream.readByte();
            } catch (EOFException e) {
                System.out.println("End file!");
                break;
            }
            String checkLen = Integer.toBinaryString(byteCode);
            if (byteCode==3||byteCode==2){
                checkLen="0"+checkLen;
            }else if(byteCode==1){
                checkLen="00"+checkLen;
            }
            byteString += checkLen;
            String str = "";
            for (int i = 0; i < byteString.length(); i++) {
                char symbol = byteString.charAt(i);
                str += symbol;
                for (int j = 0; j < nodeList.size(); j++) {
                    if (nodeList.get(j).getCode().equals(str)) {
                        Node temp = nodeList.get(j);
                        fileWriter.write(nodeList.get(j).getSymbol());
                        byteString = "";
                        str = "";
                    }
                }
            }

        }
        fileInputStream.close();
        dataInputStream.close();


    }*/