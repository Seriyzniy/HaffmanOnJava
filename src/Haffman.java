import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class Haffman {
    private List<RepeatSymbol> alfabet = new ArrayList<>();
    private WorkWithHaffmanTree treeService  = new WorkWithHaffmanTree();
    private int blockSize = 16;

    private Map<Character, String> codes;
    private String decompressMessage = "";
    private List<Short> compressMessage = new ArrayList<>();
    {
        compressMessage.add((short)0);
    }
    private int lastBuzyBit = 0;
    private int countWord = 0;
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
        System.out.print("\n");
    }
    //
    //Write in compress message
    private void WriteInCompressMessage(Short code, int bitsLenght){
        int offsetHighBits = 0;
        int offsetLowBits = 0;
        int freeSizeBlock = 0;
        short result = 0;

        if(lastBuzyBit == 0 || lastBuzyBit == blockSize){
            offsetHighBits = blockSize - bitsLenght;
            lastBuzyBit = blockSize - offsetHighBits;
        }
        else{
            freeSizeBlock = blockSize - lastBuzyBit;
            if(freeSizeBlock >= bitsLenght){
                offsetHighBits = freeSizeBlock - bitsLenght;
                lastBuzyBit = blockSize - offsetHighBits;
            }
            else{
                offsetHighBits = bitsLenght - freeSizeBlock;
                offsetLowBits = blockSize - offsetHighBits;
                lastBuzyBit = blockSize - offsetLowBits;
            }
        }
        if(offsetLowBits == 0){
            result = (short) ((code << offsetHighBits) | compressMessage.get(countWord));
            compressMessage.set(countWord, result);
            if(lastBuzyBit == blockSize){
                countWord++;
                compressMessage.add((short)0);
            }
        }
        else{
            result = (short) ((code >> offsetHighBits) | compressMessage.get(countWord));
            compressMessage.set(countWord, result);
            countWord++;
            compressMessage.add((short)0);
            result = (short) ((code << offsetLowBits) | compressMessage.get(countWord));
            compressMessage.set(countWord, result);
        }
    }
    //
    //Coder Message
    public void CompressMessage(String message){
        short code = 0;

        StringBuilder changedMessage = new StringBuilder(message);
        changedMessage.append('#');                 //escape symbol

        CreateAlfabet(changedMessage.toString());
        OutAlfabet();
        treeService.CreateTree(alfabet);
        treeService.OutTree();
        treeService.CreateHaffmanCodes();
        codes = treeService.getHaffmanCodes();

        for(char c : changedMessage.toString().toCharArray()){
            code = (short)Integer.parseInt(codes.get(c), 2);
            WriteInCompressMessage(code, codes.get(c).length());
        }
    }
    //
    //Decoder message
    private char GetSymbolInDict(String bitCode){
        for(char symb : codes.keySet()){
            if(bitCode.equals(codes.get(symb)) ){
                return symb;
            }
        }
        return 0;
    }
    public void DecompressMessage(){
        int readableElement = compressMessage.get(0);
        readableElement = readableElement | -2147483648;
        String readableElementStr = Integer.toBinaryString(readableElement);
        String SymbolCode = "";
        char decoderSymbol;

        for(int readableBit = blockSize, indexElement = 0; readableBit >= 0; readableBit++){
            SymbolCode += readableElementStr.charAt(readableBit);

            decoderSymbol = GetSymbolInDict(SymbolCode);
            if(decoderSymbol == '#'){
                break;
            }
            if(decoderSymbol != 0){
                decompressMessage += decoderSymbol;
                SymbolCode = "";
            }
            if(readableBit == 31){
                readableBit = blockSize-1;
                indexElement++;
                readableElement = compressMessage.get(indexElement);
                readableElement = readableElement | -2147483648;
                readableElementStr = Integer.toBinaryString(readableElement);
            }
        }
    }
    //
    //Output info
    public void printTree(){
        treeService.OutTree();
    }
    public void getHaffmanCodes(){
        if(!codes.isEmpty()) {
            System.out.println(treeService.getHaffmanCodes() + "\n");
        }
    }
    public void printCompressMessage(){//todo
        String binaryStr;
        System.out.println("Compress message: ");
        for(short i : compressMessage){
            binaryStr = Integer.toBinaryString(i & 0xFFFF);
            String formattedBinaryString = String.format("%16s", binaryStr).replace(' ', '0');
            System.out.println(i + "\t" + formattedBinaryString);
        }
        System.out.println("Size message in bites = " + blockSize * compressMessage.size()  + "\n");
    }
    public void printDecompressMessage(){
        if(!decompressMessage.isEmpty()){
            System.out.println("Decompress message: " + decompressMessage  + "\n");
        }
    }
}