package model;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.concurrent.ExecutionException;

/**
 * The Interface Model.
 * 
 * @author Liran Avishay
 */
public interface Model {

	
	/**
	 * Dir m.
	 *
	 * @param path the path
	 */
	public void dirM(String path);
	
	/**
	 * Generate maze m.
	 *
	 * @param args the args
	 * @throws ExecutionException 
	 * @throws InterruptedException 
	 */
	public void generateMazeM(String[] args) throws InterruptedException, ExecutionException;
	/**
	 * Display maze m.
	 *
	 * @param name the name
	 */
	public void displayMazeM(String name);
	
	/**
	 * Cross section m.
	 *
	 * @param args the args
	 */
	public void crossSectionM(String[] args);
	
	/**
	 * Save maze m.
	 *
	 * @param args the args
	 */
	public void saveMazeM(String[] args);
	
	/**
	 * Load maze m.
	 *
	 * @param args the args
	 */
	public void loadMazeM(String[] args);
	
	/**
	 * Maze size m.
	 *
	 * @param name the name
	 */
	public void mazeSizeM(String name);
	
	/**
	 * File size m.
	 *
	 * @param name the name
	 */
	public void fileSizeM(String name);
	/**
	 * Solve m.
	 *
	 * @param args the args
	 * @throws ExecutionException 
	 * @throws InterruptedException 
	 */
	public void solveM(String[] args) throws InterruptedException, ExecutionException;
	
	/**
	 * Display solution m.
	 *
	 * @param name the name
	 */
	public void displaySolutionM(String name);
	
	public void loadProperties(String fileName);

	/**
	 * Exit m.
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 */
	public void exitM() throws FileNotFoundException, IOException;	
}
