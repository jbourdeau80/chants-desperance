package com.example.chantsdesperance.Models;
import org.parceler.Parcel;


// The Chants class is a model class that represents a chant object.
// It is annotated with @Parcel to enable serialization and deserialization using Parceler library

@Parcel
public class Chants {
    int numeroChant;
    String titreChant;
    String texteChant;
    String nomSection;


    // Default constructor required by Parceler library
    public Chants(){}

    // Parameterized constructor to initialize the chant object
    public Chants(int numeroChant, String titreChant, String texteChant, String nomSection){
        this.numeroChant = numeroChant;
        this.titreChant = titreChant;
        this.texteChant = texteChant;
        this.nomSection = nomSection;
    }

    // Parameterized constructor to initialize the chant object
    public int getnumeroChant() {
        return numeroChant;
    }

    // Getter method for retrieving the chant title
    public String gettitreChant() { return titreChant; }

    // Getter method for retrieving the section name
    public String getnomSection() { return nomSection; }


    // Getter method for retrieving the chant text
    public String gettexteChant() { return texteChant; }

}
