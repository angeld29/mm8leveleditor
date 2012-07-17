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
package org.gamenet.application.mm8leveleditor.data;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.gamenet.swing.controls.NumberLabelValueHolder;

public class DateTime
{
    static public NumberLabelValueHolder[] getMonthOptions()
    {
        return new NumberLabelValueHolder[] {
                new NumberLabelValueHolder(0, "January"),
                new NumberLabelValueHolder(1, "February"),
                new NumberLabelValueHolder(2, "March"),
                new NumberLabelValueHolder(3, "April"),
                new NumberLabelValueHolder(4, "May"),
                new NumberLabelValueHolder(5, "June"),
                new NumberLabelValueHolder(6, "July"),
                new NumberLabelValueHolder(7, "August"),
                new NumberLabelValueHolder(8, "September"),
                new NumberLabelValueHolder(9, "October"),
                new NumberLabelValueHolder(10, "November"),
                new NumberLabelValueHolder(11, "December") };
    }

    private static int BASE_YEAR = 1165;

    static public int getYearForValue(long value)
    {
        return BASE_YEAR + (int) (value / 256L / 60L / 24L / 28L / 12L);
    }

    static public int getMonthOfYearForValue(long value)
    {
        return (int) ((value / 256L / 60L / 24L / 28L) % 12L);
    }

    static public int getDayOfMonthForValue(long value)
    {
        return (int) ((value / 256L / 60L / 24L) % 28L);
    }

    static public int getHourOfDayForValue(long value)
    {
        return (int) ((value / 256L / 60L) % 24L);
    }

    static public int getMinuteOfHourForValue(long value)
    {
        return (int) ((value / 256L) % 60L);
    }

    static public int getTickOfMinuteForValue(long value)
    {
        return (int) (value % 256L);
    }

    static public long createDateTime(int year, int month, int day, int hour,
            int minute, int tick)
    {
        return tick + (256L * minute) + (256L * 60L * hour)
                + (256L * 60L * 24L * day) + (256L * 60L * 24L * 28L * month)
                + (256L * 60L * 24L * 28L * 12L * (year - BASE_YEAR));
    }

    static public long updateYearForValue(int year, long value)
    {
        return createDateTime(year, getMonthOfYearForValue(value),
                getDayOfMonthForValue(value), getHourOfDayForValue(value),
                getMinuteOfHourForValue(value), getTickOfMinuteForValue(value));
    }

    static public long updateMonthOfYearForValue(int month, long value)
    {
        return createDateTime(getYearForValue(value), month,
                getDayOfMonthForValue(value), getHourOfDayForValue(value),
                getMinuteOfHourForValue(value), getTickOfMinuteForValue(value));
    }

    static public long updateDayOfMonthForValue(int day, long value)
    {
        return createDateTime(getYearForValue(value),
                getMonthOfYearForValue(value), day,
                getHourOfDayForValue(value), getMinuteOfHourForValue(value),
                getTickOfMinuteForValue(value));
    }

    static public long updateHourOfDayForValue(int hour, long value)
    {
        return createDateTime(getYearForValue(value),
                getMonthOfYearForValue(value), getDayOfMonthForValue(value),
                hour, getMinuteOfHourForValue(value),
                getTickOfMinuteForValue(value));
    }

    static public long updateMinuteOfHourForValue(int minute, long value)
    {
        return createDateTime(getYearForValue(value),
                getMonthOfYearForValue(value), getDayOfMonthForValue(value),
                getHourOfDayForValue(value), minute,
                getTickOfMinuteForValue(value));
        //        return (value / 256L / 60L) + (minute * 256L) + (value % 256L);
    }

    static public long updateTickOfMinuteForValue(int tick, long value)
    {
        return createDateTime(getYearForValue(value),
                getMonthOfYearForValue(value), getDayOfMonthForValue(value),
                getHourOfDayForValue(value), getMinuteOfHourForValue(value),
                tick);
        //        return (value / 256L) + tick;
    }

    public static String toString(long value)
    {
        return getMonthOptions()[getMonthOfYearForValue(value)].getLabel() + " "
                + (getDayOfMonthForValue(value) + 1) + ", " + getYearForValue(value) + " "
                + getHourOfDayForValue(value) + ":"
                + getMinuteOfHourForValue(value) + ":"
                + getTickOfMinuteForValue(value);
    }

public static Long parseLong(String dateString)
    {
    	// This doesn't actually work since seconds aren't 0 to 256 in value
        SimpleDateFormat parser = new SimpleDateFormat("MMMM d, yyyy HH:mm:ss");
        try
        {
            Date date = parser.parse(dateString);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
        	return new Long(createDateTime(calendar.get(Calendar.YEAR),
        	        calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH) - 1,
        	        calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE),
        	        calendar.get(Calendar.SECOND)));
        }
        catch (ParseException exception)
        {
            exception.printStackTrace();
            return null;
        }
    }}
