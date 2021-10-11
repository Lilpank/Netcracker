package GUI.WindowException;

import javax.swing.*;

public class WindowException {
    public WindowException(ExceptionPanel exception){
        JOptionPane.showMessageDialog(JOptionPane.getRootFrame(),
                exception.getMessage(),
                "Ошибка :'(",
                JOptionPane.WARNING_MESSAGE
        );
    }
}
