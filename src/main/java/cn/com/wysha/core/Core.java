package cn.com.wysha.core;

public class Core {
    RegisterByte ah = new RegisterByte();
    RegisterByte al = new RegisterByte();
    RegisterByte bh = new RegisterByte();
    RegisterByte bl = new RegisterByte();
    RegisterByte ch = new RegisterByte();
    RegisterByte cl = new RegisterByte();
    RegisterByte dh = new RegisterByte();
    RegisterByte dl = new RegisterByte();

    public short getAH() {
        return ah.get();
    }

    public short getAL() {
        return al.get();
    }

    public short getBH() {
        return bh.get();
    }

    public short getBL() {
        return bl.get();
    }

    public short getCH() {
        return ch.get();
    }

    public short getCL() {
        return cl.get();
    }

    public short getDH() {
        return dh.get();
    }

    public short getDL() {
        return dl.get();
    }

    public void setAH(short value) {
        ah.set((short) (value >> 8));
    }

    public void setAL(short value) {
        al.set((short) (value & 0xff));
    }

    public void setBH(short value) {
        bh.set((short) (value >> 8));
    }

    public void setBL(short value) {
        bl.set((short) (value & 0xff));
    }

    public void setCH(short value) {
        ch.set((short) (value >> 8));
    }

    public void setCL(short value) {
        cl.set((short) (value & 0xff));
    }

    public void setDH(short value) {
        dh.set((short) (value >> 8));
    }

    public void setDL(short value) {
        dl.set((short) (value & 0xff));
    }

    public int hlToWord(short highByte, short lowByte) {
        return (highByte << 8) | lowByte;
    }

    public int getAX() {
        return hlToWord(ah.get(), al.get());
    }

    public int getBX() {
        return hlToWord(bh.get(), bl.get());
    }

    public int getCX() {
        return hlToWord(ch.get(), cl.get());
    }

    public int getDX() {
        return hlToWord(dh.get(), dl.get());
    }

    private void setHL(RegisterByte h, RegisterByte l, int value) {
        h.set((short) (value >>> 8));
        l.set((short) (value & 0xff));
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

    RegisterWord si = new RegisterWord();
    RegisterWord di = new RegisterWord();
    RegisterWord bp = new RegisterWord();
    RegisterWord sp = new RegisterWord();

    RegisterWord cs = new RegisterWord();
    RegisterWord ds = new RegisterWord();
    RegisterWord es = new RegisterWord();
    RegisterWord ss = new RegisterWord();

    public int getSI() {
        return si.get();
    }

    public int getDI() {
        return di.get();
    }

    public int getBP() {
        return bp.get();
    }

    public int getSP() {
        return sp.get();
    }

    public int getCS() {
        return cs.get();
    }

    public int getDS() {
        return ds.get();
    }

    public int getES() {
        return es.get();
    }

    public int getSS() {
        return ss.get();
    }

    private void setSI(int value) {
        si.set(value);
    }

    private void setDI(int value) {
        di.set(value);
    }

    private void setBP(int value) {
        bp.set(value);
    }

    private void setSP(int value) {
        sp.set(value);
    }

    private void setCS(int value) {
        cs.set(value);
    }

    private void setDS(int value) {
        ds.set(value);
    }

    private void setES(int value) {
        es.set(value);
    }

    private void setSS(int value) {
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

    public Core(int memorySize) {
        memory = new short[memorySize];
    }

    public boolean run(String cmd) {
        String[] strings = cmd.split(" ");
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
                                        default -> {
                                            return false;
                                        }
                                    }
                                }
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
                                        case "AX", "BX", "CX", "DX", "BP", "SP", "SI", "DI","CS", "DS", "ES", "SS" ->
                                                setMemWord(stringToMemIndex(sss[0]), stringToWord(sss[1]));
                                        default -> {
                                            return false;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                default -> {
                    return false;
                }
            }
        }catch (Exception e){
            return false;
        }
        return true;
    }

    private short addByte(short a, short b) {
        int v = a + b;
        short rs = (short) (v & 0xFF);
        setFLAG(a, b, v, rs, 8);
        return rs;
    }

    private int addWord(int a, int b) {
        int v = a + b;
        int rs = v & 0xFFFF;
        setFLAG(a, b, v, rs, 16);
        return rs;
    }

    private void setFLAG(int a, int b, int v, int rs, int size) {
        String as = Integer.toString(a, 2);
        String bs = Integer.toString(b, 2);
        int rsAF=(a&0xF)+(b&0xF);
        AF = Integer.toString(rsAF, 2).length() > 4;
        String s = Integer.toString(v, 2);
        CF = s.length() > size;
        PF = countCharacter(s, '1') % 2 == 0;
        OF = v != rs;
        ZF = rs == 0;
        if (s.length() > size) {
            SF = s.substring(s.length() - size).startsWith("1");
        }else if (s.length() == size){
            SF = s.startsWith("1");
        }else {
            SF = false;
        }
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

    public int stringToMemIndex(String string) {
        String s = string.replace("[", "");
        s = s.replace("]", "");
        return stringToWord(s);
    }

    private short stringToByte(String string) {
        if (string.startsWith("[")) {
            String s= string.substring(1, string.length() - 1);
            return getMemByte(stringToWord(s));
        }else {
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
                        default -> throw new RuntimeException("寄存器不存在,你他妈在扯淡");
                    };
                }
            }
        }
    }

    private int stringToWord(String string) {
        if (string.startsWith("[")) {
            String s= string.substring(1, string.length() - 1);
            return getMemWord(stringToWord(s));
        }else {
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
                        default -> throw new RuntimeException("寄存器不存在,你他妈在扯淡");
                    };
                }
            }
        }
    }
}
