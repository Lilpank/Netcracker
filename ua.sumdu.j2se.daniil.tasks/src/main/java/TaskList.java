
public interface TaskList<T> {
    public void add(Task task);

    public boolean remove(Task task);

    public int size();

    public Task getTask(int index);

    public T incoming(int from, int to);
}
