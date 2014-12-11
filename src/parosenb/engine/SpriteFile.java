package parosenb.engine;

import java.awt.image.BufferedImage;

public class SpriteFile {
	public final String fileName;
	public final int spriteSize;
	public final int padding;
	private BufferedImage image;
	
	public void setImage(BufferedImage image){
		this.image = image;
	}
	
	public BufferedImage getImage(){
		return this.image;
	}
	
	public SpriteFile(String fileName, int spriteSize, int padding){
		this.fileName = fileName;
		this.padding = padding;
		this.spriteSize = spriteSize;
	}
}
