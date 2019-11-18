package ru.job4j.javaxjson.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Objects;

public class CountryModel {

    @JsonProperty("country")
    private String countryName;
    @JsonProperty("cities")
    private List<String> cities;

    public CountryModel() {
    }

    public CountryModel(String countryName, List<String> cities) {
        this.countryName = countryName;
        this.cities = cities;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public List<String> getCities() {
        return cities;
    }

    public void setCities(List<String> cities) {
        this.cities = cities;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CountryModel that = (CountryModel) o;
        return Objects.equals(countryName, that.countryName)
                && Objects.equals(cities, that.cities);
    }

    @Override
    public int hashCode() {

        return Objects.hash(countryName, cities);
    }
}