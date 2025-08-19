UML Class Diagram: 
<img width="1002" height="581" alt="image" src="https://github.com/user-attachments/assets/eee3e341-420b-4904-ae93-47f9999dd300" />


# How it Works

This project is a **turn-based monster combat system** implemented in **Java**, designed around strong object-oriented principles. I used **inheritance, abstract classes, and interfaces** to model monster hierarchies and behaviors, while implementing dynamic combat mechanics like cloning, poison effects, randomized weapon selection, and stat-based attack calculations. The result is a modular framework that simulates dungeon-style battles between diverse monster types.

---

## System design

The system is built on a **class hierarchy**:

- **Monster (abstract superclass)** → Defines shared attributes (name, health, attack power, defense) and base methods.  
- **Humanoid (abstract subclass)** → Foundation for human-like monsters with unique logic.  
- **Ooze (abstract subclass)** → Foundation for slime-like monsters with distinct abilities.  
- **Concrete subclasses**:  
  - **Bandit** (humanoid) → Agile attacker with weapon-based moves.  
  - **Doppelganger** (humanoid) → Special ability to **clone** another monster’s stats and attacks.  
  - **Jubilex** (ooze) → Applies **poison damage over time**.  
  - **Ochre** (ooze) → Durable defender with unique resistances.  

This object-oriented design allows **shared behavior via inheritance** while keeping **unique abilities encapsulated in subclasses**.

---

## Combat mechanics

- **Turn-based system**: Players and monsters alternate attacks, with turn order determined by simple sequencing.  
- **Attack calculations**:  
  - Damage = `attack power – defense` (bounded to ensure no negative damage).  
  - Critical/randomized rolls add variability.  
- **Weapon selection**: Monsters like **Bandit** randomly select from a set of weapons, each with unique stats.  
- **Status effects**:  
  - **Poison** (Jubilex) → Inflicts recurring damage each turn.  
  - **Clone** (Doppelganger) → Copies abilities and stats of an opponent mid-battle.  
- **Dungeon simulation**: Battles can be chained together inside a **Dungeon** class, ranking monsters based on performance.  

---

## Key techniques used

- **Inheritance & abstraction** → Defined common monster traits in abstract superclasses, extended by concrete subclasses.  
- **Interfaces** → Implemented Java’s `Comparable` and `Cloneable` for ranking monsters and supporting cloning mechanics.  
- **Polymorphism** → Allowed interchangeable monster objects to battle each other with different implementations of `attack()`.  
- **Encapsulation** → Each monster type encapsulated its own behavior (e.g., poison logic in `Jubilex`).  

---

## Insights delivered

This system demonstrates how **object-oriented design** can be applied to simulate complex combat mechanics in a scalable way. By modeling monsters as classes and behaviors as methods, the framework can easily be extended with new monster types, abilities, or game modes.  

It also highlights practical Java concepts:  
- Abstract classes vs. interfaces.  
- Overriding methods for polymorphic behavior.  
- Game mechanics like health, status effects, and randomness implemented via clean modular code.  

---

## Challenges I solved

- **Balancing inheritance vs. abstraction** → Split monsters into humanoid vs. ooze subclasses to avoid bloated base classes.  
- **Cloning logic** → Implemented `Cloneable` in Java so Doppelganger could replicate an opponent’s abilities dynamically.  
- **Status effect handling** → Designed poison and recurring damage mechanics without breaking turn sequencing.  
- **Extensibility** → Structured the code so adding a new monster type only requires subclassing and overriding methods.  

---

✅ This project showcases **Java OOP expertise** and the ability to design **modular, extensible systems** with real combat mechanics — simulating ranking, dungeon battles, and dynamic monster interactions.
