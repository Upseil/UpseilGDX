package com.upseil.gdx.util.format;

public abstract class BigNumberFormat implements DoubleFormatter {
    
    public static final BigNumberFormat Abbreviation = new AbbreviationFormat();
    public static final BigNumberFormat Engineering = new EngineeringFormat();
    
    private static final String infinity = "\u221e";
    private static final double log1000 = Math.log(1000);
    
    @Override
    public String apply(double value) {
        if (Double.isInfinite(value)) {
            return infinity;
        }
        if (value < 0) {
            return "-" + apply(-1 * value);
        }
        if (value < 10000) {
            return formatNumber(value);
        }
        
        int base = (int) (Math.log(value) / log1000);
        value /= Math.pow(1000, base);
        return formatNumber(value) + getSuffix(base);
    }
    
    private String formatNumber(double value) {
        if (value >= 100) {
            return (int) value + "";
        }
        
        double precisionFactor = 10 * (value < 10 ? 10 : 1);
        double precisionValue = Math.round(value * precisionFactor) / precisionFactor;
        if (precisionValue == Math.floor(precisionValue)) {
            return (int) precisionValue + "";
        } else {
            return precisionValue + "";
        }
    }
    
    protected abstract String getSuffix(int base1000);
    
    private static class AbbreviationFormat extends BigNumberFormat {
        
        private final static String[] suffices = { // Sufficient till 999e93
                " K", " M", " B", " T", " Qa", " Qi", " Sx", " Sp", " Oc", " No", " Dc", " Ud", " Dd", " Td", " Qad", " Qid", " Sxd", " Spd", " Od", " Nd",
                " V", " Uv", " Dv", " Tv", " Qav", " Qiv", " Sxv", " Spv", " Ov", " Nv", " Tt" };
        
        @Override
        protected String getSuffix(int base1000) {
            int suffixIndex = base1000 - 1;
            if (suffixIndex >= suffices.length) {
                return BigNumberFormat.Engineering.getSuffix(base1000);
            }
            return suffices[suffixIndex];
        }
        
    }
    
    private static class EngineeringFormat extends BigNumberFormat {
        
        @Override
        protected String getSuffix(int base1000) {
            return "e" + (base1000 * 3);
        }
        
    }
    
}
