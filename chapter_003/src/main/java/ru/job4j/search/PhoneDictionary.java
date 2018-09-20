package ru.job4j.search;

import java.util.ArrayList;
import java.util.List;

public class PhoneDictionary {
    private List<Person> persons = new ArrayList<Person>();

    public void add(Person person) {
        this.persons.add(person);
    }

    /**
     * Вернуть список всех пользователей, который содержат key в любых полях.
     * @param key Ключ поиска.
     * @return Список подощедщих пользователей.
     */
    public List<Person> find(String key) {
        List<Person> result = new ArrayList<>();
        for(Person prs : persons) {
            if(prs.getName().contains(key) ||
                    prs.getSurname().contains(key) ||
                    prs.getPhone().contains(key) ||
                    prs.getAddress().contains(key)) {
                result.add(prs);
            }
        }
        return result;
    }
}