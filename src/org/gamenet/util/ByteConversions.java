/*
 * Copyright (c) 2005 (Mike) Maurice Kienenberger (mkienenb@gmail.com)
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package org.gamenet.util;

import java.util.Arrays;

/**
 * @author mlk
 */
public class ByteConversions
{
    
    /**
     * @param byteArray
     * @param offset
     * @param maxLength
     * @return
     */
    public static String getZeroTerminatedStringInByteArrayAtPositionMaxLength(byte[] byteArray, int offset, int maxLength)
    {
        int length = 0;
        
        while ( (0 != byteArray[offset + length]) && (length < maxLength) )
            length++;
                
        return new String(byteArray, offset, length);
    }


    /**
     * @param value
     * @param byteArray
     * @param offset
     * @param maxLength
     */
    public static void setZeroTerminatedStringInByteArrayAtPositionMaxLength(String value, byte[] byteArray, int offset, int maxLength)
    {
        if (null != value)
        {
            // update value
            Arrays.fill(byteArray, offset, offset + maxLength, (byte)0);
            byte[] valueBytes = value.getBytes();
            System.arraycopy(valueBytes, 0, byteArray, offset, Math.min(maxLength, valueBytes.length));
            if (valueBytes.length < maxLength) byteArray[offset + valueBytes.length] = 0;
        }
    }

    /**
     * @param byteArray
     * @param offset
     * @return
     */
    public static long getLongInByteArrayAtPosition(byte[] byteArray, int offset)
    {
        long longValue = 0;
        for (int i = 0; i < 8; i++) {
            longValue += ((long)convertByteToInt(byteArray[offset + i])) << (8 * i);
        }
        
        return longValue;
    }

    /**
     * @param value
     * @param byteArray
     * @param offset
     */
    static public void setLongInByteArrayAtPosition(
        long value,
        byte[] byteArray,
        int offset)
    {
        for (int i = 0; i < 8; i++)
        {
            long shiftedValue = value >> (long)(8 * i);
            int intValue = (int)(shiftedValue & 0xFF);
            byte byteValue = convertIntToByte(intValue);
            byteArray[offset + i] = byteValue;
        }
    }

    /**
     * @param value
     * @param byteArray
     * @param offset
     */
    static public void setIntegerInByteArrayAtPosition(
        long value,
        byte[] byteArray,
        int offset)
    {
        for (int i = 0; i < 4; i++)
        {
            int intValue = (int)((value >> (8 * i)) & 0xFF);
            byte byteValue = convertIntToByte(intValue);
            byteArray[offset + i] = byteValue;
        }
    }


    /**
     * @param byteArray
     * @param offset
     * @return
     */
    public static int getIntegerInByteArrayAtPosition(byte[] byteArray, int offset)
    {
        int intValue = 0;
        for (int i = 0; i < 4; i++) {
            intValue += convertByteToInt(byteArray[offset + i]) << (8 * i);
        }
        
        return intValue;
    }

    /**
     * @param value
     */
    static public byte convertIntToByte(int value)
    {
        return value < 128 ? (byte)value : (byte)(value - 256);
    }

    /**
     * @param value
     */
    static public int convertByteToInt(byte value)
    {
        return value >= 0 ? value : (256 + ((int)value));
    }


    /**
     * @param byteArray
     * @param offset
     * @return
     */
    public static short getShortInByteArrayAtPosition(byte[] byteArray, int offset)
    {
        short shortValue = 0;
        for (int i = 0; i < 2; i++) {
            shortValue += convertByteToInt(byteArray[offset + i]) << (8 * i);
        }
        
        return shortValue;
    }
    
    public static int getUnsignedShortInByteArrayAtPosition(byte[] byteArray, int offset)
    {
        int shortValue = 0;
        for (int i = 0; i < 2; i++) {
            shortValue += convertByteToInt(byteArray[offset + i]) << (8 * i);
        }
        
        return shortValue;
    }

    /**
     * @param value
     * @param byteArray
     * @param offset
     */
    static public void setShortInByteArrayAtPosition(
        short value,
        byte[] byteArray,
        int offset)
    {
        for (int i = 0; i < 2; i++)
        {
            int intValue = (int)((value >> (8 * i)) & 0xFF);
            byte byteValue = convertIntToByte(intValue);
            byteArray[offset + i] = byteValue;
        }
    }

    static public boolean getValueForByteAtBit(byte data, int bitIndex)
    {
        if (bitIndex > 7)  throw new RuntimeException("bitIndex <" + bitIndex + "> out of range.");
        int mask = 1 << bitIndex;
        return (data & mask) == mask;
    }

    static public byte setValueForByteAtBit(boolean value, byte data, int bitIndex)
    {
        if (bitIndex > 7)  throw new RuntimeException("bitIndex <" + bitIndex + "> out of range.");
        if (value)
        {
            return (byte) (data | (1 << bitIndex));
        }
        else
        {
            return (byte) (data & (~(1 << bitIndex)));
        }
    }
    
    public static void main(String args[])
    {
        byte test[] = new byte[8];
        
        long interval = Long.MAX_VALUE / 1600000L;
        
        for (long expected = Long.MAX_VALUE; expected > 0 ; expected -= interval)
        {
            setLongInByteArrayAtPosition(expected, test, 0);
            long actual = getLongInByteArrayAtPosition(test, 0);
            if (expected != actual)
            {
                throw new AssertionError("expected<" + String.valueOf(expected) + ">, actual<" + String.valueOf(actual) + ">");
            }
        }
        for (long expected = Long.MIN_VALUE; expected < 0 ; expected += interval)
        {
            setLongInByteArrayAtPosition(expected, test, 0);
            long actual = getLongInByteArrayAtPosition(test, 0);
            if (expected != actual)
            {
                throw new AssertionError("expected<" + String.valueOf(expected) + ">, actual<" + String.valueOf(actual) + ">");
            }
        }
    }


    /**
     * @param i
     * @param newData
     * @param offset
     */
    public static void setUnsignedShortInByteArrayAtPosition(int i, byte[] newData, int offset)
    {
        // TODO: write this method
        ByteConversions.setShortInByteArrayAtPosition((short)i, newData, offset);
    }


    /**
     * @param data
     * @param offset
     * @return
     */
    public static float getFloatInByteArrayAtPosition(byte[] data, int offset)
    {
        int value = getIntegerInByteArrayAtPosition(data, offset);
        return Float.intBitsToFloat(value);
    }


    /**
     * @param f
     * @param newData
     * @param offset
     */
    public static void setFloatInByteArrayAtPosition(float f, byte[] newData, int offset)
    {
        int value = Float.floatToIntBits(f);
        setIntegerInByteArrayAtPosition(value, newData, offset);
    }

    public static int getIntegerFromDataInByteArrayAtPositionForLength(byte[] byteArray, int offset, int length)
    {
        switch (length)
        {
            case 1:
                return ByteConversions.convertByteToInt(byteArray[offset]);
            case 2:
                return ByteConversions.getUnsignedShortInByteArrayAtPosition(byteArray, offset);
            case 4:
                return ByteConversions.getIntegerInByteArrayAtPosition(byteArray, offset);
            default:
                throw new RuntimeException("Unsupported length of '" + length + "' for getIntegerFromDataInByteArrayAtPositionForLength.");
        }
    }
}