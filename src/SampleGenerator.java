// ====================================================
// TileMapGenerator Copyright(C) 2018 Furkan Türkal
// This program comes with ABSOLUTELY NO WARRANTY; This is free software,
// and you are welcome to redistribute it under certain conditions; See
// file LICENSE, which is part of this source code package, for details.
// ====================================================

import java.util.Random;

public final class SampleGenerator {

    private LayerSetting m_layerSetting;

    private int m_height;

    private int m_width;

    public double[] values;

    public SampleGenerator(LayerSetting layerSetting, int stepSize) {
        Random random = layerSetting.getRandom();

        this.m_height = layerSetting.getHeight();
        this.m_width = layerSetting.getWidth();

        values = new double[m_width * m_height];

        for (int y = 0; y < m_width; y += stepSize) {
            for (int x = 0; x < m_width; x += stepSize) {
                setSample(x, y, random.nextFloat() * 2 - 1);
            }
        }

        double scale = 1.0 / m_width;
        double scaleMod = 1;
        do {
            int halfStep = stepSize / 2;
            for (int y = 0; y < m_width; y += stepSize) {
                for (int x = 0; x < m_width; x += stepSize) {
                    double a = getSample(x, y);
                    double b = getSample(x + stepSize, y);
                    double c = getSample(x, y + stepSize);
                    double d = getSample(x + stepSize, y + stepSize);

                    double e = (a + b + c + d) / 4.0 + (random.nextFloat() * 2 - 1) * stepSize * scale;
                    setSample(x + halfStep, y + halfStep, e);
                }
            }
            for (int y = 0; y < m_width; y += stepSize) {
                for (int x = 0; x < m_width; x += stepSize) {
                    double a = getSample(x, y);
                    double b = getSample(x + stepSize, y);
                    double c = getSample(x, y + stepSize);
                    double d = getSample(x + halfStep, y + halfStep);
                    double e = getSample(x + halfStep, y - halfStep);
                    double f = getSample(x - halfStep, y + halfStep);

                    double H = (a + b + d + e) / 4.0 + (random.nextFloat() * 2 - 1) * stepSize * scale * 0.5;
                    double g = (a + c + d + f) / 4.0 + (random.nextFloat() * 2 - 1) * stepSize * scale * 0.5;
                    setSample(x + halfStep, y, H);
                    setSample(x, y + halfStep, g);
                }
            }
            stepSize /= 2;
            scale *= (scaleMod + 0.8);
            scaleMod *= 0.3;
        } while (stepSize > 1);
    }

    private double getSample(int x, int y) {
        return values[(x & (this.m_width - 1)) + (y & (this.m_height - 1)) * this.m_width];
    }

    private void setSample(int x, int y, double value) {
        values[(x & (this.m_width - 1)) + (y & (this.m_height - 1)) * this.m_width] = value;
    }
}