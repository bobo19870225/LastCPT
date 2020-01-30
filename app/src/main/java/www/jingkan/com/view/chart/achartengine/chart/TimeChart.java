//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package www.jingkan.com.view.chart.achartengine.chart;

import android.graphics.Canvas;
import android.graphics.Paint;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import www.jingkan.com.view.chart.achartengine.model.XYMultipleSeriesDataset;
import www.jingkan.com.view.chart.achartengine.model.XYSeries;
import www.jingkan.com.view.chart.achartengine.renderer.XYMultipleSeriesRenderer;

public class TimeChart extends LineChart {
    public static final String TYPE = "Time";
    public static final long DAY = 86400000L;
    private String mDateFormat;
    private Double mStartPoint;

    TimeChart() {
    }

    public TimeChart(XYMultipleSeriesDataset dataset, XYMultipleSeriesRenderer renderer) {
        super(dataset, renderer);
    }

    public String getDateFormat() {
        return this.mDateFormat;
    }

    public void setDateFormat(String format) {
        this.mDateFormat = format;
    }

    protected void drawXLabels(List<Double> xLabels, Double[] xTextLabelLocations, Canvas canvas, Paint paint, int left, int top, int bottom, double xPixelsPerUnit, double minX, double maxX) {
        int length = xLabels.size();
        if (length > 0) {
            boolean showLabels = this.mRenderer.isShowLabels();
            boolean showGridY = this.mRenderer.isShowGridY();
            boolean showTickMarks = this.mRenderer.isShowTickMarks();
            DateFormat format = this.getDateFormat((Double) xLabels.get(0), (Double) xLabels.get(length - 1));

            for (int i = 0; i < length; ++i) {
                long label = Math.round((Double) xLabels.get(i));
                float xLabel = (float) ((double) left + xPixelsPerUnit * ((double) label - minX));
                if (showLabels) {
                    paint.setColor(this.mRenderer.getXLabelsColor());
                    if (showTickMarks) {
                        canvas.drawLine(xLabel, (float) bottom, xLabel, (float) bottom + this.mRenderer.getLabelsTextSize() / 3.0F, paint);
                    }

                    this.drawText(canvas, format.format(new Date(label)), xLabel, (float) bottom + this.mRenderer.getLabelsTextSize() * 4.0F / 3.0F + this.mRenderer.getXLabelsPadding(), paint, this.mRenderer.getXLabelsAngle());
                }

                if (showGridY) {
                    paint.setColor(this.mRenderer.getGridColor(0));
                    canvas.drawLine(xLabel, (float) bottom, xLabel, (float) top, paint);
                }
            }
        }

        this.drawXTextLabels(xTextLabelLocations, canvas, paint, true, left, top, bottom, xPixelsPerUnit, minX, maxX);
    }

    private DateFormat getDateFormat(double start, double end) {
        DateFormat format;
        if (this.mDateFormat != null) {

            try {
                format = new SimpleDateFormat(this.mDateFormat);
                return format;
            } catch (Exception var8) {
            }
        }

        format = SimpleDateFormat.getDateInstance(2);
        double diff = end - start;
        if (diff > 8.64E7D && diff < 4.32E8D) {
            format = SimpleDateFormat.getDateTimeInstance(3, 3);
        } else if (diff < 8.64E7D) {
            format = SimpleDateFormat.getTimeInstance(2);
        }

        return format;
    }

    public String getChartType() {
        return "Time";
    }

    protected List<Double> getXLabels(double min, double max, int count) {
        List<Double> result = new ArrayList();
        int i;
        if (!this.mRenderer.isXRoundedLabels()) {
            if (this.mDataset.getSeriesCount() <= 0) {
                return super.getXLabels(min, max, count);
            } else {
                XYSeries series = this.mDataset.getSeriesAt(0);
                int length = series.getItemCount();
                int intervalLength = 0;
                int startIndex = -1;


                for (i = 0; i < length; ++i) {
                    double value = series.getX(i);
                    if (min <= value && value <= max) {
                        ++intervalLength;
                        if (startIndex < 0) {
                            startIndex = i;
                        }
                    }
                }

                if (intervalLength < count) {
                    for (i = startIndex; i < startIndex + intervalLength; ++i) {
                        result.add(series.getX(i));
                    }
                } else {
                    float step = (float) intervalLength / (float) count;
                    int intervalCount = 0;

                    for (i = 0; i < length && intervalCount < count; ++i) {
                        double value = series.getX(Math.round((float) i * step));
                        if (min <= value && value <= max) {
                            result.add(value);
                            ++intervalCount;
                        }
                    }
                }

                return result;
            }
        } else {
            if (this.mStartPoint == null) {
                this.mStartPoint = min - min % 8.64E7D + 8.64E7D + (double) ((new Date(Math.round(min))).getTimezoneOffset() * 60 * 1000);
            }

            if (count > 25) {
                count = 25;
            }

            double cycleMath = (max - min) / (double) count;
            if (cycleMath <= 0.0D) {
                return result;
            } else {
                double cycle = 8.64E7D;
                if (cycleMath <= 8.64E7D) {
                    while (cycleMath < cycle / 2.0D) {
                        cycle /= 2.0D;
                    }
                } else {
                    while (cycleMath > cycle) {
                        cycle *= 2.0D;
                    }
                }

                double val = this.mStartPoint - Math.floor((this.mStartPoint - min) / cycle) * cycle;

                for (i = 0; val < max && i++ <= count; val += cycle) {
                    result.add(val);
                }

                return result;
            }
        }
    }
}
