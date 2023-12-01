package Huffman;


import util.FileHelper;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class NodeList {
    private ArrayList<Node> nodeList;
    private ArrayList<Node> nodeActiveList;

    public NodeList() {
    }

    public NodeList(ArrayList<Node> nodeList, ArrayList<Node> nodeActiveList) {
        this.nodeList = nodeList;
        this.nodeActiveList = nodeActiveList;
    }

    public ArrayList<Node> getNodeList() {
        return nodeList;
    }

    public void setNodeList(ArrayList<Node> nodeList) {
        this.nodeList = nodeList;
    }

    public ArrayList<Node> getNodeActiveList() {
        return nodeActiveList;
    }

    public void setNodeActiveList(ArrayList<Node> nodeActiveList) {
        this.nodeActiveList = nodeActiveList;
    }

    public boolean inNodeList(char symbol) {
        for (int i = 0; i < nodeList.size(); i++) {
            if (nodeList.get(i).getSymbol() == symbol) {
                nodeList.get(i).incFrequency();
                return true;
            }
        }
        return false;
    }

    public void entryListFromKeyboard() throws IOException {
        System.out.print("\n Needed temporary file ");
     //  FileWriter fileWriter = FileHelper.newFilewriter();
        FileWriter fileWriter = new FileWriter("D:\\data\\Coursework.txt");//newFilewriter();
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()) {
            String word = scanner.nextLine();
            fileWriter.write(word);
            if (word.isEmpty())
                break;
            for (int i = 0; i < word.length(); i++) {
                char symbol = word.charAt(i);
                createNode(symbol);
            }
        }
        fileWriter.close();
    }

    public void entryListFromFile() throws IOException {
        System.out.println(" Source file");
        // File file = FileHelper.openFile();
        File file = new File("D:\\data\\Coursework.txt");
        Scanner scanner = new Scanner(file);
        while (scanner.hasNextLine()) {
            String word = scanner.nextLine();
            if (word.isEmpty())
                break;
            for (int i = 0; i < word.length(); i++) {
                char symbol = word.charAt(i);
                createNode(symbol);
            }
        }
    }

    public void createNode(char symbol) {
        if (!inNodeList(symbol)) {
            Node NodeObj = new Node(symbol);
            nodeList.add(NodeObj);
            nodeActiveList.add(NodeObj);
        }
    }

    public Node findNodeWithMinFrequency() {
        Node NodeWithMinFreq = null;
        long min = Long.MAX_VALUE;
        long currFreq;
        for (int i = 0; i < nodeActiveList.size(); i++) {
            currFreq = nodeActiveList.get(i).getFrequency();
            if (min > currFreq) {
                min = currFreq;
                NodeWithMinFreq = nodeActiveList.get(i);
            }
        }
        nodeActiveList.remove(NodeWithMinFreq);
        return NodeWithMinFreq;
    }

    public void createTree() {
        while (nodeActiveList.size() > 1) {
            Node minFreq1 = findNodeWithMinFrequency();
            Node minFreq2 = findNodeWithMinFrequency();
            Node newNode = new Node();
            newNode.setFrequency(minFreq1.getFrequency() + minFreq2.getFrequency());
            nodeActiveList.add(newNode);
            if (minFreq1.getFrequency() > minFreq2.getFrequency()) {
                minFreq1.setWeight(1);
                minFreq2.setWeight(0);
            } else if (minFreq1.getFrequency() < minFreq2.getFrequency()) {
                minFreq2.setWeight(1);
                minFreq1.setWeight(0);
            } else {
                minFreq1.setWeight(1);
                minFreq2.setWeight(0);
            }
            minFreq1.setNextNode(newNode);
            minFreq2.setNextNode(newNode);
        }
        printConnections();
    }

    public void printConnections() {
        for (int i = 0; i < nodeList.size(); i++) {
            Node tempNode = nodeList.get(i);
            while (tempNode != null) {
                System.out.print(tempNode);
                tempNode = tempNode.getNextNode();
            }
            System.out.println("\n\n");
        }
    }

    public void printList(ArrayList<Node> nodeList) {
        for (int i = 0; i < nodeList.size(); i++) {
            System.out.println(nodeList.get(i));
        }
    }
    //public File createDictionary() throws IOException {
    public void createDictionary() throws IOException {
        //String filePath = FileHelper.getFilePath();
        //File file = new File(filePath);
        FileWriter fileWriter = FileHelper.newFilewriter("D:\\data\\CourseworkDictionary.txt");
        for (Node node: nodeList) {
            String element = node.getSymbol()+" "+ node.getCode()+"\n";
            fileWriter.write(element);
        }
        //return file;
    }

    public void createCode() {
        for (int i = 0; i < nodeList.size(); i++) {
            String code = "";
            Node tempNode = nodeList.get(i);
            while (tempNode.getNextNode() != null) {
                code += tempNode.getWeight();
                tempNode = tempNode.getNextNode();
            }
            StringBuilder temp = new StringBuilder(code);
            temp.reverse();
            code = temp.toString();
            nodeList.get(i).setCode(code);
            //nodeList.get(i).setCoded(Integer.parseInt(code, 2));
        }
    }
    /////////////////////////////////////////////

//    public FileWriter newFilewriter() throws IOException {
//        String filePath = getFilePath();
//        FileWriter fileWriter = new FileWriter(filePath);
//        return fileWriter;
//    }
//    public File openFile() {
//        String filePath = getFilePath();
//        File file = new File(filePath);
//        return file;
//    }
//    public String getFilePath() {
//        System.out.print("Enter path to file: ");
//        Scanner scanner = new Scanner(System.in);
//        String filePath = scanner.nextLine();
//        return filePath;
//    }
/*    public void encodeText() throws IOException {
        System.out.println("Source file: ");
        File file = new File("D:\\data\\Coursework.txt");//openFile();
        System.out.println("File for result: ");
        FileWriter fileWriter = new FileWriter("D:\\data\\CourseworkRes.txt");//newFilewriter();
        Scanner scanner = new Scanner(file);
        while(scanner.hasNextLine()){
            String word =  scanner.nextLine();
            for (int i = 0; i < word.length(); i++) {
                char symbol = word.charAt(i);
                for (int j = 0; j < nodeList.size(); j++) {
                    if(nodeList.get(j).getSymbol()==symbol){
                        fileWriter.write(nodeList.get(j).getCode());
                     }
                }
            }
        }
        fileWriter.close();
    }*/
/////////////////////////////////////////////
/*    public void decodeText() throws IOException {
        System.out.println("Source file: ");
        File file = new File("D:\\data\\CourseworkRes.txt");//openFile();
        System.out.println("File for result: ");
        FileWriter fileWriter = new FileWriter("D:\\data\\CourseworkDecode.txt");//newFilewriter();
        Scanner scanner = new Scanner(file);
        String number = "";
        while(scanner.hasNextLine()){
            String symbolCode =  scanner.nextLine();
            for (int i = 0; i < symbolCode.length(); i++) {
                number += symbolCode.charAt(i);
                for (int j = 0; j < nodeList.size(); j++) {
                    if(nodeList.get(j).getCode().equals(number)){
                        fileWriter.write(nodeList.get(j).getSymbol());
                        number="";
                    }
                }
            }
        }
        fileWriter.close();
    }*/
    /*public void createTextBin() throws IOException {
        File file = new File("D:\\data\\Coursework.txt");
        FileOutputStream fileOutputStream = new FileOutputStream ("D:\\data\\CourseworkRes.bin");
        DataOutputStream dataOutputStream = new DataOutputStream(fileOutputStream);
        Scanner scanner = new Scanner(file);
        while(scanner.hasNextLine()){
            String word =  scanner.nextLine();
            for (int i = 0; i < word.length(); i++) {
                char Node = word.charAt(i);
                for (int j = 0; j < nodeList.size(); j++) {
                    if(nodeList.get(j).getValue()==Node){
                        dataOutputStream.write(0b101);
                    }
                }
            }
        }
        dataOutputStream.close();
        fileOutputStream.close();
    }*/

    /*    public void print() throws IOException {
            FileWriter fileWriter = new FileWriter("D:\\data\\CourseworkRes.txt");
            String k = "101";//0b101;
            for (long i = 0; i < 10000; i++) {
                fileWriter.write(k);
            }
            System.out.println("COMPLATE");
            fileWriter.close();
        }*/
    public void print() throws IOException {
        FileOutputStream fileOutputStream = new FileOutputStream("D:\\data\\CourseworkRes.bin");
        DataOutputStream dataOutputStream = new DataOutputStream(fileOutputStream);
        for (long i = 0; i < 10000; i++) {
            dataOutputStream.write(0b101);
        }
        System.out.println("COMPLATE");
        dataOutputStream.close();
        fileOutputStream.close();
    }

    public void isDubble() {
        for (int i = 0; i < nodeList.size(); i++) {
            for (int j = i + 1; j < nodeList.size(); j++) {
                if (nodeList.get(i).getCode() == nodeList.get(j).getCode()) {
                    System.out.println("SAME CODE\n" + nodeList.get(i) + "\n" + nodeList.get(j));

                }

            }
        }

    }


    /*    public void encodeText() throws IOException {
            System.out.println("Source file: ");
            File inputFile = new File("D:\\data\\Coursework.txt");
            System.out.println("Binary output file: ");
            File outputFile = new File("D:\\data\\CourseworkRes.bin");

            FileOutputStream fileOutputStream = new FileOutputStream(outputFile);
            DataOutputStream dataOutputStream = new DataOutputStream(fileOutputStream);
            Scanner scanner = new Scanner(inputFile);

            while (scanner.hasNextLine()) {
                String word = scanner.nextLine();
                for (int i = 0; i < word.length(); i++) {
                    char symbol = word.charAt(i);
                    for (int j = 0; j < nodeList.size(); j++) {
                        if (nodeList.get(j).getSymbol() == symbol) {
                            String code = nodeList.get(j).getCode();
                            for (int k = 0; k < code.length(); k++) {
                                char bit = code.charAt(k);
                                dataOutputStream.writeByte(bit == '1' ? 1 : 0); // Записуємо байт 1 або 0
                            }
                        }
                    }
                }
            }

            dataOutputStream.close();
            fileOutputStream.close();
        }*/
    public void encodeText() throws IOException {
        System.out.println("Source file: ");
        File inputFile = new File("D:\\data\\Coursework.txt");
        System.out.println("Binary output file: ");
        File outputFile = new File("D:\\data\\CourseworkRes.bin");

        FileOutputStream fileOutputStream = new FileOutputStream(outputFile);
        DataOutputStream dataOutputStream = new DataOutputStream(fileOutputStream);
        Scanner scanner = new Scanner(inputFile);

        while (scanner.hasNextLine()) {
            String word = scanner.nextLine();
            for (int i = 0; i < word.length(); i++) {
                char symbol = word.charAt(i);
                for (int j = 0; j < nodeList.size(); j++) {
                    if (nodeList.get(j).getSymbol() == symbol) {
                        String code = nodeList.get(j).getCode();
                        byte[] symbolCode = convertToByte(code);
                        for (int k = 0; k < symbolCode.length; k++) {
                            dataOutputStream.writeByte(symbolCode[k]);
                        }
                    }
                }
            }
        }

        dataOutputStream.close();
        fileOutputStream.close();
    }

    /* public static byte[] convertToByte(String code) {
         int lenCode = code.length();
         int zeroBeforecode = 0;
         int byteCount = (int) Math.ceil(lenCode / 7.0);
         byte[] byteCode = new byte[byteCount * 2];
         int startIndex = 0;
         for (int i = 0; i < byteCount * 2; i += 2) {
             int endIndex = Math.min(startIndex + 7, lenCode);
             String byteSubstring = code.substring(startIndex, endIndex);
             for (int j = 0; j < byteSubstring.length(); j++) {
                 if (byteSubstring.charAt(j) == '0') {
                     zeroBeforecode++;
                 } else {
                     break;
                 }
             }
             byteCode[i] = (byte) zeroBeforecode;
             byteCode[i + 1] = (byte) Integer.parseInt(byteSubstring, 2);

             startIndex = endIndex;
             zeroBeforecode = 0;
         }

         return byteCode;
     }


     public void decodeText() throws IOException {
         System.out.println("Binary input file: ");
         File inputFile = new File("D:\\data\\CourseworkRes.bin");
         System.out.println("Decoded output file: ");
         FileWriter fileWriter = new FileWriter("D:\\data\\CourseworkDecode.txt");

         FileInputStream fileInputStream = new FileInputStream(inputFile);
         DataInputStream dataInputStream = new DataInputStream(fileInputStream);
         String byteString = "";
         int countZero = 0;
         while(true) {
             String tempByteString;
             byte byteCode;
             try {
                 byteCode = dataInputStream.readByte();
                 countZero = byteCode;
                 byteCode = dataInputStream.readByte();
                 tempByteString = Integer.toBinaryString(byteCode);
             } catch (EOFException e) {
                 System.out.println("End file!");
                 break;
             }
             while (countZero > 0) {
                 tempByteString = "0" + tempByteString;
                 countZero--;
             }
             byteString += tempByteString;
             String checkCode = "";
             for (int i = 0; i < byteString.length(); i++) {
                 char symbol = byteString.charAt(i);
                 checkCode += symbol;
                 for (int j = 0; j < nodeList.size(); j++) {
                     if (nodeList.get(j).getCode().equals(checkCode)) {
                         Node temp = nodeList.get(j);
                         fileWriter.write(nodeList.get(j).getSymbol());
                         byteString = "";
                         checkCode = "";
                         break;
                     }
                 }
             }
         }
         fileInputStream.close();
         dataInputStream.close();


     }*/
    public static byte[] convertToByte(String code) {
        int lenCode = code.length();
        int byteCount = (int) Math.ceil(lenCode / 6.0);
        byte[] byteCode = new byte[byteCount];
        int startIndex = 0;
        for (int i = 0; i < byteCount; i ++) {
            int endIndex = Math.min(startIndex + 6, lenCode);
            String byteSubstring = code.substring(startIndex, endIndex);
            byteSubstring="1"+byteSubstring;
            byteCode[i] = (byte) Integer.parseInt(byteSubstring, 2);

            startIndex = endIndex;
        }

        return byteCode;
    }



    public void decodeText() throws IOException {
        System.out.println("Binary input file: ");
        File inputFile = new File("D:\\data\\CourseworkRes.bin");
        System.out.println("Decoded output file: ");
        FileWriter fileWriter = new FileWriter("D:\\data\\CourseworkDecode.txt");

        FileInputStream fileInputStream = new FileInputStream(inputFile);
        DataInputStream dataInputStream = new DataInputStream(fileInputStream);
        String byteString = "";
        while(true) {
            String tempByteString;
            byte byteCode;
            try {
                byteCode = dataInputStream.readByte();
                tempByteString = Integer.toBinaryString(byteCode);
                tempByteString=tempByteString.substring(1);
            } catch (EOFException e) {
                System.out.println("End file!");
                break;
            }
            byteString += tempByteString;
            String checkCode = "";
            for (int i = 0; i < byteString.length(); i++) {
                char symbol = byteString.charAt(i);
                checkCode += symbol;
                for (int j = 0; j < nodeList.size(); j++) {
                    if (nodeList.get(j).getCode().equals(checkCode)) {
                        fileWriter.write(nodeList.get(j).getSymbol());
                        byteString = "";
                        checkCode = "";
                        break;
                    }
                }
            }
        }
        fileInputStream.close();
        dataInputStream.close();


    }
}

