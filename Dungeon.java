/**
 * Provides utility methods for calculating betting odds, equipping monsters,
 * and running a showdown between two monsters in the dungeon.
 *
 * Bugs: none known.
 *
 * @author David Do
 */
public class Dungeon{

    private static final double ODDS1 = 0.8;
    private static final double ODDS2 = 1.2;
    private static final int TWO_WON = 2;
    // Necessary constants
    private final static int SPACING = 17;
    private final static String LEFT = "Left";
    private final static String RIGHT = "Right";

    /**
     * Prevents instantiation of the Dungeon 
     * class since it only provides static methods.
     *
     * Precondition: none.
     * Postcondition: Dungeon cannot be instantiated.
     */
    private Dungeon(){}

    /**
     * Calculates betting odds for monster1 versus monster2.
     *
     * @param monster1 the first monster
     * @param monster2 the second monster
     * @return odds in favor of monster1 winning
     */
    public static double calculateBettingOdds(Monster monster1,
                                              Monster monster2){
        double power1 = monster1.calculatePower();
        double power2 = monster2.calculatePower();
        double P = power1 / (power1 + power2);
        double odds = P / (1 - P);
        if(monster1.compareTo(monster2) < 0){
            odds *= ODDS1;
        }
        else if(monster1.compareTo(monster2) > 0){
            odds *= ODDS2;
        }
        return odds;
    }

    /**
     * Sends a monster to the armory to apply its armory effect.
     *
     * @param monster the monster to equip
     */
    public static void armory(Monster monster){
        monster.applyArmoryEffect();
    }

    /**
     * Runs a duel between two monsters until one or both die, printing
     * round-by-round details.
     *
     * @param monster1 the left-side monster
     * @param monster2 the right-side monster
     * @return 0 if tie, 1 if monster1 wins, 2 if monster2 wins
     */
    public static int showdown(Monster monster1, Monster monster2){
        int round = 0;
        while(monster1.getVitality() > 0
              && monster2.getVitality() > 0){
            printRound(round);
            printBothMonsters(monster1, monster2);
            monster1.performSpecialAbility(monster2);
            monster2.performSpecialAbility(monster1);
            printAttack(LEFT, monster1.attack(monster2));
            printAttack(RIGHT, monster2.attack(monster1));
            System.out.println("RIGHT attack: "
                               + monster2.attack(monster1));
            if (monster1.getVitality() <= 0) {
                if (monster1.handleDeathrattle()) {
                    System.out.println("monster1 "
                                       + "was resurrected from its "
                                       + "Deathrattle!");
                } else {
                    System.out.println("monster1 has no clones left "
                                       + "and is dead.");
                }
            }

            if (monster2.getVitality() <= 0) {
                if (monster2.handleDeathrattle()) {
                    System.out.println("monster2 was resurrected "
                                       + "from its Deathrattle!");
                } else {
                    System.out.println("monster2 has no clones left "
                                       + "and is dead.");
                }
            }
            if(monster1.getVitality() > 0){
                monster1.rest();
            }

            if(monster2.getVitality() > 0){
                monster2.rest();
            }
            round++;
        }
        boolean poisoned = monster1.isPoisoned()
                           || monster2.isPoisoned();
        printFinalStats(monster1, monster2, poisoned);
        int vitality1 = monster1.getVitality();
        int vitality2 = monster2.getVitality();
        if(vitality1 == 0 && vitality1 == vitality2){
            printTieGame();
            return 0;
        }
        else if(vitality1 > vitality2){
            System.out.println("LEFT");
            return 1;
        }
        System.out.println("RIGHT");
        return TWO_WON;
    }

    /* Below are helper methods to make showdown() work */

    /**
     * Use this method in showdown() to display the stats of both
     * monsters together.
     *
     * @param monster1 Monster on the left side to display stats
     * @param monster2 Monster on the right side to display stats
     */
    public static void printBothMonsters(Monster monster1, Monster monster2) {
        int armorSpacing = calcSpacing(Integer
                                       .toString(monster1.getArmor()));
        int healthSpacing = calcSpacing(Integer
                                        .toString(monster1.getVitality()));
        int strSpacing = calcSpacing(
                             String.format("%.2f", monster1.getSpeed()));
        int monsterSpacing = calcSpacing(monster1.getClass()
                                         .getName());
        String str = String.format( "(%s) %s  (%s)\n"
             + "----------" + "        	" + "----------\n"
             + "A: %d %s A: %d\n"
             + "V: %d %s V: %d\n"
             + "S: %.2f %s S: %.2f\n",
             monster1.getClass().getName(),
             " ".repeat(monsterSpacing),
             monster2.getClass().getName(),
             monster1.getArmor(),
             " ".repeat(armorSpacing),
             monster2.getArmor(),
             monster1.getVitality(),
             " ".repeat(healthSpacing),
             monster2.getVitality(),
             monster1.getSpeed(),
             " ".repeat(strSpacing),
             monster2.getSpeed()
        );
        System.out.println(str);
    }

    /**
     * Helper method for printBothMonsters().
     *
     * @param str String on the left - used to calculate spacing
     * @return number of spaces to put between strings
     */
    public static int calcSpacing(String str) {
        int totalWidth = SPACING;
        int str1Width = str.length();
        int spacing = (totalWidth - str1Width);
        if (spacing < 0) {
            return 0;
        }
        return spacing;
    }

    /**
     * Use this method in showdown() to display the current round.
     *
     * @param round an int of the round (should start at 0)
     */
    public static void printRound(int round) {
        System.out.println();
        System.out.println("Round " + round + ":");
    }

    /**
     * Use this method in showdown() to display the damage each round.
     *
     * @param side   the side of the Monster that invoked attack()
     * @param damage the int (damage) returned from an attack() call
     */
    public static void printAttack(String side, int damage) {
        System.out.printf("%s does %d damage!\n", side, damage);
    }

    /**
     * Use this method in showdown() to display final stats and poison status.
     *
     * @param monster1 Left monster
     * @param monster2 Right monster
     * @param poisoned whether either monster was poisoned
     */
    public static void printFinalStats(Monster monster1,
                                       Monster monster2,
                                       boolean poisoned) {
        System.out.println();
        printBothMonsters(monster1, monster2);
        if (poisoned) {
            System.out.println("A monster was poisoned.");
        }
    }

    /**
     * Use this method in showdown() to display a tie game.
     */
    public static void printTieGame() {
        System.out.println("-------GAME OVER-------");
        System.out.println("TIE: Both monsters died!");
    }

    /**
     * Use this method in showdown() to display the winner.
     *
     * @param side the side of the Monster that won
     */
    public static void printWinner(String side) {
        System.out.println("-------GAME OVER-------");
        System.out.println(side + " monster wins!");
    }
}