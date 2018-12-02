
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
	private static Connection connect = null;
	private final static String className="com.mysql.jdbc.Driver";
	private final static String url="jdbc:mysql://localhost:3306/baitaplon";
	
	//Ket noi toi mysql
  	private static void connectMySQL() {
  		
  		try {
  			Class.forName(className);
  			connect=DriverManager.getConnection(url, "root", "123456");
  			System.out.println("Ket noi thanh cong!\n");
  		}catch (SQLException e) {
  			System.out.println("Không tìm thấy class");
  		} catch (ClassNotFoundException e) {
  			System.out.println("Lỗi kết nối");
  		}
  	}
	
	public WeekCalendarTest(ArrayList<CalendarEvent> events) {
		frm = new JFrame();
		WeekCalendar cal = new WeekCalendar(events);


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
        frm.setVisible(true);
        frm.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	}
	
    public static void main(String[] args) throws ParseException {

        ArrayList<CalendarEvent> events = new ArrayList<>();
        connectMySQL();
        try {
			PreparedStatement s = connect.prepareStatement("select start_time, end_time, content, color from lich");
			ResultSet set = s.executeQuery(); //Tra ve cac ban ghi trong mysql
			
			//Lay du lieu cua tung dong, them vao mang de ve
			while (set.next()) {
				Date d = new Date();
				Date d1 = new Date();
				
				//Chuyen doi chuoi thanh Datetime
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				d = format.parse(set.getString(1));
				d1 = format.parse(set.getString(2));
				
				//Lay mau cua lich dat
				String c = set.getString(4).toString();
				Scanner sc = new Scanner(c);
			    sc.useDelimiter("\\D+");
			    Color color = new Color(sc.nextInt(), sc.nextInt(), sc.nextInt());
			 
				//Them vao mang CalendarEvent
				events.add(new CalendarEvent(LocalDate.of(d.getYear() + 1900, d.getMonth() + 1, d.getDate()), LocalTime.of(d.getHours(), d.getMinutes(), d.getSeconds()),
						LocalTime.of(d1.getHours(), d1.getMinutes(), d1.getSeconds()), set.getString(3), color));
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
        		dl.btnOk.addActionListener(new ActionListener() {
        			public void actionPerformed(ActionEvent e1) {
        				Lich lich = new Lich();
        				lich.setName(dl.getName());
        				lich.setContent(dl.getContent());
        				
        				//Chuyen dinh dang cua ngay thang nam va thoi gian bat dau, ket thuc lich
        				SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd");
        				SimpleDateFormat formater1 = new SimpleDateFormat("HH:mm:ss");
        				
        				//Thoi gian bat dau
        				String day = formater.format(dl.getStart_day().getDate());
        				String time1 = formater1.format(dl.getStart_time().getValue());
        				day += " " + time1;
        				lich.setStart_time(day);
        				
        				//Thoi gian ket thuc
        				day = formater.format(dl.getEnd_day().getDate());
        				time1 = formater1.format(dl.getEnd_time().getValue());
        				day += " " + time1;
        				lich.setEnd_time(day);
        				
        				lich.setPriority(dl.getPriority().getSelectedIndex());
        				//Kiem tra che do nhac nho
        				if (dl.getRdbtnYes().isSelected()) {
        					lich.setPrompt("Yes");
        				}
        				else lich.setPrompt("No");
        				
        				//Kiem tra che do Lap lai
        				if (dl.getRdbtnYes1().isSelected()) {
        					lich.setRepeat("Yes");
        				}
        				else lich.setRepeat("No");
        				
        				lich.setPrompt_before(dl.getPrompt_before().getSelectedItem().toString());
        				lich.setSound(dl.getSound());
        				lich.setColor(dl.getColor().getSelectedColor().toString());
        			 
        				
        				//Them thong tin cua lich vao CSDL
        				try {
        					PreparedStatement s = connect.prepareStatement("insert into lich (`name`, `content`, `start_time`, `end_time`, `priority`, `status`, `prompt`, `repeat`, `prompt_before`, `color`, `sound`) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ");
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
        					int res = s.executeUpdate();
        					if (res > 0) {
        						JOptionPane.showMessageDialog(null, "Thêm thông tin thành công");
        						
        						//Lay thong tin ve ngay bat dau, thoi gian bat dau, thoi gian ket thuc, color de ve
        						Date d = dl.getStart_day().getDate();
                				Date d1 = (Date) dl.getStart_time().getValue();
                				Date d2 = (Date) dl.getEnd_time().getValue();
                				
                				String c =lich.getColor();
                				Scanner sc = new Scanner(c);
                			    sc.useDelimiter("\\D+");
                			    Color color = new Color(sc.nextInt(), sc.nextInt(), sc.nextInt());
                			    
                			    try {
									Player player = new Player(new FileInputStream(lich.getSound()));
									player.play();
								} catch (FileNotFoundException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								} catch (JavaLayerException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
                				
                				events.add(new CalendarEvent(LocalDate.of(d.getYear() + 1900, d.getMonth() + 1, d.getDate()), 
                						LocalTime.of(d1.getHours(), d1.getMinutes()), 
                						LocalTime.of(d2.getHours(), d2.getMinutes()), lich.getContent(), color));
                				  
                				frm.setVisible(false);
                				WeekCalendarTest wtest = new WeekCalendarTest(events);
 
        					}
        					else {
        						JOptionPane.showMessageDialog(null, "Lỗi");
        					}
        				} catch (SQLException e2) {
        					// TODO Auto-generated catch block
        					e2.printStackTrace();
        				}
        			
        			}
        		});
        	}
        });  
        
         
    }
}