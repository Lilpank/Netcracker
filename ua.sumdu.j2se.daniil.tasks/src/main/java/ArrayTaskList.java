import java.util.ArrayList;

public class ArrayTaskList {
    /**
     * Класс содержит в себе list задач и различные методы для list.
     */
    private final ArrayList<Task> list = new ArrayList<Task>();


    public void add(Task task) {
        list.add(task);
    }

    public boolean remove(Task task) {
        for (Task element : list) {
            if (element.getTitle().equals(task.getTitle())) {
                return list.remove(element);
            }
        }
        return false;
    }

    public int size() {
        return list.size();
    }

    public Task getTask(int index) {
        return list.get(index);
    }

    /**
     *В общем, метод возраващает подмножество задач, которые находятся в интервале [from, to]
     * @param from - time
     * @param to   - time
     * @return list
     */
    public ArrayList<Task> incoming(int from, final int to) {
        ArrayList<Task> temp = new ArrayList<Task>();
        for (Task element : list) {
            if ((from <= element.getStartTime() && element.getStartTime() <= to) || (from <= element.getTime() && element.getTime() <= to)) {
                temp.add(element);
            }
        }
        return temp;
    }
}
