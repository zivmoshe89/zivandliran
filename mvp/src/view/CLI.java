package view;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
/**
 * defines the CLI .
 *
 * @author Liran Avishay&Ziv Moshe
 */
public class CLI {

	private View v;
	private BufferedReader in;
	private PrintWriter out;
	//private HashMap<String, Command> commands = new HashMap<String, Command>();
	
	public CLI(BufferedReader in,PrintWriter out,View v) 
	{
		this.in = in;
		this.out = out;
		this.v = v;
		
	}
//	public void setCommands(HashMap<String, Command> commands)
//	{
//		this.commands = commands;
//	}
	
	//start the CLI
	public void start()
	{
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				
				String line ="";
				try {
						
					do
					{	
						System.out.println("enter command:");
						line = in.readLine();
						
						if(!line.contains(",") && (!line.equals("exit")))
							System.out.println("please use , for separate variables");
						else if(line.contains(",") || line.equals("exit"))
							{
								String[] sp = line.split(",");
						//		String commandName = sp[0];
						//		String[] args = null; 
								
								//if(commands.containsKey(commandName))
								//{
									v.sendCommandArgsToPresenter(sp);
									/*
									Command command = commands.get(commandName);
									args = new String[sp.length-1];
									for(int i = 0;i < sp.length-1;i++)
										args[i] = sp[i+1];
									command.doCommand(args);
									*/	
								//}
							}
						
						
						
							
					}while(!line.equals("exit"));	
					
					
				} catch (IOException e) {
					e.printStackTrace();
				} finally{
					try {
						in.close();
						out.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}).start(); 
		
		
		
	}
	
	
	
	
	
	
}
