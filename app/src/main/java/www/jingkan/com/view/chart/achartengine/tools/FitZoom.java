//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package www.jingkan.com.view.chart.achartengine.tools;


import www.jingkan.com.view.chart.achartengine.chart.AbstractChart;
import www.jingkan.com.view.chart.achartengine.chart.RoundChart;
import www.jingkan.com.view.chart.achartengine.chart.XYChart;
import www.jingkan.com.view.chart.achartengine.model.XYSeries;
import www.jingkan.com.view.chart.achartengine.renderer.DefaultRenderer;

public class FitZoom extends AbstractTool {
    public FitZoom(AbstractChart chart) {
        super(chart);
    }

    public void apply() {
        if (this.mChart instanceof XYChart) {
            if (((XYChart) this.mChart).getDataset() == null) {
                return;
            }

            int scales = this.mRenderer.getScalesCount();
            if (this.mRenderer.isInitialRangeSet()) {
                for (int i = 0; i < scales; ++i) {
                    if (this.mRenderer.isInitialRangeSet(i)) {
                        this.mRenderer.setRange(this.mRenderer.getInitialRange(i), i);
                    }
                }
            } else {
                XYSeries[] series = ((XYChart) this.mChart).getDataset().getSeries();
                int length = series.length;
                if (length > 0) {
                    for (int i = 0; i < scales; ++i) {
                        double[] range = new double[]{1.7976931348623157E308D, -1.7976931348623157E308D, 1.7976931348623157E308D, -1.7976931348623157E308D};

                        for (int j = 0; j < length; ++j) {
                            if (i == series[j].getScaleNumber()) {
                                range[0] = Math.min(range[0], series[j].getMinX());
                                range[1] = Math.max(range[1], series[j].getMaxX());
                                range[2] = Math.min(range[2], series[j].getMinY());
                                range[3] = Math.max(range[3], series[j].getMaxY());
                            }
                        }

                        double marginX = Math.abs(range[1] - range[0]) / 40.0D;
                        double marginY = Math.abs(range[3] - range[2]) / 40.0D;
                        this.mRenderer.setRange(new double[]{range[0] - marginX, range[1] + marginX, range[2] - marginY, range[3] + marginY}, i);
                    }
                }
            }
        } else {
            DefaultRenderer renderer = ((RoundChart) this.mChart).getRenderer();
            renderer.setScale(renderer.getOriginalScale());
        }

    }
}
