package Function;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

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
public class Task implements Cloneable, Serializable {
    private static final long serialVersionUID = 2261744915236392856L;
    private String title;
    private LocalDateTime end;
    private int interval = 0;
    private LocalDateTime start;
    private boolean active = true;
    private LocalDateTime time;

    /**
     * Конструктор для неактивной задачи, без повторения с заданным именем и временем.
     */
    public Task(String title, LocalDateTime time) throws IllegalArgumentException {
        if (title == null || time == null) {
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
    public Task(String title, LocalDateTime start, LocalDateTime end, int interval) throws IllegalArgumentException {
        if (interval <= 0) {
            throw new IllegalArgumentException();
        }

        if (title == null || start == null || end == null) {
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
    public LocalDateTime getTime() {
        if (isRepeated()) {
            return this.start;
        }
        return this.time;
    }

    public void setTime(LocalDateTime time) throws IllegalArgumentException {
        if (time == null) {
            throw new IllegalArgumentException();
        }

        if (isRepeated()) {
            this.interval = 0;
        } else {
            this.time = time;
        }
    }

    public LocalDateTime getStartTime() {
        return getTime();
    }

    public int getRepeatInterval() {
        return this.interval;
    }

    public LocalDateTime getEndTime() {
        if (isRepeated()) {
            return this.end;
        }
        return this.time;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return interval == task.interval &&
                active == task.active &&
                Objects.equals(title, task.title) &&
                Objects.equals(end, task.end) &&
                Objects.equals(start, task.start) &&
                Objects.equals(time, task.time);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, end, interval, start, active, time);
    }

    public void setTime(LocalDateTime start, LocalDateTime end, int interval) {
        if (interval <= 0 || start.isAfter(end)) {
            throw new IllegalArgumentException();
        }
        this.start = start;
        this.end = end;
        this.interval = interval;
    }

    @Override
    protected Task clone() throws CloneNotSupportedException {
        return (Task) super.clone();
    }

    @Override
    public String toString() {
        return "Function.Task - " + getTitle() + " Время начала: " + getTime() + " Время окончания: " + getEndTime();
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
    public LocalDateTime nextTimeAfter(LocalDateTime current) {
        if (isActive()) {
            if (this.getTime().isBefore(current)) return this.getTime();
        }
        return null;
    }

    public String getActive() {
        return isActive() ? "1" : "0";
    }

    public String getExecutionTime() {
        return isRepeated() ? " " + getStartTime() + "\n" + getEndTime() : " " + getTime();
    }
}