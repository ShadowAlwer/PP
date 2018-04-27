package com.bluewares;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import javax.swing.JPanel;

/**
 *
 * @author Kamil Sowa
 */
public class BarChart extends JPanel {

    //offsets (padding of actual chart to its border)
    int leftOffset = 140;
    int topOffset = 130;
    int bottomOffset = 100;
    int rightOffset = 15;

    //height of X labels (must be significantly smaller than bottomOffset)
    int xLabelOffset = 40;
    //width of Y labels (must be significantly smaller than leftOffset)
    int yLabelOffset = 40;

    //tick widths
    int majorTickWidth = 650;
    int majorTickHeight = 400;
    int secTickWidth = 5;
    int minorTickWidth = 2;

    String xAxisStr = "Time";
    String yAxisStr = "Machines";
    String title = "Jobs time for machines Graph";

    int width = 800; //total width of the component
    int height = 630; //total height of the component

    Color textColor = Color.BLACK;
    Color backgroundColor = Color.WHITE;

    Font textFont = new Font("Arial", Font.BOLD, 20);
    Font yFont = new Font("Arial", Font.BOLD, 18);
    Font xFont = new Font("Arial", Font.BOLD, 18);
    Font titleFont = new Font("Arial", Font.BOLD, 18);

    Font yCatFont = new Font("Arial", Font.BOLD, 12);
    Font xCatFont = new Font("Arial", Font.BOLD, 12);

    ArrayList<ArrayList<Bar>> bars;
    Axis xAxis;
    int barWidth = 30;

    /**
     * Construct BarChart
     *
     * @param bars a number of bars to display
     * @param yAxis Axis object describes how to display y Axis
     */
    public BarChart(ArrayList<ArrayList<Bar>> bars, Axis xAxis) {
        this.bars = bars;
        this.xAxis = xAxis;
    }

    public void setBars(ArrayList<ArrayList<Bar>> bars) {
        this.bars = bars;
    }

    public void setxAxis(Axis xAxis) {
        this.xAxis = xAxis;
    }

    @Override
    protected void paintComponent(Graphics g) {

        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, // Anti-alias!
                RenderingHints.VALUE_ANTIALIAS_ON);

        g.drawRect(0, 0, width, height);
        g2d.setColor(backgroundColor);
        g.fillRect(0, 0, width, height);
        g2d.setColor(Color.BLACK);

        int heightChart = height - (topOffset + bottomOffset);
        int widthChart = width - (leftOffset + rightOffset);

        //left
        g.drawLine(leftOffset, topOffset, leftOffset, heightChart + topOffset);

        //bottom
        g.drawLine(leftOffset, heightChart + topOffset, leftOffset + widthChart, heightChart + topOffset);

        if (this.xAxis.primaryIncrements != 0) {
           // drawTick(heightChart, this.xAxis.primaryIncrements, g, Color.BLACK, majorTickWidth);
            drawVerticalTick(widthChart,heightChart, this.xAxis.primaryIncrements, g, Color.BLACK, majorTickHeight);
        }
        if (this.xAxis.secondaryIncrements != 0) {
          //  drawTick(heightChart, this.yAxis.secondaryIncrements, g, Color.BLACK, secTickWidth);
        }
        if (this.xAxis.tertiaryIncrements != 0) {
          //  drawTick(heightChart, this.yAxis.tertiaryIncrements, g, Color.BLACK, minorTickWidth);
        }

       // drawYLabels(heightChart, this.xAxis.primaryIncrements, g, Color.BLACK);
        drawXLabels(widthChart,heightChart, this.xAxis.primaryIncrements, g, Color.BLACK);

       // drawBars(heightChart, widthChart, g);
        drawBars2(heightChart, widthChart, g);

        drawLabels(heightChart, widthChart, g);
    }

    private void drawTick(int heightChart, int increment, Graphics g, Color c, int tickWidth) {
        
        int incrementNo = xAxis.maxValue / increment ;

        double factor = ((double) heightChart / (double) xAxis.maxValue);

        double incrementInPixel = (double) (increment * factor);

        g.setColor(c);

        for (int i = 0; i < incrementNo; i++) {
            int fromTop = heightChart + topOffset - (int) (i * incrementInPixel);
            g.drawLine(leftOffset, fromTop, leftOffset + tickWidth, fromTop);
        }
    }
    private void drawVerticalTick(int widthChart, int heightChart, int increment, Graphics g, Color c, int tickHeight) {
        
        int incrementNo = xAxis.maxValue / increment ;

        double factor = ((double) widthChart / (double) xAxis.maxValue);

        double incrementInPixel = (double) (increment * factor);

        g.setColor(c);

        for (int i = 0; i < incrementNo; i++) {
            int fromLeft =  leftOffset + (int)(i * incrementInPixel);
            g.drawLine(fromLeft, heightChart + topOffset  , fromLeft, heightChart + topOffset - tickHeight);
        }
    }

    private void drawYLabels(int heightChart, int increment, Graphics g, Color c) {

       
        int incrementNo = xAxis.maxValue / increment;

        double factor = ((double) heightChart / (double) xAxis.maxValue);

        int incrementInPixel = (int) (increment * factor);

        g.setColor(c);
        FontMetrics fm = getFontMetrics(yCatFont);

        for (int i = 0; i < incrementNo; i++) {
            int fromTop = heightChart + topOffset - (i * incrementInPixel);

            String yLabel = "" + (i * increment);

            int widthStr = fm.stringWidth(yLabel);
            int heightStr = fm.getHeight();

            g.setFont(yCatFont);
            g.drawString(yLabel, (leftOffset - yLabelOffset) + (yLabelOffset / 2 - widthStr / 2), fromTop + (heightStr / 2));
        }
    }
    private void drawXLabels(int widthChart,int heightChart, int increment, Graphics g, Color c) {

       
        int incrementNo = xAxis.maxValue / increment;

        double factor = ((double) widthChart / (double) xAxis.maxValue);

        int incrementInPixel = (int) (increment * factor);

        g.setColor(c);
        FontMetrics fm = getFontMetrics(yCatFont);

        for (int i = 0; i < incrementNo; i++) {
            int fromLeft =  leftOffset + (i * incrementInPixel);

            String xLabel = "" + (i * increment);

            int widthStr = fm.stringWidth(xLabel);
            int heightStr = fm.getHeight();

            g.setFont(yCatFont);
            g.drawString(xLabel, fromLeft + (widthStr / 2),  topOffset +heightChart+ (xLabelOffset - heightStr / 2));
        }
    }

    private void drawBars(int heightChart, int widthChart, Graphics g) {

        int i = 0;
        int barNumber = bars.size();
        int j = 0;
        int pointDistance = (int) (widthChart / (barNumber + 1));
        int counter = 0;
        int lastheight = 0;
        for (ArrayList<Bar> bar1 : bars) {
            i++;
            for (Bar bar : bar1) {

                double factor = ((double) heightChart / (double) xAxis.maxValue);

                int scaledBarHeight = (int) (bar.value * factor);
                int scaledBarStartHeight = (int) (bar.startValue * factor);

                j = topOffset + heightChart - scaledBarHeight;

                // if (counter>0)
                // j-=lastheight;
                //lastheight=scaledBarHeight;
                g.setColor(bar.color);
                //x, y , szerokosc, wysokosc
                g.drawRect(leftOffset + (i * pointDistance)-(barWidth / 2),               j - scaledBarStartHeight,        barWidth,       scaledBarHeight);
                //g.fillRect(leftOffset + (i * pointDistance) - (barWidth / 2), j, barWidth, scaledBarHeight);

                //draw tick
                g.drawLine(leftOffset + (i * pointDistance),
                        topOffset + heightChart,
                        leftOffset + (i * pointDistance),
                        topOffset + heightChart + 2);

                FontMetrics fm = getFontMetrics(xCatFont);
                int widthStr = fm.stringWidth(bar.name);
                int heightStr = fm.getHeight();

                g.setFont(xCatFont);
                g.setColor(Color.BLACK);

                int xPosition = leftOffset + (i * pointDistance) - (widthStr / 2);
                int yPosition = topOffset + heightChart + xLabelOffset - heightStr / 2;

                //draw tick
                //tu jest opis baru
                g.drawString(bar.name, leftOffset + (i * pointDistance) - (barWidth / 2), j - scaledBarStartHeight + scaledBarHeight / 2);
                //g.drawString(bar.name, xPosition, yPosition);
                counter++;

            }
            counter = 0;
        }

    }
      private void drawBars2(int heightChart, int widthChart, Graphics g) {

        int i = 0;
        int barNumber = bars.size();
        int j = 0;
        int pointDistance = (int) (heightChart / (barNumber + 1));
        int counter = 0;
        int lastheight = 0;
        for (ArrayList<Bar> bar1 : bars) {
            i++;
            for (Bar bar : bar1) {

                double factor = ((double) widthChart / (double) xAxis.maxValue);

                int scaledBarWidth = (int) (bar.value * factor);
                int scaledBarStartWidth = (int) (bar.startValue * factor);

                j = leftOffset + widthChart - scaledBarStartWidth;

                // if (counter>0)
                // j-=lastheight;
                //lastheight=scaledBarHeight;
                g.setColor(bar.color);
                //x, y , szerokosc, wysokosc
               // g.drawRect(leftOffset + (i * pointDistance) - (barWidth / 2), j - scaledBarStartWidth, scaledBarWidth, barWidth);
                g.drawRect( leftOffset + scaledBarStartWidth, leftOffset + (i * pointDistance) - (barWidth / 2), 
                        scaledBarWidth, barWidth);
                //g.fillRect(leftOffset + (i * pointDistance) - (barWidth / 2), j, barWidth, scaledBarHeight);

                //draw tick
                g.drawLine(leftOffset + (i * pointDistance),
                        topOffset + widthChart,
                        leftOffset + (i * pointDistance),
                        topOffset + widthChart + 2);

                FontMetrics fm = getFontMetrics(xCatFont);
                int widthStr = fm.stringWidth(bar.name);
                int heightStr = fm.getHeight();

                g.setFont(xCatFont);
                g.setColor(Color.BLACK);

                int xPosition = leftOffset + (i * pointDistance) - (widthStr / 2);
                int yPosition = topOffset + widthChart + xLabelOffset - heightStr / 2;

                //draw tick
                //tu jest opis baru
                //g.drawString(bar.name, leftOffset + (i * pointDistance) - (barWidth / 2), j - scaledBarStartWidth + scaledBarWidth / 2);
                g.drawString(bar.name, leftOffset+ scaledBarStartWidth + scaledBarWidth / 2, topOffset + (i * pointDistance) +(barWidth / 2));
                //g.drawString(bar.name, xPosition, yPosition);
                counter++;

            }
            counter = 0;
        }

    }

    private void drawLabels(int heightChart, int widthChart, Graphics g) {

        Graphics2D g2d = (Graphics2D) g;

        AffineTransform oldTransform = g2d.getTransform();

        FontMetrics fmY = getFontMetrics(yFont);
        int yAxisStringWidth = fmY.stringWidth(yAxisStr);
        int yAxisStringHeight = fmY.getHeight();

        FontMetrics fmX = getFontMetrics(xFont);
        int xAxisStringWidth = fmX.stringWidth(yAxisStr);
        int xAxisStringHeight = fmX.getHeight();

        FontMetrics fmT = getFontMetrics(titleFont);
        int titleStringWidth = fmT.stringWidth(title);
        int titleStringHeight = fmT.getHeight();

        g2d.setColor(Color.BLACK);
        //draw tick
        g2d.rotate(Math.toRadians(270)); //rotates to above out of screen.

        int translateDown = -leftOffset - (topOffset + heightChart / 2 + yAxisStringWidth / 2);

        //starts off being "topOffset" off, so subtract that first
        int translateLeft = -topOffset + (leftOffset - yLabelOffset) / 2 + yAxisStringHeight / 2;

        //pull down, which is basically the left offset, topOffset, then middle it by 
        //usin chart height and using text height.
        g2d.translate(translateDown, translateLeft);

        g2d.setFont(yFont);
        //yAxisStr=yAxis.yLabel;
        g2d.drawString(yAxisStr, leftOffset, topOffset);

        //reset
        g2d.setTransform(oldTransform);

        int xAxesLabelHeight = bottomOffset - xLabelOffset;

        //x label        
        g2d.setFont(xFont);
        g2d.drawString(xAxisStr, widthChart / 2 + leftOffset - xAxisStringWidth / 2, topOffset + heightChart + xLabelOffset + xAxesLabelHeight / 2);

        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, // Anti-alias!
                RenderingHints.VALUE_ANTIALIAS_ON);
        //title
        g2d.setFont(titleFont);
        int titleX = (leftOffset + rightOffset + widthChart) / 2 - titleStringWidth / 2;
        int titleY = topOffset / 2 + titleStringHeight / 2;
        System.out.println("titleStringHeight " + titleStringHeight);
        System.out.println("titleX " + titleX);
        System.out.println("titleY " + titleY);
        System.out.println("topOffset " + topOffset);

        g2d.drawString(title, titleX, titleY);
    }
}
