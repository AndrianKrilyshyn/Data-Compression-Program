package Dictionary;


import LZW.Symbol;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Random;

public class  Dictionary{
    private  List<Symbol> dictionary;

    public Dictionary(List<Symbol> dictionary) {
        this.dictionary = dictionary;
        try {
            generateBaseDictionary();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Symbol> getDictionary() {
        return dictionary;
    }

    public void setDictionary(List<Symbol> dictionary) {
        this.dictionary = dictionary;
    }

    private void generateBaseDictionary() throws IOException {
        for (int i = 1040, index = 0; i < 1104; i++,index++) {
            this.dictionary.add(new Symbol(index,(char) i));
        }
        dictionary.add(new Symbol(dictionary.size()+1,'і'));
        dictionary.add(new Symbol(dictionary.size()+1,'І'));
        dictionary.add(new Symbol(dictionary.size()+1,'ї'));
        dictionary.add(new Symbol(dictionary.size()+1,'Ї'));
        dictionary.add(new Symbol(dictionary.size()+1,'є'));
        dictionary.add(new Symbol(dictionary.size()+1,'Є'));
        for (int i = 32, index = dictionary.size()+1; i < 58; i++, index++) {
            this.dictionary.add(new Symbol(index,(char) i));
        }
//        Random random = new Random();
//        FileWriter fileWriter = new FileWriter("G:\\data\\1.txt");
//        for (int i = 0; i < 100_000_000; i++) {
//            char c = (char)random.nextInt(1040,1104);
//            fileWriter.write(c);
//        }

    }

    public int getSymbolCode(String symb){
        for (Symbol symbol:dictionary) {
            if(symbol.getSymbol().equals(symb)){
                return symbol.getCode();
            }
        }
        return -1;
    }
    public boolean containSymbol(String symb){
        for (Symbol symbol: dictionary) {
            if(symbol.getSymbol().equals(symb)){
                return true;
            }
        }
        return false;
    }

    public String getSymbolByCode(int code){
        for (Symbol symbol:dictionary) {
            if(symbol.getCode()==code){
                return symbol.getSymbol();
            }
        }
        return null;
    }
    public boolean containCode(int code){
        for (Symbol symbol: dictionary) {
            if(symbol.getCode()==code){
                return true;
            }
        }
        return false;
    }
    public void printDictionary(){
        System.out.println(dictionary.toString());
    }
}
