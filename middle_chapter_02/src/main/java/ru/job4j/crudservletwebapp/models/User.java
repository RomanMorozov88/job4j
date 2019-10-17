package ru.job4j.crudservletwebapp.models;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Модель пользователя.
 */
public class User {

    private final int id;
    private String name;
    private String login;
    private String password;
    private String email;
    private LocalDateTime createDate;
    private String photoId;
    private String rolename;

    public User(int id, String name, String login,
                String password, String email,
                LocalDateTime createDate, String rolename) {
        this.id = id;
        this.name = name;
        this.login = login;
        this.password = password;
        this.email = email;
        this.createDate = createDate;
        this.rolename = rolename;
    }

    public User(int id, String name, String login, String password, String email, String rolename) {
        this.id = id;
        this.name = name;
        this.login = login;
        this.password = password;
        this.email = email;
        this.rolename = rolename;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public String getPhotoId() {
        return photoId;
    }

    public void setPhotoId(String photoId) {
        this.photoId = photoId;
    }

    public String getRolename() {
        return rolename;
    }

    public void setRolename(String rolename) {
        this.rolename = rolename;
    }

    @Override
    public String toString() {
        return "User{id=" + id
                + ", name='" + name + '\''
                + ", login='" + login + '\''
                + ", email='" + email + '\''
                + ", createDate=" + createDate
                + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        User user = (User) o;
        return id == user.id
                && Objects.equals(name, user.name)
                && Objects.equals(login, user.login)
                && Objects.equals(password, user.password)
                && Objects.equals(email, user.email)
                && Objects.equals(createDate, user.createDate)
                && Objects.equals(rolename, user.rolename);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, name, login, password, email, createDate, rolename);
    }
}