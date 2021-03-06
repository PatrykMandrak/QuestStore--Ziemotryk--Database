package models;

import databaseAccess.ArtifactsDAO;

public class Backpack {
    private int backpackId;
    private int artifactId;
    private String artifactName;
    private String artifactDescription;
    private boolean isUsed;

    public Backpack(int backpackId, int artifactId, boolean isUsed) {
        this.backpackId = backpackId;
        this.artifactId = artifactId;
        Artifact artifact = new ArtifactsDAO().get(artifactId);
        this.artifactName = artifact.getName();
        this.artifactDescription = artifact.getDescription();
        this.isUsed = isUsed;
    }

    public Backpack(String artifactName, String artifactDescription, boolean isUsed) {
        this.artifactName = artifactName;
        this.artifactDescription = artifactDescription;
        this.isUsed = isUsed;
    }

    // Getters & Setters
    public int getBackpackId() {
        return backpackId;
    }

    public int getArtifactId() {
        return artifactId;
    }

    public boolean isUsed() {
        return isUsed;
    }
    
    public String getArtifactName() {
        return artifactName;
    }

    public String getArtifactDescription() {
        return artifactDescription;
    }
}
