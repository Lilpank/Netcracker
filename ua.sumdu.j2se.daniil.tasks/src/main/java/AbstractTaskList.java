import java.time.LocalDateTime;
import java.util.stream.Stream;

public abstract class AbstractTaskList implements Iterable<Task> {
    public abstract void add(Task task);

    public abstract boolean remove(Task task);

    public abstract int size();

    public abstract Task getTask(int index);

    protected final AbstractTaskList incoming(LocalDateTime from, LocalDateTime to) {
        if (from == null || to == null) {
            throw new IllegalArgumentException();
        }
        if (from.isAfter(to)) {
            throw new IllegalArgumentException();
        }

        return (AbstractTaskList) getStream().filter(task -> isIntervalValid(task, from, to));
    }

    private boolean isIntervalValid(Task element, LocalDateTime from, LocalDateTime to) {
        return (from.isBefore(element.getStartTime()) && to.isAfter(element.getStartTime())) || (from.isBefore(element.getTime()) && to.isAfter(element.getTime()));
    }

    abstract Stream<Task> getStream();
}
