public class Die
{
    /** Instance Variables **/

    private int numSides;

    /** Constructors **/

    public Die(int numSides) {
        if (numSides < 2) {
            this.numSides = 6;
        } else {
            this.numSides = numSides;
        }
    }

    public Die() {
        numSides = 6;
    }

    /** Methods **/

    /**
     * Returns the number of sides on the Die.
     */
    public int getSides() {
        return numSides;
    }

    /**
     * Returns a random int between 1 and
     * the number of sides on the Die
     */
    public int roll() {
        return (int)(Math.random() * (numSides - 1) + 1);
    }

    /**
     * Rolls the dice the numRolls times
     * and returns the max value of the rolls
     */
    public int getMaxRoll(int numRolls) {
        int max = 0;
        for (int i = 0; i < numRolls; i++) {
            max = Math.max(max, this.roll());
        }
        return max;
    }

    /**
     * Returns a String in the following form:
     * "This is a n-sided die."
     */
    public String toString() {
        return "this is a " + this.roll() + "die.";
    }
}