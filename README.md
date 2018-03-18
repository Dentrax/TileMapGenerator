<h1 align="center">TileMapGenerator Public Source Repository</h1>

**Create your own 2D Maps with layer-by-layer system using Noise-Sample and BufferedImage pattern**

**Warning: Please see the [Copyright & Licensing](#copyright--licensing) section before use**

[What It Is](#what-it-is)

[How To Use](#how-to-use)

[Features](#features)

[Requirements](#requirements)

[About](#about)  

[Collaborators](#collaborators)  

[Branches](#branches) 

[Copyright & Licensing](#copyright--licensing)  

[Contributing](#contributing)  

[Contact](#contact)

## What It Is

**TileMapGenerator with Java using Noise-Sample and BufferedImage pattern**

TileMapGenerator program for Java language is an easy and simple way to create your own 2D Maps with layer-by-layer system.

**Uses : Please see [Requirements](#requirements) before use**

**128x128**

* SKY

![Preview Thumbnail](https://raw.githubusercontent.com/Dentrax/TileMapGenerator/master/screenshots/sky_128.gif)

* GROUND

![Preview Thumbnail](https://raw.githubusercontent.com/Dentrax/TileMapGenerator/master/screenshots/ground_128.gif)

* UNDERGROUND

![Preview Thumbnail](https://raw.githubusercontent.com/Dentrax/TileMapGenerator/master/screenshots/underground_128.gif)

**128x256**

![Preview Thumbnail](https://raw.githubusercontent.com/Dentrax/TileMapGenerator/master/screenshots/mix_128_256.gif)

## Features

* Map generation engine in given specifications

* Adjustable Map size

* Adjustable MapViewer scale-size

* Adjustable TileTypes colors & ids

* Detailed Map-Layer specifications

* Adjustable size & scale & step-size feature of each Map-Layer

* Advanced Map sample & noise pattern generation engine

* Configurable random seed for each Map-Layer

* Unique, fully randomized Map generation

## How To Use

**Warning: You must use power-of-two values for map-size**

**Warning: You must use least 128x128 values for map-size**

* 1. Define your unique Random

```java
private static final Random random = new Random(System.currentTimeMillis());
```

* 2. Define your own LayerSettings

```java
LayerSetting patternLayerSetting = new LayerSetting(height, width, stepSize, depth, random);

LayerSetting skyLayerSetting = new LayerSetting(128, 128, 16, 0, random);
LayerSetting groundLayerSetting = new LayerSetting(128, 128, 16, 0, random);
LayerSetting undergroundLayerSetting = new LayerSetting(128, 128, 16, 0, random);
```

* 3. Define your own LayerGenerators

```java
LayerGenerator patternGenerator = new LayerGenerator(patternLayerSetting);

LayerGenerator skyGenerator = new LayerGenerator(skyLayerSetting);
LayerGenerator groundGenerator = new LayerGenerator(groundLayerSetting);
LayerGenerator undergroundGenerator = new LayerGenerator(undergroundLayerSetting);
```

* 4. Create your own LayerMap

```java
LayerMap patternMap = patternGenerator.doCreate(LayerType.ANY);

LayerMap skyMap = skyGenerator.doCreate(LayerType.SKY);
LayerMap groundMap = groundGenerator.doCreate(LayerType.GROUND);
LayerMap undergroundMap = undergroundGenerator.doCreate(LayerType.UNDERGROUND);
```

* 5. View your created Map

```java
TileMapViewer.ViewMap("PATTERN", heightScaleFactor, widthScaleFactor, patternMap);

TileMapViewer.ViewMap("SKY", 4, 4, skyMap);
TileMapViewer.ViewMap("GROUND", 4, 4, groundMap);
TileMapViewer.ViewMap("UNDERGROUND", 4, 4, undergroundMap);
```

## Requirements

* You should be familiar with Java family
* You will need a text editor (i.e Notepad++) or IDE (i.e IntelliJ Idea)
* You will need a computer on which you have the rights to install JDK and Java SE dependencies

## About

TileMapGenerator was created to serve three purposes:

**TileMapGenerator is a basically an simple Noise Tile-Map-Generator**

1. To act as a map-viewer which is given noise and generator ratios.

2. To act as a byte-pixel based BufferedImage viewer.

3. To act as a map-generator that you can use in your own games or research purposes.

## Collaborators

**Project Manager** - Furkan Türkal (GitHub: **[dentrax](https://github.com/dentrax)**)

## Branches

We publish source for the **[TileMapGenerator]** in single rolling branch:

The **[master branch](https://github.com/dentrax/TileMapGenerator/tree/master)** is extensively tested by our QA team and makes a great starting point for learning the algorithms. Also tracks [live changes](https://github.com/dentrax/TileMapGenerator/commits/master) by our team. 

## Copyright & Licensing

The core project code is copyrighted by Markus 'Notch' Persson who made main-noise-core in Twitch live-broadcast.

The main project code is copyrighted by Furkan 'Dentrax' Türkal who made enhancements, fixes and improvements. And is covered by single licence. 

All program code (i.e. .java) is licensed under MIT License unless otherwise specified. Please see the **[LICENSE.md](https://github.com/Dentrax/TileMapGenerator/blob/master/LICENSE)** file for more information.

**References**

While this repository is being prepared, it may have been quoted from some sources. (i.e Notch)
If there is an unspecified source, please contact me.

## Contributing

Please check the [CONTRIBUTING.md](CONTRIBUTING.md) file for contribution instructions and naming guidelines.

## Contact

TileMapGenerator was created by Furkan 'Dentrax' Türkal

 * <https://www.furkanturkal.com>
 
You can contact by URL:
    **[CONTACT](https://github.com/dentrax)**

<kbd>Best Regards</kbd>