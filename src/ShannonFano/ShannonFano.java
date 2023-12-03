package ShannonFano;

import java.io.IOException;
import java.util.*;

public class ShannonFano {
    private List<Symbol> symbols;
    private List<SymbolList> symbolLists;

    public ShannonFano(List<SymbolList> symbolLists) {
        this.symbolLists = symbolLists;
    }

    public void createTree() throws IOException {
        SymbolList symbolList = new SymbolList(new ArrayList<>());
        symbolList.entryListFromFile();
        System.out.println(" ENTRY:");
        System.out.println(symbolList);
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
        }////////////
        System.out.println(" CONNECTIONS:");
        printConnections(symbolList);
        System.out.println(" CODES:");
        createCode(symbolList,"");
        System.out.println(" RESULT NOT CLEAR");
        printCodes(symbolLists);
        System.out.println(" RESULT CLEAR");
         symbols = clearList(symbolLists);
        printCode(symbols);
        System.out.println("NO DUBLLES? "+isDubble(symbols));

    }
    public boolean isDubble(List<Symbol> symbols){
        for (int i = 0; i < symbols.size(); i++) {
            String code = symbols.get(i).getCode();
            for (int j = i+1; j < symbols.size(); j++) {
                if(code.equals(symbols.get(j).getCode())){
                    System.out.println(" DUBBLE");
                    return false;
                }
            }
        }
        return true;
    }
    public void createCode(SymbolList symbolList, String code){
        if(symbolList.getLeft()==null){
            code=symbolList.getWeight()+code;
            code=code.substring(0,code.length()-1);
            symbolList.setCode(code);
            System.out.println(" END CODE: symbol: "+symbolList.getListSymbols().get(0)+" code: "+code);
            code=code.substring(0,code.length()-1);
            return;
        }

        System.out.println(symbolList);
        code=symbolList.getWeight()+code;
        System.out.println(code);
        createCode(symbolList.getLeft(),code);
        createCode(symbolList.getRight(),code);
    }
    public void printConnections(SymbolList symbolList){
        if(symbolList==null){
            return;
        }
        System.out.println(symbolList);
        printConnections(symbolList.getLeft());
        printConnections(symbolList.getRight());
    }

//    public void clearList(List<SymbolList> symbolLists){
//        Iterator<SymbolList> iterator = symbolLists.iterator();
//        while (iterator.hasNext()){
//            if(iterator.next().getListSymbols().size()>1){
//                iterator.remove();
//            }
//        }
//    }

    public List<Symbol> clearList(List<SymbolList> symbolLists){
        List<Symbol> symbols = new ArrayList<>();
        for (SymbolList element:symbolLists) {
            if(element.getListSymbols().size()==1) {
                symbols.add(element.getListSymbols().get(0));
            }
        }
        Collections.sort(symbols, new Comparator<Symbol>() {
            @Override
            public int compare(Symbol o1, Symbol o2) {
                return (o1.getSymbol()-o2.getSymbol());
            }
        });
        return symbols;
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

    public static void main(String[] args) {
        ShannonFano shannonFano = new ShannonFano(new ArrayList<>());
        try {
            shannonFano.createTree();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
