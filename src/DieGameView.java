// Imports classes used for the window to display information
import java.awt.*;
import javax.swing.ImageIcon;
import javax.swing.JFrame;

public class DieGameView extends JFrame {
    // Defines final properties of the window that stay constant
    private final int WINDOW_WIDTH = 1500;
    private final int WINDOW_HEIGHT = 1000;
    private final int BANNER_HEIGHT = 23;

    // Instance variables to keep track of the back end
    private Image[] diceFaces;
    private DieGame d;

    // Constructor for class that initializes instance variables and calls helper methods
    public DieGameView(DieGame d) {
        this.d = d;
        diceFaces = new Image[7];
        // Calls loadDiceFaces method found below
        loadDiceFaces();
        // Adds window properties necessary for it too work and appear
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setTitle("--*Dice Roulette*--");
        this.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        this.setVisible(true);
    }

    // Helper method that loads each dice face (as well as an undecided one) into the diceFaces array
    private void loadDiceFaces() {
        // Each of these images are found in the resources folder
        diceFaces[0] = new ImageIcon("Resources/dice-six-faces-one.png").getImage();
        diceFaces[1] = new ImageIcon("Resources/dice-six-faces-two.png").getImage();
        diceFaces[2] = new ImageIcon("Resources/dice-six-faces-three.png").getImage();
        diceFaces[3] = new ImageIcon("Resources/dice-six-faces-four.png").getImage();
        diceFaces[4] = new ImageIcon("Resources/dice-six-faces-five.png").getImage();
        diceFaces[5] = new ImageIcon("Resources/dice-six-faces-six.png").getImage();
        diceFaces[6] = new ImageIcon("Resources/perspective-dice-six-faces-random.png").getImage();
    }

    // Draws the dice found on the left of the window and the background (the lines) onto the window
    public void drawDiceAndBackground(Graphics g) {
        // Draws a white background too start
        g.drawRect(0,0,1500,1000);
        g.setColor(Color.WHITE);
        g.fillRect(0,0,1500,1000);
        // Draws the dice faces depending on what their number is, each case has two drawImage for top and bottom dice
        if (!(d.getDiceRolls()[0] - 1 < 0 || d.getDiceRolls()[1] - 1 < 0)) {
            g.drawImage(diceFaces[d.getDiceRolls()[0] - 1], 100, 100, 300, 300, this);
            g.drawImage(diceFaces[d.getDiceRolls()[1] - 1], 100, 600, 300, 300, this);
        } else {
            g.drawImage(diceFaces[6], 100, 100, 300, 300, this);
            g.drawImage(diceFaces[6], 100, 600, 300, 300, this);
        }
        // Draws the lines found in the background
        g.setColor(Color.BLACK);
        g.drawLine(500, 0, 500, 1000);
        g.drawLine(500, 500, 1500, 500);
    }

    // Special helper function that uses math to center the text in a box when its displayed
    public void displayCenteredText(Graphics g, String text, int width, int height, int xPos, int yPos, int yShift) {
        int w = width;
        int h = height;
        // Uses FontMetrics object to get properties about the current font such as pixel height and total string width
        FontMetrics metrics = g.getFontMetrics(g.getFont());
        // Calculates correct x and y position such that the center of the text is centered in the chosen box
        int x = xPos + (w - metrics.stringWidth(text)) / 2;
        int y = yPos + ((h - metrics.getHeight()) / 2) + metrics.getAscent();
        // Prints the string onto the window using the calculated x and y coordinates as well as the veritcal shift
        g.drawString(text, x, y - yShift);
    }

    // This method displays each unique gameState onto the window
    public void drawGameState(Graphics g) {
        // It receives the current game state from the back end object
        int state = d.getGameState();
        // Checks the conditions where there is no game state (does nothing)
        if (state > -1) {
            // If there is a game state it draws the gray rectangle everything is printed on top of
            g.drawRect(150, 150, 1200, 700);
            g.setColor(Color.GRAY);
            g.fillRect(150,150,1200,700);
        } else {
            drawDiceAndBackground(g);
            drawInfo(g);
            return;
        }
        g.setColor(Color.BLACK);
        // Cases for each unique game state that would potentially be displayed at different points along the game
        // Start game state
        if (state == 0) {
            g.setFont(new Font("Serif", Font.BOLD, 46));
            displayCenteredText(g, "Press Enter to Start The Game", 1200, 700, 150, 150, 0);
        }
        // You won money state
        else if (state == 1) {
            g.setFont(new Font("Serif", Font.BOLD, 46));
            displayCenteredText(g, "You Won!", 1200, 700, 150, 150, 100);
            g.setFont(new Font("Serif", Font.BOLD, 36));
            displayCenteredText(g, "Answer y to play again or n to quit", 1200, 700, 150, 150, -100);
        }
        // You lost money state
        else if (state == 2) {
            g.setFont(new Font("Serif", Font.BOLD, 46));
            displayCenteredText(g, "Your Bet Lost!", 1200, 700, 150, 150, 100);
            g.setFont(new Font("Serif", Font.BOLD, 36));
            displayCenteredText(g, "Answer y to play again or n to quit", 1200, 700, 150, 150, -100);
        }
        // You went bankrupt state (game over)
        else if (state == 3) {
            g.setFont(new Font("Serif", Font.BOLD, 46));
            displayCenteredText(g, "Game Over!", 1200, 700, 150, 150, 100);
            g.setFont(new Font("Serif", Font.BOLD, 36));
            displayCenteredText(g, "Better Luck Next Time!", 1200, 700, 150, 150, -100);
        }
        // Exit game state (thanks for playing)
        else if (state == 4) {
            g.setFont(new Font("Serif", Font.BOLD, 46));
            displayCenteredText(g, "Thank you for playing!", 1200, 700, 150, 150, 100);
            g.setFont(new Font("Serif", Font.BOLD, 36));
            displayCenteredText(g, "Your Final Balance Was: $" + d.getMoney(), 1200, 700, 150, 150, -100);
        }
        // Display instructions state
        else if (state == 5) {
            g.setFont(new Font("Serif", Font.PLAIN, 36));
            displayCenteredText(g, "Welcome to Dice Roulette!", 1200, 700, 150, 150, 300);
            g.setFont(new Font("Serif", Font.PLAIN, 30));
            displayCenteredText(g, "Instructions Below:", 1200, 700, 150, 150, 200);
            displayCenteredText(g, "To play you get $100 to gamble with, on each turn a"
                    + "pair of dice are rolled", 1200, 700, 150, 150, 100);
            displayCenteredText(g, "Before each turn you can bet on either a number (fr"
                    + "om 2 - 12)", 1200, 700, 150, 150, 0);
            displayCenteredText(g,  "that the dice will add up too, or bet"
                    + " between even or odd.", 1200, 700, 150, 150, -50);
        }
    }

    // Draws all the information about current bets and game status
    public void drawInfo(Graphics g) {
        g.setFont(new Font("Serif", Font.PLAIN, 36));
        displayCenteredText(g, "Your Current Bet is:", 1000, 500, 500, 0, 200 - BANNER_HEIGHT);
        // Checks if there is no present information to print out -- instead of a number
        if (d.getBetAmount() == 0) {
            displayCenteredText(g, "$--" + " on " + d.getCurrentBet(), 1000, 500, 500, 0, 100 - BANNER_HEIGHT);
        } else {
            displayCenteredText(g, "$"+ d.getBetAmount() + " on " + d.getCurrentBet(), 1000, 500, 500, 0, 100 - BANNER_HEIGHT);
        }
        // Prints out the current balance remaining in the bottom right square
        displayCenteredText(g, "Your Current Balance is:", 1000, 500, 500, 500, 200);
        displayCenteredText(g, "$"+ d.getMoney(), 1000, 500, 500, 500, 100);
    }

    // Paint method which calls all above helper methods in order to fully pain the visual display above
    public void paint(Graphics g) {
        drawDiceAndBackground(g);
        drawInfo(g);
        drawGameState(g);
    }
}