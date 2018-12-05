import java.util.*;

import javax.swing.JOptionPane;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.text.*;
 
public class Reminder {		
	DateFormat df;
	Timer timer;	
	String name;
	String content;
	String start_time;
	String reminder;
	String sound;
	
	public Reminder(String name, String content, String start_time, String reminder, String sound) throws ParseException
	{
		df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		timer = new Timer();
		this.name = name;
		this.content = content;
		this.start_time = start_time;
		this.reminder = reminder;
		this.sound = sound;
		Date date = df.parse(this.reminder);
		
		timer.schedule(new MyTimeTask(), date);
	}
 
	private class MyTimeTask extends TimerTask {
		public void run() {
			
			MyPlayer myplayer = new MyPlayer(sound, true);
			try {
				myplayer.play();
			} catch (FileNotFoundException | JavaLayerException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			String[] buttons = {"Bao lai", "Bo qua"};
			int ret = JOptionPane.showOptionDialog(null, content + "\nBat dau luc: "+ start_time, 
					        name, 0, JOptionPane.INFORMATION_MESSAGE, null, buttons, buttons[0]);		
			
			if (ret == 0)
			{
				myplayer.stop();
				JOptionPane.showMessageDialog(null, "Bao lai sau 10 phut", "Thong bao", 
														JOptionPane.INFORMATION_MESSAGE);
				timer = new Timer();
				timer.schedule(new MyTimeTask(), 10*1000*60);
			} 
			if (ret == 1)
			{
				myplayer.stop();
				timer.cancel();
			}
      }
   }
}