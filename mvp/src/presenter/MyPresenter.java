package presenter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.ExecutionException;

import model.Model;
import view.View;
import algorithms.search.Solution;

public class MyPresenter implements Presenter, Observer {

	View v;
	Model m;
	
	HashMap<String, Command> commands;
	
	
	public MyPresenter(View v,Model m) {
		this.v = v;
		this.m = m;
		commands = new HashMap<String, Command>();
		commands.put("dir", new DirCommand());
		commands.put("generate 3d Maze", new GenerateMazeCommand());
		commands.put("display", new DisplayMazeCommand());
		commands.put("display cross section by {X-Y-Z}", new DisplayCrossSectionCommand());
		commands.put("save maze", new SaveMazeCommand());
		commands.put("load maze", new LoadMazeCommand());
		commands.put("maze size", new MazeSizeCommand());
		commands.put("file size", new FileSizeCommand());
		commands.put("solve", new SolveCommand());
		commands.put("display solution", new DisplaySolutionCommand());
		commands.put("exit", new ExitCommand());
		//v.setCommands(commands);
	}
	
	//check who send notification and decide what to do 
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void update(Observable o, Object arg) {
		
		if(o instanceof View)
		{
			if(arg instanceof String)
				m.loadProperties((String)arg);
			else 
			{
				String[] sp = ((String[]) arg);
				if(commands.containsKey(sp[0]))
				{
					Command command = commands.get(sp[0]);
					String[] arrArgs = new String[sp.length-1];
					for(int i = 0;i < sp.length-1;i++)
						arrArgs[i] = sp[i+1];
					command.doCommand(arrArgs);
				}
				else{
					v.printStringV("name of command is not legal");
					return;
				}
			}
			
		}
		else if(o instanceof Model)
		{
			if(arg instanceof String)
				v.printStringV((String)arg);
			if(arg instanceof byte[])
				v.printMazeByByteV((byte[])arg);
			if(arg instanceof int[][])
				v.printMaze2dV((int[][])arg);
			if(arg instanceof Solution)
				v.displaySolution((Solution)arg);
			if(arg instanceof Properties)
				v.resetMazeFromProperties((Properties)arg);
		}
	}
	/**
     * {@inheritDoc}
     */
//	@Override
//	public void setView(View v) {
//		this.v = v;
//		v.setCommands(commands);
//	}
	/**
	 * The Class DirCommand.
	 */
	private class DirCommand implements Command
	{
		@Override
		public void doCommand(String[] path) {
			m.dirM(path[0]);	
		}
	}
	/**
	 * The Class GenerateMazeCommand.
	 */
	private class GenerateMazeCommand implements Command
	{
		@Override
		public void doCommand(String[] args) {
			try {
				m.generateMazeM(args);
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (ExecutionException e) {
				e.printStackTrace();
			}	
		}
	}
	/**
	 * The Class DisplayMazeCommand.
	 */
	private class DisplayMazeCommand implements Command
	{
		@Override
		public void doCommand(String[] args) {
			m.displayMazeM(args[0]);	
		}
	}
	/**
	 * The Class DisplayCrossSectionCommand.
	 */
	private class DisplayCrossSectionCommand implements Command
	{
		@Override
		public void doCommand(String[] args) {
			m.crossSectionM(args);	
		}
	}
	
	/**
	 * The Class SaveMazeCommand.
	 */
	private class SaveMazeCommand implements Command
	{
		@Override
		public void doCommand(String[] args) {
			m.saveMazeM(args);	
		}
		
	}
	
	/**
	 * The Class LoadMazeCommand.
	 */
	private class LoadMazeCommand implements Command
	{
		@Override
		public void doCommand(String[] args) {
			m.loadMazeM(args);	
		}
		
	}
	
	/**
	 * The Class MazeSizeCommand.
	 */
	private class MazeSizeCommand implements Command
	{
		@Override
		public void doCommand(String[] args) {
			m.mazeSizeM(args[0]);	
		}
		
	}
	
	/**
	 * The Class MazeSizeCommand.
	 */
	private class FileSizeCommand implements Command
	{
		@Override
		public void doCommand(String[] args) {
			m.fileSizeM(args[0]);	
		}
		
	}
	
	/**
	 * The Class SolveCommand.
	 */
	private class SolveCommand implements Command
	{
		@Override
		public void doCommand(String[] args) {
			try {
				m.solveM(args);
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (ExecutionException e) {
				e.printStackTrace();
			}	
		}
		
	}
	
	/**
	 * The Class DisplaySolutionCommand.
	 */
	private class DisplaySolutionCommand implements Command
	{
		@Override
		public void doCommand(String[] args) {
			m.displaySolutionM(args[0]);	
		}
		
	}
	
	/**
	 * The Class ExitCommand.
	 */
	private class ExitCommand implements Command
	{
		@Override
		public void doCommand(String[] args) {
			try {
				m.exitM();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}	
		}
		
	}
	
	
}
