// Imports scanner to take user input
import java.util.Scanner;

public class DieGame {
    private DieGameView window;
    private RunGame game;

    private int gameState;
    // Main function to call a version of the game and run it
    public static void main(String[] args) {
        // Makes DieGame object and runs the game
        DieGame game1 = new DieGame();
        game1.runGame();
    }

    public DieGame() {
        window = new DieGameView(this);
        game = new RunGame(this);
        gameState = 0;
    }

    public int getGameState() {
        return gameState;
    }

    public void setGameState(int gameState) {
        this.gameState = gameState;
    }

    public int[] getDiceRolls() {
        return game.getCurrentDiceVals();
    }

    // Prints the instructions you see at the beginning of every game
    public static void printInstructions() {
        System.out.println("Welcome to Dice Roulette!");
        System.out.println("\nInstructions Below:\n");
        System.out.println("To play you get $100 to gamble with, on each turn a"
                + "pair of dice are rolled");
        System.out.println("Before each turn you can bet on either a number (fr"
                + "om 2 - 12) that the dice will add up too, or bet"
                + " between even or odd.");
    }

    // Checks the type of bet the user inputted to make sure its format is valid
    public String checkBetType(String response, Scanner sc) {
        String repResponse = response;
        boolean isAnswerVerified = false;
        Integer answerInt;

        // While loop to check and reprompt user until they enter a correct format
        while (!isAnswerVerified) {
            // Main if statement that checks if the response they inputted is
            //                                          an acceptable length
            if (repResponse.length() == 9 || repResponse.length() == 10 ||
                    repResponse.length() == 3 || repResponse.length() == 4) {

                // Checks which type of bet it is (Number, odd, or even) and
                //                          checks if the formatting is valid
                if (repResponse.indexOf("Number: ") == 0 ||
                        repResponse.indexOf("odd") == 0 ||
                        repResponse.indexOf("even") == 0) {
                    isAnswerVerified = true;
                }

                // Checks special case for the Number bet type with a double
                //                                              digit number
                if (repResponse.length() == 10) {
                    answerInt = Integer.valueOf(repResponse.substring(8, 10));
                    if (answerInt < 2 || answerInt > 12) {
                        isAnswerVerified = false;
                    }
                }

                // Checks special case for the Number bet type with a single
                //                                              digit number
                if (repResponse.length() == 9) {
                    answerInt = Integer.valueOf(repResponse.substring(8, 9));
                    if (answerInt < 2) {
                        isAnswerVerified = false;
                    }
                }

                // Checks special case where if you enter "oddd" it doesnt read
                //                                                  it as even
                if (repResponse.length() == 4) {
                    if (repResponse.indexOf("odd") != -1) {
                        isAnswerVerified = false;
                    }
                }
            }

            // If the previous answer didn't pass the checks it reprompts the user
            if (!isAnswerVerified) {
                System.out.println("\nPlease re-enter your answer in the correc"
                        + "t format to continue playing the game!\n");
                repResponse = sc.nextLine();
            }
            window.repaint();
        }

        // Returns the answer that satifies all the above conditions
        return repResponse;
    }

    // Checks the number the user enters as a bet amount
    public int checkBetAmount(String response, Scanner sc, RunGame g1) {
        int valRep = 0;
        String repResponse = response;
        boolean isAnswerVerified = false;

        // While loop to verify and reprompt user to enter a new number if
        //                                                      necessary
        while (!isAnswerVerified) {
            // Reads in the number the user typed in
            try {
                valRep = Integer.parseInt(repResponse);
                // Checks if the number is one of the acceptable amounts
                if (valRep == 5 || valRep == 10 || valRep == 25 || valRep == 50) {
                    if (g1.getMoney() >= valRep) {
                        isAnswerVerified = true;
                    }
                }
            } catch (Exception e) {}

            // Reprompts user if they entered an incorrect number
            if (!isAnswerVerified) {
                System.out.println("\nPlease re-enter your bet amount, you ente"
                        + "red it incorrectly the first time. Make sure to enter is sim"
                        + "ilarly to the follow example: \"25\"");
                System.out.println("Please re-enter your amount below:\n");

                // Reads in next input for the next iteration of while loop
                repResponse = sc.nextLine();
            }
            window.repaint();
        }

        // Returns the number that passes the above checks
        return valRep;
    }

    public static void startGame(Scanner sc) {
        String response;
        do {
            System.out.println("Press enter to start the game: ");
            response = sc.nextLine();
        } while (response.isEmpty());
    }
    // Runs the main game script, and calls functions from other classes
    //                                      necessary to run the full game
    public void runGame() {

        // Prints the instructions by calling the above function
        printInstructions();
        // Creates a scanner object in order to be able to read in user input
        Scanner sc = new Scanner(System.in);
        gameState = 0;
        window.repaint();
        startGame(sc);
        int whenToPrompt = 0;
        String response;
        String typeBet;
        int betAmount;
        int val;
        gameState = 0;
        window.repaint();
        // Goes into a loop that runs the main part of the game including
        //              simulating results and getting betting amounts etc
        while (game.getMoney() > 0) {
            // Prompts the user to enter a bet type
            System.out.println("Here comes your dice roll! What would you like "
                    + "to bet on?");
            System.out.println("\nRemember that you can bet on either a number"
                    + " (from 2 - 12) that the dice will add up too, or bet between even"
                    + " or odd.\n");
            System.out.println("Please enter your answer in the form \"Number: "
                    + "{your number}\" or \"odd\" or \"even\".\n");

            // Reads in input of the type of bet
            response = sc.nextLine();

            // Checks the user input to make sure they inputted a valid betType
            typeBet = checkBetType(response, sc);

            // Prompts the user to enter a bet amount
            System.out.println("\nPlease enter your bet amount (you can only be"
                    + "t, 5, 10, 25, or 50 dollars).");
            System.out.println("If your bet is even or odd and you win, your m"
                    + "oney gets increased by 0.7x that you bet. Otherwise, you lose al"
                    + "l the money you bet.");
            System.out.println("If your bet is a specific number and you win, y"
                    + "our money gets increased by 4x that you bet. Otherwise, you lose"
                    + "all the money you bet.");
            System.out.println("Now please go ahead and enter your bet amount"
                    + "(example entry = \"50\") below:\n");

            // Reads in input of bet amount
            response = sc.nextLine();
            System.out.println("\n");

            // Checks the user input to make sure they inputted a valid betAmount
            betAmount = checkBetAmount(response, sc, game);

            // Simulates whether they lose or gain money depending on their bet
            game.simulateResult(typeBet, betAmount);

            // Checks if they are bankrupt and when to quit the game
            if (game.getMoney() <= 0) {
                System.out.println("\n");
                System.out.println("Sorry, you went bankrupt! You can no longer"
                        + " continue this round of the game.");
                System.out.println("Thanks for playing! Better luck next time!");
                gameState = 3;
                window.repaint();
                whenToPrompt = 0;
            }

            // Makes sure the user wants to keep playing the game before moving 
            //                                              onto the next round
            if (whenToPrompt == 1) {
                // Prompts user to get their input on whether to continue or not
                System.out.println("If you want to continue playing, type \"y\""
                        + ", otherwise type \"n\".");
                gameState = 0;
                window.repaint();
                response = sc.nextLine();
                if (response.equals("n")) {
                    break;
                }
            }
            whenToPrompt = 1;
            System.out.println("\n");
        }

        // Ends the game in case the user quit playing or went bankrupt
        // Prints their final money balance so that the user knows
        System.out.println("\nThank you for playing! Your final bank balance is"
                + ": $" + game.getMoney() + ".");
        gameState = 4;
        window.repaint();
    }
}