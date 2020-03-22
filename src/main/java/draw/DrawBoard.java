package draw;

import util.Board;
import util.Digit;
import util.Highlight;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class DrawBoard extends JPanel{
	/**
	 * 
	 */
	private static final long serialVersionUID = 6189729972338696424L;
	
	private int boardRowPixelLength; 
	private int boardColumnPixelLength; 
	private int margin;
	private int boxSize;
	private int lineBoldSize;
	private int lineThinSize;
	private int panelWidth;
	private int panelHeight;
	private int numSelectorPosX;
	private int numSelectorPosY;
	private int modSelectorPosX;
	private int modSelectorPosY;
	private int selectedNumber = 1;
	private int posSelected;

	private int selectedMode = 0;
	private Board sudokuBoard;
	private ArrayList<Highlight> toHighlight;
	private Images imgs;
	private String solverType;
	
	
	public DrawBoard(Board sb, ArrayList<Highlight> high, String type){
		sudokuBoard = sb;
		
		margin = 30;
		if(sudokuBoard.getNumberCount()>= 12)
			boxSize = 36;
		else
			boxSize = 30;
		
		lineBoldSize = 4;
		lineThinSize = 2;
		posSelected = 0;
		
		boardRowPixelLength = (sudokuBoard.getBoxHeight() + 1) * lineBoldSize + sudokuBoard.getBoxHeight() * (sudokuBoard.getBoxWidth() - 1) * lineThinSize + sudokuBoard.getNumberCount() * boxSize;
		boardColumnPixelLength = (sudokuBoard.getBoxWidth() + 1) * lineBoldSize + sudokuBoard.getBoxWidth() * (sudokuBoard.getBoxHeight() - 1) * lineThinSize + sudokuBoard.getNumberCount() * boxSize;
		if(sudokuBoard.getNumberCount() == 16){
			panelHeight = 2*margin + boardRowPixelLength;
			panelWidth = 3*margin + 2*boxSize + lineThinSize + boardColumnPixelLength;
		}
		else {
			panelWidth = 2*margin + boardRowPixelLength;
			panelHeight = 3*margin + 2*boxSize + lineThinSize + boardColumnPixelLength;
		}
		setFocusable(true);
		toHighlight = high;
		solverType = "";
		setPreferredSize(new Dimension(panelWidth, panelHeight));
		int PointX;
		int PointY = margin + lineBoldSize;
		for(int i = 0; i<sudokuBoard.getNumberCount(); i++){
			PointX = margin + lineBoldSize;
			for(int j=0; j<sudokuBoard.getNumberCount(); j++){
				sb.setPosX(sb.getPos(j, i), PointX);
				sb.setPosY(sb.getPos(j, i), PointY);
				PointX += boxSize + lineThinSize;
				if((j+1) % sudokuBoard.getBoxWidth() == 0)
					PointX += lineBoldSize - lineThinSize;
			}
			PointY += boxSize + lineThinSize;
			if((i+1) % sudokuBoard.getBoxHeight() == 0)
				PointY += lineBoldSize - lineThinSize;
		}
		imgs = new Images(type);
	}
	
	public void paintBoard(Graphics g){
		g.setColor(Color.BLACK);
		int PointToDraw = margin;
		g.fillRect(PointToDraw, margin, lineBoldSize, boardColumnPixelLength);
		PointToDraw += boxSize + lineBoldSize;
        for (int i = 1; i < sudokuBoard.getNumberCount() + 1; i++){
            if (i % sudokuBoard.getBoxWidth() == 0){
                g.fillRect(PointToDraw, margin, lineBoldSize, boardColumnPixelLength);
                PointToDraw += boxSize + lineBoldSize;
            }
            else{
                g.fillRect(PointToDraw, margin, lineThinSize, boardColumnPixelLength);
                PointToDraw += boxSize + lineThinSize;
            }

        }
        
        PointToDraw = margin;
        g.fillRect(margin, PointToDraw, boardRowPixelLength, lineBoldSize);
        PointToDraw += boxSize + lineBoldSize;
        for (int i = 1; i < sudokuBoard.getNumberCount() + 1; i++){
            if (i % sudokuBoard.getBoxHeight() == 0) {
                g.fillRect(margin, PointToDraw, boardRowPixelLength, lineBoldSize);
                PointToDraw += boxSize + lineBoldSize;
            }
            else
            {
                g.fillRect(margin, PointToDraw, boardRowPixelLength, lineThinSize);
                PointToDraw += boxSize + lineThinSize;
            }
        }
	}
	public void paintChoosers(Graphics g){
		if(sudokuBoard.getNumberCount() == 16){
			int chooserLengthInPixel = sudokuBoard.getNumberCount()*boxSize + lineThinSize * (sudokuBoard.getNumberCount() + 1);
	        int chooserXPosition = numSelectorPosX =  2*margin + boardRowPixelLength;
	        int chooserYPosition =  panelHeight/2 - (chooserLengthInPixel / 2);
	        g.fillRect(chooserXPosition-lineThinSize, chooserYPosition,lineThinSize, chooserLengthInPixel);
	        g.fillRect(chooserXPosition+boxSize, chooserYPosition, lineThinSize, chooserLengthInPixel);
	    	for(int i=0; i<sudokuBoard.getNumberCount() + 1; i++){
	    		if(i+1 == selectedNumber){
	    			g.drawImage(imgs.getImg(0), chooserXPosition, chooserYPosition+lineThinSize, this);
	    		}
	    		g.fillRect(chooserXPosition, chooserYPosition, boxSize,lineThinSize);
	    		chooserYPosition += boxSize + lineThinSize;
	        }
	        int numPosition = numSelectorPosY =  panelHeight/2 - (chooserLengthInPixel / 2) + lineThinSize;
	    	for(int i=1; i<sudokuBoard.getNumberCount() + 1; i++){
	    		g.drawImage(imgs.getImg(i, Digit.VALUE_TYPE.NUMBER), chooserXPosition, numPosition, this);
				numPosition += boxSize + lineThinSize;
	        }
	    	
	    	modSelectorPosY = panelHeight / 2;
	    	modSelectorPosX = chooserXPosition + boxSize + lineThinSize*3;
	    	g.drawRect(modSelectorPosX, modSelectorPosY, boxSize, boxSize);
	    	g.drawRect(modSelectorPosX, modSelectorPosY-boxSize, boxSize, boxSize);
	    	if(selectedMode == 1){
	        	g.fillRect(modSelectorPosX, modSelectorPosY, boxSize, boxSize);
	    	}
	    	else if(selectedMode == 0){
	        	g.fillRect(modSelectorPosX, modSelectorPosY-boxSize, boxSize, 	boxSize);
	    	}
		}
		else{
	        int chooserLengthInPixel = sudokuBoard.getNumberCount()*boxSize + lineThinSize * (sudokuBoard.getNumberCount() + 1);
	        int chooserYPosition = numSelectorPosY =  2*margin + boardColumnPixelLength;
	        int chooserXPosition =  panelWidth/2 - (chooserLengthInPixel / 2);
	        g.fillRect(chooserXPosition, chooserYPosition-lineThinSize,chooserLengthInPixel, lineThinSize);
	        g.fillRect(chooserXPosition, chooserYPosition+boxSize,chooserLengthInPixel, lineThinSize);
	    	for(int i=0; i<sudokuBoard.getNumberCount() + 1; i++){
	    		if(i+1 == selectedNumber){
	    			g.drawImage(imgs.getImg(0), chooserXPosition+lineThinSize, chooserYPosition, this);
	    		}
	    		g.fillRect(chooserXPosition, chooserYPosition, lineThinSize, boxSize);
	    		chooserXPosition += boxSize + lineThinSize;
	        }
	        int numPosition = numSelectorPosX =  panelWidth/2 - (chooserLengthInPixel / 2) + lineThinSize;
			for(int i=1; i<sudokuBoard.getNumberCount() + 1; i++){
				g.drawImage(imgs.getImg(i, Digit.VALUE_TYPE.NUMBER), numPosition, chooserYPosition, this);
				numPosition += boxSize + lineThinSize;
	        }
	    	
	    	modSelectorPosX = panelWidth / 2;
	    	modSelectorPosY = chooserYPosition + boxSize + lineThinSize*3;
	    	g.drawRect(modSelectorPosX, modSelectorPosY, boxSize, boxSize);
	    	g.drawRect(modSelectorPosX-boxSize, modSelectorPosY, boxSize, boxSize);
	    	if(selectedMode == 1){
	        	g.fillRect(modSelectorPosX, modSelectorPosY, boxSize, boxSize);
	    	}
	    	else if(selectedMode == 0){
	        	g.fillRect(modSelectorPosX-boxSize, modSelectorPosY, boxSize, boxSize);
	    	}
		}
	}
	public void paintNumbers(Graphics g){
		for(int i = 0; i<sudokuBoard.getNumberCount(); i++){
			for(int j=0; j<sudokuBoard.getNumberCount(); j++){
				int toX = sudokuBoard.getPosX(sudokuBoard.getPos(j, i)) ;
				int toY = sudokuBoard.getPosY(sudokuBoard.getPos(j, i)) ;
				Digit current = sudokuBoard.getDigit(j,i);
				for(int k=0; k<current.size(); k++){
			        g.drawImage(imgs.getImg(current.get(k), current.getType()), toX,toY, this);
				}
			}
		}
	}	
	
	@Override
	public void paint(Graphics g){
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, panelWidth, panelHeight);
		paintBoard(g);
		paintChoosers(g);
		while(!toHighlight.isEmpty()){
			Highlight h = toHighlight.remove(0);
			g.setColor(h.getColor());
			g.fillRect(h.getX(), h.getY(), boxSize, boxSize);
		}
		if(posSelected != -1)
			g.drawImage(imgs.getImg(1), sudokuBoard.getPosX(posSelected),sudokuBoard.getPosY(posSelected), this);

		paintNumbers(g);
		g.setColor(Color.BLACK);
		g.drawString(solverType, 10, 10);
		solverType = "";
	}
	
	public void paintAt(int x, int y, String text){
		Graphics g = this.getGraphics();
		g.drawString("asd", 80, 10);
	}
	
	public int getPanelWidth(){
		return this.panelWidth;
	}
	public int getPanelHeight(){
		return this.panelHeight;
	}
	public Board getBoard(){
		return sudokuBoard;
	}
	
	public String getSolverType(){
		return solverType;
	}
	
	public void setSolverType(String toSet){
		solverType = toSet;
	}

	public int getSelectedNumber() {
		return selectedNumber;
	}

	public void setSelectedNumber(int selectedNumber) {
		this.selectedNumber = selectedNumber;
	}

	public int getSelectedMode() {
		return selectedMode;
	}

	public void setSelectedMode(int selectedMode) {
		this.selectedMode = selectedMode;
	}

	public int getBoardRowPixelLength() {
		return boardRowPixelLength;
	}

	public int getBoardColumnPixelLength() {
		return boardColumnPixelLength;
	}

	public int getMargin() {
		return margin;
	}

	public int getBoxSize() {
		return boxSize;
	}

	public int getLineBoldSize() {
		return lineBoldSize;
	}

	public int getLineThinSize() {
		return lineThinSize;
	}

	public int getNumSelectorPosX() {
		return numSelectorPosX;
	}

	public int getNumSelectorPosY() {
		return numSelectorPosY;
	}

	public int getModSelectorPosX() {
		return modSelectorPosX;
	}

	public int getModSelectorPosY() {
		return modSelectorPosY;
	}

	public int getPosSelected() {
		return posSelected;
	}

	public void setPosSelected(int posSelected) {
		this.posSelected = posSelected;
	}
}
