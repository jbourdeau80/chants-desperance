package com.example.chantsdesperance;
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

}