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
                                        default -> {
                                            return false;
                                        }
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
                                        default -> {
                                            return false;
                                        }
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
                                        default -> {
                                            return false;
                                        }
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
                                        default -> {
                                            return false;
                                        }
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
                                        default -> {
                                            return false;
                                        }
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
                                        default -> {
                                            return false;
                                        }
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
                                        default -> {
                                            return false;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                case "NOT" -> {
                    switch (strings[1]) {
                        case "BYTE" -> setMemByte(stringToMemIndex(strings[2]), notByte(getMemByte(stringToMemIndex(strings[2]))));
                        case "WORD" -> setMemWord(stringToMemIndex(strings[2]), notWord(getMemWord(stringToMemIndex(strings[2]))));
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
                                default -> {
                                    return false;
                                }
                            }
                        }
                    }
                }
                case "NEG" -> {
                    switch (strings[1]) {
                        case "BYTE" -> setMemByte(stringToMemIndex(strings[2]), negByte(getMemByte(stringToMemIndex(strings[2]))));
                        case "WORD" -> setMemWord(stringToMemIndex(strings[2]), negWord(getMemWord(stringToMemIndex(strings[2]))));
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
                                default -> {
                                    return false;
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
                case "CLD" -> cld();
                case "STD" -> std();
                default -> {
                    return false;
                }
            }
        }catch (Exception e){
            return false;
        }
        return true;
    }



    private void setFLAG(int v,String vString, int rs, int size) {
        OF = v != rs;
        ZF = rs == 0;
        if (vString.length() > size) {
            SF = vString.substring(vString.length() - size).startsWith("1");
        }else if (vString.length() == size){
            SF = vString.startsWith("1");
        }else {
            SF = false;
        }
        PF = countCharacter(vString, '1') % 2 == 0;
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
        int rsAF=(a&0xF)+(b&0xF);
        AF = Integer.toString(rsAF, 2).length() > 4;
        String s = Integer.toString(v, 2);
        CF = s.length() > size;
        setFLAG(v, s, rs, size);
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
        setFLAG(v, Integer.toString(v, 2), rs, size);
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
        short rs = (byte) (((v & 0xFF)+((v & 0xFFFFFF00)>>>8))&0xFF);
        lSetFLAG(v, rs, 8);
        return (short) (rs & 0xFF);
    }

    private int rolWord(int a, int b) {
        int v = a << b;
        int rs = (short) (((v & 0xFFFF)+((v & 0xFFFF0000)>>>16))&0xFFFF);
        lSetFLAG(v, rs, 8);
        return rs & 0xFFFF;
    }

    private void lSetFLAG(int v, int rs, int size) {
        String s = Integer.toString(v, 2);
        CF = s.length() > size;
        setFLAG(v, s, rs, size);
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
        short rs = (byte) (((v & 0xFF)+((v & 0xFFFFFF00)>>>8))&0xFF);
        rSetFLAG(v, rs, 8);
        return (short) (rs & 0xFF);
    }

    private int rorWord(int a, int b) {
        int v = a >>> b;
        int rs = (short) (((v & 0xFFFF)+((v & 0xFFFF0000)>>>16))&0xFFFF);
        rSetFLAG(v, rs, 8);
        return rs & 0xFFFF;
    }

    private void rSetFLAG(int v, int rs, int size) {
        String s = Integer.toString(v, 2);
        CF = s.length() > size;
        setFLAG(v, s, rs, size);
    }



    private short notByte(short a) {
        return (short) (~a & 0xFF);
    }

    private int notWord(int a) {
        return ~a & 0xFFFF;
    }

    private short negByte(short a) {
        return (short) ((notByte(a)+1)&0xFF);
    }

    private int negWord(int a) {
        return (notWord(a)+1)&0xFFFF;
    }



    private void cld(){
        DF=false;
    }

    private void std(){
        DF=true;
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
        String[] strings=s.split(":");
        if (strings.length==1){
            return (getDS()<<4)+stringToWord(s);
        }else {
            return (stringToWord(strings[0])<<4)+stringToWord(strings[1]);
        }
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
