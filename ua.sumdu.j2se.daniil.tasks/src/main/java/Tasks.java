import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class Tasks {
    /**
     * Метод возвращающий подмножество задач.
     *
     * @param tasks - итератор
     * @param start - начальное время
     * @param end   - конечное время
     * @return
     */
    public static Iterable<Task> incoming(Iterable<Task> tasks, LocalDateTime start, LocalDateTime end) {
        if (start == null || end == null) {
            throw new IllegalArgumentException();
        }
        if (start.isAfter(end)) {
            throw new IllegalArgumentException();
        }
        return StreamSupport.stream(tasks.spliterator(), false)
                .filter(task -> isIntervalValid(task, start, end))
                .collect(Collectors.toList());
    }

    private static boolean isIntervalValid(Task element, LocalDateTime from, LocalDateTime to) {
        return from.isBefore(element.getStartTime()) && to.isAfter(element.getStartTime()) ||
                from.isBefore(element.getTime()) && to.isAfter(element.getTime());
    }

    /**
     * Например, список включает следующие задачи:
     * 1.	Пообедать с красивой девушкой 24 августа в 16:00.
     * 2.	Утренняя пробежка с 1 марта по 1 сентября каждый день в 8:15.
     * 3.	Прием лекарств с 20 августа в 8:15 по 28 августа каждые 12 часов.
     * 4.	Встреча с друзьями 1 сентября в 18:00.
     * <p>
     * Итак, календарь задач на период с 25 августа 8:00 по 26 августа 8:00 будет выглядеть следующим образом:
     * Дата Задачи
     * 25 августа, 8:15 Утренняя пробежка, прием лекарств
     * 25 августа, 20:15 Прием лекарств
     *
     * @param tasks - входящий список задач.
     * @param start - отсчет времени
     * @param end   - конечное время
     * @return
     */
    public SortedMap<LocalDateTime, Set<Task>> calendar(Iterable<Task> tasks, LocalDateTime start, LocalDateTime end) {
        Iterable<Task> iterable = incoming(tasks, start, end);
        TreeMap<LocalDateTime, Set<Task>> sortedMap = new TreeMap<>();
        tasks.forEach(task -> sortedMap.put(task.getTime(),
                StreamSupport.stream(iterable.spliterator(), false).collect(Collectors.toSet())

        ));
//        tasks.forEach(task -> sortedMap.put(
//                start,
//                StreamSupport.stream(iterable.spliterator(), false).collect(Collectors.toSet())
//                )
//        );
        return sortedMap;
    }
}