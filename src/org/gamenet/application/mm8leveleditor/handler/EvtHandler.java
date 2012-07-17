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
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import org.gamenet.application.mm8leveleditor.control.EventControl;
import org.gamenet.application.mm8leveleditor.control.Event_3_46_Control;
import org.gamenet.application.mm8leveleditor.data.Event;
import org.gamenet.application.mm8leveleditor.data.EventFormat;
import org.gamenet.application.mm8leveleditor.data.GameVersion;
import org.gamenet.application.mm8leveleditor.data.mm6.Event_3_46;
import org.gamenet.application.mm8leveleditor.data.mm6.fileFormat.EventFormatMM6;
import org.gamenet.application.mm8leveleditor.data.mm6.fileFormat.NpcTextTxt;
import org.gamenet.application.mm8leveleditor.data.mm6.fileFormat.Str;
import org.gamenet.application.mm8leveleditor.data.mm6.fileFormat.Txt;
import org.gamenet.application.mm8leveleditor.data.mm7.fileFormat.EventFormatMM7;
import org.gamenet.application.mm8leveleditor.data.mm8.fileFormat.EventFormatMM8;
import org.gamenet.application.mm8leveleditor.lod.LodResource;
import org.gamenet.application.mm8leveleditor.lod.TemplateLodResource;
import org.gamenet.swing.controls.ComponentArrayPanel;
import org.gamenet.util.TaskObserver;
import org.gamenet.util.UnsupportedFileFormatException;

public class EvtHandler extends AbstractBaseHandler
{
    private LodResource sourceLodResource = null;
    private List<Event> eventList = null;
    private List<Event_3_46> event_3_46List = null;
    private EventFormat eventFormat = null;
    
    public Component getComponentFor(LodResource lodResource, TaskObserver taskObserver) throws InterruptedException
    {
        this.sourceLodResource = lodResource;
        
        taskObserver.taskProgress(lodResource.getName(), 1f / (float)taskObserver.getRange());

        JPanel panel = new JPanel(new BorderLayout());
        JTextArea description = new JTextArea(lodResource.getTextDescription());
        panel.add(description, BorderLayout.PAGE_START);
 
        taskObserver.taskProgress(lodResource.getName(), 2f / (float)taskObserver.getRange());

        byte byteArray[] = null;
        try
        {
            byteArray = lodResource.getData();
        }
        catch (IOException anIOException)
        {
            Throwable throwable = new Throwable("Unable to extract data.", anIOException);
            throwable.printStackTrace();
            StringWriter stringWriter = new StringWriter();
            PrintWriter printWriter = new PrintWriter(stringWriter, true);
            throwable.printStackTrace(printWriter);
            
            Component component = new JTextArea(stringWriter.toString());

            panel.add(component, BorderLayout.CENTER);
            return panel;
        }
        
        taskObserver.taskProgress(lodResource.getName(), 0.7f);
        if (Thread.currentThread().isInterrupted())
            throw new InterruptedException("EvtHandler.getComponentFor() was interrupted.");

        String eventFormatType = "unknown";

        switch (getGameVersionPreference())
        {
	    	case GameVersion.MM6:
	            if (EventFormatMM6.checkDataIntegrity(byteArray, 0, byteArray.length))
	            {
	                this.eventFormat = new EventFormatMM6();
	                eventFormatType = "MM6 Event Resource";
	            }
	            else if (EventFormatMM8.checkDataIntegrity(byteArray, 0, byteArray.length))
	            {
	                this.eventFormat = new EventFormatMM8();
	                eventFormatType = "MM8 Event Resource";
	            }
	            else if (EventFormatMM7.checkDataIntegrity(byteArray, 0, byteArray.length))
	            {
	                this.eventFormat = new EventFormatMM7();
	                eventFormatType = "MM7 Event Resource";
	            }
	            else
	            {
	            	// Try obsolete event handler since there are some bad evt files which it could somewhat parse.
	            	return obsoleteEventHandler(byteArray, taskObserver, lodResource);
	            }
	            break;
	    	case GameVersion.MM7:
	            if (EventFormatMM7.checkDataIntegrity(byteArray, 0, byteArray.length))
	            {
	                this.eventFormat = new EventFormatMM7();
	                eventFormatType = "MM7 Event Resource";
	            }
	            else if (EventFormatMM8.checkDataIntegrity(byteArray, 0, byteArray.length))
	            {
	                this.eventFormat = new EventFormatMM8();
	                eventFormatType = "MM8 Event Resource";
	            }
	            else if (EventFormatMM6.checkDataIntegrity(byteArray, 0, byteArray.length))
	            {
	                this.eventFormat = new EventFormatMM6();
	                eventFormatType = "MM6 Event Resource";
	            }
	            else
	            {
	            	// Try obsolete event handler since there are some bad evt files which it could somewhat parse.
	            	return obsoleteEventHandler(byteArray, taskObserver, lodResource);
	            }
	            break;
        	case GameVersion.UNKNOWN:
        	default:
                if (EventFormatMM8.checkDataIntegrity(byteArray, 0, byteArray.length))
                {
                    this.eventFormat = new EventFormatMM8();
                    eventFormatType = "MM8 Event Resource";
                }
                else if (EventFormatMM7.checkDataIntegrity(byteArray, 0, byteArray.length))
                {
                    this.eventFormat = new EventFormatMM7();
                    eventFormatType = "MM7 Event Resource";
                }
                else if (EventFormatMM6.checkDataIntegrity(byteArray, 0, byteArray.length))
                {
                    this.eventFormat = new EventFormatMM6();
                    eventFormatType = "MM6 Event Resource";
                }
                else
                {
                	// Try obsolete event handler since there are some bad evt files which it could somewhat parse.
                	return obsoleteEventHandler(byteArray, taskObserver, lodResource);
                }
                break;
        }
        
	    eventList = this.eventFormat.readEvents(byteArray);
        
        taskObserver.taskProgress(lodResource.getName(), 0.75f);
        if (Thread.currentThread().isInterrupted())
            throw new InterruptedException("EvtHandler.getComponentFor() was interrupted.");

    	// IMPLEMENT: // TODO: ResourceServer.getInstance() shouldn't be called from here
    	String baseName = ResourceServer.getInstance().getBaseName(lodResource.getName());
    	if ("Global".equalsIgnoreCase(baseName))
    	{
    		Str eventStr = null;
         	Txt txt = ResourceServer.getInstance().getTxt("npctext.txt");
         	NpcTextTxt npcTextTxt = new NpcTextTxt();
         	npcTextTxt.setLineList(txt.getLineList());
			if (null != npcTextTxt)
			{
				List<String> stringsList = npcTextTxt.getStringsList();
	        	eventStr = new Str();
	        	eventStr.setStringsList(stringsList);
			}
        	eventFormat.setStrResource(eventStr);
    	}
    	else
    	{
        	final Str eventStr = ResourceServer.getInstance().getStr(baseName + ".STR");
        	eventFormat.setStrResource(eventStr);
    	}
    	
        taskObserver.taskProgress(lodResource.getName(), 0.90f);
        if (Thread.currentThread().isInterrupted())
            throw new InterruptedException("EvtHandler.getComponentFor() was interrupted.");

        JPanel eventHandlerSuperPanel = new JPanel(new BorderLayout());
        
        JPanel eventHandlerPanel = new JPanel(new BorderLayout());
        
		Component component = eventHandlerSuperPanel;
		
		eventHandlerSuperPanel.add(new JLabel(eventFormatType), BorderLayout.PAGE_START);
		eventHandlerSuperPanel.add(eventHandlerPanel, BorderLayout.CENTER);

		// eventHandlerPanel.add(new ComparativeTableControl(Event.getOffsetList(), eventList));

		// IMPLEMENT: update # of events value when events are added or deleted
		eventHandlerPanel.add(new JLabel("# of Events: " + String.valueOf(eventList.size())), BorderLayout.PAGE_START);

        List<EventControl> eventControlList = new ArrayList<EventControl>(eventList.size());
        for (Iterator<Event> eventIterator = eventList.iterator(); eventIterator.hasNext();)
        {
            Event event = eventIterator.next();
            eventControlList.add(new EventControl(event));
        }
        
        ComponentArrayPanel eventComponentArrayPanel = new ComponentArrayPanel(eventControlList, new ComponentArrayPanel.ComponentDataSource()
        {
            public Component createComponent(int componentIndex)
            {
                Event newEvent = createNewEvent();
                eventList.add(componentIndex, newEvent);
                return new EventControl(newEvent);
            }

            public void fireComponentAdded(int componentIndex, Component component) {}
            public void fireComponentDeleted(int componentIndex, Component component) {}
            public void fireComponentMovedUp(int componentIndex, Component component) {}
            public void fireComponentMovedDown(int componentIndex, Component component) {}
        });

        // JScrollPane eventScroller = new JScrollPane(eventComponentArrayPanel);
        eventHandlerPanel.add(eventComponentArrayPanel, BorderLayout.CENTER);

        panel.add(component, BorderLayout.CENTER);
        
        taskObserver.taskProgress(lodResource.getName(), 1f);

        return panel;
    }		

    private Component obsoleteEventHandler(byte[] byteArray, TaskObserver taskObserver, LodResource lodResource) throws InterruptedException
    {
        if (false == Event_3_46.checkDataIntegrity(byteArray, 0, byteArray.length))
        {
            throw new UnsupportedFileFormatException("Not an expected mm6 evt format.");
        }
	    event_3_46List = Event_3_46.readEvents(byteArray);
        
        taskObserver.taskProgress(lodResource.getName(), 0.75f);
        if (Thread.currentThread().isInterrupted())
            throw new InterruptedException("EvtHandler.getComponentFor() was interrupted.");

        JPanel eventHandlerSuperPanel = new JPanel(new BorderLayout());
        
        JPanel eventHandlerPanel = new JPanel(new BorderLayout());
        
		Component component = eventHandlerSuperPanel;
				
		JLabel obsoleteEventFormatLabel = new JLabel("Obsolete Event Format");
		obsoleteEventFormatLabel.setForeground(Color.RED);
		Font originalFont = obsoleteEventFormatLabel.getFont();
		obsoleteEventFormatLabel.setFont(originalFont.deriveFont(originalFont.getSize() * 1.5f));
		
		
        JPanel panel = new JPanel(new BorderLayout());
        JTextArea description = new JTextArea(lodResource.getTextDescription());
        panel.add(description, BorderLayout.PAGE_START);

		eventHandlerSuperPanel.add(obsoleteEventFormatLabel, BorderLayout.PAGE_START);
		eventHandlerSuperPanel.add(eventHandlerPanel, BorderLayout.CENTER);
		
        // eventHandlerPanel.add(new ComparativeTableControl(Event.getOffsetList(), eventList));

		// IMPLEMENT: update # of events value when events are added or deleted
		eventHandlerPanel.add(new JLabel("# of Events: " + String.valueOf(event_3_46List.size())), BorderLayout.PAGE_START);

        List<Event_3_46_Control> eventControlList = new ArrayList<Event_3_46_Control>(event_3_46List.size());
        for (Iterator<Event_3_46> eventIterator = event_3_46List.iterator(); eventIterator.hasNext();)
        {
            Event_3_46 event = eventIterator.next();
            eventControlList.add(new Event_3_46_Control(event));
        }
        
        ComponentArrayPanel eventComponentArrayPanel = new ComponentArrayPanel(eventControlList, new ComponentArrayPanel.ComponentDataSource()
        {
            public Component createComponent(int componentIndex)
            {
            	// UNSUPPORTED OPERATION FOR OBSOLETE FILE FORMAT
                return null;
            }

            public void fireComponentAdded(int componentIndex, Component component) {}
            public void fireComponentDeleted(int componentIndex, Component component) {}
            public void fireComponentMovedUp(int componentIndex, Component component) {}
            public void fireComponentMovedDown(int componentIndex, Component component) {}
        });

        // JScrollPane eventScroller = new JScrollPane(eventComponentArrayPanel);
        eventHandlerPanel.add(eventComponentArrayPanel, BorderLayout.CENTER);

        panel.add(component, BorderLayout.CENTER);
        
        taskObserver.taskProgress(lodResource.getName(), 1f);

        return panel;
    }
    
    private Event createNewEvent()
    {
        Integer commandIntegerArray[] = this.eventFormat.getCommandTypes();
        String commandNameArray[] = new String[commandIntegerArray.length];
        for (int commandIndex = 0; commandIndex < commandIntegerArray.length; commandIndex++)
        {
            if (null == commandIntegerArray[commandIndex])
            {
                commandNameArray[commandIndex] = "unused/unknown";
            }
            else
            {
                commandNameArray[commandIndex] = this.eventFormat.getCommandTypeName(commandIntegerArray[commandIndex].intValue());
            }
        }
        
        String s = (String)JOptionPane.showInputDialog(
                            null,
                            "Choose an event command type:\n",
                            "Event Command Wizard",
                            JOptionPane.PLAIN_MESSAGE,
                            null,
                            commandNameArray,
                            commandNameArray[0]);

        if ((s == null) || (s.length() == 0))
        {
            // no command type chosen
            return null;
        }

        int commandValue = -1;
        for (int commandIndex = 0; commandIndex < commandNameArray.length; commandIndex++)
        {
            String value = commandNameArray[commandIndex];
            if (s.equals(value))
            {
                commandValue = commandIntegerArray[commandIndex].intValue();
                break;
            }
        }

        if (-1 == commandValue)
        {
            // no command found
            return null;
        }

        int eventNumber = 0;
        int eventSequence = 0;
        
        Event newEvent = this.eventFormat.createEventInstance();
        this.eventFormat.initializeWithValues(newEvent, eventNumber, eventSequence, commandValue);
        
        return newEvent;
    }

    public LodResource getUpdatedLodResource()
    {
        return new EvtLodResource(sourceLodResource, eventList);
    }
    
    public class EvtLodResource extends TemplateLodResource
    {
        private List<Event> eventList = null;
        
        public EvtLodResource(LodResource templateLodResource, List<Event> eventList)
        {
            super(templateLodResource);
            this.eventList = eventList;
        }
        
        public byte[] getData() throws IOException
        {
            return eventFormat.writeEvents(eventList);
        }
    }
}
