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

    public BigInteger getAH() {
        return ah.get();
    }

    public BigInteger getAL() {
        return al.get();
    }

    public BigInteger getBH() {
        return bh.get();
    }

    public BigInteger getBL() {
        return bl.get();
    }

    public BigInteger getCH() {
        return ch.get();
    }

    public BigInteger getCL() {
        return cl.get();
    }

    public BigInteger getDH() {
        return dh.get();
    }

    public BigInteger getDL() {
        return dl.get();
    }

    public void setAH(BigInteger value) {
        ah.set(value);
    }

    public void setAL(BigInteger value) {
        al.set(value);
    }

    public void setBH(BigInteger value) {
        bh.set(value);
    }

    public void setBL(BigInteger value) {
        bl.set(value);
    }

    public void setCH(BigInteger value) {
        ch.set(value);
    }

    public void setCL(BigInteger value) {
        cl.set(value);
    }

    public void setDH(BigInteger value) {
        dh.set(value);
    }

    public void setDL(BigInteger value) {
        dl.set(value);
    }

    public BigInteger hlToWord(BigInteger highByte, BigInteger lowByte) {
        return highByte.shiftLeft(8).or(lowByte);
    }

    public BigInteger getAX() {
        return hlToWord(getAH(), getAL());
    }

    public BigInteger getBX() {
        return hlToWord(getBH(), getBL());
    }

    public BigInteger getCX() {
        return hlToWord(getCH(), getCL());
    }

    public BigInteger getDX() {
        return hlToWord(getDH(), getDL());
    }

    private void setHL(Register h, Register l, BigInteger value) {
        h.set(value.shiftRight(8).and(BigInteger.valueOf(0xff)));
        l.set(value.and(BigInteger.valueOf(0xff)));
    }

    private void setAX(BigInteger value) {
        setHL(ah, al, value);
    }

    private void setBX(BigInteger value) {
        setHL(bh, bl, value);
    }

    private void setCX(BigInteger value) {
        setHL(ch, cl, value);
    }

    private void setDX(BigInteger value) {
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

    public BigInteger getSI() {
        return si.get();
    }

    public BigInteger getDI() {
        return di.get();
    }

    public BigInteger getBP() {
        return bp.get();
    }

    public BigInteger getSP() {
        return sp.get();
    }

    public BigInteger getCS() {
        return cs.get();
    }

    public BigInteger getDS() {
        return ds.get();
    }

    public BigInteger getES() {
        return es.get();
    }

    public BigInteger getSS() {
        return ss.get();
    }

    private void setSI(BigInteger value) {
        si.set(value);
    }

    private void setDI(BigInteger value) {
        di.set(value);
    }

    private void setBP(BigInteger value) {
        bp.set(value);
    }

    private void setSP(BigInteger value) {
        sp.set(value);
    }

    private void setCS(BigInteger value) {
        cs.set(value);
    }

    private void setDS(BigInteger value) {
        ds.set(value);
    }

    private void setES(BigInteger value) {
        es.set(value);
    }

    private void setSS(BigInteger value) {
        ss.set(value);
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

    public BigInteger getMem(int index,int sizeByte) {
        BigInteger rs = BigInteger.ZERO;
        for (int i = 0; i < sizeByte; i++) {
            rs = rs.add(BigInteger.valueOf(memory[index + i]).shiftLeft(i * 8));
        }
        return rs;
    }

    public void setMem(int index, BigInteger s,int sizeByte) {
        for (int i = 0; i < sizeByte; i++) {
            memory[index + i] = s.shiftRight(i * 8).and(BigInteger.valueOf(0xff)).shortValue();
        }
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
                case "MOV" -> {
                    switch (strings[1]) {
                        case "BYTE" -> {
                            String[] sss = strings[2].split(",");
                            setMem(stringToMemIndex(sss[0]), stringToNumber(sss[1],1),1);
                        }
                        case "WORD" -> {
                            String[] sss = strings[2].split(",");
                            setMem(stringToMemIndex(sss[0]), stringToNumber(sss[1],2),2);
                        }
                        default -> {
                            String[] sss = strings[1].split(",");
                            switch (sss[0]) {
                                case "AH" -> setAH(stringToNumber(sss[1],1));
                                case "AL" -> setAL(stringToNumber(sss[1],1));
                                case "BH" -> setBH(stringToNumber(sss[1],1));
                                case "BL" -> setBL(stringToNumber(sss[1],1));
                                case "CH" -> setCH(stringToNumber(sss[1],1));
                                case "CL" -> setCL(stringToNumber(sss[1],1));
                                case "DH" -> setDH(stringToNumber(sss[1],1));
                                case "DL" -> setDL(stringToNumber(sss[1],1));
                                case "AX" -> setAX(stringToNumber(sss[1],2));
                                case "BX" -> setBX(stringToNumber(sss[1],2));
                                case "CX" -> setCX(stringToNumber(sss[1],2));
                                case "DX" -> setDX(stringToNumber(sss[1],2));
                                case "BP" -> setBP(stringToNumber(sss[1],2));
                                case "SP" -> setSP(stringToNumber(sss[1],2));
                                case "SI" -> setSI(stringToNumber(sss[1],2));
                                case "DI" -> setDI(stringToNumber(sss[1],2));
                                case "CS" -> setCS(stringToNumber(sss[1],2));
                                case "DS" -> setDS(stringToNumber(sss[1],2));
                                case "ES" -> setES(stringToNumber(sss[1],2));
                                case "SS" -> setSS(stringToNumber(sss[1],2));
                                default -> {
                                    int index = stringToMemIndex(sss[0]);
                                    switch (sss[1]) {
                                        case "AH", "AL", "BH", "BL", "CH", "CL", "DH", "DL" ->
                                                setMem(index, stringToNumber((sss[1]),1),1);
                                        case "AX", "BX", "CX", "DX", "BP", "SP", "SI", "DI", "CS", "DS", "ES", "SS" ->
                                                setMem(index, stringToNumber((sss[1]),2),2);
                                        default -> throw new RuntimeException("指令他妈不存在,你在他妈的扯淡");
                                    }
                                }
                            }
                        }
                    }
                }
                case "ADD" -> {
                    switch (strings[1]) {
                        case "BYTE" -> {
                            String[] sss = strings[2].split(",");
                            int index = stringToMemIndex(sss[0]);
                            setMem(stringToMemIndex(sss[0]), add(getMem(index,1), stringToNumber(sss[1],1),1),1);
                        }
                        case "WORD" -> {
                            String[] sss = strings[2].split(",");
                            int index = stringToMemIndex(sss[0]);
                            setMem(stringToMemIndex(sss[0]), add(getMem(index,2), stringToNumber(sss[1],2),2),2);
                        }
                        default -> {
                            String[] sss = strings[1].split(",");
                            switch (sss[0]) {
                                case "AH" -> setAH(add(getAH(), stringToNumber(sss[1],1),1));
                                case "AL" -> setAL(add(getAL(), stringToNumber(sss[1],1),1));
                                case "BH" -> setBH(add(getBH(), stringToNumber(sss[1],1),1));
                                case "BL" -> setBL(add(getBL(), stringToNumber(sss[1],1),1));
                                case "CH" -> setCH(add(getCH(), stringToNumber(sss[1],1),1));
                                case "CL" -> setCL(add(getCL(), stringToNumber(sss[1],1),1));
                                case "DH" -> setDH(add(getDH(), stringToNumber(sss[1],1),1));
                                case "DL" -> setDL(add(getDL(), stringToNumber(sss[1],1),1));
                                case "AX" -> setAX(add(getAX(), stringToNumber(sss[1],2),2));
                                case "BX" -> setBX(add(getBX(), stringToNumber(sss[1],2),2));
                                case "CX" -> setCX(add(getCX(), stringToNumber(sss[1],2),2));
                                case "DX" -> setDX(add(getDX(), stringToNumber(sss[1],2),2));
                                case "SI" -> setSI(add(getSI(), stringToNumber(sss[1],2),2));
                                case "DI" -> setDI(add(getDI(), stringToNumber(sss[1],2),2));
                                case "BP" -> setBP(add(getBP(), stringToNumber(sss[1],2),2));
                                case "SP" -> setSP(add(getSP(), stringToNumber(sss[1],2),2));
                                case "CS" -> setCS(add(getCS(), stringToNumber(sss[1],2),2));
                                case "DS" -> setDS(add(getDS(), stringToNumber(sss[1],2),2));
                                case "ES" -> setES(add(getES(), stringToNumber(sss[1],2),2));
                                case "SS" -> setSS(add(getSS(), stringToNumber(sss[1],2),2));
                                default -> {
                                    int index = stringToMemIndex(sss[0]);
                                    switch (sss[1]) {
                                        case "AH", "AL", "BH", "BL", "CH", "CL", "DH", "DL" ->
                                                setMem(index, add(getMem(index,1), stringToNumber(sss[1],1),1),1);
                                        case "AX", "BX", "CX", "DX", "SI", "DI", "BP", "SP", "CS", "DS", "ES", "SS" ->
                                                setMem(index, add(getMem(index,2), stringToNumber(sss[2],2),2),2);
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
                            setMem(stringToMemIndex(sss[0]), sub(getMem(index,1), stringToNumber(sss[1],1),1),1);
                        }
                        case "WORD" -> {
                            String[] sss = strings[2].split(",");
                            int index = stringToMemIndex(sss[0]);
                            setMem(stringToMemIndex(sss[0]), sub(getMem(index,2), stringToNumber(sss[1],2),2),2);
                        }
                        default -> {
                            String[] sss = strings[1].split(",");
                            switch (sss[0]) {
                                case "AH" -> setAH(sub(getAH(), stringToNumber(sss[1],1),1));
                                case "AL" -> setAL(sub(getAL(), stringToNumber(sss[1],1),1));
                                case "BH" -> setBH(sub(getBH(), stringToNumber(sss[1],1),1));
                                case "BL" -> setBL(sub(getBL(), stringToNumber(sss[1],1),1));
                                case "CH" -> setCH(sub(getCH(), stringToNumber(sss[1],1),1));
                                case "CL" -> setCL(sub(getCL(), stringToNumber(sss[1],1),1));
                                case "DH" -> setDH(sub(getDH(), stringToNumber(sss[1],1),1));
                                case "DL" -> setDL(sub(getDL(), stringToNumber(sss[1],1),1));
                                case "AX" -> setAX(sub(getAX(), stringToNumber(sss[1],2),2));
                                case "BX" -> setBX(sub(getBX(), stringToNumber(sss[1],2),2));
                                case "CX" -> setCX(sub(getCX(), stringToNumber(sss[1],2),2));
                                case "DX" -> setDX(sub(getDX(), stringToNumber(sss[1],2),2));
                                case "SI" -> setSI(sub(getSI(), stringToNumber(sss[1],2),2));
                                case "DI" -> setDI(sub(getDI(), stringToNumber(sss[1],2),2));
                                case "BP" -> setBP(sub(getBP(), stringToNumber(sss[1],2),2));
                                case "SP" -> setSP(sub(getSP(), stringToNumber(sss[1],2),2));
                                case "CS" -> setCS(sub(getCS(), stringToNumber(sss[1],2),2));
                                case "DS" -> setDS(sub(getDS(), stringToNumber(sss[1],2),2));
                                case "ES" -> setES(sub(getES(), stringToNumber(sss[1],2),2));
                                case "SS" -> setSS(sub(getSS(), stringToNumber(sss[1],2),2));
                                default -> {
                                    int index = stringToMemIndex(sss[0]);
                                    switch (sss[1]) {
                                        case "AH", "AL", "BH", "BL", "CH", "CL", "DH", "DL" ->
                                                setMem(index, sub(getMem(index,1), stringToNumber(sss[1],1),1),1);
                                        case "AX", "BX", "CX", "DX", "SI", "DI", "BP", "SP", "CS", "DS", "ES", "SS" ->
                                                setMem(index, sub(getMem(index,2), stringToNumber(sss[2],2),2),2);
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
                            setMem(stringToMemIndex(sss[0]), and(getMem(index,1), stringToNumber(sss[1],1),1),1);
                        }
                        case "WORD" -> {
                            String[] sss = strings[2].split(",");
                            int index = stringToMemIndex(sss[0]);
                            setMem(stringToMemIndex(sss[0]), and(getMem(index,2), stringToNumber(sss[1],2),2),2);
                        }
                        default -> {
                            String[] sss = strings[1].split(",");
                            switch (sss[0]) {
                                case "AH" -> setAH(and(getAH(), stringToNumber(sss[1],1),1));
                                case "AL" -> setAL(and(getAL(), stringToNumber(sss[1],1),1));
                                case "BH" -> setBH(and(getBH(), stringToNumber(sss[1],1),1));
                                case "BL" -> setBL(and(getBL(), stringToNumber(sss[1],1),1));
                                case "CH" -> setCH(and(getCH(), stringToNumber(sss[1],1),1));
                                case "CL" -> setCL(and(getCL(), stringToNumber(sss[1],1),1));
                                case "DH" -> setDH(and(getDH(), stringToNumber(sss[1],1),1));
                                case "DL" -> setDL(and(getDL(), stringToNumber(sss[1],1),1));
                                case "AX" -> setAX(and(getAX(), stringToNumber(sss[1],2),2));
                                case "BX" -> setBX(and(getBX(), stringToNumber(sss[1],2),2));
                                case "CX" -> setCX(and(getCX(), stringToNumber(sss[1],2),2));
                                case "DX" -> setDX(and(getDX(), stringToNumber(sss[1],2),2));
                                case "SI" -> setSI(and(getSI(), stringToNumber(sss[1],2),2));
                                case "DI" -> setDI(and(getDI(), stringToNumber(sss[1],2),2));
                                case "BP" -> setBP(and(getBP(), stringToNumber(sss[1],2),2));
                                case "SP" -> setSP(and(getSP(), stringToNumber(sss[1],2),2));
                                case "CS" -> setCS(and(getCS(), stringToNumber(sss[1],2),2));
                                case "DS" -> setDS(and(getDS(), stringToNumber(sss[1],2),2));
                                case "ES" -> setES(and(getES(), stringToNumber(sss[1],2),2));
                                case "SS" -> setSS(and(getSS(), stringToNumber(sss[1],2),2));
                                default -> {
                                    int index = stringToMemIndex(sss[0]);
                                    switch (sss[1]) {
                                        case "AH", "AL", "BH", "BL", "CH", "CL", "DH", "DL" ->
                                                setMem(index, and(getMem(index,1), stringToNumber(sss[1],1),1),1);
                                        case "AX", "BX", "CX", "DX", "SI", "DI", "BP", "SP", "CS", "DS", "ES", "SS" ->
                                                setMem(index, and(getMem(index,2), stringToNumber(sss[2],2),2),2);
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
                            setMem(stringToMemIndex(sss[0]), or(getMem(index,1), stringToNumber(sss[1],1),1),1);
                        }
                        case "WORD" -> {
                            String[] sss = strings[2].split(",");
                            int index = stringToMemIndex(sss[0]);
                            setMem(stringToMemIndex(sss[0]), or(getMem(index,2), stringToNumber(sss[1],2),2),2);
                        }
                        default -> {
                            String[] sss = strings[1].split(",");
                            switch (sss[0]) {
                                case "AH" -> setAH(or(getAH(), stringToNumber(sss[1],1),1));
                                case "AL" -> setAL(or(getAL(), stringToNumber(sss[1],1),1));
                                case "BH" -> setBH(or(getBH(), stringToNumber(sss[1],1),1));
                                case "BL" -> setBL(or(getBL(), stringToNumber(sss[1],1),1));
                                case "CH" -> setCH(or(getCH(), stringToNumber(sss[1],1),1));
                                case "CL" -> setCL(or(getCL(), stringToNumber(sss[1],1),1));
                                case "DH" -> setDH(or(getDH(), stringToNumber(sss[1],1),1));
                                case "DL" -> setDL(or(getDL(), stringToNumber(sss[1],1),1));
                                case "AX" -> setAX(or(getAX(), stringToNumber(sss[1],2),2));
                                case "BX" -> setBX(or(getBX(), stringToNumber(sss[1],2),2));
                                case "CX" -> setCX(or(getCX(), stringToNumber(sss[1],2),2));
                                case "DX" -> setDX(or(getDX(), stringToNumber(sss[1],2),2));
                                case "SI" -> setSI(or(getSI(), stringToNumber(sss[1],2),2));
                                case "DI" -> setDI(or(getDI(), stringToNumber(sss[1],2),2));
                                case "BP" -> setBP(or(getBP(), stringToNumber(sss[1],2),2));
                                case "SP" -> setSP(or(getSP(), stringToNumber(sss[1],2),2));
                                case "CS" -> setCS(or(getCS(), stringToNumber(sss[1],2),2));
                                case "DS" -> setDS(or(getDS(), stringToNumber(sss[1],2),2));
                                case "ES" -> setES(or(getES(), stringToNumber(sss[1],2),2));
                                case "SS" -> setSS(or(getSS(), stringToNumber(sss[1],2),2));
                                default -> {
                                    int index = stringToMemIndex(sss[0]);
                                    switch (sss[1]) {
                                        case "AH", "AL", "BH", "BL", "CH", "CL", "DH", "DL" ->
                                                setMem(index, or(getMem(index,1), stringToNumber(sss[1],1),1),1);
                                        case "AX", "BX", "CX", "DX", "SI", "DI", "BP", "SP", "CS", "DS", "ES", "SS" ->
                                                setMem(index, or(getMem(index,2), stringToNumber(sss[2],2),2),2);
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
                            setMem(stringToMemIndex(sss[0]), xor(getMem(index,1), stringToNumber(sss[1],1),1),1);
                        }
                        case "WORD" -> {
                            String[] sss = strings[2].split(",");
                            int index = stringToMemIndex(sss[0]);
                            setMem(stringToMemIndex(sss[0]), xor(getMem(index,2), stringToNumber(sss[1],2),2),2);
                        }
                        default -> {
                            String[] sss = strings[1].split(",");
                            switch (sss[0]) {
                                case "AH" -> setAH(xor(getAH(), stringToNumber(sss[1],1),1));
                                case "AL" -> setAL(xor(getAL(), stringToNumber(sss[1],1),1));
                                case "BH" -> setBH(xor(getBH(), stringToNumber(sss[1],1),1));
                                case "BL" -> setBL(xor(getBL(), stringToNumber(sss[1],1),1));
                                case "CH" -> setCH(xor(getCH(), stringToNumber(sss[1],1),1));
                                case "CL" -> setCL(xor(getCL(), stringToNumber(sss[1],1),1));
                                case "DH" -> setDH(xor(getDH(), stringToNumber(sss[1],1),1));
                                case "DL" -> setDL(xor(getDL(), stringToNumber(sss[1],1),1));
                                case "AX" -> setAX(xor(getAX(), stringToNumber(sss[1],2),2));
                                case "BX" -> setBX(xor(getBX(), stringToNumber(sss[1],2),2));
                                case "CX" -> setCX(xor(getCX(), stringToNumber(sss[1],2),2));
                                case "DX" -> setDX(xor(getDX(), stringToNumber(sss[1],2),2));
                                case "SI" -> setSI(xor(getSI(), stringToNumber(sss[1],2),2));
                                case "DI" -> setDI(xor(getDI(), stringToNumber(sss[1],2),2));
                                case "BP" -> setBP(xor(getBP(), stringToNumber(sss[1],2),2));
                                case "SP" -> setSP(xor(getSP(), stringToNumber(sss[1],2),2));
                                case "CS" -> setCS(xor(getCS(), stringToNumber(sss[1],2),2));
                                case "DS" -> setDS(xor(getDS(), stringToNumber(sss[1],2),2));
                                case "ES" -> setES(xor(getES(), stringToNumber(sss[1],2),2));
                                case "SS" -> setSS(xor(getSS(), stringToNumber(sss[1],2),2));
                                default -> {
                                    int index = stringToMemIndex(sss[0]);
                                    switch (sss[1]) {
                                        case "AH", "AL", "BH", "BL", "CH", "CL", "DH", "DL" ->
                                                setMem(index, xor(getMem(index,1), stringToNumber(sss[1],1),1),1);
                                        case "AX", "BX", "CX", "DX", "SI", "DI", "BP", "SP", "CS", "DS", "ES", "SS" ->
                                                setMem(index, xor(getMem(index,2), stringToNumber(sss[2],2),2),2);
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
                            setMem(stringToMemIndex(sss[0]), shl(getMem(index,1), stringToNumber(sss[1],1),1),1);
                        }
                        case "WORD" -> {
                            String[] sss = strings[2].split(",");
                            int index = stringToMemIndex(sss[0]);
                            setMem(stringToMemIndex(sss[0]), shl(getMem(index,2), stringToNumber(sss[1],2),2),2);
                        }
                        default -> {
                            String[] sss = strings[1].split(",");
                            switch (sss[0]) {
                                case "AH" -> setAH(shl(getAH(), stringToNumber(sss[1],1),1));
                                case "AL" -> setAL(shl(getAL(), stringToNumber(sss[1],1),1));
                                case "BH" -> setBH(shl(getBH(), stringToNumber(sss[1],1),1));
                                case "BL" -> setBL(shl(getBL(), stringToNumber(sss[1],1),1));
                                case "CH" -> setCH(shl(getCH(), stringToNumber(sss[1],1),1));
                                case "CL" -> setCL(shl(getCL(), stringToNumber(sss[1],1),1));
                                case "DH" -> setDH(shl(getDH(), stringToNumber(sss[1],1),1));
                                case "DL" -> setDL(shl(getDL(), stringToNumber(sss[1],1),1));
                                case "AX" -> setAX(shl(getAX(), stringToNumber(sss[1],2),2));
                                case "BX" -> setBX(shl(getBX(), stringToNumber(sss[1],2),2));
                                case "CX" -> setCX(shl(getCX(), stringToNumber(sss[1],2),2));
                                case "DX" -> setDX(shl(getDX(), stringToNumber(sss[1],2),2));
                                case "SI" -> setSI(shl(getSI(), stringToNumber(sss[1],2),2));
                                case "DI" -> setDI(shl(getDI(), stringToNumber(sss[1],2),2));
                                case "BP" -> setBP(shl(getBP(), stringToNumber(sss[1],2),2));
                                case "SP" -> setSP(shl(getSP(), stringToNumber(sss[1],2),2));
                                case "CS" -> setCS(shl(getCS(), stringToNumber(sss[1],2),2));
                                case "DS" -> setDS(shl(getDS(), stringToNumber(sss[1],2),2));
                                case "ES" -> setES(shl(getES(), stringToNumber(sss[1],2),2));
                                case "SS" -> setSS(shl(getSS(), stringToNumber(sss[1],2),2));
                                default -> {
                                    int index = stringToMemIndex(sss[0]);
                                    switch (sss[1]) {
                                        case "AH", "AL", "BH", "BL", "CH", "CL", "DH", "DL" ->
                                                setMem(index, shl(getMem(index,1), stringToNumber(sss[1],1),1),1);
                                        case "AX", "BX", "CX", "DX", "SI", "DI", "BP", "SP", "CS", "DS", "ES", "SS" ->
                                                setMem(index, shl(getMem(index,2), stringToNumber(sss[2],2),2),2);
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
                            setMem(stringToMemIndex(sss[0]), shr(getMem(index,1), stringToNumber(sss[1],1),1),1);
                        }
                        case "WORD" -> {
                            String[] sss = strings[2].split(",");
                            int index = stringToMemIndex(sss[0]);
                            setMem(stringToMemIndex(sss[0]), shr(getMem(index,2), stringToNumber(sss[1],2),2),2);
                        }
                        default -> {
                            String[] sss = strings[1].split(",");
                            switch (sss[0]) {
                                case "AH" -> setAH(shr(getAH(), stringToNumber(sss[1],1),1));
                                case "AL" -> setAL(shr(getAL(), stringToNumber(sss[1],1),1));
                                case "BH" -> setBH(shr(getBH(), stringToNumber(sss[1],1),1));
                                case "BL" -> setBL(shr(getBL(), stringToNumber(sss[1],1),1));
                                case "CH" -> setCH(shr(getCH(), stringToNumber(sss[1],1),1));
                                case "CL" -> setCL(shr(getCL(), stringToNumber(sss[1],1),1));
                                case "DH" -> setDH(shr(getDH(), stringToNumber(sss[1],1),1));
                                case "DL" -> setDL(shr(getDL(), stringToNumber(sss[1],1),1));
                                case "AX" -> setAX(shr(getAX(), stringToNumber(sss[1],2),2));
                                case "BX" -> setBX(shr(getBX(), stringToNumber(sss[1],2),2));
                                case "CX" -> setCX(shr(getCX(), stringToNumber(sss[1],2),2));
                                case "DX" -> setDX(shr(getDX(), stringToNumber(sss[1],2),2));
                                case "SI" -> setSI(shr(getSI(), stringToNumber(sss[1],2),2));
                                case "DI" -> setDI(shr(getDI(), stringToNumber(sss[1],2),2));
                                case "BP" -> setBP(shr(getBP(), stringToNumber(sss[1],2),2));
                                case "SP" -> setSP(shr(getSP(), stringToNumber(sss[1],2),2));
                                case "CS" -> setCS(shr(getCS(), stringToNumber(sss[1],2),2));
                                case "DS" -> setDS(shr(getDS(), stringToNumber(sss[1],2),2));
                                case "ES" -> setES(shr(getES(), stringToNumber(sss[1],2),2));
                                case "SS" -> setSS(shr(getSS(), stringToNumber(sss[1],2),2));
                                default -> {
                                    int index = stringToMemIndex(sss[0]);
                                    switch (sss[1]) {
                                        case "AH", "AL", "BH", "BL", "CH", "CL", "DH", "DL" ->
                                                setMem(index, shr(getMem(index,1), stringToNumber(sss[1],1),1),1);
                                        case "AX", "BX", "CX", "DX", "SI", "DI", "BP", "SP", "CS", "DS", "ES", "SS" ->
                                                setMem(index, shr(getMem(index,2), stringToNumber(sss[2],2),2),2);
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
                            setMem(stringToMemIndex(sss[0]), rol(getMem(index,1), stringToNumber(sss[1],1),1),1);
                        }
                        case "WORD" -> {
                            String[] sss = strings[2].split(",");
                            int index = stringToMemIndex(sss[0]);
                            setMem(stringToMemIndex(sss[0]), rol(getMem(index,2), stringToNumber(sss[1],2),2),2);
                        }
                        default -> {
                            String[] sss = strings[1].split(",");
                            switch (sss[0]) {
                                case "AH" -> setAH(rol(getAH(), stringToNumber(sss[1],1),1));
                                case "AL" -> setAL(rol(getAL(), stringToNumber(sss[1],1),1));
                                case "BH" -> setBH(rol(getBH(), stringToNumber(sss[1],1),1));
                                case "BL" -> setBL(rol(getBL(), stringToNumber(sss[1],1),1));
                                case "CH" -> setCH(rol(getCH(), stringToNumber(sss[1],1),1));
                                case "CL" -> setCL(rol(getCL(), stringToNumber(sss[1],1),1));
                                case "DH" -> setDH(rol(getDH(), stringToNumber(sss[1],1),1));
                                case "DL" -> setDL(rol(getDL(), stringToNumber(sss[1],1),1));
                                case "AX" -> setAX(rol(getAX(), stringToNumber(sss[1],2),2));
                                case "BX" -> setBX(rol(getBX(), stringToNumber(sss[1],2),2));
                                case "CX" -> setCX(rol(getCX(), stringToNumber(sss[1],2),2));
                                case "DX" -> setDX(rol(getDX(), stringToNumber(sss[1],2),2));
                                case "SI" -> setSI(rol(getSI(), stringToNumber(sss[1],2),2));
                                case "DI" -> setDI(rol(getDI(), stringToNumber(sss[1],2),2));
                                case "BP" -> setBP(rol(getBP(), stringToNumber(sss[1],2),2));
                                case "SP" -> setSP(rol(getSP(), stringToNumber(sss[1],2),2));
                                case "CS" -> setCS(rol(getCS(), stringToNumber(sss[1],2),2));
                                case "DS" -> setDS(rol(getDS(), stringToNumber(sss[1],2),2));
                                case "ES" -> setES(rol(getES(), stringToNumber(sss[1],2),2));
                                case "SS" -> setSS(rol(getSS(), stringToNumber(sss[1],2),2));
                                default -> {
                                    int index = stringToMemIndex(sss[0]);
                                    switch (sss[1]) {
                                        case "AH", "AL", "BH", "BL", "CH", "CL", "DH", "DL" ->
                                                setMem(index, rol(getMem(index,1), stringToNumber(sss[1],1),1),1);
                                        case "AX", "BX", "CX", "DX", "SI", "DI", "BP", "SP", "CS", "DS", "ES", "SS" ->
                                                setMem(index, rol(getMem(index,2), stringToNumber(sss[2],2),2),2);
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
                            setMem(stringToMemIndex(sss[0]), ror(getMem(index,1), stringToNumber(sss[1],1),1),1);
                        }
                        case "WORD" -> {
                            String[] sss = strings[2].split(",");
                            int index = stringToMemIndex(sss[0]);
                            setMem(stringToMemIndex(sss[0]), ror(getMem(index,2), stringToNumber(sss[1],2),2),2);
                        }
                        default -> {
                            String[] sss = strings[1].split(",");
                            switch (sss[0]) {
                                case "AH" -> setAH(ror(getAH(), stringToNumber(sss[1],1),1));
                                case "AL" -> setAL(ror(getAL(), stringToNumber(sss[1],1),1));
                                case "BH" -> setBH(ror(getBH(), stringToNumber(sss[1],1),1));
                                case "BL" -> setBL(ror(getBL(), stringToNumber(sss[1],1),1));
                                case "CH" -> setCH(ror(getCH(), stringToNumber(sss[1],1),1));
                                case "CL" -> setCL(ror(getCL(), stringToNumber(sss[1],1),1));
                                case "DH" -> setDH(ror(getDH(), stringToNumber(sss[1],1),1));
                                case "DL" -> setDL(ror(getDL(), stringToNumber(sss[1],1),1));
                                case "AX" -> setAX(ror(getAX(), stringToNumber(sss[1],2),2));
                                case "BX" -> setBX(ror(getBX(), stringToNumber(sss[1],2),2));
                                case "CX" -> setCX(ror(getCX(), stringToNumber(sss[1],2),2));
                                case "DX" -> setDX(ror(getDX(), stringToNumber(sss[1],2),2));
                                case "SI" -> setSI(ror(getSI(), stringToNumber(sss[1],2),2));
                                case "DI" -> setDI(ror(getDI(), stringToNumber(sss[1],2),2));
                                case "BP" -> setBP(ror(getBP(), stringToNumber(sss[1],2),2));
                                case "SP" -> setSP(ror(getSP(), stringToNumber(sss[1],2),2));
                                case "CS" -> setCS(ror(getCS(), stringToNumber(sss[1],2),2));
                                case "DS" -> setDS(ror(getDS(), stringToNumber(sss[1],2),2));
                                case "ES" -> setES(ror(getES(), stringToNumber(sss[1],2),2));
                                case "SS" -> setSS(ror(getSS(), stringToNumber(sss[1],2),2));
                                default -> {
                                    int index = stringToMemIndex(sss[0]);
                                    switch (sss[1]) {
                                        case "AH", "AL", "BH", "BL", "CH", "CL", "DH", "DL" ->
                                                setMem(index, ror(getMem(index,1), stringToNumber(sss[1],1),1),1);
                                        case "AX", "BX", "CX", "DX", "SI", "DI", "BP", "SP", "CS", "DS", "ES", "SS" ->
                                                setMem(index, ror(getMem(index,2), stringToNumber(sss[2],2),2),2);
                                        default -> throw new RuntimeException("指令他妈不存在,你在他妈的扯淡");
                                    }
                                }
                            }
                        }
                    }
                }
                case "NOT" -> {
                    switch (strings[1]) {
                        case "BYTE" -> {
                            String[] sss = strings[2].split(",");
                            int index = stringToMemIndex(sss[0]);
                            setMem(stringToMemIndex(sss[0]), not(getMem(index,1)),1);
                        }
                        case "WORD" -> {
                            String[] sss = strings[2].split(",");
                            int index = stringToMemIndex(sss[0]);
                            setMem(stringToMemIndex(sss[0]), not(getMem(index,2)),2);
                        }
                        default -> {
                            String[] sss = strings[1].split(",");
                            switch (sss[0]) {
                                case "AH" -> setAH(not(getAH()));
                                case "AL" -> setAL(not(getAL()));
                                case "BH" -> setBH(not(getBH()));
                                case "BL" -> setBL(not(getBL()));
                                case "CH" -> setCH(not(getCH()));
                                case "CL" -> setCL(not(getCL()));
                                case "DH" -> setDH(not(getDH()));
                                case "DL" -> setDL(not(getDL()));
                                case "AX" -> setAX(not(getAX()));
                                case "BX" -> setBX(not(getBX()));
                                case "CX" -> setCX(not(getCX()));
                                case "DX" -> setDX(not(getDX()));
                                default -> {
                                    int index = stringToMemIndex(sss[0]);
                                    switch (sss[1]) {
                                        case "AH", "AL", "BH", "BL", "CH", "CL", "DH", "DL" ->
                                                setMem(index, not(getMem(index,1)),1);
                                        case "AX", "BX", "CX", "DX", "SI", "DI", "BP", "SP", "CS", "DS", "ES", "SS" ->
                                                setMem(index, not(getMem(index,2)),2);
                                        default -> throw new RuntimeException("指令他妈不存在,你在他妈的扯淡");
                                    }
                                }
                            }
                        }
                    }
                }
                case "NEG" -> {
                    switch (strings[1]) {
                        case "BYTE" -> {
                            String[] sss = strings[2].split(",");
                            int index = stringToMemIndex(sss[0]);
                            setMem(stringToMemIndex(sss[0]), neg(getMem(index,1)),1);
                        }
                        case "WORD" -> {
                            String[] sss = strings[2].split(",");
                            int index = stringToMemIndex(sss[0]);
                            setMem(stringToMemIndex(sss[0]), neg(getMem(index,2)),2);
                        }
                        default -> {
                            String[] sss = strings[1].split(",");
                            switch (sss[0]) {
                                case "AH" -> setAH(neg(getAH()));
                                case "AL" -> setAL(neg(getAL()));
                                case "BH" -> setBH(neg(getBH()));
                                case "BL" -> setBL(neg(getBL()));
                                case "CH" -> setCH(neg(getCH()));
                                case "CL" -> setCL(neg(getCL()));
                                case "DH" -> setDH(neg(getDH()));
                                case "DL" -> setDL(neg(getDL()));
                                case "AX" -> setAX(neg(getAX()));
                                case "BX" -> setBX(neg(getBX()));
                                case "CX" -> setCX(neg(getCX()));
                                case "DX" -> setDX(neg(getDX()));
                                default -> {
                                    int index = stringToMemIndex(sss[0]);
                                    switch (sss[1]) {
                                        case "AH", "AL", "BH", "BL", "CH", "CL", "DH", "DL" ->
                                                setMem(index, neg(getMem(index,1)),1);
                                        case "AX", "BX", "CX", "DX", "SI", "DI", "BP", "SP", "CS", "DS", "ES", "SS" ->
                                                setMem(index, neg(getMem(index,2)),2);
                                        default -> throw new RuntimeException("指令他妈不存在,你在他妈的扯淡");
                                    }
                                }
                            }
                        }
                    }
                }
                case "MUL" -> {
                    switch (strings[1]) {
                        case "AH", "AL", "BH", "BL", "CH", "CL", "DH", "DL" ->
                                mul(stringToNumber(strings[1],1),1);
                        case "AX", "BX", "CX", "DX", "BP", "SP", "SI", "DI", "CS", "DS", "ES", "SS" ->
                                mul(stringToNumber(strings[1],2),2);
                        case "BYTE" -> mul(stringToNumber(strings[2],1),1);
                        case "WORD" -> mul(stringToNumber(strings[2],2),2);
                        default -> throw new RuntimeException("指令他妈不存在,你在他妈的扯淡");
                    }
                }
                case "DIV" -> {
                    switch (strings[1]) {
                        case "AH", "AL", "BH", "BL", "CH", "CL", "DH", "DL" ->
                                div(stringToNumber(strings[1],1),1);
                        case "AX", "BX", "CX", "DX", "BP", "SP", "SI", "DI", "CS", "DS", "ES", "SS" ->
                                div(stringToNumber(strings[1],2),2);
                        case "BYTE" -> div(stringToNumber(strings[2],1),1);
                        case "WORD" -> div(stringToNumber(strings[2],2),2);
                        default -> throw new RuntimeException("指令他妈不存在,你在他妈的扯淡");
                    }
                }
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
                case "PUSH" -> {
                    switch (strings[1]) {
                        case "AX", "BX", "CX", "DX", "BP", "SP", "SI", "DI", "CS", "DS", "ES", "SS" ->
                                push(stringToNumber(strings[1],2),2);
                        case "WORD" -> push(stringToNumber(strings[2],2),2);
                        default -> throw new RuntimeException("指令他妈不存在,你在他妈的扯淡");
                    }
                }
                case "POP" -> {
                    switch (strings[1]) {
                        case "AX" -> setAX(pop(2));
                        case "BX" -> setBX(pop(2));
                        case "CX" -> setCX(pop(2));
                        case "DX" -> setDX(pop(2));
                        case "BP" -> setBP(pop(2));
                        case "SP" -> setSP(pop(2));
                        case "SI" -> setSI(pop(2));
                        case "DI" -> setDI(pop(2));
                        case "CS" -> setCS(pop(2));
                        case "DS" -> setDS(pop(2));
                        case "ES" -> setES(pop(2));
                        case "SS" -> setSS(pop(2));
                        case "WORD" -> setMem(stringToMemIndex(strings[2]), pop(2),2);
                        default -> throw new RuntimeException("指令他妈不存在,你在他妈的扯淡");
                    }
                }
                case "CLD" -> cld();
                case "STD" -> std();
                default -> throw new RuntimeException("指令他妈不存在,你在他妈的扯淡");
            }
        } catch (Exception e) {
            MainGUI.mainGUI.addData(e.getMessage());
            MainGUI.mainGUI.refresh();
            System.out.println(e.getMessage());
            return false;
        }
        MainGUI.mainGUI.refresh();
        return true;
    }


    public int stringToMemIndex(String string) {
        String s = string.replace("[", "");
        s = s.replace("]", "");
        String[] strings = s.split(":");
        if (strings.length < 2) {
            String[] sss = s.split("\\+");
            if (sss.length < 2) {
                return (getDS().shiftLeft(4)).add(stringToNumber(s,2)).intValue();
            } else {
                return (getDS().shiftLeft(4)).add(stringToNumber(sss[0],2)).add(stringToNumber(sss[1],2)).intValue();
            }
        } else {
            String[] sss = strings[1].split("\\+");
            if (sss.length < 2) {
                return (stringToNumber(strings[0],2).shiftLeft(4)).add(stringToNumber(strings[1],2)).intValue();
            } else {
                return (stringToNumber(strings[0],2).shiftLeft(4)).add(stringToNumber(sss[0],2)).add(stringToNumber(sss[1],2)).intValue();
            }
        }
    }

    private BigInteger stringToNumber(String string, int sizeByte) {
        if (string.startsWith("[")) {
            String s = string.substring(1, string.length() - 1);
            return getMem(stringToMemIndex(s),sizeByte);
        } else if (string.startsWith("'")) {
            String s = string.substring(1, string.length() - 1);
            BigInteger bigInteger=BigInteger.ZERO;
            for (int i = 0; i < sizeByte; i++) {
                bigInteger=bigInteger.add(BigInteger.valueOf(s.getBytes()[i]));
            }
            return bigInteger;
        } else {
            if (string.startsWith("0X")) {
                return new BigInteger(string.substring(2), 16);
            } else if (string.endsWith("H")) {
                return new BigInteger(string.substring(0, string.length() - 1), 16);
            } else if (string.endsWith("D")) {
                return new BigInteger(string.substring(0, string.length() - 1));
            } else if (string.endsWith("B")) {
                return new BigInteger(string.substring(0, string.length() - 1), 2);
            } else {
                try {
                    return new BigInteger(string);
                } catch (Exception e) {
                    if (sizeByte==1){
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
                                    yield bigInteger.and(new BigInteger("F".repeat(sizeByte*2),16));
                                }
                            }
                        };
                    }else if (sizeByte==2){
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
                                    yield bigInteger.and(new BigInteger("F".repeat(sizeByte*2),16));
                                }
                            }
                        };
                    }else {
                        throw new RuntimeException("这玩意有问题,你他妈在扯淡");
                    }
                }
            }
        }
    }



    private void setFLAG(BigInteger v, String vString, BigInteger rs, int sizeByte) {
        OF = v.compareTo(rs) != 0;
        ZF = rs.compareTo(BigInteger.ZERO) == 0;
        if (vString.length() > sizeByte*8) {
            SF = vString.substring(vString.length() - sizeByte*8).startsWith("1");
        } else if (vString.length() == sizeByte*8) {
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


    private BigInteger add(BigInteger d, BigInteger s,int sizeByte) {
        BigInteger v = d.add(s);
        BigInteger rs = v.and(new BigInteger("f".repeat(sizeByte*2),16));
        add_subSetFLAG(d, s, v, rs, sizeByte);
        return rs;
    }

    private BigInteger sub(BigInteger d, BigInteger s,int sizeByte) {
        BigInteger v = d.add(neg(s));
        BigInteger rs = v.and(new BigInteger("f".repeat(sizeByte*2),16));
        add_subSetFLAG(d, s, v, rs, sizeByte);
        return rs;
    }

    private void add_subSetFLAG(BigInteger d, BigInteger s, BigInteger v, BigInteger rs, int sizeByte) {
        BigInteger rsAF = d.and(BigInteger.valueOf(0xF)).add(s.and(BigInteger.valueOf(0xF)));
        AF = rsAF.toString(2).length() > 4;
        String string = v.toString(2);
        CF = string.length() > sizeByte*8;
        setFLAG(v, string, rs, sizeByte);
    }



    private BigInteger and(BigInteger d, BigInteger b, int sizeByte) {
        BigInteger v = d.and(b);
        BigInteger rs = v.and(new BigInteger("f".repeat(sizeByte*2),16));
        and_or_xorSetFLAG(v, rs, sizeByte);
        return rs;
    }

    private BigInteger or(BigInteger d, BigInteger s, int sizeByte) {
        BigInteger v = d.or(s);
        BigInteger rs = v.and(new BigInteger("f".repeat(sizeByte*2),16));
        and_or_xorSetFLAG(v, rs, sizeByte);
        return rs;
    }

    private BigInteger xor(BigInteger d, BigInteger s, int sizeByte) {
        BigInteger v = d.xor(s);
        BigInteger rs = v.and(new BigInteger("f".repeat(sizeByte*2),16));
        and_or_xorSetFLAG(v, rs, sizeByte);
        return rs;
    }

    private void and_or_xorSetFLAG(BigInteger v, BigInteger rs, int sizeByte) {
        CF = false;
        AF = false;
        setFLAG(v, v.toString(2), rs, sizeByte);
    }



    private BigInteger shl(BigInteger d, BigInteger s, int sizeByte) {
        BigInteger v = d.shiftLeft(s.intValue());
        BigInteger rs = v.and(new BigInteger("f".repeat(sizeByte*2),16));
        lSetFLAG(v, rs, sizeByte);
        return rs;
    }

    private BigInteger rol(BigInteger d, BigInteger s, int sizeByte) {
        BigInteger v = d.shiftLeft(s.intValue());
        BigInteger rs = v.and(new BigInteger("f".repeat(sizeByte*2),16)).add(v.shiftRight(sizeByte*8 - s.intValue()));
        lSetFLAG(v, rs, 8);
        return rs.and(new BigInteger("f".repeat(sizeByte*2),2));
    }

    private void lSetFLAG(BigInteger v, BigInteger rs, int sizeByte) {
        String s = v.toString(2);
        CF = s.length() > sizeByte*8;
        setFLAG(v, s, rs, sizeByte);
    }

    private BigInteger shr(BigInteger d, BigInteger s, int sizeByte) {
        BigInteger v = d.shiftRight(s.intValue());
        BigInteger rs = v.and(new BigInteger("f".repeat(sizeByte*2),16));
        rSetFLAG(v, rs, sizeByte);
        return rs;
    }

    private BigInteger ror(BigInteger d, BigInteger s, int sizeByte) {
        BigInteger v = d.shiftRight(s.intValue());
        BigInteger rs = v.add(v.and(new BigInteger("1".repeat(s.intValue()),2).shiftLeft(s.intValue())));
        rSetFLAG(v, rs, sizeByte);
        return rs.and(new BigInteger("f".repeat(sizeByte*2),16));
    }

    private void rSetFLAG(BigInteger v, BigInteger rs, int size) {
        String s = v.toString(2);
        CF = s.length() > size;
        setFLAG(v, s, rs, size);
    }


    private BigInteger not(BigInteger a) {
        return a.not();
    }



    private BigInteger neg(BigInteger a) {
        return not(a).add(BigInteger.ONE);
    }


    private void mul(BigInteger a, int sizeByte) {
        BigInteger v;
        if (sizeByte == 1) {
            v = getAL().multiply(a);
        } else if (sizeByte == 2) {
            v = getAX().multiply(a);
        }else {
            return;
        }
        setFLAG(v, v.toString(2), v.add(new BigInteger("F".repeat(sizeByte*2))), sizeByte);
        CF = OF;
        if (sizeByte == 1) {
            setAX(v);
        } else {
            setAX(v.and(BigInteger.valueOf(0xFFFF)));
            setDX(v.shiftRight(16));
        }
    }



    private void div(BigInteger a, int sizeByte) {
        if (sizeByte == 1) {
            BigInteger d = getAX();
            BigInteger[] v = d.divideAndRemainder(a);
            setAL(v[0]);
            setAH(v[1]);
        }else if (sizeByte == 2) {
            BigInteger d = getAX().add(getDX().shiftLeft(16));
            BigInteger[] v = d.divideAndRemainder(a);
            setAX(v[0]);
            setDX(v[1]);
        }
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



    public void push(BigInteger s, int sizeByte) {
        setSP((getSP().subtract(BigInteger.valueOf(2))).and(new BigInteger("F".repeat(sizeByte*2),2)));
        setMem((getSS().shiftLeft(4)).add(getSP()).intValue(), s,sizeByte);
    }

    public BigInteger pop(int sizeByte) {
        BigInteger s = getMem(getSS().shiftLeft(4).and(getSP()).intValue(),sizeByte);
        setSP((getSP().add(BigInteger.valueOf(2))).and(new BigInteger("F".repeat(sizeByte*2),2)));
        return s;
    }

}
