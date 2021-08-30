import java.util.ArrayList;
import java.util.Arrays;

/**
 * Класс содержит в себе массив объектов Task и различные методы для list.
 */

public class ArrayTaskList {

    private static final int INITIAL_CAPACITY = 10;
    private int size = 0;
    private Task[] elementData = {};


    public ArrayTaskList() {
        elementData = new Task[INITIAL_CAPACITY];
    }

    public void add(Task task) {
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


    public boolean remove(Task task) {
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
        return element.getTitle().equals(task.getTitle()) && element.getTime() == task.getTime();
    }

    public int size() {
        return elementData.length;
    }

    public Task getTask(int index) {
        return elementData[index];
    }

    /**
     * В общем, метод возраващает подмножество задач, которые находятся в интервале [from, to]
     *
     * @param from - time
     * @param to   - time
     * @return
     */
    public ArrayList<Task> incoming(int from, int to) {
        ArrayList<Task> tasks = new ArrayList<>();
        for (Task element : elementData) {
            if (isIntervalValid(element, from, to)) {
                tasks.add(element);
            }
        }
        return tasks;
    }


    // Метод проверяет, входит ли задача в заданный интервал [from, to]
    private boolean isIntervalValid(Task element, int from, int to) {
        return (from <= element.getStartTime() && element.getStartTime() <= to) || (from <= element.getTime() && element.getTime() <= to);
    }

    public void display() {
        System.out.print("Displaying list : ");
        for (int i = 0; i < size; i++) {
            System.out.print(elementData[i].getTime() + " ");
        }
    }
}
