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

public class CalendarTest {
	private static JButton datLich = new JButton("New");
	private static JFrame frm;
	private static WeekCalendar weekcal;
	private static DayCalendar daycal;
	private static JComboBox chooser;
	
	public CalendarTest(ArrayList<CalendarEvent> events) {
		frm = new JFrame();
		weekcal = new WeekCalendar(events);
		daycal = new DayCalendar(events);
		
		JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

		//Mac dinh hien thi la week
		JButton goToTodayBtn = new JButton("Today");
        goToTodayBtn.addActionListener(e -> weekcal.goToToday());

        JButton nextWeekBtn = new JButton(">");
        nextWeekBtn.addActionListener(e -> weekcal.nextWeek());

        JButton prevWeekBtn = new JButton("<");
        prevWeekBtn.addActionListener(e -> weekcal.prevWeek());

        JButton nextMonthBtn = new JButton(">>");
        nextMonthBtn.addActionListener(e -> weekcal.nextMonth());

        JButton prevMonthBtn = new JButton("<<");
        prevMonthBtn.addActionListener(e -> weekcal.prevMonth());
        
        JPanel weekControls = new JPanel();
        weekControls.add(prevMonthBtn);
        weekControls.add(prevWeekBtn);
        weekControls.add(goToTodayBtn);
        weekControls.add(nextWeekBtn);
        weekControls.add(nextMonthBtn);
        
        panel.add(weekControls, BorderLayout.CENTER);
        		              
      //Mac dinh hien thi theo week
        frm.add(weekcal, BorderLayout.CENTER);
        JPanel Controls = new JPanel();
        Controls.add(datLich);
        frm.add(Controls, BorderLayout.SOUTH);
        
        //Neu chon day or week       
        String[] array = {"Day", "Week"};
        chooser = new JComboBox(array);
        chooser.setSelectedItem("Week");
        
        frm.add(panel, BorderLayout.NORTH);
        panel.add(chooser, BorderLayout.EAST);
        frm.setVisible(true);
        
        chooser.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                Object selectedValue = chooser.getSelectedItem();
                // carry on with what ever you need
                if (selectedValue.toString().equals("Day"))
                {
                	//Xoa di ve lai frame
                	  frm.getContentPane().removeAll();
                	  frm.repaint();
                	  
                	  panel.removeAll();
                	  panel.revalidate();
                	  panel.repaint();
                	  
                	  JButton goToTodayBtn = new JButton("Today");
		              goToTodayBtn.addActionListener(e -> daycal.goToToday());

		              JButton nextDayBtn = new JButton(">");
		              nextDayBtn.addActionListener(e -> daycal.nextDay());

		              JButton prevDayBtn = new JButton("<");
		              prevDayBtn.addActionListener(e -> daycal.prevDay());

		              JPanel dayControls = new JPanel();
		              dayControls.add(prevDayBtn);
		              dayControls.add(goToTodayBtn);
		              dayControls.add(nextDayBtn);		
		              
		              panel.add(dayControls, BorderLayout.CENTER);
		              panel.add(chooser, BorderLayout.EAST);
		              
		              frm.add(panel, BorderLayout.NORTH);
		              
		              //Neu la day thi hien thi day
		              frm.add(daycal, BorderLayout.CENTER);
		              
		              JPanel Controls = new JPanel();
		              Controls.add(datLich);
		              frm.add(Controls, BorderLayout.SOUTH);
		              
		              frm.setVisible(true);
		            
                }
                else
                {
                	//Xoa di ve lai frame
              	  	  frm.getContentPane().removeAll();
              	      frm.repaint();
              	                   
              	      panel.removeAll();
              	      panel.revalidate();
              	      panel.repaint();
              	  
                	  JButton goToTodayBtn = new JButton("Today");
		              goToTodayBtn.addActionListener(e -> weekcal.goToToday());

		              JButton nextWeekBtn = new JButton(">");
		              nextWeekBtn.addActionListener(e -> weekcal.nextWeek());

		              JButton prevWeekBtn = new JButton("<");
		              prevWeekBtn.addActionListener(e -> weekcal.prevWeek());

		              JButton nextMonthBtn = new JButton(">>");
		              nextMonthBtn.addActionListener(e -> weekcal.nextMonth());

		              JButton prevMonthBtn = new JButton("<<");
		              prevMonthBtn.addActionListener(e -> weekcal.prevMonth());
		              
		              JPanel weekControls = new JPanel();
		              weekControls.add(prevMonthBtn);
		              weekControls.add(prevWeekBtn);
		              weekControls.add(goToTodayBtn);
		              weekControls.add(nextWeekBtn);
		              weekControls.add(nextMonthBtn);
		              
		              panel.add(weekControls, BorderLayout.CENTER);
		              panel.add(chooser, BorderLayout.EAST);
		              
		              frm.add(panel, BorderLayout.NORTH);
		              
		            //Neu la day thi hien thi day

		              frm.add(weekcal, BorderLayout.CENTER);
		              
		              JPanel Controls = new JPanel();
		              Controls.add(datLich);
		              frm.add(Controls, BorderLayout.SOUTH);
		              
		              frm.setVisible(true);
                }
            }
        });
       
        
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
				lich.setStatus(set.getString(6));
				lich.setPrompt(set.getString(7));
				lich.setPrompt_before(set.getString(8));
				lich.setColor(set.getString(9));
				lich.setSound(set.getString(10));
			 
				
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
 
        CalendarTest test = new CalendarTest(events);
        new NhacLich();
 
        datLich.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		DatLich dl = new DatLich();
        		dl.setVisible(true);
        		dl.getOk().addActionListener(new ActionListener() {
        			public void actionPerformed(ActionEvent e1) {
        				dl.getXuLyOk(e1, null);
        				CalendarEvent event = dl.getEvent();
        				
        				//Ve su kien 
        				if (chooser.getSelectedItem().toString().equals("Week"))
        					dl.paintEvent(weekcal, event);
        				
        			}
        			
        		});
        	}
        });  
        
         
    }
    public WeekCalendar getWeekCal() {
    	return weekcal;
    }
    
    public DayCalendar getDayCal()
    {
    	return daycal;
    }
}