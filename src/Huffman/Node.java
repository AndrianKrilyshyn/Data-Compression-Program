package Huffman;
public class Node {
    private char symbol='d';
    private long frequency=1;
    private Node nextNode = null;
    private int weight=-1;
    private String code;


    public Node(){}
    public Node(char symbol) {
        this.symbol = symbol;

    }

    public char getSymbol() {
        return symbol;
    }

    public int getWeight() {
        return weight;
    }

    public String getCode() {
        return code;
    }
    public Node getNextNode() {
        return nextNode;
    }

    public long getFrequency() {
        return frequency;
    }
    public void setCode(String code) {
        this.code = code;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public void setSymbol(char symbol) {
        this.symbol = symbol;
    }



    public void setFrequency(long frequency) {
        this.frequency = frequency;
    }

    public void setNextNode(Node nextNode) {
        this.nextNode = nextNode;
    }
    public void incFrequency(){
        frequency+=1;
    }
    /*
        @Override
        public String toString() {
            return "Node{" +
                    "symbol=" + symbol +
                    ", frequency=" + frequency +
                    ", prev=" + nextNode +
                    '}';
        }*/
 /*   @Override
    public String toString() {
        return " "+ symbol +"("+frequency+")"+
                " - "+ weight + " - ";
    }*/
    @Override
    public String toString() {
        return " "+ symbol +"("+frequency+")"+" - "+code;
    }
}
