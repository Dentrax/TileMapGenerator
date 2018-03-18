// ====================================================
// TileMapGenerator Copyright(C) 2018 Furkan Türkal
// This program comes with ABSOLUTELY NO WARRANTY; This is free software,
// and you are welcome to redistribute it under certain conditions; See
// file LICENSE, which is part of this source code package, for details.
// ====================================================

public final class LayerMap {

    private  LayerSetting m_setting;

    private byte[] m_mapData;

    public LayerMap(LayerSetting setting, byte[] mapData) {
        this.m_setting = setting;
        this.m_mapData = mapData;
    }

    public byte[] getMapData(){
        return this.m_mapData;
    }

    public int getHeight(){
        return this.m_setting.getHeight();
    }

    public int getWidth(){
        return this.m_setting.getWidth();
    }
}
