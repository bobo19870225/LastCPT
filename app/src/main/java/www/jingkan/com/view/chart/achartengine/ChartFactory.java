//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package www.jingkan.com.view.chart.achartengine;

import android.content.Context;
import android.content.Intent;

import www.jingkan.com.view.chart.achartengine.chart.BarChart;
import www.jingkan.com.view.chart.achartengine.chart.BubbleChart;
import www.jingkan.com.view.chart.achartengine.chart.CombinedXYChart;
import www.jingkan.com.view.chart.achartengine.chart.CubicLineChart;
import www.jingkan.com.view.chart.achartengine.chart.DialChart;
import www.jingkan.com.view.chart.achartengine.chart.DoughnutChart;
import www.jingkan.com.view.chart.achartengine.chart.LineChart;
import www.jingkan.com.view.chart.achartengine.chart.PieChart;
import www.jingkan.com.view.chart.achartengine.chart.RangeBarChart;
import www.jingkan.com.view.chart.achartengine.chart.ScatterChart;
import www.jingkan.com.view.chart.achartengine.chart.TimeChart;
import www.jingkan.com.view.chart.achartengine.chart.XYChart;
import www.jingkan.com.view.chart.achartengine.model.CategorySeries;
import www.jingkan.com.view.chart.achartengine.model.MultipleCategorySeries;
import www.jingkan.com.view.chart.achartengine.model.XYMultipleSeriesDataset;
import www.jingkan.com.view.chart.achartengine.renderer.DefaultRenderer;
import www.jingkan.com.view.chart.achartengine.renderer.DialRenderer;
import www.jingkan.com.view.chart.achartengine.renderer.XYMultipleSeriesRenderer;

public class ChartFactory {
    public static final String CHART = "chart";
    public static final String TITLE = "title";

    private ChartFactory() {
    }

    public static final GraphicalView getLineChartView(Context context, XYMultipleSeriesDataset dataset, XYMultipleSeriesRenderer renderer) {
        checkParameters(dataset, renderer);
        XYChart chart = new LineChart(dataset, renderer);
        return new GraphicalView(context, chart);
    }

    public static final GraphicalView getCubeLineChartView(Context context, XYMultipleSeriesDataset dataset, XYMultipleSeriesRenderer renderer, float smoothness) {
        checkParameters(dataset, renderer);
        XYChart chart = new CubicLineChart(dataset, renderer, smoothness);
        return new GraphicalView(context, chart);
    }

    public static final GraphicalView getScatterChartView(Context context, XYMultipleSeriesDataset dataset, XYMultipleSeriesRenderer renderer) {
        checkParameters(dataset, renderer);
        XYChart chart = new ScatterChart(dataset, renderer);
        return new GraphicalView(context, chart);
    }

    public static final GraphicalView getBubbleChartView(Context context, XYMultipleSeriesDataset dataset, XYMultipleSeriesRenderer renderer) {
        checkParameters(dataset, renderer);
        XYChart chart = new BubbleChart(dataset, renderer);
        return new GraphicalView(context, chart);
    }

    public static final GraphicalView getTimeChartView(Context context, XYMultipleSeriesDataset dataset, XYMultipleSeriesRenderer renderer, String format) {
        checkParameters(dataset, renderer);
        TimeChart chart = new TimeChart(dataset, renderer);
        chart.setDateFormat(format);
        return new GraphicalView(context, chart);
    }

    public static final GraphicalView getBarChartView(Context context, XYMultipleSeriesDataset dataset, XYMultipleSeriesRenderer renderer, BarChart.Type type) {
        checkParameters(dataset, renderer);
        XYChart chart = new BarChart(dataset, renderer, type);
        return new GraphicalView(context, chart);
    }

    public static final GraphicalView getRangeBarChartView(Context context, XYMultipleSeriesDataset dataset, XYMultipleSeriesRenderer renderer, BarChart.Type type) {
        checkParameters(dataset, renderer);
        XYChart chart = new RangeBarChart(dataset, renderer, type);
        return new GraphicalView(context, chart);
    }

    public static final GraphicalView getCombinedXYChartView(Context context, XYMultipleSeriesDataset dataset, XYMultipleSeriesRenderer renderer, CombinedXYChart.XYCombinedChartDef[] types) {
        checkParameters(dataset, renderer);
        CombinedXYChart chart = new CombinedXYChart(dataset, renderer, types);
        return new GraphicalView(context, chart);
    }

    public static final GraphicalView getPieChartView(Context context, CategorySeries dataset, DefaultRenderer renderer) {
        checkParameters(dataset, renderer);
        PieChart chart = new PieChart(dataset, renderer);
        return new GraphicalView(context, chart);
    }

    public static final GraphicalView getDialChartView(Context context, CategorySeries dataset, DialRenderer renderer) {
        checkParameters((CategorySeries) dataset, (DefaultRenderer) renderer);
        DialChart chart = new DialChart(dataset, renderer);
        return new GraphicalView(context, chart);
    }

    public static final GraphicalView getDoughnutChartView(Context context, MultipleCategorySeries dataset, DefaultRenderer renderer) {
        checkParameters(dataset, renderer);
        DoughnutChart chart = new DoughnutChart(dataset, renderer);
        return new GraphicalView(context, chart);
    }

    public static final Intent getLineChartIntent(Context context, XYMultipleSeriesDataset dataset, XYMultipleSeriesRenderer renderer) {
        return getLineChartIntent(context, dataset, renderer, "");
    }

    public static final Intent getCubicLineChartIntent(Context context, XYMultipleSeriesDataset dataset, XYMultipleSeriesRenderer renderer, float smoothness) {
        return getCubicLineChartIntent(context, dataset, renderer, smoothness, "");
    }

    public static final Intent getScatterChartIntent(Context context, XYMultipleSeriesDataset dataset, XYMultipleSeriesRenderer renderer) {
        return getScatterChartIntent(context, dataset, renderer, "");
    }

    public static final Intent getBubbleChartIntent(Context context, XYMultipleSeriesDataset dataset, XYMultipleSeriesRenderer renderer) {
        return getBubbleChartIntent(context, dataset, renderer, "");
    }

    public static final Intent getTimeChartIntent(Context context, XYMultipleSeriesDataset dataset, XYMultipleSeriesRenderer renderer, String format) {
        return getTimeChartIntent(context, dataset, renderer, format, "");
    }

    public static final Intent getBarChartIntent(Context context, XYMultipleSeriesDataset dataset, XYMultipleSeriesRenderer renderer, BarChart.Type type) {
        return getBarChartIntent(context, dataset, renderer, type, "");
    }

    public static final Intent getLineChartIntent(Context context, XYMultipleSeriesDataset dataset, XYMultipleSeriesRenderer renderer, String activityTitle) {
        checkParameters(dataset, renderer);
        Intent intent = new Intent(context, GraphicalActivity.class);
        XYChart chart = new LineChart(dataset, renderer);
        intent.putExtra("chart", chart);
        intent.putExtra("title", activityTitle);
        return intent;
    }

    public static final Intent getCubicLineChartIntent(Context context, XYMultipleSeriesDataset dataset, XYMultipleSeriesRenderer renderer, float smoothness, String activityTitle) {
        checkParameters(dataset, renderer);
        Intent intent = new Intent(context, GraphicalActivity.class);
        XYChart chart = new CubicLineChart(dataset, renderer, smoothness);
        intent.putExtra("chart", chart);
        intent.putExtra("title", activityTitle);
        return intent;
    }

    public static final Intent getScatterChartIntent(Context context, XYMultipleSeriesDataset dataset, XYMultipleSeriesRenderer renderer, String activityTitle) {
        checkParameters(dataset, renderer);
        Intent intent = new Intent(context, GraphicalActivity.class);
        XYChart chart = new ScatterChart(dataset, renderer);
        intent.putExtra("chart", chart);
        intent.putExtra("title", activityTitle);
        return intent;
    }

    public static final Intent getBubbleChartIntent(Context context, XYMultipleSeriesDataset dataset, XYMultipleSeriesRenderer renderer, String activityTitle) {
        checkParameters(dataset, renderer);
        Intent intent = new Intent(context, GraphicalActivity.class);
        XYChart chart = new BubbleChart(dataset, renderer);
        intent.putExtra("chart", chart);
        intent.putExtra("title", activityTitle);
        return intent;
    }

    public static final Intent getTimeChartIntent(Context context, XYMultipleSeriesDataset dataset, XYMultipleSeriesRenderer renderer, String format, String activityTitle) {
        checkParameters(dataset, renderer);
        Intent intent = new Intent(context, GraphicalActivity.class);
        TimeChart chart = new TimeChart(dataset, renderer);
        chart.setDateFormat(format);
        intent.putExtra("chart", chart);
        intent.putExtra("title", activityTitle);
        return intent;
    }

    public static final Intent getBarChartIntent(Context context, XYMultipleSeriesDataset dataset, XYMultipleSeriesRenderer renderer, BarChart.Type type, String activityTitle) {
        checkParameters(dataset, renderer);
        Intent intent = new Intent(context, GraphicalActivity.class);
        BarChart chart = new BarChart(dataset, renderer, type);
        intent.putExtra("chart", chart);
        intent.putExtra("title", activityTitle);
        return intent;
    }

    public static final Intent getRangeBarChartIntent(Context context, XYMultipleSeriesDataset dataset, XYMultipleSeriesRenderer renderer, BarChart.Type type, String activityTitle) {
        checkParameters(dataset, renderer);
        Intent intent = new Intent(context, GraphicalActivity.class);
        RangeBarChart chart = new RangeBarChart(dataset, renderer, type);
        intent.putExtra("chart", chart);
        intent.putExtra("title", activityTitle);
        return intent;
    }

    public static final Intent getCombinedXYChartIntent(Context context, XYMultipleSeriesDataset dataset, XYMultipleSeriesRenderer renderer, CombinedXYChart.XYCombinedChartDef[] types, String activityTitle) {
        checkParameters(dataset, renderer);
        Intent intent = new Intent(context, GraphicalActivity.class);
        CombinedXYChart chart = new CombinedXYChart(dataset, renderer, types);
        intent.putExtra("chart", chart);
        intent.putExtra("title", activityTitle);
        return intent;
    }

    public static final Intent getPieChartIntent(Context context, CategorySeries dataset, DefaultRenderer renderer, String activityTitle) {
        checkParameters(dataset, renderer);
        Intent intent = new Intent(context, GraphicalActivity.class);
        PieChart chart = new PieChart(dataset, renderer);
        intent.putExtra("chart", chart);
        intent.putExtra("title", activityTitle);
        return intent;
    }

    public static final Intent getDoughnutChartIntent(Context context, MultipleCategorySeries dataset, DefaultRenderer renderer, String activityTitle) {
        checkParameters(dataset, renderer);
        Intent intent = new Intent(context, GraphicalActivity.class);
        DoughnutChart chart = new DoughnutChart(dataset, renderer);
        intent.putExtra("chart", chart);
        intent.putExtra("title", activityTitle);
        return intent;
    }

    public static final Intent getDialChartIntent(Context context, CategorySeries dataset, DialRenderer renderer, String activityTitle) {
        checkParameters((CategorySeries) dataset, (DefaultRenderer) renderer);
        Intent intent = new Intent(context, GraphicalActivity.class);
        DialChart chart = new DialChart(dataset, renderer);
        intent.putExtra("chart", chart);
        intent.putExtra("title", activityTitle);
        return intent;
    }

    private static void checkParameters(XYMultipleSeriesDataset dataset, XYMultipleSeriesRenderer renderer) {
        if (dataset == null || renderer == null || dataset.getSeriesCount() != renderer.getSeriesRendererCount()) {
            throw new IllegalArgumentException("Dataset and renderer should be not null and should have the same number of series");
        }
    }

    private static void checkParameters(CategorySeries dataset, DefaultRenderer renderer) {
        if (dataset == null || renderer == null || dataset.getItemCount() != renderer.getSeriesRendererCount()) {
            throw new IllegalArgumentException("Dataset and renderer should be not null and the dataset number of items should be equal to the number of series renderers");
        }
    }

    private static void checkParameters(MultipleCategorySeries dataset, DefaultRenderer renderer) {
        if (dataset == null || renderer == null || !checkMultipleSeriesItems(dataset, renderer.getSeriesRendererCount())) {
            throw new IllegalArgumentException("Titles and values should be not null and the dataset number of items should be equal to the number of series renderers");
        }
    }

    private static boolean checkMultipleSeriesItems(MultipleCategorySeries dataset, int value) {
        int count = dataset.getCategoriesCount();
        boolean equal = true;

        for (int k = 0; k < count && equal; ++k) {
            equal = dataset.getValues(k).length == dataset.getTitles(k).length;
        }

        return equal;
    }
}
