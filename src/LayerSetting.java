// ====================================================
// TileMapGenerator Copyright(C) 2018 Furkan Türkal
// This program comes with ABSOLUTELY NO WARRANTY; This is free software,
// and you are welcome to redistribute it under certain conditions; See
// file LICENSE, which is part of this source code package, for details.
// ====================================================

import java.util.Random;

public final class LayerSetting {

    private int m_width;

    private int m_height;

    private int m_stepSize;

    private int m_depth;

    private Random m_random;

    public LayerSetting(int width, int height, int stepSize, int depth, Random random) {
        this.m_width = width;
        this.m_height = height;
        this.m_stepSize = stepSize;
        this.m_depth = depth;
        this.m_random = random;
    }

    public int getWidth() {
        return this.m_width;
    }

    public int getHeight() {
        return this.m_height;
    }

    public int getStepSize() {
        return this.m_stepSize;
    }

    public int getDepth() {
        return this.m_depth;
    }

    public Random getRandom() {
        return this.m_random;
    }
}
