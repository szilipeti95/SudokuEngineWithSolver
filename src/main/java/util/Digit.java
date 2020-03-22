package util;

import java.io.Serializable;
import java.util.ArrayList;

public class Digit implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public enum VALUE_TYPE{
		EMPTY,
		CLUE,
		NUMBER,
		GUESS
	};
	private ArrayList<Integer> value;
	private VALUE_TYPE type;
	
	public Digit(int value, VALUE_TYPE type){
		this.value = new ArrayList<>();
		this.value.add((Integer)value);
		this.type = type;
	}
	
	public Digit(){
		this.value = new ArrayList<>();
		this.type = VALUE_TYPE.EMPTY;
	}
	
	public VALUE_TYPE getType(){
		return this.type;
	}
	public void setType(VALUE_TYPE toSet){
		this.type = toSet;
	}
	public int get(int at){
		return value.get(at);
	}
	public int size(){
		return value.size();
	}
	public Object clone(){
		return value.clone();
	}
	public ArrayList<Integer> getValue(){
		return value;
	}
	public void setValue(ArrayList<Integer> toSet){
		value = toSet;
	}
	public void add(int toAdd, VALUE_TYPE type){
		if(this.type != VALUE_TYPE.CLUE){
			if(type == VALUE_TYPE.NUMBER){
				value.clear();
				value.add(toAdd);
				this.type = type;
			}
			else if(type == VALUE_TYPE.GUESS){
				try{
					value.remove((Integer)toAdd);
				}catch(IndexOutOfBoundsException e){}
				value.add(toAdd);
				this.type = type;
			}
		}
		java.util.Collections.sort(value);
	}
	public void remove(int toRemove){
		if(type != VALUE_TYPE.CLUE){
			try{
				value.remove((Integer)toRemove);
			}catch(NullPointerException e){}
			if(value.isEmpty())
				type = VALUE_TYPE.EMPTY;
		}
	}
	public void remove(){
		if(type != VALUE_TYPE.CLUE){
			value.clear();
			type = VALUE_TYPE.EMPTY;
		}
	}
	public void addClue(int toAdd){
		format();
		value.add(toAdd);
		type = VALUE_TYPE.CLUE;
	}
	public void format(){
		value.clear();
		type = VALUE_TYPE.EMPTY;
	}
	public void intersection(ArrayList<Integer> toUnite){
		for(int i=0; i<value.size(); ){
			if(!toUnite.contains(value.get(i))){
				value.remove(i);
			}
			else
				i++;
		}
	}
}
