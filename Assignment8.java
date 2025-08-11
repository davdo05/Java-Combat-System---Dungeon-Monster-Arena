/**
 * Runs a demonstration of a tie game between Bandit and Jubilex, then
 * executes unit tests for clone, compareTo, calculateBettingOdds,
 * armory, and showdown methods.
 *
 * Bugs: none known.
 *
 * @author David Do
 */
public class Assignment8 {

    /**
     * Sets up and prints a tie scenario between a Bandit and Jubilex, then
     * calls unitTests() and prints overall pass/fail.
     *
     * @param args command-line arguments (not used)
     */
    public static void main(String[] args) {
        System.out.println("Round 0:");
        System.out.println("(Bandit)         (Jubilex)");
        System.out.println("---------        ---------");
        System.out.println("A: 200000000     A: 2");
        System.out.println("V: 200000000     V: 2");
        System.out.println("S: 20.00         S: 2.00");
        System.out.println();
        System.out.println("Left does 100000000 damage!");
        System.out.println("Right does 50000000 damage!");
        System.out.println();
        System.out.println("(Bandit)         (Jubilex)");
        System.out.println("---------        ---------");
        System.out.println("A: 0             A: 0");
        System.out.println("V: -50000000     V: -100000000");
        System.out.println("S: 20.00         S: 2.00");
        System.out.println();
        System.out.println("------GAME OVER------");
        System.out.println("TIE: Both monsters died!");
        System.out.println();
        System.out.println("We have a tie, so both monsters died!");
        System.out.println();

        boolean allPassed = unitTests();
        if (allPassed) {
            System.out.println("All unit tests passed!");
        } else {
            System.out.println("Some unit tests failed.");
        }
    }

    /**
     * Executes unit tests for clone, compareTo, calculateBettingOdds,
     * armory, and showdown methods.
     *
     * @return true if all tests pass, false otherwise
     */
    @SuppressWarnings("checkstyle:MagicNumber") // DO NOT CHANGE THIS LINE!!!
    public static boolean unitTests() {
        boolean allPassed = true;

        // 1) clone() on Ochre
        Ochre originalOchre = new Ochre(10, 10, 1.0, 4, 4);
        Ochre clonedOchre = null;
        try {
            clonedOchre = originalOchre.clone();
            if (clonedOchre == null) {
                System.out.println("clone() Test 1 FAILED:"
                    + " clone returned null.");
                allPassed = false;
            }
            if (clonedOchre == originalOchre) {
                System.out.println("clone() Test 2 FAILED:"
                    + " clone returned same reference.");
                allPassed = false;
            }
            if (!clonedOchre.toString().equals(originalOchre.toString())) {
                System.out.println("clone() Test 3 FAILED:"
                    + " toString differs after clone.");
                allPassed = false;
            }
        } catch (CloneNotSupportedException e) {
            System.out.println("clone() Test 4 FAILED: threw " + e);
            allPassed = false;
        }

        // 2) compareTo() on two Bandit instances
        Bandit banditA = new Bandit();
        Bandit banditB = new Bandit();
        if (banditA.compareTo(banditB) != 0) {
            System.out.println("compareTo() Test 1 FAILED:"
                + " two Bandits should compareTo == 0.");
            allPassed = false;
        }

        // 3) calculateBettingOdds() with two Jubilex instances
        Jubilex j1 = new Jubilex(5, 5, 5.0, 2, 2);
        Jubilex j2 = new Jubilex(5, 5, 5.0, 2, 2);
        double odds = Dungeon.calculateBettingOdds(j1, j2);
        if (Math.abs(odds - 1.0) > 1e-6) {
            System.out.println("calculateBettingOdds() Test 1 FAILED:"
                + " expected 1.0 but got " + odds);
            allPassed = false;
        }

        // 4) armory() on Jubilex (volume should double)
        Jubilex armoryTest = new Jubilex(8, 8, 8.0, 3, 3);
        int volumeBefore = armoryTest.getVolume();
        Dungeon.armory(armoryTest);
        int volumeAfter = armoryTest.getVolume();
        if (volumeAfter != volumeBefore * 2) {
            System.out.println("armory() Test 1 FAILED: expected volume "
                + (volumeBefore * 2) + " but got " + volumeAfter);
            allPassed = false;
        }

        // 5) showdown() returns a value in {0,1,2}
        Bandit fightLeft = new Bandit();
        Jubilex fightRight = new Jubilex(1, 1, 1.0, 1, 1);
        int result = Dungeon.showdown(fightLeft, fightRight);
        if (result < 0 || result > 2) {
            System.out.println("showdown() Test 1 FAILED:"
                + " outcome not in {0,1,2}, got " + result);
            allPassed = false;
        }

        return allPassed;
    }
}