package models;

public class Backpack {
    private int backpackId;
    private int artifactId;
    private boolean isUsed;

    // Getters & Setters
    public int getBackpackId() {
        return backpackId;
    }

    public void setBackpackId(int backpackId) {
        this.backpackId = backpackId;
    }

    public int getArtifactId() {
        return artifactId;
    }

    public void setArtifactId(int artifactId) {
        this.artifactId = artifactId;
    }

    public boolean isUsed() {
        return isUsed;
    }

    public void setUsed(boolean used) {
        isUsed = used;
    }
}
