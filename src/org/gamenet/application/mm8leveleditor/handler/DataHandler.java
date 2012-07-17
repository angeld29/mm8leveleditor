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


package org.gamenet.application.mm8leveleditor.handler;
import java.awt.BorderLayout;
import java.awt.Component;
import java.io.IOException;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import org.gamenet.application.mm8leveleditor.lod.LodResource;
import org.gamenet.application.mm8leveleditor.lod.TemplateLodResource;
import org.gamenet.swing.controls.ByteDataTableControl;
import org.gamenet.util.TaskObserver;

public class DataHandler extends AbstractBaseHandler
{
    private LodResource sourceLodResource = null;
    private ByteDataTableControl byteDataTableControl = null;
    
    public Component getComponentFor(LodResource lodResource, TaskObserver taskObserver) throws InterruptedException
    {
        this.sourceLodResource = lodResource;
		
        Component component = null;

		byte byteData[] = null;
		try
		{
		    byteData = lodResource.getData();
		}
		catch(Exception anException)
		{
			component = new JLabel(anException.getMessage());
		}

		int byteDataColumns = 32;
	
		byteDataTableControl = new ByteDataTableControl(byteData, byteDataColumns, 0);
		
		component = byteDataTableControl;

		JPanel panel = new JPanel(new BorderLayout());
		JTextArea description = new JTextArea(lodResource.getTextDescription());
		
        byte dataHeader[] = lodResource.getDataHeader();
		ByteDataTableControl byteDataTableControl = new ByteDataTableControl(dataHeader, 32, 0);
		JPanel dataHeaderPanel = new JPanel(new BorderLayout());
        dataHeaderPanel.add(makeNonStretchedPanelFor(new JLabel("Data Header:")), BorderLayout.PAGE_START);
        dataHeaderPanel.add(makeNonStretchedPanelFor(byteDataTableControl), BorderLayout.CENTER);

		JPanel dataPanel = new JPanel(new BorderLayout());
        dataPanel.add(makeNonStretchedPanelFor(new JLabel("Data:")), BorderLayout.PAGE_START);
        dataPanel.add(component, BorderLayout.CENTER);
        
		panel.add(description, BorderLayout.PAGE_START);
        panel.add(dataHeaderPanel, BorderLayout.CENTER);
		panel.add(dataPanel, BorderLayout.PAGE_END);
		return panel;
    }

    public LodResource getUpdatedLodResource()
    {
        return new ByteDataLodResource(sourceLodResource, byteDataTableControl.getDataCopy());
    }
    
    public class ByteDataLodResource extends TemplateLodResource
    {
        private byte data[] = null;
        
        public ByteDataLodResource(LodResource templateLodResource, byte data[])
        {
            super(templateLodResource);
            this.data = data;
        }
        
        public byte[] getData() throws IOException
        {
            return this.data;
        }
    }
}
