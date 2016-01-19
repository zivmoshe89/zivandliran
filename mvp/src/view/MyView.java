package view;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Observable;

import presenter.Properties;
import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;
import algorithms.search.Solution;

public class MyView extends Observable implements View {

	
	CLI cli;
	//HashMap<String, Command> commands = new HashMap<String, Command>();
	
	public MyView() {
		
	    cli = new CLI(new BufferedReader(new InputStreamReader(System.in)), new PrintWriter(System.out),this);
	}
	
	
	
	/**
     * {@inheritDoc}
     */
	@Override
	public void start() {
		printMenu();
		cli.start();
	//	loadProperties();
	}
	
	/**
     * {@inheritDoc}
     */
//	@Override
//	public void setCommands(HashMap<String, Command> commands) {
//		this.commands = commands;
//		cli.setCommands(commands);
//	}
	
	/**
     * {@inheritDoc}
     */
	@Override
	public void printMenu() {
		System.out.println("enter command and separation by , \n"
				+ "command: dir <path>\n"
				+ "command: generate 3d Maze <name> <x> <y> <z> <algorithm - simple/dfs>\n"
				+ "command: display <name>\n"
				+ "command: display cross section by {X-Y-Z} <index> for <name>\n"
				+ "command: save maze <name> <file name>\n"
				+ "command: load maze <file name> <name>\n"
				+ "command: maze size <name>\n"
				+ "command: file size <name file>\n"
				+ "command: solve <name> <algorithm - bfs/Astar manhattan distance/Astar air distance>\n"
				+ "command: display solution <name>\n"
				+ "command: exit\n<<<MVP>>>");
	}
	@Override
	public void sendCommandArgsToPresenter(String[] commandArgs)
	{
		if(commandArgs[0].equals("generate 3d Maze") && commandArgs[5].equals(""))
		{
			//commandArgs[5] = properties.createAlgorithm;
		}
		this.setChanged();
		this.notifyObservers(commandArgs);
	}
	
	/**
     * {@inheritDoc}
     */
	@Override
	public void printStringV(String s) {
		System.out.println(s);
	}
	
	/**
     * {@inheritDoc}
     */
	@Override
	public void printMazeByByteV(byte[] mazeByte) {
		try {
			Maze3d maze = new Maze3d(mazeByte);
			System.out.println(maze.toString());
			//mw.setMaze(maze); 
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	/**
     * {@inheritDoc}
     */
	@Override
	public void printMaze2dV(int[][] Maze2d) {
		for (int i=0; i<Maze2d.length; i++)
		{
			if (i!=0)
				System.out.println();
			for (int j=0; j<Maze2d[i].length; j++)
				System.out.print(Maze2d[i][j]);
		}
		System.out.println();
		
	}
	/*
	public void loadProperties()
	{
		Properties pro;
		FileInputStream fis;
		XMLDecoder xmlDecoder = null;
		try {
			fis = new FileInputStream("properties.xml");
			BufferedInputStream bis = new BufferedInputStream(fis);
			xmlDecoder = new XMLDecoder(bis);
			pro = (Properties) xmlDecoder.readObject();
			System.out.println(pro.getNumOfThred());
			System.out.println(pro.getAlgorithmCreate());
			System.out.println(pro.getAlgorithmSolve());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		finally{
			xmlDecoder.close();
		}
		
	}
	*/



	@Override
	public void displaySolution(Solution<Position> sol) {
		System.out.println(sol.toString()); 
	}



	@Override
	public void resetMazeFromProperties(Properties pro) {
		// TODO Auto-generated method stub
		
	}
	
	
}
