
import javax.swing.*;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

public class WeekCalendarTest {
	private static JButton datLich = new JButton("New");
	private static JFrame frm;
	private static WeekCalendar cal;
	
	public WeekCalendarTest(ArrayList<CalendarEvent> events) {
		frm = new JFrame();
		cal = new WeekCalendar(events);


        JButton goToTodayBtn = new JButton("Today");
        goToTodayBtn.addActionListener(e -> cal.goToToday());

        JButton nextWeekBtn = new JButton(">");
        nextWeekBtn.addActionListener(e -> cal.nextWeek());

        JButton prevWeekBtn = new JButton("<");
        prevWeekBtn.addActionListener(e -> cal.prevWeek());

        JButton nextMonthBtn = new JButton(">>");
        nextMonthBtn.addActionListener(e -> cal.nextMonth());

        JButton prevMonthBtn = new JButton("<<");
        prevMonthBtn.addActionListener(e -> cal.prevMonth());
        
        JPanel weekControls = new JPanel();
        weekControls.add(prevMonthBtn);
        weekControls.add(prevWeekBtn);
        weekControls.add(goToTodayBtn);
        weekControls.add(nextWeekBtn);
        weekControls.add(nextMonthBtn);
        weekControls.add(datLich);
        
        frm.add(weekControls, BorderLayout.NORTH);

        JPanel Controls = new JPanel();
        Controls.add(datLich);
        frm.add(Controls, BorderLayout.SOUTH);
        
        frm.add(cal, BorderLayout.CENTER);
        frm.setSize(1000, 900);
        frm.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frm.setVisible(true);
        frm.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	}
	
    public static void main(String[] args) throws ParseException {

        ArrayList<CalendarEvent> events = new ArrayList<>();
        ConnectMySQL connection = new ConnectMySQL();
        Connection connect = connection.connectMySQL();
        try {
			PreparedStatement s = connect.prepareStatement("select * from lich");
			ResultSet set = s.executeQuery(); //Tra ve cac ban ghi trong mysql
			//Lay du lieu cua tung dong, them vao mang de ve
			while (set.next()) {
				Lich lich = new Lich();
				lich.setName(set.getString(2));
				lich.setContent(set.getString(3));
				lich.setStart_time(set.getString(4));
				lich.setEnd_time(set.getString(5));
				lich.setPriority(Integer.parseInt(set.getString(6)));
				lich.setStatus(set.getString(7));
				lich.setPrompt(set.getString(8));
				lich.setRepeat(set.getString(9));
				lich.setPrompt_before(set.getString(10));
				lich.setColor(set.getString(11));
				lich.setSound(set.getString(11));
			 
				
				Date d = new Date();
				Date d1 = new Date();
				
				//Chuyen doi chuoi thanh Datetime
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				d = format.parse(lich.getStart_time());
				d1 = format.parse(lich.getEnd_time());
				
				//Lay mau cua lich dat
				String c = lich.getColor();
				Scanner sc = new Scanner(c);
			    sc.useDelimiter("\\D+");
			    Color color = new Color(sc.nextInt(), sc.nextInt(), sc.nextInt());
			 
				//Them vao mang CalendarEvent
				events.add(new CalendarEvent(Integer.parseInt(set.getString(1)), lich, LocalDate.of(d.getYear() + 1900, d.getMonth() + 1, d.getDate()), LocalTime.of(d.getHours(), d.getMinutes(), d.getSeconds()),
						LocalTime.of(d1.getHours(), d1.getMinutes(), d1.getSeconds()), lich.getContent(), color, LocalDate.of(d1.getYear() + 1900, d1.getMonth() + 1, d1.getDate())));
			}
		} catch (SQLException e3) {
			// TODO Auto-generated catch block
			e3.printStackTrace();
		}
 
        WeekCalendarTest test = new WeekCalendarTest(events);
 
        datLich.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		DatLich dl = new DatLich();
        		dl.setVisible(true);
        		dl.getOk().addActionListener(new ActionListener() {
        			public void actionPerformed(ActionEvent e1) {
        				dl.getXuLyOk(e1, null);
        				CalendarEvent event = dl.getEvent();
//        				Date start_date = new Date();
//        				Date end_date = new Date();
//        				start_date.setDate(event.getDate().getDayOfMonth());
//        				start_date.setMonth(event.getDate().getMonthValue());
//        				start_date.setYear(event.getDate().getYear());
//        				end_date.setDate(event.getEnd_date().getDayOfMonth());
//        				end_date.setMonth(event.getEnd_date().getMonthValue());
//        				end_date.setYear(event.getEnd_date().getYear());
//        				
//        				//Tinh khoang cach giua 2 thoi gian (bat dau va ket thuc dat lich)
//        				int time = (int) ((end_date.getTime() - start_date.getTime())/1000/60/60/24);
        				
        				//Ve su kien 
        				dl.paintEvent(cal, event);
        				 
        			}
        			
        		});
        	}
        });  
        
         
    }
    public WeekCalendar getCal() {
    	return cal;
    }
}