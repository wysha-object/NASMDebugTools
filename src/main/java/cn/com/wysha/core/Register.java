package cn.com.wysha.core;

import java.math.BigInteger;

public class Register {
    private BigInteger value=BigInteger.ZERO;

    public BigInteger get() {
        return value;
    }

    public void set(BigInteger sourceWord) {
        this.value= sourceWord;
    }
}
