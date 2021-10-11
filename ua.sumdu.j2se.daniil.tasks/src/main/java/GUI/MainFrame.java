package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

public class MainFrame extends JFrame implements ActionListener {
    public static final int HEIGHT = 640;
    public static final int WIDTH = HEIGHT / 12 * 9;
    private final JButton jButtonTask = new JButton("Окно задач");
    private final JButton jMenuCalendar = new JButton("Календарь");


    public void backgroundImage() {
        try {
            final Image backgroundImage = javax.imageio.ImageIO.read(new File("Icon.jpg"));
            setContentPane(new JPanel(new BorderLayout()) {
                @Override
                public void paintComponent(Graphics g) {
                    g.drawImage(backgroundImage, 0, 40, null);
                }
            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public MainFrame() {
        super("Главное окно");
        //Масштаб окна
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setMaximumSize(new Dimension(WIDTH, HEIGHT));
        setMinimumSize(new Dimension(WIDTH, HEIGHT));
        //рисует картинку
        backgroundImage();

        //Компановка кнопок
        compose();

        jMenuCalendar.addActionListener(this);
        jButtonTask.addActionListener(this);

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void compose() {
        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                .addGroup(layout.createSequentialGroup()
                        .addComponent(jButtonTask)
                        .addComponent(jMenuCalendar)
                )
        );

        layout.setVerticalGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(jButtonTask)
                        .addComponent(jMenuCalendar)
                )
        );
    }

    public static void main(String[] args) {
        new MainFrame();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == jButtonTask) {
            WindowTask windowTask = new WindowTask();
            windowTask.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
            windowTask.setVisible(true);
        }
    }
}
