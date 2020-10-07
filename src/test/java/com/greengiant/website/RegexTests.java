package com.greengiant.website;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import java.util.regex.Pattern;

public class RegexTests {

    @Test
    public void testMeta1() {
//        Assertions.assertTrue(Pattern.matches("...om", "Tom"));
        Assertions.assertTrue(Pattern.matches("...om", "TTTom"));
        Assertions.assertTrue(Pattern.matches("s+", "sss"));
        Assertions.assertTrue(Pattern.matches("s+a", "sssa"));
        Assertions.assertTrue(Pattern.matches("ab[^pqr]", "abd"));
        Assertions.assertTrue(Pattern.matches("this\\s+is\\s+text", "this  is   text"));
        Assertions.assertTrue(Pattern.matches("A(?:nt|pple)", "Ant"));
        Assertions.assertTrue(Pattern.matches("^[\\d].*", "1abc"));
        Assertions.assertTrue(Pattern.matches("^[^\\d].*", "abc123"));

    }

}
