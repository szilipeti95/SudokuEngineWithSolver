import draw.DrawBoard;
import draw.HelpFrame;
import util.Board;
import util.Digit;
import util.Highlight;
import util.Mechanics;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Map;
import java.util.Random;

public class GraphicSudoku extends JFrame{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private DrawBoard p;
	private JMenuBar menuBar;
	private JMenu file;
	private JMenuItem _new;
	private JMenuItem open;
	private JMenuItem save;
	private JMenuItem exit;
	private JMenu settings;
	private JMenu setBoardSize;
	private JMenuItem two_two;
	private JMenuItem three_two;
	private JMenuItem three_three;
	private JMenuItem four_three;
	private JMenuItem four_four;
	private JMenu solver;
	private JMenuItem solutionCheck;
	private JMenuItem checkAll;
	private JMenuItem scanAll;
	private JMenuItem solve;
	private JMenuItem solution;
	private JMenu help;
	private JMenuItem about;
	
	private boolean testmode = true;
	
	private Board sudokuBoard;
	private Mechanics sudokuMechanics;
	private Map<String, ArrayList<Board>> savedBoards;
	private String type;
	private ArrayList<Highlight> toHighlight;
    private Random randomGenerator;
	@SuppressWarnings("unchecked")
	public GraphicSudoku(){
		super("Sudoku Game");
		
		menuBar = new JMenuBar();
			file = new JMenu("File");
				_new = new JMenuItem("New");
					_new.addActionListener(new MenuSelectListener());
				open = new JMenuItem("Open");
					open.addActionListener(new MenuSelectListener());
				save = new JMenuItem("Save");
					save.addActionListener(new MenuSelectListener());
				exit = new JMenuItem("Exit");
					exit.addActionListener(new MenuSelectListener());
			file.add(_new);
			file.add(open);
			file.add(save);
			file.add(exit);
			file.setMnemonic( KeyEvent.VK_ALT );
			settings = new JMenu("Settings");
				setBoardSize = new JMenu("Set Board Size");
					two_two = new JMenuItem("4x4");
						two_two.addActionListener(new MenuSelectListener());
					three_two = new JMenuItem("6x6");
						three_two.addActionListener(new MenuSelectListener());
					three_three = new JMenuItem("9x9");
						three_three.addActionListener(new MenuSelectListener());
					four_three = new JMenuItem("12x12");
						four_three.addActionListener(new MenuSelectListener());
					four_four = new JMenuItem("16x16");
						four_four.addActionListener(new MenuSelectListener());
				setBoardSize.add(two_two);
				setBoardSize.add(three_two);
				setBoardSize.add(three_three);
				setBoardSize.add(four_three);
				setBoardSize.add(four_four);
			settings.add(setBoardSize);
			solver = new JMenu("Solver");
				solutionCheck = new JMenuItem("Solution Check");
					solutionCheck.addActionListener(new MenuSelectListener());
				checkAll = new JMenuItem("Check");
					checkAll.addActionListener(new MenuSelectListener());
				scanAll = new JMenuItem("Scan");
					scanAll.addActionListener(new MenuSelectListener());
				solve = new JMenuItem("Solve");
					solve.addActionListener(new MenuSelectListener());
				solution = new JMenuItem("Show Solution");
					solution.addActionListener(new MenuSelectListener());
			solver.add(solutionCheck);
			if(testmode){
				solver.add(checkAll);
				solver.add(scanAll);
			}
			solver.add(solve);
			solver.add(solution);
			help = new JMenu("Help");
				about = new JMenuItem("About");
					about.addActionListener(new MenuSelectListener());
			help.add(about);
		menuBar.add(file);
		menuBar.add(settings);
		menuBar.add(solver);
		menuBar.add(help);
		
		this.setJMenuBar(menuBar);
		this.setResizable(false);
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		try {
			this.setIconImage(ImageIO.read(getClass().getClassLoader().getResource("files/ICON.png")));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	
		randomGenerator = new Random();
		toHighlight = new ArrayList<>();
		type = "3x3";
		/*
		try {
			InputStream in = this.getClass().getClassLoader().getResourceAsStream("files/SavedBoards.dat");
			ObjectInputStream ois = new ObjectInputStream(in);
			savedBoards = (Map<String, ArrayList<Board>>)ois.readObject();
			ois.close();
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		 */
		//ArrayList<Board> list = savedBoards.get(type);
		init(new Board(3, 3, "Sudokus/3_3_0.txt"));
		//init(list.get(randomGenerator.nextInt(list.size())).copy());
	}
	
	public void init(Board sb){
		try{
			this.remove(p);
		}catch(NullPointerException e){}
		sudokuBoard = sb;
		p = new DrawBoard(sudokuBoard, toHighlight, type);
		sudokuMechanics = new Mechanics(sudokuBoard, toHighlight);
		this.add(p);
		this.pack();
		p.requestFocus();
		p.addKeyListener(new keyPressListener());
		p.addKeyListener(new manualType());
		p.addMouseListener(new mouseClickListener());
	}
	
	private class MenuSelectListener implements ActionListener{
		ArrayList<Board> list;
		@Override
		public void actionPerformed(ActionEvent e) {
			String option = ((JMenuItem)e.getSource()).getText();
			switch(option){
				case "New" : _new(); break;
				case "Open" : load(); break;
				case "Save" : save(); break;
				case "Exit" : System.exit(0); break;
				case "4x4" : type = "2x2"; 
							 list = savedBoards.get(type);
							 init(list.get(randomGenerator.nextInt(list.size())).copy());
							 break;
				case "6x6" : type = "3x2"; 
							 list = savedBoards.get(type);
							 init(list.get(randomGenerator.nextInt(list.size())).copy());
							 break;
				case "9x9" : type = "3x3"; 
							 list = savedBoards.get(type);
							 init(list.get(randomGenerator.nextInt(list.size())).copy());
							 break;
				case "12x12" : type = "4x3"; 
							   list = savedBoards.get(type);
							   init(list.get(randomGenerator.nextInt(list.size())).copy());
							   break;
				case "16x16" : type = "4x4"; 
				 			   list = savedBoards.get(type);
				 			   init(list.get(randomGenerator.nextInt(list.size())).copy());
				 			   break;
				case "Check" : sudokuMechanics.checkAll(); p.setSolverType("Check"); p.repaint(); break;
				case "Solution Check": boolean finished = sudokuMechanics.solutionCheck();
									   if(finished){
										   if(JOptionPane.showConfirmDialog(null, "Sikeresen kitöltötted a sudokut. Szeretnél újat kezdeni?", "Info", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION){
												ArrayList<Board> list = savedBoards.get(type);
												init(list.get(randomGenerator.nextInt(list.size())).copy());
										   }
									   }   
									   p.repaint(); break;
				case "Scan" : p.setSolverType("Scan");
							  sudokuMechanics.checkAll(); 
							  sudokuMechanics.scanAll(); 
							  sudokuMechanics.checkAll(); 
							  p.repaint(); break;
				case "Solve" :  solver();
								p.repaint();
								break;
				case "Show Solution" : sudokuMechanics.solutionShow(); 
				  p.repaint(); break;
				case "About" : new HelpFrame(g); 
				   break;
				default: break;
			}
		}
	}
	
	private class keyPressListener implements KeyListener{
		@Override
		public void keyPressed(KeyEvent e) {}

		@Override
		public void keyReleased(KeyEvent e) {
			if(e.getKeyCode() == KeyEvent.VK_H && ((e.getModifiers() & KeyEvent.CTRL_MASK) != 0)){
				solver();
				p.repaint();
			}
			else if(e.getKeyCode() == KeyEvent.VK_T && ((e.getModifiers() & KeyEvent.CTRL_MASK) != 0)){
				boolean finished = sudokuMechanics.solutionCheck();
			    if(finished){
				    if(JOptionPane.showConfirmDialog(null, "Sikeresen kitöltötted a sudokut. Szeretnél újat kezdeni?", "Info", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION){
				    	ArrayList<Board> list = savedBoards.get(type);
				 		init(list.get(randomGenerator.nextInt(list.size())).copy());
				    }
			    }   
			    p.repaint();
			}
		}

		@Override
		public void keyTyped(KeyEvent e) {
			// TODO Auto-generated method stub
		}
	}
	private class mouseClickListener implements MouseListener{
		@Override
		public void mouseClicked(MouseEvent arg0) {	
			arg0.getComponent().requestFocus();
		}
		@Override
		public void mouseEntered(MouseEvent arg0) { }
		@Override
		public void mouseExited(MouseEvent arg0) { }
		@Override
		public void mousePressed(MouseEvent arg0) { }
		@Override
		public void mouseReleased(MouseEvent arg0) {
			
			if(arg0.getX() > p.getMargin() && arg0.getY() > p.getMargin() && arg0.getX() < p.getMargin() + p.getBoardRowPixelLength() && arg0.getY() <p.getMargin() + p.getBoardColumnPixelLength()){
				
				int checkX = sudokuBoard.getPosX(0);
				int checkY = sudokuBoard.getPosY(0);
				int pos = 0;
				while(pos < sudokuBoard.getBoxCount()){
					checkX = sudokuBoard.getPosX(pos);
					checkY = sudokuBoard.getPosY(pos);
					if(checkX < arg0.getX() && checkX + p.getBoxSize() > arg0.getX() && checkY < arg0.getY() && checkY + p.getBoxSize() > arg0.getY())
						break;
					pos++;
				}
				if(pos < sudokuBoard.getBoxCount()){
					p.setPosSelected(pos);
					if(arg0.getButton() == MouseEvent.BUTTON3){
						if(p.getSelectedMode() == 0)
							sudokuBoard.getDigit(pos).remove();
						else if(p.getSelectedMode() == 1)
							sudokuBoard.getDigit(pos).remove(p.getSelectedNumber());
					}
					else if(arg0.getButton() == MouseEvent.BUTTON1 && p.getSelectedMode() == 0){
						sudokuBoard.getDigit(pos).add(p.getSelectedNumber(), Digit.VALUE_TYPE.NUMBER);
					}
					else if(arg0.getButton() == MouseEvent.BUTTON1 && p.getSelectedMode() == 1){
						sudokuBoard.getDigit(pos).add(p.getSelectedNumber(), Digit.VALUE_TYPE.GUESS);
					}
				}
			}
			if(sudokuBoard.getNumberCount() == 16){
				if(arg0.getX() >= p.getNumSelectorPosX() && arg0.getX() < p.getNumSelectorPosX()+p.getBoxSize()){
			        int from = p.getNumSelectorPosY() + p.getBoxSize() + p.getLineThinSize();
			        int selectedNum = -1;
			    	for(int i=1; i<sudokuBoard.getNumberCount() + 1 && selectedNum == -1; i++){
			    		if(from > arg0.getY()){
			    			p.setSelectedNumber(i);
			    			selectedNum = i;
			    		}
			    		from += p.getBoxSize()+p.getLineThinSize();
			        }
				}
				if(arg0.getX() >= p.getModSelectorPosX() && arg0.getX() < p.getModSelectorPosX() + p.getBoxSize()){
					if(arg0.getY() < p.getModSelectorPosY()){
						p.setSelectedMode(0);
					}
					else
						p.setSelectedMode(1);
				}
			}
			else{
				if(arg0.getY() >= p.getNumSelectorPosY() && arg0.getY() < p.getNumSelectorPosY()+p.getBoxSize()){
			        int from = p.getNumSelectorPosX() + p.getBoxSize() + p.getLineThinSize();
			        int selectedNum = -1;
			    	for(int i=1; i<sudokuBoard.getNumberCount() + 1 && selectedNum == -1; i++){
			    		if(from > arg0.getX()){
			    			p.setSelectedNumber(i);
			    			selectedNum = i;
			    		}
			    		from += p.getBoxSize()+p.getLineThinSize();
			        }
				}
				if(arg0.getY() >= p.getModSelectorPosY() && arg0.getY() < p.getModSelectorPosY() + p.getBoxSize()){
					if(arg0.getX() < p.getModSelectorPosX()){
						p.setSelectedMode(0);
					}
					else
						p.setSelectedMode(1);
				}
			}
	    	repaint();
		}
	}
	private class manualType implements KeyListener{
		@Override
		public void keyTyped(KeyEvent e) {
		}

		@Override
		public void keyPressed(KeyEvent e) {
		}

		@Override
		public void keyReleased(KeyEvent e) {
			if(e.getKeyCode() != KeyEvent.VK_CONTROL){
				if(e.getKeyChar() > '0' && e.getKeyChar() <= '9' && sudokuBoard.getNumberCount() >= e.getKeyChar()-'0'){
					p.setSelectedNumber(e.getKeyChar()-'0');
					if(p.getSelectedMode() == 0)
						sudokuBoard.getDigit(p.getPosSelected()).add(p.getSelectedNumber(), Digit.VALUE_TYPE.NUMBER);
					else if(p.getSelectedMode() == 1)
						sudokuBoard.getDigit(p.getPosSelected()).add(p.getSelectedNumber(), Digit.VALUE_TYPE.GUESS);
				}
				else if(e.getKeyChar() >= 'a' && e.getKeyChar() <= 'g' && sudokuBoard.getNumberCount() >= (e.getKeyChar()-'a' + 10)){
					p.setSelectedNumber(e.getKeyChar()-'a' + 10);
					if(p.getSelectedMode() == 0)
						sudokuBoard.getDigit(p.getPosSelected()).add(p.getSelectedNumber(), Digit.VALUE_TYPE.NUMBER);
					else if(p.getSelectedMode() == 1)
						sudokuBoard.getDigit(p.getPosSelected()).add(p.getSelectedNumber(), Digit.VALUE_TYPE.GUESS);
				}
				else if(e.getKeyCode() == KeyEvent.VK_DELETE){
					if(p.getSelectedMode() == 0)
						sudokuBoard.getDigit(p.getPosSelected()).remove();
					else if(p.getSelectedMode() == 1)
						sudokuBoard.getDigit(p.getPosSelected()).remove(p.getSelectedNumber());
				}
				else if(e.getKeyCode() == KeyEvent.VK_SPACE){
					p.setSelectedMode((p.getSelectedMode()+1)%2);
				}
				else if(e.getKeyCode() == KeyEvent.VK_UP){
					if(p.getPosSelected() == -1){
						p.setPosSelected(0);
					}
					else{
						p.setPosSelected(p.getPosSelected() - sudokuBoard.getNumberCount());
						if(p.getPosSelected() < 0){
							p.setPosSelected(p.getPosSelected() + sudokuBoard.getBoxCount());
						}
					}
				}
				else if(e.getKeyCode() == KeyEvent.VK_DOWN){
					if(p.getPosSelected() == -1){
						p.setPosSelected(0);
					}
					else{
						p.setPosSelected(p.getPosSelected() + sudokuBoard.getNumberCount());
						if(p.getPosSelected() >= sudokuBoard.getBoxCount()){
							p.setPosSelected(p.getPosSelected() - sudokuBoard.getBoxCount());
						}
					}
				}
				else if(e.getKeyCode() == KeyEvent.VK_LEFT){
					if(p.getPosSelected() == -1){
						p.setPosSelected(0);
					}
					else{
						if(p.getPosSelected() % sudokuBoard.getNumberCount() == 0){
							p.setPosSelected(p.getPosSelected() + sudokuBoard.getNumberCount());
						}
						p.setPosSelected(p.getPosSelected()-1);
					}
				}
				else if(e.getKeyCode() == KeyEvent.VK_RIGHT){
					if(p.getPosSelected() == -1){
						p.setPosSelected(0);
					}
					else{
						if((p.getPosSelected()+1) % sudokuBoard.getNumberCount() == 0){
							p.setPosSelected(p.getPosSelected() - sudokuBoard.getNumberCount());
						}
						p.setPosSelected(p.getPosSelected()+1);
					}
				}
				repaint();
			}
		}
	}
	public void load(){
		JFileChooser fc = new JFileChooser();
		fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
		fc.setAcceptAllFileFilterUsed(false);
		fc.addChoosableFileFilter(new FileNameExtensionFilter("Sudoku Save File", "ssf"));
		if(fc.showOpenDialog(this) == JFileChooser.APPROVE_OPTION){
			FileInputStream fis;
			try {
				fis = new FileInputStream(fc.getSelectedFile());
				ObjectInputStream ois = new ObjectInputStream(fis);
				sudokuBoard = (Board)ois.readObject();
				ois.close();
			} catch (IOException | ClassNotFoundException e) {
				e.printStackTrace();
			}
			init(sudokuBoard);
		}
	}
	public void save(){
		JFileChooser fc = new JFileChooser();
		fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
		fc.setAcceptAllFileFilterUsed(false);
		fc.addChoosableFileFilter(new FileNameExtensionFilter("Sudoku Save File", "ssf"));
		if(fc.showSaveDialog(this) == JFileChooser.APPROVE_OPTION){
			try{
				FileOutputStream fos = new FileOutputStream(fc.getSelectedFile() + ".ssf");
				ObjectOutputStream oos = new ObjectOutputStream(fos);
				oos.writeObject(sudokuBoard);
				oos.close();
			}catch(IOException e) {}
		}
	}
	public void _new(){
		ArrayList<Board> list = savedBoards.get(type);
		init(list.get(randomGenerator.nextInt(list.size())).copy());
	}
	public void solver(){
		if(sudokuMechanics.solutionCheck()){
			p.setSolverType("finished");
		}
		else{
			if(sudokuMechanics.getScanned()){
				sudokuMechanics.scanAll();
				p.setSolverType("scan");
			}
			if(!sudokuMechanics.checkAll()){
				if(!sudokuMechanics.solvedAll()){
					if(!sudokuMechanics.scanAll()){
						p.setSolverType("intersection");
					}else p.setSolverType("scan");
				}else p.setSolverType("solved");
			}else p.setSolverType("check");
		}
	}
	
	static GraphicSudoku g;
	public static void main(String[] args) {
		g = new GraphicSudoku();
		g.setVisible(true);
	}
}
