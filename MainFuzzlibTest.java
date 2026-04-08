package com.fss;

import fuzzlib.FuzzySet;
import fuzzlib.creators.OperationCreator;
import fuzzlib.norms.Norm;
import fuzzlib.norms.TNMin;
import fuzzlib.norms.TNorm;

public class MainFuzzlibTest {

    public static void main(String[] args) {

        FuzzySet fs = new FuzzySet();
        FuzzySet fs2 = new FuzzySet();
        FuzzySet fs3 = new FuzzySet();
        FuzzySet fs4 = new FuzzySet();
        FuzzySet fs5 = new FuzzySet();

        fs.addPoint(-2.0, 0.0);
        fs.addPoint(0.0, 1.0);
        fs.addPoint(2.0, 0.0);

        fs2.newTriangle(0.0, 1.0);
        fs3.newGaussian(0.0,3.0);
        fs5.newGaussian(0.0,1.0);


        fs.fuzzyfy(-1);
        fs3.fuzzyfy(-2.6);

        System.out.println(fs);
        System.out.println(fs2);

        Norm op = OperationCreator.newTNorm(TNorm.TN_MINIMUM);


        FuzzySet.processSetsWithNorm(fs4, fs, fs2, op);

        System.out.println(fs4);



    }
}
