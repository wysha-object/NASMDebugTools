package cn.com.wysha.GUI;

import cn.com.wysha.core.CmdList;
import cn.com.wysha.core.EndMode;

import javax.swing.*;
import java.awt.*;

import static cn.com.wysha.core.Core.core;

public class AllCmdListGUI extends JDialog {
    private JPanel contentPane;
    private JList<CmdList> list;
    private JButton editButton;
    private JButton delButton;
    private JButton newButton;

    public AllCmdListGUI() {
        setContentPane(contentPane);
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        setSize(dimension.width / 2, dimension.height / 2);
        setLocationRelativeTo(null);
        setModal(true);

        newButton.addActionListener(_ -> {
            CmdList cmdList=new CmdList(core, "", "", EndMode.STOP, "");
            new CmdListEditGUI(cmdList).setVisible(true);
            core.addCmdList(cmdList);
            refresh();
        });

        editButton.addActionListener(_ -> {
            CmdList cmdList = list.getSelectedValue();
            if (cmdList != null) {
                new CmdListEditGUI(cmdList).setVisible(true);
                refresh();
            }
        });

        delButton.addActionListener(_ -> {
            CmdList cmdList = list.getSelectedValue();
            if (cmdList != null) {
                core.removeCmdList(cmdList);
                list.setListData(core.allCmdList());
            }
        });

        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        refresh();
    }
    private void refresh(){
        list.setListData(core.allCmdList());
    }
}
