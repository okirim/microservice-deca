package com.zema.commons.validations;

public class ValidationUtils {
    private static final int MAX_LONG_STR_LEN = Long.toString(Long.MAX_VALUE).length();

    public static boolean validId(final CharSequence id)
    {
        //avoid null
        if (id == null)
        {
            return false;
        }

        int len = id.length();

        //avoid empty or oversize
        if (len < 1 || len > MAX_LONG_STR_LEN)
        {
            return false;
        }

        long result = 0;
        // ASCII '0' at position 48
        int digit = id.charAt(0) - 48;

        //first char cannot be '0' in my "id" case
        if (digit < 1 || digit > 9)
        {
            return false;
        }
        else
        {
            result += digit;
        }

        //start from 1, we already did the 0.
        for (int i = 1; i < len; i++)
        {
            // ASCII '0' at position 48
            digit = id.charAt(i) - 48;

            //only numbers
            if (digit < 0 || digit > 9)
            {
                return false;
            }

            result *= 10;
            result += digit;

            //if we hit 0x7fffffffffffffff
            // we are at 0x8000000000000000 + digit - 1
            // so negative
            if (result < 0)
            {
                //overflow
                return false;
            }
        }

        return true;
    }
}
