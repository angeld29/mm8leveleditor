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
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextArea;

import org.gamenet.application.mm8leveleditor.data.mm6.fileFormat.Txt;
import org.gamenet.application.mm8leveleditor.lod.LodResource;
import org.gamenet.application.mm8leveleditor.lod.RawDataTemplateLodResource;
import org.gamenet.swing.controls.ResizingJTable;
import org.gamenet.swing.controls.VariableTabularStringTableModel;
import org.gamenet.util.TaskObserver;

public class TxtHandler implements LodResourceHandler
{
    private LodResource sourceLodResource = null;
    private VariableTabularStringTableModel stringTableModel = null;
    
    public Component getComponentFor(LodResource lodResource, TaskObserver taskObserver)
    {
        this.sourceLodResource = lodResource;
        
		Component component = null;

        JPanel panel = new JPanel(new BorderLayout());
        JTextArea description = new JTextArea(lodResource.getTextDescription());
        panel.add(description, BorderLayout.PAGE_START);

        byte byteData[] = null;
        try
        {
            byteData = lodResource.getData();
        }
        catch (IOException anIOException)
        {
            Throwable throwable = new Throwable("Unable to extract data.", anIOException);
            throwable.printStackTrace();
            StringWriter stringWriter = new StringWriter();
            PrintWriter printWriter = new PrintWriter(stringWriter, true);
            throwable.printStackTrace(printWriter);
            
            component = new JTextArea(stringWriter.toString());

            panel.add(component, BorderLayout.CENTER);
            return panel;
        }

        // TODO: Txt object should be backing object not transient object
        Txt txt = new Txt();
        txt.initialize(byteData, 0);

		// TODO: Only good for read-only data
		List<List<String>> lineList = txt.getLineList();

		this.stringTableModel = new VariableTabularStringTableModel(lineList);
		
		JTable dataTable = new ResizingJTable(stringTableModel);
		component = dataTable;

		panel.add(component, BorderLayout.CENTER);
		return panel;
    }

    public LodResource getUpdatedLodResource()
    {
        List<List<String>> lineList = this.stringTableModel.getDataAsListOfListOfStrings();
        
		byte[] byteData = Txt.updateData(lineList);
		
        return new RawDataTemplateLodResource(sourceLodResource, byteData);
    }
}
