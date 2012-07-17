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

import javax.swing.JPanel;
import javax.swing.JSplitPane;

import org.gamenet.application.mm8leveleditor.data.mm6.outdoor.DeltaOutdoorDataMap;
import org.gamenet.application.mm8leveleditor.dataSectionable.DeltaOutdoorDataMapDataSectionable;
import org.gamenet.swing.controls.DataSectionTree;

import com.mmbreakfast.unlod.app.UnlodFrame;

public class DeltaOutdoorDataMapControl extends JPanel
{
    private DeltaOutdoorDataMap deltaOutdoorDataMap = null;
    
    public DeltaOutdoorDataMapControl(DeltaOutdoorDataMap srcDeltaOutdoorDataMap)
    {
        super(new FlowLayout(FlowLayout.LEFT));
        
        this.deltaOutdoorDataMap = srcDeltaOutdoorDataMap;
        
		final JPanel componentPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

		DataSectionTree tree = new DataSectionTree("DeltaOutdoorDataMap", new DeltaOutdoorDataMapDataSectionable(deltaOutdoorDataMap), componentPanel, UnlodFrame.defaultApplicationController.getProgressDisplayer());

		JPanel splitPanePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
                tree, componentPanel);
        splitPanePanel.add(splitPane);
        Component component = splitPanePanel;

        this.add(component);
    }

    public DeltaOutdoorDataMap getDeltaOutdoorDataMap()
    {
        return deltaOutdoorDataMap;
    }
}
