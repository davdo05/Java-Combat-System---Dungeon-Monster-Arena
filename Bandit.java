/**
 * A Bandit is a Humanoid monster that can rest, calculate power,
 * strike, attack, and equip in the dungeon armory.
 *
 * Bugs: none known.
 *
 * @author David Do
 */
public class Bandit extends Humanoid {
    private static final int REST = 30;
    private static final double AXE_VITALITY = 0.65;
    private static final double AXE_INTELLIGENCE = 0.35;
    private static final double AXE_SPEED = 0.1;
    private static final double CROSSBOW_VITALITY = 0.25;
    private static final double CROSSBOW_INTELLIGENCE = 0.5;
    private static final double CROSSBOW_SPEED = 0.25;
    private static final double SHIELD_ARMOR = 0.7;
    private static final double SHIELD_VITALITY = 0.2;
    private static final double SHIELD_SPEED = 0.1;
    private static final double SHIELD_INTELLIGENCE = 0.2;
    private static final double MIN_CONSTANT = 0.15;
    private static final double MAX_CONSTANT = 0.25;
    private static final int TOTAL_ARMORY = 3;
    private static final int DOUBLE = 2;
    private static final int THIRD_ELEMENT = 2;
    private static final double THRESHOLD = 0.6;
    private static final String AXE = "Axe";
    private static final String CROSSBOW = "Crossbow";
    private static final String SHIELD = "Shield";
    private static final String STICK = "Stick";

    /**
     * Instantiates a Bandit with default Humanoid values.
     */
    public Bandit() {
        super();
    }

    /**
     * Constructs a Bandit with specified armor, vitality, speed,
     * intelligence, and weapon.
     *
     * @param armor       initial armor value
     * @param vitality    initial vitality value
     * @param speed       initial speed value
     * @param intelligence initial intelligence value
     * @param weapon      initial weapon name
     */
    public Bandit(int armor, int vitality,
                  double speed, int intelligence, String weapon) {
        super(armor, vitality, speed, intelligence, weapon);
    }

    /**
     * Increases this Bandit's vitality by a fixed amount when resting.
     */
    @Override
    public void rest() {
        int currentVitality = getVitality();
        setVitality(currentVitality + REST);
    }

    /**
     * Computes Bandit's power based on weapon, stats, and critical chance.
     *
     * @return calculated power value
     */
    @Override
    public double calculatePower() {
        String weapon = getWeapon();
        int vitality = getVitality();
        int intelligence = getIntelligence();
        double speed = getSpeed();
        int armor = getArmor();
        double base;

        if (weapon != null && weapon.equals(AXE)) {
            base = AXE_VITALITY * vitality
                 + AXE_INTELLIGENCE * intelligence
                 - AXE_SPEED * speed;
        } else if (weapon != null && weapon.equals(CROSSBOW)) {
            base = CROSSBOW_VITALITY * vitality
                 + CROSSBOW_INTELLIGENCE * intelligence
                 + CROSSBOW_SPEED * speed;
        } else if (weapon != null && weapon.equals(SHIELD)) {
            base = SHIELD_ARMOR * armor
                 + SHIELD_VITALITY * vitality
                 + SHIELD_SPEED * speed
                 - SHIELD_INTELLIGENCE * intelligence;
        } else {
            base = 0.0;
        }
        double random = Math.random();
        if (random > THRESHOLD) {
            base *= DOUBLE;
        }
        return base;
    }

    /**
     * Performs a strike on the target monster, reducing its armor
     * or vitality based on strike value.
     *
     * @param monster target of the strike
     * @return computed strike value
     */
    @Override
    public int strike(Monster monster) {
        double power = calculatePower();
        double min = power - MIN_CONSTANT * getIntelligence();
        double max = power + MAX_CONSTANT * getIntelligence();
        double rd = min + (Math.random() * (max - min));
        int strikeValue = (int) Math.floor(rd);
        if (strikeValue <= 0) {
            return 0;
        }
        int monsterArmor = monster.getArmor();
        int monsterVitality = monster.getVitality();
        if (strikeValue < monsterArmor) {
            monster.setArmor(monsterArmor - strikeValue);
        } else {
            monster.setVitality(
                monsterVitality - (strikeValue - monsterArmor)
            );
            monster.setArmor(0);
        }
        return strikeValue;
    }

    /**
     * Attacks the target monster by performing a strike.
     *
     * @param monster target of the attack
     * @return result of strike on target
     */
    @Override
    public int attack(Monster monster) {
        return strike(monster);
    }

    /**
     * Returns the list of available weapons for this Bandit.
     *
     * @return array of weapon names
     */
    @Override
    protected String[] getAvailableWeapons() {
        String[] weapons = {AXE, CROSSBOW, SHIELD, STICK};
        return weapons;
    }

    /**
     * Applies armory effect: first call Humanoid effect, then double
     * one random stat (armor, vitality, or speed).
     */
    @Override
    public void applyArmoryEffect() {
        super.applyArmoryEffect();
        double[] armory = {getArmor(), getVitality(), getSpeed()};
        int random = (int) (Math.random() * TOTAL_ARMORY);
        double newValue = armory[random] * DOUBLE;
        if (random == 0) {
            setArmor((int) newValue);
        }
        if (random == 1) {
            setVitality((int) newValue);
        }
        if (random == THIRD_ELEMENT) {
            setSpeed(newValue);
        }
    }
}
