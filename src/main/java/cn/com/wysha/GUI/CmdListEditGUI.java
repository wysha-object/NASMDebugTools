package cn.com.wysha.GUI;

import cn.com.wysha.core.CmdList;
import cn.com.wysha.core.EndMode;

import javax.swing.*;
import java.awt.*;

public class CmdListEditGUI extends JDialog {
    private JPanel contentPane;
    private JTextArea textArea;
    private JButton buttonOK;
    private JTextField textFieldName;
    private JButton editLocalLabelButton;
    private JCheckBox addGlobalCheckBox;
    private JTextField endCmdListNameTextField;
    private JComboBox<EndMode> endModeComboBox;
    private JButton cancelButton;
    private final CmdList cmdList;

    public CmdListEditGUI(CmdList cmdList) {
        setContentPane(contentPane);
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        setSize(dimension.width / 2, dimension.height / 2);
        setLocationRelativeTo(null);
        setModal(true);

        this.cmdList = cmdList;

        buttonOK.addActionListener(_ -> onOK());
        cancelButton.addActionListener(_ -> dispose());
        editLocalLabelButton.addActionListener(_ -> new EditLabelListGUI(cmdList));
        endModeComboBox.addItem(EndMode.STOP);
        endModeComboBox.addItem(EndMode.RUN);

        textFieldName.setText(cmdList.getName());
        textArea.setText(cmdList.getCmdList());
        endModeComboBox.setSelectedItem(cmdList.getEndMode());
        endCmdListNameTextField.setText(cmdList.getEndCmdListName());

        addGlobalCheckBox.setSelected(cmdList.isAddGlobal());
    }

    private void onOK() {
        dispose();
        cmdList.setName(textFieldName.getText().toUpperCase());
        cmdList.setCmdList(textArea.getText());
        cmdList.setAddGlobal(addGlobalCheckBox.isSelected());
        cmdList.setEndMode((EndMode) endModeComboBox.getSelectedItem());
        cmdList.setEndCmdListName(endCmdListNameTextField.getText().toUpperCase());
    }
}
