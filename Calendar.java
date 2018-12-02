import javax.swing.*;
import javax.swing.event.EventListenerList;

//import test.CalendarEvent;

//import CalendarEvent;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.TextStyle;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.Scanner;
public abstract class Calendar extends JComponent {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected static final LocalTime START_TIME = LocalTime.of(0, 0);
    protected static final LocalTime END_TIME = LocalTime.of(23, 59);

    protected static final int MIN_WIDTH = 100;
    protected static final int MIN_HEIGHT = MIN_WIDTH;

    protected static final int HEADER_HEIGHT = 30;
    protected static final int TIME_COL_WIDTH = 100;

    // An estimate of the width of a single character (not exact but good
    // enough)
    private static final int FONT_LETTER_PIXEL_WIDTH = 7;
    private ArrayList<CalendarEvent> events;
    private double timeScale;
    private double dayWidth;
    private Graphics2D g2;

//    private EventListenerList listenerList = new EventListenerList();

    public Calendar() {
        this(new ArrayList<>());
    }

    Calendar(ArrayList<CalendarEvent> events) {
        this.events = events;
        setupEventListeners();
        setupTimer();
    }
    
    private void setupEventListeners() {
        this.addMouseListener(new MouseAdapter() {
            private Connection connect;
			private ConnectMySQL connection;

			@Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
	            //Lay su kien tuong ung
	            CalendarEvent event = checkCalendarEventClick(e.getPoint());
	            PreparedStatement s;
	            connection = new ConnectMySQL();
       		 	connect = ConnectMySQL.connectMySQL();
                //Kiem tra neu la chuot phai duoc click
                if ((e.getModifiers() & InputEvent.BUTTON3_MASK) == InputEvent.BUTTON3_MASK) {
                	 int selection = JOptionPane.showConfirmDialog(null, "Bạn có chắc chắn muốn xóa?", "Xóa lịch", JOptionPane.OK_CANCEL_OPTION);
                	//Xu ly su kien neu dong y xoa
                	//Xoa su kien khoi CSDL
                	 if (selection == JOptionPane.OK_OPTION) {
                		 try {
							s = connect.prepareStatement("delete from lich where id = ?");
							s.setInt(1, event.getId());
							int ret = s.executeUpdate();
							if (ret > 0) {
								JOptionPane.showMessageDialog(null, "Xóa thành công", "Xóa lịch", JOptionPane.INFORMATION_MESSAGE);
								events.remove(event);
							}
							else {
								JOptionPane.showMessageDialog(null, "Lỗi", "Xóa lịch", JOptionPane.INFORMATION_MESSAGE);
							}
                		 } catch (SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
                		 
                	 }
                }
                else {
	                //Kiem tra co nam trong su kien nao khong
	                if (checkCalendarEventClick (e.getPoint()) != null) {
	                	//Tao doi tuong luu du lieu cua su kien event
	                	DatLich datlich = new DatLich();
	                	datlich.setTitle("Sửa lịch");
	                	
	                	//Xet cac gia tri cua su kien vao form datlich
	                	datlich.setName(event.getLich().getName());
	                	datlich.setContent(event.getLich().getContent());
	                	
	                	//Lay mau sac cua su kien
	                	String c =event.getLich().getColor();
	    				Scanner sc = new Scanner(c);
	    			    sc.useDelimiter("\\D+");
	    			    Color color = new Color(sc.nextInt(), sc.nextInt(), sc.nextInt());
	                	datlich.getColor().setSelectedColor(color);
	                	
	                	//Date and time
	                	Date d = new Date();
	                	Date d1 = new Date();
	                	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    				try {
							d = format.parse(event.getLich().getStart_time());
							d1 = format.parse(event.getLich().getEnd_time());
						} catch (ParseException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
	    				datlich.getStart_day().setDate(d);
	    				datlich.getEnd_day().setDate(d1);
	    				datlich.getStart_time().setValue(d);
	    				datlich.getEnd_time().setValue(d1);
	    				
	    				//Chuong bao
	                	datlich.setSound(event.getLich().getSound());
	                	
	                	//Che do nhac truoc cua su kien
	                	if (event.getLich().getPrompt().compareTo("Yes") == 0) {
	                		datlich.getRdbtnYes().setSelected(true);
	                	}
	                	else datlich.getRdbtnNo().setSelected(true);
	                	
	                	//Che do lap lai cua su kien
	                	if (event.getLich().getRepeat().compareTo("Yes") == 0) {
	                		datlich.getRdbtnYes1().setSelected(true);
	                	}
	                	else datlich.getRdbtnNo1().setSelected(true);
	                	
	                	datlich.setVisible(true);
	                	
	                	//Xu ly su kien ok (luu thay doi)
	                	datlich.getOk().addActionListener(new ActionListener() {
	                		public void actionPerformed(ActionEvent e) {
	                			Lich lich = new Lich();
	                			//Ten cong viec
	                			lich.setName(datlich.getName());
	                			
	                			//Noi dung cong viec
	                			lich.setContent(datlich.getContent());
	                			
	                			//Chuyen dinh dang cua ngay thang nam va thoi gian bat dau, ket thuc lich
	                			SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd");
	                			SimpleDateFormat formater1 = new SimpleDateFormat("HH:mm:ss");
	                			
	                			//Thoi gian bat dau
	                			String day = formater.format(datlich.getStart_day().getDate());
	                			String time1 = formater1.format(datlich.getStart_time().getValue());
	                			day += " " + time1;
	                			lich.setStart_time(day);
	                			
	                			//Thoi gian ket thuc
	                			day = formater.format(datlich.getEnd_day().getDate());
	                			time1 = formater1.format(datlich.getEnd_time().getValue());
	                			day += " " + time1;
	                			lich.setEnd_time(day);
	                			
	                			//Do uu tien cua su kien
	                			lich.setPriority((int)datlich.getPriority().getSelectedItem());
	                			
	                			//Kiem tra che do nhac nho
	                			if (datlich.getRdbtnYes().isSelected()) {
	                				lich.setPrompt("Yes");
	                			}
	                			else lich.setPrompt("No");
	                			
	                			//Kiem tra che do Lap lai
	                			if (datlich.getRdbtnYes1().isSelected()) {
	                				lich.setRepeat("Yes");
	                			}
	                			else lich.setRepeat("No");
	                			
	                			//Che do nhac truoc
	                			lich.setPrompt_before(datlich.getPrompt_before().getSelectedItem().toString());
	                			
	                			//Chuong bao
	                			lich.setSound(datlich.getSound());
	                			
	                			//Mau cua su kien
	                			lich.setColor(datlich.getColor().getSelectedColor().toString());
	                			System.out.println(lich.getName());
	                			System.out.println(lich.getContent());
	                			System.out.println(lich.getStart_time());
	                			System.out.println(lich.getEnd_time());
	                			System.out.println(lich.getPriority());
	                			System.out.println(lich.getPrompt());
	                			System.out.println(lich.getRepeat());
	                			System.out.println(lich.getPrompt_before());
	                			System.out.println(lich.getSound());
	                			System.out.println(lich.getColor());
	                			System.out.println(lich.getStatus());
	                			System.out.println(event.getId());
	                			try {
	    							PreparedStatement s = connect.prepareStatement("update lich set name = ?, content = ?, start_time = ?, end_time = ?, priority = ?, status = ?, prompt = ?, isrepeat = ?, prompt_before = ?, color = ?, sound = ? where id = ?");
	    							s.setString(1, lich.getName());
	    							s.setString(2, lich.getContent());
	    							s.setString(3, lich.getStart_time());
	    							s.setString(4, lich.getEnd_time());
	    							s.setInt(5, lich.getPriority());
	    							s.setString(6, "Chưa hoàn thành");
	    							s.setString(7, lich.getPrompt());
	    							s.setString(8, lich.getRepeat());
	    							s.setString(9, lich.getPrompt_before());
	    							s.setString(10, lich.getColor());
	    							s.setString(11, lich.getSound());
	    							s.setInt(12, event.getId());
	    							int res = s.executeUpdate();
	    							
	    							if (res > 0) {
	    								JOptionPane.showMessageDialog(null, "Sửa thông tin thành công");
	    								//Lay thong tin ve ngay bat dau, thoi gian bat dau, thoi gian ket thuc, color de ve
	    								Date d = datlich.getStart_day().getDate();
	    								Date d1 = (Date)datlich.getStart_time().getValue();
	    								Date d2 = (Date)datlich.getEnd_time().getValue();
	    								
	    								//Mau su kien
	    								String c =lich.getColor();
	    								Scanner sc = new Scanner(c);
	    							    sc.useDelimiter("\\D+");
	    							    Color color = new Color(sc.nextInt(), sc.nextInt(), sc.nextInt());
	    								
	    							    CalendarEvent event1 = new CalendarEvent(event.getId(), lich, LocalDate.of(d.getYear() + 1900, d.getMonth() + 1, d.getDate()), 
	    										LocalTime.of(d1.getHours(), d1.getMinutes()), LocalTime.of(d2.getHours(), d2.getMinutes()), lich.getContent(), color);
	    							    events.remove(event);
	    							    events.add(event1);
	    								datlich.setVisible(false);
	    							}
	    	                	} catch (SQLException e1) {
	    							// TODO Auto-generated catch block
	    							e1.printStackTrace();
	    						}
	                		}
	                	});
	                	 
	                }
                }
            }
        });
    }
    private CalendarEvent checkCalendarEventClick(Point p) {
        double x0, x1, y0, y1;
        for (CalendarEvent event : events) {
            if (!dateInRange(event.getDate())) continue;

            x0 = dayToPixel(event.getDate().getDayOfWeek());
            y0 = timeToPixel(event.getStart());
            x1 = dayToPixel(event.getDate().getDayOfWeek()) + dayWidth;
            y1 = timeToPixel(event.getEnd());

            //Neu nam trong hinh chu nhat cua su kien
            if (p.getX() >= x0 && p.getX() <= x1 && p.getY() >= y0 && p.getY() <= y1) {
            	//Xu ly su kien
            	 
                return event;
            }
        }
        return null;
    }
	  
    public static LocalTime roundTime(LocalTime time, int minutes) {
        LocalTime t = time;

        if (t.getMinute() % minutes > minutes / 2) {
            t = t.plusMinutes(minutes - (t.getMinute() % minutes));
        } else if (t.getMinute() % minutes < minutes / 2) {
            t = t.minusMinutes(t.getMinute() % minutes);
        }

        return t;
    }
    
    private void calculateScaleVars() {
        int width = getWidth();
        int height = getHeight();

        if (width < MIN_WIDTH) {
            width = MIN_WIDTH;
        }

        if (height < MIN_HEIGHT) {
            height = MIN_HEIGHT;
        }

        // Units are pixels per second
        timeScale = (double) (height - HEADER_HEIGHT) / (END_TIME.toSecondOfDay() - START_TIME.toSecondOfDay());
        dayWidth = (width - TIME_COL_WIDTH) / numDaysToShow();
        
        //System.out.println(timeScale);

    }
    
    protected abstract int numDaysToShow();

    // Gives x val of left most pixel for day col
    protected abstract double dayToPixel(DayOfWeek dayOfWeek);

    private double timeToPixel(LocalTime time) {
        return ((time.toSecondOfDay() - START_TIME.toSecondOfDay()) * timeScale) + HEADER_HEIGHT; 
    }

    private LocalTime pixelToTime(double y) {
        return LocalTime.ofSecondOfDay((int) ((y - HEADER_HEIGHT) / timeScale) + START_TIME.toSecondOfDay()).truncatedTo(ChronoUnit.MINUTES);
    }

    private DayOfWeek pixelToDay(double x) {
        double pixel;
        DayOfWeek day;
        for (int i = getStartDay().getValue(); i <= getEndDay().getValue(); i++) {
            day = DayOfWeek.of(i);
            pixel = dayToPixel(day);
            if (x >= pixel && x < pixel + dayWidth) {
                return day;
            }
        }
        return null;
    }

    @Override
    protected void paintComponent(Graphics g) {
        calculateScaleVars();
        g2 = (Graphics2D) g;

        // Rendering hints try to turn anti-aliasing on which improves quality
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Set background to white
        g2.setColor(Color.white);
        g2.fillRect(0, 0, getWidth(), getHeight());

        // Set paint colour to black
        g2.setColor(Color.black);

        drawDayHeadings();
        drawTodayShade();
        drawGrid();
        drawTimes();
        drawEvents();
        drawCurrentTimeLine();
    }

    protected abstract DayOfWeek getStartDay();

    protected abstract DayOfWeek getEndDay();
    
    protected abstract LocalDate getDateFromDay(DayOfWeek day);

    private void drawDayHeadings() {
        int y = 20;
        int x;
        LocalDate day;
        DayOfWeek dayOfWeek;

        for (int i = getStartDay().getValue(); i <= getEndDay().getValue(); i++) {
            dayOfWeek = DayOfWeek.of(i);
            day = getDateFromDay(dayOfWeek);          

            String text = dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.ENGLISH) + " " + day.getDayOfMonth() + "/" + day.getMonthValue();
            x = (int) (dayToPixel(DayOfWeek.of(i)) + (dayWidth / 2) - (FONT_LETTER_PIXEL_WIDTH * text.length() / 2));
            
            LocalDate today = LocalDate.now();

            final Color origColor = g2.getColor();
            Color orange = new Color(255, 165, 0);
            Color green = new Color(154,205,50);
            Color textColor = new Color(255, 255, 255);
            
            // Check that date range being viewed is current date range
            if (day.compareTo(today)==0)           
            	g2.setColor(orange);           
            else
            	g2.setColor(green);     

            FontMetrics fm = g2.getFontMetrics();
            Rectangle2D rect = fm.getStringBounds(text, g2);
            
            int x1 = (int) (dayToPixel(DayOfWeek.of(i)));
            int y1 = 0;
            g2.fillRect(x1,
                  y1- fm.getAscent(),
                  (int) (dayToPixel(getEndDay()) + dayWidth),
                 (int) timeToPixel(START_TIME)+14);

            g2.setColor(textColor);
            g2.drawString(text, x, y);
                       
            g2.setColor(origColor);
            
        }
    }

    private void drawGrid() {
        // Save the original colour
        final Color ORIG_COLOUR = g2.getColor();

        // Set colour to grey with half alpha (opacity)
        Color alphaGray = new Color(128, 128, 128, 128);
        Color alphaGrayLighter = new Color(200, 200, 200, 128);
        Color white = new Color(255, 255, 255);
        
        //g2.setColor(white);

        // Draw vertical grid lines
        double x;
        for (int i = getStartDay().getValue(); i <= getEndDay().getValue(); i++) {
            x = dayToPixel(DayOfWeek.of(i));
            //System.out.println(x);
            g2.setColor(white);
            g2.draw(new Line2D.Double(x, 0, x, HEADER_HEIGHT));
            g2.setColor(alphaGray);
            g2.draw(new Line2D.Double(x, HEADER_HEIGHT , x, timeToPixel(END_TIME)));
        }

        // Draw horizontal grid lines
        double y;
        int x1;
        
        LocalTime time = LocalTime.of(23, 59);
        LocalTime t;
        
        if (END_TIME.compareTo(time) == 0)
        {
        	LocalDate date = LocalDate.of(2014, 1, 1); // arbitrary date
    		LocalDateTime tsp = LocalDateTime.of(date, LocalTime.MIDNIGHT);
    		//LocalTime t;

    		do {
    			  t = tsp.toLocalTime() ;
    		      //System.out.println(t);
    		    
    		      y = timeToPixel(t);
              
    	          //System.out.println(y);
    	          
    	          if (t.getMinute() == 0) {
    	              g2.setColor(alphaGray);
    	              x1 = 0;
    	          } else {
    	              g2.setColor(alphaGrayLighter);
    	              x1 = TIME_COL_WIDTH;
    	          }
    	          g2.draw(new Line2D.Double(x1, y, dayToPixel(getEndDay()) + dayWidth, y));
    		    
    		    tsp = tsp.plus(Duration.ofHours(1).plusMinutes(0));
    		} while (date.equals(tsp.toLocalDate()));
        }
        else
        {
        	for (time = START_TIME; time.compareTo(END_TIME) <= 0; time = time.plusMinutes(60)) {
                y = timeToPixel(time);
                if (time.getMinute() == 0) {
                    g2.setColor(alphaGray);
                    x1 = 0;
                } else {
                    g2.setColor(alphaGrayLighter);
                    x1 = TIME_COL_WIDTH;
                }
                g2.draw(new Line2D.Double(x1, y, dayToPixel(getEndDay()) + dayWidth, y));
            }
        }
        
        

        // Reset the graphics context's colour
        g2.setColor(ORIG_COLOUR);
    }
    
    protected abstract boolean dateInRange(LocalDate date);

    private void drawTodayShade() {
        LocalDate today = LocalDate.now();

        // Check that date range being viewed is current date range
        if (!dateInRange(today)) return;

        final double x = dayToPixel(today.getDayOfWeek());
        final double y = timeToPixel(START_TIME);
        final double width = dayWidth;
        final double height = timeToPixel(END_TIME) - timeToPixel(START_TIME);

        final Color origColor = g2.getColor();
        Color alphaGray = new Color(200, 200, 200, 64);
        g2.setColor(alphaGray);
        g2.fill(new Rectangle2D.Double(x, y, width, height));             
        g2.setColor(origColor);
    }
    
    private void drawCurrentTimeLine() {
        LocalDate today = LocalDate.now();

        // Check that date range being viewed is current date range
        if (!dateInRange(today)) return;

        final double x0 = dayToPixel(today.getDayOfWeek());
        final double x1 = dayToPixel(today.getDayOfWeek()) + dayWidth;
        final double y = timeToPixel(LocalTime.now());

        final Color origColor = g2.getColor();
        final Stroke origStroke = g2.getStroke();

        g2.setColor(new Color(255, 127, 110));
        g2.setStroke(new BasicStroke(2));
        g2.draw(new Line2D.Double(x0, y, x1, y));

        g2.setColor(origColor);
        g2.setStroke(origStroke);
    }

    private void drawTimes() {
        int y;
        
        LocalTime time = LocalTime.of(23, 59);
        LocalTime t;
        
        if (END_TIME.compareTo(time) == 0)
        {
        	LocalDate date = LocalDate.of(2014, 1, 1); // arbitrary date
    		LocalDateTime tsp = LocalDateTime.of(date, LocalTime.MIDNIGHT);
    		

    		do {
    			t = tsp.toLocalTime() ;
    		    //System.out.println(t);
    		    
    		    y = (int) timeToPixel(t) + 15;
              
              g2.drawString(t.toString(), TIME_COL_WIDTH - (FONT_LETTER_PIXEL_WIDTH * t.toString().length()) -5, y);
    		    
    		    tsp = tsp.plus(Duration.ofHours(1).plusMinutes(0));
    		} while (date.equals(tsp.toLocalDate()));
        }
        else
        {
        	for (time = START_TIME; time.compareTo(END_TIME) < 0; time = time.plusHours(1)) {
                y = (int) timeToPixel(time) + 15;
                g2.drawString(time.toString(), TIME_COL_WIDTH - (FONT_LETTER_PIXEL_WIDTH * time.toString().length()) - 5, y);
            }
        }
        
        
        
    }

    private void drawEvents() {
        double x;
        double y0;

        for (CalendarEvent event : events) {
            if (!dateInRange(event.getDate())) continue;

            x = dayToPixel(event.getDate().getDayOfWeek());
            y0 = timeToPixel(event.getStart());

            Rectangle2D rect = new Rectangle2D.Double(x, y0, dayWidth, (timeToPixel(event.getEnd()) - timeToPixel(event.getStart())));
            Color origColor = g2.getColor();
            g2.setColor(event.getColor());
            g2.fill(rect);
            g2.setColor(origColor);

            // Draw time header

            // Store the current font state
            Font origFont = g2.getFont();

            final float fontSize = origFont.getSize() - 1.6F;

            // Create a new font with same properties but bold
            Font newFont = origFont.deriveFont(Font.BOLD, fontSize);
            g2.setFont(newFont);

            g2.drawString(event.getStart() + " - " + event.getEnd(), (int) x + 5, (int) y0 + 11);

            // Unbolden
            g2.setFont(origFont.deriveFont(fontSize));

            // Draw the event's text
            g2.drawString(event.getText(), (int) x + 5, (int) y0 + 23);

            // Reset font
            g2.setFont(origFont);
        }
    }

    protected double getDayWidth() {
        return dayWidth;
    }

    // Repaints every minute to update the current time line
    private void setupTimer() {
        Timer timer = new Timer(1000*60, e -> repaint());
        timer.start();
    }
    public void addEvent(CalendarEvent event) {
        events.add(event);
        repaint();
    }
    protected abstract void setRangeToToday();

    public void goToToday() {
        setRangeToToday();
        repaint();
    }
}
