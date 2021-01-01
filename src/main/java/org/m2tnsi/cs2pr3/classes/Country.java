package org.m2tnsi.cs2pr3.classes;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;
import java.sql.Timestamp;

@Entity(name="Countries")
public class Country implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String country;
    private String countryCode;
    private String slug;
    private int newConfirmed;
    private int totalConfirmed;
    private int newDeaths;
    private int totalDeaths;
    private int totalRecovered;
    private Timestamp dateMaj;

    public Country() {}

    public Country(String country, String countryCode, String slug, int newConfirmed, int totalConfirmed,
                   int newDeaths, int totalDeaths, int totalRecovered, Timestamp dateMaj) {
        this.country = country;
        this.countryCode = countryCode;
        this.slug = slug;
        this.newConfirmed = newConfirmed;
        this.totalConfirmed = totalConfirmed;
        this.newDeaths = newDeaths;
        this.totalDeaths = totalDeaths;
        this.totalRecovered = totalRecovered;
        this.dateMaj = dateMaj;
    }

    public int getId() {
        return id;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public int getNewConfirmed() {
        return newConfirmed;
    }

    public void setNewConfirmed(int newConfirmed) {
        this.newConfirmed = newConfirmed;
    }

    public int getTotalConfirmed() {
        return totalConfirmed;
    }

    public void setTotalConfirmed(int totalConfirmed) {
        this.totalConfirmed = totalConfirmed;
    }

    public int getNewDeaths() {
        return newDeaths;
    }

    public void setNewDeaths(int newDeaths) {
        this.newDeaths = newDeaths;
    }

    public int getTotalDeaths() {
        return totalDeaths;
    }

    public void setTotalDeaths(int totalDeaths) {
        this.totalDeaths = totalDeaths;
    }

    public int getTotalRecovered() {
        return totalRecovered;
    }

    public void setTotalRecovered(int totalRecovered) {
        this.totalRecovered = totalRecovered;
    }

    public Timestamp getDateMaj() {
        return dateMaj;
    }

    public void setDateMaj(Timestamp dateMaj) {
        this.dateMaj = dateMaj;
    }

    @Override
    public String toString() {
        return "{" +
                "\"Country\": \"" + country + "\"" +
                ", \"CountryCode\": \"" + countryCode + "\"" +
                ", \"Slug\": \"" + slug + "\"" +
                ", \"NewConfirmed\":" + newConfirmed +
                ", \"TotalConfirmed\":" + totalConfirmed +
                ", \"NewDeaths\":" + newDeaths +
                ", \"TotalDeaths\":" + totalDeaths +
                ", \"TotalRecovered\":" + totalRecovered +
                ", \"DateMaj\":\"" + dateMaj +
                "\"}";
    }
}
