package cn.com.wysha.core;

public class CmdList extends LabelList{
    private boolean addGlobal;

    public boolean isAddGlobal() {
        return addGlobal;
    }

    public void setAddGlobal(boolean addGlobal) {
        this.addGlobal = addGlobal;
    }

    private final Core core;
    private String name;
    private EndMode endMode;
    private String endCmdListName;
    private String[] cmdList=new String[0];

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public EndMode getEndMode() {
        return endMode;
    }

    public void setEndMode(EndMode endMode) {
        this.endMode = endMode;
    }

    public String getCmdList() {
        StringBuilder v= new StringBuilder();
        for (String line : cmdList) {
            v.append(line).append("\r\n");
        }
        return v.toString();
    }

    public void setCmdList(String cmdList) {
        this.cmdList= cmdList.split("\\r?\\n");
    }

    public String getEndCmdListName() {
        return endCmdListName;
    }

    public void setEndCmdListName(String endCmdListName) {
        this.endCmdListName = endCmdListName;
    }

    public CmdList(Core core, String name, String cmdList, EndMode endMode, String endCmdListName) {
        this.core = core;
        setName(name);
        setCmdList(cmdList);
        setEndMode(endMode);
        setEndCmdListName(endCmdListName.toUpperCase());
    }

    public void runCmdList(){
        for (int i = 0; i < cmdList.length; i++) {
            String cmd = cmdList[i];
            if (!cmd.isEmpty()){
                if (!core.runCmd(cmd)){
                    System.out.println("End : cmdList = " + this +" , index = " + i +" , cmd = "+cmd);
                    break;
                }
            }
        }
        switch (endMode){
            case EndMode.STOP->{
            }
            case EndMode.RUN-> {
                CmdList cmdList=core.findCmdList(getEndCmdListName());
                if (cmdList.isAddGlobal()){
                    setLabelListString(cmdList.getLabelListString());
                }else {
                    setLabelListString(this.getLabelListString()+"\r\n"+cmdList.getLabelListString());
                }
                cmdList.runCmdList();
            }
        }
    }

    @Override
    public String toString() {
        return "{ Name = " + name + " , Mode = " + endMode.toString()+" }";
    }
}
