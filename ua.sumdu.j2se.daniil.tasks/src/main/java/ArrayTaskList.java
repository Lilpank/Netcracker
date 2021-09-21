import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * Класс содержит в себе массив объектов Task и различные методы для list.
 */

public class ArrayTaskList extends AbstractTaskList implements Iterator<Task>, Cloneable {

    private static final int INITIAL_CAPACITY = 10;
    private int size = 0;
    private Task[] elementData = {};
    private int position = 0;

    public ArrayTaskList() {
        elementData = new Task[INITIAL_CAPACITY];
    }

    @Override
    public @NotNull Iterator<Task> iterator() {
        return this;
    }

    @Override
    public void add(Task task) throws IllegalArgumentException {
        if (task == null) {
            throw new IllegalArgumentException();
        }

        if (size == elementData.length) {
            ensureCapacity(); // вызывается метод, если закончился массив и нужно создать новый с большим размером.
        }
        elementData[size++] = task;
    }


    //Метод создает новый массив с большим размером.
    private void ensureCapacity() {
        int newIncreasedCapacity = (elementData.length * 3) / 2 + 1;
        elementData = Arrays.copyOf(elementData, newIncreasedCapacity);
    }

    @Override
    public boolean remove(Task task) throws IllegalArgumentException {
        if (task == null) {
            throw new IllegalArgumentException();
        }

        for (int i = 0; i < size; i++) {
            if (equalsTasks(elementData[i], task)) {
                if (size - 1 - i >= 0) System.arraycopy(elementData, i + 1, elementData, i, size - 1 - i);
                size--;
                return true;
            }
        }
        return false;
    }

    //Метод проверяет задачи на эквивалентность.
    private boolean equalsTasks(Task element, Task task) {
        return (element.getTitle().equals(task.getTitle())) && element.getTime() == task.getTime();
    }

    @Override
    public int size() {
        return elementData.length;
    }

    @Override
    public Task getTask(int index) throws IndexOutOfBoundsException {
        if (index >= size()) {
            throw new IndexOutOfBoundsException("index не должен превышать размер листа.");
        }

        return elementData[index];
    }

    @Override
    public Stream<Task> getStream() {
        Stream<Task> stream = Stream.of(this.getTask(0));
        for (int i = 1; i < size(); i++) {
            stream = Stream.concat(stream, Stream.of(this.getTask(i)));
        }
        return stream;
    }


    @Override
    public boolean hasNext() {
        return position < size();
    }


    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (obj == this) return true;
        if (!(obj instanceof ArrayTaskList)) return false;

        if (((ArrayTaskList) obj).size() == this.size()) {
            for (int i = 0; i < this.size(); i++) {
                if (!(this.getTask(i).equals(((ArrayTaskList) obj).getTask(i)))) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }


    @Override
    protected ArrayTaskList clone() throws CloneNotSupportedException {
        return (ArrayTaskList) super.clone();
    }


    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        for (int i = 0; i < size(); i++) {
            str.append(getTask(i).toString());
            str.append("\n");
        }

        return "ArrayTaskList size: " + size() + str;
    }

    @Override
    public Task next() {
        if (position == size()) {
            throw new NoSuchElementException();
        }
        Task task = getTask(position);
        position++;
        return task;
    }
}
