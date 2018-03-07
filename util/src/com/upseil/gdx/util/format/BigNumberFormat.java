package com.upseil.gdx.util.format;

public abstract class BigNumberFormat implements DoubleFormatter {
    
    public static final BigNumberFormat Abbreviation = new AbbreviationFormat();
    public static final BigNumberFormat Engineering = new EngineeringFormat();

    private static final StringBuilder textBuilder = new StringBuilder(8);
    private static final String infinity = "\u221e";
    private static final double log1000 = Math.log(1000);
    
    @Override
    public String apply(double value) {
        textBuilder.setLength(0);
        if (value < 0) {
            textBuilder.append("-");
            value = -1 * value;
        }
        
        if (Double.isInfinite(value)) {
            textBuilder.append(infinity);
        } else {
            if (value < 10000) {
                textBuilder.append(formatNumber(value));
            } else {
                int base = (int) (Math.log(value) / log1000);
                value /= Math.pow(1000, base);
                textBuilder.append(formatNumber(value));
                appendSuffix(base, textBuilder);
            }
        }
        return textBuilder.toString();
    }
    
    private String formatNumber(double value) {
        if (value >= 100) {
            return Integer.toString((int) Math.round(value));
        }
        
        double precisionFactor = 10 * (value < 10 ? 10 : 1);
        double precisionValue = Math.round(value * precisionFactor) / precisionFactor;
        if (precisionValue == Math.floor(precisionValue)) {
            return Integer.toString((int) precisionValue);
        } else {
            return Double.toString(precisionValue);
        }
    }
    
    protected abstract void appendSuffix(int base1000, StringBuilder builder);
    
    private static class AbbreviationFormat extends BigNumberFormat {
        
        private final static String[] suffices = { // Sufficient till 999e93
                " K", " M", " B", " T", " Qa", " Qi", " Sx", " Sp", " Oc", " No", " Dc", " Ud", " Dd", " Td", " Qad", " Qid", " Sxd", " Spd", " Od", " Nd",
                " V", " Uv", " Dv", " Tv", " Qav", " Qiv", " Sxv", " Spv", " Ov", " Nv", " Tt" };
        
        @Override
        protected void appendSuffix(int base1000, StringBuilder builder) {
            int suffixIndex = base1000 - 1;
            if (suffixIndex >= suffices.length) {
                Engineering.appendSuffix(base1000, builder);
            }
            builder.append(suffices[suffixIndex]);
        }
        
    }
    
    private static class EngineeringFormat extends BigNumberFormat {
        
        private static final String exponent = "e";
        
        @Override
        protected void appendSuffix(int base1000, StringBuilder builder) {
            builder.append(exponent).append(base1000 * 3);
        }
        
    }
    
}
