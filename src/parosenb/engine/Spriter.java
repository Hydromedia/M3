package parosenb.engine;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import cs1971.Vec2f;
import cs1971.Vec2i;

public class Spriter {
	public static class SpriteLoader {
		private static ArrayList<SpriteFile> spriteSheets;
		private static boolean isInitialized;
		
	    public static void initialize(ArrayList<SpriteFile> files) {
	    	spriteSheets = files;
	    	isInitialized = false;
	    	for (SpriteFile s : files) {
		    	try {
					s.setImage((ImageIO.read(new File(s.fileName))));
					isInitialized = true;
				} catch (IOException e) {
					System.out.println("Invalid File Specified.");
				}
	    	}
	    }
	    
	    public static void drawImage(Graphics2D g, Vec2f destPosition, Vec2f destSize, Vec2i sourceIndex, int spriteSheetsIndex) {
	    	if (isInitialized && spriteSheetsIndex < spriteSheets.size()) {
	    		g.drawImage(spriteSheets.get(spriteSheetsIndex).getImage(), (int) (destPosition.x*destSize.x), (int) (destPosition.y*destSize.y), (int) (destPosition.x*destSize.x + destSize.x), 
	    				(int) (destPosition.y*destSize.y + destSize.y), 
	    				sourceIndex.x*spriteSheets.get(spriteSheetsIndex).spriteSize + spriteSheets.get(spriteSheetsIndex).padding * (sourceIndex.x + 1), 
	    				sourceIndex.y*spriteSheets.get(spriteSheetsIndex).spriteSize + spriteSheets.get(spriteSheetsIndex).padding * (sourceIndex.y + 1),
	    				sourceIndex.x*spriteSheets.get(spriteSheetsIndex).spriteSize + spriteSheets.get(spriteSheetsIndex).spriteSize + spriteSheets.get(spriteSheetsIndex).padding * (sourceIndex.x + 1), 
	    				sourceIndex.y*spriteSheets.get(spriteSheetsIndex).spriteSize + spriteSheets.get(spriteSheetsIndex).spriteSize + spriteSheets.get(spriteSheetsIndex).padding * (sourceIndex.y + 1),
	    				null);
	    	}
		}
	}
}
