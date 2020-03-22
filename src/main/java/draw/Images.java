package draw;

import util.Digit;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class Images {
	BufferedImage[][] images;
	@SuppressWarnings("resource")
	Images(String type){
		InputStream in;
		switch(type){
			case "2x2": images = new BufferedImage[4][4];
	    	try {
	    		for(int i=0; i<4; i++){
	    			in = this.getClass().getClassLoader().getResourceAsStream("files/30px/" + (i+1) + "_CLUE.png");
	    			images[i][0] = ImageIO.read(in);
	    			in = this.getClass().getClassLoader().getResourceAsStream("files/30px/" + (i+1) + "_NUMBERS.png");
	    			images[i][1] = ImageIO.read(in);
	    			in = this.getClass().getClassLoader().getResourceAsStream("files/30px/" + (i+1) + "_GUESS.png");
	    			images[i][2] = ImageIO.read(in);
	    		}
	    		in = this.getClass().getClassLoader().getResourceAsStream("files/30px/0_HIGHLIGHT.png");
				images[0][3] = ImageIO.read(in);
	    		in = this.getClass().getClassLoader().getResourceAsStream("files/30px/1_HIGHLIGHT.png");
	    		images[1][3] = ImageIO.read(in);
			} catch (IOException e) {
				e.printStackTrace();
			}
	    	break;
			case "3x2": images = new BufferedImage[6][4];
	    	try {
	    		for(int i=0; i<6; i++){
	    			in = this.getClass().getClassLoader().getResourceAsStream("files/30px/" + (i+1) + "_CLUE.png");
	    			images[i][0] = ImageIO.read(in);
	    			in = this.getClass().getClassLoader().getResourceAsStream("files/30px/" + (i+1) + "_NUMBERS.png");
	    			images[i][1] = ImageIO.read(in);
	    			in = this.getClass().getClassLoader().getResourceAsStream("files/30px/" + (i+1) + "_GUESS.png");
	    			images[i][2] = ImageIO.read(in);
	    		}
	    		in = this.getClass().getClassLoader().getResourceAsStream("files/30px/0_HIGHLIGHT.png");
				images[0][3] = ImageIO.read(in);
	    		in = this.getClass().getClassLoader().getResourceAsStream("files/30px/1_HIGHLIGHT.png");
	    		images[1][3] = ImageIO.read(in);
			} catch (IOException e) {
				e.printStackTrace();
			}
	    	break;
			case "3x3": images = new BufferedImage[9][4];
		    	try {
		    		for(int i=0; i<9; i++){
		    			in = this.getClass().getClassLoader().getResourceAsStream("files/30px/" + (i+1) + "_CLUE.png");
		    			images[i][0] = ImageIO.read(in);
		    			in = this.getClass().getClassLoader().getResourceAsStream("files/30px/" + (i+1) + "_NUMBERS.png");
		    			images[i][1] = ImageIO.read(in);
		    			in = this.getClass().getClassLoader().getResourceAsStream("files/30px/" + (i+1) + "_GUESS.png");
		    			images[i][2] = ImageIO.read(in);
		    		}
		    		in = this.getClass().getClassLoader().getResourceAsStream("files/30px/0_HIGHLIGHT.png");
					images[0][3] = ImageIO.read(in);
		    		in = this.getClass().getClassLoader().getResourceAsStream("files/30px/1_HIGHLIGHT.png");
		    		images[1][3] = ImageIO.read(in);
				} catch (IOException e) {
					e.printStackTrace();
				}
		    	break;
			case "4x3": images = new BufferedImage[12][4];
	    	try {
	    		for(int i=0; i<12; i++){
	    			if(i<9){
		    			in = this.getClass().getClassLoader().getResourceAsStream("files/36px/" + (i+1) + "_CLUE_BIG.png");
		    			images[i][0] = ImageIO.read(in);
		    			in = this.getClass().getClassLoader().getResourceAsStream("files/36px/" + (i+1) + "_NUMBERS_BIG.png");
		    			images[i][1] = ImageIO.read(in);
		    			in = this.getClass().getClassLoader().getResourceAsStream("files/36px/" + (i+1) + "_GUESS_BIG.png");
		    			images[i][2] = ImageIO.read(in);
	    			}
	    			else{
		    			in = this.getClass().getClassLoader().getResourceAsStream("files/36px/" + (char)('A'+(i-9)) + "_CLUE_BIG.png");
		    			images[i][0] = ImageIO.read(in);
		    			in = this.getClass().getClassLoader().getResourceAsStream("files/36px/" + (char)('A'+(i-9)) + "_NUMBERS_BIG.png");
		    			images[i][1] = ImageIO.read(in);
		    			in = this.getClass().getClassLoader().getResourceAsStream("files/36px/" + (char)('A'+(i-9)) + "_GUESS_BIG.png");
		    			images[i][2] = ImageIO.read(in);
	    			}
	    		}
	    		in = this.getClass().getClassLoader().getResourceAsStream("files/36px/0_HIGHLIGHT_BIG.png");
				images[0][3] = ImageIO.read(in);
	    		in = this.getClass().getClassLoader().getResourceAsStream("files/36px/1_HIGHLIGHT_BIG.png");
	    		images[1][3] = ImageIO.read(in);
			} catch (IOException e) {
				e.printStackTrace();
			}
	    	break;
			 case "4x4": images = new BufferedImage[16][4];
	    	try {
	    		for(int i=0; i<16; i++){
	    			if(i<9){
		    			in = this.getClass().getClassLoader().getResourceAsStream("files/36px/" + (i+1) + "_CLUE_BIG.png");
		    			images[i][0] = ImageIO.read(in);
		    			in = this.getClass().getClassLoader().getResourceAsStream("files/36px/" + (i+1) + "_NUMBERS_BIG.png");
		    			images[i][1] = ImageIO.read(in);
		    			in = this.getClass().getClassLoader().getResourceAsStream("files/36px/" + (i+1) + "_GUESS_BIG.png");
		    			images[i][2] = ImageIO.read(in);
	    			}
	    			else{
		    			in = this.getClass().getClassLoader().getResourceAsStream("files/36px/" + (char)('A'+(i-9)) + "_CLUE_BIG.png");
		    			images[i][0] = ImageIO.read(in);
		    			in = this.getClass().getClassLoader().getResourceAsStream("files/36px/" + (char)('A'+(i-9)) + "_NUMBERS_BIG.png");
		    			images[i][1] = ImageIO.read(in);
		    			in = this.getClass().getClassLoader().getResourceAsStream("files/36px/" + (char)('A'+(i-9)) + "_GUESS_BIG.png");
		    			images[i][2] = ImageIO.read(in);
	    			}
	    		}
	    		in = this.getClass().getClassLoader().getResourceAsStream("files/36px/0_HIGHLIGHT_BIG.png");
				images[0][3] = ImageIO.read(in);
	    		in = this.getClass().getClassLoader().getResourceAsStream("files/36px/1_HIGHLIGHT_BIG.png");
	    		images[1][3] = ImageIO.read(in);
			} catch (IOException e) {
				e.printStackTrace();
			}
	    	break;
		}
	}
	public BufferedImage getImg(int pos, Digit.ValueType type){
		if(type == Digit.ValueType.NUMBER)
			return images[pos-1][1];
		else if(type == Digit.ValueType.CLUE)
			return images[pos-1][0];
		else if(type == Digit.ValueType.GUESS)
			return images[pos-1][2];
		return null;
	}
	public BufferedImage getImg(int pos){
		return images[pos][3];
	}
}
