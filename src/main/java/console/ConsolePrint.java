package console;

import util.Board;
import util.Digit;

public class ConsolePrint{
	private Board sudokuBoard;
	public ConsolePrint(Board sudokuBoard){
		this.sudokuBoard = sudokuBoard;
	}
	public void Print(){
		for(int i=0; i<sudokuBoard.getBoxCount(); i++){	
			Digit toPrint = sudokuBoard.getDigit(i);
			
			if(toPrint.size() == 1)
				System.out.print(" " + toPrint.get(0) + " ");
			else if(toPrint.size() > 1)
				System.out.print(" G ");
			else
				System.out.print("   ");
			
			if( (i+1)%sudokuBoard.getBoxCountInLine() == 0 )
				System.out.print(" | ");
			if( (i+1)%sudokuBoard.getNumberCount() == 0)
				System.out.println("");
			if((i+1)%(sudokuBoard.getNumberCount()*sudokuBoard.getBoxCountInColumn()) == 0){
				for(int j=0; j<sudokuBoard.getNumberCount()+sudokuBoard.getBoxCountInColumn(); j++)
					System.out.print(" - ");
				System.out.println("");
			}
		}		
	}
	public void PrintAllValue(int pos){
		Digit toPrint = sudokuBoard.getDigit(pos);
		
		for(int i=0; i<toPrint.size(); i++)
			System.out.print(toPrint.get(i) + " ");
		System.out.println("");

	}
}
