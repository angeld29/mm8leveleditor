/*
 * Copyright (c) 2005 (Mike) Maurice Kienenberger (mkienenb@gmail.com)
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package org.gamenet.swing.controls;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;


public class SwatchPanel extends JPanel
{
    private static final int HORIZONTAL_ASPECT_RATIO = 1;
    private static final int VERTICAL_ASPECT_RATIO = 1;
    
    protected Dimension paletteDimension = null;
    protected Color[] palette = null;
    protected int columns = -1;
    protected int rows = -1;
    protected int cellPadding = 2;

    public SwatchPanel(Color paletteColorArray[], int columns, int rows, int cellPadding, int pixelsPerCell)
    {
		this.palette = paletteColorArray;
		this.columns = columns;
		this.rows = rows;
		this.cellPadding = cellPadding;

        this.paletteDimension = new Dimension(columns * pixelsPerCell * HORIZONTAL_ASPECT_RATIO, rows * pixelsPerCell * VERTICAL_ASPECT_RATIO);

        setOpaque(true);
        setBackground(Color.white);
       	setSize(paletteDimension);
    }

    public SwatchPanel(PixelMapDataSource pixelMapDataSource, int cellPadding, int pixelsPerCell, boolean normalize)
    {
        this(createPixelMapColorArray(pixelMapDataSource, normalize), pixelMapDataSource.getColumnCount(), pixelMapDataSource.getRowCount(), cellPadding, pixelsPerCell);
    }
    
	public Dimension getPreferredSize()
	{
		return paletteDimension;
	}
	
    public void paintComponent(Graphics aGraphics)
    {
		Dimension swatchDimension = new Dimension(getWidth() / columns, getHeight() / rows);

		aGraphics.setColor(getBackground());
		aGraphics.fillRect(0, 0, getWidth(), getHeight());
        for (int row = 0; row < rows; ++row)
        {
            for (int column = 0; column < columns; ++column)
            {
				aGraphics.setColor(palette[(row * columns) + column]);
                int x = column * swatchDimension.width;
                int y = row * swatchDimension.height;
                aGraphics.fillRect(x + cellPadding, y + cellPadding, swatchDimension.width - cellPadding, swatchDimension.height - cellPadding);
				if (0 != cellPadding)
				{
	                aGraphics.setColor(Color.black);
					aGraphics.drawRect(x + cellPadding, y + cellPadding, swatchDimension.width - cellPadding, swatchDimension.height - cellPadding);
				}
            }
        }
    }
    
    public static Color[] createPixelMapColorArray(PixelMapDataSource pixelMapDataSource, boolean normalize)
    {
        int min = Integer.MAX_VALUE;
        int max = Integer.MIN_VALUE;
        float multiplier = 1f;

        if (normalize)
        {
            // normalize
            for (int row = 0; row < 128; ++row)
            {
                for (int col = 0; col < 128; ++col)
                {
            		int value = pixelMapDataSource.getValueAt(row, col);
            		if (min > value)  min = value;
            		if (max < value)  max = value;
                }
            }
            multiplier = 256f / ((float)((max - min) + 1));
        }
        
        Color mapColorArray[] = new Color[pixelMapDataSource.getRowCount() * pixelMapDataSource.getColumnCount()];
        for (int row = 0; row < pixelMapDataSource.getRowCount(); ++row)
        {
            for (int col = 0; col < pixelMapDataSource.getColumnCount(); ++col)
            {
                int value = pixelMapDataSource.getValueAt(row, col);
                
                if (normalize)
                {
                    value = (int)((value - min) * multiplier);
            		if (value > 255)  value = 255;
            		if (value < 0)  value = 0;
                }

        		int red = value;
        		int green = value;
        		int blue = value;
        		
        		mapColorArray[(row * pixelMapDataSource.getColumnCount()) + col] = new Color(red, green, blue);
            }
        }
        
        return mapColorArray;
    }

}