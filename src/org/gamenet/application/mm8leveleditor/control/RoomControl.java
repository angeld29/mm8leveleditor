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

import java.awt.Component;
import java.awt.FlowLayout;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;

import org.gamenet.application.mm8leveleditor.data.mm6.indoor.Room;
import org.gamenet.swing.controls.ShortTextField;
import org.gamenet.swing.controls.ShortValueHolder;

public class RoomControl extends JPanel
{
    private Room room = null;
    
    public RoomControl(Room srcRoom)
    {
        super();
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setBorder(new BevelBorder(BevelBorder.LOWERED));
        
        this.room = srcRoom;
        
	    final short floorArray[] = room.getFloorArray();
	    JPanel floorPanel = (JPanel)makeNonStretchedPanelFor(new JLabel("Floor List: "));
        for (int index = 0; index < floorArray.length; ++index)
        {
            final int finalizedIndex = index;
            floorPanel.add(new ShortTextField(new ShortValueHolder() {
                public short getValue() { return floorArray[finalizedIndex]; }
                public void setValue(short value) { floorArray[finalizedIndex] = value; }
            }));
        }
        this.add(floorPanel);

	    final short wallArray[] = room.getWallArray();
	    JPanel wallPanel = (JPanel)makeNonStretchedPanelFor(new JLabel("Wall List: "));
        for (int index = 0; index < wallArray.length; ++index)
        {
            final int finalizedIndex = index;
            wallPanel.add(new ShortTextField(new ShortValueHolder() {
                public short getValue() { return wallArray[finalizedIndex]; }
                public void setValue(short value) { wallArray[finalizedIndex] = value; }
            }));
        }
        this.add(wallPanel);

	    final short ceilingArray[] = room.getCeilingArray();
	    JPanel ceilingPanel = (JPanel)makeNonStretchedPanelFor(new JLabel("Ceiling List: "));
        for (int index = 0; index < ceilingArray.length; ++index)
        {
            final int finalizedIndex = index;
            ceilingPanel.add(new ShortTextField(new ShortValueHolder() {
                public short getValue() { return ceilingArray[finalizedIndex]; }
                public void setValue(short value) { ceilingArray[finalizedIndex] = value; }
            }));
        }
        this.add(ceilingPanel);

	    final short fluidArray[] = room.getFluidArray();
	    JPanel fluidPanel = (JPanel)makeNonStretchedPanelFor(new JLabel("Fluid List: "));
        for (int index = 0; index < fluidArray.length; ++index)
        {
            final int finalizedIndex = index;
            fluidPanel.add(new ShortTextField(new ShortValueHolder() {
                public short getValue() { return fluidArray[finalizedIndex]; }
                public void setValue(short value) { fluidArray[finalizedIndex] = value; }
            }));
        }
        this.add(fluidPanel);

	    final short portalArray[] = room.getPortalArray();
	    JPanel portalPanel = (JPanel)makeNonStretchedPanelFor(new JLabel("Portal List: "));
        for (int index = 0; index < portalArray.length; ++index)
        {
            final int finalizedIndex = index;
            portalPanel.add(new ShortTextField(new ShortValueHolder() {
                public short getValue() { return portalArray[finalizedIndex]; }
                public void setValue(short value) { portalArray[finalizedIndex] = value; }
            }));
        }
        this.add(portalPanel);

	    final short drawingArray[] = room.getDrawingArray();
	    JPanel drawingPanel = (JPanel)makeNonStretchedPanelFor(new JLabel("Drawing List: "));
        for (int index = 0; index < drawingArray.length; ++index)
        {
            final int finalizedIndex = index;
            drawingPanel.add(new ShortTextField(new ShortValueHolder() {
                public short getValue() { return drawingArray[finalizedIndex]; }
                public void setValue(short value) { drawingArray[finalizedIndex] = value; }
            }));
        }
        this.add(drawingPanel);

	    final short cogArray[] = room.getCogArray();
	    JPanel cogPanel = (JPanel)makeNonStretchedPanelFor(new JLabel("Cog List: "));
        for (int index = 0; index < cogArray.length; ++index)
        {
            final int finalizedIndex = index;
            cogPanel.add(new ShortTextField(new ShortValueHolder() {
                public short getValue() { return cogArray[finalizedIndex]; }
                public void setValue(short value) { cogArray[finalizedIndex] = value; }
            }));
        }
        this.add(cogPanel);

	    final short decorationArray[] = room.getDecorationArray();
	    JPanel decorationPanel = (JPanel)makeNonStretchedPanelFor(new JLabel("Decoration List: "));
        for (int index = 0; index < decorationArray.length; ++index)
        {
            final int finalizedIndex = index;
            decorationPanel.add(new ShortTextField(new ShortValueHolder() {
                public short getValue() { return decorationArray[finalizedIndex]; }
                public void setValue(short value) { decorationArray[finalizedIndex] = value; }
            }));
        }
        this.add(decorationPanel);

	    final short specialMarkerArray[] = room.getSpecialMarkerArray();
	    JPanel specialMarkerPanel = (JPanel)makeNonStretchedPanelFor(new JLabel("Special Marker List: "));
        for (int index = 0; index < specialMarkerArray.length; ++index)
        {
            final int finalizedIndex = index;
            specialMarkerPanel.add(new ShortTextField(new ShortValueHolder() {
                public short getValue() { return specialMarkerArray[finalizedIndex]; }
                public void setValue(short value) { specialMarkerArray[finalizedIndex] = value; }
            }));
        }
        this.add(specialMarkerPanel);

        final short permanentLightArray[] = room.getPermanentLightArray();
	    JPanel permanentLightPanel = (JPanel)makeNonStretchedPanelFor(new JLabel("Permanent Light List: "));
        for (int index = 0; index < permanentLightArray.length; ++index)
        {
            final int finalizedIndex = index;
            permanentLightPanel.add(new ShortTextField(new ShortValueHolder() {
                public short getValue() { return permanentLightArray[finalizedIndex]; }
                public void setValue(short value) { permanentLightArray[finalizedIndex] = value; }
            }));
        }
        this.add(permanentLightPanel);

    }

    public Object getRoom()
    {
        return room;
    }
    
    protected JPanel makeNonStretchedPanelFor(Component component)
    {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        if (null != component)  panel.add(component);
        return panel;
    }
}
