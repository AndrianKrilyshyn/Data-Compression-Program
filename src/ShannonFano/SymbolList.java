package ShannonFano;



import util.FileHelper;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class SymbolList {

    private  double numberOfSymbols;
    private List<Symbol> listSymbols;
    private SymbolList left;
    private SymbolList right;
    private SymbolList next;
    private int weight = 2;
    private boolean operated = false;
    public SymbolList(List<Symbol> listSymbols){
        this.listSymbols=listSymbols;
    }

    public File entryListFromFile() throws IOException {
        System.out.println(" Source file");
        // File file = FileHelper.openFile();
        File file = new File("G:\\data\\ShannonCoursework.txt");
        Scanner scanner = new Scanner(file);
        while (scanner.hasNextLine()) {
            String word = scanner.nextLine();
            if (word.isEmpty())
                break;
            for (int i = 0; i < word.length(); i++) {
                char symbol = word.charAt(i);
                createNewSymbol(symbol);
                numberOfSymbols++;
            }
        }
        return file;
    }
    public File entryListFromKeyboard() throws IOException {
        System.out.println("\n Needed temporary file ");
        File file = new File("G:\\data\\ShannonCoursework.txt");
        //File file = FileHelper.openFile();
        FileWriter fileWriter = new FileWriter(file);
        //FileWriter fileWriter = new FileWriter("G:\\data\\ShannonCoursework.txt");//newFilewriter();
        System.out.println(" Enter text:");
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()) {
            String word = scanner.nextLine();
            fileWriter.write(word);
            if (word.isEmpty())
                break;
            for (int i = 0; i < word.length(); i++) {
                char symbol = word.charAt(i);
                createNewSymbol(symbol);
            }
        }
        fileWriter.close();
        return file;
    }

    public void createNewSymbol(char symbol) {
        if (!inSymbolList(symbol)) {
            Symbol symb = new Symbol(symbol);
            listSymbols.add(symb);
        }
    }
    public boolean inSymbolList(char symb) {
        for (Symbol symbol : listSymbols) {
            if (symbol.getSymbol() == symb) {
                symbol.incFrequency();
                return true;
            }
        }
        return false;
    }
    public void setUpProbability(){
        for (Symbol symbol: listSymbols) {
            symbol.setProbability(symbol.getFrequency()/numberOfSymbols);
        }
    }
    public void createConnection(List<Symbol> listSymbols) {
        //System.out.println(listSymbols);
        int i = 0;
        int j = listSymbols.size() - 1;
        if(listSymbols.size()>2) {
            double sum1 = listSymbols.get(i++).getProbability();
            double sum2 = listSymbols.get(j--).getProbability();
            while (i < j) {
                if (sum1 > sum2) {
                    sum2 += listSymbols.get(j--).getProbability();
                } else {
                    sum1 += listSymbols.get(i++).getProbability();
                }

            }
            //System.out.println(" LIST 1:");
            left = new SymbolList(new ArrayList<>(listSymbols.subList(0, i)));
            //System.out.println(left + "\n PR: " + sumProbability(left.getListSymbols()));
            //System.out.println(" LIST 2:");
            right = new SymbolList(new ArrayList<>(listSymbols.subList(j, listSymbols.size())));
        }else{
            left = new SymbolList(new ArrayList<>(listSymbols.subList(0, i+1)));
            right = new SymbolList(new ArrayList<>(listSymbols.subList(j, listSymbols.size())));

        }
        left.setNext(this);
        right.setNext(this);
        left.setWeight(0);
        right.setWeight(1);
        //System.out.println(right + "\n PR: " + sumProbability(right.getListSymbols()));
        operated=true;
    }
    public double sumProbability(List<Symbol> symbols){
        double result=0;
        for (Symbol symbol: symbols) {
            result+=symbol.getProbability();
        }
        return result;
    }

    public List<Symbol> getListSymbols() {
        return listSymbols;
    }

    public void setListSymbols(List<Symbol> listSymbols) {
        this.listSymbols = listSymbols;
    }

    public SymbolList getLeft() {
        return left;
    }

    public void setLeft(SymbolList left) {
        this.left = left;
    }

    public SymbolList getRight() {
        return right;
    }

    public void setRight(SymbolList right) {
        this.right = right;
    }

    public boolean isOperated() {
        return operated;
    }

    public SymbolList getNext() {
        return next;
    }

    public void setNext(SymbolList next) {
        this.next = next;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public String getCode() {
        return this.listSymbols.get(0).getCode();
    }

    public void setCode(String code) {
        this.listSymbols.get(0).setCode(code);
    }

    @Override
    public String toString() {
        return "SymbolList{"+ listSymbols +
                '}';
    }
}

