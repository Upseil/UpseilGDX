package com.upseil.gdx.util.properties;

public abstract class AbstractProperties<K> implements Properties<K> {
    
    private boolean strict;
    
    public AbstractProperties() {
        this(false);
    }

    public AbstractProperties(boolean strict) {
        this.strict = strict;
    }

    @Override
    public boolean isStrict() {
        return strict;
    }
    
    protected void setStrict(boolean strict) {
        this.strict = strict;
    }
    
}
