package view;

import java.util.ArrayList;

import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;

import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;

public abstract class MazeDisplay extends Canvas {

	GameCharacter gameCharacter;
	int[][][] mazeSolution;
	int[][] mazeData;
	Maze3d maze3d;
	
	public int[][][] getMazeSolution() {
		return mazeSolution;
	}

	public void setMazeSolution(int[][][] mazeSolution) {
		this.mazeSolution = mazeSolution;
	}
	boolean displaySolution = false;
	String characterPosition = "";
	
	public MazeDisplay(Composite parent, int style) {
		super(parent,style);
		mazeData = new int[1][1];
	}
	
	public int[][] getMazeData() {
		return mazeData;
	}

	public void setMazeData(int[][] mazeData) {
		this.mazeData = mazeData;
	}

	public Maze3d getMaze3d() {
		return maze3d;
	}

	public void setMaze3d(Maze3d maze3d) {
		this.maze3d = maze3d;
		mazeSolution = new int [maze3d.getX()][maze3d.getY()][maze3d.getZ()];
	}

	public abstract  void setCharacterPosition(int row,int col);

	public abstract void moveUp();

	public abstract  void moveDown();

	public abstract  void moveLeft();

	public  abstract void moveRight();
	
	public  abstract void pageUp();
	
	public  abstract void pageDown();
	
	public  void solve(ArrayList<Position> solution)
	{
		displaySolution = true;
		for (Position p : solution) 	
		{			
			mazeSolution[p.getX()][p.getY()][p.getZ()] = 8;
		}
		//set gameCharacter to start point
		gameCharacter.setX(maze3d.getStartPosition().getX());
		gameCharacter.setY(maze3d.getStartPosition().getY());
		gameCharacter.setZ(maze3d.getStartPosition().getZ());
		mazeData = maze3d.getCrossSectionByX(0);
		
		getDisplay().timerExec(200, new Runnable() {
			int countPosition = solution.size()-1;
			@Override
			public void run() {
				
				// one floor up
//				System.out.println("gameCharacter: " + gameCharacter.getZ() + "," + gameCharacter.getY() + "," + gameCharacter.getX());
//				System.out.println("solution: " + "X:"+solution.get(countPosition).getX() + " ,Y:" +solution.get(countPosition).getY() +" ,Z:"+solution.get(countPosition).getZ());
				if (gameCharacter.getX()<solution.get(countPosition).getX()) {
					gameCharacter.setX(gameCharacter.getX()+1);
					setMazeData(maze3d.getCrossSectionByX(gameCharacter.getX()));
					redraw();
				}
		        // one floor down
				else if (gameCharacter.getX()>solution.get(countPosition).getX()) {
					gameCharacter.setX(gameCharacter.getX()-1);
					setMazeData(maze3d.getCrossSectionByX(gameCharacter.getX()));
					redraw();
				}
				else {
					
					if(gameCharacter.getZ() < solution.get(countPosition).getZ())
						moveRight();
					if(gameCharacter.getZ() > solution.get(countPosition).getZ())
						moveLeft();
					if(gameCharacter.getY() < solution.get(countPosition).getY())
						moveDown();
					if(gameCharacter.getY() > solution.get(countPosition).getY())
						moveUp();
				}
				getDisplay().timerExec(200,this);
//				System.out.println("countPosition: " + countPosition);
				countPosition--;
				if (countPosition == -1) {
					getDisplay().timerExec(-1, this);
				    
				}
				
			}
		});
				
		
		
				 
	}

	

}
