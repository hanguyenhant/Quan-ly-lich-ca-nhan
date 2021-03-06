import java.awt.*;
import java.time.LocalDate;
import java.time.LocalTime;

public class CalendarEvent {

    private static final Color DEFAULT_COLOR = Color.PINK;
    private Lich lich = new Lich();
    private LocalDate date;
    private LocalDate end_date;
    private LocalTime start;
    private LocalTime end;
    private String text;
    private Color color;
    private int id;

    public CalendarEvent(int id, Lich lich, LocalDate date, LocalTime start, LocalTime end, String text, LocalDate end_date) {
        this(id, lich, date, start, end, text, DEFAULT_COLOR, end_date);
    }

    public CalendarEvent(int id, Lich lich, LocalDate date, LocalTime start, LocalTime end, String text, Color color, LocalDate end_date) {
    	this.id = id;
    	this.lich = lich;
        this.date = date;
        this.start = start;
        this.end = end;
        this.text = text;
        this.color = color;
        this.end_date = end_date;
    }
    
    public LocalDate getEnd_date() {
    	return end_date;
    }
    
    public void setEnd_date(LocalDate date) {
    	this.end_date = date;
    }

    public int getId() {
    	return id;
    }
    
    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getStart() {
        return start;
    }

    public void setStart(LocalTime start) {
        this.start = start;
    }

    public LocalTime getEnd() {
        return end;
    }

    public void setEnd(LocalTime end) {
        this.end = end;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String toString() {
        return getDate() + " " + getStart() + "-" + getEnd() + ". " + getText();
    }

    public Color getColor() {
        return color;
    }
    
    public Lich getLich() {
    	return lich;
    }
    

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CalendarEvent that = (CalendarEvent) o;

        if (!date.equals(that.date)) return false;
        if (!start.equals(that.start)) return false;
        return end.equals(that.end);

    }

    @Override
    public int hashCode() {
        int result = date.hashCode();
        result = 31 * result + start.hashCode();
        result = 31 * result + end.hashCode();
        return result;
    }
}