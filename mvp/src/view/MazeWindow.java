package view;

import java.io.IOException;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.swt.widgets.Text;

import presenter.Properties;
import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;
import algorithms.search.Solution;

public class MazeWindow extends BasicWindow implements View {
	
	Game game;
	Maze2D maze2d;
	Maze3d maze;
	String[] algorithmsSolve = {"bfs","a star(manhattan)","a star(air)"};
	String fileName;
	Text textName;
	Spinner spinnerX;
	Spinner spinnerY;
	Spinner spinnerZ;
	Button dfsB;
	Button simpleB;
	Combo comboSolve;
	Button newGameB;
	Button solveB;
	Button saveB;
	Button loadB;
	Button openPropertiesB;
	Button exitB;
	
	public MazeWindow(String title, int width, int height) {
		super(title,width,height);
		//maze = new MyMaze3dGenerator().generate(pro.getX(), pro.getY(), pro.getZ());
	}
	
	public Maze3d getMaze() {
		return maze;
	}

	public void setMaze(Maze3d maze) {
		this.maze = maze;
	}
	
	void initWidgets() {
		shell.setLayout(new GridLayout(2,false));
		Image carP = new Image(display, "src/images/5.png");
		
		//Name of the maze
		Group groupTextName = new Group(shell, SWT.BORDER);
		groupTextName.setLayout(new GridLayout(2,true));
		groupTextName.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 1, 1));
		new Label(groupTextName, SWT.NONE).setText("Name");
		textName = new Text(groupTextName, SWT.BORDER);
			
		//display the maze
		maze2d = new Maze2D(shell, SWT.BORDER);
		maze2d.setBackgroundImage(carP);
		maze2d.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 9));	
				
	    printMenu();
		
		//shell.setBackgroundImage(carP);
	    
	    shell.addListener(SWT.Close, new Listener()
	    {
	        public void handleEvent(Event event)
	        {
				String[] exit = {"exit"};
				setChanged();
				notifyObservers(exit);
	        }
	    });
	    //ESCAPE-close
	    shell.addListener(SWT.Traverse, new Listener() {
	        public void handleEvent(Event event) {
	          switch (event.detail) {
	          case SWT.TRAVERSE_ESCAPE:
	            shell.close();
	            event.detail = SWT.TRAVERSE_NONE;
	            event.doit = false;
	            break;
	          }
	        }
	      });
		
		
		newGameB.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				String[] args = new String[6];
				args[0] = "generate 3d Maze";
				args[1] = textName.getText();
				args[2] = spinnerX.getText();
				args[3] = spinnerY.getText();
				args[4] = spinnerZ.getText();
				if(dfsB.getSelection())	
					args[5] = "dfs";
				if(simpleB.getSelection())
					args[5] = "simple";
				//if(!shell.isDisposed())	
				//	md.startGame();	
				
					maze2d.setFocus();
					sendCommandArgsToPresenter(args);
					
				
//				newGameB.setEnabled(false);
//				exitB.setEnabled(true);
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub
			}
		});
		
		//save button
		saveB.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				String[] args = new String[3];
				args[0] = "save maze";
				args[1] = textName.getText();
				args[2] = textName.getText();
				setChanged();
				notifyObservers(args);
				
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		
		//load button
		loadB.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				FileDialog fdLoaded=new FileDialog(shell,SWT.OPEN); 
				fdLoaded.setText("open");
				fdLoaded.setFilterPath("");
				String[] filterExt = { "*"}; 
				fdLoaded.setFilterExtensions(filterExt);
//				if(fdLoaded.open() != null)
//				{
					fileName = fdLoaded.open();
					String[] args = new String[3];
					args[0] = "load maze";
					args[1] = fileName;
					args[2] = fileName;
					setChanged();
					notifyObservers(args);
//				}

				
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		
		
		solveB.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				
				String[] args = new String[3];
				args[0] = "solve";
				args[1] = textName.getText();
				args[2] = comboSolve.getText();
				setChanged();
				notifyObservers(args);

				//solveB.setEnabled(false);
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		
		openPropertiesB.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				FileDialog fd=new FileDialog(shell,SWT.OPEN); 
				fd.setText("open");
				fd.setFilterPath("");
				String[] filterExt = { "*.xml"}; 
				fd.setFilterExtensions(filterExt);
//				if(fd.open() != null)
//				{
					fileName = fd.open();
					setChanged();
					notifyObservers(fileName);
//				}
				
				
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub
			}
		});
		
		exitB.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent arg0) {
//				String[] exit = {"exit"};
//				setChanged();
//				notifyObservers(exit);
				shell.close();
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
			
			}
		});
		
		
	}

	@Override
	public void start() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void printMenu() {
		//set size of the maze		
		Group groupSizeMaze = new Group(shell, SWT.BORDER);
		groupSizeMaze.setLayout(new GridLayout(6,false));
		groupSizeMaze.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 1, 1));
		new Label(groupSizeMaze, SWT.NONE).setText("X:");
		//Spinner X
	    spinnerX = new Spinner(groupSizeMaze, SWT.BORDER);
	    spinnerX.setMinimum(1);
	    spinnerX.setMaximum(40);
	    spinnerX.setSelection(5);
	    spinnerX.setIncrement(1);
	    spinnerX.setPageIncrement(100);
	    spinnerX.pack();

	    //Spinner Y
	    new Label(groupSizeMaze, SWT.NONE).setText("Y:");
	    spinnerY = new Spinner(groupSizeMaze, SWT.BORDER);
	    spinnerY.setMinimum(3);
	    spinnerY.setMaximum(40);
	    spinnerY.setSelection(5);
	    spinnerY.setIncrement(1);
	    spinnerY.setPageIncrement(100);
	    spinnerY.pack();
	    //Spinner Z
	    new Label(groupSizeMaze, SWT.NONE).setText("Z:");
	    spinnerZ = new Spinner(groupSizeMaze, SWT.BORDER);
	    spinnerZ.setMinimum(3);
	    spinnerZ.setMaximum(40);
	    spinnerZ.setSelection(5);
	    spinnerZ.setIncrement(1);
	    spinnerZ.setPageIncrement(100);
	    spinnerZ.pack();

	    //choose algorithm
  		Group groupTypeMaze = new Group(shell, SWT.BORDER);
  		groupTypeMaze.setLayout(new GridLayout(2,true));
  		groupTypeMaze.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 1, 1));
  		groupTypeMaze.setText("Select type of algorithm");
  		dfsB = new Button(groupTypeMaze, SWT.RADIO);
  		dfsB.setText("dfs");
  		dfsB.setSelection(true);
  		simpleB = new Button(groupTypeMaze, SWT.RADIO);
  		simpleB.setText("simple");
  		
  	    //new game
		newGameB = new Button(shell, SWT.PUSH);
		newGameB.setText("start game");
		newGameB.setLayoutData(new GridData(SWT.FILL,SWT.None,false,false,1,1));
		
		
		Group groupSolution = new Group(shell, SWT.BORDER);
		groupSolution.setLayout(new GridLayout(1,true));
		groupSolution.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false,false, 1, 1));
		
		new Label(groupSolution, SWT.NONE).setText("Select algorithm for solution:");

		comboSolve = new Combo(groupSolution, SWT.DROP_DOWN | SWT.READ_ONLY);
		comboSolve.setItems(algorithmsSolve);
		comboSolve.select(0);
		comboSolve.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));
		
		solveB = new Button(groupSolution, SWT.PUSH);
		solveB.setText("solution");
		solveB.setLayoutData(new GridData(SWT.FILL,SWT.None,false,false,1,1));
		
		//save button
		saveB = new Button(shell, SWT.None);
		saveB.setText("save maze");
		saveB.setLayoutData(new GridData(SWT.FILL,SWT.None,false,false,1,1));
		
		//load button
		loadB = new Button(shell, SWT.None);
		loadB.setText("load maze");
		loadB.setLayoutData(new GridData(SWT.FILL,SWT.None,false,false,1,1));
		
		
		//open properties Button
		openPropertiesB = new Button(shell, SWT.PUSH);
		openPropertiesB.setText("insert properties");
		openPropertiesB.setLayoutData(new GridData(SWT.FILL,SWT.None,false,false,1,1));
		
		exitB = new Button(shell, SWT.PUSH);
		exitB.setText("exit");
		exitB.setLayoutData(new GridData(SWT.FILL,SWT.None,false,false,1,1));
  		
  		/*
		Group groupSizeMaze = new Group(shell, SWT.BORDER);
		groupSizeMaze.setLayout(new GridLayout(4,true));
		groupSizeMaze.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 1, 1));
		
		new Label(groupSizeMaze, SWT.NONE).setText("Enter:");
		Text textX = new Text(groupSizeMaze, SWT.BORDER);
		textX.setText("X");
		textX.setLayoutData(new GridData(SWT.NONE, SWT.NONE, false, false, 1, 1));
		Text textY = new Text(groupSizeMaze, SWT.BORDER);
		textY.setText("Y");
		textY.setLayoutData(new GridData(SWT.NONE, SWT.NONE, false, false, 1, 1));
		Text textZ = new Text(groupSizeMaze, SWT.BORDER);
		textZ.setText("Z");
		textZ.setLayoutData(new GridData(SWT.NONE, SWT.NONE, false, false, 1, 1));
		
		//select cross by 
		Group groupCrossBy = new Group(shell, SWT.BORDER);
		groupCrossBy.setLayout(new GridLayout(5,true));
		groupCrossBy.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 1, 1));
		groupCrossBy.setText("Select cross by");
		Button xB = new Button(groupCrossBy, SWT.RADIO);
		xB.setText("X");
		xB.setSelection(true);
		Button yB = new Button(groupCrossBy, SWT.RADIO);
		yB.setText("Y");
		Button zB = new Button(groupCrossBy, SWT.RADIO);
		zB.setText("Z");
		*/
  		
	}

	@Override
	public void printStringV(String s) {
		//System.out.println(s);
		String[] words = s.split(" ");
		//request display maze
		if(words[0].equals("maze") || words[0].equals("loaded"))
		{
			String[] args = new String[2];
			args[0] = "display";
			args[1] = words[1];
			this.setChanged();
			this.notifyObservers(args);
		}
		//request display solution
		if(words[0].equals("solution"))
		{
			String[] args = new String[2];
			args[0] = "display solution";
			args[1] = words[1];
			this.setChanged();
			this.notifyObservers(args);
		}
		if(words[0].equals("error"))
		{
			MessageBox MessageBox = new MessageBox(shell, SWT.ICON_WORKING | SWT.OK);
			MessageBox.setText("ERROR!");
			MessageBox.setMessage(s);
			MessageBox.open();
		}
		if(words[0].equals("Properties"))
		{
			MessageBox MessageBox = new MessageBox(shell, SWT.ICON_WORKING | SWT.OK);
			MessageBox.setText("Load Properties");
			MessageBox.setMessage(s);
			MessageBox.open();
		}
		if(words[0].equals("all") || words[0].equals("saved"))
		{
			MessageBox MessageBox = new MessageBox(shell, SWT.ICON_WORKING | SWT.OK);
			if(words[0].equals("all"))	
				MessageBox.setText("Exit successfully");
			if(words[0].equals("saved"))
				MessageBox.setText("Saved successfully");
			MessageBox.setMessage(s);
			MessageBox.open();
		}
		
		
	}

	@Override
	public void printMazeByByteV(byte[] mazeByte) {
		try {
			Maze3d maze3d = new Maze3d(mazeByte);
			//System.out.println(maze3d.toString());
			setMaze(maze3d);
			game = new Game(maze3d,maze2d);
			//game.setMaze3d();
			//game.setMaze2d();
		    game.startGameG();
		    maze2d.setFocus();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public void printMaze2dV(int[][] Maze2d) {
		
		
	}

	@Override
	public void sendCommandArgsToPresenter(String[] commandArgs) {
		setChanged();
		notifyObservers(commandArgs);
		
	}

	@Override
	public void displaySolution(Solution<Position> sol) {
		game.solveGame(sol);
		
	}

	@Override
	public void resetMazeFromProperties(Properties pro) {
		textName.setText(pro.getName());
		spinnerX.setSelection(pro.getX());
		spinnerY.setSelection(pro.getY());
		spinnerZ.setSelection(pro.getZ());
		if(pro.getAlgorithmCreate().equals("dfs"))
			dfsB.setSelection(true);
		if(pro.getAlgorithmCreate().equals("simple"))
			{
				simpleB.setSelection(true);
				dfsB.setEnabled(false);
			}
		if(pro.getAlgorithmSolve().equals("bfs"))
			comboSolve.select(0);
		if(pro.getAlgorithmSolve().equals("a star(manhattan)"))
			comboSolve.select(1);
		if(pro.getAlgorithmSolve().equals("a star(air)"))
			comboSolve.select(2);
		
	}
	
	
}
