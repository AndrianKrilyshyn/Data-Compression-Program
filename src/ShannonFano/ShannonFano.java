package ShannonFano;

import Huffman.NodeList;
import util.FileHelper;

import java.io.*;
import java.util.*;

public class ShannonFano {
        private List<Symbol> symbols;
        private List<SymbolList> symbolLists;

        public ShannonFano(List<SymbolList> symbolLists) {
            this.symbolLists = symbolLists;
        }

        public File createTree(int mode) throws IOException {
            SymbolList symbolList = new SymbolList(new ArrayList<>());
            File file;
            if(mode==1) {
                file=symbolList.entryListFromFile();
            }else {
                file=symbolList.entryListFromKeyboard();
            }
            symbolList.setUpProbability();
            symbolList.getListSymbols().sort(new Comparator<Symbol>() {
                @Override
                public int compare(Symbol o1, Symbol o2) {
                    return o1.getProbability()>o2.getProbability()?-1:1;
                }
            });
            symbolLists.add(symbolList);
            for (int i = 0; i < symbolLists.size(); i++) {
                if(!symbolLists.get(i).isOperated()&&symbolLists.get(i).getListSymbols().size()>1){
                    symbolLists.get(i).createConnection(symbolLists.get(i).getListSymbols());
                    symbolLists.add(symbolLists.get(i).getLeft());
                    symbolLists.add(symbolLists.get(i).getRight());
                    i=0;
                }
            }
            return file;
        }
        public File encodeText(File inputFile) throws IOException {
            System.out.println("Source file ");
           // File inputFile = new File("G:\\data\\ShannonCoursework.txt");
            System.out.println("Binary output file ");
            File outputFile = new File("G:\\data\\ShannonCourseworkRes.bin");//FileHelper.openFile();

            FileOutputStream fileOutputStream = new FileOutputStream(outputFile);
            DataOutputStream dataOutputStream = new DataOutputStream(fileOutputStream);
            Scanner scanner = new Scanner(inputFile);

            while (scanner.hasNextLine()) {
                String word = scanner.nextLine();
                for (int i = 0; i < word.length(); i++) {
                    char symbol = word.charAt(i);
                    for (int j = 0; j < symbolLists.size(); j++) {
                        if (symbolLists.get(j).getListSymbols().get(0).getSymbol() == symbol) {
                            String code = symbolLists.get(j).getListSymbols().get(0).getCode();
                            byte[] symbolCode = NodeList.convertToByte(code);
                            for (int k = 0; k < symbolCode.length; k++) {
                                dataOutputStream.writeByte(symbolCode[k]);
                            }
                            break;
                        }
                    }
                }
            }

            dataOutputStream.close();
            fileOutputStream.close();
            return outputFile;
        }

        public void decodeText(File inputFile) throws IOException {
            //System.out.println("Binary input file: ");
            //File inputFile = new File("G:\\data\\ShannonCourseworkRes.bin");
            System.out.println(" Decoded output file: ");
            File outputFile = new File("G:\\data\\ShannonCourseworkDecode.txt");//FileHelper.openFile();
            FileWriter fileWriter = new FileWriter(outputFile);

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
                    //System.out.println("End file!");
                    break;
                }
                byteString += tempByteString;
                String checkCode = "";
                for (int i = 0; i < byteString.length(); i++) {
                    char symbol = byteString.charAt(i);
                    checkCode += symbol;
                    for (int j = 0; j < symbolLists.size(); j++) {
                        if (symbolLists.get(j).getListSymbols().get(0).getCode().equals(checkCode)) {
                            fileWriter.write(symbolLists.get(j).getListSymbols().get(0).getSymbol());
                            byteString = "";
                            checkCode = "";
                            break;
                        }
                    }
                }
            }
            fileInputStream.close();
            dataInputStream.close();
            fileWriter.close();


        }

//        public static void main(String[] args) {
//            ShannonFano shannonFano = new ShannonFano(new ArrayList<>());
//            try {
//                shannonFano.createTree();
//            } catch (IOException e) {
//                throw new RuntimeException(e);
//            }
//            try {
//                shannonFano.encodeText();
//            } catch (IOException e) {
//                throw new RuntimeException(e);
//            }
//            try {
//                shannonFano.decodeText();
//            } catch (IOException e) {
//                throw new RuntimeException(e);
//            }
//        }
    public void printNext(){
        for (int i = 0; i < symbolLists.size(); i++) {
            SymbolList sf2 = symbolLists.get(i);
            while (sf2!=null){
                System.out.println("i = "+i+" value "+sf2);
                sf2=sf2.getNext();
            }
        }
    }
    public void printCodes(){
        for (SymbolList element:symbolLists) {
            System.out.println("Symbol: "+element.getListSymbols().get(0).getSymbol()+" code: "+element.getCode());
        }
    }
    public boolean isDubble(){
        for (int i = 0; i < symbolLists.size(); i++) {
            String code = symbolLists.get(i).getCode();
            for (int j = i+1; j < symbolLists.size(); j++) {
                if(code.equals(symbolLists.get(j).getCode())){
                    System.out.println(" DUBBLE");
                    return false;
                }
            }
        }
        for (int i = 0; i < symbolLists.size(); i++) {
            String code = symbolLists.get(i).getCode();
            String coded="";
            for (int j = 0; j < code.length(); j++) {
                coded += code.charAt(j);
                for (int k = i+1; k < symbolLists.size(); k++) {
                    if (symbolLists.get(k).getCode().equals(coded)) {
                        System.out.println(" ERROR DUBBLLES \n SYMBOL1 "+symbolLists.get(k).getCode() + " CODE "+ coded+" CUR CODE "+code);
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public void createCode() {
        for (int i = 0; i < symbolLists.size(); i++) {
            String code = "";
            SymbolList tempNode = symbolLists.get(i);
            while (tempNode.getNext() != null) {
                code += tempNode.getWeight();
                tempNode = tempNode.getNext();
            }
            StringBuilder temp = new StringBuilder(code);
            temp.reverse();
            code = temp.toString();
            symbolLists.get(i).setCode(code);
        }
    }

    public void printConnections(SymbolList symbolList){
        if(symbolList==null){
            return;
        }
        System.out.println(symbolList);
        printConnections(symbolList.getLeft());
        printConnections(symbolList.getRight());
    }

    public void clearList(){
        Iterator<SymbolList> iterator = symbolLists.iterator();
        while (iterator.hasNext()){
            SymbolList sf2M = iterator.next();
            if(sf2M.getListSymbols().size()!=1){
                iterator.remove();
            }
        }
    }
    public void printCode(List<Symbol> symbolLists){
        for (Symbol element:symbolLists) {
            System.out.println("Symbol: "+element.getSymbol()+" code: "+element.getCode());
        }
    }
    public void printCodes(List<SymbolList> symbolLists){
        for (SymbolList element:symbolLists) {
            System.out.println("Symbol: "+element.getListSymbols().get(0).getSymbol()+" code: "+element.getCode());
        }
    }
//    System.out.println(" CONNECTIONS:");
//    printConnections(symbolList);
//    System.out.println(" CODES:");
//    //createCode(symbolList,"");
//    createCode();
//    printCodes(symbolLists);
//    System.out.println(" RESULT CLEAR");
//    clearList(symbolLists);
//            System.out.println(" PRINT NEXT");
//    printNext();
//            System.out.println(" PRINT CODES");
//    printCodes();
//    //   printCode(symbols);
//            System.out.println("NO DUBLLES? "+isDubble());

}

//  Create new Symbol list of SymbolList list
//    public void clearList(List<SymbolList> symbolLists){
//        Iterator<SymbolList> iterator = symbolLists.iterator();
//        while (iterator.hasNext()){
//            if(iterator.next().getListSymbols().size()>1){
//                iterator.remove();
//            }
//        }
//    }
//  Create code by recursion
//        public void createCode(SF2 symbolList, String code){
//            if(symbolList.getLeft()==null){
//                code=symbolList.getWeight()+code;
//                code=code.substring(0,code.length()-1);
//                symbolList.setCode(code);
//                System.out.println(" END CODE: symbol: "+symbolList.getListSymbols().get(0)+" code: "+code);
//                return;
//            }
//
//            System.out.println(symbolList);
//            code=symbolList.getWeight()+code;
//            System.out.println(code);
//            createCode(symbolList.getLeft(),code);
//            createCode(symbolList.getRight(),code);
//        }



