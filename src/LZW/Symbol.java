package LZW;


public class Symbol {
    private int code;
    private String symbol;
    public Symbol(int code, char symbol) {
        this.code = code;
        this.symbol = String.valueOf(symbol);
    }
    public Symbol(int code, String symbol) {
        this.code = code;
        this.symbol = symbol;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getSymbol() {
        return symbol;
    }

    @Override
    public String toString() {
        return "\nSymbol{" +
                "code=" + code +
                ", symbol='" + symbol + '\'' +
                '}';
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }
}
