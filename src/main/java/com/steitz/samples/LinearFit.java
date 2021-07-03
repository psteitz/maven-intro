package com.steitz.samples;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.hipparchus.stat.regression.SimpleRegression;

public class LinearFit {

    public static void main(String[] args) {
        if (args.length > 0) {
            // first command line argument is full path to the input file
            SimpleRegression model = null;
            final String filename = args[0];
            final File file = new File(filename);
            try {
                model = new LinearFit().fitModel(file);
            } catch (IOException ex) {
                System.err
                    .println("Error opening or parsing data from " + filename);
            }
            if (model == null || Double.isNaN(model.getSlope())) {
                System.err
                    .println("Error estimating the model from " + filename);
            } else {
                showResults(model);
            }
        } else {
            System.out.println("Please provide the input file name");
        }
    }

    public SimpleRegression fitModel(File dataFile)
        throws IOException {
        final Reader in = new FileReader(dataFile);
        final SimpleRegression model = new SimpleRegression();
        try {
            final Iterable<CSVRecord> records = CSVFormat.EXCEL.withHeader()
                .parse(in);
            for (CSVRecord record : records) {
                model.addData(Double.valueOf(record.get(1)),
                              Double.valueOf(record.get(0)));
            }
        } finally {
            in.close();
        }
        return model;
    }

    private static void showResults(SimpleRegression model) {
        if (model == null) {
            System.err.println("Null results.");
        } else {
            final StringBuilder buff = new StringBuilder("Regression Results \n");
            buff.append("Number of observations: ")
            .append(model.getN())
            .append('\n')
            .append("Estimated model y = b_0 + b_1x")
            .append("b0 = ")
            .append(model.getIntercept())
            .append('\n')
            .append("b1: ")
            .append(model.getSlope())
            .append('\n')
            .append("b_0 standard error: ")
            .append(model.getInterceptStdErr())
            .append('\n')
            .append("b_1 standard error: ")
            .append(model.getSlopeStdErr())
            .append("RSquare: ")
            .append(model.getRSquare())
            .append('\n');
            System.out.println(buff);
        }
    }

}
