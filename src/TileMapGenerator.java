// ====================================================
// TileMapGenerator Copyright(C) 2018 Furkan Türkal
// This program comes with ABSOLUTELY NO WARRANTY; This is free software,
// and you are welcome to redistribute it under certain conditions; See
// file LICENSE, which is part of this source code package, for details.
// ====================================================

import java.util.Random;

public final class TileMapGenerator {

    private static final Random random = new Random(System.currentTimeMillis());

    public static void main(String[] args) {

        LayerSetting skyLayerSetting = new LayerSetting(128, 128, 16, 0, random);
        LayerSetting groundLayerSetting = new LayerSetting(128, 128, 16, 0, random);
        LayerSetting undergroundLayerSetting = new LayerSetting(128, 128, 16, 0, random);

        LayerGenerator skyGenerator = new LayerGenerator(skyLayerSetting);
        LayerGenerator groundGenerator = new LayerGenerator(groundLayerSetting);
        LayerGenerator undergroundGenerator = new LayerGenerator(undergroundLayerSetting);

        int widthScaleFactor = 4;
        int heightScaleFactor = 4;

        int attempt = 1;

        while (true){

            LayerMap skyMap = skyGenerator.doCreate(LayerType.SKY);
            LayerMap groundMap = groundGenerator.doCreate(LayerType.GROUND);
            LayerMap undergroundMap = undergroundGenerator.doCreate(LayerType.UNDERGROUND);

            TileMapViewer.ViewMap("SKY - Attempt: " + attempt, heightScaleFactor, widthScaleFactor, skyMap);
            TileMapViewer.ViewMap("GROUND - Attempt: " + attempt, heightScaleFactor, widthScaleFactor, groundMap);
            TileMapViewer.ViewMap("UNDERGROUND - Attempt: " + attempt, heightScaleFactor, widthScaleFactor, undergroundMap);

            attempt++;
        }
    }

}
