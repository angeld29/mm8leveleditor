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

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import org.gamenet.application.mm8leveleditor.lod.LodResource;
import org.gamenet.util.TaskObserver;
import org.gamenet.util.UnimplementedMethodException;

public class SmackHandler implements LodResourceHandler
{
    public Component getComponentFor(LodResource lodResource, TaskObserver taskObserver)
    {
        Component component = null;

        try
        {
//            extractionmanager.startExtraction(lodResource);
//            byte byteData[] = lodResource.extractToByteArray();
//            extractionmanager.stopExtraction();
            
			component = new JTextArea("Play movie using external SmackPlw tool or dll.\nhttp://www.radgametools.com\nYou can also convert video to smack format using the free Rad Video tools at\nhttp://www.radgametools.com/bnkdown.htm\n");
        }
        catch (Exception exception)
        {
            component = new JLabel(exception.toString());
            exception.printStackTrace();
        }

        JPanel panel = new JPanel(new BorderLayout());
        JTextArea description = new JTextArea(lodResource.getTextDescription());
        panel.add(description, BorderLayout.PAGE_START);
        panel.add(component, BorderLayout.CENTER);
        return panel;
    }

    public LodResource getUpdatedLodResource()
    {
        throw new UnimplementedMethodException("getUpdatedLodResource() Unimplemented.");
    }
}
