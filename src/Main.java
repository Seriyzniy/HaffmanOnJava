import java.util.*;


//___________________________Main function__________________________________________
public class Main {
    public static void main(String[] args) {
        Haffman h = new Haffman();
        h.CompressMessage("Super puper mega jopa");
        h.OutAlfabet();
    }
}
//__________________________________________________________________________________

class Haffman{
    private List<RepeatSymbol> alfabet = new ArrayList<>();
    private List<NodeTree> haffmanTree = new ArrayList<>();
    //
    //Creating a message alfabet
    private void CreateAlfabet(String message){
        RepeatSymbol readSymbol = new RepeatSymbol();
        RepeatSymbol symbInAlfabet = new RepeatSymbol();

        for(Character c : message.toCharArray()){
            symbInAlfabet = inAlfabet(c);
            if(symbInAlfabet != null){
                symbInAlfabet.incrementCoutRepeat();
            }
            else{
                readSymbol.setSymbol(c);
                alfabet.add(new RepeatSymbol(readSymbol.getSymbol(), readSymbol.getCountRepeat()));
            }
        }
        Collections.sort(alfabet);
    }
    private RepeatSymbol inAlfabet(char symbol){
        for(RepeatSymbol i : alfabet){
            if(i.getSymbol()== symbol){
                return i;
            }
        }
        return null;
    }
    public void OutAlfabet(){
        for(RepeatSymbol i : alfabet){
            System.out.print(i.getSymbol() + " " + i.getCountRepeat() + " \n");
        }
    }
    //
    //Creating a Huffman tree
    private NodeTree FindMin(){//TODO
        NodeTree minRepeat = haffmanTree.getLast();
        for(int i = haffmanTree.size()-2; i >= 0; i--){
            if(haffmanTree.get(i).getWeight() < minRepeat.getWeight()){
                minRepeat = haffmanTree.get(i);
            }else if(haffmanTree.get(i).getWeight() > minRepeat.getWeight()) {
                haffmanTree.remove(minRepeat);
                return minRepeat;
            }
        }
        haffmanTree.remove(minRepeat);
        return minRepeat;
    }
    private void CreateNodesFromAlfabet(){
        for(RepeatSymbol rs : alfabet){
            haffmanTree.add(new NodeTree(rs.getSymbol(), rs.getCountRepeat()));
        }
    }
    private void CreateTree(){//TODO либо просто брать два последних узла, либо долбиться с min1 min2
        while(haffmanTree.size() > 1){
            NodeTree right = FindMin();
            NodeTree left = FindMin();

            NodeTree parent = new NodeTree(null, left.getWeight() + right.getWeight(), left,right);
            parent.getLeft().setPar(parent);
            parent.getRight().setPar(parent);
            haffmanTree.add(parent);
        }
    }
    public void OutTree(){
        NodeTree head = haffmanTree.getFirst();
        NodeTree currentNode = head;
        NodeTree lastPassed = null;


    }
    //
    //Main function
    public void CompressMessage(String message){
        CreateAlfabet(message);
        CreateNodesFromAlfabet();
        CreateTree();
    }
}

class NodeTree{
    private Character symbol;
    private int weight;
    private NodeTree left;
    private NodeTree right;
    private NodeTree par;

    public NodeTree(Character smbl,int wght){
        symbol = smbl;
        weight = wght;
    }
    public NodeTree(Character smbl,int wght, NodeTree left, NodeTree right){
        symbol = smbl;
        weight = wght;
        this.left = left;
        this.right = right;
    }
    //Setters
    public void setSymbol(char smb){
        symbol = smb;
    }
    public void setWeight(int wght){
        weight = wght;
    }
    public void setPar(NodeTree parent){
        par= parent;
    }
    //Getters
    public int getWeight() {
        return weight;
    }
    public char getSymbol() {
        return symbol;
    }
    public NodeTree getLeft(){
        return left;
    }
    public NodeTree getRight() {
        return right;
    }
    public NodeTree getPar() {
        return par;
    }
}

class RepeatSymbol implements Comparable<RepeatSymbol>{
    private char symbol;
    private int countRepeaat = 1;

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