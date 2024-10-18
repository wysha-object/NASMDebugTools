package cn.com.wysha.core;

import cn.com.wysha.tool.Tool;

import java.math.BigInteger;
import java.util.HashMap;

public abstract class LabelList {
    private String labelString;
    private HashMap<String, BigInteger> labelMap=new HashMap<>();

    public String getLabelListString(){
        return labelString;
    }

    public void setLabelListString(String string){
        labelMap=new HashMap<>();
        labelString = string;
        String[] rs = Tool.stringToCmdList(string);
        for (String s : rs) {
            String[] ss = s.split(" ");
            if (ss.length > 1) {
                labelMap.put(ss[0].toUpperCase(), new BigInteger(ss[1], 16));
            }
        }
    }

    public BigInteger findValue(String label){
        return labelMap.get(label);
    }
}
