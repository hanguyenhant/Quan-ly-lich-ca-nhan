import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

public class MyPlayer {
	private Player player;
	private FileInputStream FIS;
	private BufferedInputStream BIS;
	private String path;
	private static boolean loop;
	
	static MyPlayer myplayer;
	
	public MyPlayer(String path, boolean loop)
	{
		this.path = path;
		MyPlayer.loop = loop;
	}
	
	public boolean setLoop(boolean loop)
	{
		return MyPlayer.loop = loop;
	}
	
	public void stop()
	{
	    try{
	    	player.close();
	    	FIS = null;
	    	BIS = null;
	    	player = null;
	    	loop = false;	    	    	
	    }catch(Exception e){}
	}
	
	public void play() throws FileNotFoundException, JavaLayerException
	{
		
		Runnable thread = new Runnable() 
			{
				@Override
				public void run() {
					// TODO Auto-generated method stub
					while(loop==true) {
						try {
							FIS = new FileInputStream(path);
							BIS = new BufferedInputStream(FIS);
							player = new Player(BIS);
							player.play();
						} catch (JavaLayerException e) {
							// TODO Auto-generated catch block							
							e.printStackTrace();
						} catch (FileNotFoundException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}	
					}
														
				}
			};
		Thread mythread = new Thread(thread);
		mythread.start();		
	}	
}
