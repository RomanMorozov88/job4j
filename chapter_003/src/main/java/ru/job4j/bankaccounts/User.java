package ru.job4j.bankaccounts;

    import java.util.*;

    public class User {

        private String name;
        private String passport;

        public User(String name, String passport) {
            this.name = name;
            this.passport = passport;
        }
        public String getName() {
            return this.name;
        }
        public String getPassport() {
            return this.passport;
        }

        /*@Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            User user = (User) o;
            return passport == user.passport &&
                    Objects.equals(name, user.name);
        }*/

        /**
         * Методы equals и hashCode переобределены так, что бы считать одинаковыми
         * объекты User с одинаковым полем passport. Поле name не учитывается.
         */
        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            User user = (User) o;
            return passport == user.passport;
        }

        /*@Override
        public int hashCode() {
            return Objects.hash(name, passport);
        }*/
        @Override
        public int hashCode() {
            return Objects.hash(passport);
        }
    }