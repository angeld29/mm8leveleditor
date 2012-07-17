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
import java.awt.Toolkit;
import java.io.ByteArrayInputStream;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import org.gamenet.application.mm8leveleditor.lod.LodResource;
import org.gamenet.application.mm8leveleditor.lod.TGADataProducer;
import org.gamenet.util.BitmapDecoder;
import org.gamenet.util.TaskObserver;
import org.gamenet.util.UnimplementedMethodException;

public class MultipleBitmapHandler implements LodResourceHandler
{
    public Component getComponentFor(LodResource lodResource, TaskObserver taskObserver)
    {
		Component component = null;

        TGADataProducer tgaDataProducer = null;
        if (lodResource instanceof TGADataProducer)
        {
            tgaDataProducer = (TGADataProducer) lodResource;
        }
        else
        {
            component = new JTextArea("Resource does not contain TGA Data.");

            JPanel panel = new JPanel(new BorderLayout());
            JTextArea description = new JTextArea(lodResource.getTextDescription());
            panel.add(description, BorderLayout.PAGE_START);
            panel.add(component, BorderLayout.CENTER);
            return panel;
        }
        
        int width = tgaDataProducer.getByteWidth();
        int height = tgaDataProducer.getByteHeight();

        try
        {
			JPanel panel = new JPanel();
			component = panel;

			byte byteArray[] = lodResource.getData();

			byte byteArray1[] = new byte[width * height];
			System.arraycopy(byteArray, 0, byteArray1, 0, byteArray1.length);
			
			byte byteArray2[] = new byte[byteArray1.length / 4];
			System.arraycopy(byteArray, byteArray1.length, byteArray2, 0, byteArray2.length);
			
			byte byteArray3[] = new byte[byteArray2.length / 4];
			System.arraycopy(byteArray, byteArray1.length + byteArray2.length, byteArray3, 0, byteArray3.length);
			
			byte byteArray4[] = new byte[byteArray3.length / 4];
			System.arraycopy(byteArray, byteArray1.length + byteArray2.length + byteArray3.length, byteArray4, 0, byteArray4.length);
			
			int paletteArray[] = tgaDataProducer.getPalette();

			ByteArrayInputStream bytearrayinputstream1 =
				new ByteArrayInputStream(byteArray1);

			ByteArrayInputStream bytearrayinputstream2 =
				new ByteArrayInputStream(byteArray2);

			ByteArrayInputStream bytearrayinputstream3 =
				new ByteArrayInputStream(byteArray3);

			ByteArrayInputStream bytearrayinputstream4 =
				new ByteArrayInputStream(byteArray4);

			panel.add(
					new JLabel(
						new ImageIcon(
							Toolkit.getDefaultToolkit().createImage(
								new BitmapDecoder().decode(paletteArray, width, height, bytearrayinputstream1)))));

			panel.add(
					new JLabel(
						new ImageIcon(
							Toolkit.getDefaultToolkit().createImage(
								new BitmapDecoder().decode(paletteArray, width / 2 , height / 2, bytearrayinputstream2)))));

			panel.add(
					new JLabel(
						new ImageIcon(
							Toolkit.getDefaultToolkit().createImage(
								new BitmapDecoder().decode(paletteArray, width / 4, height / 4, bytearrayinputstream3)))));

			panel.add(
					new JLabel(
						new ImageIcon(
							Toolkit.getDefaultToolkit().createImage(
								new BitmapDecoder().decode(paletteArray, width / 8, height / 8, bytearrayinputstream4)))));

		}
        catch (Exception exception)
        {
			component = new JLabel(exception.toString());
			exception.printStackTrace();
         }

		JPanel panel = new JPanel(new BorderLayout());
		JTextArea description = new JTextArea(lodResource.getTextDescription());
		panel.add(description, BorderLayout.PAGE_START);
		//		Make the center component big, since that's the
		//		typical usage of BorderLayout.
		panel.add(component, BorderLayout.CENTER);
		return panel;
    }

    public LodResource getUpdatedLodResource()
    {
        throw new UnimplementedMethodException("getUpdatedLodResource() Unimplemented.");
    }
}
