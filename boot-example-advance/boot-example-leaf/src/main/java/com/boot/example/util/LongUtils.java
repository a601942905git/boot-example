package com.boot.example.util;

/**
 * com.boot.example.util.LongUtils
 *
 * @author lipeng
 * @date 2021/12/4 2:31 PM
 */
public class LongUtils {

    final static char[] DIGITS = {
            's' , 'k' , '2' , '3' , 'y' , '5' ,
            'r' , '7' , 'p' , 'f' , 'l' , 'b' ,
            'c' , 'd' , 'e' , '9' , 'g' , 'h' ,
            'i' , 'z' , '1' , 'a' , 'm' , 'x' ,
            'o' , '8' , 'q' , '6' , '0' , 't' ,
            'u' , '4' , 'j' , 'n' , 'v' , 'w'
    };

    public static String toString(long i, int radix) {
        if (radix < Character.MIN_RADIX || radix > Character.MAX_RADIX) {
            radix = 10;
        }
        if (radix == 10) {
            return Long.toString(i);
        }
        char[] buf = new char[65];
        int charPos = 64;
        boolean negative = (i < 0);

        if (!negative) {
            i = -i;
        }

        while (i <= -radix) {
            buf[charPos--] = DIGITS[(int)(-(i % radix))];
            i = i / radix;
        }
        buf[charPos] = DIGITS[(int)(-i)];

        if (negative) {
            buf[--charPos] = '-';
        }

        return new String(buf, charPos, (65 - charPos));
    }
}
