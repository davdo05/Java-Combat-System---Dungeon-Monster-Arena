/**
 * A Jubilex is an Ooze monster that gains armor when resting,
 * has corrosive attacks, and can kit itself in the armory.
 *
 * Bugs: none known.
 *
 * @author David Do
 */
public class Jubilex extends Ooze {
    private static final int REST = 10000;
    private static final int VITALITY_CONSTANT = 70;
    private static final int VOLUME_CONSTANT = 350;
    private static final int ACID_CONSTANT = 100;
    private static final double BOUND = 0.01;
    private static final int MULTIPLIER = 100;
    private static final double UPPER_BOUND = 0.95;
    private static final double MIN_CONSTANT = 0.005;
    private static final double MAX_CONSTANT = 0.5;
    private static final int TOTAL_ARMORY = 3;
    private static final int DOUBLE = 2;

    /**
     * Constructs a Jubilex with default stats (armor=0, vitality=0,
     * speed=0.0, volume=0, acidity=0).
     * Precondition: none.
     * Postcondition: fields are initialized to zero.
     */
    public Jubilex() {
        super();
    }

    /**
     * Constructs a Jubilex with specified armor, vitality, speed,
     * volume, and acidity.
     *
     * @param armor    initial armor value
     * @param vitality initial vitality value
     * @param speed    initial speed value
     * @param volume   initial volume value
     * @param acidity  initial acidity value
     * Precondition: none.
     * Postcondition: fields set accordingly.
     */
    public Jubilex(int armor, int vitality,
                   double speed, int volume, int acidity) {
        super(armor, vitality, speed, volume, acidity);
    }

    /**
     * Rests this Jubilex, increasing its armor by a fixed amount.
     * Precondition: none.
     * Postcondition: armor increased by REST.
     */
    @Override
    public void rest() {
        int oldArmor = getArmor();
        setArmor(oldArmor + REST);
    }

    /**
     * Calculates power based on vitality, volume, acidity, and
     * applies a critical multiplier with high probability.
     *
     * @return calculated power value
     * Precondition: none.
     * Postcondition: none.
     */
    @Override
    public double calculatePower() {
        int vitality = getVitality();
        int volume = getVolume();
        int acidity = getAcidity();
        double power = VITALITY_CONSTANT * vitality
                     + VOLUME_CONSTANT * volume
                     + ACID_CONSTANT * acidity;
        double random = Math.random();
        if (random > BOUND) {
            power *= MULTIPLIER;
        }
        return power;
    }

    /**
     * Attempts to corrode; succeeds 95% of the time.
     *
     * @return true if corrosion occurs, false otherwise
     * Precondition: none.
     * Postcondition: none.
     */
    @Override
    public boolean corrode() {
        double random = Math.random();
        if (random > UPPER_BOUND) {
            return false;
        }
        return true;
    }

    /**
     * Attacks the target monster, reducing its armor or vitality
     * based on strike value range determined by volume.
     *
     * @param monster the Monster to attack
     * @return the strike value applied
     * Precondition: monster is not null.
     * Postcondition: monster's armor or vitality reduced.
     */
    @Override
    public int attack(Monster monster) {
        double power = calculatePower();
        int volume = getVolume();
        double min = power - MIN_CONSTANT * volume;
        double max = power + MAX_CONSTANT * volume;
        double temp = min + Math.random() * (max - min);
        int armor = monster.getArmor();
        int vitality = monster.getVitality();
        int strikeValue = (int) Math.floor(temp);
        if (strikeValue <= 0) {
            return 0;
        }
        if (strikeValue < armor) {
            monster.setArmor(armor - strikeValue);
        }
        if (strikeValue >= armor) {
            monster.setVitality(vitality - (strikeValue - armor));
            monster.setArmor(0);
        }
        return strikeValue;
    }

    /**
     * Applies armory effect: first applies Ooze effect, then
     * doubles either armor, vitality, or speed randomly.
     * Precondition: none.
     * Postcondition: one stat doubled.
     */
    @Override
    public void applyArmoryEffect() {
        super.applyArmoryEffect();
        int random = (int) (Math.random() * TOTAL_ARMORY);
        int armor = getArmor();
        int vitality = getVitality();
        double speed = getSpeed();
        if (random == 0) {
            setArmor(armor * DOUBLE);
        } else if (random == 1) {
            setVitality(vitality * DOUBLE);
        } else if (random == DOUBLE) {
            setSpeed(speed * DOUBLE);
        }
    }
}