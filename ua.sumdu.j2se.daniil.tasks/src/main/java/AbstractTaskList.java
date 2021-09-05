import java.util.stream.Stream;

public abstract class AbstractTaskList implements Iterable<Task> {
    public abstract void add(Task task);

    public abstract boolean remove(Task task);

    public abstract int size();

    public abstract Task getTask(int index);

    protected AbstractTaskList incoming(int from, int to) {
        if (from < 0 || to < 0) {
            throw new IndexOutOfBoundsException();
        }

        return (AbstractTaskList) getStream().filter(task -> isIntervalValid(task, from, to));
    }

    private boolean isIntervalValid(Task element, int from, int to) {
        return (from <= element.getStartTime() && element.getStartTime() <= to) || (from <= element.getTime() && element.getTime() <= to);
    }

    abstract Stream<Task> getStream();
}
