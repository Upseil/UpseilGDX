package com.upseil.gdx.tools;

import java.util.Locale;

public class PseudoRandomDistributionCalculator {
    
    private static final boolean verbose = false;
    
    private static final int probabilityDecimals = 3;
    private static final int testIterations = 100000;
    
    public static void main(String[] args) {
        PseudoRandomDistributionCalculator calculator = new PseudoRandomDistributionCalculator();
        
        System.out.println("Calculating probability constants: ");
        if (verbose) {
            System.out.println("Desired Probability -> Probability Constant (Tested Probability)");
        }
        
        StringBuilder arrayOutput = new StringBuilder("{");
        double iMax = Math.pow(10, probabilityDecimals);
        for (int i = 1; i < iMax; i++) {
            double p = i / iMax;
            double c = calculator.cFromP(p);
            arrayOutput.append(i > 1 ? ", " : "").append(c);
            
            if (verbose) {
                System.out.println(String.format(Locale.ENGLISH, "%." + probabilityDecimals + "f -> %.10f (%f)", p, c, testC(c)));
            } else {
                System.out.print(".");
            }
        }
        arrayOutput.append("}");
        
        System.out.println();
        System.out.println("Probability Constants formatted as double array:");
        System.out.println(arrayOutput);
    }
    
    public double cFromP(double p) {
        double cMid, p1, p2 = 1;
        double cUpper = p;
        double cLower = 0;
        
        while (true) {
            cMid = (cUpper + cLower) * 0.5;
            p1 = pFromC(cMid);
            
            if (Math.abs(p1 - p2) <= 0) {
                break;
            }
            
            if (p1 > p) {
                cUpper = cMid;
            } else {
                cLower = cMid;
            }
            
            p2 = p1;
        }
        
        return cMid;
    }
    
    private double pFromC(double c) {
        double pProcOnN = 0;
        double pProcByN = 0;
        double sumNpProcOnN = 0;
        
        int maxFails = (int) Math.ceil(1.0 / c);
        for (int n = 1; n <= maxFails; ++n) {
            pProcOnN = Math.min(1, n * c) * (1 - pProcByN);
            pProcByN += pProcOnN;
            sumNpProcOnN += n * pProcOnN;
        }
        
        return 1.0 / sumNpProcOnN;
    }
    
    private static double testC(double c) {
        double nSum = 0;
        
        for (int i = 0; i < testIterations; i++) {
            int n = 1;
            
            while (Math.random() > (c * n)) {
                n++;
            }
            
            nSum += n;
        }
        
        return (100.0 / (nSum / testIterations)) / 100.0;
    }
    
}
