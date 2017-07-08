package com.kyberlabs.projetvbg.classes;

/**
 * Created by Dario DONOU on 02/06/2017.
 */

public class ClassDenonce {

    private int id;
    private String categorie;
    private String quartier;
    private String ville;
    private String departement;
    private String description;
    private String date;

    public ClassDenonce(){

    }

    public ClassDenonce(int id, String categorie, String quartier, String ville, String departement, String description, String date){
        this.id=id;
        this.categorie=categorie;
        this.quartier=quartier;
        this.ville=ville;
        this.departement=departement;
        this.description=description;
        this.date=date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCategorie() {
        return categorie;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }

    public String getQuartier() {
        return quartier;
    }

    public void setQuartier(String quartier) {
        this.quartier = quartier;
    }

    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public String getDepartement() {
        return departement;
    }

    public void setDepartement(String departement) {
        this.departement = departement;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
