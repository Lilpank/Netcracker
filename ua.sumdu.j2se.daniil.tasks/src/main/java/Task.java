/**
 * Класс отвечает за создание задачи.
 * Задача имеет название и время, когда она будет выполняться.
 * Задачи являются повторяющимися и неповторяющимися.
 * задачи могут быть активными или неактивными.
 * Например, в праздничный день задача «Утренняя пробежка» может быть неактивна и временно не выполняться.
 * <p>
 * Внимание: time в методичке подразумевается как часы, например, start = 8 утра, interval = 2 часа, end=54часа.
 *
 * @version 0.1
 * @autor Daniil Gorelykh
 */
public class Task implements Cloneable{
    private String title;
    private int end;
    private int interval = 0;
    private int start;
    private boolean active = true;
    private int time;

    /**
     * Конструктор для неактивной задачи, без повторения с заданным именем и временем.
     */
    public Task(String title, int time) throws IllegalArgumentException {
        if (time < 0) {
            throw new IllegalArgumentException("Время не может быть отрицательным.");
        }
        if (title == null) {
            throw new IllegalArgumentException();
        }
        this.title = title;
        this.time = time;
    }

    /**
     * Конструктор создает неактивную задачу для выполнения в указанном диапазоне времени
     * (включая время начала и окончания) с заданным интервалом повторения и с заданным именем.
     *
     * @param interval - время, через которое будет повторяться задача.
     */
    public Task(String title, int start, int end, int interval) throws IllegalArgumentException {
        if (interval <= 0 || start < 0 || end <= 0 || (start >= end)) {
            throw new IllegalArgumentException();
        }

        if (title == null) {
            throw new IllegalArgumentException();
        }
        this.title = title;
        this.start = start;
        this.end = end;
        this.interval = interval;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) throws IllegalArgumentException {
        if (title == null) {
            throw new IllegalArgumentException();
        }
        this.title = title;
    }

    /**
     * Возвращает active задачи, default - true.
     */
    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    /**
     * Возвращает время начало повторения задачи.
     * Смысл - есть какой-либо интервал повторения, следует определить точное время когда нужно выполнить задачу.
     *
     * @return - точное время выполнения задачи.
     */
    public int getTime() {
        if (isRepeated()) {
            this.start = (this.start + this.interval) % 24;
            return this.start;
        }
        return this.time;
    }

    public void setTime(int time) throws IllegalArgumentException {
        if (time < 0) {
            throw new IllegalArgumentException();
        }
        if (isRepeated()) {
            this.interval = 0;
        } else {
            this.time = time;
        }
    }

    public int getStartTime() {
        if (isRepeated()) {
            return this.start;
        }
        return this.time;
    }

    public int getRepeatInterval() {
        return this.interval;
    }

    public int getEndTime() {
        if (isRepeated()) {
            return this.end;
        }
        return this.time;
    }

    public void setTime(int start, int end, int interval) {
        if (interval <= 0 || start < 0 || end <= 0 || (start >= end)) {
            throw new IllegalArgumentException();
        }
        this.start = start;
        this.end = end;
        this.interval = interval;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (!(obj instanceof Task)) return false;
        if (obj == this) return true;

        return isCheckTask(this, (Task) obj);
    }

    private boolean isCheckTask(Task myTask, Task other) {
        return myTask.getTitle().equals(other.getTitle()) && myTask.getTime() == other.getTime();
    }

    @Override
    protected Task clone() throws CloneNotSupportedException {
        return (Task) super.clone();
    }

    @Override
    public String toString() {
        return "Task - " + getTitle() + "Время начала: " + getTime() + " Время окончания: " + getEndTime();
    }

    public boolean isRepeated() {
        return getRepeatInterval() != 0;
    }

    /**
     * Метод проверяет, выполнится ли следующее задание относительно текущего времени.
     *
     * @param current - текущее время.
     * @return -1 в плохом случае, time в лучшем.
     */
    public int nextTimeAfter(int current) {
        if (isActive()) {
            return getTime() - current > 0 ? getTime() : -1;
        }
        return -1;
    }

    public static void main(String[] args) throws CloneNotSupportedException {
        Task task = new Task("", 1);
        Task cloneTask = task.clone();
        System.out.println(cloneTask.equals(task));
    }

}