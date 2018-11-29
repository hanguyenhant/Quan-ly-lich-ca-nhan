import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class test {
	
	protected static final LocalTime START_TIME = LocalTime.of(0, 0);
    protected static final LocalTime END_TIME = LocalTime.of(23, 59);

    protected static final int MIN_WIDTH = 100;
    protected static final int MIN_HEIGHT = MIN_WIDTH;

    protected static final int HEADER_HEIGHT = 30;
    protected static final int TIME_COL_WIDTH = 100;
    
    private static double timeScale;
    private double dayWidth;
    
    static int height = 900;
    
    private static double timeToPixel(LocalTime time) {
        return ((time.toSecondOfDay() - START_TIME.toSecondOfDay()) * timeScale) + HEADER_HEIGHT; 
    }

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		
//		timeScale = (double) (height - HEADER_HEIGHT) / (END_TIME.toSecondOfDay() - START_TIME.toSecondOfDay());
//        
//		double y = timeToPixel(END_TIME);
//		
//		System.out.println(y);
		
		LocalDate date = LocalDate.of(2014, 1, 1); // arbitrary date
		LocalDateTime tsp = LocalDateTime.of(date, LocalTime.MIDNIGHT);
		LocalTime t;

		do {
			t = tsp.toLocalTime() ;
		    System.out.println(t);
		    tsp = tsp.plus(Duration.ofHours(1).plusMinutes(0));
		} while (date.equals(tsp.toLocalDate()));
		
	}

}
