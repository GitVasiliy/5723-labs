public class Main {
    public static void main(String[] args) throws InterruptedException {
        if (args.length > 0) {

            String[] filenames = new String[args.length];

            for (int i = 0; i < args.length; i++) {
                filenames[i] = args[i];
            }

            ReaderWriter readerWriter = new ReaderWriter(filenames);

            readerWriter.scan();

            readerWriter.printMap();
        } else {
            System.out.println("Error. Input filenames!");
            System.exit(0);
        }

    }
}