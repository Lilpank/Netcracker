import java.util.ArrayList;
import java.util.List;

/**
 * Класс содержит в себе list задач и различные методы для list.
 */
public class ArrayTaskList {

    private final List<Task> taskArrayList = new ArrayList<>();


    public void add(Task task) {
        taskArrayList.add(task);
    }

    public boolean remove(Task task) {
        for (Task element : taskArrayList) {
            if (element.getTitle().equals(task.getTitle())) {
                return taskArrayList.remove(element);
            }
        }
        return false;
    }

    public int size() {
        return taskArrayList.size();
    }

    public Task getTask(int index) {
        return taskArrayList.get(index);
    }

    /**
     * В общем, метод возраващает подмножество задач, которые находятся в интервале [from, to]
     *
     * @param from - time
     * @param to   - time
     * @return list
     */
    public ArrayList<Task> incoming(int from, final int to) {
        ArrayList<Task> temp = new ArrayList<>();
        for (Task element : taskArrayList) {
            if (isIntervalValid(element, from, to)) {
                temp.add(element);
            }
        }
        return temp;
    }

    // Проверка, входит ли задача в заданный интервал [from, to]
    private boolean isIntervalValid(Task element, int from, int to) {
        return (from <= element.getStartTime() && element.getStartTime() <= to) || (from <= element.getTime() && element.getTime() <= to);
    }
}
