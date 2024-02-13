import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JFrame;

public class DieGameView extends JFrame {
    private final int WINDOW_WIDTH = 1500;
    private final int WINDOW_HEIGHT = 1000;
    private Image[] diceFaces;
    private DieGame d;

    public DieGameView(DieGame d) {
        this.d = d;
        diceFaces = new Image[6];
        loadDiceFaces();

        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setTitle("--*Dice Roulette*--");
        this.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        this.setVisible(true);
    }

    private void loadDiceFaces() {
        diceFaces[0] = new ImageIcon("Resources/dice-six-faces-one.png").getImage();
        diceFaces[1] = new ImageIcon("Resources/dice-six-faces-two.png").getImage();
        diceFaces[2] = new ImageIcon("Resources/dice-six-faces-three.png").getImage();
        diceFaces[3] = new ImageIcon("Resources/dice-six-faces-four.png").getImage();
        diceFaces[4] = new ImageIcon("Resources/dice-six-faces-five.png").getImage();
        diceFaces[5] = new ImageIcon("Resources/dice-six-faces-six.png").getImage();
    }

    public void drawDiceAndBackground(Graphics g) {
        // d.getDiceRolls()[0] - 1
        // d.getDiceRolls()[1] - 1
        g.drawRect(0,0,1500,1000);
        g.setColor(Color.WHITE);
        g.fillRect(0,0,1500,1000);
        g.drawImage(diceFaces[1], 100, 100, 300, 300, this);
        g.drawImage(diceFaces[2], 100, 600, 300, 300, this);
        g.setColor(Color.BLACK);
        g.drawLine(500, 0, 500, 1000);
        g.drawLine(500, 500, 1500, 500);
    }

    public void drawGameState(Graphics g) {
        return;
    }

    public void drawInfo(Graphics g) {
        return;
    }

    public void paint(Graphics g) {
        drawDiceAndBackground(g);
        drawGameState(g);
        drawInfo(g);
    }
}