package ru.job4j.bankaccounts;

import ru.job4j.bankaccounts.menu.IncorrectDataException;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Класс, содержащий методы для работы со списком пользователей и их аккаунтов.
 */
public class UsersMap {

    /**
     * Map, содержащий список аккаунтов с ключом типа User.
     */
    private HashMap<User, List<Account>> usersAccounts = new HashMap<>();
    /**
     * Список аккаунтов.
     */
    private List<Account> accountsList = new ArrayList<>();

    /**
     * Использование метода putIfAbsent.
     *
     * @param user входящий объект.
     */
    public void addUser(User user) {
        this.usersAccounts.putIfAbsent(user, accountsList);
    }

    /**
     * Удаление поьзователя.
     *
     * @param user
     * @return
     */
    public boolean deleteUser(User user) {
        boolean result = false;
        User userResult = this.findUser(user.getPassport());
        if (userResult != null
                && userResult.equals(user)) {
            this.usersAccounts.remove(userResult);
            result = true;
        }
        return result;
    }

    /**
     * Добавление счёта пользователю.
     *
     * @param passport паспорт пользователя, которому добавляем счёт.
     * @param account  счёт.
     * @return
     */
    public boolean addAccountToUser(String passport, Account account) {
        boolean result = false;
        User user = this.findUser(passport);
        if (user != null) {
            this.usersAccounts.get(user).add(account);
            result = true;
        }
        return result;
    }

    /**
     * Удаление счёта пользователя.
     *
     * @param passport   паспорт пользователя, у которого удаляем счёт.
     * @param requisites
     * @return
     */
    public boolean deleteAccountFromUser(String passport, int requisites) {
        boolean result = false;
        List<Account> userlist = this.usersAccounts.get(this.findUser(passport));
        if (userlist.size() != 0) {
            for (int i = 0; i < userlist.size(); i++) {
                if (userlist.get(i).getRequisites() == requisites) {
                    userlist.remove(i);
                    result = true;
                }
            }
        }
        return result;
    }

    /**
     * Получаем список всех счетов пользователя.
     *
     * @param passport
     * @return
     */
    public List<Account> getUserAccounts(String passport) {
        return this.usersAccounts.get(this.findUser(passport));
    }

    /**
     * Получаем список всех пользователей.
     *
     * @return
     */
    public Set<User> getUsers() {
        Set<User> result = new TreeSet<>(Comparator.comparing(User::getName));
        result.addAll(this.usersAccounts.keySet());
        return result;
    }

    /**
     * Перевод другому пользователю.
     *
     * @param srcPassport  от кого
     * @param srcRequisite откуда
     * @param desPassport  кому
     * @param desRequisite куда
     * @param amount       сколько
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
     *
     * @param srcPassport
     * @param srcRequisite откуда
     * @param desRequisite куда
     * @param amount       сколько
     * @return
     */
    public boolean transferMoney(String srcPassport, int srcRequisite,
                                 int desRequisite, double amount) {
        return this.transferMoney(srcPassport, srcRequisite, srcPassport, desRequisite, amount);
    }

    /**
     * Вспомогательный метод для поиска аккаунта.
     *
     * @param passport
     * @param requisites
     * @return
     */
    public Account findAccount(String passport, int requisites) {
        List<Account> list = this.getUserAccounts(passport);
        Account result = list.stream()
                .filter(x -> x.getRequisites() == requisites)
                .findFirst()
                .orElse(null);
        return result;
    }

    /**
     * Вспомогательный метод для поиска пользователя.
     *
     * @param passport
     * @return
     */
    public User findUser(String passport) {
        User result = this.usersAccounts.keySet().stream()
                .filter(x -> x.getPassport().equals(passport))
                .findFirst()
                .orElse(null);
        return result;
    }
}