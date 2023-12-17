package LZW;


import util.FileHelper;
import Dictionary.Dictionary;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class LZWMethod {
    private Dictionary dictionary;

    public LZWMethod(Dictionary dictionary) {
        this.dictionary = dictionary;
    }

    public Dictionary getDictionary() {
        return dictionary;
    }

    public void setDictionary(Dictionary dictionary) {
        this.dictionary = dictionary;
    }

    public File encodeText(int mode) throws IOException {
        Scanner scanner;
        System.out.println(" File for coded result");
        File fileForWrite = new File("G:\\data\\LZWCoded.txt");
        //File fileForWrite = FileHelper.openFile();
        FileWriter fileWriter = new FileWriter(fileForWrite);
        if (mode == 1) {
            System.out.println(" Source file");
             //File file = FileHelper.openFile();
            File file = new File("G:\\data\\LZWCoursework.txt");//openFile();
            scanner = new Scanner(file);
        } else {
            scanner = new Scanner(System.in);
            System.out.println(" Enter text:");
        }
        while (scanner.hasNextLine()) {
            String word = scanner.nextLine();
            if (word.isEmpty()) {
                break;
            }
            encodeWord(word, fileWriter);
        }
        fileWriter.close();
        //dictionary.printDictionary();
        return fileForWrite;
    }

    public void encodeWord(String word, FileWriter fileWriter) throws IOException {

        char[] symbolsArray = word.toCharArray();
        String totalSymbols = "";
        for (int i = 0; i < symbolsArray.length; i++) {
            String currentSymbols = totalSymbols + symbolsArray[i];
            if (dictionary.containSymbol(currentSymbols)) {
                totalSymbols = currentSymbols;
            } else {
                dictionary.getDictionary().add(new Symbol(dictionary.getDictionary().size() + 1, currentSymbols));
                fileWriter.write(dictionary.getSymbolCode(totalSymbols));
                i--;
                totalSymbols = "";//String.valueOf(symbolsArray[i]);
            }
        }
        if (!totalSymbols.isEmpty()) {
            fileWriter.write(dictionary.getSymbolCode(totalSymbols));
            fileWriter.write(dictionary.getSymbolCode(" "));
        }
    }

    public void decodeText(File fileOutput) throws IOException {
        //File fileOutput = new File("G:\\data\\LZWCoded.txt");
        System.out.println(" Decoded output file:");
        File fileInput = new File("G:\\data\\LZWDecoded.txt");
        //File fileInput = FileHelper.openFile();
        Dictionary dictionary = new Dictionary(new ArrayList<>());
        FileWriter fileWriter = new FileWriter(fileInput);
        FileReader fileReader = new FileReader(fileOutput);
        int code = -1;
        String totalSymbols = "";
        while ((code = fileReader.read()) != -1) {
            String currentSymbols;
            if (dictionary.getSymbolByCode(code) == null && dictionary.containSymbol(totalSymbols)) {
                currentSymbols = totalSymbols + totalSymbols.charAt(0);
            } else {
                currentSymbols = dictionary.getSymbolByCode(code);
            }

            totalSymbols += currentSymbols;
            fileWriter.write(currentSymbols);
            if (!dictionary.containSymbol(totalSymbols)) {
                String toDictionary = "";
                int index = 0;
                do {
                    toDictionary += String.valueOf(totalSymbols.charAt(index));
                    index++;
                } while (dictionary.containSymbol(toDictionary));
                dictionary.getDictionary().add(new Symbol(dictionary.getDictionary().size() + 1, toDictionary));
                totalSymbols = totalSymbols.substring(index - 1);
            }
        }
        if (!totalSymbols.isEmpty()) {
            fileWriter.write(totalSymbols);
        }
        fileReader.close();
        fileWriter.close();
        //dictionary.printDictionary();
    }
    ////////////////////////////////////////////////////////////////////////
    /*    public void encodeText() throws IOException {
            File fileForWrite = FileHelper.openFile("D:\\data\\LZWCoded.txt");
            FileWriter fileWriter= new FileWriter(fileForWrite);
            System.out.println("Source file ");
            //File file = FileHelper.openFile();
            File file = new File("D:\\data\\CourseworkLZW.txt");//openFile();
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                String word = scanner.next();
                if (word.isEmpty()) {
                    break;
                }
                System.out.println(encodeWord(word, fileWriter).toString());
            }
            fileWriter.close();
            dictionary.printDictionary();
        }*/
  /*  public List<Integer> encodeWord(String word) throws IOException {
        FileOutputStream fileOutputStream = new FileOutputStream("D:\\data\\1.txt");
        DataOutputStream dataOutputStream = new DataOutputStream(fileOutputStream);
        List<Integer> result=new ArrayList<>();
        char[] symbolsArray = word.toCharArray();
        String totalSymbols="";
        int symbolCode=-1;
        for (int i = 0; i < symbolsArray.length; i++) {
            String currentSymbols=totalSymbols+symbolsArray[i];
            if(dictionary.containSymbol(currentSymbols)){
                totalSymbols=currentSymbols;
            }else {
                dictionary.getDictionary().add(new Symbol(dictionary.getDictionary().size()+1,currentSymbols));
                dataOutputStream.writeByte(dictionary.getSymbolCode(totalSymbols));
                //result.add(dictionary.getSymbolCode(totalSymbols));
                i--;
                totalSymbols="";//String.valueOf(symbolsArray[i]);
            }
        }
        if(!totalSymbols.isEmpty()){
            dataOutputStream.write(dictionary.getSymbolCode(totalSymbols));
        }
        return result;
    }*/

/*    public void decodeText() throws IOException {
        File fileOutput = new File("D:\\data\\LZWCoded.txt");
        File fileInput = new File("D:\\data\\LZWDecoded.txt");
        Dictionary dictionary = new Dictionary(new ArrayList<>());
        FileWriter fileWriter = new FileWriter(fileInput);
        FileReader fileReader =  new FileReader(fileOutput);
        int code=-1;
        String totalSymbols="";
        boolean ff=false;
        while ((code = fileReader.read()) != -1)  {
            String currentSymbols=null;
            if(dictionary.getSymbolByCode(code)==null&&!totalSymbols.isEmpty()&&dictionary.containSymbol(totalSymbols)){
                fileWriter.write(totalSymbols);
                currentSymbols= totalSymbols+String.valueOf(totalSymbols.charAt(0));
                ff=true;
            }else {
                 currentSymbols = totalSymbols + dictionary.getSymbolByCode(code);
            }
            if(dictionary.containSymbol(currentSymbols)){
                totalSymbols=currentSymbols;
            }else {
                dictionary.getDictionary().add(new Symbol(dictionary.getDictionary().size()+1,currentSymbols));
                      if(!ff) {
                          for (int i = 0; i < currentSymbols.length() - 1; i++) {
                              fileWriter.write(currentSymbols.charAt(i));
                          }
                          totalSymbols = String.valueOf(currentSymbols.charAt(currentSymbols.length() - 1));
                      }else {
                          totalSymbols=currentSymbols;
                          ff=false;
                      }
            }
        }
        if(!totalSymbols.isEmpty()){
            fileWriter.write(totalSymbols);
        }
        fileReader.close();
        fileWriter.close();
        dictionary.printDictionary();
    }*/
/*    public void decodeText() throws IOException {
        File fileOutput = new File("D:\\data\\LZWCoded.txt");
        File fileInput = new File("D:\\data\\LZWDecoded.txt");
        Dictionary dictionary = new Dictionary(new ArrayList<>());
        FileWriter fileWriter = new FileWriter(fileInput);
        FileReader fileReader =  new FileReader(fileOutput);
        int code=-1;
        String totalSymbols="";
        while ((code = fileReader.read()) != -1)  {
            if(dictionary.getSymbolByCode(code)!=null && dictionary.getSymbolByCode(code).equals(" ")){
                fileWriter.write(totalSymbols);
                fileWriter.write(" ");
                totalSymbols="";
                continue;
            }
            String currentSymbols=null;
            if(dictionary.getSymbolByCode(code)==null&&!totalSymbols.isEmpty()&&dictionary.containSymbol(totalSymbols)){
                currentSymbols= totalSymbols+String.valueOf(totalSymbols.charAt(0));
            }else {
                currentSymbols = totalSymbols + dictionary.getSymbolByCode(code);
            }
            if(dictionary.containSymbol(currentSymbols)){
                totalSymbols=currentSymbols;
            }else {
                dictionary.getDictionary().add(new Symbol(dictionary.getDictionary().size()+1,currentSymbols));
                //fileWriter.write(totalSymbols);
                if(currentSymbols.length()==99999) {
                    //  totalSymbols = String.valueOf(currentSymbols.charAt(1));
                }else{
                    for (int i = 0; i < currentSymbols.length()-1; i++) {
                        fileWriter.write(currentSymbols.charAt(i));
                    }
                    totalSymbols = String.valueOf(currentSymbols.charAt(currentSymbols.length()-1));
                }
            }
        }
        if(!totalSymbols.isEmpty()){
            fileWriter.write(totalSymbols);
        }
        fileReader.close();
        fileWriter.close();
        dictionary.printDictionary();
    }*/

 /*   public void decodeText() throws IOException {
        File fileOutput = new File("D:\\data\\LZWCoded.txt");
        File fileInput = new File("D:\\data\\LZWDecoded.txt");
        Dictionary dictionary = new Dictionary(new ArrayList<>());
        FileWriter fileWriter = new FileWriter(fileInput);
        FileReader fileReader =  new FileReader(fileOutput);
        int code=-1;
        String totalSymbols="";
        while ((code = fileReader.read()) != -1)  {
    String       currentSymbols = totalSymbols + dictionary.getSymbolByCode(code);
            if(dictionary.containSymbol(currentSymbols)){
                totalSymbols=currentSymbols;
            }else {
                dictionary.getDictionary().add(new Symbol(dictionary.getDictionary().size()+1,currentSymbols));
                fileWriter.write(totalSymbols);
                totalSymbols = String.valueOf(currentSymbols.charAt(1));
            }
        }
        if(!totalSymbols.isEmpty()){
            fileWriter.write(totalSymbols);
        }
        fileReader.close();
        fileWriter.close();
        dictionary.printDictionary();
    }*/
/*    public void decodeText() throws IOException {
        System.out.println("Source file ");
        //File file = FileHelper.openFile();
        FileInputStream fileInputStream = new FileInputStream("D:\\data\\1.txt");
        DataInputStream dataInputStream = new DataInputStream(fileInputStream);
        FileWriter fileWriter = new FileWriter("D:\\data\\2.txt");
        //FileOutputStream fileOutputStream = new FileOutputStream("D:\\data\\2.txt");
       // DataOutputStream dataOutputStream = new DataOutputStream(fileOutputStream);
        byte code=-1;
        String totalSymbols="";
        while (true) {
           try{
               code = dataInputStream.readByte();
           }catch (EOFException e){
               System.out.println("End file!");
               break;
           }
            String currentSymbols = totalSymbols+dictionary.getSymbolByCode(code);
           if(dictionary.containSymbol(currentSymbols)){
           totalSymbols=currentSymbols;
           }else {
               dictionary.getDictionary().add(new Symbol(dictionary.getDictionary().size()+1,currentSymbols));
             fileWriter.write(totalSymbols);
               //dataOutputStream.write(totalSymbols.getBytes());
               //result.add(dictionary.getSymbolCode(totalSymbols));
               totalSymbols= String.valueOf(currentSymbols.charAt(1));
           }
        }
        if(!totalSymbols.isEmpty()){
             fileWriter.write(totalSymbols);
            //dataOutputStream.write(totalSymbols.getBytes());
        }
    }*/
    /*public List<Integer> decodeWord(int code) throws IOException {
        FileOutputStream fileOutputStream = new FileOutputStream("D:\\data\\2.txt");
        DataOutputStream dataOutputStream = new DataOutputStream(fileOutputStream);
        List<Integer> result=new ArrayList<>();
        String totalSymbols="";
        int symbolCode=-1;
        for (int i = 0; i < symbolsArray.length; i++) {
            String currentSymbols=totalSymbols+symbolsArray[i];
            if(dictionary.containSymbol(currentSymbols)){
                totalSymbols=currentSymbols;
            }else {
                dictionary.getDictionary().add(new Symbol(dictionary.getDictionary().size()+1,currentSymbols));
                dataOutputStream.write(dictionary.getSymbolCode(totalSymbols));
                //result.add(dictionary.getSymbolCode(totalSymbols));
                i--;
                totalSymbols="";//String.valueOf(symbolsArray[i]);
            }
        }
        if(!totalSymbols.isEmpty()){
            dataOutputStream.write(dictionary.getSymbolCode(totalSymbols));
        }
        return result;
    }*/
    //public void writeToFile(List<Integer>)


}

