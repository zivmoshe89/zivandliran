package view;

import presenter.Properties;
import algorithms.mazeGenerators.Position;
import algorithms.search.Solution;



/**
 * The Interface View.
 * 
 * @author Liran Avishay
 */
public interface View {

	/**
	 * Start.
	 */
	public void start();
	/**
	 * Prints the menu.
	 */
	
	public void printMenu();
	/**
	 * display the files and directories in this path.
	 *
	 * @param commands 
	 */
	//public void setCommands(HashMap<String, Command> commands);
	
	/**
	 * Prints the string v.
	 *
	 * @param s content of the message
	 */
	public void printStringV(String s);
	
	/**
	 * Prints the maze by byte v.
	 *
	 * @param mazeByte the maze in byte
	 */
	public void printMazeByByteV(byte[] mazeByte);
	
	/**
	 * Prints the maze2d v.
	 *
	 * @param Maze2d the maze2d
	 */
	public void printMaze2dV(int[][] Maze2d);
	
	public void sendCommandArgsToPresenter(String[] commandArgs);
	
	public void displaySolution(Solution<Position> sol);
	
	public void resetMazeFromProperties(Properties pro);
}
