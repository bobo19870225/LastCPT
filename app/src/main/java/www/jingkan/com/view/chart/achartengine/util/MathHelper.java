//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package www.jingkan.com.view.chart.achartengine.util;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class MathHelper {
    public static final double NULL_VALUE = 1.7976931348623157E308D;
    private static final NumberFormat FORMAT = NumberFormat.getNumberInstance();

    private MathHelper() {
    }

    public static double[] minmax(List<Double> values) {
        if (values.size() == 0) {
            return new double[2];
        } else {
            double min = (Double) values.get(0);
            double max = min;
            int length = values.size();

            for (int i = 1; i < length; ++i) {
                double value = (Double) values.get(i);
                min = Math.min(min, value);
                max = Math.max(max, value);
            }

            return new double[]{min, max};
        }
    }

    public static List<Double> getLabels(double start, double end, int approxNumLabels) {
        List<Double> labels = new ArrayList();
        if (approxNumLabels <= 0) {
            return labels;
        } else {
            FORMAT.setMaximumFractionDigits(5);
            double[] labelParams = computeLabels(start, end, approxNumLabels);
            int numLabels = 1 + (int) ((labelParams[1] - labelParams[0]) / labelParams[2]);

            for (int i = 0; i < numLabels; ++i) {
                double z = labelParams[0] + (double) i * labelParams[2];

                try {
                    z = FORMAT.parse(FORMAT.format(z)).doubleValue();
                } catch (ParseException var12) {
                }

                labels.add(z);
            }

            return labels;
        }
    }

    private static double[] computeLabels(double start, double end, int approxNumLabels) {
        if (Math.abs(start - end) < 1.0000000116860974E-7D) {
            return new double[]{start, start, 0.0D};
        } else {
            double s = start;
            double e = end;
            boolean switched = false;
            if (start > end) {
                switched = true;
                s = end;
                e = start;
            }

            double xStep = roundUp(Math.abs(s - e) / (double) approxNumLabels);
            double xStart = xStep * Math.ceil(s / xStep);
            double xEnd = xStep * Math.floor(e / xStep);
            return switched ? new double[]{xEnd, xStart, -1.0D * xStep} : new double[]{xStart, xEnd, xStep};
        }
    }

    private static double roundUp(double val) {
        int exponent = (int) Math.floor(Math.log10(val));
        double rval = val * Math.pow(10.0D, (double) (-exponent));
        if (rval > 5.0D) {
            rval = 10.0D;
        } else if (rval > 2.0D) {
            rval = 5.0D;
        } else if (rval > 1.0D) {
            rval = 2.0D;
        }

        rval *= Math.pow(10.0D, (double) exponent);
        return rval;
    }
}
