// ====================================================
// TileMapGenerator Copyright(C) 2018 Furkan Türkal
// This program comes with ABSOLUTELY NO WARRANTY; This is free software,
// and you are welcome to redistribute it under certain conditions; See
// file LICENSE, which is part of this source code package, for details.
// ====================================================

import javax.swing.*;
import java.util.Random;

public final class LayerGenerator {

    private LayerSetting m_layerSetting;

    private Random m_random;

    public LayerGenerator(LayerSetting layerSetting) {
        this.m_layerSetting = layerSetting;
        this.m_random = layerSetting.getRandom();
    }

    private boolean isPowerOfTwo(int number) {
        return number > 0 && ((number & (number - 1)) == 0);
    }

    public LayerMap doCreate(LayerType layerType){
        LayerMap layerMap = null;

        if(this.m_layerSetting.getHeight() < 128){
            JOptionPane.showMessageDialog(null, "Height must be least 128", "Warning", JOptionPane.INFORMATION_MESSAGE);
            return null;
        }

        if(!this.isPowerOfTwo(this.m_layerSetting.getHeight())){
            JOptionPane.showMessageDialog(null, "Height must be power-up-two", "Warning", JOptionPane.INFORMATION_MESSAGE);
            return null;
        }

        if(this.m_layerSetting.getWidth() < 128){
            JOptionPane.showMessageDialog(null, "Width must be least 128", "Warning", JOptionPane.INFORMATION_MESSAGE);
            return null;
        }

        if(!this.isPowerOfTwo(this.m_layerSetting.getWidth())){
            JOptionPane.showMessageDialog(null, "Width must be power-up-two", "Warning", JOptionPane.INFORMATION_MESSAGE);
            return null;
        }

        switch (layerType){
            case SKY:
                layerMap = new LayerMap(this.m_layerSetting, this.doCreateSkyMap(this.m_layerSetting.getWidth(), this.m_layerSetting.getHeight())[0]);
                break;
            case GROUND:
                layerMap = new LayerMap(this.m_layerSetting, this.doCreateGroundMap(this.m_layerSetting.getWidth(), this.m_layerSetting.getHeight())[0]);
                break;
            case UNDERGROUND:
                layerMap = new LayerMap(this.m_layerSetting, this.doCreateUndergroundMap(this.m_layerSetting.getWidth(), this.m_layerSetting.getHeight(), this.m_layerSetting.getDepth())[0]);
                break;
        }

        return layerMap;
    }

    private byte[][] doCreateSkyMap(int w, int h) {
        do {
            byte[][] result = this.createSkyMapSample(w, h);

            int[] count = new int[256];

            for (int i = 0; i < w * h; i++) {
                count[result[0][i] & 0xff]++;
            }
            if (count[TileType.CLOUD.getID() & 0xff] < 2000) continue;
            if (count[TileType.STAIRSDOWN.getID() & 0xff] < 2) continue;

            return result;

        } while (true);
    }

    private byte[][] doCreateGroundMap(int w, int h) {
        do {
            byte[][] result = this.createGroundMapSample(w, h);

            int[] count = new int[256];

            for (int i = 0; i < w * h; i++) {
                count[result[0][i] & 0xff]++;
            }
            if (count[TileType.ROCK.getID() & 0xff] < 100) continue;
            if (count[TileType.SAND.getID() & 0xff] < 100) continue;
            if (count[TileType.GRASS.getID() & 0xff] < 100) continue;
            if (count[TileType.TREE.getID() & 0xff] < 100) continue;
            if (count[TileType.STAIRSDOWN.getID() & 0xff] < 2) continue;

            return result;

        } while (true);
    }

    private byte[][] doCreateUndergroundMap(int w, int h, int depth) {
        do {
            byte[][] result = this.createUndergroundMapSample(w, h, depth);

            int[] count = new int[256];

            for (int i = 0; i < w * h; i++) {
                count[result[0][i] & 0xff]++;
            }
            if (count[TileType.ROCK.getID() & 0xff] < 100) continue;
            if (count[TileType.DIRT.getID() & 0xff] < 100) continue;
            if (count[(TileType.IRONORE.getID() & 0xff) + depth - 1] < 20) continue;
            if (depth < 3) if (count[TileType.STAIRSDOWN.getID() & 0xff] < 2) continue;

            return result;

        } while (true);
    }


    private byte[][] createSkyMapSample(int w, int h) {
        SampleGenerator noise1 = new SampleGenerator(this.m_layerSetting, 8);
        SampleGenerator noise2 = new SampleGenerator(this.m_layerSetting, 8);

        byte[] map = new byte[w * h];
        byte[] data = new byte[w * h];
        for (int y = 0; y < h; y++) {
            for (int x = 0; x < w; x++) {
                int i = x + y * w;

                double val = Math.abs(noise1.values[i] - noise2.values[i]) * 3 - 2;

                double xd = x / (w - 1.0) * 2 - 1;
                double yd = y / (h - 1.0) * 2 - 1;
                if (xd < 0) xd = -xd;
                if (yd < 0) yd = -yd;
                double dist = xd >= yd ? xd : yd;
                dist = dist * dist * dist * dist;
                dist = dist * dist * dist * dist;
                val = -val * 1 - 2.2;
                val = val + 1 - dist * 20;

                if (val < LayerRatio.SKY.NOISE_INFINITEFALL_LOWER) {
                    map[i] = TileType.INFINITEFALL.getID();
                } else {
                    map[i] = TileType.CLOUD.getID();
                }
            }
        }

        stairsLoop: for (int i = 0; i < w * h / LayerRatio.SKY.RATIO_CLOUDCACTUS; i++) {
            int x = m_random.nextInt(w - 2) + 1;
            int y = m_random.nextInt(h - 2) + 1;

            for (int yy = y - 1; yy <= y + 1; yy++)
                for (int xx = x - 1; xx <= x + 1; xx++) {
                    if (map[xx + yy * w] != TileType.CLOUD.getID()) continue stairsLoop;
                }

            map[x + y * w] = TileType.CLOUDCACTUS.getID();
        }

        int count = 0;
        stairsLoop: for (int i = 0; i < w * h; i++) {
            int x = m_random.nextInt(w - 2) + 1;
            int y = m_random.nextInt(h - 2) + 1;

            for (int yy = y - 1; yy <= y + 1; yy++)
                for (int xx = x - 1; xx <= x + 1; xx++) {
                    if (map[xx + yy * w] != TileType.CLOUD.getID()) continue stairsLoop;
                }

            map[x + y * w] = TileType.STAIRSDOWN.getID();
            count++;
            if (count == LayerRatio.SKY.MAX_STAIRS_COUNT) break;
        }

        return new byte[][] { map, data };
    }

    private byte[][] createGroundMapSample(int w, int h) {
        SampleGenerator mnoise1 = new SampleGenerator(this.m_layerSetting, 16);
        SampleGenerator mnoise2 = new SampleGenerator(this.m_layerSetting, 16);
        SampleGenerator mnoise3 = new SampleGenerator(this.m_layerSetting, 16);

        SampleGenerator noise1 = new SampleGenerator(this.m_layerSetting, 32);
        SampleGenerator noise2 = new SampleGenerator(this.m_layerSetting, 32);

        byte[] map = new byte[w * h];
        byte[] data = new byte[w * h];
        for (int y = 0; y < h; y++) {
            for (int x = 0; x < w; x++) {
                int i = x + y * w;

                double val = Math.abs(noise1.values[i] - noise2.values[i]) * 3 - 2;
                double mval = Math.abs(mnoise1.values[i] - mnoise2.values[i]);
                mval = Math.abs(mval - mnoise3.values[i]) * 3 - 2;

                double xd = x / (w - 1.0) * 2 - 1;
                double yd = y / (h - 1.0) * 2 - 1;
                if (xd < 0) xd = -xd;
                if (yd < 0) yd = -yd;
                double dist = xd >= yd ? xd : yd;
                dist = dist * dist * dist * dist;
                dist = dist * dist * dist * dist;
                val = val + 1 - dist * 20;

                if (val < LayerRatio.GROUND.NOISE_WATER_LOWER) {
                    map[i] = TileType.WATER.getID();
                } else if (val > LayerRatio.GROUND.NOISE_ROCK_HIGHER && mval < LayerRatio.GROUND.NOISE_ROCK_LOWER) {
                    map[i] = TileType.ROCK.getID();
                } else {
                    map[i] = TileType.GRASS.getID();
                }
            }
        }

        for (int i = 0; i < w * h / LayerRatio.GROUND.RATIO_SAND; i++) {
            int xs = m_random.nextInt(w);
            int ys = m_random.nextInt(h);
            for (int k = 0; k < 10; k++) {
                int x = xs + m_random.nextInt(21) - 10;
                int y = ys + m_random.nextInt(21) - 10;
                for (int j = 0; j < 100; j++) {
                    int xo = x + m_random.nextInt(5) - m_random.nextInt(5);
                    int yo = y + m_random.nextInt(5) - m_random.nextInt(5);
                    for (int yy = yo - 1; yy <= yo + 1; yy++)
                        for (int xx = xo - 1; xx <= xo + 1; xx++)
                            if (xx >= 0 && yy >= 0 && xx < w && yy < h) {
                                if (map[xx + yy * w] == TileType.GRASS.getID()) {
                                    map[xx + yy * w] = TileType.SAND.getID();
                                }
                            }
                }
            }
        }

        /*
         * for (int i = 0; i < w * h / 2800; i++) { int xs = random.nextInt(w); int ys = random.nextInt(h); for (int k = 0; k < 10; k++) { int x = xs + random.nextInt(21) - 10; int y = ys + random.nextInt(21) - 10; for (int j = 0; j < 100; j++) { int xo = x + random.nextInt(5) - random.nextInt(5); int yo = y + random.nextInt(5) - random.nextInt(5); for (int yy = yo - 1; yy <= yo + 1; yy++) for (int xx = xo - 1; xx <= xo + 1; xx++) if (xx >= 0 && yy >= 0 && xx < w && yy < h) { if (map[xx + yy * w] == TileType.grass.getID()) { map[xx + yy * w] = TileType.dirt.getID(); } } } } }
         */

        for (int i = 0; i < w * h / LayerRatio.GROUND.RATIO_TREE; i++) {
            int x = m_random.nextInt(w);
            int y = m_random.nextInt(h);
            for (int j = 0; j < 200; j++) {
                int xx = x + m_random.nextInt(15) - m_random.nextInt(15);
                int yy = y + m_random.nextInt(15) - m_random.nextInt(15);
                if (xx >= 0 && yy >= 0 && xx < w && yy < h) {
                    if (map[xx + yy * w] == TileType.GRASS.getID()) {
                        map[xx + yy * w] = TileType.TREE.getID();
                    }
                }
            }
        }

        for (int i = 0; i < w * h / LayerRatio.GROUND.RATIO_FLOWER; i++) {
            int x = m_random.nextInt(w);
            int y = m_random.nextInt(h);
            int col = m_random.nextInt(4);
            for (int j = 0; j < 30; j++) {
                int xx = x + m_random.nextInt(5) - m_random.nextInt(5);
                int yy = y + m_random.nextInt(5) - m_random.nextInt(5);
                if (xx >= 0 && yy >= 0 && xx < w && yy < h) {
                    if (map[xx + yy * w] == TileType.GRASS.getID()) {
                        map[xx + yy * w] = TileType.FLOWER.getID();
                        data[xx + yy * w] = (byte) (col + m_random.nextInt(4) * 16);
                    }
                }
            }
        }

        for (int i = 0; i < w * h / LayerRatio.GROUND.RATIO_CACTUS; i++) {
            int xx = m_random.nextInt(w);
            int yy = m_random.nextInt(h);
            if (xx >= 0 && yy >= 0 && xx < w && yy < h) {
                if (map[xx + yy * w] == TileType.SAND.getID()) {
                    map[xx + yy * w] = TileType.CACTUS.getID();
                }
            }
        }

        int count = 0;
        stairsLoop: for (int i = 0; i < w * h / 100; i++) {
            int x = m_random.nextInt(w - 2) + 1;
            int y = m_random.nextInt(h - 2) + 1;

            for (int yy = y - 1; yy <= y + 1; yy++)
                for (int xx = x - 1; xx <= x + 1; xx++) {
                    if (map[xx + yy * w] != TileType.ROCK.getID()) continue stairsLoop;
                }

            map[x + y * w] = TileType.STAIRSDOWN.getID();
            count++;
            if (count == LayerRatio.GROUND.MAX_STAIRS_COUNT) break;
        }

        return new byte[][] { map, data };
    }

    private byte[][] createUndergroundMapSample(int w, int h, int depth) {
        SampleGenerator mnoise1 = new SampleGenerator(this.m_layerSetting, 16);
        SampleGenerator mnoise2 = new SampleGenerator(this.m_layerSetting, 16);
        SampleGenerator mnoise3 = new SampleGenerator(this.m_layerSetting, 16);

        SampleGenerator nnoise1 = new SampleGenerator(this.m_layerSetting, 16);
        SampleGenerator nnoise2 = new SampleGenerator(this.m_layerSetting, 16);
        SampleGenerator nnoise3 = new SampleGenerator(this.m_layerSetting, 16);

        SampleGenerator wnoise1 = new SampleGenerator(this.m_layerSetting, 16);
        SampleGenerator wnoise2 = new SampleGenerator(this.m_layerSetting, 16);
        SampleGenerator wnoise3 = new SampleGenerator(this.m_layerSetting, 16);

        SampleGenerator noise1 = new SampleGenerator(this.m_layerSetting, 32);
        SampleGenerator noise2 = new SampleGenerator(this.m_layerSetting, 32);

        byte[] map = new byte[w * h];
        byte[] data = new byte[w * h];
        for (int y = 0; y < h; y++) {
            for (int x = 0; x < w; x++) {
                int i = x + y * w;

                double val = Math.abs(noise1.values[i] - noise2.values[i]) * 3 - 2;

                double mval = Math.abs(mnoise1.values[i] - mnoise2.values[i]);
                mval = Math.abs(mval - mnoise3.values[i]) * 3 - 2;

                double nval = Math.abs(nnoise1.values[i] - nnoise2.values[i]);
                nval = Math.abs(nval - nnoise3.values[i]) * 3 - 2;

                double wval = Math.abs(wnoise1.values[i] - wnoise2.values[i]);
                wval = Math.abs(nval - wnoise3.values[i]) * 3 - 2;

                double xd = x / (w - 1.0) * 2 - 1;
                double yd = y / (h - 1.0) * 2 - 1;
                if (xd < 0) xd = -xd;
                if (yd < 0) yd = -yd;
                double dist = xd >= yd ? xd : yd;
                dist = dist * dist * dist * dist;
                dist = dist * dist * dist * dist;
                val = val + 1 - dist * 20;

                if (val > LayerRatio.UNDERGROUND.NOISE_LIQUID_HIGHER && wval < -2.0 + (depth) / 2 * 3) {
                    if (depth > LayerRatio.UNDERGROUND.DEPTH_LAVA)
                        map[i] = TileType.LAVA.getID();
                    else
                        map[i] = TileType.WATER.getID();
                } else if (val > LayerRatio.UNDERGROUND.NOISE_DIRT_HIGHER && (mval < LayerRatio.UNDERGROUND.NOISE_DIRT_MVAL || nval < LayerRatio.UNDERGROUND.NOISE_DIRT_NVAL)) {
                    map[i] = TileType.DIRT.getID();
                } else {
                    map[i] = TileType.ROCK.getID();
                }
            }
        }

        {
            int r = 2;
            for (int i = 0; i < w * h / LayerRatio.UNDERGROUND.RATIO_IRONORE; i++) {
                int x = m_random.nextInt(w);
                int y = m_random.nextInt(h);
                for (int j = 0; j < 30; j++) {
                    int xx = x + m_random.nextInt(5) - m_random.nextInt(5);
                    int yy = y + m_random.nextInt(5) - m_random.nextInt(5);
                    if (xx >= r && yy >= r && xx < w - r && yy < h - r) {
                        if (map[xx + yy * w] == TileType.ROCK.getID()) {
                            map[xx + yy * w] = (byte) ((TileType.IRONORE.getID() & 0xff) + depth - 1);
                        }
                    }
                }
            }
        }

        if (depth < 3) {
            int count = 0;
            stairsLoop: for (int i = 0; i < w * h / 100; i++) {
                int x = m_random.nextInt(w - 20) + 10;
                int y = m_random.nextInt(h - 20) + 10;

                for (int yy = y - 1; yy <= y + 1; yy++)
                    for (int xx = x - 1; xx <= x + 1; xx++) {
                        if (map[xx + yy * w] != TileType.ROCK.getID()) continue stairsLoop;
                    }

                map[x + y * w] = TileType.STAIRSDOWN.getID();
                count++;
                if (count == LayerRatio.UNDERGROUND.MAX_STAIRS_COUNT) break;
            }
        }

        return new byte[][] { map, data };
    }


}
