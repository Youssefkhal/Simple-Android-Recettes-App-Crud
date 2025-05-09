package com.example.recettesapp;

public class Recette {
    private int id;
    private String titre;
    private String description;

    public Recette(int id, String titre, String description) {
        this.id = id;
        this.titre = titre;
        this.description = description;
    }

    public Recette(String titre, String description) {
        this.titre = titre;
        this.description = description;
    }

    public int getId() { return id; }
    public String getTitre() { return titre; }
    public String getDescription() { return description; }

    public void setId(int id) { this.id = id; }
    public void setTitre(String titre) { this.titre = titre; }
    public void setDescription(String description) { this.description = description; }
}
