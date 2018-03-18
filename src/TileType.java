// ====================================================
// TileMapGenerator Copyright(C) 2018 Furkan Türkal
// This program comes with ABSOLUTELY NO WARRANTY; This is free software,
// and you are welcome to redistribute it under certain conditions; See
// file LICENSE, which is part of this source code package, for details.
// ====================================================

public enum TileType {
    GRASS(0, 0x208020),
    ROCK(1, 0xa0a0a0),
    WATER(2, 0x000080),
    FLOWER(3, 0x0),
    TREE(4, 0x003000),
    DIRT(5, 0x604040),
    SAND(6, 0xa0a040),
    CACTUS(7, 0x0),
    HOLE(8, 0x0),
    TREESAPLING(9, 0x0),
    CACTUSSAPLING(10, 0x0),
    FARMLAND(11, 0x0),
    WHEAT(12, 0x0),
    LAVA(13, 0xff2020),
    STAIRSDOWN(14, 0xffffff),
    STAIRSUP(15, 0xffffff),
    INFINITEFALL(16, 0x0),
    CLOUD(17, 0xa0a0a0),
    HARDROCK(18, 0x0),
    IRONORE(19, 0x0),
    GOLDORE(20, 0x0),
    GEMORE(21, 0x0),
    CLOUDCACTUS(22, 0xff00ff);

    private final int m_id;

    private final int m_hexColor;

    TileType(final int id, final int hexColor) {
        this.m_id = id;
        this.m_hexColor = hexColor;
    }

    public byte getID(){
        return (byte)(this.m_id & (0xff));
    }

    public int getHexColor() {
        return this.m_hexColor;
    }

}
