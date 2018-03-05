package com.upseil.gdx.math;

import com.badlogic.gdx.math.Interpolation;
import static com.badlogic.gdx.math.Interpolation.*;

public enum BuiltInInterpolation {
    
    Linear(linear), Smooth(smooth), Smooth2(smooth2), Smoother(smoother), Fade(fade),
    Pow2(pow2), Pow2In(pow2In), Pow2InInverse(pow2InInverse), Pow2Out(pow2Out), Pow2OutInverse(pow2OutInverse),
    Pow3(pow3), Pow3In(pow3In), Pow3InInverse(pow3InInverse), Pow3Out(pow3Out), Pow3OutInverse(pow3OutInverse),
    Pow4(pow4), Pow4In(pow4In), Pow4Out(pow4Out), Pow5(pow5), Pow5In(pow5In), Pow5Out(pow5Out),
    Sine(sine), SineIn(sineIn), SineOut(sineOut),
    Exp10(exp10), Exp10In(exp10In), Exp10Out(exp10Out), Exp5(exp5), Exp5In(exp5In), Exp5Out(exp5Out),
    Circle(circle), CircleIn(circleIn), CircleOut(circleOut), Elastic(elastic), ElasticIn(elasticIn), ElasticOut(elasticOut),
    Swing(swing), SwingIn(swingIn), SwingOut(swingOut), Bounce(bounce), BounceIn(bounceIn), BounceOut(bounceOut);
    
    private final Interpolation interpolation;

    private BuiltInInterpolation(Interpolation interpolation) {
        this.interpolation = interpolation;
    }
    
    public Interpolation get() {
        return interpolation;
    }
    
}
