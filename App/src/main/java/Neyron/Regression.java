
package Neyron;

import org.apache.commons.math3.stat.regression.SimpleRegression;

public class Regression {

    public static void regression (double[] x, double[] y){
        SimpleRegression simpleRegression = new SimpleRegression();
        for (int i =0; i < x.length; i++){
            simpleRegression.addData(x[i], y[i]);
        }
        
        double yIntercept = simpleRegression.getIntercept();
        double slope = simpleRegression.getSlope();
        double rSquare = simpleRegression.getRSquare();
        System.out.println("Regression: \n" + 
                            "Y-Intercep: " + yIntercept + 
                            "\nSlope: " + slope + 
                            "\nRSquare: " + rSquare);
    }
}
