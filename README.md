# chip8-java

A CHIP-8 emulator, written from scratch in Java, with no AI assistance in the implementation.


## Why CHIP-8

CHIP-8 is a simple interpreted language from the 1970s designed to make writing games easier on early hobbyist computers. It has a tiny instruction set (~35 opcodes), 4KB of memory, and a 64x32 monochrome display. It's small enough to fully understand end-to-end, but still involves real emulator concepts: a fetch-decode-execute loop, memory-mapped state, registers, timers, and rendering a framebuffer.

## Goals (in order)

- [ ] Render a static window (Swing/AWT) showing a blank 64x32 grid, scaled up
- [ ] Load a ROM file into memory at `0x200`
- [ ] Implement just enough opcodes to run the **IBM logo** test ROM
- [ ] Implement the full CHIP-8 opcode table
- [ ] Handle input (16-key hex keypad mapped to keyboard)
- [ ] Handle delay timer / sound timer
- [ ] Pass standard CHIP-8 test ROMs (opcode test, flags test, etc.)
- [ ] Play a real CHIP-8 game (Pong, Tetris, Space Invaders) end to end

## Tech stack

- **Java** (no external emulation libraries)
- **Swing/AWT** for rendering — a `BufferedImage` blitted onto a `JPanel`, scaled up from the native 64x32 resolution
- No build tool decided yet (plain `javac` to start, may move to Maven/Gradle later)

## Project structure 

```
chip8-java/
├── src/
│   └── chip8/
│       ├── Chip8.java       # core: memory, registers, PC, stack, timers, fetch/decode/execute
│       ├── Display.java     # 64x32 framebuffer + rendering
│       ├── Keypad.java      # input handling
│       └── Main.java        # entry point, load ROM, run loop
├── roms/
│   └── ibm_logo.ch8
└── README.md
```

## Reference material

- Cowgod's CHIP-8 Technical Reference — the standard spec doc, read in full before writing any code
- IBM logo test ROM — minimal ROM that only exercises a handful of opcodes (`00E0`, `1NNN`, `6XNN`, `ANNN`, `DXYN`), used as the first milestone before the full opcode table
