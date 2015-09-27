package com.example.vjobanputra.girdimagesearch.Models;

import java.io.Serializable;

/**
 * Created by vjobanputra on 9/26/15.
 */
public class Setting implements Serializable {
    public String imageSite;

    public int imageSizeIndex;
    public int imageColorIndex;
    public int imageTypeIndex;

    public Setting(int imageSizeIndex, int imageColorIndex, int imageTypeIndex, String imageSite) {
        this.imageSizeIndex = imageSizeIndex;
        this.imageColorIndex = imageColorIndex;
        this.imageTypeIndex = imageTypeIndex;
        this.imageSite = imageSite;
    }

    public String getImageColor() {
        return colorOptions[imageColorIndex];
    }
    public String getImagesize() {
        return sizeOptions[imageSizeIndex];
    }
    public String getImageType() {
        return typeOptions[imageTypeIndex];
    }
    public String getImageSite() {
        return imageSite;
    }

    public static String[] sizeOptions = {"any", "small", "medium", "large", "xxlarge", "huge"};
    public static String[] colorOptions = {"any", "black", "blue", "brown", "gray", "green"};
    public static String[] typeOptions = {"any", "faces", "photo", "clip art", "food"};

}
