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
import java.awt.Dimension;
import java.awt.Panel;
import java.io.ByteArrayInputStream;
import java.io.IOException;

import javax.media.CachingControl;
import javax.media.CachingControlEvent;
import javax.media.Controller;
import javax.media.ControllerClosedEvent;
import javax.media.ControllerErrorEvent;
import javax.media.ControllerEvent;
import javax.media.ControllerListener;
import javax.media.EndOfMediaEvent;
import javax.media.Manager;
import javax.media.NoPlayerException;
import javax.media.Player;
import javax.media.RealizeCompleteEvent;
import javax.media.StartEvent;
import javax.media.Time;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import org.gamenet.application.mm8leveleditor.lod.LodResource;
import org.gamenet.util.TaskObserver;
import org.gamenet.util.UnimplementedMethodException;
import org.gamenet.util.WavInputStreamDataSource;

public class WavHandler implements LodResourceHandler, ControllerListener
{
	private ByteArrayInputStream bytearrayinputstream = null;
	
    public Component getComponentFor(LodResource lodResource, TaskObserver taskObserver)
    {
		Component component = null;

		byte byteData[] = null;
		try
		{
            byteData = lodResource.getData();

			bytearrayinputstream = new ByteArrayInputStream(byteData);

			JPanel parentPanel = new JPanel();
			panel = new Panel();
			
			parentPanel.add(panel);

			component = parentPanel;
			
		}
		catch (Exception exception)
		{
			component = new JLabel(exception.getMessage());
		}
		
		JPanel contentPanel = new JPanel(new BorderLayout());
		JTextArea description = new JTextArea(lodResource.getTextDescription());
		contentPanel.add(description, BorderLayout.PAGE_START);
		contentPanel.add(component, BorderLayout.CENTER);

        try
        {
            initPlayer(bytearrayinputstream);
        }
        catch (java.lang.NoClassDefFoundError e)
        {
            JTextArea error = new JTextArea("Unable to initialize wav player.   Is jfm.jar in the classpath?");
            contentPanel.add(error, BorderLayout.PAGE_END);
            e.printStackTrace();
        }

		return contentPanel;
    }

	// media Player
	Player player = null;
	// component in which video is playing
	Component visualComponent = null;
	// controls gain, position, start, stop
	Component controlComponent = null;
	// displays progress during download
	Component progressBar = null;
	boolean firstTime = true;
	long CachingSize = 0L;
	Panel panel = null;
	int controlPanelHeight = 0;
	int videoWidth = 0;
	int videoHeight = 0;
	boolean canStartAgain = false;
	/**
	 * Read the applet file parameter and create the media 
	 * player.
	 */
	public void initPlayer(ByteArrayInputStream bytearrayinputstream)
	{
		panel.setLayout( null );
		panel.setBounds(0, 0, 320, 240);

		WavInputStreamDataSource aDataSource = new WavInputStreamDataSource(bytearrayinputstream);
		try
        {
            player = Manager.createPlayer(aDataSource);
            player.addControllerListener(this);
        }
        catch (NoPlayerException aNoPlayerException)
        {
			panel.add(new JLabel(aNoPlayerException.getMessage()));
			aNoPlayerException.printStackTrace();
        }
        catch (IOException anIOException)
        {
			panel.add(new JLabel(anIOException.getMessage()));
			anIOException.printStackTrace();
        }
        
		canStartAgain = false;
        player.realize();
        
	}

	/**
	 * Start media file playback. This function is called the
	 * first time that the Applet runs and every
	 * time the user re-enters the page.
	 */

	public void start()
	{
		//$ System.out.println("Applet.start() is called");
		// Call start() to prefetch and start the player.
		if (player != null)
			player.start();
	}

	/**
	 * Stop media file playback and release resource before
	 * leaving the page.
	 */
	public void stop()
	{
		//$ System.out.println("Applet.stop() is called");
		if (player != null)
		{
			player.stop();
			player.deallocate();
		}
	}

	public void destroy()
	{
		//$ System.out.println("Applet.destroy() is called");
		player.close();
	}

	/**
	 * This controllerUpdate function must be defined in order to
	 * implement a ControllerListener interface. This 
	 * function will be called whenever there is a media event
	 */
	public synchronized void controllerUpdate(ControllerEvent event)
	{
		// If we're getting messages from a dead player, 
		// just leave
		if (player == null)
			return;

//		Date now = new Date();
//		System.out.println(now.toString() + ": " + event.toString());

		// When the player is Realized, get the visual 
		// and control components and add them to the Applet
		if (event instanceof RealizeCompleteEvent)
		{
			if (firstTime)
			{
				if (progressBar != null)
				{
					panel.remove(progressBar);
					progressBar = null;
				}

				int width = 320;
				int height = 0;
				if (controlComponent == null)
					if ((controlComponent = player.getControlPanelComponent())
						!= null)
					{

						controlPanelHeight =
							controlComponent.getPreferredSize().height;
						panel.add(controlComponent);
						height += controlPanelHeight;
					}
				if (visualComponent == null)
					if ((visualComponent = player.getVisualComponent()) != null)
					{
						panel.add(visualComponent);
						Dimension videoSize = visualComponent.getPreferredSize();
						videoWidth = videoSize.width;
						videoHeight = videoSize.height;
						width = videoWidth;
						height += videoHeight;
						visualComponent.setBounds(0, 0, videoWidth, videoHeight);
					}

				panel.setBounds(0, 0, width, height);
				if (controlComponent != null)
				{
					controlComponent.setBounds(
						0,
						videoHeight,
						width,
						controlPanelHeight);
					controlComponent.invalidate();
				}

			}
		}
		else if (event instanceof CachingControlEvent)
		{
			if (player.getState() > Controller.Realizing)
				return;
			// Put a progress bar up when downloading starts, 
			// take it down when downloading ends.
			CachingControlEvent e = (CachingControlEvent)event;
			CachingControl cc = e.getCachingControl();

			// Add the bar if not already there ...
			if (progressBar == null)
			{
				if ((progressBar = cc.getControlComponent()) != null)
				{
					panel.add(progressBar);
					panel.setSize(progressBar.getPreferredSize());
				}
			}
		}
		else if (event instanceof EndOfMediaEvent)
		{
			if (false == canStartAgain)
			{
				canStartAgain = true;
				// We've reached the end of the media; rewind and
				// start over
//				player.setMediaTime(new Time(0));
			}
		}
		else if (event instanceof StartEvent)
		{
			if (canStartAgain)
			{
				
				// Repositioning MediaTime then playing works in the original SimplePlayerApplet code, but not here for some reason.
				canStartAgain = false;
				// We've reached the end of the media; rewind and
				// start over
				player.setMediaTime(new Time(0));
				player.start();
			}
		}
		else if (event instanceof ControllerErrorEvent)
		{
			// Tell TypicalPlayerApplet.start() to call it a day
			player = null;
			String s = ((ControllerErrorEvent)event).getMessage();
			// System.err.println("FATAL ERROR: " + s);
			throw new Error(s); // Invoke the uncaught exception
		}
		else if (event instanceof ControllerClosedEvent)
		{
			panel.removeAll();
		}
		else
		{
			// System.out.println(event.toString() + " is unhandled.");
		}
	}

    public LodResource getUpdatedLodResource()
    {
        throw new UnimplementedMethodException("getUpdatedLodResource() Unimplemented.");
    }
}
