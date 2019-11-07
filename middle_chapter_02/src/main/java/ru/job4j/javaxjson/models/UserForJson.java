package ru.job4j.javaxjson.models;

import java.util.Objects;

/**
 * Простая модель.
 * Обязательно: конструктор по умолчанию и геттеры\сеттеры.
 */
public class UserForJson {

    private String firstName;
    private String lastName;
    private String description;
    private String gender;

    public UserForJson() {
    }

    public UserForJson(String firstName, String lastName, String gender) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserForJson that = (UserForJson) o;
        return Objects.equals(firstName, that.firstName) &&
                Objects.equals(lastName, that.lastName) &&
                Objects.equals(description, that.description) &&
                Objects.equals(gender, that.gender);
    }

    @Override
    public int hashCode() {

        return Objects.hash(firstName, lastName, description, gender);
    }

    @Override
    public String toString() {
        return "UserForJson{"
                + "firstName='" + firstName + '\''
                + ", lastName='" + lastName + '\''
                + ", description='" + description + '\''
                + ", gender='" + gender + '\''
                + '}';
    }
}