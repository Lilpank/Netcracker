package GUI.AbstractModel;

import Function.Task;

import javax.swing.table.AbstractTableModel;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class AbstractModelForTask extends AbstractTableModel {
    private static final int INDEX_COLUMN_NUMBER = 0;
    private static final int Title_COLUMN = 1;
    private static final int Start_COLUMN = 2;
    private static final long serialVersionUID = 8913282639235346347L;
    private final List<Task> tasks;

    public AbstractModelForTask(List<Task> task) {
        this.tasks = task;
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
                return tasks.get(rowIndex).getTitle();
            case Start_COLUMN:
                return tasks.get(rowIndex).getStartTime();
        }
        throw new UnsupportedOperationException();
    }

    /**
     * @param aValue      значение в ячейке
     * @param rowIndex    - Индекс строки
     * @param columnIndex - индекс столбца
     * @throws NumberFormatException
     */
    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        if (columnIndex == Title_COLUMN) {
            tasks.get(rowIndex).setTitle(aValue.toString());

        } else if (columnIndex == Start_COLUMN) {
            try {
                tasks.get(rowIndex).setTime(LocalDateTime.parse(aValue.toString()));
            } catch (Exception e) {
                tasks.get(rowIndex).setTime(LocalDateTime.now());
            }
        }
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        switch (columnIndex) {
            case INDEX_COLUMN_NUMBER:
            case Title_COLUMN:
            case Start_COLUMN:
                return true;
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

    public void clear() {
        tasks.clear();
    }
}
