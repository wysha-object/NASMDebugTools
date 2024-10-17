package cn.com.wysha.GUI;

import cn.com.wysha.core.LabelList;

import javax.swing.*;
import java.awt.*;

public class EditLabelListGUI extends JDialog{
    private JPanel contentPane;
    private JTextArea textArea;
    private JButton buttonOK;
    private JButton cancelButton;
    final LabelList labelList;
    public EditLabelListGUI(LabelList labelList){
        this.labelList = labelList;
        setContentPane(contentPane);
        Dimension dimension=Toolkit.getDefaultToolkit().getScreenSize();
        setSize(dimension.width/2,dimension.height/2);
        setLocationRelativeTo(null);
        setModal(true);
        
        buttonOK.addActionListener(_ -> onOK());
        cancelButton.addActionListener(_ -> dispose());

        textArea.setText(labelList.getLabelListString());
    }

    public void onOK(){
        dispose();
        labelList.setLabelListString(textArea.getText());
    }
}
