import java.util.Date;

public class Lich {
	private String name;
	private String content;
	private String start_time;
	private String end_time;
	private int priority;
	private String status;
	private String prompt;
	private String repeat;
	private String prompt_before;
	private String color;
	private String sound;
	public Lich() {
		
	}
	public Lich(String name, String content, String start_time, String end_time, int priority, String status, String prompt,
			String repeat, String prompt_before, String color, String sound) {
		super();
		this.name = name;
		this.content = content;
		this.start_time = start_time;
		this.end_time = end_time;
		this.priority = priority;
		this.status = status;
		this.prompt = prompt;
		this.repeat = repeat;
		this.prompt_before = prompt_before;
		this.color = color;
		this.sound = sound;
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
	public void setStart_time(String string) {
		this.start_time = string;
	}
	public String getEnd_time() {
		return end_time;
	}
	public void setEnd_time(String end_time) {
		this.end_time = end_time;
	}
	public int getPriority() {
		return priority;
	}
	public void setPriority(int priority) {
		this.priority = priority;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getPrompt() {
		return prompt;
	}
	public void setPrompt(String prompt) {
		this.prompt = prompt;
	}
	public String getRepeat() {
		return repeat;
	}
	public void setRepeat(String repeat) {
		this.repeat = repeat;
	}
	public String getPrompt_before() {
		return prompt_before;
	}
	public void setPrompt_before(String prompt_before) {
		this.prompt_before = prompt_before;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public String getSound() {
		return sound;
	}
	public void setSound(String sound) {
		this.sound = sound;
	}
}
