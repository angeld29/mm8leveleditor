THIS FILE IS NOW OUT OF DATE.

See build/site/index.html


GENERAL
=======
MM8LevelEditor is a tool for extracting, viewing, and modifying Might and Magic 6, Might and Magic 7, and Might and Magic 8 game resources.
The eventual goal is to be able to customize these resources, to the point of creating new content for MM6-8.

This tool was inspired by UnLod and MM7View, and almost encompasses all functionality of both.  In fact, this tool is an expansion of Sil's UnLod 2.0 Beta tool.  You can find the last release of UnLod source at the mm8leveleditor project.

Using this tool requires that you own and have installed MM6, MM7, or MM8 as this tool does not provide any MM6-8 content.

This tool also bundles Java Media Foundation (JMF) for wav file support.  http://java.sun.com/products/java-media/jmf/ for more details.

The MM8LevelEditor Project is hosted at SourceForge.net, which generously provides all development resources for creating and maintaining this package.

http://sourceforge.net/projects/mm8leveleditor


USAGE
=====
This tool requires Java Runtime (JRE) 1.4.2 or greater.  You may already have this installed.  From a command prompt type "java -version", and if the result is "java 1.4" or higher, you should be set.

Currently this can be retrieved at the following URL if you do not already have it installed.

The full development environment is available at

http://java.sun.com/j2se/1.4.2/download.html

Supposedly, just the basic java runtime can be retrieved at

http://java.sun.com/getjava/


Once java is installed, you should be able to launch this program with "java -jar mm8leveleditor.jar", or if you're using windows, you can probably just double-click on the mm8leveleditor.jar file.

You might need to grant java more memory (defaults to 64Mb total) for large data resources.   Use "java -Xms1024M -Xmx1024M -jar mm8leveleditor.jar" to do this.

Technical note: The jar file itself contains the classpath information needed so that it knows to look for unlod-base.jar and jmf.jar (only used for wav file support) in the same folder as mm8leveleditor.jar.  If you choose to run this using an alternative method, note that all three jar files (mm8leveleditor.jar, unlod-base.jar, jmf.jar) need to be in your classpath, and the Class to execute is "org.gamenet.application.mm8leveleditor.MM8LevelEditor"

The preferences for this application are stored in a file called "MM8LevelEditor.properties" which will probably be created in your current working directory.  Some future change may make the location of this file easier to deal with.  For now, you should try to keep your working directory in the same place as your jar files.   If MM8LevelEditor is unable to find this file, it will create a new one.


PROBLEMS/QUESTIONS/SUGGESTIONS
==============================
Please feel free to contact me for any assistance, or if you'd like to help out with this project.

If you contact me, please specify "mm8leveleditor" in the email subject and please use plain-text (not HTML) email.   Otherwise your message is very likely to be detected as spam.  It's always possible it might end up getting caught in the spam filter anyway, so if you don't hear from me within a few days, you may want to try again.

I'm always looking for assistance on this project.   There's web pages that need to be designed, documentation that needs to be written, testing that needs to be done, and setting up the various administrative functions on sourceforge (mailing lists, bug tracker, etc) as well as general programming on the project itself. 


RELEASE NOTES
=============
3.44	Decoded "artifact found" bits in saved game.
		Decoded item held on cursor when saving game.
		Decoded Chest Unknown1 (DChest id -- picture)

3.43	Party.bin decoding.
		Integrity checking of file formats while parsing.
		Support for MM7 and MM8 bin files (excluding party.bin and dsft.bin).
		Reorganized class and data layout.

3.42	Indoor level decoding (blv and dlv)
		Fix bug in computing new size of odm files (was always using original size)
		Added ability to update DSFT.bin files.
		Decoded some DSFT.bin fields
		party.bin decoding, although most is not yet visible to the user
		Dchest.bin, DDecList.bin, DIft.Bin, DMonList.bin, DObjList.bin, DSounds.bin, DTft.bin, and DTile.bin basic decoding.

3.41	More Event decoding
		Add/Delete/Move around events
		Allow save game editing (saved games are new.lod files)
		Handler for npcdata.bin resources.
		Handler for dsft.bin resources.
		Added Monster decoding for odm files.
		Added Item and Chest Content decoding for ddm files.
		Add/Delete/Move around 3d objects, sprites, monsters, creatures, items, and chest contents.
		Improved control for comparing and manipulating unknown data.
		Minor decoding of ddm creatures.
		
3.40	Display progress monitor panel for import operations
		Provide better error reporting during import/export operations
		Provide cancel import functionality
		Select filename for new rebuilt lodfile
		Rebuild a lod file directly from a resource viewer/editor.  (odm, evt, str, txt, and raw data displays).
		Fixed binary data editor input to properly handle the various display modes.
		Display offset for binary data editor.
		Toggle offset display between hex and decimal on offset-column click.
		Provide user-specified blank cell padding to align binary data horizontally.
		Display progress monitor panel when loading data to display
		Quick-update the current lod resource by appending updated content to the end of the lod file.
		Quick-update a lod resource by appending the contents of a selected file to the end of the lod file.
		Ability to view any lod resource with Data handler
		Peliminary support for displaying/editing outdoor level maps (tiles, heights, 3d object locations, sprite locations)
		Peliminary support for displaying/editing event file instructions

3.30	Ability to export and import all lod file resources (Thanks to Gabor Toth for keeping me going).
		Start on displaying Outdoor level maps (Thanks to Richard Johnson for outdoor file format decoding).
		Fix to display/extract/import all four components of a Bitmap.lod tile.
		Str resources now extracted as/imported from a txt file.
		Removed LodEntryHandlerManager code and preference as it's no longer necessary in an open-source project.

3.10	Files now have consistent file suffixes when saved.
		TGA and Sprite file types are automatically extracted as Bitmaps.

3.00	Initial Release.

FUTURE PLANS
============
Currently, work is in progress to edit indoor and outdoor maps.

Mike Kienenberger
mkienenb@gmail.com
April 26, 2005


Note: Might and Magic 6, 7, and 8 are owned by their respective copyright holders (3D0/New World Computing at the time of game publication, possibly UbiSoft now.)
