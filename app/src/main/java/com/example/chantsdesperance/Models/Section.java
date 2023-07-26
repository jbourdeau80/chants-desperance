package com.example.chantsdesperance.Models;
import org.parceler.Parcel;


@Parcel
public class Section {
    String nomSection;
    int indiceSection;

    // Constructor to initialize the section object with the provided data

    // Constructor to initialize the section object with the provided data
    public Section(String nomSection, int indiceSection)
    {   this.nomSection = nomSection;
        this.indiceSection = indiceSection;
    }

    // Empty constructor required by Parceler library
    public Section(){}

    // Getter method for retrieving the section name
    public String getnomSection() { return nomSection; }

    // Getter method for retrieving the section index
    public int getindiceSection() {
        return indiceSection;
    }

}