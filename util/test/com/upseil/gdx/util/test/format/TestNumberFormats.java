package com.upseil.gdx.util.test.format;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.theInstance;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import com.upseil.gdx.util.format.BigNumberFormat;
import com.upseil.gdx.util.format.DoubleFormatter;
import com.upseil.gdx.util.format.PercentFormat;
import com.upseil.gdx.util.format.RomanFormat;
import com.upseil.gdx.util.format.SimpleNumberFormat;

public class TestNumberFormats {
    
    @Test
    public void testSimpleNumberFormat() {
        DoubleFormatter format = SimpleNumberFormat.get(2);
        assertThat(format.apply(1.23456), is("1.23"));
        assertThat(format.apply(1.23700), is("1.24"));
        assertThat(format.apply(7.00000), is("7"));
        
        format = SimpleNumberFormat.get(5);
        assertThat(format.apply(1.23456), is("1.23456"));
        assertThat(format.apply(1.23700), is("1.237"));
        
        format = SimpleNumberFormat.get(0);
        assertThat(format.apply(1.23456), is("1"));
        assertThat(format.apply(1.56789), is("2"));

        SimpleNumberFormat.get(1);
        SimpleNumberFormat.get(3);
        assertThat(SimpleNumberFormat.get(0), is(theInstance(format)));
    }
    
    @Test
    public void testPercentFormat() {
        DoubleFormatter format = PercentFormat.get(2);
        assertThat(format.apply(1.23456), is("123.46%"));
        assertThat(format.apply(1.23700), is("123.7%"));
        assertThat(format.apply(7.00000), is("700%"));
        
        format = PercentFormat.get(0);
        assertThat(format.apply(1.23456), is("123%"));
        assertThat(format.apply(1.56789), is("157%"));

        PercentFormat.get(1);
        PercentFormat.get(3);
        assertThat(PercentFormat.get(0), is(theInstance(format)));
    }
    
    @Test
    public void testRomanFormat() {
        DoubleFormatter format = new RomanFormat();

        assertThat(format.apply(0), is("N"));
        assertThat(format.apply(-0), is("N"));
        assertThat(format.apply(0.9), is("N"));
        assertThat(format.apply(-0.2), is("N"));

        assertThat(format.apply(3), is("III"));
        assertThat(format.apply(4), is("IV"));
        assertThat(format.apply(6), is("VI"));
        assertThat(format.apply(9), is("IX"));

        assertThat(format.apply(30), is("XXX"));
        assertThat(format.apply(40), is("XL"));
        assertThat(format.apply(70), is("LXX"));
        assertThat(format.apply(90), is("XC"));

        assertThat(format.apply(200), is("CC"));
        assertThat(format.apply(400), is("CD"));
        assertThat(format.apply(800), is("DCCC"));
        assertThat(format.apply(900), is("CM"));

        assertThat(format.apply(1000), is("M"));
        assertThat(format.apply(3000), is("MMM"));
        

        assertThat(format.apply(27), is("XXVII"));
        assertThat(format.apply(444), is("CDXLIV"));
        assertThat(format.apply(873), is("DCCCLXXIII"));
        assertThat(format.apply(10722), is("MMMMMMMMMMDCCXXII"));

        assertThat(format.apply(-3), is("-III"));
        assertThat(format.apply(-4), is("-IV"));
        assertThat(format.apply(-6), is("-VI"));
        assertThat(format.apply(-9), is("-IX"));

        assertThat(format.apply(3.9), is("III"));
        assertThat(format.apply(4.2), is("IV"));
        assertThat(format.apply(6.9), is("VI"));
        assertThat(format.apply(9.2), is("IX"));
    }
    
    @Test
    public void testAbbreviationFormat() {
        DoubleFormatter format = BigNumberFormat.Abbreviation;

        assertThat(format.apply(Double.POSITIVE_INFINITY), is("\u221e"));
        assertThat(format.apply(Double.NEGATIVE_INFINITY), is("-\u221e"));

        assertThat(format.apply(0.225), is("0.23"));
        assertThat(format.apply(1.225), is("1.23"));
        assertThat(format.apply(10.225), is("10.2"));
        assertThat(format.apply(100.225), is("100"));
        assertThat(format.apply(100.525), is("101"));
        assertThat(format.apply(1000.225), is("1000"));
        assertThat(format.apply(9000.225), is("9000"));

        assertThat(format.apply(10000.225), is("10 K"));
        assertThat(format.apply(10225.225), is("10.2 K"));
        assertThat(format.apply(10255.225), is("10.3 K"));
        assertThat(format.apply(915225.225), is("915 K"));
        assertThat(format.apply(1010225.225), is("1.01 M"));
        assertThat(format.apply(1015225.225), is("1.02 M"));
        assertThat(format.apply(2519000), is("2.52 M"));
    }
    
    @Test
    public void testEngineeringFormat() {
        DoubleFormatter format = BigNumberFormat.Engineering;

        assertThat(format.apply(Double.POSITIVE_INFINITY), is("\u221e"));
        assertThat(format.apply(Double.NEGATIVE_INFINITY), is("-\u221e"));

        assertThat(format.apply(0.225), is("0.23"));
        assertThat(format.apply(1.225), is("1.23"));
        assertThat(format.apply(10.225), is("10.2"));
        assertThat(format.apply(100.225), is("100"));
        assertThat(format.apply(100.525), is("101"));
        assertThat(format.apply(1000.225), is("1000"));
        assertThat(format.apply(9000.225), is("9000"));

        assertThat(format.apply(10000.225), is("10e3"));
        assertThat(format.apply(10225.225), is("10.2e3"));
        assertThat(format.apply(10255.225), is("10.3e3"));
        assertThat(format.apply(915225.225), is("915e3"));
        assertThat(format.apply(1010225.225), is("1.01e6"));
        assertThat(format.apply(1015225.225), is("1.02e6"));
        assertThat(format.apply(2519000), is("2.52e6"));
    }
    
}
