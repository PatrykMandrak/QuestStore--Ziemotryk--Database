package models;

public class Level {
    private int level;
    private int coolcoinsNeeded;
    private String levelName;
    private String levelDescription;

    // Getters & Setters
    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getCoolcoinsNeeded() {
        return coolcoinsNeeded;
    }

    public void setCoolcoinsNeeded(int coolcoinsNeeded) {
        this.coolcoinsNeeded = coolcoinsNeeded;
    }

    public String getLevelName() {
        return levelName;
    }

    public void setLevelName(String levelName) {
        this.levelName = levelName;
    }

    public String getLevelDescription() {
        return levelDescription;
    }

    public void setLevelDescription(String levelDescription) {
        this.levelDescription = levelDescription;
    }
}