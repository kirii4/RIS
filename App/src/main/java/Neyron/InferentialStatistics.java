
package Neyron;

import org.apache.commons.math3.stat.correlation.Covariance;
import org.apache.commons.math3.stat.correlation.PearsonsCorrelation;
import org.apache.commons.math3.stat.correlation.SpearmansCorrelation;

public class InferentialStatistics {

    public static void analyze(double[] x, double[] y){
        Covariance covariance = new Covariance();
        PearsonsCorrelation pearsonsCorrelation = new PearsonsCorrelation();
        SpearmansCorrelation spearmansCorrelation = new SpearmansCorrelation();
        
        double covarianceDbl = covariance.covariance(x, y);
        double pearsonsCorrelationDbl = pearsonsCorrelation.correlation(x, y);
        double spearmansCorrelationDbl = spearmansCorrelation.correlation(x, y);
        
        System.out.println("Inferential Statistics Results: \n");
        System.out.println("\nCovariance: " + covarianceDbl + 
                            "\nPearsonsCorrelation: " + pearsonsCorrelationDbl +
                            "\nSpearmansCorrelation: " + spearmansCorrelationDbl);
    }
}
