package ShannonFano;

import Huffman.Node;

public class Symbol extends Node {
    private double probability;
    private Node nextNode = null;
    public Symbol(char symbol){
        this.symbol=symbol;
    }

    public double getProbability() {
        return probability;
    }

    public void setProbability(double probability) {
        this.probability = probability;
    }



    //    @Override
//    public String toString() {
//        return "\nsymbol=" + symbol +
//                "probability=" + probability +
//                ", frequency=" + frequency +
//                ", weight=" + weight +
//                ", code='" + code ;
//    }
@Override
public String toString() {
    return  symbol+ " ";// + probability;
}
}
