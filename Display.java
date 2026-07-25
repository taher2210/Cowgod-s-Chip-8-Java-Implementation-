import java.awt.*;
import javax.swing.*; //GUI IS  VIBECODED

public class Display extends JPanel {

    private final byte[][] display;
    private static final int SCALE = 10;

    public Display(byte[][] display) {
        this.display = display;

        JFrame frame = new JFrame("CHIP-8");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(this);
        frame.pack();
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(64 * SCALE, 32 * SCALE);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(Color.BLACK);
        g.fillRect(0, 0, getWidth(), getHeight());

        g.setColor(Color.WHITE);

        for (int y = 0; y < 32; y++) {
            for (int x = 0; x < 64; x++) {
                if (display[y][x] == 1) {
                    g.fillRect(
                        x * SCALE,
                        y * SCALE,
                        SCALE,
                        SCALE
                    );
                }
            }
        }
    }

    public void refresh() {
        repaint();
    }
}