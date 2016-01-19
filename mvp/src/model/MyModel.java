package model;

import io.MyCompressorOutputStream;
import io.MyDecompressorInputStream;

import java.beans.XMLDecoder;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Observable;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.zip.GZIPInputStream;

import presenter.Properties;
import algorithms.demo.Maze3dSearchable;
import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Maze3dGenerator;
import algorithms.mazeGenerators.MyMaze3dGenerator;
import algorithms.mazeGenerators.Position;
import algorithms.mazeGenerators.SimpleMaze3dGenerator;
import algorithms.search.AStar;
import algorithms.search.BFS;
import algorithms.search.MazeAirDistance;
import algorithms.search.MazeManhattanDistance;
import algorithms.search.Searcher;
import algorithms.search.Solution;

public class MyModel extends Observable implements Model {

	/** The Executor. */
	ExecutorService executor = Executors.newCachedThreadPool();
	
	/** The created mazes. */
	HashMap<String, Maze3d> createdMazes;
	
	/** The saved mazes. */
	HashMap<String, String> savedMazes;
	
	/** The solution mazes. */
	ConcurrentHashMap<String, Solution<Position>> solutionMazes;
	
	@SuppressWarnings("unchecked")
	public MyModel() throws ClassNotFoundException  {
		createdMazes = new HashMap<String, Maze3d>();
		savedMazes = new HashMap<String, String>();
		solutionMazes = new ConcurrentHashMap<String, Solution<Position>>();
		ObjectInputStream ois;
		try {
			ois = new ObjectInputStream(new GZIPInputStream(new FileInputStream("solution")));
			solutionMazes =  (ConcurrentHashMap<String, Solution<Position>>) ois.readObject();
			ois.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}	
		
		
	}
	
	
	/**
     * {@inheritDoc}
     */
	@Override
	public void dirM(String path) {
		File file = new File(path);
		String s = "";
		if(file.exists())
		{
			if(file.isFile())
			{
				s = file.getName();
				this.setChanged();
				this.notifyObservers(s);
			}	
			else{
				for(File f : file.listFiles())
					s += f.getName() + ", ";
				this.setChanged();
				this.notifyObservers(s);
			}
		}
		else{
			this.setChanged();
			this.notifyObservers("error - file not exist");
		}
	}
	
	/**
     * {@inheritDoc}
	 * @throws ExecutionException 
	 * @throws InterruptedException 
     */
	@Override
	public void generateMazeM(String[] args) throws InterruptedException, ExecutionException {
		
		if(args.length != 5)
		{
			this.setChanged();
			this.notifyObservers("error - invalid input");
			return;
		}
		if(Integer.parseInt(args[1]) < 1 || Integer.parseInt(args[2]) < 3 || Integer.parseInt(args[3]) < 3)
		{
			this.setChanged();
			this.notifyObservers("error - invalid input");
			return;
		}
		if(createdMazes.containsKey(args[0]))
		{
			this.setChanged();
			this.notifyObservers("error - the name is already exits");
			return;
		}
		if(!(args[4].equals("dfs")) && !(args[4].equals("simple")))
		{
			 
			this.setChanged();
			this.notifyObservers("error - invalid input");
			return;
		}
		
		Future<Maze3d> f = executor.submit (new generateMazeMCallable (args));
		Maze3d maze = null;
	    maze = f.get();
	    
		if(maze == null)
		{
			Thread.sleep(50);
			maze = f.get();
		}
				
		createdMazes.put(args[0], maze);
		this.setChanged();
		this.notifyObservers("maze " + args[0] + " is ready");
		
	}
	
	/**
     * {@inheritDoc}
     */
	@Override
	public void displayMazeM(String name) {
		
		if(!createdMazes.containsKey(name))
		{
			this.setChanged();
			this.notifyObservers("error - name of the maze isn't exits");
			return;
		}
		Maze3d displayMaze = createdMazes.get(name);
		this.setChanged();
		this.notifyObservers(displayMaze.toByteArray());
		
	}
	
	/**
     * {@inheritDoc}
     */
	@Override
	public void crossSectionM(String[] args) {
		
		if(!createdMazes.containsKey(args[2]))
		{
			this.setChanged();
			this.notifyObservers("error - name of the maze isn't exits");
			return;
		}
		if(args[0].equals("x"))
		{
			
			Maze3d crossMaze = createdMazes.get(args[2]);
			//check if the index in the range of 0 and size of x
			if((Integer.parseInt(args[1]) >= 0) && (Integer.parseInt(args[1]) < crossMaze.getX()))
			{
				int[][] maze2d = crossMaze.getCrossSectionByX(Integer.parseInt(args[1]));
				this.setChanged();
				this.notifyObservers(maze2d);
			}
			else
			{
				this.setChanged();
				this.notifyObservers("error - index out of bounds");
				return;
			}
		}
		if(args[0].equals("y"))
		{
			Maze3d crossMaze = createdMazes.get(args[2]);
			//check if the index in the range of 0 and size of y
			if((Integer.parseInt(args[1]) >= 0) && (Integer.parseInt(args[1]) < crossMaze.getY()))
			{
				int[][] maze2d = crossMaze.getCrossSectionByY(Integer.parseInt(args[1]));
				this.setChanged();
				this.notifyObservers(maze2d);
			}
			else
			{
				this.setChanged();
				this.notifyObservers("error - index out of bounds");
				return;
			}
		}
		if(args[0].equals("z"))
		{
			Maze3d crossMaze = createdMazes.get(args[2]);
			//check if the index in the range of 0 and size of z
			if((Integer.parseInt(args[1]) >= 0) && (Integer.parseInt(args[1]) < crossMaze.getZ()))
			{
				int[][] maze2d = crossMaze.getCrossSectionByZ(Integer.parseInt(args[1]));
				this.setChanged();
				this.notifyObservers(maze2d);
			}
			else
			{
				this.setChanged();
				this.notifyObservers("error - index out of bounds");
				return;
			}
		}
		
	}

	/**
     * {@inheritDoc}
     */
	@Override
	public void saveMazeM(String[] args) {
		if(args.length != 2)
		{
			this.setChanged();
			this.notifyObservers("error - invalid input");
			return;
		}
		if(!createdMazes.containsKey(args[0]))
		{
			this.setChanged();
			this.notifyObservers("error - name of the maze isn't exits");
			return;
		}
		
		Maze3d maze = createdMazes.get(args[0]);
		byte[] mazeArray = new byte[maze.toByteArray().length];
		mazeArray = maze.toByteArray();
		
		//System.out.println(maze.toByteArray().length);
		try {
			
			OutputStream out=new MyCompressorOutputStream(new FileOutputStream(args[1]));			
			out.write(mazeArray);
			out.flush();
			out.close();
			
			
	        int sizeMaze = maze.toByteArray().length; 
	        byte[]sizeArray = toBytes(sizeMaze); 
	        OutputStream outSize=new MyCompressorOutputStream(new FileOutputStream(args[1] + "Size"));			
			outSize.write(sizeArray);
			outSize.flush();
			outSize.close();
			savedMazes.put(args[0], args[1]);
			this.setChanged();
			this.notifyObservers("saved " + args[1]);
	        
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	/**
     * {@inheritDoc}
     */
	@Override
	public void loadMazeM(String[] args) {
		if(args.length != 2)
		{
			this.setChanged();
			this.notifyObservers("error - invalid input");
			return;
		}
		
		byte[] sizeByte = new byte[4]; 
		try
		{
			InputStream inSize=new MyDecompressorInputStream(new FileInputStream(args[0]+"Size"));
			InputStream in=new MyDecompressorInputStream(new FileInputStream(args[0]));
			inSize.read(sizeByte);//read 4 first byte that present length
			inSize.close();
			
			int size = fromByteArray(sizeByte);
			//System.out.println(size);
			byte[] mazeOriginal=new byte[size]; 
			in.read(mazeOriginal);//read the content of the maze 
			in.close();
			
			
			createdMazes.put(args[1], new Maze3d(mazeOriginal));
			this.setChanged();
			this.notifyObservers("loaded " +  args[1] + " from file " + args[0]);
		
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
     * {@inheritDoc}
     */
	@Override
	public void mazeSizeM(String name) {
		
		if(!createdMazes.containsKey(name))
		{
			this.setChanged();
			this.notifyObservers("error - name of the maze isn't exits");
			return;
		}
		Maze3d maze = createdMazes.get(name);
		int length = maze.toByteArray().length;
		this.setChanged();
		this.notifyObservers("size of the maze " + name + " in the memory is " + length);
		
	}
	
	/**
     * {@inheritDoc}
     */
	@Override
	public void fileSizeM(String name) {
		
		if(!savedMazes.containsKey(name))
		{
			this.setChanged();
			this.notifyObservers("error - name of the file isn't exits");
			return;
		}
		File f = new File(savedMazes.get(name));
		double length = f.length();
		this.setChanged();
		this.notifyObservers("size of the file " + name + " is " + length);
		
	}
	
	
	/**
     * {@inheritDoc}
	 * @throws ExecutionException 
	 * @throws InterruptedException 
     */
	@Override
	public void solveM(String[] args) throws InterruptedException, ExecutionException {
		if(args.length != 2)
		{
			this.setChanged();
			this.notifyObservers("error - invalid input");
			return;
		}
		if(!createdMazes.containsKey(args[0]))
		{
			this.setChanged();
			this.notifyObservers("error - name of the maze isn't exits");
			return;
		}
		if(solutionMazes.containsKey(args[0]))
		{
			this.setChanged();
			this.notifyObservers("solution is already ready");
			return;
		}
		if(args[1].equals("bfs") || args[1].equals("Astar manhattan distance") || args[1].equals("Astar air distance"))
		{
			Solution<Position> solution = null;
			Future<Solution<Position>> s = executor.submit(new solveCallable(args));
			solution = s.get();
			while(solution == null)
			{
				Thread.sleep(50);
				solution = s.get();
			}
			this.setChanged();
			this.notifyObservers("solution " + args[0] + " is ready");
		}
		
	}

	/**
     * {@inheritDoc}
     */
	@Override
	public void displaySolutionM(String name) {
		
		if(!createdMazes.containsKey(name) && !solutionMazes.containsKey(name) )
		{
			this.setChanged();
			this.notifyObservers("error - name of the maze isn't exits");
			return;
		}
		if(!solutionMazes.containsKey(name))
		{
			this.setChanged();
			this.notifyObservers("error - there is no solution for this name");
			return;
		}
		
		this.setChanged();
		this.notifyObservers(solutionMazes.get(name));
		
	}

	
	public void loadProperties(String fileName)
	{	
		Properties pro;
		FileInputStream fis;
		XMLDecoder xmlDecoder = null;
		try {
			fis = new FileInputStream(fileName);
			BufferedInputStream bis = new BufferedInputStream(fis);
			xmlDecoder = new XMLDecoder(bis);
			pro = (Properties) xmlDecoder.readObject();
			System.out.println(pro.getNumOfThred());
			System.out.println(pro.getAlgorithmCreate());
			System.out.println(pro.getAlgorithmSolve());
			this.setChanged();
			this.notifyObservers(pro);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		finally{
			xmlDecoder.close();
		}
		
	}
	
	/**
     * {@inheritDoc}
	 * @throws IOException 
	 * @throws FileNotFoundException 
     */
	@Override
	public void exitM() throws FileNotFoundException, IOException 
	{
		
//		ObjectOutputStream gzos = new ObjectOutputStream(new GZIPOutputStream(new FileOutputStream("solution"))) ;
//		gzos.writeObject(solutionMazes);
//		gzos.flush();
//		gzos.close();
		executor.shutdown();
		this.setChanged();
		this.notifyObservers("all threds and files are closed");
	}
	
	private class generateMazeMCallable implements Callable<Maze3d>
	{
		String[] args;
		public generateMazeMCallable(String[] args) {
			this.args = args;
		}
		
		@Override
		public Maze3d call() throws Exception {
			
			Maze3dGenerator maze = null;
			if(args[4].equals("dfs"))
				maze = new MyMaze3dGenerator();
			else if(args[4].equals("simple"))
				maze = new SimpleMaze3dGenerator();
			
			Maze3d maze3d = maze.generate(Integer.parseInt(args[1]), Integer.parseInt(args[2]), Integer.parseInt(args[3]));		
			
			return maze3d;
		}
		
	}
	
	private class solveCallable implements Callable<Solution<Position>>
	{
		Solution<Position> solution;
		String[] args;
		public solveCallable(String[] args) {
			this.args = args;
		}
		
		@Override
		public Solution<Position> call() throws Exception {
			
			Maze3dSearchable ms = new Maze3dSearchable(createdMazes.get(args[0]));
			
			if(args[1].equals("bfs"))
			{
				Searcher<Position> bfs = new BFS<Position>();
			    solution = bfs.search(ms);
			}
			if(args[1].equals("Astar manhattan distance"))
			{
				Searcher<Position> AstarM = new AStar<Position>(new MazeManhattanDistance());
			    solution = AstarM.search(ms);
			}
			if(args[1].equals("Astar air distance"))
			{
				Searcher<Position> AstarA = new AStar<Position>(new MazeAirDistance());
			    solution = AstarA.search(ms);
			}
			
			solutionMazes.put(args[0], solution);
			return solution;
		}
		
	}
	public byte[] toBytes(int i)
	{
	  byte[] result = new byte[4];

	  result[0] = (byte) (i >> 24);
	  result[1] = (byte) (i >> 16);
	  result[2] = (byte) (i >> 8);
	  result[3] = (byte) (i);

	  return result;
	}
	
	int fromByteArray(byte[] bytes) {
	     return bytes[0] << 24 | (bytes[1] & 0xFF) << 16 | (bytes[2] & 0xFF) << 8 | (bytes[3] & 0xFF);
	}
	
	
}

