package com.example.chantsdesperance;

import org.parceler.Parcel;

@Parcel
public class Chants {
    int numeroChant;
    String titreChant;
    String texteChant;
    String nomSection;



    public Chants(){}
    public Chants(int numeroChant, String titreChant, String texteChant, String nomSection){
        this.numeroChant = numeroChant;
        this.titreChant = titreChant;
        this.texteChant = texteChant;
        this.nomSection = nomSection;
    }


    public int getnumeroChant() {
        return numeroChant;
    }

    public String gettitreChant() {
        return titreChant;
    }

    public String getnomSection() { return nomSection; }


    public String gettexteChant() {
        return texteChant;
    }

}
