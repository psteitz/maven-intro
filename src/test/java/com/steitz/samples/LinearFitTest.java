package com.steitz.samples;

import java.io.File;
import java.net.URL;

import org.hipparchus.stat.regression.SimpleRegression;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

/**
 * Test cases for LinearFit class.
 */
public class LinearFitTest {

    // Test data and expected results are from 
    // https://www.itl.nist.gov/div898/strd/lls/data/LINKS/DATA/Norris.dat
    private static final String TEST_FILE = "norris.csv";
    private static final double TOL = 1e-12;
    
    @Test
    public void testParameterEstimates() throws Exception {
        final URL url = this.getClass().getResource(TEST_FILE);
        final File file = new File(url.getFile());
        final LinearFit fitter = new LinearFit();
        final SimpleRegression model = fitter.fitModel(file);
        assertEquals(1.00211681802045, model.getSlope(), TOL);
        assertEquals(-0.262323073774029, model.getIntercept(), TOL);
    }

}
