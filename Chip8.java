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



    public void initialise() {
   
        memory = new byte[4096];
        V = new byte[16];
        pc = 0x200;
        I = 0;
        opcode = 0;
        memory_start = 0x200;
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

}
private void execute(){

}

}
