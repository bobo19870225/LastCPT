//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package www.jingkan.com.view.chart.achartengine.tools;


import www.jingkan.com.view.chart.achartengine.chart.AbstractChart;
import www.jingkan.com.view.chart.achartengine.chart.XYChart;
import www.jingkan.com.view.chart.achartengine.renderer.XYMultipleSeriesRenderer;

public abstract class AbstractTool {
    protected AbstractChart mChart;
    protected XYMultipleSeriesRenderer mRenderer;

    public AbstractTool(AbstractChart chart) {
        this.mChart = chart;
        if (chart instanceof XYChart) {
            this.mRenderer = ((XYChart) chart).getRenderer();
        }

    }

    public double[] getRange(int scale) {
        double minX = this.mRenderer.getXAxisMin(scale);
        double maxX = this.mRenderer.getXAxisMax(scale);
        double minY = this.mRenderer.getYAxisMin(scale);
        double maxY = this.mRenderer.getYAxisMax(scale);
        return new double[]{minX, maxX, minY, maxY};
    }

    public void checkRange(double[] range, int scale) {
        if (this.mChart instanceof XYChart) {
            double[] calcRange = ((XYChart) this.mChart).getCalcRange(scale);
            if (calcRange != null) {
                if (!this.mRenderer.isMinXSet(scale)) {
                    range[0] = calcRange[0];
                    this.mRenderer.setXAxisMin(range[0], scale);
                }

                if (!this.mRenderer.isMaxXSet(scale)) {
                    range[1] = calcRange[1];
                    this.mRenderer.setXAxisMax(range[1], scale);
                }

                if (!this.mRenderer.isMinYSet(scale)) {
                    range[2] = calcRange[2];
                    this.mRenderer.setYAxisMin(range[2], scale);
                }

                if (!this.mRenderer.isMaxYSet(scale)) {
                    range[3] = calcRange[3];
                    this.mRenderer.setYAxisMax(range[3], scale);
                }
            }
        }

    }

    protected void setXRange(double min, double max, int scale) {
        this.mRenderer.setXAxisMin(min, scale);
        this.mRenderer.setXAxisMax(max, scale);
    }

    protected void setYRange(double min, double max, int scale) {
        this.mRenderer.setYAxisMin(min, scale);
        this.mRenderer.setYAxisMax(max, scale);
    }
}
