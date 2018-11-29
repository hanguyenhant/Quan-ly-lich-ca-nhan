//import Calendar;
////import test.CalendarEvent;
//import WeekCalendar;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public class WeekCalendarTest {
    public static void main(String[] args) {
        JFrame frm = new JFrame();

        ArrayList<CalendarEvent> events = new ArrayList<>();
        events.add(new CalendarEvent(LocalDate.of(2018, 11, 28), LocalTime.of(9, 0), LocalTime.of(9, 20), "Test 28/11 9:00-9:20"));
        events.add(new CalendarEvent(LocalDate.of(2018, 11, 28), LocalTime.of(14, 15), LocalTime.of(15, 00), "Test 28/11 14:15-15:00"));
        events.add(new CalendarEvent(LocalDate.of(2018, 11, 29), LocalTime.of(12, 0), LocalTime.of(13, 20), "Test 29/11 12:00-13:20"));
        events.add(new CalendarEvent(LocalDate.of(2016, 11, 16), LocalTime.of(9, 0), LocalTime.of(9, 20), "Test 29/11 9:00-9:20"));

        WeekCalendar cal = new WeekCalendar(events);

//        cal.addCalendarEventClickListener(e -> System.out.println(e.getCalendarEvent()));
//        cal.addCalendarEmptyClickListener(e -> {
//            System.out.println(e.getDateTime());
//            System.out.println(Calendar.roundTime(e.getDateTime().toLocalTime(), 30));
//        });

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
        
        JButton datLich = new JButton("New");
        datLich.addActionListener(e -> cal.datLich());

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
}