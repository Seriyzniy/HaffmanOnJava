public class Main {
    public static void main(String[] args) {
        String origMessage = "Avada kedavra vada";

        Haffman h = new Haffman();
        h.CompressMessage(origMessage);
        h.DecompressMessage();

        h.printCompressMessage();
        h.printDecompressMessage();
        System.out.println("Length orig message = " + origMessage.length() +
                "\nSize CHAR in bites = " + 16 +
                "\nOriginal message size in bites (lenth * sizeofCHAR) = " + origMessage.length()*16);
    }
}
