package cn.com.wysha.GUI;

import cn.com.wysha.core.Core;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.util.ArrayList;

public class MainGUI extends JFrame {
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
    private final ArrayList<String> dataArrayList=new ArrayList<>();
    private final Core core;
    private final String[] columnNames={"0","1","2","3","4","5","6","7","8","9","a","b","c","d","e","f"};
    private final String[][] dataTable=new String[16][17];

    int start=0;

    public MainGUI() {
        setContentPane(contentPane);
        Dimension dimension=Toolkit.getDefaultToolkit().getScreenSize();
        setSize(dimension.width/2,dimension.height/2);
        setLocationRelativeTo(null);

        GetValue getValue=new GetValue();
        getValue.setVisible(true);
        core=new Core(getValue.value);

        header.add(table.getTableHeader());
        startIndex.setText("0");

        OKButton.addActionListener(_ -> {
            String[] lines = textArea.getText().split("\\r?\\n");
            for (String line : lines) {
                if (core.run(line.toUpperCase())){
                    dataArrayList.add(line);
                }
            }
        });

        tableRefreshButton.addActionListener(_ -> {
            start=Integer.valueOf(startIndex.getText(),16);
            for (int i = 0; i < 16; i++) {
                for (int j = 0; j < 17; j++) {
                    if (j==0){
                        dataTable[i][j]=Integer.toString(start+i*16,16);
                    }else {
                        dataTable[i][j]=Integer.toString(core.getMemByte(start+i*16+j-1),16);
                    }
                }
            }

            TableModel tableModel=new DefaultTableModel(dataTable,columnNames);
            table.setModel(tableModel);
        });

        new Thread(() -> {
            while (true){
                refresh();
            }
        }).start();

        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < 17; j++) {
                if (j==0){
                    dataTable[i][j]=Integer.toString(start+i*16,16);
                }else {
                    dataTable[i][j]=Integer.toString(core.getMemByte(start+i*16+j-1),16);
                }
            }
        }

        TableModel tableModel=new DefaultTableModel(dataTable,columnNames);
        table.setModel(tableModel);
    }
    public void refresh(){
        String[] infoData=new String[21];
        infoData[0]="AX:"+Integer.toString(core.getAX(),16);
        infoData[1]="BX:"+Integer.toString(core.getBX(),16);
        infoData[2]="CX:"+Integer.toString(core.getCX(),16);
        infoData[3]="DX:"+Integer.toString(core.getDX(),16);
        infoData[4]="SI:"+Integer.toString(core.getSI(),16);
        infoData[5]="DI:"+Integer.toString(core.getDI(),16);
        infoData[6]="BP:"+Integer.toString(core.getBP(),16);
        infoData[7]="SP:"+Integer.toString(core.getSP(),16);
        infoData[8]="CS:"+Integer.toString(core.getCS(),16);
        infoData[9]="DS:"+Integer.toString(core.getDS(),16);
        infoData[10]="ES:"+Integer.toString(core.getES(),16);
        infoData[11]="SS:"+Integer.toString(core.getSS(),16);
        infoData[12]="CF:"+core.isCF();
        infoData[13]="PF:"+core.isPF();
        infoData[14]="AF:"+core.isAF();
        infoData[15]="ZF:"+core.isZF();
        infoData[16]="SF:"+core.isSF();
        infoData[17]="TF:"+core.isTF();
        infoData[18]="IF:"+core.isIF();
        infoData[19]="DF:"+core.isDF();
        infoData[20]="OF:"+core.isOF();
        infoList.setListData(infoData);

        Integer[] integers=new Integer[dataArrayList.size()];
        for (int i=0;i<dataArrayList.size();i++){
            integers[i]=i;
        }
        indexList.setListData(integers);
        dataList.setListData(dataArrayList.toArray(new String[0]));
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
