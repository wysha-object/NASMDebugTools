package cn.com.wysha.GUI;

import cn.com.wysha.core.CmdList;
import cn.com.wysha.core.Core;
import cn.com.wysha.core.EndMode;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.util.ArrayList;

import static cn.com.wysha.core.Core.core;

public class MainGUI extends JFrame {
    public static final MainGUI mainGUI=new MainGUI();

    private JPanel contentPane;
    private JList<String> infoList;
    private JList<String> dataList;
    private JList<Integer> indexList;
    private JScrollPane indexScrollPane;
    private JScrollPane dataScrollPane;
    private JScrollBar scrollBar;
    private JButton OKButton;
    private JTextArea textArea;
    private JTable table;
    private JTextField startIndex;
    private JButton tableRefreshButton;
    private JPanel header;
    private JButton editGlobalLabelButton;
    private JButton editAllCmdListButton;

    private final ArrayList<String> dataArrayList=new ArrayList<>();

    public void addData(String data){
        dataArrayList.add(data);
        refresh();
        revalidate();
        scrollBar.setValue(scrollBar.getMaximum());
    }

    private final String[] columnNames={"0","1","2","3","4","5","6","7","8","9","a","b","c","d","e","f"};
    private final String[][] dataTable=new String[16][17];

    int start=0;
    private MainGUI() {
        setContentPane(contentPane);
        Dimension dimension=Toolkit.getDefaultToolkit().getScreenSize();
        setSize(dimension.width/2,dimension.height/2);
        setLocationRelativeTo(null);

        GetValueGUI getValueGUI =new GetValueGUI();
        getValueGUI.setVisible(true);
        new Core(getValueGUI.value);

        header.add(table.getTableHeader());
        startIndex.setText("0");

        OKButton.addActionListener(_ -> new CmdList(core, "Main", textArea.getText(), EndMode.STOP, "").runCmdList());

        tableRefreshButton.addActionListener(_ -> {
            start=Integer.valueOf(startIndex.getText(),16);
            refresh();
        });

        editAllCmdListButton.addActionListener(_ -> new AllCmdListGUI().setVisible(true));

        editGlobalLabelButton.addActionListener(_ -> new EditLabelListGUI(core).setVisible(true));

        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < 17; j++) {
                if (j==0){
                    dataTable[i][j]=Integer.toString(start+i*16,16);
                }else {
                    dataTable[i][j]=core.getMem(start+i*16+j-1,1).toString(16);
                }
            }
        }

        TableModel tableModel=new DefaultTableModel(dataTable,columnNames);
        table.setModel(tableModel);
        refresh();
    }
    public void refresh(){
        String[] infoData=new String[21];
        infoData[0]="AX="+Integer.toString(core.getAX().intValue(),16);
        infoData[1]="BX="+Integer.toString(core.getBX().intValue(),16);
        infoData[2]="CX="+Integer.toString(core.getCX().intValue(),16);
        infoData[3]="DX="+Integer.toString(core.getDX().intValue(),16);
        infoData[4]="SI="+Integer.toString(core.getSI().intValue(),16);
        infoData[5]="DI="+Integer.toString(core.getDI().intValue(),16);
        infoData[6]="BP="+Integer.toString(core.getBP().intValue(),16);
        infoData[7]="SP="+Integer.toString(core.getSP().intValue(),16);
        infoData[8]="CS="+Integer.toString(core.getCS().intValue(),16);
        infoData[9]="DS="+Integer.toString(core.getDS().intValue(),16);
        infoData[10]="ES="+Integer.toString(core.getES().intValue(),16);
        infoData[11]="SS="+Integer.toString(core.getSS().intValue(),16);
        infoData[12]="CF="+core.isCF();
        infoData[13]="PF="+core.isPF();
        infoData[14]="AF="+core.isAF();
        infoData[15]="ZF="+core.isZF();
        infoData[16]="SF="+core.isSF();
        infoData[17]="TF="+core.isTF();
        infoData[18]="IF="+core.isIF();
        infoData[19]="DF="+core.isDF();
        infoData[20]="OF="+core.isOF();
        infoList.setListData(infoData);

        Integer[] integers=new Integer[dataArrayList.size()];
        for (int i=0;i<dataArrayList.size();i++){
            integers[i]=i;
        }
        indexList.setListData(integers);
        dataList.setListData(dataArrayList.toArray(new String[0]));

        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < 17; j++) {
                if (j==0){
                    dataTable[i][j]=Integer.toString(start+i*16,16);
                }else {
                    dataTable[i][j]=core.getMem(start+i*16+j-1,1).toString(16);
                }
            }
        }

        TableModel tableModel=new DefaultTableModel(dataTable,columnNames);
        table.setModel(tableModel);
    }
    private void createUIComponents() {
        // TODO: place custom component creation code here
        scrollBar=new JScrollBar();
        indexScrollPane=new JScrollPane(indexList);
        dataScrollPane=new JScrollPane(dataList);
        indexScrollPane.setVerticalScrollBar(scrollBar);
        dataScrollPane.setVerticalScrollBar(scrollBar);
    }
}
