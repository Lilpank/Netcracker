package GUI;

import Function.AbstractTaskList;
import Function.LinkedTaskList;
import GUI.AbstractModel.AbstractTableModelForWindowTask;
import GUI.WindowException.ExceptionPanel;
import GUI.WindowException.WindowException;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.*;

public class WindowTask extends JDialog {
    private final JButton JButtonAddTasks = new JButton("Добавить задачи");
    private final JButton JButtonSave = new JButton("Сохранить");
    private final JButton JButtonDownload = new JButton("Загрузить");
    private AbstractTaskList abstractTaskList = new LinkedTaskList();

    private final AbstractTableModelForWindowTask tableModel = new AbstractTableModelForWindowTask(abstractTaskList);
    private final JTable firstTableXY1 = new JTable(tableModel);
    private final JFileChooser jFileChooser = new JFileChooser();

    public WindowTask() {
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Text files", "txt");
        jFileChooser.setFileFilter(filter);
        //Настройки окна
        getContentPane().setLayout(new GridLayout());
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setMaximumSize(new Dimension(1000, MainFrame.HEIGHT));
        setMinimumSize(new Dimension(1000, MainFrame.HEIGHT));
        compose();
        setLocationRelativeTo(null);
        setVisible(false);
        setModal(true);

        JButtonAddTasks.addActionListener(args -> {
            try {
                CreatingTableTask temp = new CreatingTableTask();

                abstractTaskList = temp.getTaskList();
                tableModel.setTasks(abstractTaskList);
                tableModel.fireTableDataChanged();
            } catch (UnsupportedOperationException | NullPointerException e) {
                System.out.println();
            }
        });


        JButtonDownload.addActionListener(args -> {
            jFileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
            jFileChooser.showOpenDialog(this);
            File file = jFileChooser.getSelectedFile();
            try {
                Function.TaskIO.readText(abstractTaskList, file);
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (abstractTaskList != null) {
                for (int i = 0; i < abstractTaskList.size(); i++) {
                    tableModel.setTasks(abstractTaskList);
                    tableModel.fireTableDataChanged();
                }
            }
        });

        JButtonSave.addActionListener(args -> {
            if (abstractTaskList == null) {
                new WindowException(new ExceptionPanel(new NullPointerException("Введите значения!")));
            } else {
                jFileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                JTextFileName jTextFileName = new JTextFileName();
                jTextFileName.setModalityType(ModalityType.APPLICATION_MODAL);
                jTextFileName.setVisible(true);
                if (!jTextFileName.getFileName().isEmpty()) {
                    jFileChooser.showSaveDialog(this);
                    File file = jFileChooser.getSelectedFile();
                    if (abstractTaskList != null) {
                        if (file != null) {
                            Function.TaskIO.writeText(abstractTaskList, new File(file + "\\" + jTextFileName.getFileName() + ".txt"));
                        }
                    }
                }
            }
        });
    }

    private void compose() {
        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);
        JScrollPane tableScrollPaneLeft = new JScrollPane(firstTableXY1);

        layout.setHorizontalGroup(
                layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addGroup(layout.createSequentialGroup()
                                        .addComponent(JButtonAddTasks)
                                        .addComponent(JButtonDownload)
                                        .addComponent(JButtonSave)
                                )
                                .addGroup(layout.createSequentialGroup()
                                        .addComponent(tableScrollPaneLeft)
                                )
                        )
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addGroup(layout.createSequentialGroup()
                                )
                                .addGroup(layout.createSequentialGroup()
                                )));

        layout.setVerticalGroup(
                layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(JButtonAddTasks)
                                .addComponent(JButtonDownload)
                                .addComponent(JButtonSave)
                        )
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addComponent(tableScrollPaneLeft)
                                .addGroup(layout.createSequentialGroup()
                                )
                        )
        );
    }

    public static void main(String[] args) {
        new WindowTask();
    }
}