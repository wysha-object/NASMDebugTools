package cn.com.wysha.GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GetValue extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JSpinner spinner1;
    public int value=4096;

    public GetValue() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        Dimension dimension=Toolkit.getDefaultToolkit().getScreenSize();
        setSize(dimension.width/2,dimension.height/2);
        setLocationRelativeTo(null);

        spinner1.setValue(value);

        buttonOK.addActionListener(e -> onOK());
    }

    private void onOK() {
        value= (int) spinner1.getValue();
        dispose();
    }

    public static void main(String[] args) {
        GetValue dialog = new GetValue();
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }
}
