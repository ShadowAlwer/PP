/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
        this.startValue=startValue;
    }
}
 