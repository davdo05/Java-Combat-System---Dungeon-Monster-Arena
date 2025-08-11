import java.util.ArrayList;
/**
 * A mischievous shapeshifting Humanoid that can 
 * clone itself and share power with clones.
 *
 * Bugs: none known.
 *
 * @author David Do
 */
public class Doppelganger extends Humanoid implements Cloneable {
    private ArrayList<Doppelganger> clones;
    private static final int REST = 10;
    private static final double STAFF_VITALITY = 0.35;
    private static final double STAFF_INTELLIGENCE = 0.3;
    private static final double STAFF_SPEED = 0.6;
    private static final double DAGGER_VITALITY = 0.05;
    private static final double DAGGER_INTELLIGENCE = 0.15;
    private static final double DAGGER_SPEED = 0.8;
    private static final double RAPIER_ARMOR = 0.4;
    private static final double RAPIER_INTELLIGENCE = 0.2;
    private static final double RAPIER_SPEED = 0.5;
    private static final double HALF = 0.5;
    private static final double LENGTH = 6.0;
    private static final String STAFF = "Staff";
    private static final String DAGGER = "Dagger";
    private static final String RAPIER = "Rapier";
    private static final String STICK = "Stick";

    /**
     * Creates a new Doppelganger with default stats and empty clone list.
     * Precondition: None.
     * Postcondition: clones is initialized to an empty ArrayList.
     */
    public Doppelganger() {
        super();
        clones = new ArrayList<>();
    }

    /**
     * Constructs a Doppelganger with specified stats and empty clone list.
     *
     * @param armor       initial armor value
     * @param vitality    initial vitality value
     * @param speed       initial speed value
     * @param intelligence initial intelligence value
     * @param weapon      initial weapon name
     * Precondition: weapon may be null or one of available weapons.
     * Postcondition: clones is initialized to an empty ArrayList.
     */
    public Doppelganger(int armor, int vitality,
                       double speed, int intelligence, String weapon) {
        super(armor, vitality, speed, intelligence, weapon);
        clones = new ArrayList<>();
    }

    /**
     * Creates a deep copy of this Doppelganger without copying its clones.
     *
     * @return a new Doppelganger with identical stats but empty clone list
     * @throws CloneNotSupportedException if this instance cannot be cloned
     * Precondition: this.volume > 0
     * Postcondition: returned clone has same stats, clones list is empty.
     */
    @Override
    protected Doppelganger clone() throws CloneNotSupportedException {
        Doppelganger copy = new Doppelganger(
            this.getArmor(),
            this.getVitality(),
            this.getSpeed(),
            this.getIntelligence(),
            this.getWeapon()
        );
        return copy;
    }

    /**
     * Returns the list of clones belonging to this Doppelganger.
     *
     * @return ArrayList of this Doppelganger's clones
     * Precondition: None.
     * Postcondition: clones list is returned (may be empty).
     */
    public ArrayList<Doppelganger> getClones() {
        return clones;
    }

    /**
     * Checks whether this Doppelganger is alive or has any clones.
     *
     * @return true if vitality > 0 or clones list is nonempty; false otherwise
     * Precondition: None.
     * Postcondition: None.
     */
    @Override
    public boolean isAlive() {
        return getVitality() > 0 || clones.size() > 0;
    }

    /**
     * Rests this Doppelganger and all clones, increasing each vitality by REST.
     *
     * Precondition: None.
     * Postcondition: This instance's vitality increased by REST;
     *                each clone's rest() also called.
     */
    @Override
    public void rest() {
        int oldVitality = getVitality();
        setVitality(oldVitality + REST);
        for (int i = 0; i < clones.size(); i++) {
            clones.get(i).rest();
        }
    }

    /**
     * Calculates total power from this Doppelganger and all its clones.
     *
     * @return sum of calculated power of this instance and each clone
     * Precondition: None.
     * Postcondition: None.
     */
    public double calculatePower() {
        String weapon = getWeapon();
        int vitality = getVitality();
        int intel = getIntelligence();
        double speed = getSpeed();
        int armor = getArmor();
        double total_power = 0.0;
        if (STAFF.equals(weapon)) {
            total_power += STAFF_VITALITY * vitality
                         + STAFF_INTELLIGENCE * intel
                         - STAFF_SPEED * speed;
        } else if (DAGGER.equals(weapon)) {
            total_power += DAGGER_VITALITY * vitality
                         + DAGGER_INTELLIGENCE * intel
                         + DAGGER_SPEED * speed;
        } else if (RAPIER.equals(weapon)) {
            total_power += RAPIER_ARMOR * armor
                         + RAPIER_INTELLIGENCE * intel
                         + RAPIER_SPEED * speed;
        } else {
            total_power = 0.0;
        }
        for (int i = 0; i < clones.size(); i++) {
            total_power += clones.get(i).calculatePower();
        }
        return total_power;
    }

    /**
     * Performs a strike on the target monster, reducing its armor or vitality.
     *
     * @param monster the target Monster to attack
     * @return the computed strike value applied to the target
     * Precondition: monster is not null.
     * Postcondition: monster's armor or vitality reduced based on strikeValue.
     */
    @Override
    public int strike(Monster monster) {
        double power = calculatePower();
        int intelligence = getIntelligence();
        int armor = monster.getArmor();
        int vitality = monster.getVitality();
        double min = power - HALF * intelligence;
        double max = power + HALF * intelligence;
        double random = min + Math.random() * (max - min);
        int strikeValue = (int) Math.floor(random);
        if (strikeValue <= 0) {
            return 0;
        } else if (strikeValue < armor) {
            monster.setArmor(armor - strikeValue);
        } else if (strikeValue >= armor) {
            monster.setVitality(vitality - (strikeValue - armor));
            monster.setArmor(0);
        }
        return strikeValue;
    }

    /**
     * Attacks the target monster by invoking strike(monster).
     *
     * @param monster the target Monster to attack
     * @return result of strike on the target
     * Precondition: monster is not null.
     * Postcondition: same as strike(monster).
     */
    @Override
    public int attack(Monster monster) {
        return strike(monster);
    }

    /**
     * Returns an array of available weapons for this Doppelganger.
     *
     * @return String[] containing "Staff", "Dagger", "Rapier", and "Stick"
     * Precondition: None.
     * Postcondition: None.
     */
    @Override
    protected String[] getAvailableWeapons() {
        String[] weapons = {STAFF, DAGGER, RAPIER, STICK};
        return weapons;
    }

    /**
     * Applies armory effect: calls super then adds 0–5 clones of itself.
     *
     * Precondition: None.
     * Postcondition: Up to floor(random*6) new clones added to clones list.
     */
    @Override
    public void applyArmoryEffect() {
        super.applyArmoryEffect();
        int numClones = (int) Math.floor(Math.random() * LENGTH);
        for (int i = 0; i < numClones; i++) {
            try {
                Doppelganger copy = this.clone();
                clones.add(copy);
            } catch (CloneNotSupportedException e) {
                break;
            }
        }
    }

    /**
     * Attempts deathrattle: if dead and clones available,
     *  resurrect from first clone.
     *
     * @return true if resurrected from a clone, false otherwise
     * Precondition: None.
     * Postcondition: If resurrected,
     * this instance's stats replaced with first clone.
     */
    @Override
    public boolean handleDeathrattle() {
        int vitality = getVitality();
        if (vitality <= 0 && !clones.isEmpty()) {
            Doppelganger ressurected = clones.remove(0);
            setArmor(ressurected.getArmor());
            setVitality(ressurected.getVitality());
            setSpeed(ressurected.getSpeed());
            setIntelligence(ressurected.getIntelligence());
            setWeapon(ressurected.getWeapon());
            clearPoison();
            return true;
        }
        return false;
    }

}