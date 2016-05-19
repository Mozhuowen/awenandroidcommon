package com.mozhuowen.util;

public class ImagePathToUri
{
    public static String convert(String path) {
        return "file://"+path;
    }
}