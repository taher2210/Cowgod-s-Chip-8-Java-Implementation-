import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Chip8 {
    byte[] memory;
    byte[] V;
    int pc;
    int I;
    int opcode;
    int memory_start;
    byte[][] display;
    Display renderer;



    public void initialise() {
   
        memory = new byte[4096];
        V = new byte[16];
        pc = 0x200;
        I = 0;
        opcode = 0;
        memory_start = 0x200;
        display = new byte[32][64];
        renderer = new Display(display);
    }

    public void  loadRom() {
        
        Path path = Paths.get("IBM Logo.ch8");
        byte[] romData;
        try {
            romData = Files.readAllBytes(path);
            System.out.println("ROM LOADED SUCCESSFULLY!!!");
          
        } catch (Exception e) {
            System.out.println("Failed to read the ROM file.");
            e.printStackTrace();
            return;
        }
        int start = 0;
        int end = romData.length;
        for(int i = start; i< end; i++){
            memory[memory_start] = romData[i];
            memory_start++;

      
    }
}
public void emulateCycle(){
    fetch();
    decode();
    renderer.refresh();

}
private void fetch(){
        /*
    Read the instruction that PC is currently pointing at from memory. An instruction is two bytes, so you will need to read two successive bytes from memory and combine them into one 16-bit instruction.
    You should then immediately increment the PC by 2, to be ready to fetch the next opcode. Some people do this during the “execute” stage, 
    since some instructions will increment it by 2 more to skip an instruction, but in my opinion that’s very error-prone. 
    Code duplication is a bad thing. If you forget to increment it in one of the instructions, you’ll have problems. Do it here!
        
    * Fetch the next 2-byte CHIP-8 instruction.
    *
    * Example:
    * pc = 0x200
    * memory[0x200] = 0x60
    * memory[0x201] = 0x0A
    *
    * Shift first byte left:
    * 0x60 -> 0x6000
    *
    * Combine with second byte:
    * 0x6000 | 0x0A = 0x600A
    *
    * "& 0xFF" prevents Java's signed bytes from corrupting the opcode.
    *
    * Finally, move PC to the next instruction (2 bytes ahead).
 */
opcode = ((memory[pc] & 0xFF) << 8) | (memory[pc + 1] & 0xFF);
pc += 2;
System.out.printf("PC=%03X OPCODE=%04X%n", pc - 2, opcode);
}
private void decode(){
  
    /*
 * Useful masks for decoding CHIP-8 opcodes:
 *
 * opcode & 0xF000 -> Keep the 1st hex digit (instruction family)
 *                    Example: 0x6A0F -> 0x6000
 *
 * opcode & 0x0FFF -> Keep the last 3 hex digits (NNN - 12-bit address)
 *                    Example: 0x6A0F -> 0x0A0F
 *
 * opcode & 0x00FF -> Keep the last 2 hex digits (NN - 8-bit value)
 *                    Example: 0x6A0F -> 0x000F
 *
 * opcode & 0x000F -> Keep the last hex digit (N - 4-bit value)
 *                    Example: 0x6A0F -> 0x000F
 */
switch (opcode & 0xF000) {

    case 0x0000:
        switch (opcode & 0x00FF) {

            case 0x00E0:
                // 00E0 - Clear Screen
                for (int y = 0; y < 32; y++) {
                    for (int x = 0; x < 64; x++) {
                        display[y][x] = 0;
                    }
                }
                break;

            case 0x00EE:
                // 00EE - Return from subroutine
                // TODO: Implement when stack and subroutines are added.
                break;
        }
        break;

    case 0x1000:
        // 1NNN - Jump
        pc = opcode & 0x0FFF;
        break;

    case 0x6000:
        // 6XNN - Set VX = NN
        int x6 = (opcode & 0x0F00) >> 8;
        V[x6] = (byte) (opcode & 0x00FF);
        break;

    case 0x7000:
        // 7XNN - VX += NN
        int x7 = (opcode & 0x0F00) >> 8;
        V[x7] = (byte) (V[x7] + (opcode & 0x00FF));
        break;

    case 0xA000:
        // ANNN - Set I = NNN
        I = opcode & 0x0FFF;
        break;

    case 0xD000: //draw display is made using ai assistance as the algorithm was beyond my comprehension
    //This is the most involved instruction. It will draw an N pixels tall sprite f
    // from the memory location that the I index register is holding to the screen, at the horizontal X coordinate in VX and the Y coordinate in VY.
    //  All the pixels that are “on” in the sprite will flip the pixels on the screen that it is drawn to (from left to right, from most to least significant bit).
    //  If any pixels on the screen were turned “off” by this, the VF flag register is set to 1. Otherwise, it’s set to 0.
    int x = (opcode & 0x0F00) >> 8;
    int y = (opcode & 0x00F0) >> 4;
    int N = opcode & 0x000F;

    int startx = V[x] & 0xFF;
    int starty = V[y] & 0xFF;

    startx %= 64;
    starty %= 32;

    V[0xF] = 0;

    for (int i = 0; i < N; i++) {

        int row = memory[I + i] & 0xFF;

        for (int bit = 0; bit < 8; bit++) {

            int mask = 0x80 >> bit;

            if ((row & mask) != 0) {

                int screenX = startx + bit;
                int screenY = starty + i;

                if (screenX < 64 && screenY < 32) {

                    // Collision detection
                    if (display[screenY][screenX] == 1) {
                        V[0xF] = 1;
                    }

                    // XOR draw
                    display[screenY][screenX] ^= 1;
                }
            }
        }
    }
    break;





        




       
}

}

}

