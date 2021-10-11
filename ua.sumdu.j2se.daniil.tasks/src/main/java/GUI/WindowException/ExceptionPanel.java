package GUI.WindowException;

public class ExceptionPanel extends Exception {
    public ExceptionPanel() {
    }

    public ExceptionPanel(Exception e) {
        super("что-то пошло не так");
    }

    public ExceptionPanel(String message) {
        super(message);
    }

    public ExceptionPanel(NumberFormatException message) {
        super("Введите число!");
    }

    public ExceptionPanel(NullPointerException message) {
        super(message);
    }

    public ExceptionPanel(IllegalArgumentException message) {
        super("Введите число!");
    }

}
