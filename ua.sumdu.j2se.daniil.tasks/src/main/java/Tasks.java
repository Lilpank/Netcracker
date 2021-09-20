import java.time.LocalDateTime;
import java.util.*;

public class Tasks {
    public static Iterable<Task> incoming(Iterable<Task> tasks, LocalDateTime start, LocalDateTime end) {
        // Не понял задания..
        Iterator<Task> iterator = tasks.iterator();
        while (iterator.hasNext()) {
            if (!(iterator.next().getStartTime().isAfter(start) || iterator.next().getStartTime().isBefore(end))) {
                iterator.remove();
            }
        }
        return tasks;
    }

    public SortedMap<LocalDateTime, Set<Task>> calendar(Iterable<Task> tasks, LocalDateTime start, LocalDateTime end) {
        return null;
    }
}