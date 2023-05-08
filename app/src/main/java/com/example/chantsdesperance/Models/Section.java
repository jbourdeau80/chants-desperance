package com.example.chantsdesperance.Models;
import org.parceler.Parcel;


@Parcel
public class Section {
    String nomSection;
    int indiceSection;

    public Section(String nomSection, int indiceSection)
    { this.nomSection = nomSection;
        this.indiceSection = indiceSection;
    }
    public Section(){}


    public String getnomSection() {
        return nomSection;
    }

    public int getindiceSection() {
        return indiceSection;
    }

}