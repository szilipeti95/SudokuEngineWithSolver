package solver;

import engine.Board;
import engine.Digit;

import java.awt.Color;
import java.util.ArrayList;

public class Solver {
    private Board sudokuBoard;
    private ArrayList<Highlight> toHighlight;
    private boolean scanned;
    private int helpCount;
    public Solver(Board sudokuBoard, ArrayList<Highlight> high){
        this.sudokuBoard = sudokuBoard;
        this.toHighlight = high;
        scanned = false;
        helpCount = 0;
    }

    public ArrayList<Integer> checkRowAt(int x, int y, int pos){
        if(sudokuBoard.getDigit(pos).getType() == Digit.VALUE_TYPE.EMPTY || sudokuBoard.getDigit(pos).getType() == Digit.VALUE_TYPE.GUESS){
            ArrayList<Integer> check = new ArrayList<>();
            for(int i = 1; i<sudokuBoard.getMaxNumber()+1; i++)
                check.add(i);
            for(int i = 0; i<sudokuBoard.getMaxNumber(); i++){
                Digit current = sudokuBoard.getDigit(sudokuBoard.getPos(i, y));
                if(i != x && current.size() == 1 && current.getType() != Digit.VALUE_TYPE.GUESS){
                    check.remove((Integer)current.get(0));
                }
            }
            sudokuBoard.getDigit(pos).setType(Digit.VALUE_TYPE.GUESS);
            return check;
        }
        else
            return sudokuBoard.getDigit(sudokuBoard.getPos(x, y)).getValue();
    }
    public ArrayList<Integer> checkColumnAt(int x, int y, int pos){
        if(sudokuBoard.getDigit(pos).getType() == Digit.VALUE_TYPE.EMPTY || sudokuBoard.getDigit(pos).getType() == Digit.VALUE_TYPE.GUESS){
            ArrayList<Integer> check = new ArrayList<>();
            for(int i = 1; i<sudokuBoard.getMaxNumber()+1; i++) check.add((Integer)i);

            for(int i=x; i<sudokuBoard.getBoxCount(); i+=sudokuBoard.getMaxNumber()){
                Digit current = sudokuBoard.getDigit(i);
                if(i != pos && current.size() == 1 && current.getType() != Digit.VALUE_TYPE.GUESS){
                    check.remove((Integer)current.get(0));
                }
            }
            sudokuBoard.getDigit(pos).setType(Digit.VALUE_TYPE.GUESS);
            return check;
        }
        else
            return sudokuBoard.getDigit(sudokuBoard.getPos(x, y)).getValue();
    }
    public ArrayList<Integer> checkBoxAt(int x, int y, int pos) {
        if(sudokuBoard.getDigit(pos).getType() == Digit.VALUE_TYPE.EMPTY || sudokuBoard.getDigit(pos).getType() == Digit.VALUE_TYPE.GUESS) {
            ArrayList<Integer> check = new ArrayList<>();
            for(int i = 1; i<sudokuBoard.getMaxNumber()+1; i++) check.add((Integer)i);

            int fromPos = sudokuBoard.getPos((x/sudokuBoard.getBoxWidth())*sudokuBoard.getBoxWidth(), (y/sudokuBoard.getBoxHeight())*sudokuBoard.getBoxHeight());
            for(int i = 0; i<sudokuBoard.getMaxNumber(); i++) {
                Digit current = sudokuBoard.getDigit(fromPos);
                if(fromPos != pos && current.size() == 1 && current.getType() != Digit.VALUE_TYPE.GUESS){
                    check.remove((Integer)current.get(0));
                }
                if((1+i)%sudokuBoard.getBoxWidth() == 0)
                    fromPos = fromPos-(sudokuBoard.getBoxWidth()-1)+sudokuBoard.getMaxNumber();
                else
                    fromPos++;
            }
            sudokuBoard.getDigit(pos).setType(Digit.VALUE_TYPE.GUESS);
            return check;
        }
        else
            return sudokuBoard.getDigit(sudokuBoard.getPos(x, y)).getValue();
    }
    public boolean checkAt(int x, int y){
        int pos = sudokuBoard.getPos(x, y);
        @SuppressWarnings("unchecked")
        ArrayList<Integer> before = (ArrayList<Integer>) sudokuBoard.getDigit(pos).clone();
        sudokuBoard.getDigit(pos).setValue(this.checkRowAt(x, y, pos));
        sudokuBoard.getDigit(pos).intersection(this.checkColumnAt(x, y, pos));
        sudokuBoard.getDigit(pos).intersection(this.checkBoxAt(x, y, pos));
        return !before.equals(sudokuBoard.getDigit(pos).getValue());
    }
    public boolean checkAll(){
        boolean modded = false;
        for(int i = 0; i<sudokuBoard.getMaxNumber(); i++){
            for(int j = 0; j<sudokuBoard.getMaxNumber(); j++){
                if(this.checkAt(j, i) == true)
                    modded = true;
            }
        }
        helpCount++;
        return modded;
    }
    public boolean solvedAt(int pos){
        Digit current = sudokuBoard.getDigit(pos);
        if(current.size() == 1 && current.getType() == Digit.VALUE_TYPE.GUESS){
            current.setType(Digit.VALUE_TYPE.NUMBER);
            toHighlight.add(new Highlight(sudokuBoard.getPosX(pos), sudokuBoard.getPosY(pos), Color.GREEN));
            return true;
        }
        else
            return false;
    }
    public boolean solvedAll(){
        boolean found = false;
        for(int i=0; i<sudokuBoard.getBoxCount() && !found; i++){
            found = solvedAt(i);
        }
        helpCount++;
        return found;
    }

    public int scanRowAt(int x, int y, int pos){
        Digit current = sudokuBoard.getDigit(pos);
        @SuppressWarnings("unchecked")
        ArrayList<Integer> currentList = (ArrayList<Integer>)current.clone();
        for(int i = 0; i<sudokuBoard.getMaxNumber(); i++){
            current = sudokuBoard.getDigit(sudokuBoard.getPos(i, y));
            if(i != x && current.getType() == Digit.VALUE_TYPE.GUESS){
                for(int j = 0; j<current.size(); j++){
                    currentList.remove((Integer)current.get(j));
                }
            }
            toHighlight.add(new Highlight(sudokuBoard.getPosX(sudokuBoard.getPos(i, y)), sudokuBoard.getPosY(sudokuBoard.getPos(i, y)), Color.YELLOW));
        }
        if(currentList.size() == 1){
            toHighlight.add(new Highlight(sudokuBoard.getPosX(pos), sudokuBoard.getPosY(pos), Color.GREEN));
            return currentList.get(0);
        }
        else{
            toHighlight.clear();
            return 0;
        }
    }
    public int scanColumnAt(int x, int y, int pos){
        Digit current = sudokuBoard.getDigit(pos);
        @SuppressWarnings("unchecked")
        ArrayList<Integer> currentList = (ArrayList<Integer>)current.clone();
        for(int i = 0; i<sudokuBoard.getMaxNumber(); i++){
            current = sudokuBoard.getDigit(sudokuBoard.getPos(x, i));
            if(i != y && current.getType() == Digit.VALUE_TYPE.GUESS){
                for(int j = 0; j<current.size(); j++){
                    currentList.remove((Integer)current.get(j));
                }
            }
            toHighlight.add(new Highlight(sudokuBoard.getPosX(sudokuBoard.getPos(x, i)), sudokuBoard.getPosY(sudokuBoard.getPos(x, i)), Color.YELLOW));
        }
        if(currentList.size() == 1){
            toHighlight.add(new Highlight(sudokuBoard.getPosX(pos), sudokuBoard.getPosY(pos), Color.GREEN));
            return currentList.get(0);
        }
        else{
            toHighlight.clear();
            return 0;
        }
    }
    public int scanBoxAt(int x, int y, int pos){
        Digit current = sudokuBoard.getDigit(pos);
        @SuppressWarnings("unchecked")
        ArrayList<Integer> currentList = (ArrayList<Integer>)current.clone();

        int fromPos = sudokuBoard.getPos((x/sudokuBoard.getBoxWidth())*sudokuBoard.getBoxWidth(), (y/sudokuBoard.getBoxHeight())*sudokuBoard.getBoxHeight());

        for(int i = 0; i<sudokuBoard.getMaxNumber(); i++){
            current = sudokuBoard.getDigit(fromPos);
            if(pos != fromPos && current.getType() == Digit.VALUE_TYPE.GUESS){
                for(int j = 0; j<current.size(); j++){
                    currentList.remove((Integer)current.get(j));
                }
            }
            toHighlight.add(new Highlight(sudokuBoard.getPosX(fromPos), sudokuBoard.getPosY(fromPos), Color.YELLOW));
            if((1+i)%sudokuBoard.getBoxWidth() == 0)
                fromPos = fromPos-(sudokuBoard.getBoxWidth()-1)+sudokuBoard.getMaxNumber();
            else
                fromPos++;
        }
        if(currentList.size() == 1){
            toHighlight.add(new Highlight(sudokuBoard.getPosX(pos), sudokuBoard.getPosY(pos), Color.GREEN));
            return currentList.get(0);
        }
        else{
            toHighlight.clear();
            return 0;
        }
    }
    public boolean scanAt(int x, int y){
        int pos = sudokuBoard.getPos(x, y);
        Digit current = sudokuBoard.getDigit(pos);
        int scan = 1;
        if(current.getType() == Digit.VALUE_TYPE.GUESS){
            if((scan = scanRowAt(x, y, pos)) > 0 ? false : true){
                if((scan = scanColumnAt(x, y, pos)) > 0 ? false : true){
                    if((scan = scanBoxAt(x, y, pos)) > 0 ? false : true){}
                    else{
                        if(scanned)
                            current.add(scan, Digit.VALUE_TYPE.NUMBER);
                        return true;
                    }
                }
                else{
                    if(scanned)
                        current.add(scan, Digit.VALUE_TYPE.NUMBER);
                    return true;
                }
            }
            else{
                if(scanned)
                    current.add(scan, Digit.VALUE_TYPE.NUMBER);
                return true;
            }
        }
        return false;
    }
    public boolean scanAll(){
        boolean found = false;
        for(int i = 0; i<sudokuBoard.getMaxNumber() && !found; i++){
            for(int j = 0; j<sudokuBoard.getMaxNumber() && !found; j++){
                found = scanAt(j,i);
            }
        }
        if(found)
            scanned = !scanned;
        helpCount++;
        return found;
    }

    public boolean solutionCheck(){
        boolean finished = true;
        for(int i=0; i<sudokuBoard.getBoxCount(); i++){
            Digit current = sudokuBoard.getDigit(i);
            if(current.getType() == Digit.VALUE_TYPE.NUMBER){
                if(current.get(0) != sudokuBoard.getAnswer(i)){
                    toHighlight.add(new Highlight(sudokuBoard.getPosX(i), sudokuBoard.getPosY(i), Color.RED));
                    finished = false;
                }
            }
            else if (current.getType() != Digit.VALUE_TYPE.CLUE){
                finished = false;
            }
        }
        if(!finished)
            helpCount++;
        return finished;
    }
    public void solutionShow(){
        for(int i = 0; i<sudokuBoard.getBoxCount(); i++){
            if(sudokuBoard.getDigit(i).getType() != Digit.VALUE_TYPE.CLUE){
                sudokuBoard.getDigit(i).add(sudokuBoard.getAnswer(i), Digit.VALUE_TYPE.NUMBER);
            }
        }
    }

    public boolean getScanned(){
        return scanned;
    }
    public int getHelpCount(){
        return helpCount;
    }
}
