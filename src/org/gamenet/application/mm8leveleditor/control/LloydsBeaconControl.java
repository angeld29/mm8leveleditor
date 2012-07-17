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
package org.gamenet.application.mm8leveleditor.control;

import java.awt.FlowLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

import org.gamenet.application.mm8leveleditor.data.mm6.LloydsBeacon;
import org.gamenet.swing.controls.LongValueHolder;
import org.gamenet.swing.controls.ShortTextField;
import org.gamenet.swing.controls.ShortValueHolder;
import org.gamenet.swing.controls.Vertex3DTextFieldPanel;
import org.gamenet.swing.controls.Vertex3DValueHolder;

public class LloydsBeaconControl extends JPanel
{
    private LloydsBeacon lloydsBeacon = null;
    
    public LloydsBeaconControl(LloydsBeacon srcLloydsBeacon)
    {
        super(new FlowLayout(FlowLayout.LEFT));
        
        this.lloydsBeacon = srcLloydsBeacon;

        this.add(new JLabel("End Date:"));
        DateTimeControl endDateTimeControl = new DateTimeControl(new LongValueHolder()
                {
                    public long getValue() { return lloydsBeacon.getEndDateTime(); }
                    public void setValue(long value) { lloydsBeacon.setEndDateTime(value); }
                });
        this.add(endDateTimeControl);
        
        this.add(new JLabel("Coords:"));
        this.add(new Vertex3DTextFieldPanel(new Vertex3DValueHolder()
        {
            public int getX() { return lloydsBeacon.getX(); }
            public void setX(int x) { lloydsBeacon.setX(x); }
            public int getY() { return lloydsBeacon.getX(); }
            public void setY(int y) { lloydsBeacon.setY(y); }
            public int getZ() { return lloydsBeacon.getZ(); }
            public void setZ(int z) { lloydsBeacon.setZ(z); }
        }));

        this.add(new JLabel("Facing:"));
        this.add(new ShortTextField(new ShortValueHolder()
                {
                    public short getValue() { return lloydsBeacon.getFacing(); }
                    public void setValue(short value) { lloydsBeacon.setFacing(value); }
                }));

        this.add(new JLabel("Tilt:"));
        this.add(new ShortTextField(new ShortValueHolder()
                {
                    public short getValue() { return lloydsBeacon.getTilt(); }
                    public void setValue(short value) { lloydsBeacon.setTilt(value); }
                }));

        this.add(new JLabel("Destination:"));
        this.add(new ShortTextField(new ShortValueHolder()
                {
                    public short getValue() { return lloydsBeacon.getDestination(); }
                    public void setValue(short value) { lloydsBeacon.setDestination(value); }
                }));
    }

    public LloydsBeacon getLloydsBeacon()
    {
        return lloydsBeacon;
    }
}
