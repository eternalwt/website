package com.greengiant.website.lib;

import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;
import org.junit.jupiter.api.Test;

import java.nio.charset.Charset;

public class BloomFilterTest {

    @Test
    public void testCreateBloomFilter() {
        BloomFilter<String> blackListedIps = BloomFilter.create(Funnels.stringFunnel(Charset.forName("UTF-8")),
                10000);

        // Add the data sets
        blackListedIps.put("192.170.0.1");
        blackListedIps.put("75.245.10.1");
        blackListedIps.put("10.125.22.20");

        // Test the bloom filter
        System.out.println(blackListedIps.mightContain("75.245.10.1"));
        System.out.println(blackListedIps.mightContain("101.125.20.22"));
    }

}
