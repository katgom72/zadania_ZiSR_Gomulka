package com.fss;

import fuzzlib.FuzzySet;
import fuzzlib.creators.OperationCreator;
import fuzzlib.norms.SNorm;
import fuzzlib.norms.TNorm;


public class Zad5_ZiSR {

    public static void main(String[] args) {

        double lightReading = 25.0;
        double motionReading = 0.8;

        System.out.println("Dane z sensorów -> Światło: " + lightReading + "lx, Ruch: " + motionReading);


        FuzzySet fLightLow = new FuzzySet(); fLightLow.newTriangle(0, 45);
        FuzzySet fLightMid = new FuzzySet(); fLightMid.newTriangle(50, 40);
        FuzzySet fLightHigh = new FuzzySet(); fLightHigh.newTriangle(100, 45);

        FuzzySet fMotionNo = new FuzzySet(); fMotionNo.newTriangle(0, 0.8);
        FuzzySet fMotionYes = new FuzzySet(); fMotionYes.newTriangle(1, 0.8);

        FuzzySet outBrightLow = new FuzzySet(); outBrightLow.newTriangle(0, 40);
        FuzzySet outBrightMid = new FuzzySet(); outBrightMid.newTriangle(50, 40);
        FuzzySet outBrightHigh = new FuzzySet(); outBrightHigh.newTriangle(100, 40);

        FuzzySet durShort = new FuzzySet(); durShort.newTriangle(5, 15);
        FuzzySet durMid = new FuzzySet(); durMid.getMembership(30); durMid.newTriangle(45, 25);
        FuzzySet durLong = new FuzzySet(); durLong.newTriangle(90, 40);


        double[] weights = {
                Math.min(fLightLow.getMembership(lightReading), fMotionNo.getMembership(motionReading)),
                Math.min(fLightLow.getMembership(lightReading), fMotionYes.getMembership(motionReading)),
                Math.min(fLightMid.getMembership(lightReading), fMotionNo.getMembership(motionReading)),
                Math.min(fLightMid.getMembership(lightReading), fMotionYes.getMembership(motionReading)),
                Math.min(fLightHigh.getMembership(lightReading), fMotionNo.getMembership(motionReading)),
                Math.min(fLightHigh.getMembership(lightReading), fMotionYes.getMembership(motionReading))
        };

        FuzzySet[] mapBright = { outBrightLow, outBrightHigh, outBrightLow, outBrightMid, outBrightLow, outBrightLow };
        FuzzySet[] mapDur = { durShort, durLong, durShort, durMid, durShort, durShort };


        TNorm tMin = OperationCreator.newTNorm(TNorm.TN_MINIMUM);
        SNorm sMax = (SNorm) OperationCreator.newSNorm(SNorm.SN_MAXIMUM);

        FuzzySet resB = new FuzzySet(); resB.addPoint(0, 0); resB.addPoint(100, 0);
        FuzzySet resD = new FuzzySet(); resD.addPoint(0, 0); resD.addPoint(100, 0);

        for (int i = 0; i < weights.length; i++) {
            if (weights[i] <= 0) continue;

            FuzzySet level = new FuzzySet();
            level.addPoint(0, weights[i]);
            level.addPoint(100, weights[i]);

            FuzzySet tempB = new FuzzySet();
            FuzzySet tempD = new FuzzySet();

            FuzzySet.processSetsWithNorm(tempB, mapBright[i], level, tMin);
            FuzzySet.processSetsWithNorm(tempD, mapDur[i], level, tMin);

            FuzzySet nextB = new FuzzySet();
            FuzzySet nextD = new FuzzySet();
            FuzzySet.processSetsWithNorm(nextB, resB, tempB, sMax);
            FuzzySet.processSetsWithNorm(nextD, resD, tempD, sMax);

            resB = nextB;
            resD = nextD;
        }


        double finalB = resB.DeFuzzyfy();
        double finalD = resD.DeFuzzyfy();

        System.out.println("DECYZJA SYSTEMU:");
        System.out.printf("Moc świecenia: %.1f%%%n", finalB);
        System.out.printf("Czas świecenia: %.1fs%n", finalD);
    }
}