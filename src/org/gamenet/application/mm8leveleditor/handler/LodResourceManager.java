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

import org.gamenet.application.mm8leveleditor.lod.LodEntry;
import org.gamenet.application.mm8leveleditor.lod.LodResource;

public class LodResourceManager
{
    public LodResourceHandler getHandlerFor(
        LodResource lodResource)
    {
    	ResourceServer.getInstance().setLodFile(((LodEntry)lodResource).getLodFile());

        if (lodResource.getName().toLowerCase().equals("npcdata.bin"))
            return new NPCDataBinHandler();
        else if (lodResource.getName().toLowerCase().equals("party.bin"))
            return new PartyBinHandler();
        else if (lodResource.getName().toLowerCase().equals("dsft.bin"))
            return new DsftBinHandler();
        else if (lodResource.getName().toLowerCase().equals("dsounds.bin"))
            return new DSoundsBinHandler();
        else if (lodResource.getName().toLowerCase().equals("dtile.bin"))
            return new DTileBinHandler();
        else if (lodResource.getName().toLowerCase().equals("dtft.bin"))
            return new DTftBinHandler();
        else if (lodResource.getName().toLowerCase().equals("dpft.bin"))
            return new DPftBinHandler();
        else if (lodResource.getName().toLowerCase().equals("dift.bin"))
            return new DIftBinHandler();
        else if (lodResource.getName().toLowerCase().equals("dobjlist.bin"))
            return new DObjListBinHandler();
        else if (lodResource.getName().toLowerCase().equals("dchest.bin"))
            return new DChestBinHandler();
        else if (lodResource.getName().toLowerCase().equals("ddeclist.bin"))
            return new DDecListBinHandler();
        else if (lodResource.getName().toLowerCase().equals("dmonlist.bin"))
            return new DMonListBinHandler();
        else if (lodResource.getName().toLowerCase().equals("doverlay.bin"))
            return new DOverlayBinHandler();

        String resourceType = lodResource.getResourceType();

        try
        {
            if (resourceType.equals("pal"))
                return new PalHandler();
            if (resourceType.equals("txt"))
                return new TxtHandler();
            if (resourceType.equals("pcx"))
                return new PcxHandler();
            if (resourceType.equals("wav"))
            {
                try
                {
                    return new WavHandler();
                }
                catch (java.lang.NoClassDefFoundError e)
                {
                    e.printStackTrace();
                    return new ErrorHandler("Unable to initialize wav player.   Is jfm.jar in the classpath?");
                }
            }
            if (resourceType.equals("str"))
                return new StrHandler();
            if (resourceType.equals("tga"))
                return new BitmapHandler();
            if (resourceType.equals("4tga"))
                return new MultipleBitmapHandler();
            if (resourceType.equals("sprite"))
                return new BitmapHandler();
            if (resourceType.equals("smk"))
                return new SmackHandler();
            if (resourceType.equals("bik"))
                return new BinkHandler();
            if (resourceType.equals("blv"))
                return new BlvHandler();
            if (resourceType.equals("dlv"))
                return new DlvHandler();
            if (resourceType.equals("odm"))
                return new OdmHandler();
            if (resourceType.equals("ddm"))
                return new DdmHandler();
            if (resourceType.equals("evt"))
                return new EvtHandler();
            
            return new DataHandler();
        }
        catch (RuntimeException e)
        {
            e.printStackTrace();
            return new ErrorHandler("Unabled to initialize viewer for resource '" + lodResource.getName() + "': " + e.getMessage());
        }
    }
    
    public Class[] getSupportedHandlers() {
        return new Class[] {
            PalHandler.class, TxtHandler.class, PcxHandler.class,
            WavHandler.class, StrHandler.class, BitmapHandler.class,
            SmackHandler.class, DataHandler.class};
    }
}
