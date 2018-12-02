import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.border.TitledBorder;
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
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DatLich extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
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
	private JComboBox priority;
	private ColorChooserButton color;
	private JTextField textField_2;
	private JTextField textField_3;
	private JTextField textField_4;
	private JTextField textField_5;
	private Lich lich;
	JButton btnOk;
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
//		connectMySQL();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 500, 514);
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
		start_day.setFormats(new SimpleDateFormat("dd.MM.yyyy"));
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
		end_day.setFormats(new SimpleDateFormat("dd.MM.yyyy"));
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
		
		priority = new JComboBox();
		priority.setBounds(147, 23, 52, 20);
		priority.addItem(1);
		priority.addItem(2);
		priority.addItem(3);
		priority.addItem(4);
		priority.addItem(5);
		
		
		panel_1.add(priority);
		
		JLabel lblNhcNh = new JLabel("Nhắc nhở");
		lblNhcNh.setBounds(262, 26, 75, 14);
		panel_1.add(lblNhcNh);
		
		//Nhac nho chon Yes or No
		//Cho vao button group. Chon Yes thi khong No va nguoc lai.
		
		ButtonGroup buttonGroup = new ButtonGroup();

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
		
		ButtonGroup buttonGroup1 = new ButtonGroup();

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
	            fileChooser.setControlButtonsAreShown(true);
	            int choose = fileChooser.showOpenDialog(null);
	            //Neu chon Open thi lay duong dan cua file day vao textFiled_1
	            if (choose == fileChooser.APPROVE_OPTION) {
	            	File f = fileChooser.getSelectedFile();
	            	String path = f.getPath();
	            	sound.setText(path);
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
	
//	public Connection getConnect() {
//		return connect;
//	}
}
