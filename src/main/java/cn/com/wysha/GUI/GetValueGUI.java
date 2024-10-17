package cn.com.wysha.GUI;

import javax.swing.*;
import java.awt.*;

public class GetValueGUI extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JSpinner spinner1;
    public int value=1024;

    public GetValueGUI() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        setAlwaysOnTop(true);
        setUndecorated(true);

        Dimension dimension=Toolkit.getDefaultToolkit().getScreenSize();
        setSize(dimension.width/2,dimension.height/2);
        setLocationRelativeTo(null);

        spinner1.setValue(value);

        buttonOK.addActionListener(_ -> onOK());
    }

    private void onOK() {
        value= (int) spinner1.getValue();
        dispose();
    }
}
