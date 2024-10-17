package cn.com.wysha.tool;

import java.util.ArrayList;

public class Tool {
    public static String stringToCmd(String s) {
        while (true){
            if (s.startsWith(" ")){
                s=s.substring(1);
            }else {
                break;
            }
        }
        while (true){
            if (s.endsWith(" ")){
                s=s.substring(0, s.length()-1);
            }else {
                break;
            }
        }
        if (s.contains(";")){
            s=s.substring(0, s.indexOf(";"));
        }
        while (true){
            if (s.endsWith(" ")){
                s=s.substring(0, s.length()-1);
            }else {
                break;
            }
        }
        return s;
    }
    public static String[] stringToCmdList(String s) {
        String[] strings=s.split("\\r?\\n");
        ArrayList<String> list=new ArrayList<>();
        for (String string : strings) {
            if (!string.isEmpty()) {
                list.add(stringToCmd(string));
            }
        }
        return list.toArray(new String[0]);
    }
}
