package de.tkprog.tkBATtray.main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class IconImageGen {
	
	private int type = TYPE_SIMPLE_TWO_COLOUR;
	public static final int TYPE_SIMPLE_TWO_COLOUR = 0;
	public static final int TYPE_ONLY_PERCENTAGE = 1;
	public static final int TYPE_SIMPLE_TWO_COLOUR_WITH_PRECENTAGE = 2;
	
	private int width = 20;
	private int height = 20;
	
	public BufferedImage getImage(BatteryInformation_ArchLinux BIAL){
		BufferedImage out = null;
		Graphics g = null;
		switch(type){
			case TYPE_SIMPLE_TWO_COLOUR:
				double emptied = (double)(100-BIAL.getCapacity())/100.0d;
				double filled = (double)BIAL.getCapacity()/100.0d;
				if(filled>1.0d){
					filled = 1.0d;
				}
				else if(filled<0.0d){
					filled = 0.0d;
				}
				if(emptied>1.0d){
					emptied = 1.0d;
				}
				else if(emptied<0.0d){
					emptied = 0.0d;
				}
				if(width<=0){
					return null;
				}
				if(height<=0){
					return null;
				}
				
				out = new BufferedImage(getWidth(),getHeight(),BufferedImage.TYPE_4BYTE_ABGR);
				g = out.getGraphics();
				g.setColor(new Color(255,0,0,255));
				g.fillRect(0, 0, out.getWidth(), (int)((double)out.getHeight()*emptied));
				g.setColor(new Color(0,255,0,255));
				g.fillRect(0, (int)((double)out.getHeight()*emptied), out.getWidth(), (int)((double)out.getHeight()*filled));
				break;
			case TYPE_ONLY_PERCENTAGE:
				out = new BufferedImage(getWidth(),getHeight(),BufferedImage.TYPE_4BYTE_ABGR);
				g = out.getGraphics();
				g.setColor(Color.black);
				g.fillRect(0,0,out.getWidth(),out.getHeight());
				g.setColor(Color.white);
				g.drawString(String.valueOf(BIAL.getCapacity()), 0, 0);
				break;
		}
		return out;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}
}
