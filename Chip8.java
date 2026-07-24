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



    public void initialise() {
   
        memory = new byte[4096];
        V = new byte[16];
        pc = 0x200;
        I = 0;
        opcode = 0;
        memory_start = 0x200;
        display = new byte[32][64];
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
    execute();
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

    case 0xD000:
                                    /*
                                * DXYN - Draw Sprite
                                *
                                * TODO:
                                *
                                * 1. Extract X, Y and N from the opcode.
                                *    - X = value stored in VX
                                *    - Y = value stored in VY
                                *    - N = sprite height
                                *
                                * 2. Wrap starting coordinates:
                                *    - X %= 64
                                *    - Y %= 32
                                *
                                * 3. Set VF = 0 (collision flag)
                                *
                                * 4. For each of the N sprite rows:
                                *      - Read sprite byte from memory[I + row]
                                *
                                * 5. For each of the 8 bits in the sprite byte:
                                *      - Check if the current sprite bit is 1.
                                *      - If so, XOR it with the display pixel.
                                *      - If a display pixel changes from 1 -> 0,
                                *        set VF = 1.
                                *      - Stop drawing if the right edge of the
                                *        screen is reached.
                                *
                                * 6. Stop drawing if the bottom edge of the
                                *    screen is reached.
                                *
                                * Notes:
                                * - Sprite data is stored in memory starting at I.
                                * - VX and VY contain the starting coordinates.
                                * - Display size is 64x32.
                                * - Drawing uses XOR.
                                * - I, VX and VY are NOT modified.
                                */
        break;
}

}
private void execute(){

}

}
