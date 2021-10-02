import org.jetbrains.annotations.NotNull;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.stream.Stream;

/**
 * Класс создан на основе логики коллекции LinkedList,
 * используется для удобства быстрой вставки\удаления элемента, выполняется за константное время.
 */

public class LinkedTaskList extends AbstractTaskList implements @NotNull Iterator<Task>, Cloneable {
    private Node head;
    private Node tail;
    private int count = 0;
    private int position = 0;

    @Override
    public @NotNull Iterator<Task> iterator() {
        return this;
    }

    @Override
    public boolean hasNext() {
        return position < size();
    }

    @Override
    public int hashCode() {
        return Objects.hash(head, tail, count, position);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (obj == this) return true;
        if (!(obj instanceof LinkedTaskList)) return false;

        if (((LinkedTaskList) obj).size() == this.size()) {
            for (int i = 0; i < this.size(); i++) {
                if (!(this.getTask(i).equals(((LinkedTaskList) obj).getTask(i)))) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    @Override
    protected LinkedTaskList clone() throws CloneNotSupportedException {
        return (LinkedTaskList) super.clone();
    }

    @Override
    public Task next() throws NoSuchElementException {
        if (position == size()) {
            throw new NoSuchElementException();
        }
        Task task = getTask(position);
        position++;
        return task;
    }

    // Создаем класс Узел, у которого есть ссылки на first, last элемент.
    private static class Node {
        public Task data;
        public Node next = null;
        public Node previous = null;
    }

    // Создаем лист
    public LinkedTaskList() {
        head = null;
        tail = null;
    }


    @Override
    public void add(Task task) throws IllegalArgumentException {
        if (task == null) {
            throw new IllegalArgumentException();
        }
        Node newNode = new Node();
        newNode.data = task;

        if (head == null) {
            head = newNode;
        } else {
            tail.next = newNode;
        }
        newNode.previous = tail;
        tail = newNode;
        count++;
    }


    @Override
    public boolean remove(Task task) throws IllegalArgumentException {
        if (task == null) {
            throw new IllegalArgumentException();
        }
        // Если лист пустой.
        if (count == 0) {
            return false;
        }
        //Проверка если пришел Task на самый первый элемент, то есть head.
        if (equalsTasks(head.data, task)) {
            head = head.next;
            head.previous = null;
            --count;
            return true;
        }
        // Если пришел Task на самый последний элемент, то есть tail.
        if (equalsTasks(tail.data, task)) {
            tail = tail.previous;
            tail.previous = null;
            tail.next = null;
            --count;
            return true;
        }
        // Находим нужный нам Node
        Node deleteNode = findNode(task);
        // Если не нашли
        if (deleteNode == null) {
            return false;
        } else { // Если нашли, избовляемся от ссылок объекта
            Node last = deleteNode.previous;
            Node first = deleteNode.next;
            last.next = first;
            first.previous = last;

            deleteNode.next = null;
            deleteNode.previous = null;
            --count;
            return true;
        }
    }

    private Node findNode(Task value) {
        Node node = head;
        while (node.next != null) {
            if (equalsTasks(node.next.data, value)) {
                return node.next;
            }
            node = node.next;
        }
        return null;
    }

    @Override
    public int size() {
        return count;
    }

    @Override
    public Task getTask(int index) throws IndexOutOfBoundsException {
        if (index >= count || index < 0) {
            throw new IndexOutOfBoundsException();
        }

        int i = 0;
        Node currentNode = head;
        while (currentNode != null) {
            if (i == index) {
                return currentNode.data;
            }
            currentNode = currentNode.next;
            ++i;
        }
        return null;
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
    public String toString() {
        StringBuilder str = new StringBuilder();
        for (int i = 0; i < size(); i++) {
            str.append(getTask(i).toString());
            str.append("\n");
        }

        return "LinkedListTask size: " + size() + "\n" + str;
    }


    //Метод проверяет задачи на эквивалентность.
    private boolean equalsTasks(Task element, Task task) {
        return (element.getTitle().equals(task.getTitle())) && element.getTime() == task.getTime();
    }
}
