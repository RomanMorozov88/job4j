package ru.job4j.bankaccounts.menu;

import org.junit.Test;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import org.junit.After;
import org.junit.Before;
import ru.job4j.bankaccounts.*;


public class StartUITest {

    private final PrintStream stdout = System.out;
    private final ByteArrayOutputStream out = new ByteArrayOutputStream();

    private final String separator = System.lineSeparator();
    private final String menu = new StringBuilder()
            .append("----------Меню----------").append(separator)
            .append("0. Показать список пользователей.").append(separator)
            .append("1. Добавить нового пользователя.").append(separator)
            .append("2. Удалить пользователя.").append(separator)
            .append("3. Добавить счёт.").append(separator)
            .append("4. Удалить счёт.").append(separator)
            .append("5. Показать все счета пользователя.").append(separator)
            .append("6. Сделать перевод между счетами одного пользователя.").append(separator)
            .append("7. Сделать перевод другому пользователю.").append(separator)
            .append("8. Выход.").append(separator)
            .toString();

    @Before
    public void loadOutput() {
        System.out.println("execute before method");
        System.setOut(new PrintStream(this.out));
    }

    @After
    public void backOutput() {
        System.setOut(this.stdout);
        System.out.println("execute after method");
    }

    /**
     * метод для установки input и для запуска StartUI().init().
     * @param users массив, содержащий последовательность ответов пользователя.
     */
    public void setInput(String[] users, UsersMap usersmap) {
        Input input = new StubInput(users);
        new StartUI(input, usersmap).init();
    }

    @Test
    public void whenUserAddUser() {
        UsersMap usersmap = new UsersMap();
        setInput(new String[]{"1", "test", "desc", "8"}, usersmap);
        assertThat(
                new String(this.out.toByteArray()),
                is(
                        new StringBuilder()
                                .append(menu)
                                .append("------------ Добавление нового пользователя. --------------").append(separator)
                                .append("Имя: test").append(separator)
                                .append("Паспорт: desc").append(separator)
                                .append("Счетов у пользователя пока нет.").append(separator)
                                .append(menu)
                                .toString()
                )
        );
    }

    @Test
    public void whenDeleteUser() {
        UsersMap usersmap = new UsersMap();
        usersmap.addUser(new User("name", "test"));
        setInput(new String[]{"2", "name", "test", "8"}, usersmap);
        assertThat(
                new String(this.out.toByteArray()),
                is(
                        new StringBuilder()
                                .append(menu)
                                .append("------------ Удаление пользователя. --------------").append(separator)
                                .append("------------ Пользователь удалён. --------------").append(separator)
                                .append(menu)
                                .toString()
                )
        );
    }

    @Test
    public void whenAddAccountToUser() {
        UsersMap usersmap = new UsersMap();
        User user = new User("name", "test");
        usersmap.addUser(user);
        setInput(new String[]{"3", "test", "10", "8"}, usersmap);
        assertThat(
                new String(this.out.toByteArray()),
                is(
                        new StringBuilder()
                                .append(menu)
                                .append("------------ Добавление нового счёта. --------------").append(separator)
                                .append("Имя: name").append(separator)
                                .append("Паспорт: test").append(separator)
                                .append("Счета пользователя:").append(separator)
                                .append("-----------------------------------------").append(separator)
                                .append("Реквезиты: " + usersmap.getUserAccounts("test").get(0).getRequisites())
                                .append(separator)
                                .append("Баланс: 10.0").append(separator)
                                .append(menu)
                                .toString()
                )
        );
    }

    @Test
    public void whenDeleteAccount() {
        UsersMap usersmap = new UsersMap();
        User user = new User("name", "test");
        usersmap.addUser(user);
        Account account = new Account(10);
        usersmap.addAccountToUser(user.getPassport(), account);
        setInput(new String[]{"4", "test",
                String.valueOf(account.getRequisites()), "8"}, usersmap);
        assertThat(
                new String(this.out.toByteArray()),
                is(
                        new StringBuilder()
                                .append(menu)
                                .append("------------ Удаление счёта. --------------").append(separator)
                                .append("------------ Счёт удалён. --------------").append(separator)
                                .append(menu)
                                .toString()
                )
        );
    }

    @Test
    public void whenShowAllAccounts() {
        UsersMap usersmap = new UsersMap();
        User user = new User("name", "test");
        usersmap.addUser(user);
        Account account1 = new Account(10);
        Account account2 = new Account(7.4);
        usersmap.addAccountToUser(user.getPassport(), account1);
        usersmap.addAccountToUser(user.getPassport(), account2);
        setInput(new String[]{"5", "test", "8"}, usersmap);
        assertThat(
                new String(this.out.toByteArray()),
                is(
                        new StringBuilder()
                                .append(menu)
                                .append("------------ Список счетов пользователяname--------------").append(separator)
                                .append("-----------------------------------------").append(separator)
                                .append("Реквизиты: " + account1.getRequisites()).append(separator)
                                .append("Баланс: 10.0").append(separator)
                                .append("-----------------------------------------").append(separator)
                                .append("Реквизиты: " + account2.getRequisites()).append(separator)
                                .append("Баланс: 7.4").append(separator)
                                .append("-----------------------------------------").append(separator)
                                .append(menu)
                                .toString()
                )
        );
    }
    @Test
    public void whenShowAllUsers() {
        UsersMap usersmap = new UsersMap();
        User user1 = new User("name1", "test1");
        User user2 = new User("name2", "test2");
        usersmap.addUser(user1);
        usersmap.addUser(user2);
        setInput(new String[]{"0", "8"}, usersmap);
        assertThat(
                new String(this.out.toByteArray()),
                is(
                        new StringBuilder()
                                .append(menu)
                                .append("------------ Список пользователей. --------------").append(separator)
                                .append("name1").append(separator)
                                .append("name2").append(separator)
                                .append("-----------------------------------------").append(separator)
                                .append(menu)
                                .toString()
                )
        );
    }
    @Test
    public void whenTransferToOtherUser() {
        UsersMap usersmap = new UsersMap();
        User user1 = new User("name", "test1");
        User user2 = new User("name", "test2");
        usersmap.addUser(user1);
        usersmap.addUser(user2);
        Account account1 = new Account(50);
        Account account2 = new Account(0);
        usersmap.addAccountToUser(user1.getPassport(), account1);
        usersmap.addAccountToUser(user2.getPassport(), account2);
        setInput(new String[]{"7", "test1", "test2",
                String.valueOf(account1.getRequisites()),
                String.valueOf(account2.getRequisites()), "30", "8"}, usersmap);
        assertThat(
                new String(this.out.toByteArray()),
                is(
                        new StringBuilder()
                                .append(menu)
                                .append("Перевод выполнен.").append(separator)
                                .append(menu)
                                .toString()
                )
        );
    }
    @Test
    public void whenTransferToOtherAccount() {
        UsersMap usersmap = new UsersMap();
        User user1 = new User("name", "test1");
        usersmap.addUser(user1);
        Account account1 = new Account(50);
        Account account2 = new Account(0);
        usersmap.addAccountToUser(user1.getPassport(), account1);
        usersmap.addAccountToUser(user1.getPassport(), account2);
        setInput(new String[]{"6", "test1",
                String.valueOf(account1.getRequisites()),
                String.valueOf(account2.getRequisites()), "30", "8"}, usersmap);
        assertThat(
                new String(this.out.toByteArray()),
                is(
                        new StringBuilder()
                                .append(menu)
                                .append("Перевод выполнен.").append(separator)
                                .append(menu)
                                .toString()
                )
        );
    }
    @Test(expected = MenuOutException.class)
    public void whenOutOfMenu() {
        UsersMap usersmap = new UsersMap();
        setInput(new String[]{"9"}, usersmap);
    }
}