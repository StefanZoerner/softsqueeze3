/*
 *   SoftSqueeze Copyright (c) 2004 Richard Titmuss
 *
 *   This file is part of SoftSqueeze.
 *
 *   SoftSqueeze is free software; you can redistribute it and/or modify
 *   it under the terms of the GNU General Public License as published by
 *   the Free Software Foundation; either version 2 of the License, or
 *   (at your option) any later version.
 *
 *   SoftSqueeze is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU General Public License for more details.
 *
 *   You should have received a copy of the GNU General Public License
 *   along with SoftSqueeze; if not, write to the Free Software
 *   Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 *
 */

package org.titmuss.softsqueeze.display;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

/**
 * @author Richard Titmuss
 *
 */
public class FrameE implements Frame {
    private byte frame[];
    
    public FrameE(byte buf[], int offset, int len) {
        frame = new byte[len];
        System.arraycopy(buf, offset, frame, 0, len);
    }
    
	public void render(Graphics g, int width, int offset, Color color[]) {
	    Graphics2D g2 = (Graphics2D)g;
	    
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.CLEAR, 1.0f));
	    g2.fillRect(0, 0, LcdDisplay.SCREEN_WIDTH, LcdDisplay.SCREEN_HEIGHT);
	    g2.setComposite(AlphaComposite.Src);

		g.setColor(color[3]);
		
		int x = 0;
		int end = Math.min( (offset + width)*4, frame.length);
		for (int i = offset*4; i < end; ) {
		    int y = 0;
		    int pc = 0;
		    int py = 0;
		    
			for (int j = 0; j < 4; j++) {
		        byte dots = frame[i++];
		        
		        for (int k = 7; k >= 0; k--, y++) {
		            int c = (dots >> k) & 0x01;
		            
		            if (pc == c)
		                continue;
		            
		            if (pc > 0) {
		                g.drawLine(x, py, x, y-1);
		            }
		            
		            pc = c;
		            py = y;
		        }		        
		    }
			
			if (pc > 0) {
			    g.drawLine(x, py, x, y-1);
			}
			
			x++;
		}
	}
}
