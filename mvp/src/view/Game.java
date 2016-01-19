package view;

import java.util.ArrayList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;

import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;
import algorithms.search.Solution;

public class Game {
	KeyListener keyListener;
	Maze2D maze2d;
	Maze3d maze3d;
	Position position;
	int[][][] mazeSolution;
	int floor = 0;

	public Game(Maze3d maze3d,Maze2D maze2d) 
	{
		this.maze3d = maze3d;
		this.maze2d = maze2d;
	}

	public Maze2D getMaze2d() {
		return maze2d;
	}

	public void setMaze2d(Maze2D maze2d) {
		this.maze2d = maze2d;
	}

	public Maze3d getMaze3d() {
		return maze3d;
	}

	public void setMaze3d(Maze3d maze3d) {
		this.maze3d = maze3d;
		mazeSolution = new int [maze3d.getX()][maze3d.getY()][maze3d.getZ()];
	}
	
	public int getFloor() {
		return floor;
	}

	public void setFloor(int floor) {
		this.floor = floor;
	}
	

	
	public void startGameG()
	{
		//sets mazes
        maze2d.setMazeData(maze3d.getCrossSectionByX(0));
		maze2d.setMaze3d(maze3d);
		maze2d.startGame();
		position = new Position(maze3d.getStartPosition().getX(),maze3d.getStartPosition().getY(), maze3d.getStartPosition().getZ());
		
		
//		System.out.println(maze3d.getStartPosition());
//		System.out.println(maze3d.getGoalPosition());
//		System.out.println(position);
//		maze3d.printCrossSection(maze3d.getCrossSectionByY(0));
		keyListener = new KeyListener()
	    {
			
			@Override
			public void keyReleased(KeyEvent e){}
			
			@Override
			public void keyPressed(KeyEvent e) 
			{
				
				int key = e.keyCode;
				switch (key)
				{

				case SWT.ARROW_UP:

//					System.out.println("ARROW_Up");
//					System.out.println(position.getX() + ","  + (position.getY()-1) + ","  + position.getZ());
//					System.out.println(maze3d.getValue(position.getX(), position.getY()-1, position.getZ()));
					if(position.getY()-1 >= 0 && maze3d.getValue(position.getX(), position.getY()-1, position.getZ()) == 0)
					{
						position.setY(position.getY()-1);
						maze2d.moveUp();
					}
					break;

				case SWT.ARROW_DOWN:

//					System.out.println("ARROW_Down");
//					System.out.println(position.getX() + ","  + (position.getY()+1) + ","  + position.getZ());
//					System.out.println(maze3d.getValue(position.getX(), position.getY()+1, position.getZ()));
					if(position.getY()+1 < maze3d.getY() && maze3d.getValue(position.getX(), position.getY()+1, position.getZ()) == 0)
					{
						
						position.setY(position.getY()+1);
						maze2d.moveDown();
					}
					break;
				
				case SWT.ARROW_RIGHT:
//					System.out.println("ARROW_Right");
//					System.out.println(position.getX() + ","  + position.getY() + ","  + (position.getZ()+1));
//					System.out.println(maze3d.getValue(position.getX(), position.getY(), position.getZ()+1));
					if(position.getZ()+1 < maze3d.getZ() && (maze3d.getValue(position.getX(), position.getY(), position.getZ()+1) == 0))
					{
						position.setZ(position.getZ()+1);
						maze2d.moveRight();
					}
					break;
					
				case SWT.ARROW_LEFT:
//					System.out.println("ARROW_Left");
//					System.out.println(position.getX() + ","  + position.getY() + ","  + (position.getZ()-1));
//					System.out.println(maze3d.getValue(position.getX(), position.getY(), position.getZ()-1));
//					System.out.println(position.getZ()-1);
//					
					if(position.getZ()-1 >= 0 && maze3d.getValue(position.getX(), position.getY(), (position.getZ()-1)) == 0)
					{
						position.setZ(position.getZ()-1);
						maze2d.moveLeft();
					}
					break;
				case SWT.PAGE_UP:
//					System.out.println("PAGE_UP");
					
					if(position.getX()+1 < maze3d.getX() && maze3d.getValue(position.getX()+1, position.getY(), (position.getZ())) == 0)
					{
						//System.out.println("PAGE_UPDone");
						maze2d.setMazeData(maze3d.getCrossSectionByX(floor+1));
						position.setX(position.getX()+1);
						floor++;
//						System.out.println("floor: " + floor);
						maze2d.pageUp();
						
					}
					break;
					
				case SWT.PAGE_DOWN:
//					System.out.println("PAGE_DOWN");
//					
//					System.out.println(position.getX()-1);
					if(position.getX()-1 >= 0 && maze3d.getValue(position.getX()-1, position.getY(), (position.getZ())) == 0)
					{
//						System.out.println("PAGE_Down");
						maze2d.setMazeData(maze3d.getCrossSectionByX(floor-1));
						position.setX(position.getX()-1);
						floor--;
//						System.out.println("floor: " + floor);
						maze2d.pageDown();
					}
					break;
				
				}
			}
		};
		maze2d.addKeyListener(keyListener);
		
	}
	
	public void solveGame(Solution<Position> solution)
	{
		ArrayList<Position> sol = solution.getArrayLS();
//		solution.printSolution();
		stopKey();
		maze2d.solve(sol);

	}
	public void stopKey()
	{
		maze2d.removeKeyListener(keyListener);
	}
	
	
}
