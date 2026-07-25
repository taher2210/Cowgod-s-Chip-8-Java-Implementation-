public class Main {

    public static void main(String[] args) {

        Chip8 chip8 = new Chip8();

        chip8.initialise();
        chip8.loadRom();

        while (true) {
            chip8.emulateCycle();

            try {
                Thread.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}