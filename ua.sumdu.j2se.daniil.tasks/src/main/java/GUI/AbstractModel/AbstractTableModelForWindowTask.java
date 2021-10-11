package GUI.AbstractModel;

import Function.AbstractTaskList;

import javax.swing.table.AbstractTableModel;
import java.time.LocalDateTime;

public class AbstractTableModelForWindowTask extends AbstractTableModel {
    private static final int INDEX_COLUMN_NUMBER = 0;
    private static final int Title_COLUMN = 1;
    private static final int Start_COLUMN = 2;
    private static final long serialVersionUID = 8913282639235346347L;
    private AbstractTaskList tasks;

    public AbstractTableModelForWindowTask(AbstractTaskList function) {
        this.tasks = function;
    }

    @Override
    public int getRowCount() {
        return (tasks == null) ? 0 : tasks.size();
    }

    @Override
    public int getColumnCount() {
        return 3;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        switch (columnIndex) {
            case INDEX_COLUMN_NUMBER:
                return rowIndex;
            case Title_COLUMN:
                return tasks.getTask(rowIndex).getTitle();
            case Start_COLUMN:
                return tasks.getTask(rowIndex).getStartTime();
        }
        throw new UnsupportedOperationException();
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) throws NumberFormatException {
        if (columnIndex == Title_COLUMN) {
            tasks.getTask(rowIndex).setTitle(aValue.toString());

        } else if (columnIndex == Start_COLUMN) {
            tasks.getTask(rowIndex).setTime(LocalDateTime.parse(aValue.toString()));
        }
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        switch (columnIndex) {
            case INDEX_COLUMN_NUMBER:
            case Title_COLUMN:
            case Start_COLUMN:
                return false;
        }
        return false;
    }

    @Override
    public String getColumnName(int column) {
        switch (column) {
            case INDEX_COLUMN_NUMBER:
                return "Индекс";
            case Title_COLUMN:
                return "Название";
            case Start_COLUMN:
                return "Начальное время";
        }
        return super.getColumnName(column);
    }

    public AbstractTaskList getTasks() {
        return tasks;
    }

    public void setTasks(AbstractTaskList tasks) {
        this.tasks = tasks;
    }
}

