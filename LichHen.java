import java.text.ParseException;

public class LichHen {
	private String name;
	private String content;
	private String start_time;
	private String reminder;
	private String sound;
	Reminder myreminder;
	
	public Reminder getMyreminder() throws ParseException
	{
		return new Reminder(this.name, this.content, this.start_time, this.reminder, this.sound);
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getStart_time() {
		return start_time;
	}
	public void setStart_time(String start_time) {
		this.start_time = start_time;
	}
	public String getReminder() {
		return reminder;
	}
	public void setReminder(String reminder) {
		this.reminder = reminder;
	}

	public String getSound() {
		return sound;
	}

	public void setSound(String sound) {
		this.sound = sound;
	}


	
}
