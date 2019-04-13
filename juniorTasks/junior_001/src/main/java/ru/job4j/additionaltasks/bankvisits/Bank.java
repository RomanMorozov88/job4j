package ru.job4j.additionaltasks.bankvisits;

import java.util.*;

/**
 * От банка поступил заказ разработать алгоритм подсчета временных интервалов,
 * когда в банке было максимальное число посетителей.
 * Банк начинает работать с 8:00 по 20:00.
 * У каждого посетителя есть время когда он пришел и когда он ушел.
 * Время фиксируется в миллисекундах.
 * Необходимо разработать алгоритм, который принимает список всех посещений и выдает
 * интервалы времени, когда в банке было максимальное количество посетителей.
 */
public class Bank {

    public static class Visit {
        private final long in;
        private final long out;

        public Visit(final long in, final long out) {
            this.in = in;
            this.out = out;
        }
    }

    public static class Info {
        private long max;
        private long start;
        private long end;

        public Info() {
            this(0, 0, 0);
        }

        public Info(long max, long start, long end) {
            this.max = max;
            this.start = start;
            this.end = end;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Info info = (Info) o;

            if (max != info.max) return false;
            if (start != info.start) return false;
            return end == info.end;
        }

        @Override
        public int hashCode() {
            int result = (int) (max ^ (max >>> 32));
            result = 31 * result + (int) (start ^ (start >>> 32));
            result = 31 * result + (int) (end ^ (end >>> 32));
            return result;
        }

        @Override
        public String toString() {
            return "Info{" +
                    "max=" + max +
                    ", start=" + this.toTime(this.start) +
                    ", end=" + this.toTime(this.end) +
                    '}';
        }

        public String toTime(long time) {
            Calendar cal = Calendar.getInstance();
            cal.setTimeInMillis(time);
            return String.format("%s:%s", cal.get(Calendar.HOUR), cal.get(Calendar.MINUTE));
        }
    }

    /**
     * В ходе выполения метода можем столкнуться с ситуацией,
     * когда будет два или более одинаковых Info. Что бы избавиться
     * от дубликатов внутри используем set.
     *
     * @param visits
     * @return
     */
    public List<Info> max(List<Visit> visits) {

        Set<Info> workSet = new HashSet<>();

        for (Visit v : visits) {
            long count = 0;
            //Устанавливаем временные границы, которыми будем оперировать.
            long inBorder = v.in;
            long outBorder = v.out;
            for (Visit v1 : visits) {
                long in = v1.in;
                long out = v1.out;
                if (in <= outBorder && out >= inBorder) {
                    count++;
                    if (in > inBorder) {
                        inBorder = in;
                    }
                    if (out < outBorder) {
                        outBorder = out;
                    }
                }
            }
            workSet.add(new Info(count, inBorder, outBorder));
        }
        return new ArrayList<>(workSet);
    }
}