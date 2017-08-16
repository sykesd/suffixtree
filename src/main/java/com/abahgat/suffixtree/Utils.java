/**
 * Copyright 2012 Alessandro Bahgat Shehata
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.abahgat.suffixtree;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class Utils {

    /**
     * Get the last code point in the given {@link String}.
     * <p>
     *     This method will most likely throw an exception if an empty string
     *     is passed in.
     * </p>
     *
     * @param in the {@link String} from which we want the last code point
     * @return the last code point in the given {@link String}
     */
    public static int lastCodePoint(String in) {
        if (in.length() == 1) {
            return in.codePointAt(0);
        }

        char checkCh = in.charAt(in.length() - 2);
        if (Character.isHighSurrogate(checkCh)) {
            return Character.toCodePoint(checkCh, in.charAt(in.length() - 1));
        }

        return in.codePointAt(in.length() - 1);
    }

    /**
     * Remove the last code point from the given {@link String} and return the prefix.
     * <p>
     *     If an empty string is passed, or <code>null</code>, or a string of length 1
     *     then this method will return the empty string.
     * </p>
     *
     * @param in the {@link String} to remove the last code point from
     * @return a new {@link String} which is the input string with the last code point removed
     */
    public static String removeLastCodePoint(String in) {
        if (in == null || in.length() < 2) {
            return "";
        }

        char checkCh = in.charAt(in.length() - 2);
        if (Character.isHighSurrogate(checkCh)) {
            return in.substring(0, in.length() - 2);
        }

        return in.substring(0, in.length() - 1);
    }


    /**
     * Remove the last code point from the given {@link String} and return the prefix.
     * <p>
     *     This is the equivalent of calling <code>in.substring(1)</code> if we did not
     *     care about Unicode characters that are encoded using surrogate pairs.
     * </p>
     * <p>
     *     If an empty string is passed, or <code>null</code>, or a string of length 1
     *     then this method will return the empty string.
     * </p>
     *
     * @param in the {@link String} to remove the first code point from
     * @return a new {@link String} which is the input string with the first code point removed
     */
    public static String removeFirstCodePoint(String in) {
        if (in == null || in.length() < 2) {
            return "";
        }

        char checkCh = in.charAt(0);
        if (Character.isHighSurrogate(checkCh)) {
            return in.substring(2);
        }

        return in.substring(1);
    }

    /**
     * Normalize an input string
     * 
     * @param in the input string to normalize
     * @return <tt>in</tt> all lower-case, without any non alphanumeric character
     */
    public static String normalize(String in) {
        StringBuilder out = new StringBuilder();
        String l = in.toLowerCase();
        for (int i = 0; i < l.length(); ++i) {
            char c = l.charAt(i);
            if (c >= 'a' && c <= 'z' || c >= '0' && c <= '9') {
                out.append(c);
            }
        }
        return out.toString();
    }

    /**
     * Computes the set of all the substrings contained within the <tt>str</tt>
     * 
     * It is fairly inefficient, but it is used just in tests ;)
     * @param str the string to compute substrings of
     * @return the set of all possible substrings of str
     */
    public static Set<String> getSubstrings(String str) {
        Set<String> ret = new HashSet<String>();
        if (str.length() == 0) {
            return Collections.emptySet();
        }

        int[] codePoints = str.codePoints().toArray();

        // compute all substrings
        for (int len = 1; len <= codePoints.length; ++len) {
            for (int start = 0; start + len <= codePoints.length; ++start) {
                String itstr = codePointSubstring(codePoints, start, start+len);
                ret.add(itstr);
            }
        }

        return ret;
    }

    private static String codePointSubstring(int[] codePoints, int startIndex, int endIndex) {
        StringBuilder s = new StringBuilder();
        for (int index = startIndex; index < endIndex; index++) {
            s.appendCodePoint(codePoints[index]);
        }
        return s.toString();
    }

}
