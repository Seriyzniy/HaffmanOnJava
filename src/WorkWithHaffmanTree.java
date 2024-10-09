import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WorkWithHaffmanTree {
    private List<NodeTree> nodes = new ArrayList<>();
    private Map<Character, String> haffmanCodes = new HashMap<>();
    //
    //Creating a Huffman tree from alfabet
    private NodeTree FindMin(){//TODO
        NodeTree minRepeat = nodes.getLast();
        for(int i = nodes.size()-2; i >= 0; i--){
            if(nodes.get(i).getWeight() < minRepeat.getWeight()){
                minRepeat = nodes.get(i);
            }else if(nodes.get(i).getWeight() > minRepeat.getWeight()) {
                nodes.remove(minRepeat);
                return minRepeat;
            }
        }
        nodes.remove(minRepeat);
        return minRepeat;
    }
    private void CreateNodesFromAlfabet(List<RepeatSymbol> alfabet){
        for(RepeatSymbol rs : alfabet){
            nodes.add(new NodeTree(rs.getSymbol(), rs.getCountRepeat()));
        }
    }
    public NodeTree CreateTree(List<RepeatSymbol> alfabet){//TODO либо просто брать два последних узла, либо долбиться с min1 min2
        CreateNodesFromAlfabet(alfabet);
        while(nodes.size() > 1){
            NodeTree right = FindMin();
            NodeTree left = FindMin();

            NodeTree parent = new NodeTree(null, left.getWeight() + right.getWeight(), left,right);
            parent.getLeft().setPar(parent);
            parent.getRight().setPar(parent);
            nodes.add(parent);
        }
        return nodes.getFirst();
    }
    //
    //Print Huffman tree
    private boolean isLeafOrNull(NodeTree node){
        if(node == null){
            return true;
        }
        return (node.getLeft() == null && node.getRight()==null);
    }
    public void OutTree(){
        //The upper branch is left branch tree, and low branch is right
        NodeTree head = nodes.getFirst();
        NodeTree currentNode = head.getLeft();
        int countSpase = 0;

        System.out.print(head.getWeight());

        while(currentNode != head){
            //Go to the left leaf
            while(currentNode.getLeft() != null && currentNode.getRight() != null){
                System.out.print("\n" + " ".repeat(countSpase)+ "└──"+currentNode.getWeight());
                countSpase += 3;
                currentNode = currentNode.getLeft();
            }
            System.out.print("\n" + " ".repeat(countSpase)+ "└──"+currentNode.getSymbol());

            //Go up, while right brother is leaf of null
            while(currentNode != head && isLeafOrNull(currentNode.getPar().getRight())){
                if(currentNode.getPar().getRight() != null){
                    System.out.print("\n" + " ".repeat(countSpase)
                            + "└──"+ currentNode.getPar().getRight().getSymbol());
                }
                currentNode = currentNode.getPar();
                countSpase -= 3;
                //Go up, if the current node is right child for parent
                while(currentNode != head && currentNode == currentNode.getPar().getRight()){
                    currentNode = currentNode.getPar();
                    countSpase -= 3;
                }
            }
            if(currentNode == head) break;
            currentNode = currentNode.getPar().getRight();
        }
        System.out.print("\n");
    }
    //
    //Create Codes
    private NodeTree getLeftLeaf(NodeTree source){
        while(source.getLeft() != null && source.getRight() != null){
            source = source.getLeft();
        }
        return source;
    }
    public void CreateHaffmanCodes(){
        //Left step 0, right 1
        StringBuilder binaryCode = new StringBuilder(10);

        NodeTree head = nodes.getFirst();
        NodeTree currentNode = head.getLeft();
        binaryCode.append(0);

        while(currentNode != head){
            //Go to the left leaf
            while(currentNode.getLeft() != null && currentNode.getRight() != null){
                currentNode = currentNode.getLeft();
                binaryCode.append(0);
            }
            haffmanCodes.put(currentNode.getSymbol(), binaryCode.toString());

            //Go up if right brother is leaf of null
            while(currentNode != head && isLeafOrNull(currentNode.getPar().getRight())){
                if(currentNode.getPar().getRight() != null) {
                    binaryCode.deleteCharAt(binaryCode.length()-1);
                    binaryCode.append(1);
                    haffmanCodes.put(currentNode.getPar().getRight().getSymbol(),binaryCode.toString());
                }
                currentNode = currentNode.getPar();
                binaryCode.deleteCharAt(binaryCode.length()-1);

                while(currentNode != head && currentNode == currentNode.getPar().getRight()){
                    currentNode = currentNode.getPar();
                    binaryCode.deleteCharAt(binaryCode.length()-1);
                }
            }
            if(currentNode == head) break;
            currentNode = currentNode.getPar().getRight();
            binaryCode.deleteCharAt(binaryCode.length()-1);
            binaryCode.append(1);
        }
    }
    public Map<Character, String> getHaffmanCodes(){
        return haffmanCodes;
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
