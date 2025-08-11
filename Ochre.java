import java.util.ArrayList;
/**
 * An Ochre is an Ooze that can split into smaller clones and share power.
 *
 * Bugs: none known.
 *
 * @author David Do
 */

public class Ochre extends Ooze implements Cloneable {
    private ArrayList<Ochre> clones;
    private static final int DOUBLE = 2;
    private static final int REST = 20;
    private static final double VITALITY_CONSTANT = 0.7;
    private static final double VOLUME_CONSTANT = 0.35;
    private static final double CHANCE = 0.095;
    private static final double HALF = 0.5;
    private static final int RANGE = 6;

    /**
     * Constructs an Ochre with default stats (armor=0, vitality=0,
     * speed=0.0, volume=0, acidity=0) and an empty clone list.
     *
     * Precondition: none.
     * Postcondition: clones is initialized to an empty list.
     */
    public Ochre() {
        super();
        clones = new ArrayList<>();
    }

    /**
     * Constructs an Ochre with specified stats and an empty clone list.
     *
     * @param armor     initial armor value
     * @param vitality  initial vitality value
     * @param speed     initial speed value
     * @param volume    initial volume value
     * @param acidity   initial acidity value
     * Precondition: none.
     * Postcondition: clones is initialized to an empty list.
     */
    public Ochre(int armor, int vitality, double speed,
                 int volume, int acidity) {
        super(armor, vitality, speed, volume, acidity);
        clones = new ArrayList<>();
    }

    /**
     * Creates a clone of this Ochre, splitting volume in half.
     *
     * @return new Ochre clone with half volume
     * @throws CloneNotSupportedException if volume is 1
     * Precondition: none.
     * Postcondition: this volume is halved; returned clone has 
     * same other stats.
     */
    @Override
    protected Ochre clone() throws CloneNotSupportedException {
        int volume = getVolume();
        if (volume == 1) {
            throw new CloneNotSupportedException();
        }
        int halfVolume = volume / DOUBLE;
        Ochre cloned = new Ochre(
            getArmor(),
            getVitality(),
            getSpeed(),
            halfVolume,
            getAcidity()
        );
        setVolume(halfVolume);
        return cloned;
    }

    /**
     * Returns the list of this Ochre's clones.
     *
     * @return ArrayList of clones (may be empty)
     * Precondition: none.
     * Postcondition: none.
     */
    public ArrayList<Ochre> getClones() {
        return clones;
    }

    /**
     * Checks if this Ochre is alive or has clones.
     *
     * @return true if vitality > 0 or clones nonempty; false otherwise
     * Precondition: none.
     * Postcondition: none.
     */
    @Override
    public boolean isAlive() {
        return getVitality() > 0 || !clones.isEmpty();
    }

    /**
     * Rests this Ochre and its clones, increasing armor by REST.
     *
     * Precondition: none.
     * Postcondition: this and each clone's armor increased by REST.
     */
    @Override
    public void rest() {
        setArmor(getArmor() + REST);
        for (Ochre o : clones) {
            o.rest();
        }
    }

    /**
     * Calculates total power based on vitality, volume, acidity,
     * and includes power of all clones.
     *
     * @return sum of this and clones' power values
     * Precondition: none.
     * Postcondition: none.
     */
    @Override
    public double calculatePower() {
        double totalPower = VITALITY_CONSTANT * getVitality()
                          + VOLUME_CONSTANT * getVolume()
                          + getAcidity();
        for (Ochre o : clones) {
            totalPower += o.calculatePower();
        }
        return totalPower;
    }

    /**
     * Attempts corrosion with CHANCE probability; clones may also corrode.
     *
     * @return true if any instance corrodes; false otherwise
     * Precondition: none.
     * Postcondition: none.
     */
    @Override
    public boolean corrode() {
        if (Math.random() < CHANCE) {
            return true;
        }
        for (Ochre o : clones) {
            if (o.corrode()) {
                return true;
            }
        }
        return false;
    }

    /**
     * Attacks a target monster, reducing its armor or vitality
     * based on a random strike value influenced by volume.
     *
     * @param monster target of the attack
     * @return computed strike value applied
     * Precondition: monster is not null.
     * Postcondition: monster's armor or vitality reduced accordingly.
     */
    @Override
    public int attack(Monster monster) {
        double power = calculatePower();
        int volume = getVolume();
        double min = power - HALF * volume;
        double max = power + HALF * volume;
        double temp = min + Math.random() * (max - min);
        int strikeValue = (int) Math.floor(temp);
        if (strikeValue <= 0) {
            return 0;
        }
        int armor = monster.getArmor();
        int vitality = monster.getVitality();
        if (strikeValue < armor) {
            monster.setArmor(armor - strikeValue);
        } else {
            monster.setVitality(vitality - (strikeValue - armor));
            monster.setArmor(0);
        }
        return strikeValue;
    }

    /**
     * Applies armory effect: calls super then generates up to RANGE clones.
     *
     * Precondition: none.
     * Postcondition: up to floor(random*RANGE) new clones added; this instance
     *                may have reduced volume per clone logic.
     */
    @Override
    public void applyArmoryEffect() {
        super.applyArmoryEffect();
        int numClones = (int) Math.floor(Math.random() * RANGE);
        for (int i = 0; i < numClones; i++) {
            try {
                Ochre newClone = this.clone();
                clones.add(newClone);
            } catch (CloneNotSupportedException e) {
                break;
            }
        }
    }

    /**
     * Handles deathrattle: if dead and clones exist,
     *  resurrect using first clone.
     *
     * @return true if resurrected from a clone; false otherwise
     * Precondition: none.
     * Postcondition: if resurrected, this stats updated from clone.
     */
    @Override
    public boolean handleDeathrattle() {
        if (getVitality() <= 0 && !clones.isEmpty()) {
            Ochre next = clones.remove(0);
            setArmor(next.getArmor());
            setVitality(next.getVitality());
            setSpeed(next.getSpeed());
            setVolume(next.getVolume());
            setAcidity(next.getAcidity());
            clearPoison();
            return true;
        }
        return false;
    }
}
