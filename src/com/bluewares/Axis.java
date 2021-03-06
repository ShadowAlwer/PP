package com.bluewares;

/**
 *
 * @author Kamil Sowa
 */
public class Axis {

    int primaryIncrements = 0;
    int secondaryIncrements = 0;
    int tertiaryIncrements = 0;

    int maxValue = 100;
    int minValue = 0;

    String yLabel;

    public Axis(String name) {
        this(100, 0, 50, 10, 5, name);
    }

    public Axis(int primaryIncrements, int secondaryIncrements, int tertiaryIncrements, String name) {
        this(100, 0, primaryIncrements, secondaryIncrements, tertiaryIncrements, name);
    }

    public Axis(Integer maxValue, Integer minValue, int primaryIncrements, int secondaryIncrements, int tertiaryIncrements, String name) {

        this.maxValue = maxValue;
        this.minValue = minValue;
        this.yLabel = name;

        if (primaryIncrements != 0) {
            this.primaryIncrements = primaryIncrements;
        }
        if (secondaryIncrements != 0) {
            this.secondaryIncrements = secondaryIncrements;
        }
        if (tertiaryIncrements != 0) {
            this.tertiaryIncrements = tertiaryIncrements;
        }
    }

    public Axis(int i, int i0, int primaryIncrements, int secondaryIncrements, int tertiaryIncrements) {
        this(i, i0, primaryIncrements, secondaryIncrements, tertiaryIncrements, "nic");
    }
}
