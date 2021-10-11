package GUI;


import Function.AbstractTaskList;
import Function.LinkedTaskList;
import Function.Task;
import GUI.AbstractModel.AbstractModelForTask;
import GUI.WindowException.ExceptionPanel;
import GUI.WindowException.WindowException;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class CreatingTableTask extends JDialog {
    private final List<Task> dateTimeList = new ArrayList<>();

    private final AbstractModelForTask tableModel = new AbstractModelForTask(dateTimeList);
    private final JTable tableXY = new JTable(tableModel);
    private final JButton buttonCreateFunction = new JButton("Создать");
    private final JButton buttonCreateTask = new JButton("Создать задачи");
    private final JLabel textField = new JLabel("Введите количество задач: ");
    private final JTextField textFieldCount = new JTextField();
    private final AbstractTaskList taskList = new LinkedTaskList();

    public CreatingTableTask() {
        //размеры окна, и Layout
        getContentPane().setLayout(new GridLayout());
        setPreferredSize(new Dimension(MainFrame.WIDTH, MainFrame.HEIGHT));
        setMaximumSize(new Dimension(MainFrame.WIDTH, MainFrame.HEIGHT));
        setMinimumSize(new Dimension(MainFrame.WIDTH, MainFrame.HEIGHT));
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        //чтобы пользователь мог выбрать только 1 строку
        tableXY.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        compose();

        //окно по середине выйдет
        setLocationRelativeTo(null);
        // парсер ввода числа точек
        buttonCreateTask.addActionListener(args -> {
            try {
                int temp = -1;
                temp = Integer.parseInt(textFieldCount.getText());
                if (temp < 0) {
                    throw new ExceptionPanel("Введите положительное число!");
                }
                addTableLine(temp);
            } catch (NumberFormatException exception) {
                new WindowException(new ExceptionPanel(exception));
            } catch (NullPointerException exception) {
                new WindowException(new ExceptionPanel(exception));
            } catch (IllegalArgumentException exception) {
                new WindowException(new ExceptionPanel(exception));
            } catch (ExceptionPanel exceptionPanel) {
                new WindowException(exceptionPanel);
            }
        });

        //закрывается окно с таблицей
        buttonCreateFunction.addActionListener(args -> {
            try {
                setTaskList();
                this.dispose();
            } catch (IllegalArgumentException exception) {
                new WindowException(new ExceptionPanel(exception));
            }
        });
        setModal(true);
        setVisible(true);
    }


    private void compose() {
        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);
        JScrollPane tableScrollPane = new JScrollPane(tableXY);

        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(textField)
                                .addComponent(textFieldCount)))
                .addComponent(buttonCreateTask)
                .addComponent(buttonCreateFunction)
                .addComponent(tableScrollPane)
        );

        layout.setVerticalGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(textField)
                        .addComponent(textFieldCount))
                .addComponent(buttonCreateTask)
                .addComponent(tableScrollPane)
                .addComponent(buttonCreateFunction)
        );
    }

    private void addTableLine(int count) {
        tableModel.clear();
        tableModel.fireTableDataChanged();

        for (int i = 0; i < count; i++) {
            dateTimeList.add(new Task("default title", LocalDateTime.now()));

            tableModel.fireTableDataChanged();
        }
    }

    private void setTaskList() {
        for (Task task : dateTimeList) {
            taskList.add(task);
        }
    }

    public AbstractTaskList getTaskList() {
        return taskList;
    }

    public static void main(String[] args) {
        new CreatingTableTask();
    }
}
