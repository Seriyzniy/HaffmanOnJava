public class RepeatSymbol implements Comparable<RepeatSymbol>{
    private char symbol;
    private int countRepeaat = 1;
    private String binCode;

    public RepeatSymbol(){};
    public RepeatSymbol(char symb, int count){
        symbol = symb;
        countRepeaat = count;
    }
    public char getSymbol(){
        return symbol;
    }
    public int getCountRepeat(){
        return countRepeaat;
    }
    public void setBinCode(String code){
        binCode = code;
    }
    public void setSymbol(char value){
        symbol = value;
    }
    public void incrementCoutRepeat(){
        countRepeaat++;
    }
    public int compareTo(RepeatSymbol o) {
        return o.getCountRepeat() - this.getCountRepeat();
    }
}
