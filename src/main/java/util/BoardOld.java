package util;

import java.io.*;

public class BoardOld implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Digit[] puzzle;
	private int[] answer;
	private int boxHeight;
	private int boxWidth;
	private int numberCount;
	private int boxCount;
	
	private Integer[] posX;
	private Integer[] posY;
	
	public BoardOld(){
		
	}
	
	
	public BoardOld(int x, int y){
		this.numberCount = x*y;
		this.boxCount = numberCount*numberCount;
		this.puzzle = new Digit[boxCount];
		this.boxHeight = y;
		this.boxWidth = x;
		for(int i=0; i<boxCount; i++){
			puzzle[i] = new Digit();
		}
		posX = new Integer[boxCount];
		posY = new Integer[boxCount];
		for(int i=0; i<boxCount; i++){
			posX[i] = new Integer(0);
			posY[i] = new Integer(0);
		}
		answer = new int[boxCount];
	}
	
	public BoardOld(int x, int y, String fname){
		this.numberCount = x*y;
		this.boxCount = numberCount*numberCount;
		this.puzzle = new Digit[boxCount];
		this.boxHeight = y;
		this.boxWidth = x;
		for(int i=0; i<boxCount; i++){
			puzzle[i] = new Digit();
		}
		posX = new Integer[boxCount];
		posY = new Integer[boxCount];
		for(int i=0; i<boxCount; i++){
			posX[i] = new Integer(0);
			posY[i] = new Integer(0);
		}
		answer = new int[boxCount];
		try {
			BufferedReader br = new BufferedReader(new FileReader(fname));
			String read;
			String[] numbers;
			int number;
			int count = 0;
			for(int j=0; j<numberCount; j++){
				read = br.readLine();
				numbers = read.split(" ");
				for(int i = 0; i<numberCount; i++){
					number = Integer.parseInt(numbers[i]);
					if(number != 0){
						puzzle[count] = new Digit(number, Digit.VALUE_TYPE.CLUE);
					}
					count++;
				}
			}
			count = 0;
			for(int j=0; j<numberCount; j++){
				read = br.readLine();
				numbers = read.split(" ");
				for(int i = 0; i<numberCount; i++){
					this.answer[count] = Integer.parseInt(numbers[i]);
					count++;
				}
			}
			br.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public int getPos(int x, int y){
		return y*this.numberCount + x;
	}
	public Digit getDigit(int x, int y){
		return puzzle[getPos(x,y)];
	}
	public Digit getDigit(int pos){
		return puzzle[pos];
	}
	public void setDigit(Digit toSet, int x, int y){
		puzzle[getPos(x, y)] = toSet;
	}
	public void setDigit(Digit toSet, int pos){
		puzzle[pos] = toSet;
	}
	public int getBoxWidth() {
		return boxWidth;
	}
	public void setBoxWidth(int boxWidth) {
		this.boxWidth = boxWidth;
	}
	public int getBoxHeight() {
		return boxHeight;
	}
	public void setBoxHeight(int boxHeight) {
		this.boxHeight = boxHeight;
	}
	public int getNumberCount() {
		return numberCount;
	}
	public void setNumberCount(int numberCount) {
		this.numberCount = numberCount;
	}
	public int getBoxCount(){
		return boxCount;
	}
	public void setBoxCount(int boxCount){
		this.boxCount = boxCount;
	}

	public int getAnswer(int pos) {
		return this.answer[pos];
	}
	public void setAnswer(int answer, int pos) {
		this.answer[pos] = answer;
	}
	public int getPosX(int pos) {
		return posX[pos];
	}
	public void setPosX(int pos, int value) {
		this.posX[pos] = new Integer(value);
	}
	public int getPosY(int pos) {
		return posY[pos];
	}
	public void setPosY(int pos, int value) {
		this.posY[pos] = new Integer(value);
	}	

	public Board copy() {
		Board obj = null;
	    try {
	        ByteArrayOutputStream bos = new ByteArrayOutputStream();
	        ObjectOutputStream out = new ObjectOutputStream(bos);
	        out.writeObject(this);
	        out.flush();
	        out.close();
	
	        ObjectInputStream in = new ObjectInputStream(
	            new ByteArrayInputStream(bos.toByteArray()));
	        obj = (Board)in.readObject();
	    }
	    catch(IOException | ClassNotFoundException e) {
	        e.printStackTrace();
	    }
	    return obj;
	}
}
