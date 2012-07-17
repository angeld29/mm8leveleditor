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

package org.gamenet.util;

public class SubTaskObserverImpl implements SubTaskObserver, TaskObserver
{
    private TaskObserver parentTaskObserver = null;
    private String identifier;
    private float start;
    private float end;
    
    public SubTaskObserverImpl(TaskObserver parentTaskObserver, String identifier, float start, float end)
    {
        this.parentTaskObserver = parentTaskObserver;
        this.identifier = identifier;
        this.start = start;
        this.end = end;
    }
    
    public void taskProgress(float percentageDone)
    {
        float adjustedPercentageDone = start + ((end - start) * percentageDone);
        parentTaskObserver.taskProgress(identifier, adjustedPercentageDone);
    }

    public int getRange()
    {
        return 100;
    }

    public void taskProgress(String identifier, float percentageDone)
    {
        float adjustedPercentageDone = start + ((end - start) * percentageDone);
        parentTaskObserver.taskProgress(identifier, adjustedPercentageDone);
    }
}

