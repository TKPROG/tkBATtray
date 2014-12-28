package de.tkprog.tkBATtray.main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class IconImageGen {
	
	private int type = TYPE_SIMPLE_TWO_COLOUR_WITH_PRECENTAGE;
	public static final int TYPE_SIMPLE_TWO_COLOUR = 0;
	public static final int TYPE_ONLY_PERCENTAGE = 1;
	public static final int TYPE_SIMPLE_TWO_COLOUR_WITH_PRECENTAGE = 2;
	
	private int width = 20;
	private int height = 20;
	private float lastWorkingFontSize = 10;
	
	public BufferedImage getImage(BatteryInformation_ArchLinux BIAL){
		BufferedImage out = null;
		Graphics g = null;
		double emptied;
		double filled;
		String output;
		switch(type){
			case TYPE_SIMPLE_TWO_COLOUR:
				emptied = (double)(100-BIAL.getCapacity())/100.0d;
				filled = (double)BIAL.getCapacity()/100.0d;
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
				if(BIAL.getStatus().equalsIgnoreCase("charging")){
					g.setColor(Color.YELLOW);
				}
				g.fillRect(0, 0, out.getWidth(), (int)((double)out.getHeight()*emptied));
				g.setColor(new Color(0,255,0,255));
				g.fillRect(0, (int)((double)out.getHeight()*emptied), out.getWidth(), (int)((double)out.getHeight()*filled));
				break;
			case TYPE_ONLY_PERCENTAGE:
				out = new BufferedImage(getWidth(),getHeight(),BufferedImage.TYPE_4BYTE_ABGR);
				g = out.getGraphics();
				g.setColor(Color.blue);
				g.fillRect(0,0,out.getWidth(),out.getHeight());
				g.setColor(Color.white);
				output = String.valueOf(BIAL.getCapacity());
				g.setFont(g.getFont().deriveFont(checkFontSize(output, width, height, g)));
				g.drawString(output, (int)(((double)width/(double)2)-((double)g.getFontMetrics().stringWidth(output)/(double)2)), (int)((double)height/(double)2)+(int)((double)g.getFontMetrics().getHeight()/(double)2)-3);
				break;
			case TYPE_SIMPLE_TWO_COLOUR_WITH_PRECENTAGE:
				emptied = (double)(100-BIAL.getCapacity())/100.0d;
				filled = (double)BIAL.getCapacity()/100.0d;
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
				if(BIAL.getStatus().equalsIgnoreCase("charging")){
					g.setColor(Color.YELLOW);
				}
				g.fillRect(0, 0, out.getWidth(), (int)((double)out.getHeight()*emptied));
				g.setColor(new Color(0,255,0,255));
				g.fillRect(0, (int)((double)out.getHeight()*emptied), out.getWidth(), (int)((double)out.getHeight()*filled));
				output = String.valueOf(BIAL.getCapacity());
				g.setColor(Color.black);
				g.setFont(g.getFont().deriveFont(checkFontSize(output, width, height, g)));
				g.drawString(output, (int)(((double)width/(double)2)-((double)g.getFontMetrics().stringWidth(output)/(double)2)), (int)((double)height/(double)2)+(int)((double)g.getFontMetrics().getHeight()/(double)2)-3);
				break;
		}
		return out;
	}

	private float checkFontSize(String string, float width2, float height2, Graphics g) {
		float maxSize = checkFontSize2(string, width2, height2, g, g.getFont().getSize(),128, 1);
		float minSize = checkFontSize2(string, width2, height2, g, g.getFont().getSize(),0, -1);
		if(minSize<=0){
			lastWorkingFontSize = maxSize;
			return maxSize;
		}
		lastWorkingFontSize = ((maxSize>minSize)?(maxSize):(minSize));
		return ((maxSize>minSize)?(maxSize):(minSize));
	}

	/**
	 * This function tries to get the string with the given Font in g to fit into the width2 and heigh2 area. To do this it works through all possible sizes from size to max with steps of inc.
	 * @param string The string which has to be fitted in
	 * @param width2 The width of the area
	 * @param height2 The height of the area
	 * @param g The given Graphics Context with its font
	 * @param size The starting size
	 * @param max
	 * @param inc
	 * @return The best possible area
	 */
	private float checkFontSize2(String string, float width2, float height2, Graphics g, float size, float max, float inc) {
		float outSize = size;
		if(inc > 0 && !(size<max)){
			System.err.println("IconImageGen.checkFontSize2(...): inc ("+inc+") is bigger than 0, but size ("+size+") is smaller than max ("+max+")");
			return size;
		}
		if(inc < 0 && !(size>max)){
			System.err.println("IconImageGen.checkFontSize2(...): inc ("+inc+") is bigger than 0, but size ("+size+") is smaller than max ("+max+")");
			return size;
		}
		if(inc == 0){
			System.err.println("IconImageGen.checkFontSize2(...): inc ("+inc+") is 0");
			return size;
		}
		Graphics g2 = g.create();
		float lastWidth = 0;
		float lastHeight = 0;
		float lastSize = 0;
		for(float a = size; a <= max;a+=inc){
			g2.setFont(g2.getFont().deriveFont(a));
			if(g2.getFontMetrics().stringWidth(string)>lastWidth && g2.getFontMetrics().stringWidth(string)<=width2){
				if( (g2.getFontMetrics().getHeight()>lastHeight || g2.getFontMetrics().getHeight()==lastHeight) && g2.getFontMetrics().getHeight() <= height2){
					lastWidth = g2.getFontMetrics().stringWidth(string);
					lastHeight = g2.getFontMetrics().getHeight();
					lastSize = a;
				}
			}
			if(g2.getFontMetrics().getHeight()>lastHeight && g2.getFontMetrics().getHeight()<=height2){
				if( (g2.getFontMetrics().stringWidth(string)>lastWidth || g2.getFontMetrics().stringWidth(string)==lastWidth) && g2.getFontMetrics().stringWidth(string)<=width2){
					lastWidth = g2.getFontMetrics().stringWidth(string);
					lastHeight = g2.getFontMetrics().getHeight();
					lastSize = a;
				}
			}
		}
		outSize = lastSize;
		return outSize;
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
