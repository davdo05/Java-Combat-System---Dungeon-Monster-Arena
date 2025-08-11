/**
 * The abstract Monster class defines core attributes and behaviors
 * for all monsters, including armor, vitality, speed, and poison status.
 *
 * Bugs: none known.
 *
 * @author David Do
 */
public abstract class Monster implements Comparable<Monster> {
    private int armor;
    private int vitality;
    private double speed;
    protected boolean poisoned;
    private static final double AVERAGE_CONSTANT = 3.0;

    /**
     * No-arg constructor initializes armor, vitality, speed to zero
     * and poisoned to false.
     *
     * Precondition: none.
     * Postcondition: armor=0, vitality=0, speed=0.0, poisoned=false.
     */
    protected Monster() {
        this.armor = 0;
        this.vitality = 0;
        this.speed = 0.0;
        this.poisoned = false;
    }

    /**
     * Constructs a Monster with specified armor, vitality, and speed.
     *
     * @param armor    initial armor value
     * @param vitality initial vitality value
     * @param speed    initial speed value
     * Precondition: none.
     * Postcondition: fields set accordingly.
     */
    protected Monster(int armor, int vitality, double speed) {
        this.armor = armor;
        this.vitality = vitality;
        this.speed = speed;
    }

    /**
     * Returns this monster's armor value.
     *
     * @return current armor
     * Precondition: none.
     * Postcondition: none.
     */
    public int getArmor() {
        return this.armor;
    }

    /**
     * Returns this monster's vitality value.
     *
     * @return current vitality
     * Precondition: none.
     * Postcondition: none.
     */
    public int getVitality() {
        return this.vitality;
    }

    /**
     * Returns this monster's speed value.
     *
     * @return current speed
     * Precondition: none.
     * Postcondition: none.
     */
    public double getSpeed() {
        return this.speed;
    }

    /**
     * Sets this monster's armor to the specified value.
     *
     * @param armor new armor value
     * Precondition: none.
     * Postcondition: armor field updated.
     */
    public void setArmor(int armor) {
        this.armor = armor;
    }

    /**
     * Sets this monster's vitality to the specified value.
     *
     * @param vitality new vitality value
     * Precondition: none.
     * Postcondition: vitality field updated.
     */
    public void setVitality(int vitality) {
        this.vitality = vitality;
    }

    /**
     * Sets this monster's speed to the specified value.
     *
     * @param speed new speed value
     * Precondition: none.
     * Postcondition: speed field updated.
     */
    public void setSpeed(double speed) {
        this.speed = speed;
    }

    /**
     * Returns whether this monster is poisoned.
     *
     * @return true if poisoned, false otherwise
     * Precondition: none.
     * Postcondition: none.
     */
    public boolean isPoisoned() {
        return this.poisoned;
    }

    /**
     * Applies poison status to this monster.
     *
     * Precondition: none.
     * Postcondition: poisoned set to true.
     */
    public void applyPoison() {
        this.poisoned = true;
    }

    /**
     * Clears poison status from this monster.
     *
     * Precondition: none.
     * Postcondition: poisoned set to false.
     */
    public void clearPoison() {
        this.poisoned = false;
    }

    /**
     * Returns true if this monster's vitality is greater than zero.
     *
     * @return true if alive, false otherwise
     * Precondition: none.
     * Postcondition: none.
     */
    public boolean isAlive() {
        if (this.vitality > 0) {
            return true;
        }
        return false;
    }

    /**
     * Default special ability (does nothing). Subclasses may override.
     *
     * @param target the Monster to target
     * Precondition: target not null.
     * Postcondition: none.
     */
    public void performSpecialAbility(Monster target) {
    }

    /**
     * Compares this monster to another based on average of armor,
     * vitality, and speed.
     *
     * @param monster the other Monster to compare
     * @return -1 if this average < other, 0 if equal, 1 if greater
     * Precondition: monster not null.
     * Postcondition: none.
     */
    public int compareTo(Monster monster) {
        double instanceAverage = (this.armor + this.vitality + this.speed)
            / AVERAGE_CONSTANT;
        double monsterAverage = (monster.getArmor() + monster.getSpeed()
            + monster.getVitality()) / AVERAGE_CONSTANT;
        if (instanceAverage < monsterAverage) {
            return -1;
        } else if (instanceAverage > monsterAverage) {
            return 1;
        } else {
            return 0;
        }
    }

    /**
     * Returns a string representation of this monster.
     *
     * @return formatted string "(ClassName) armor: X; vitality: Y; speed: Z"
     * Precondition: none.
     * Postcondition: none.
     */
    @Override
    public String toString() {
        return "(" + getClass().getName() + ")" + " armor: " + getArmor()
            + "; vitality: " + getVitality() + "; speed: " + getSpeed();
    }

    /**
     * Restores or recharges monster-specific stats. Must be overridden.
     * Precondition: none.
     * Postcondition: none.
     */
    public abstract void rest();

    /**
     * Calculates this monster's power based on its stats. Must be overridden.
     *
     * @return calculated power value
     * Precondition: none.
     * Postcondition: none.
     */
    public abstract double calculatePower();

    /**
     * Performs an attack on the specified monster. Must be overridden.
     *
     * @param monster target of the attack
     * @return integer damage or strike value
     * Precondition: monster not null.
     * Postcondition: monster's stats may be reduced.
     */
    public abstract int attack(Monster monster);

    /**
     * Applies armory effect to this monster. Must be overridden.
     * Precondition: none.
     * Postcondition: monster's stats modified.
     */
    public abstract void applyArmoryEffect();

    /**
     * Handles deathrattle behavior. Must be overridden.
     *
     * @return true if deathrattle triggered a resurrection, false otherwise
     * Precondition: none.
     * Postcondition: monster's stats may be replaced if resurrected.
     */
    public abstract boolean handleDeathrattle();

}