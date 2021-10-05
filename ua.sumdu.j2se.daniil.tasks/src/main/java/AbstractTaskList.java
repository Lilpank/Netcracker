import java.io.OutputStream;
import java.io.Serializable;
import java.util.stream.Stream;

public abstract class AbstractTaskList implements Iterable<Task>, Serializable {
    private static final long serialVersionUID = -7347283020702680691L;

    public abstract void add(Task task);

    public abstract boolean remove(Task task);

    public abstract int size();

    public abstract Task getTask(int index);


    public Stream<Task> getStream() {
        Stream<Task> stream = Stream.of(this.getTask(0));
        for (int i = 1; i < size(); i++) {
            stream = Stream.concat(stream, Stream.of(this.getTask(i)));
        }
        return stream;
    }
}
