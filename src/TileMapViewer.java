// ====================================================
// TileMapGenerator Copyright(C) 2018 Furkan Türkal
// This program comes with ABSOLUTELY NO WARRANTY; This is free software,
// and you are welcome to redistribute it under certain conditions; See
// file LICENSE, which is part of this source code package, for details.
// ====================================================

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public final class TileMapViewer {

    public static void ViewMap(String name, int heightScaleFactor, int widthScaleFactor, LayerMap layerMap){
        int width = layerMap.getWidth();
        int height = layerMap.getHeight();

        byte[] mapData = layerMap.getMapData();

        BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        int[] pixels = new int[width * height];

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int i = x + y * width;

                if (mapData[i] == TileType.WATER.getID()) pixels[i] = TileType.WATER.getHexColor();
                if (mapData[i] == TileType.GRASS.getID()) pixels[i] = TileType.GRASS.getHexColor();
                if (mapData[i] == TileType.ROCK.getID()) pixels[i] = TileType.ROCK.getHexColor();
                if (mapData[i] == TileType.DIRT.getID()) pixels[i] = TileType.DIRT.getHexColor();
                if (mapData[i] == TileType.SAND.getID()) pixels[i] = TileType.SAND.getHexColor();
                if (mapData[i] == TileType.TREE.getID()) pixels[i] = TileType.TREE.getHexColor();
                if (mapData[i] == TileType.LAVA.getID()) pixels[i] = TileType.LAVA.getHexColor();
                if (mapData[i] == TileType.CLOUD.getID()) pixels[i] = TileType.CLOUD.getHexColor();
                if (mapData[i] == TileType.STAIRSDOWN.getID()) pixels[i] = TileType.STAIRSDOWN.getHexColor();
                if (mapData[i] == TileType.STAIRSUP.getID()) pixels[i] = TileType.STAIRSUP.getHexColor();
                if (mapData[i] == TileType.CLOUDCACTUS.getID()) pixels[i] = TileType.CLOUDCACTUS.getHexColor();
            }
        }

        img.setRGB(0, 0, width, height, pixels, 0, width);
        JOptionPane.showMessageDialog(null, null, name, JOptionPane.YES_NO_OPTION, new ImageIcon(img.getScaledInstance(width * widthScaleFactor, height * heightScaleFactor, Image.SCALE_AREA_AVERAGING)));
    }

}
