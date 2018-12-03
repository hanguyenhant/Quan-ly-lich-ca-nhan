import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Event;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.DateFormatter;
import javax.swing.JTextField;
import javax.swing.SpinnerDateModel;
import javax.swing.JTextArea;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JRadioButton;
import javax.swing.JSpinner;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import org.jdesktop.swingx.JXDatePicker;

import javafx.scene.web.PromptData;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Calendar;
import java.util.Date;
import java.util.EventObject;
import java.util.Scanner;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DatLich extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ButtonGroup buttonGroup;
	private ButtonGroup buttonGroup1;
	private JPanel contentPane;
	private JTextField name;
	private JTextField sound;
	private JTextArea content;
	private JXDatePicker start_day;
	private JXDatePicker end_day;
	private JSpinner start_time;
	private JSpinner end_time;
	private JRadioButton rdbtnYes;
	private JRadioButton rdbtnYes1;
	private JRadioButton rdbtnNo;
	private JRadioButton rdbtnNo1;
	private JComboBox prompt_before;
	private JComboBox<Integer> priority;
	private ColorChooserButton color;
	private JTextField textField_2;
	private JTextField textField_3;
	private JTextField textField_4;
	private JTextField textField_5;
	private Lich lich;
	private JButton btnOk;
	private CalendarEvent  event;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					DatLich frame = new DatLich();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public DatLich() {
		setBounds(100, 100, 500, 514);
		setTitle("Đặt lịch");
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "Description", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBounds(10, 11, 464, 234);
		
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel lblNiDungChi = new JLabel("Nội dung chi tiết");
		lblNiDungChi.setBounds(10, 61, 97, 14);
		panel.add(lblNiDungChi);
		
		JLabel lblTnCngVic = new JLabel("Tên công việc");
		lblTnCngVic.setBounds(10, 24, 97, 14);
		panel.add(lblTnCngVic);
		
		name = new JTextField();
		name.setBounds(148, 21, 292, 20);
		panel.add(name);
		name.setColumns(10);
		
		content = new JTextArea();
		content.setBounds(148, 56, 292, 84);
		panel.add(content);
		
		JLabel lblThiGianBt = new JLabel("Thời gian bắt đầu");
		lblThiGianBt.setBounds(10, 164, 107, 14);
		panel.add(lblThiGianBt);
		
		JLabel lblThiGianKt = new JLabel("Thời gian kết thúc");
		lblThiGianKt.setBounds(10, 195, 107, 14);
		panel.add(lblThiGianKt);
		
//		Chon ngay bat dau
		start_day = new JXDatePicker();
		start_day.setDate(Calendar.getInstance().getTime());
		start_day.setFormats(new SimpleDateFormat("dd-MM-yyyy"));
		start_day.setBounds(148, 161, 110, 20);
        panel.add(start_day);

		
//		Chon gio bat dau
        Calendar time = Calendar.getInstance();
        time.set(Calendar.HOUR_OF_DAY, 24); // 24 == 12 PM == 00:00:00
        time.set(Calendar.MINUTE, 0);
        time.set(Calendar.SECOND, 0);

        SpinnerDateModel model_1 = new SpinnerDateModel();
        model_1.setValue(time.getTime());

        start_time = new JSpinner(model_1);

        JSpinner.DateEditor editor_1 = new JSpinner.DateEditor(start_time, "HH:mm:ss");
        DateFormatter formatter_1 = (DateFormatter)editor_1.getTextField().getFormatter();
        formatter_1.setAllowsInvalid(false); // this makes what you want
        formatter_1.setOverwriteMode(true);

        start_time.setEditor(editor_1);
        start_time.setBounds(307, 161, 86, 20);
		panel.add(start_time);      
		
//        Chon ngay ket thuc
		end_day = new JXDatePicker();
		end_day.setDate(Calendar.getInstance().getTime());
		end_day.setFormats(new SimpleDateFormat("dd-MM-yyyy"));
		end_day.setBounds(148, 192, 110, 20);
        panel.add(end_day);
		
//		Chon gio ket thuc
        
        SpinnerDateModel model_2 = new SpinnerDateModel();
        model_2.setValue(time.getTime());

        end_time = new JSpinner(model_2);
        
        JSpinner.DateEditor editor_2 = new JSpinner.DateEditor(end_time, "HH:mm:ss");
        DateFormatter formatter_2 = (DateFormatter)editor_2.getTextField().getFormatter();
        formatter_2.setAllowsInvalid(false); // this makes what you want
        formatter_2.setOverwriteMode(true);

        end_time.setEditor(editor_2);
        end_time.setBounds(307, 192, 86, 20);
		panel.add(end_time); 
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(null, "Option", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_1.setBounds(10, 256, 464, 187);
		contentPane.add(panel_1);
		panel_1.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Độ ưu tiên");
		lblNewLabel.setBounds(10, 26, 72, 14);
		panel_1.add(lblNewLabel);
		
		priority = new JComboBox<Integer>();
		priority.setBounds(147, 23, 52, 20);
		priority.addItem(1);
		priority.addItem(2);
		priority.addItem(3);
		priority.addItem(4);
		priority.addItem(5);
	//	priority.setSelectedIndex(0);
		
		panel_1.add(priority);
		
		JLabel lblNhcNh = new JLabel("Nhắc nhở");
		lblNhcNh.setBounds(262, 26, 75, 14);
		panel_1.add(lblNhcNh);
		
		//Nhac nho chon Yes or No
		//Cho vao button group. Chon Yes thi khong No va nguoc lai.
		
		buttonGroup = new ButtonGroup();

		rdbtnYes = new JRadioButton("Yes");
		rdbtnYes.setBounds(343, 22, 52, 23);
		rdbtnYes.setSelected(true);
		buttonGroup.add(rdbtnYes);
		panel_1.add(rdbtnYes);
		
		rdbtnNo = new JRadioButton("No");
		rdbtnNo.setBounds(397, 22, 61, 23);
		buttonGroup.add(rdbtnNo);		
		panel_1.add(rdbtnNo);	
		
		JLabel lblThiGianNhc = new JLabel("Thời gian nhắc trước");
		lblThiGianNhc.setBounds(10, 61, 135, 14);
		panel_1.add(lblThiGianNhc);
		
		JLabel lblLpLi = new JLabel("Lặp lại");
		lblLpLi.setBounds(262, 61, 46, 14);
		panel_1.add(lblLpLi);
		
		buttonGroup1 = new ButtonGroup();

		rdbtnYes1 = new JRadioButton("Yes");
		rdbtnYes1.setBounds(343, 55, 52, 30);
		rdbtnYes1.setSelected(true);
		buttonGroup1.add(rdbtnYes1);
		panel_1.add(rdbtnYes1);
		
		rdbtnNo1 = new JRadioButton("No");
		rdbtnNo1.setBounds(397, 55, 52, 30);
		buttonGroup1.add(rdbtnNo1);		
		panel_1.add(rdbtnNo1);	
		
		prompt_before = new JComboBox();
		prompt_before.setBounds(147, 58, 89, 20);
		prompt_before.addItem("Không");
		prompt_before.addItem("5 minute");
		prompt_before.addItem("10 minute");
		prompt_before.addItem("15 minute");
		
		panel_1.add(prompt_before);
		
		JLabel lblmBo = new JLabel("Âm báo");
		lblmBo.setBounds(10, 101, 46, 14);
		panel_1.add(lblmBo);
		
		sound = new JTextField();
		sound.setBounds(147, 98, 189, 20);
		panel_1.add(sound);
		sound.setColumns(10);
		
		//Chon file am thanh bao 
		JButton btnBrowse = new JButton("Browse");
		btnBrowse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
	            JFileChooser fileChooser = new JFileChooser();
	            
	            FileNameExtensionFilter filter = new FileNameExtensionFilter("Audio", "mp3");
	            fileChooser.setFileFilter(filter);
	    		fileChooser.addChoosableFileFilter(filter);
	    		
	            fileChooser.setControlButtonsAreShown(true);
	            int choose = fileChooser.showOpenDialog(null);
	            //Neu chon Open thi lay duong dan cua file day vao textFiled_1
	            if (choose == fileChooser.APPROVE_OPTION) {
	            	File f = fileChooser.getSelectedFile();
	            	String path = f.getPath();
	            	sound.setText(path);
//	            	try {
//						Player player = new Player(new FileInputStream(sound.getText()));
//						player.play();
//					} catch (FileNotFoundException e1) {
//						// TODO Auto-generated catch block
//						e1.printStackTrace();
//					} catch (JavaLayerException e1) {
//						// TODO Auto-generated catch block
//						e1.printStackTrace();
//					}
	            }
			}
		});
		btnBrowse.setBounds(346, 97, 89, 23);
		panel_1.add(btnBrowse);
		
		JLabel lblMu = new JLabel("Màu");
		lblMu.setBounds(10, 140, 46, 14);
		panel_1.add(lblMu);
		
		color = new ColorChooserButton(Color.PINK);
		color.setBounds(147, 136, 89, 23);
		panel_1.add(color);
		
		btnOk = new JButton("OK");
		btnOk.setBounds(258, 447, 89, 23);
		contentPane.add(btnOk);
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.setBounds(137, 447, 89, 23);
		contentPane.add(btnCancel);
		
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
			}
		});
	}
	public JButton getOk() {
		return btnOk;
	}
	
	public String getName() {
		return name.getText();
	}
	 
	public String getSound() {
		return sound.getText();
	}
	
	public String getContent() {
		return content.getText();
	}
	public void setName(String n) {
		name.setText(n);
	}
	 
	public void setSound(String s) {
		sound.setText(s);
	}
	
	public void setContent(String c) {
		content.setText(c);
	}
	
	public JXDatePicker getStart_day() {
		return start_day;
	}
	
	public JXDatePicker getEnd_day() {
		return end_day;
	}
	
	public JSpinner getStart_time() {
		return start_time;
	}
	
	public JSpinner getEnd_time() {
		return end_time;
	}
	
	public JRadioButton getRdbtnYes() {
		return rdbtnYes;
	}
	
	public JRadioButton getRdbtnNo() {
		return rdbtnNo;
	}
	
	public JRadioButton getRdbtnYes1() {
		return rdbtnYes1;
	}
	
	public JRadioButton getRdbtnNo1() {
		return rdbtnNo1;
	}
	
	public JComboBox getPrompt_before() {
		return prompt_before;
	}
	
	public JComboBox getPriority() {
		return priority;
	}
	
	public ColorChooserButton getColor() {
		return color;
	}
	
	public ButtonGroup getPromptGroup() {
		return buttonGroup;
	}
	
	public ButtonGroup getRepeatGroup() {
		return buttonGroup1;
	}
	
	public CalendarEvent getEvent() {
		return event;
	}
	
	public void getXuLyOk(ActionEvent e, CalendarEvent event1) {
		ConnectMySQL connection = new ConnectMySQL();
	    Connection connect = connection.connectMySQL();
	    Lich lich = new Lich();
		//Ten cong viec
		lich.setName(getName());
		
		//Noi dung cong viec
		lich.setContent(getContent());
		
		//Chuyen dinh dang cua ngay thang nam va thoi gian bat dau, ket thuc lich
		SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat formater1 = new SimpleDateFormat("HH:mm:ss");
		
		//Thoi gian bat dau
		String day = formater.format(getStart_day().getDate());
		String time1 = formater1.format(getStart_time().getValue());
		day += " " + time1;
		lich.setStart_time(day);
		
		//Thoi gian ket thuc
		day = formater.format(getEnd_day().getDate());
		time1 = formater1.format(getEnd_time().getValue());
		day += " " + time1;
		lich.setEnd_time(day);
		
		//Do uu tien cua su kien
		lich.setPriority((int)getPriority().getSelectedItem());
		
		//Kiem tra che do nhac nho
		if (getRdbtnYes().isSelected()) {
			lich.setPrompt("Yes");
		}
		else lich.setPrompt("No");
		
		//Kiem tra che do Lap lai
		if (getRdbtnYes1().isSelected()) {
			lich.setRepeat("Yes");
		}
		else lich.setRepeat("No");
		
		//Che do nhac truoc
		lich.setPrompt_before(getPrompt_before().getSelectedItem().toString());
		
		//Chuong bao
		lich.setSound(getSound());
		
		//Mau cua su kien
		lich.setColor(getColor().getSelectedColor().toString());
	 
		//Them thong tin cua lich vao CSDL
		PreparedStatement s = null;
		try {
			if (getTitle().compareTo("Đặt lịch") == 0) {
				s = connect.prepareStatement("insert into lich (`name`, `content`, `start_time`, `end_time`, `priority`, `status`, `prompt`, `isrepeat`, `prompt_before`, `color`, `sound`) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)"); 
			}
			else {
				s = connect.prepareStatement("update lich set name = ?, content = ?, start_time = ?, end_time = ?, priority = ?, status = ?, prompt = ?, isrepeat = ?, prompt_before = ?, color = ?, sound = ? where id = ?");
				s.setInt(12, event1.getId());
			}
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
			int id = 0;
			if (res > 0) {
				if (getTitle().compareTo("Đặt lịch") == 0) {
					JOptionPane.showMessageDialog(null, "Thêm thông tin thành công");
					
					//Lay id cua su kien phuc vu qua trinh xoa su kien
					s = connect.prepareStatement("select max(id) from lich");
					ResultSet set = s.executeQuery();
					while (set.next()) {
						System.out.println(Integer.parseInt(set.getString(1)));
						id = Integer.parseInt(set.getString(1));
					}
				}
				else {
					JOptionPane.showMessageDialog(null, "Sửa thông tin thành công");
					id = event1.getId();
				}
				 
				//Lay thong tin ve ngay bat dau, thoi gian bat dau, thoi gian ket thuc, color de ve
				Date d = getStart_day().getDate();
				Date ed = getEnd_day().getDate();
				Date d1 = (Date)getStart_time().getValue();
				Date d2 = (Date)getEnd_time().getValue();
				
				//Mau su kien
				String c =lich.getColor();
				Scanner sc = new Scanner(c);
			    sc.useDelimiter("\\D+");
			    Color color = new Color(sc.nextInt(), sc.nextInt(), sc.nextInt());
				
			    event = new CalendarEvent(id, lich, LocalDate.of(d.getYear() + 1900, d.getMonth() + 1, d.getDate()), 
						LocalTime.of(d1.getHours(), d1.getMinutes()), LocalTime.of(d2.getHours(), d2.getMinutes()), lich.getContent(), color,LocalDate.of(ed.getYear() + 1900, ed.getMonth() + 1, ed.getDate()));
			    
			    //Dong giao dien dat lich
			    setVisible(false);
			    
			     
			}
			else {
				JOptionPane.showMessageDialog(null, "Lỗi");
			}
		} catch (SQLException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
	}
}
