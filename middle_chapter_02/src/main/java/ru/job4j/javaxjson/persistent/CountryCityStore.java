package ru.job4j.javaxjson.persistent;

import ru.job4j.javaxjson.models.CountryModel;

import java.util.*;

public class CountryCityStore {

    private static final CountryCityStore INSTANCE = new CountryCityStore();
    private final List<CountryModel> innerList = new ArrayList<>();

    private CountryCityStore() {
        innerList.add(new CountryModel("1country", Arrays.asList("1city in 1country",
                "2city in 1country")));
        innerList.add(new CountryModel("2country", Arrays.asList("1city in 2country",
                "2city  in 2country", "3city  in 2country")));
    }

    public static CountryCityStore getInstance() {
        return INSTANCE;
    }

    public List<CountryModel> getCountries() {
        return innerList;
    }

}