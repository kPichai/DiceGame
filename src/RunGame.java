public class RunGame {
    // Defines instance variables
    // Amount of money in this instances bank
    private int money;

    // Creates 2 dice objects used to roll the dice later in the game
    private Die d1 = new Die();
    private Die d2 = new Die();
    private int[] currentDiceVals;

    // Constructor which sets the inital money amount too $100
    public RunGame() {
        money = 100;
        currentDiceVals = new int[2];
    }

    // Returns the amount of money the user has
    public int getMoney() {return money;}

    // Simulates a double roll of the dice using both d1 and d2
    public int[] simulateRoll() {
        int[] diceRolls = {d1.roll(), d2.roll()};
        return diceRolls;
    }

    public int[] getCurrentDiceVals() {
        return currentDiceVals;
    }

    // Creates a bet type used in "simulateResult" in order to check which type
    //                                      of bet the user put their money on
    public int createBetType(String betType) {
        // Case for even bet
        if (betType.equals("even")) {return 0;}

        // Case for odd bet
        if (betType.equals("odd")) {return 1;}

        // Case for Number bet where the number is 2 digits
        if (betType.length() == 10) {
            return Integer.valueOf(betType.substring(8, 10));
        }

        // Case for Number bet where the number is 1 digit only
        if (betType.length() == 9) {
            return Integer.valueOf(betType.substring(8, 9));
        }

        // Case in which there is an error and it doesnt match any bet type
        return -1;
    }

    // Simulates the result of the bet, whether the user wins or loses money
    // Takes in the type of the bet the user gave and the amount they bet
    public void simulateResult(String typeBet, int betAmount) {
        int tempMoney = money;

        // Runs simulateRoll in order to get the sum of the 2 rolled dice
        int[] diceRolls = simulateRoll();
        int doubleRoll = diceRolls[0] + diceRolls[1];
        currentDiceVals = diceRolls;
        System.out.println("Your first and second die combined rolled a: "
                + doubleRoll + "!");

        // Converts the string typeBet into an int for easier comprehension
        int betType = createBetType(typeBet);

        // Case where the betType was an error
        if (betType == -1) {
            System.out.print("Error");
            return;
        }

        // Case where the user bet even
        if (betType == 0) {
            // Checks if number rolled is even
            if (doubleRoll % 2 == 0) {
                money += (int)(betAmount * 0.7);
            } else {
                // If they lose they subtract the betted money
                money -= betAmount;
            }
        }

        // Case where the user bet odd
        else if (betType == 1) {
            // Checks if number rolled is odd
            if (doubleRoll % 2 == 1) {
                money += (int)(betAmount * 0.7);
            } else {
                // If they lose they subtract the betted money
                money -= betAmount;
            }
        }

        // Extra case where the user bet a specific number the dice would roll
        else {
            // Checks if number rolled is the correct number
            if (doubleRoll == betType) {
                money += (betAmount * 3);
            } else {
                // If they lose they subtract the betted money
                money -= betAmount;
            }
        }

        // Checked if they lost or gained money
        if (tempMoney > money) {
            System.out.println("\nOh no, it looks like your bet failed!\n");
            System.out.println("Your new bank account balance is: $" + money +
                    ". Better luck next time!");
        } else {
            System.out.println("\nCongrats, it looks like your bet was "
                    + "successful!\n");
            System.out.println("Your new bank account balance is: $" + money +
                    ". I hope your luck continues!");
        }
    }
}