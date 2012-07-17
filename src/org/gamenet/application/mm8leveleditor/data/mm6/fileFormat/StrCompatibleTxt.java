package org.gamenet.application.mm8leveleditor.data.mm6.fileFormat;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

abstract public class StrCompatibleTxt extends Txt {

	abstract protected String getName();

	public List<String> getStringsList()
	{
		List<String> stringsList = new ArrayList<String>();
        stringsList.add(null);

   		int counter = 1;

   		Iterator<List<String>> lineIterator = this.getLineList().iterator();
        while (lineIterator.hasNext())
        {
            List<String> columnList = lineIterator.next();
            // TODO: should really check that the values are in the right order
            String indexString = columnList.get(0);
            int index;
			try {
				index = Integer.parseInt(indexString);
			} catch (NumberFormatException e) {
				continue;
			}
            if (index != counter)
            {
            	throw new RuntimeException("index<"
            			+ index + "> != counter<"
            			+ counter + "> for " + getName());
            }
            
            ++counter;
            if (columnList.size() > 1)
            {
	            stringsList.add(columnList.get(1));
            }
            else
            {
	            stringsList.add(null);
            }
        }
        
        return stringsList;
	}
}
