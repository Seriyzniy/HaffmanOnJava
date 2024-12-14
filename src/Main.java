public class Main {
    public static void main(String[] args) {
        String origMessage = "Avada kedavra vada";

        Haffman h = new Haffman();
        h.CompressMessage(origMessage);
        h.OutAlfabet();
        h.printTree();
        h.DecompressMessage();

        h.getHaffmanCodes();
        h.printCompressMessage();
        h.printDecompressMessage();

        System.out.println("Length orig message (count of symbol) = " + origMessage.length() +
                "\nSize CHAR in bites = " + 16 +
                "\nOriginal message size in bites (lenth * sizeofCHAR) = " + origMessage.length()*16 + "bits");
        h.saveBytesToFile();
    }
}
