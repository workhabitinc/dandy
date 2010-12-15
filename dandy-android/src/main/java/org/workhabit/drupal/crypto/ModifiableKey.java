package org.workhabit.drupal.crypto;

import java.security.Key;

/**
 * Copyright 2009 - WorkHabit, Inc. - acs
 * Date: 11/17/10, 2:47 PM
 */
public class ModifiableKey implements Key {
    private final Key unmodifiableKey;
    private final byte[] encoded;

    public ModifiableKey(Key unmodifiableKey) {
        this.encoded = unmodifiableKey.getEncoded();
        this.unmodifiableKey = unmodifiableKey;
    }

    /**
     * @see java.security.Key#getAlgorithm()
     */
    public String getAlgorithm() {
        return unmodifiableKey.getAlgorithm();
    }

    /**
     * @see java.security.Key#getEncoded()
     */
    public byte[] getEncoded() {
        return encoded;
    }

    /**
     * @see java.security.Key#getFormat()
     */
    public String getFormat() {
        return unmodifiableKey.getFormat();
    }

    private void adjustParity(ModifiableKey k) {
        byte[] encodedValues = k.getEncoded();
        for (int i = 0; i < encodedValues.length; i++) {
            encodedValues[i] = calculate7BitOddParity(encodedValues[i]);
        }
    }

    private byte calculate7BitOddParity(byte inputValue) {
        // 1 = 00000001
        // 2 = 00000010
        // 4 = 00000100
        // 8 = 00001000
        // 16 = 00010000
        // 32 = 00100000
        // 64 = 01000000
        // -128 = 10000000, (127 = 11111111)
        byte bitMasks[] = {2, 4, 8, 16, 32, 64, -128};

        byte numberOfOnes = 0;
        for (int i = 0; i < 7; i++) {
            if ((inputValue & bitMasks[i]) > 0) {
                numberOfOnes++;
            }
        }

        //if number of ones is even, the LSB is not set.
        //if the number of ones is even, odd parity returns a 1 as parity bit.
        //This will take the place of the LSB.
        if ((numberOfOnes & 1) == 0) {
            inputValue |= ((byte) 1);
        } else {
            //if the number of ones is odd, odd parity returns a 0 as parity bit.
            //This will take the place of the LSB.
            byte minus2 = -2;
            inputValue &= minus2; //set lsb to 0 since -2 = 11111110
        }
        return inputValue;
    }

}
