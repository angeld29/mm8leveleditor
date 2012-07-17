package org.gamenet.application.mm8leveleditor.data.mm6.fileFormat;

import java.util.List;

public class ClassTxt extends Txt {

	public String getNameAtIndex(int index)
	{
		// First line is the header
        List<String> columnList = lineList.get(index+1);
        return columnList.get(0);
	}
}
