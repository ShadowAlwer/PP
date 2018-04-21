package com.bluewares;

import java.awt.Color;

/**
 *
 * @author Kamil Sowa
 */
public class Bar {

    double value;
    Color color;
    String name;
    double startValue;

    public Bar(int value, Color color, String name, int startValue) {
        this.value = value;
        this.color = color;
        this.name = name;
        this.startValue = startValue;
    }
}
