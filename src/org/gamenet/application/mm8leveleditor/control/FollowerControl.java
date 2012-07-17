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

import java.awt.FlowLayout;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.gamenet.application.mm8leveleditor.data.mm6.Follower;

public class FollowerControl extends JPanel
{
    private Follower follower = null;
    
    public FollowerControl(Follower srcFollower)
    {
        super(new FlowLayout(FlowLayout.LEFT));
        
        this.follower = srcFollower;
        
        this.add(new JLabel(" Name:"));
        JFormattedTextField followerNameTextField = new JFormattedTextField(follower.getFollowerName());
        followerNameTextField.setColumns(Math.max(5, follower.getFollowerName().length()));
        followerNameTextField.addPropertyChangeListener("value", new PropertyChangeListener()
                {
                    public void propertyChange(PropertyChangeEvent e)
                    {
                        follower.setFollowerName((String)((JFormattedTextField)e.getSource()).getValue());
                    }
                });
        this.add(followerNameTextField);

        this.add(new JLabel("Picture#"));
        JFormattedTextField pictureNumberTextField = new JFormattedTextField(new Integer(follower.getPictureNumber()));
        pictureNumberTextField.setColumns(5);
        pictureNumberTextField.addPropertyChangeListener("value", new PropertyChangeListener()
                {
                    public void propertyChange(PropertyChangeEvent e)
                    {
                        follower.setPictureNumber(((Number)((JFormattedTextField)e.getSource()).getValue()).intValue());
                    }
                });
        this.add(pictureNumberTextField);

        this.add(new JLabel("Profession#"));
        JFormattedTextField eventNumberTextField = new JFormattedTextField(new Integer(follower.getProfessionNumber()));
        eventNumberTextField.setColumns(5);
        eventNumberTextField.addPropertyChangeListener("value", new PropertyChangeListener()
                {
                    public void propertyChange(PropertyChangeEvent e)
                    {
                        follower.setProfessionNumber(((Number)((JFormattedTextField)e.getSource()).getValue()).intValue());
                    }
                });
        this.add(eventNumberTextField);
    }

    public Object getFollower()
    {
        return follower;
    }
}
