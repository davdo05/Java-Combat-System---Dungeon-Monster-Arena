import java.util.Random;
import java.util.ArrayList;
/**
 * A Humanoid is a Monster with intelligence and a weapon
 * that can strike and equip from an armory.
 *
 * Bugs: none known.
 *
 * @author David Do
 */
public abstract class Humanoid extends Monster {

    private int intelligence;
    private String weapon;

    /**
     * Constructs a Humanoid with default stats (armor=0, vitality=0,
     * speed=0.0, intelligence=0, weapon=null).
     * Precondition: none.
     * Postcondition: intelligence is 0 and weapon is null.
     */
    protected Humanoid() {
        super();
        this.intelligence = 0;
        this.weapon = null;
    }

    /**
     * Constructs a Humanoid with specified armor, vitality, speed,
     * intelligence, and weapon.
     *
     * @param armor       initial armor value
     * @param vitality    initial vitality value
     * @param speed       initial speed value
     * @param intelligence initial intelligence value
     * @param weapon      initial weapon name (may be null)
     * Precondition: none.
     * Postcondition: fields set accordingly.
     */
    protected Humanoid(int armor, int vitality, double speed,
                       int intelligence, String weapon) {
        super(armor, vitality, speed);
        this.intelligence = intelligence;
        this.weapon = weapon;
    }

    /**
     * Returns this Humanoid's intelligence.
     *
     * @return intelligence value
     * Precondition: none.
     * Postcondition: none.
     */
    public int getIntelligence() {
        return this.intelligence;
    }

    /**
     * Returns this Humanoid's current weapon.
     *
     * @return weapon name (may be null)
     * Precondition: none.
     * Postcondition: none.
     */
    public String getWeapon() {
        return this.weapon;
    }

    /**
     * Sets this Humanoid's intelligence.
     *
     * @param intelligence new intelligence value
     * Precondition: intelligence >= 0.
     * Postcondition: intelligence field updated.
     */
    public void setIntelligence(int intelligence) {
        this.intelligence = intelligence;
    }

    /**
     * Sets this Humanoid's weapon.
     *
     * @param weapon new weapon name (may be null)
     * Precondition: none.
     * Postcondition: weapon field updated.
     */
    public void setWeapon(String weapon) {
        this.weapon = weapon;
    }

    /**
     * Performs a strike on the target monster, reducing its stats.
     *
     * @param monster the Monster to strike
     * @return the integer value of the strike applied
     * Precondition: monster is not null.
     * Postcondition: monster's armor or vitality is reduced.
     */
    public abstract int strike(Monster monster);

    /**
     * Returns the list of available weapons for this Humanoid.
     *
     * @return array of weapon names (non-null, may be empty)
     * Precondition: none.
     * Postcondition: none.
     */
    protected abstract String[] getAvailableWeapons();

    /**
     * Equips a randomly chosen weapon from the available weapons.
     * Precondition: getAvailableWeapons() may return null or length zero.
     * Postcondition: if weapons exist, weapon field is set randomly.
     */
    public void applyArmoryEffect() {
        String[] weapons = getAvailableWeapons();
        if (weapons == null || weapons.length == 0) {
            return;
        }
        int index = (int) (Math.random() * weapons.length);
        setWeapon(weapons[index]);
    }

    /**
     * Handles deathrattle for Humanoids (always returns false).
     *
     * @return false, as Humanoids have no deathrattle
     * Precondition: none.
     * Postcondition: none.
     */
    public boolean handleDeathrattle() {
        return false;
    }

}