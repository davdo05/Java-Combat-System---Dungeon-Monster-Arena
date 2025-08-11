/**
 * An Ooze is a Monster that can corrode targets, double its volume in armory,
 * and has acidic properties.
 *
 * Bugs: none known.
 *
 * @author David Do
 */
public abstract class Ooze extends Monster {
    private int volume;
    private int acidity;
    private static final int DOUBLE_CONSTANT = 2;

    /**
     * Constructs an Ooze with default stats (armor=0, vitality=0, speed=0.0,
     * volume=0, acidity=0).
     *
     * Precondition: none.
     * Postcondition: volume=0 and acidity=0.
     */
    protected Ooze() {
        super();
        this.volume = 0;
        this.acidity = 0;
    }

    /**
     * Constructs an Ooze with specified armor, vitality, speed, volume,
     * and acidity.
     *
     * @param armor    initial armor value
     * @param vitality initial vitality value
     * @param speed    initial speed value
     * @param volume   initial volume value
     * @param acidity  initial acidity value
     * Precondition: none.
     * Postcondition: fields set accordingly.
     */
    protected Ooze(int armor, int vitality,
                   double speed, int volume, int acidity) {
        super(armor, vitality, speed);
        this.volume = volume;
        this.acidity = acidity;
    }

    /**
     * Returns the current volume of this Ooze.
     *
     * @return volume value
     * Precondition: none.
     * Postcondition: none.
     */
    public int getVolume() {
        return volume;
    }

    /**
     * Returns the current acidity of this Ooze.
     *
     * @return acidity value
     * Precondition: none.
     * Postcondition: none.
     */
    public int getAcidity() {
        return acidity;
    }

    /**
     * Sets the volume of this Ooze.
     *
     * @param volume new volume value
     * Precondition: none.
     * Postcondition: volume field updated.
     */
    public void setVolume(int volume) {
        this.volume = volume;
    }

    /**
     * Sets the acidity of this Ooze.
     *
     * @param acidity new acidity value
     * Precondition: none.
     * Postcondition: acidity field updated.
     */
    public void setAcidity(int acidity) {
        this.acidity = acidity;
    }

    /**
     * Attempts to corrode and damage a target's armor or poison it.
     *
     * @param target the Monster to attempt corrosion on
     * Precondition: target is not null.
     * Postcondition: target's armor or poison status updated if corroded.
     */
    @Override
    public void performSpecialAbility(Monster target) {
        boolean corroded = corrode();
        if (corroded && target.getArmor() > 0) {
            target.setArmor(0);
        } else if (corroded && target.getArmor() <= 0) {
            target.applyPoison();
        }
    }

    /**
     * Applies armory effect by doubling this Ooze's volume.
     *
     * Precondition: none.
     * Postcondition: volume is doubled.
     */
    @Override
    public void applyArmoryEffect() {
        setVolume(getVolume() * DOUBLE_CONSTANT);
    }

    /**
     * Default handleDeathrattle for Ooze (no resurrection).
     *
     * @return false always
     * Precondition: none.
     * Postcondition: none.
     */
    @Override
    public boolean handleDeathrattle() {
        return false;
    }

    /**
     * Determines if corrosion occurs. Must be implemented by subclasses.
     *
     * @return true if corrosion succeeds, false otherwise
     * Precondition: none.
     * Postcondition: none.
     */
    public abstract boolean corrode();
}