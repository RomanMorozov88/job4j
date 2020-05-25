package ru.job4j.ood.simplegenerator;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 1. Генератор должен получать входную строку с ключами в тексте и список значений по этим ключам.
 * Например. Входящая строка String template = "I am a ${name}, Who are ${subject}? "
 * и список значений ассоциированных по ключу name -> "Petr", subject -> "you"
 * На выходе должна быть строка - "I am Petr, Who are you?"     [\\$\\{.+\\}]+   -заготовка шаблона.
 * Другой пример. " Help, ${sos}, ${sos}, ${sos}", sos -> "Aaaa". Должно получится " Help, Ааа, Ааа, Ааа"
 * 2. Программа должна учитывать. что ключей нет в карте и кидать исключение.
 * 3. Программа должна учитываться. что в карте есть лишние ключи и тоже кидать исключение.
 * В качестве карты можно использовать Map<String, String>, либо Pair[] - Pair - объект из библиотеки guava.
 * Примечание. В этой задаче нужно использовать RegExp
 */
public class SimpleGenerator {

    private final Pattern keys = Pattern.compile("(?<KEY>\\$\\{(?<SUBKEY>\\w+)})");

    public String generator(String template, Map<String, String> values) {
        String result = template;
        Matcher matcher = keys.matcher(result);
        Set<String> forCounting = new HashSet<>();
        while (matcher.find()) {
            String key = matcher.group("KEY");
            forCounting.add(key);
            String subkey = matcher.group("SUBKEY");
            if (values.get(subkey) == null) {
                throw new ImbalanceException("There is not a key " + subkey + ".");
            }
            result = result.replace(key, values.get(subkey));
        }
        if (forCounting.size() != values.keySet().size()) {
            throw new ImbalanceException("Count of keys and count of values is different.");
        }
        return result;
    }
}