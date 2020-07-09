package ru.job4j.list.hascycle;

public class CycleNode {

    /**
     * Для определения наличия цикла взята часть алгоритма Брента.
     *
     * @param first
     * @return
     */
    public static boolean hasCycleAB(Node first) {

        //Эти две переменные необходимы для
        //организации перемещения элемента, от которого будем пробегать по списку(здесь- slowStep).
        //В самом алгоритме-
        //"наименьшая степень 2i числа 2, которая больше как λ(длина цикла),
        //так и μ(индекс первого элемента в цикле)"(с)
        //(здесь за ненадобностью поиск μ выброшен)
        int power = 1;
        //В итоге- длина цикла.
        int lam = 1;

        //Вспомогательные узлы.
        Node slowStep = first;
        Node fastStep = first.next;

        while (slowStep != fastStep) {
            //Если эти значения равны- подтягиваем slowStep до fastStep
            //и двигаемся дальше.
            if (power == lam) {
                slowStep = fastStep;
                power *= 2;
                lam = 0;
            }
            //Т.к. fastStep "движется"- то проверяем по ней наличие окончания списка,
            //если он не зациклен.
            if (fastStep == null) {
                return false;
            }
            //Когда и slowStep и fastStep окажутся в цикле-
            //fastStep нагонит "неподвижную" slowStep.
            fastStep = fastStep.next;
            lam++;
        }
        return true;
    }

    /**
     * Алгоритм черепахи и зайца.
     * @param first
     * @return
     */
    public static boolean hasCycleTH(Node first) {
        Node slowStep = first;
        Node fastStep = first;

        while (fastStep != null && fastStep.next != null) {
            slowStep = slowStep.next;
            fastStep = fastStep.next.next;
            if (fastStep == slowStep) {
                return true;
            }
        }
        return false;
    }
}
