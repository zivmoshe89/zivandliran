package view;


import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;


public class Maze2D extends MazeDisplay {

	
	public Maze2D(Composite parent, int style) 
	{
		super(parent, SWT.DOUBLE_BUFFERED);
	}
	public void startGame()
	{
		
		setBackground(new Color(null, 0, 0, 0));//Background of the maze 
		
		gameCharacter = new GameCharacter(0, maze3d.getStartPosition().getY(),maze3d.getStartPosition().getZ());
//		Text t= new Text(getShell(), SWT.READ_ONLY | SWT.BORDER);
//		t.setVisible(true);
//		t.setText("Floor: " +floor);
		
		addPaintListener(new PaintListener() 
		{
			@Override
			public void paintControl(PaintEvent e) {
				
			  e.gc.setForeground(new Color(null,0,0,0));
			  e.gc.setBackground(new Color(null,0,0,0));
			  
			  Image goalPic = new Image(getDisplay(), "src/images/goalFlag.png");
			  Image as = new Image(getDisplay(), "src/images/as.jpg");
			  Image grass = new Image(getDisplay(), "src/images/grass.jpg");
			  Image carU = new Image(getDisplay(), "src/images/car-U.png");
			  Image carD = new Image(getDisplay(), "src/images/car-D.png");
			  Image carR = new Image(getDisplay(), "src/images/car-R.png");
			  Image carL = new Image(getDisplay(), "src/images/car-L.png");
			  Image arrowUp = new Image(getDisplay(), "src/images/arrowUp.png");
			  Image arrowDown = new Image(getDisplay(), "src/images/arrowDown.png");
			  Image arrowUpDown = new Image(getDisplay(), "src/images/arrowUpDown.png");
			  Image star = new Image(getDisplay(), "src/images/star.png");
			  //Image finish = new Image(getDisplay(), "src/images/finish.png");
			  
			  setBackgroundImage(as);
			    int width=getSize().x;
			    int height=getSize().y;
			   // int pro = Math.min(width, height);
			    int w=width/mazeData[0].length;//w width of sell
			    int h=height/mazeData.length;//h height of sell
				
			    for(int i=0;i<mazeData.length;i++)
				      for(int j=0;j<mazeData[i].length;j++)
				      {
				          int x=j*w;
				          int y=i*h;
				          if(mazeData[i][j]!= 0)
				        	e.gc.drawImage(grass, 0, 0, 50, 50, x, y, w, h);    
				        	  //e.gc.fillRectangle(x,y,w,h);//paint in black cell = 1
				          //display arrows
				          if((gameCharacter.getX()+1 < maze3d.getX() && maze3d.getValue(gameCharacter.getX(), i, j) == 0 && maze3d.getValue(gameCharacter.getX() + 1, i, j) == 0)
				        		  && (gameCharacter.getX()-1 > 0 && maze3d.getValue(gameCharacter.getX(), i, j) == 0 && maze3d.getValue(gameCharacter.getX() - 1, i, j) == 0))
				        	  		e.gc.drawImage(arrowUpDown, 0, 0, 50, 50, x, y, w, h);
				          else{
				        	  if(gameCharacter.getX()+1 < maze3d.getX() && maze3d.getValue(gameCharacter.getX(), i, j) == 0 && maze3d.getValue(gameCharacter.getX() + 1, i, j) == 0)
					        	  e.gc.drawImage(arrowUp, 0, 0, 50, 50, x, y, w, h);
					          if(gameCharacter.getX()-1 > 0 && maze3d.getValue(gameCharacter.getX(), i, j) == 0 && maze3d.getValue(gameCharacter.getX() - 1, i, j) == 0)
					        	  e.gc.drawImage(arrowDown, 0, 0, 50, 50, x, y, w, h);
				          }
				          
				          if(displaySolution){
								if(mazeSolution[gameCharacter.getX()][i][j]==8)
								{
									e.gc.drawImage(star, 0, 0, 50, 50, x, y, w, h);
								}
				          }
				      }
			    
			   // if(maze.getCurrentPosition().getZ()+1<maze.getHight() && maze.getMazeWalls()[maze.getCurrentPosition().getZ()+1][j][i]==0)
			   //display character position
			    if(characterPosition == "")
			    	e.gc.drawImage(carU, 0, 0, 50, 50,gameCharacter.z*w, gameCharacter.y*h, w, h);
			    switch (characterPosition) {
				case "up":
					e.gc.drawImage(carU, 0, 0, 50, 50,gameCharacter.z*w, gameCharacter.y*h, w, h);
					break;
					
				case "down":
					e.gc.drawImage(carD, 0, 0, 50, 50,gameCharacter.z*w, gameCharacter.y*h, w, h);
					break;
				case "right":
					e.gc.drawImage(carR, 0, 0, 50, 50,gameCharacter.z*w, gameCharacter.y*h, w, h);
					break;
				case "left":
					e.gc.drawImage(carL, 0, 0, 50, 50,gameCharacter.z*w, gameCharacter.y*h, w, h);
					break;
				default:
					break;
				}
			    //display goal position
			    if(gameCharacter.getX() == maze3d.getGoalPosition().getX())
			    {
			    	
			    	e.gc.drawImage(goalPic, 0, 0, 50, 50, maze3d.getGoalPosition().getZ()*w, maze3d.getGoalPosition().getY()*h, w, h);
			    	if(gameCharacter.getZ() == maze3d.getGoalPosition().getZ() && gameCharacter.getY() == maze3d.getGoalPosition().getY())
			    		e.gc.drawImage(star, 0, 0, 50, 50,width/3, height/3, 200, 200);
			    		
			    }	
			    
			}
		});

		
	}
	
	
	
	@Override
	public void setCharacterPosition(int row, int col) {
		if(gameCharacter.getZ() < row)
			characterPosition = "right";
		if(gameCharacter.getZ() > row)
			characterPosition = "left";
		if(gameCharacter.getY() > col)
			characterPosition = "up";
		if(gameCharacter.getY() < col)
			characterPosition = "down";
		gameCharacter.setZ(row);
		gameCharacter.setY(col);
		redraw();
	}

	@Override
	public void moveUp() {
		setCharacterPosition(gameCharacter.z,gameCharacter.y-1);
		
	}

	@Override
	public void moveDown() {
		setCharacterPosition(gameCharacter.z,gameCharacter.y+1);
	}

	@Override
	public void moveLeft() {
		setCharacterPosition(gameCharacter.z-1,gameCharacter.y);
	}

	@Override
	public void moveRight() {
		setCharacterPosition(gameCharacter.z+1,gameCharacter.y);
	}
	@Override
	public void pageUp() {
		gameCharacter.setX(gameCharacter.getX()+1);
		redraw();
	}
	@Override
	public void pageDown() {
		gameCharacter.setX(gameCharacter.getX()-1);
		redraw();
	}
	
}
