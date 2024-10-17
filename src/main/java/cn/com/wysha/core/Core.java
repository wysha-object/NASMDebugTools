package cn.com.wysha.core;

import cn.com.wysha.GUI.MainGUI;
import cn.com.wysha.tool.Tool;

import java.math.BigInteger;
import java.util.HashSet;

public class Core extends LabelList {
    private final HashSet<CmdList> cmdListArrayList = new HashSet<>();
    public static Core core;

    public CmdList[] allCmdList() {
        return cmdListArrayList.toArray(new CmdList[0]);
    }

    public void addCmdList(CmdList cmdList) {
        cmdListArrayList.add(cmdList);
    }

    public void removeCmdList(CmdList cmdList) {
        cmdListArrayList.remove(cmdList);
    }

    public CmdList findCmdList(String cmdListName) {
        for (CmdList cmdList : cmdListArrayList) {
            if (cmdList.getName().equals(cmdListName)) {
                return cmdList;
            }
        }
        return null;
    }


    Register ah = new Register();
    Register al = new Register();
    Register bh = new Register();
    Register bl = new Register();
    Register ch = new Register();
    Register cl = new Register();
    Register dh = new Register();
    Register dl = new Register();

    public short getAH() {
        return ah.get().shortValue();
    }

    public short getAL() {
        return al.get().shortValue();
    }

    public short getBH() {
        return bh.get().shortValue();
    }

    public short getBL() {
        return bl.get().shortValue();
    }

    public short getCH() {
        return ch.get().shortValue();
    }

    public short getCL() {
        return cl.get().shortValue();
    }

    public short getDH() {
        return dh.get().shortValue();
    }

    public short getDL() {
        return dl.get().shortValue();
    }

    public void setAH(short value) {
        ah.set(BigInteger.valueOf(value));
    }

    public void setAL(short value) {
        al.set(BigInteger.valueOf(value));
    }

    public void setBH(short value) {
        bh.set(BigInteger.valueOf(value));
    }

    public void setBL(short value) {
        bl.set(BigInteger.valueOf(value));
    }

    public void setCH(short value) {
        ch.set(BigInteger.valueOf(value));
    }

    public void setCL(short value) {
        cl.set(BigInteger.valueOf(value));
    }

    public void setDH(short value) {
        dh.set(BigInteger.valueOf(value));
    }

    public void setDL(short value) {
        dl.set(BigInteger.valueOf(value));
    }

    public int hlToWord(short highByte, short lowByte) {
        return (highByte << 8) | lowByte;
    }

    public int getAX() {
        return hlToWord(getAH(), getAL());
    }

    public int getBX() {
        return hlToWord(getBH(), getBL());
    }

    public int getCX() {
        return hlToWord(getCH(), getCL());
    }

    public int getDX() {
        return hlToWord(getDH(), getDL());
    }

    private void setHL(Register h, Register l, int value) {
        h.set(BigInteger.valueOf((value >>> 8) & 0xff));
        l.set(BigInteger.valueOf(value & 0xff));
    }

    private void setAX(int value) {
        setHL(ah, al, value);
    }

    private void setBX(int value) {
        setHL(bh, bl, value);
    }

    private void setCX(int value) {
        setHL(ch, cl, value);
    }

    private void setDX(int value) {
        setHL(dh, dl, value);
    }

    Register si = new Register();
    Register di = new Register();
    Register bp = new Register();
    Register sp = new Register();

    Register cs = new Register();
    Register ds = new Register();
    Register es = new Register();
    Register ss = new Register();

    public int getSI() {
        return si.get().intValue();
    }

    public int getDI() {
        return di.get().intValue();
    }

    public int getBP() {
        return bp.get().intValue();
    }

    public int getSP() {
        return sp.get().intValue();
    }

    public int getCS() {
        return cs.get().intValue();
    }

    public int getDS() {
        return ds.get().intValue();
    }

    public int getES() {
        return es.get().intValue();
    }

    public int getSS() {
        return ss.get().intValue();
    }

    private void setSI(int value) {
        si.set(BigInteger.valueOf(value));
    }

    private void setDI(int value) {
        di.set(BigInteger.valueOf(value));
    }

    private void setBP(int value) {
        bp.set(BigInteger.valueOf(value));
    }

    private void setSP(int value) {
        sp.set(BigInteger.valueOf(value));
    }

    private void setCS(int value) {
        cs.set(BigInteger.valueOf(value));
    }

    private void setDS(int value) {
        ds.set(BigInteger.valueOf(value));
    }

    private void setES(int value) {
        es.set(BigInteger.valueOf(value));
    }

    private void setSS(int value) {
        ss.set(BigInteger.valueOf(value));
    }

    boolean CF;
    boolean PF;
    boolean AF;
    boolean ZF;
    boolean SF;
    boolean TF;
    boolean IF;
    boolean DF;
    boolean OF;

    private final short[] memory;

    public boolean isCF() {
        return CF;
    }

    public boolean isPF() {
        return PF;
    }

    public boolean isAF() {
        return AF;
    }

    public boolean isZF() {
        return ZF;
    }

    public boolean isSF() {
        return SF;
    }

    public boolean isTF() {
        return TF;
    }

    public boolean isIF() {
        return IF;
    }

    public boolean isDF() {
        return DF;
    }

    public boolean isOF() {
        return OF;
    }

    public short getMemByte(int index) {
        return memory[index];
    }

    public int getMemWord(int index) {
        int rs = getMemByte(index);
        rs += getMemByte(index + 1) << 8;
        return rs;
    }

    public void setMemByte(int index, short s) {
        memory[index] = s;
    }

    public void setMemWord(int index, int s) {
        setMemByte(index, (short) (s & 0xff));
        setMemByte(index + 1, (short) ((s >>> 8) & 0xff));
    }

    public Core(int memorySizeKB) {
        memory = new short[memorySizeKB * 1024];
        core=this;
    }

    public boolean runCmd(String cmd) {
        MainGUI.mainGUI.addData(cmd);
        String[] strings = Tool.stringToCmd(cmd.toUpperCase()).split(" ");
        try {
            switch (strings[0]) {
                case "ADD" -> {
                    switch (strings[1]) {
                        case "BYTE" -> {
                            String[] sss = strings[2].split(",");
                            int index = stringToMemIndex(sss[0]);
                            setMemByte(stringToMemIndex(sss[0]), addByte(getMemByte(index), stringToByte(sss[1])));
                        }
                        case "WORD" -> {
                            String[] sss = strings[2].split(",");
                            int index = stringToMemIndex(sss[0]);
                            setMemWord(stringToMemIndex(sss[0]), addWord(getMemWord(index), stringToWord(sss[1])));
                        }
                        default -> {
                            String[] sss = strings[1].split(",");
                            switch (sss[0]) {
                                case "AH" -> setAH(addByte(getAH(), stringToByte(sss[1])));
                                case "AL" -> setAL(addByte(getAL(), stringToByte(sss[1])));
                                case "BH" -> setBH(addByte(getBH(), stringToByte(sss[1])));
                                case "BL" -> setBL(addByte(getBL(), stringToByte(sss[1])));
                                case "CH" -> setCH(addByte(getCH(), stringToByte(sss[1])));
                                case "CL" -> setCL(addByte(getCL(), stringToByte(sss[1])));
                                case "DH" -> setDH(addByte(getDH(), stringToByte(sss[1])));
                                case "DL" -> setDL(addByte(getDL(), stringToByte(sss[1])));
                                case "AX" -> setAX(addWord(getAX(), stringToWord(sss[1])));
                                case "BX" -> setBX(addWord(getBX(), stringToWord(sss[1])));
                                case "CX" -> setCX(addWord(getCX(), stringToWord(sss[1])));
                                case "DX" -> setDX(addWord(getDX(), stringToWord(sss[1])));
                                case "SI" -> setSI(addWord(getSI(), stringToWord(sss[1])));
                                case "DI" -> setDI(addWord(getDI(), stringToWord(sss[1])));
                                case "BP" -> setBP(addWord(getBP(), stringToWord(sss[1])));
                                case "SP" -> setSP(addWord(getSP(), stringToWord(sss[1])));
                                case "CS" -> setCS(addWord(getCS(), stringToWord(sss[1])));
                                case "DS" -> setDS(addWord(getDS(), stringToWord(sss[1])));
                                case "ES" -> setES(addWord(getES(), stringToWord(sss[1])));
                                case "SS" -> setSS(addWord(getSS(), stringToWord(sss[1])));
                                default -> {
                                    int index = stringToMemIndex(sss[0]);
                                    switch (sss[1]) {
                                        case "AH", "AL", "BH", "BL", "CH", "CL", "DH", "DL" ->
                                                setMemByte(index, addByte(getMemByte(index), stringToByte(sss[1])));
                                        case "AX", "BX", "CX", "DX", "SI", "DI", "BP", "SP", "CS", "DS", "ES", "SS" ->
                                                setMemWord(stringToMemIndex(sss[0]), addWord(getMemWord(index), stringToWord(sss[1])));
                                        default -> throw new RuntimeException("指令他妈不存在,你在他妈的扯淡");
                                    }
                                }
                            }
                        }
                    }
                }
                case "SUB" -> {
                    switch (strings[1]) {
                        case "BYTE" -> {
                            String[] sss = strings[2].split(",");
                            int index = stringToMemIndex(sss[0]);
                            setMemByte(stringToMemIndex(sss[0]), subByte(getMemByte(index), stringToByte(sss[1])));
                        }
                        case "WORD" -> {
                            String[] sss = strings[2].split(",");
                            int index = stringToMemIndex(sss[0]);
                            setMemWord(stringToMemIndex(sss[0]), subWord(getMemWord(index), stringToWord(sss[1])));
                        }
                        default -> {
                            String[] sss = strings[1].split(",");
                            switch (sss[0]) {
                                case "AH" -> setAH(subByte(getAH(), stringToByte(sss[1])));
                                case "AL" -> setAL(subByte(getAL(), stringToByte(sss[1])));
                                case "BH" -> setBH(subByte(getBH(), stringToByte(sss[1])));
                                case "BL" -> setBL(subByte(getBL(), stringToByte(sss[1])));
                                case "CH" -> setCH(subByte(getCH(), stringToByte(sss[1])));
                                case "CL" -> setCL(subByte(getCL(), stringToByte(sss[1])));
                                case "DH" -> setDH(subByte(getDH(), stringToByte(sss[1])));
                                case "DL" -> setDL(subByte(getDL(), stringToByte(sss[1])));
                                case "AX" -> setAX(subWord(getAX(), stringToWord(sss[1])));
                                case "BX" -> setBX(subWord(getBX(), stringToWord(sss[1])));
                                case "CX" -> setCX(subWord(getCX(), stringToWord(sss[1])));
                                case "DX" -> setDX(subWord(getDX(), stringToWord(sss[1])));
                                case "SI" -> setSI(subWord(getSI(), stringToWord(sss[1])));
                                case "DI" -> setDI(subWord(getDI(), stringToWord(sss[1])));
                                case "BP" -> setBP(subWord(getBP(), stringToWord(sss[1])));
                                case "SP" -> setSP(subWord(getSP(), stringToWord(sss[1])));
                                case "CS" -> setCS(subWord(getCS(), stringToWord(sss[1])));
                                case "DS" -> setDS(subWord(getDS(), stringToWord(sss[1])));
                                case "ES" -> setES(subWord(getES(), stringToWord(sss[1])));
                                case "SS" -> setSS(subWord(getSS(), stringToWord(sss[1])));
                                default -> {
                                    int index = stringToMemIndex(sss[0]);
                                    switch (sss[1]) {
                                        case "AH", "AL", "BH", "BL", "CH", "CL", "DH", "DL" ->
                                                setMemByte(index, subByte(getMemByte(index), stringToByte(sss[1])));
                                        case "AX", "BX", "CX", "DX", "SI", "DI", "BP", "SP", "CS", "DS", "ES", "SS" ->
                                                setMemWord(stringToMemIndex(sss[0]), subWord(getMemWord(index), stringToWord(sss[1])));
                                        default -> throw new RuntimeException("指令他妈不存在,你在他妈的扯淡");
                                    }
                                }
                            }
                        }
                    }
                }
                case "AND" -> {
                    switch (strings[1]) {
                        case "BYTE" -> {
                            String[] sss = strings[2].split(",");
                            int index = stringToMemIndex(sss[0]);
                            setMemByte(stringToMemIndex(sss[0]), andByte(getMemByte(index), stringToByte(sss[1])));
                        }
                        case "WORD" -> {
                            String[] sss = strings[2].split(",");
                            int index = stringToMemIndex(sss[0]);
                            setMemWord(stringToMemIndex(sss[0]), andWord(getMemWord(index), stringToWord(sss[1])));
                        }
                        default -> {
                            String[] sss = strings[1].split(",");
                            switch (sss[0]) {
                                case "AH" -> setAH(andByte(getAH(), stringToByte(sss[1])));
                                case "AL" -> setAL(andByte(getAL(), stringToByte(sss[1])));
                                case "BH" -> setBH(andByte(getBH(), stringToByte(sss[1])));
                                case "BL" -> setBL(andByte(getBL(), stringToByte(sss[1])));
                                case "CH" -> setCH(andByte(getCH(), stringToByte(sss[1])));
                                case "CL" -> setCL(andByte(getCL(), stringToByte(sss[1])));
                                case "DH" -> setDH(andByte(getDH(), stringToByte(sss[1])));
                                case "DL" -> setDL(andByte(getDL(), stringToByte(sss[1])));
                                case "AX" -> setAX(andWord(getAX(), stringToWord(sss[1])));
                                case "BX" -> setBX(andWord(getBX(), stringToWord(sss[1])));
                                case "CX" -> setCX(andWord(getCX(), stringToWord(sss[1])));
                                case "DX" -> setDX(andWord(getDX(), stringToWord(sss[1])));
                                case "SI" -> setSI(andWord(getSI(), stringToWord(sss[1])));
                                case "DI" -> setDI(andWord(getDI(), stringToWord(sss[1])));
                                case "BP" -> setBP(andWord(getBP(), stringToWord(sss[1])));
                                case "SP" -> setSP(andWord(getSP(), stringToWord(sss[1])));
                                case "CS" -> setCS(andWord(getCS(), stringToWord(sss[1])));
                                case "DS" -> setDS(andWord(getDS(), stringToWord(sss[1])));
                                case "ES" -> setES(andWord(getES(), stringToWord(sss[1])));
                                case "SS" -> setSS(andWord(getSS(), stringToWord(sss[1])));
                                default -> {
                                    int index = stringToMemIndex(sss[0]);
                                    switch (sss[1]) {
                                        case "AH", "AL", "BH", "BL", "CH", "CL", "DH", "DL" ->
                                                setMemByte(index, andByte(getMemByte(index), stringToByte(sss[1])));
                                        case "AX", "BX", "CX", "DX", "SI", "DI", "BP", "SP", "CS", "DS", "ES", "SS" ->
                                                setMemWord(stringToMemIndex(sss[0]), andWord(getMemWord(index), stringToWord(sss[1])));
                                        default -> throw new RuntimeException("指令他妈不存在,你在他妈的扯淡");
                                    }
                                }
                            }
                        }
                    }
                }
                case "OR" -> {
                    switch (strings[1]) {
                        case "BYTE" -> {
                            String[] sss = strings[2].split(",");
                            int index = stringToMemIndex(sss[0]);
                            setMemByte(stringToMemIndex(sss[0]), orByte(getMemByte(index), stringToByte(sss[1])));
                        }
                        case "WORD" -> {
                            String[] sss = strings[2].split(",");
                            int index = stringToMemIndex(sss[0]);
                            setMemWord(stringToMemIndex(sss[0]), orWord(getMemWord(index), stringToWord(sss[1])));
                        }
                        default -> {
                            String[] sss = strings[1].split(",");
                            switch (sss[0]) {
                                case "AH" -> setAH(orByte(getAH(), stringToByte(sss[1])));
                                case "AL" -> setAL(orByte(getAL(), stringToByte(sss[1])));
                                case "BH" -> setBH(orByte(getBH(), stringToByte(sss[1])));
                                case "BL" -> setBL(orByte(getBL(), stringToByte(sss[1])));
                                case "CH" -> setCH(orByte(getCH(), stringToByte(sss[1])));
                                case "CL" -> setCL(orByte(getCL(), stringToByte(sss[1])));
                                case "DH" -> setDH(orByte(getDH(), stringToByte(sss[1])));
                                case "DL" -> setDL(orByte(getDL(), stringToByte(sss[1])));
                                case "AX" -> setAX(orWord(getAX(), stringToWord(sss[1])));
                                case "BX" -> setBX(orWord(getBX(), stringToWord(sss[1])));
                                case "CX" -> setCX(orWord(getCX(), stringToWord(sss[1])));
                                case "DX" -> setDX(orWord(getDX(), stringToWord(sss[1])));
                                case "SI" -> setSI(orWord(getSI(), stringToWord(sss[1])));
                                case "DI" -> setDI(orWord(getDI(), stringToWord(sss[1])));
                                case "BP" -> setBP(orWord(getBP(), stringToWord(sss[1])));
                                case "SP" -> setSP(orWord(getSP(), stringToWord(sss[1])));
                                case "CS" -> setCS(orWord(getCS(), stringToWord(sss[1])));
                                case "DS" -> setDS(orWord(getDS(), stringToWord(sss[1])));
                                case "ES" -> setES(orWord(getES(), stringToWord(sss[1])));
                                case "SS" -> setSS(orWord(getSS(), stringToWord(sss[1])));
                                default -> {
                                    int index = stringToMemIndex(sss[0]);
                                    switch (sss[1]) {
                                        case "AH", "AL", "BH", "BL", "CH", "CL", "DH", "DL" ->
                                                setMemByte(index, orByte(getMemByte(index), stringToByte(sss[1])));
                                        case "AX", "BX", "CX", "DX", "SI", "DI", "BP", "SP", "CS", "DS", "ES", "SS" ->
                                                setMemWord(stringToMemIndex(sss[0]), orWord(getMemWord(index), stringToWord(sss[1])));
                                        default -> throw new RuntimeException("指令他妈不存在,你在他妈的扯淡");
                                    }
                                }
                            }
                        }
                    }
                }
                case "XOR" -> {
                    switch (strings[1]) {
                        case "BYTE" -> {
                            String[] sss = strings[2].split(",");
                            int index = stringToMemIndex(sss[0]);
                            setMemByte(stringToMemIndex(sss[0]), xorByte(getMemByte(index), stringToByte(sss[1])));
                        }
                        case "WORD" -> {
                            String[] sss = strings[2].split(",");
                            int index = stringToMemIndex(sss[0]);
                            setMemWord(stringToMemIndex(sss[0]), xorWord(getMemWord(index), stringToWord(sss[1])));
                        }
                        default -> {
                            String[] sss = strings[1].split(",");
                            switch (sss[0]) {
                                case "AH" -> setAH(xorByte(getAH(), stringToByte(sss[1])));
                                case "AL" -> setAL(xorByte(getAL(), stringToByte(sss[1])));
                                case "BH" -> setBH(xorByte(getBH(), stringToByte(sss[1])));
                                case "BL" -> setBL(xorByte(getBL(), stringToByte(sss[1])));
                                case "CH" -> setCH(xorByte(getCH(), stringToByte(sss[1])));
                                case "CL" -> setCL(xorByte(getCL(), stringToByte(sss[1])));
                                case "DH" -> setDH(xorByte(getDH(), stringToByte(sss[1])));
                                case "DL" -> setDL(xorByte(getDL(), stringToByte(sss[1])));
                                case "AX" -> setAX(xorWord(getAX(), stringToWord(sss[1])));
                                case "BX" -> setBX(xorWord(getBX(), stringToWord(sss[1])));
                                case "CX" -> setCX(xorWord(getCX(), stringToWord(sss[1])));
                                case "DX" -> setDX(xorWord(getDX(), stringToWord(sss[1])));
                                case "SI" -> setSI(xorWord(getSI(), stringToWord(sss[1])));
                                case "DI" -> setDI(xorWord(getDI(), stringToWord(sss[1])));
                                case "BP" -> setBP(xorWord(getBP(), stringToWord(sss[1])));
                                case "SP" -> setSP(xorWord(getSP(), stringToWord(sss[1])));
                                case "CS" -> setCS(xorWord(getCS(), stringToWord(sss[1])));
                                case "DS" -> setDS(xorWord(getDS(), stringToWord(sss[1])));
                                case "ES" -> setES(xorWord(getES(), stringToWord(sss[1])));
                                case "SS" -> setSS(xorWord(getSS(), stringToWord(sss[1])));
                                default -> {
                                    int index = stringToMemIndex(sss[0]);
                                    switch (sss[1]) {
                                        case "AH", "AL", "BH", "BL", "CH", "CL", "DH", "DL" ->
                                                setMemByte(index, xorByte(getMemByte(index), stringToByte(sss[1])));
                                        case "AX", "BX", "CX", "DX", "SI", "DI", "BP", "SP", "CS", "DS", "ES", "SS" ->
                                                setMemWord(stringToMemIndex(sss[0]), xorWord(getMemWord(index), stringToWord(sss[1])));
                                        default -> throw new RuntimeException("指令他妈不存在,你在他妈的扯淡");
                                    }
                                }
                            }
                        }
                    }
                }
                case "SHL" -> {
                    switch (strings[1]) {
                        case "BYTE" -> {
                            String[] sss = strings[2].split(",");
                            int index = stringToMemIndex(sss[0]);
                            setMemByte(stringToMemIndex(sss[0]), shlByte(getMemByte(index), stringToByte(sss[1])));
                        }
                        case "WORD" -> {
                            String[] sss = strings[2].split(",");
                            int index = stringToMemIndex(sss[0]);
                            setMemWord(stringToMemIndex(sss[0]), shlWord(getMemWord(index), stringToWord(sss[1])));
                        }
                        default -> {
                            String[] sss = strings[1].split(",");
                            switch (sss[0]) {
                                case "AH" -> setAH(shlByte(getAH(), stringToByte(sss[1])));
                                case "AL" -> setAL(shlByte(getAL(), stringToByte(sss[1])));
                                case "BH" -> setBH(shlByte(getBH(), stringToByte(sss[1])));
                                case "BL" -> setBL(shlByte(getBL(), stringToByte(sss[1])));
                                case "CH" -> setCH(shlByte(getCH(), stringToByte(sss[1])));
                                case "CL" -> setCL(shlByte(getCL(), stringToByte(sss[1])));
                                case "DH" -> setDH(shlByte(getDH(), stringToByte(sss[1])));
                                case "DL" -> setDL(shlByte(getDL(), stringToByte(sss[1])));
                                case "AX" -> setAX(shlWord(getAX(), stringToWord(sss[1])));
                                case "BX" -> setBX(shlWord(getBX(), stringToWord(sss[1])));
                                case "CX" -> setCX(shlWord(getCX(), stringToWord(sss[1])));
                                case "DX" -> setDX(shlWord(getDX(), stringToWord(sss[1])));
                                case "SI" -> setSI(shlWord(getSI(), stringToWord(sss[1])));
                                case "DI" -> setDI(shlWord(getDI(), stringToWord(sss[1])));
                                case "BP" -> setBP(shlWord(getBP(), stringToWord(sss[1])));
                                case "SP" -> setSP(shlWord(getSP(), stringToWord(sss[1])));
                                case "CS" -> setCS(shlWord(getCS(), stringToWord(sss[1])));
                                case "DS" -> setDS(shlWord(getDS(), stringToWord(sss[1])));
                                case "ES" -> setES(shlWord(getES(), stringToWord(sss[1])));
                                case "SS" -> setSS(shlWord(getSS(), stringToWord(sss[1])));
                                default -> {
                                    int index = stringToMemIndex(sss[0]);
                                    switch (sss[1]) {
                                        case "AH", "AL", "BH", "BL", "CH", "CL", "DH", "DL" ->
                                                setMemByte(index, shlByte(getMemByte(index), stringToByte(sss[1])));
                                        case "AX", "BX", "CX", "DX", "SI", "DI", "BP", "SP", "CS", "DS", "ES", "SS" ->
                                                setMemWord(stringToMemIndex(sss[0]), shlWord(getMemWord(index), stringToWord(sss[1])));
                                        default -> throw new RuntimeException("指令他妈不存在,你在他妈的扯淡");
                                    }
                                }
                            }
                        }
                    }
                }
                case "SHR" -> {
                    switch (strings[1]) {
                        case "BYTE" -> {
                            String[] sss = strings[2].split(",");
                            int index = stringToMemIndex(sss[0]);
                            setMemByte(stringToMemIndex(sss[0]), shrByte(getMemByte(index), stringToByte(sss[1])));
                        }
                        case "WORD" -> {
                            String[] sss = strings[2].split(",");
                            int index = stringToMemIndex(sss[0]);
                            setMemWord(stringToMemIndex(sss[0]), shrWord(getMemWord(index), stringToWord(sss[1])));
                        }
                        default -> {
                            String[] sss = strings[1].split(",");
                            switch (sss[0]) {
                                case "AH" -> setAH(shrByte(getAH(), stringToByte(sss[1])));
                                case "AL" -> setAL(shrByte(getAL(), stringToByte(sss[1])));
                                case "BH" -> setBH(shrByte(getBH(), stringToByte(sss[1])));
                                case "BL" -> setBL(shrByte(getBL(), stringToByte(sss[1])));
                                case "CH" -> setCH(shrByte(getCH(), stringToByte(sss[1])));
                                case "CL" -> setCL(shrByte(getCL(), stringToByte(sss[1])));
                                case "DH" -> setDH(shrByte(getDH(), stringToByte(sss[1])));
                                case "DL" -> setDL(shrByte(getDL(), stringToByte(sss[1])));
                                case "AX" -> setAX(shrWord(getAX(), stringToWord(sss[1])));
                                case "BX" -> setBX(shrWord(getBX(), stringToWord(sss[1])));
                                case "CX" -> setCX(shrWord(getCX(), stringToWord(sss[1])));
                                case "DX" -> setDX(shrWord(getDX(), stringToWord(sss[1])));
                                case "SI" -> setSI(shrWord(getSI(), stringToWord(sss[1])));
                                case "DI" -> setDI(shrWord(getDI(), stringToWord(sss[1])));
                                case "BP" -> setBP(shrWord(getBP(), stringToWord(sss[1])));
                                case "SP" -> setSP(shrWord(getSP(), stringToWord(sss[1])));
                                case "CS" -> setCS(shrWord(getCS(), stringToWord(sss[1])));
                                case "DS" -> setDS(shrWord(getDS(), stringToWord(sss[1])));
                                case "ES" -> setES(shrWord(getES(), stringToWord(sss[1])));
                                case "SS" -> setSS(shrWord(getSS(), stringToWord(sss[1])));
                                default -> {
                                    int index = stringToMemIndex(sss[0]);
                                    switch (sss[1]) {
                                        case "AH", "AL", "BH", "BL", "CH", "CL", "DH", "DL" ->
                                                setMemByte(index, shrByte(getMemByte(index), stringToByte(sss[1])));
                                        case "AX", "BX", "CX", "DX", "SI", "DI", "BP", "SP", "CS", "DS", "ES", "SS" ->
                                                setMemWord(stringToMemIndex(sss[0]), shrWord(getMemWord(index), stringToWord(sss[1])));
                                        default -> throw new RuntimeException("指令他妈不存在,你在他妈的扯淡");
                                    }
                                }
                            }
                        }
                    }
                }
                case "ROL" -> {
                    switch (strings[1]) {
                        case "BYTE" -> {
                            String[] sss = strings[2].split(",");
                            int index = stringToMemIndex(sss[0]);
                            setMemByte(stringToMemIndex(sss[0]), rolByte(getMemByte(index), stringToByte(sss[1])));
                        }
                        case "WORD" -> {
                            String[] sss = strings[2].split(",");
                            int index = stringToMemIndex(sss[0]);
                            setMemWord(stringToMemIndex(sss[0]), rolWord(getMemWord(index), stringToWord(sss[1])));
                        }
                        default -> {
                            String[] sss = strings[1].split(",");
                            switch (sss[0]) {
                                case "AH" -> setAH(rolByte(getAH(), stringToByte(sss[1])));
                                case "AL" -> setAL(rolByte(getAL(), stringToByte(sss[1])));
                                case "BH" -> setBH(rolByte(getBH(), stringToByte(sss[1])));
                                case "BL" -> setBL(rolByte(getBL(), stringToByte(sss[1])));
                                case "CH" -> setCH(rolByte(getCH(), stringToByte(sss[1])));
                                case "CL" -> setCL(rolByte(getCL(), stringToByte(sss[1])));
                                case "DH" -> setDH(rolByte(getDH(), stringToByte(sss[1])));
                                case "DL" -> setDL(rolByte(getDL(), stringToByte(sss[1])));
                                case "AX" -> setAX(rolWord(getAX(), stringToWord(sss[1])));
                                case "BX" -> setBX(rolWord(getBX(), stringToWord(sss[1])));
                                case "CX" -> setCX(rolWord(getCX(), stringToWord(sss[1])));
                                case "DX" -> setDX(rolWord(getDX(), stringToWord(sss[1])));
                                case "SI" -> setSI(rolWord(getSI(), stringToWord(sss[1])));
                                case "DI" -> setDI(rolWord(getDI(), stringToWord(sss[1])));
                                case "BP" -> setBP(rolWord(getBP(), stringToWord(sss[1])));
                                case "SP" -> setSP(rolWord(getSP(), stringToWord(sss[1])));
                                case "CS" -> setCS(rolWord(getCS(), stringToWord(sss[1])));
                                case "DS" -> setDS(rolWord(getDS(), stringToWord(sss[1])));
                                case "ES" -> setES(rolWord(getES(), stringToWord(sss[1])));
                                case "SS" -> setSS(rolWord(getSS(), stringToWord(sss[1])));
                                default -> {
                                    int index = stringToMemIndex(sss[0]);
                                    switch (sss[1]) {
                                        case "AH", "AL", "BH", "BL", "CH", "CL", "DH", "DL" ->
                                                setMemByte(index, rolByte(getMemByte(index), stringToByte(sss[1])));
                                        case "AX", "BX", "CX", "DX", "SI", "DI", "BP", "SP", "CS", "DS", "ES", "SS" ->
                                                setMemWord(stringToMemIndex(sss[0]), rolWord(getMemWord(index), stringToWord(sss[1])));
                                        default -> throw new RuntimeException("指令他妈不存在,你在他妈的扯淡");
                                    }
                                }
                            }
                        }
                    }
                }
                case "ROR" -> {
                    switch (strings[1]) {
                        case "BYTE" -> {
                            String[] sss = strings[2].split(",");
                            int index = stringToMemIndex(sss[0]);
                            setMemByte(stringToMemIndex(sss[0]), rorByte(getMemByte(index), stringToByte(sss[1])));
                        }
                        case "WORD" -> {
                            String[] sss = strings[2].split(",");
                            int index = stringToMemIndex(sss[0]);
                            setMemWord(stringToMemIndex(sss[0]), rorWord(getMemWord(index), stringToWord(sss[1])));
                        }
                        default -> {
                            String[] sss = strings[1].split(",");
                            switch (sss[0]) {
                                case "AH" -> setAH(rorByte(getAH(), stringToByte(sss[1])));
                                case "AL" -> setAL(rorByte(getAL(), stringToByte(sss[1])));
                                case "BH" -> setBH(rorByte(getBH(), stringToByte(sss[1])));
                                case "BL" -> setBL(rorByte(getBL(), stringToByte(sss[1])));
                                case "CH" -> setCH(rorByte(getCH(), stringToByte(sss[1])));
                                case "CL" -> setCL(rorByte(getCL(), stringToByte(sss[1])));
                                case "DH" -> setDH(rorByte(getDH(), stringToByte(sss[1])));
                                case "DL" -> setDL(rorByte(getDL(), stringToByte(sss[1])));
                                case "AX" -> setAX(rorWord(getAX(), stringToWord(sss[1])));
                                case "BX" -> setBX(rorWord(getBX(), stringToWord(sss[1])));
                                case "CX" -> setCX(rorWord(getCX(), stringToWord(sss[1])));
                                case "DX" -> setDX(rorWord(getDX(), stringToWord(sss[1])));
                                case "SI" -> setSI(rorWord(getSI(), stringToWord(sss[1])));
                                case "DI" -> setDI(rorWord(getDI(), stringToWord(sss[1])));
                                case "BP" -> setBP(rorWord(getBP(), stringToWord(sss[1])));
                                case "SP" -> setSP(rorWord(getSP(), stringToWord(sss[1])));
                                case "CS" -> setCS(rorWord(getCS(), stringToWord(sss[1])));
                                case "DS" -> setDS(rorWord(getDS(), stringToWord(sss[1])));
                                case "ES" -> setES(rorWord(getES(), stringToWord(sss[1])));
                                case "SS" -> setSS(rorWord(getSS(), stringToWord(sss[1])));
                                default -> {
                                    int index = stringToMemIndex(sss[0]);
                                    switch (sss[1]) {
                                        case "AH", "AL", "BH", "BL", "CH", "CL", "DH", "DL" ->
                                                setMemByte(index, rorByte(getMemByte(index), stringToByte(sss[1])));
                                        case "AX", "BX", "CX", "DX", "SI", "DI", "BP", "SP", "CS", "DS", "ES", "SS" ->
                                                setMemWord(stringToMemIndex(sss[0]), rorWord(getMemWord(index), stringToWord(sss[1])));
                                        default -> throw new RuntimeException("指令他妈不存在,你在他妈的扯淡");
                                    }
                                }
                            }
                        }
                    }
                }
                case "NOT" -> {
                    switch (strings[1]) {
                        case "BYTE" ->
                                setMemByte(stringToMemIndex(strings[2]), notByte(getMemByte(stringToMemIndex(strings[2]))));
                        case "WORD" ->
                                setMemWord(stringToMemIndex(strings[2]), notWord(getMemWord(stringToMemIndex(strings[2]))));
                        default -> {
                            switch (strings[1]) {
                                case "AH" -> setAH(notByte(getAH()));
                                case "AL" -> setAL(notByte(getAL()));
                                case "BH" -> setBH(notByte(getBH()));
                                case "BL" -> setBL(notByte(getBL()));
                                case "CH" -> setCH(notByte(getCH()));
                                case "CL" -> setCL(notByte(getCL()));
                                case "DH" -> setDH(notByte(getDH()));
                                case "DL" -> setDL(notByte(getDL()));
                                case "AX" -> setAX(notWord(getAX()));
                                case "BX" -> setBX(notWord(getBX()));
                                case "CX" -> setCX(notWord(getCX()));
                                case "DX" -> setDX(notWord(getDX()));
                                case "SI" -> setSI(notWord(getSI()));
                                case "DI" -> setDI(notWord(getDI()));
                                case "BP" -> setBP(notWord(getBP()));
                                case "SP" -> setSP(notWord(getSP()));
                                case "CS" -> setCS(notWord(getCS()));
                                case "DS" -> setDS(notWord(getDS()));
                                case "ES" -> setES(notWord(getES()));
                                case "SS" -> setSS(notWord(getSS()));
                                default -> throw new RuntimeException("指令他妈不存在,你在他妈的扯淡");
                            }
                        }
                    }
                }
                case "NEG" -> {
                    switch (strings[1]) {
                        case "BYTE" ->
                                setMemByte(stringToMemIndex(strings[2]), negByte(getMemByte(stringToMemIndex(strings[2]))));
                        case "WORD" ->
                                setMemWord(stringToMemIndex(strings[2]), negWord(getMemWord(stringToMemIndex(strings[2]))));
                        default -> {
                            switch (strings[1]) {
                                case "AH" -> setAH(negByte(getAH()));
                                case "AL" -> setAL(negByte(getAL()));
                                case "BH" -> setBH(negByte(getBH()));
                                case "BL" -> setBL(negByte(getBL()));
                                case "CH" -> setCH(negByte(getCH()));
                                case "CL" -> setCL(negByte(getCL()));
                                case "DH" -> setDH(negByte(getDH()));
                                case "DL" -> setDL(negByte(getDL()));
                                case "AX" -> setAX(negWord(getAX()));
                                case "BX" -> setBX(negWord(getBX()));
                                case "CX" -> setCX(negWord(getCX()));
                                case "DX" -> setDX(negWord(getDX()));
                                case "SI" -> setSI(negWord(getSI()));
                                case "DI" -> setDI(negWord(getDI()));
                                case "BP" -> setBP(negWord(getBP()));
                                case "SP" -> setSP(negWord(getSP()));
                                case "CS" -> setCS(negWord(getCS()));
                                case "DS" -> setDS(negWord(getDS()));
                                case "ES" -> setES(negWord(getES()));
                                case "SS" -> setSS(negWord(getSS()));
                                default -> throw new RuntimeException("指令他妈不存在,你在他妈的扯淡");
                            }
                        }
                    }
                }
                case "MOV" -> {
                    switch (strings[1]) {
                        case "BYTE" -> {
                            String[] sss = strings[2].split(",");
                            setMemByte(stringToMemIndex(sss[0]), stringToByte(sss[1]));
                        }
                        case "WORD" -> {
                            String[] sss = strings[2].split(",");
                            setMemWord(stringToMemIndex(sss[0]), stringToWord(sss[1]));
                        }
                        default -> {
                            String[] sss = strings[1].split(",");
                            switch (sss[0]) {
                                case "AH" -> setAH(stringToByte(sss[1]));
                                case "AL" -> setAL(stringToByte(sss[1]));
                                case "BH" -> setBH(stringToByte(sss[1]));
                                case "BL" -> setBL(stringToByte(sss[1]));
                                case "CH" -> setCH(stringToByte(sss[1]));
                                case "CL" -> setCL(stringToByte(sss[1]));
                                case "DH" -> setDH(stringToByte(sss[1]));
                                case "DL" -> setDL(stringToByte(sss[1]));
                                case "AX" -> setAX(stringToWord(sss[1]));
                                case "BX" -> setBX(stringToWord(sss[1]));
                                case "CX" -> setCX(stringToWord(sss[1]));
                                case "DX" -> setDX(stringToWord(sss[1]));
                                case "SI" -> setSI(stringToWord(sss[1]));
                                case "DI" -> setDI(stringToWord(sss[1]));
                                case "BP" -> setBP(stringToWord(sss[1]));
                                case "SP" -> setSP(stringToWord(sss[1]));
                                case "CS" -> setCS(stringToWord(sss[1]));
                                case "DS" -> setDS(stringToWord(sss[1]));
                                case "ES" -> setES(stringToWord(sss[1]));
                                case "SS" -> setSS(stringToWord(sss[1]));
                                default -> {
                                    int index = stringToMemIndex(sss[0]);
                                    switch (sss[1]) {
                                        case "AH", "AL", "BH", "BL", "CH", "CL", "DH", "DL" ->
                                                setMemByte(index, stringToByte(sss[1]));
                                        case "AX", "BX", "CX", "DX", "BP", "SP", "SI", "DI", "CS", "DS", "ES", "SS" ->
                                                setMemWord(stringToMemIndex(sss[0]), stringToWord(sss[1]));
                                        default -> throw new RuntimeException("指令他妈不存在,你在他妈的扯淡");
                                    }
                                }
                            }
                        }
                    }
                }
                case "MUL" -> {
                    switch (strings[1]) {
                        case "AH", "AL", "BH", "BL", "CH", "CL", "DH", "DL" -> mulByte(stringToByte(strings[1]));
                        case "AX", "BX", "CX", "DX", "BP", "SP", "SI", "DI", "CS", "DS", "ES", "SS" ->
                                mulWord(stringToWord(strings[1]));
                        case "BYTE" -> mulByte(stringToByte(strings[2]));
                        case "WORD" -> mulWord(stringToWord(strings[2]));
                        default -> throw new RuntimeException("指令他妈不存在,你在他妈的扯淡");
                    }
                }
                case "DIV" -> {
                    switch (strings[1]) {
                        case "AH", "AL", "BH", "BL", "CH", "CL", "DH", "DL" -> divByte(stringToByte(strings[1]));
                        case "AX", "BX", "CX", "DX", "BP", "SP", "SI", "DI", "CS", "DS", "ES", "SS" ->
                                divWord(stringToWord(strings[1]));
                        case "BYTE" -> divByte(stringToByte(strings[2]));
                        case "WORD" -> divWord(stringToWord(strings[2]));
                        default -> throw new RuntimeException("指令他妈不存在,你在他妈的扯淡");
                    }
                }
                case "PUSH" -> {
                    switch (strings[1]) {
                        case "AX", "BX", "CX", "DX", "BP", "SP", "SI", "DI", "CS", "DS", "ES", "SS" ->
                                pushWord(stringToWord(strings[1]));
                        case "WORD" -> pushWord(stringToWord(strings[2]));
                        default -> throw new RuntimeException("指令他妈不存在,你在他妈的扯淡");
                    }
                }
                case "POP" -> {
                    switch (strings[1]) {
                        case "AX" -> setAX(popWord());
                        case "BX" -> setBX(popWord());
                        case "CX" -> setCX(popWord());
                        case "DX" -> setDX(popWord());
                        case "BP" -> setBP(popWord());
                        case "SP" -> setSP(popWord());
                        case "SI" -> setSI(popWord());
                        case "DI" -> setDI(popWord());
                        case "CS" -> setCS(popWord());
                        case "DS" -> setDS(popWord());
                        case "ES" -> setES(popWord());
                        case "SS" -> setSS(popWord());
                        case "WORD" -> setMemWord(stringToMemIndex(strings[2]), popWord());
                        default -> throw new RuntimeException("指令他妈不存在,你在他妈的扯淡");
                    }
                }
                case "CLD" -> cld();
                case "STD" -> std();
                case "JMP" -> {
                    jmp(strings[1]);
                    return false;
                }
                case "JZ" -> {
                    if (isZF()){
                        jmp(strings[1]);
                        return false;
                    }
                }
                case "JNZ" -> {
                    if (!isZF()){
                        jmp(strings[1]);
                        return false;
                    }
                }
                default -> throw new RuntimeException("指令他妈不存在,你在他妈的扯淡");
            }
        } catch (Exception e) {
            MainGUI.mainGUI.addData(e.getMessage());
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }


    public int stringToMemIndex(String string) {
        String s = string.replace("[", "");
        s = s.replace("]", "");
        String[] strings = s.split(":");
        if (strings.length < 2) {
            String[] sss = s.split("\\+");
            if (sss.length < 2) {
                return (getDS() << 4) + stringToWord(s);
            } else {
                return (getDS() << 4) + stringToWord(sss[0]) + stringToWord(sss[1]);
            }
        } else {
            String[] sss = strings[1].split("\\+");
            if (sss.length < 2) {
                return (stringToWord(strings[0]) << 4) + stringToWord(strings[1]);
            } else {
                return (stringToWord(strings[0]) << 4) + stringToWord(sss[0]) + stringToWord(sss[1]);
            }
        }
    }

    private short stringToByte(String string) {
        if (string.startsWith("[")) {
            String s = string.substring(1, string.length() - 1);
            return getMemByte(stringToMemIndex(s));
        } else if (string.startsWith("'")) {
            String s = string.substring(1, string.length() - 1);
            return s.getBytes()[0];
        } else {
            if (string.startsWith("0X")) {
                return Short.parseShort(string.substring(2), 16);
            } else if (string.endsWith("H")) {
                return Short.parseShort(string.substring(0, string.length() - 1), 16);
            } else if (string.endsWith("D")) {
                return Short.parseShort(string.substring(0, string.length() - 1));
            } else if (string.endsWith("B")) {
                return Short.parseShort(string.substring(0, string.length() - 1), 2);
            } else {
                try {
                    return Byte.parseByte(string);
                } catch (Exception e) {
                    return switch (string) {
                        case "AH" -> getAH();
                        case "AL" -> getAL();
                        case "BH" -> getBH();
                        case "BL" -> getBL();
                        case "CH" -> getCH();
                        case "CL" -> getCL();
                        case "DH" -> getDH();
                        case "DL" -> getDL();
                        default -> {
                            BigInteger bigInteger = findValue(string);
                            if (bigInteger == null) {
                                throw new RuntimeException("这玩意有问题,你他妈在扯淡");
                            } else {
                                yield (short) (bigInteger.intValue() & 0xFF);
                            }
                        }
                    };
                }
            }
        }
    }

    private int stringToWord(String string) {
        if (string.startsWith("[")) {
            String s = string.substring(1, string.length() - 1);
            return getMemWord(stringToMemIndex(s));
        } else if (string.startsWith("'")) {
            String s = string.substring(1, string.length() - 1);
            return s.getBytes()[0] + (s.getBytes()[1] << 8);
        } else {
            if (string.startsWith("0X")) {
                return Integer.parseInt(string.substring(2), 16);
            } else if (string.endsWith("H")) {
                return Integer.parseInt(string.substring(0, string.length() - 1), 16);
            } else if (string.endsWith("D")) {
                return Integer.parseInt(string.substring(0, string.length() - 1));
            } else if (string.endsWith("B")) {
                return Integer.parseInt(string.substring(0, string.length() - 1), 2);
            } else {
                try {
                    return Integer.parseInt(string);
                } catch (Exception e) {
                    return switch (string) {
                        case "AX" -> getAX();
                        case "BX" -> getBX();
                        case "CX" -> getCX();
                        case "DX" -> getDX();
                        case "SI" -> getSI();
                        case "DI" -> getDI();
                        case "SP" -> getSP();
                        case "BP" -> getBP();
                        case "CS" -> getCS();
                        case "DS" -> getDS();
                        case "ES" -> getES();
                        case "SS" -> getSS();
                        default -> {
                            BigInteger bigInteger = findValue(string);
                            if (bigInteger == null) {
                                throw new RuntimeException("这玩意有问题,你他妈在扯淡");
                            } else {
                                yield bigInteger.intValue() & 0xFFFF;
                            }
                        }
                    };
                }
            }
        }
    }


    private void setFLAG(BigInteger v, String vString, BigInteger rs, int size) {
        OF = v.compareTo(rs) != 0;
        ZF = rs.compareTo(BigInteger.ZERO) == 0;
        if (vString.length() > size) {
            SF = vString.substring(vString.length() - size).startsWith("1");
        } else if (vString.length() == size) {
            SF = vString.startsWith("1");
        } else {
            SF = false;
        }
        PF = countCharacter(vString, '1') % 2 == 0;
    }



    private void jmp(String string) {
        CmdList cmdList= findCmdList(string);
        if (cmdList.isAddGlobal()){
            setLabelListString(cmdList.getLabelListString());
        }else {
            setLabelListString(this.getLabelListString()+"\r\n"+cmdList.getLabelListString());
        }
        cmdList.runCmdList();
    }


    private short addByte(short a, short b) {
        int v = a + b;
        short rs = (short) (v & 0xFF);
        addSetFLAG(a, b, v, rs, 8);
        return rs;
    }

    private int addWord(int a, int b) {
        int v = a + b;
        int rs = v & 0xFFFF;
        addSetFLAG(a, b, v, rs, 16);
        return rs;
    }

    private void addSetFLAG(int a, int b, int v, int rs, int size) {
        int rsAF = (a & 0xF) + (b & 0xF);
        AF = Integer.toString(rsAF, 2).length() > 4;
        String s = Integer.toString(v, 2);
        CF = s.length() > size;
        setFLAG(BigInteger.valueOf(v), s, BigInteger.valueOf(rs), size);
    }


    private short subByte(short a, short b) {
        int v = a + negByte(b);
        short rs = (short) (v & 0xFF);
        addSetFLAG(a, b, v, rs, 8);
        return rs;
    }

    private int subWord(int a, int b) {
        int v = a + negWord(b);
        int rs = v & 0xFFFF;
        addSetFLAG(a, b, v, rs, 16);
        return rs;
    }


    private byte andByte(short a, short b) {
        int v = a & b;
        byte rs = (byte) (v & 0xFF);
        and_or_xorSetFLAG(v, rs, 8);
        return rs;
    }

    private int andWord(int a, int b) {
        int v = a & b;
        int rs = v & 0xFFFF;
        and_or_xorSetFLAG(v, rs, 16);
        return rs;
    }

    private byte orByte(short a, short b) {
        int v = a | b;
        byte rs = (byte) (v & 0xFF);
        and_or_xorSetFLAG(v, rs, 8);
        return rs;
    }

    private int orWord(int a, int b) {
        int v = a | b;
        int rs = v & 0xFFFF;
        and_or_xorSetFLAG(v, rs, 16);
        return rs;
    }

    private byte xorByte(short a, short b) {
        int v = a ^ b;
        byte rs = (byte) (v & 0xFF);
        and_or_xorSetFLAG(v, rs, 8);
        return rs;
    }

    private int xorWord(int a, int b) {
        int v = a ^ b;
        int rs = v & 0xFFFF;
        and_or_xorSetFLAG(v, rs, 16);
        return rs;
    }

    private void and_or_xorSetFLAG(int v, int rs, int size) {
        CF = false;
        AF = false;
        setFLAG(BigInteger.valueOf(v), Integer.toString(v, 2), BigInteger.valueOf(rs), size);
    }


    private short shlByte(short a, short b) {
        int v = a << b;
        short rs = (short) (v & 0xFF);
        lSetFLAG(v, rs, 8);
        return rs;
    }

    private int shlWord(int a, int b) {
        int v = a << b;
        int rs = v & 0xFFFF;
        lSetFLAG(v, rs, 16);
        return rs;
    }

    private short rolByte(short a, short b) {
        int v = a << b;
        short rs = (byte) (((v & 0xFF) + ((v & 0xFFFFFF00) >>> 8)) & 0xFF);
        lSetFLAG(v, rs, 8);
        return (short) (rs & 0xFF);
    }

    private int rolWord(int a, int b) {
        int v = a << b;
        int rs = (short) (((v & 0xFFFF) + ((v & 0xFFFF0000) >>> 16)) & 0xFFFF);
        lSetFLAG(v, rs, 8);
        return rs & 0xFFFF;
    }

    private void lSetFLAG(int v, int rs, int size) {
        String s = Integer.toString(v, 2);
        CF = s.length() > size;
        setFLAG(BigInteger.valueOf(v), s, BigInteger.valueOf(rs), size);
    }

    private short shrByte(short a, short b) {
        int v = a >>> b;
        short rs = (short) (v & 0xFF);
        rSetFLAG(v, rs, 8);
        return rs;
    }

    private int shrWord(int a, int b) {
        int v = a >>> b;
        int rs = v & 0xFFFF;
        rSetFLAG(v, rs, 16);
        return rs;
    }

    private short rorByte(short a, short b) {
        int v = a >>> b;
        short rs = (byte) (((v & 0xFF) + ((v & 0xFFFFFF00) >>> 8)) & 0xFF);
        rSetFLAG(v, rs, 8);
        return (short) (rs & 0xFF);
    }

    private int rorWord(int a, int b) {
        int v = a >>> b;
        int rs = (short) (((v & 0xFFFF) + ((v & 0xFFFF0000) >>> 16)) & 0xFFFF);
        rSetFLAG(v, rs, 8);
        return rs & 0xFFFF;
    }

    private void rSetFLAG(int v, int rs, int size) {
        String s = Integer.toString(v, 2);
        CF = s.length() > size;
        setFLAG(BigInteger.valueOf(v), s, BigInteger.valueOf(rs), size);
    }


    private short notByte(short a) {
        return (short) (~a & 0xFF);
    }

    private int notWord(int a) {
        return ~a & 0xFFFF;
    }

    private short negByte(short a) {
        return (short) ((notByte(a) + 1) & 0xFF);
    }

    private int negWord(int a) {
        return (notWord(a) + 1) & 0xFFFF;
    }


    private void mulByte(short a) {
        int v = getAL() * a;
        setFLAG(BigInteger.valueOf(v), Integer.toString(v, 2), BigInteger.valueOf(v & 0xff), 16);
        CF = OF;
        setAX(v);
    }

    private void mulWord(int a) {
        long v = ((long) getAX()) * a;
        setFLAG(BigInteger.valueOf(v), Long.toString(v, 2), BigInteger.valueOf(v & 0xffff), 16);
        CF = OF;
        setAX((int) (v & 0xffff));
        setDX((int) ((v & 0xffff0000L) >>> 16));
    }


    private void divByte(short a) {
        int d = getAX();
        int v = d / a;
        int r = d % a;
        setAL((short) v);
        setAH((short) r);
    }

    private void divWord(int a) {
        long d = getAX();
        d += (long) getDX() << 16;
        long v = d / a;
        long r = d % a;
        setAX((int) v);
        setDX((int) r);
    }


    private void cld() {
        DF = false;
    }

    private void std() {
        DF = true;
    }


    public static int countCharacter(String str, char targetChar) {
        int count = 0;
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) == targetChar) {
                count++;
            }
        }
        return count;
    }


    public void pushWord(int s) {
        setSP((getSP() - 2) & 0xFFFF);
        setMemWord((getSS() << 4) + getSP(), s);
    }

    public int popWord() {
        int s = getMemWord((getSS() << 4) + getSP());
        setSP((getSP() + 2) & 0xFFFF);
        return s;
    }

}
