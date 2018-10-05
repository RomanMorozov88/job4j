package ru.job4j.bankaccounts;

import ru.job4j.bankaccounts.menu.IncorrectDataException;

import java.util.*;

public class UsersMap {

    private HashMap<User, List<Account>> dataMap = new HashMap<>();

    /*public boolean addUser(User user) {
        boolean result = true;
        if (dataMap.containsKey(user)) {
            result = false;
        }
        this.dataMap.put(new User(user.getName(), user.getPassport()), new AccountsList().accountsList);
        return result;
    }*/

    /**
     * Использование метода putIfAbsent.
     * @param user входящий объект.
     */
    public void addUser(User user) {
        this.dataMap.putIfAbsent(user, new AccountsList().accountsList);
    }

    /**
     * Удаление поьзователя.
     * @param user
     * @return
     */
    public boolean deleteUser(User user) {
        boolean result = true;
        User userResult = this.findUser(user.getPassport());
        if (userResult != null
                && userResult.getName().equals(user.getName())) {
            this.dataMap.remove(userResult);
        } else {
            result = false;
        }
        return result;
    }

    /**
     * Добавление счёта пользователю.
     * @param passport паспорт пользователя, которому добавляем счёт.
     * @param account счёт.
     * @return
     */
    public boolean addAccountToUser(String passport, Account account) {
        boolean result = false;
        User user = this.findUser(passport);
        if (user != null) {
                this.dataMap.get(user).add(account);
                result = true;
        }
        return result;
    }

    /**
     * Удаление счёта пользователя.
     * @param passport паспорт пользователя, у которого удаляем счёт.
     * @param requisites
     * @return
     */
    public boolean deleteAccountFromUser(String passport, int requisites) {
        boolean result = false;
        User user = this.findUser(passport);
        if (user != null) {
            for (int i = 0; i < this.dataMap.get(user).size(); i++) {
                if (this.dataMap.get(user).get(i).getRequisites() == requisites) {
                    this.dataMap.get(user).remove(i);
                    result = true;
                }
            }
        }
        return result;
    }

    /**
     * Получаем список всех счетов пользователя.
     * @param passport
     * @return
     */
    public List<Account> getUserAccounts(String passport) {
        List<Account> result = new ArrayList<>();
        User user = this.findUser(passport);
        result.addAll(this.dataMap.get(user));
        return result;
    }

    /**
     * Получаем список всех пользователей.
     * @return
     */
    public Set<User> getUsers() {
        Set<User> result = new HashSet<>();
        for (User u : this.dataMap.keySet()) {
            result.add(u);
        }
        return result;
    }

    /**
     * Перевод другому пользователю.
     * @param srcPassport  от кого
     * @param srcRequisite откуда
     * @param desPassport кому
     * @param desRequisite куда
     * @param amount сколько
     * @return
     */
    public boolean transferMoney(String srcPassport, int srcRequisite,
                                 String desPassport, int desRequisite,
                                 double amount) {
        boolean result = false;
        Account account1 = this.findAccount(srcPassport, srcRequisite);
        Account account2 = this.findAccount(desPassport, desRequisite);
        if (account1 == null || account2 == null || account1.equals(account2)) {
            throw new IncorrectDataException("...");
        }
        if ((account1.getValue() - amount) >= 0) {
            account1.editValue(-amount);
            account2.editValue(amount);
            result = true;
        }
        return result;
    }

    /**
     * Перевод между аккаунтами одного пользователя.
     * @param srcPassport
     * @param srcRequisite откуда
     * @param desRequisite куда
     * @param amount сколько
     * @return
     */
    /*public boolean transferMoney(String srcPassport, int srcRequisite,
                                 int desRequisite, double amount) {
        boolean result = false;
        Account account1 = this.findAccount(srcPassport, srcRequisite);
        Account account2 = this.findAccount(srcPassport, desRequisite);
        if (account1 == null || account2 == null || account1.equals(account2)) {
            throw new IncorrectDataException("...");
        }
        if ((account1.getValue() - amount) >= 0) {
            account1.editValue(-amount);
            account2.editValue(amount);
            result = true;
        }
        return result;
    }*/
    public boolean transferMoney(String srcPassport, int srcRequisite,
                                 int desRequisite, double amount) {
        return this.transferMoney(srcPassport, srcRequisite, srcPassport, desRequisite, amount);
    }

    /**
     * Вспомогательный метод для поиска аккаунта.
     * @param passport
     * @param requisites
     * @return
     */
    public Account findAccount(String passport, int requisites) {
        Account result = null;
        List<Account> list = this.getUserAccounts(passport);
                for (int i = 0; i < list.size(); i++) {
                    if (list.get(i).getRequisites() == requisites) {
                        result = list.get(i);
                        break;
                    }
                }
        return result;
    }

    /**
     * Вспомогательный метод для поиска пользователя.
     * @param passport
     * @return
     */
    public User findUser(String passport) {
        User result = null;
        for (User u : this.dataMap.keySet()) {
            if (u.getPassport().equals(passport)) {
                result = u;
            }
        }
        return result;
    }

    private class AccountsList {
        public List<Account> accountsList = new ArrayList<>();
    }
}