import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
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
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.awt.event.ActionListener;
import java.io.File;

public class DatLich extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;
	private JTextField textField_4;
	private JTextField textField_5;

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
		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
		
		textField = new JTextField();
		textField.setBounds(148, 21, 292, 20);
		panel.add(textField);
		textField.setColumns(10);
		
		JTextArea textArea = new JTextArea();
		textArea.setBounds(148, 56, 292, 84);
		panel.add(textArea);
		
		JLabel lblThiGianBt = new JLabel("Thời gian bắt đầu");
		lblThiGianBt.setBounds(10, 164, 107, 14);
		panel.add(lblThiGianBt);
		
		JLabel lblThiGianKt = new JLabel("Thời gian kết thúc");
		lblThiGianKt.setBounds(10, 195, 107, 14);
		panel.add(lblThiGianKt);
		
//		Chon ngay bat dau
		JXDatePicker picker_1 = new JXDatePicker();
		picker_1.setDate(Calendar.getInstance().getTime());
		picker_1.setFormats(new SimpleDateFormat("dd.MM.yyyy"));
		picker_1.setBounds(148, 161, 110, 20);
        panel.add(picker_1);

		
//		Chon gio bat dau
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 24); // 24 == 12 PM == 00:00:00
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);

        SpinnerDateModel model_1 = new SpinnerDateModel();
        model_1.setValue(calendar.getTime());

        JSpinner spinner_1 = new JSpinner(model_1);

        JSpinner.DateEditor editor_1 = new JSpinner.DateEditor(spinner_1, "HH:mm:ss");
        DateFormatter formatter_1 = (DateFormatter)editor_1.getTextField().getFormatter();
        formatter_1.setAllowsInvalid(false); // this makes what you want
        formatter_1.setOverwriteMode(true);

        spinner_1.setEditor(editor_1);
        spinner_1.setBounds(307, 161, 86, 20);
		panel.add(spinner_1);      
		
//        Chon ngay ket thuc
		JXDatePicker picker_2 = new JXDatePicker();
		picker_2.setDate(Calendar.getInstance().getTime());
		picker_2.setFormats(new SimpleDateFormat("dd.MM.yyyy"));
		picker_2.setBounds(148, 192, 110, 20);
        panel.add(picker_2);
		
//		Chon gio ket thuc
        
        SpinnerDateModel model_2 = new SpinnerDateModel();
        model_2.setValue(calendar.getTime());

        JSpinner spinner_2 = new JSpinner(model_2);
        
        JSpinner.DateEditor editor_2 = new JSpinner.DateEditor(spinner_2, "HH:mm:ss");
        DateFormatter formatter_2 = (DateFormatter)editor_2.getTextField().getFormatter();
        formatter_2.setAllowsInvalid(false); // this makes what you want
        formatter_2.setOverwriteMode(true);

        spinner_2.setEditor(editor_2);
        spinner_2.setBounds(307, 192, 86, 20);
		panel.add(spinner_2); 
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(null, "Option", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_1.setBounds(10, 256, 464, 187);
		contentPane.add(panel_1);
		panel_1.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Độ ưu tiên");
		lblNewLabel.setBounds(10, 26, 72, 14);
		panel_1.add(lblNewLabel);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setBounds(147, 23, 52, 20);
		comboBox.addItem(1);
		comboBox.addItem(2);
		comboBox.addItem(3);
		comboBox.addItem(4);
		comboBox.addItem(5);
		
		
		panel_1.add(comboBox);
		
		JLabel lblNhcNh = new JLabel("Nhắc nhở");
		lblNhcNh.setBounds(262, 26, 75, 14);
		panel_1.add(lblNhcNh);
		
		//Nhac nho chon Yes or No
		//Cho vao button group. Chon Yes thi khong No va nguoc lai.
		
		ButtonGroup buttonGroup = new ButtonGroup();

		JRadioButton rdbtnYes = new JRadioButton("Yes");
		rdbtnYes.setBounds(343, 22, 52, 23);
		rdbtnYes.setSelected(true);
		buttonGroup.add(rdbtnYes);
		panel_1.add(rdbtnYes);
		
		JRadioButton rdbtnNo = new JRadioButton("No");
		rdbtnNo.setBounds(397, 22, 61, 23);
		buttonGroup.add(rdbtnNo);		
		panel_1.add(rdbtnNo);	
		
		JLabel lblThiGianNhc = new JLabel("Thời gian nhắc trước");
		lblThiGianNhc.setBounds(10, 61, 135, 14);
		panel_1.add(lblThiGianNhc);
		
		JLabel lblLpLi = new JLabel("Lặp lại");
		lblLpLi.setBounds(262, 61, 46, 14);
		panel_1.add(lblLpLi);
		
		JComboBox comboBox_1 = new JComboBox();
		comboBox_1.setBounds(147, 58, 89, 20);
		comboBox_1.addItem("Không");
		comboBox_1.addItem("5 minute");
		comboBox_1.addItem("10 minute");
		comboBox_1.addItem("15 minute");
		
		panel_1.add(comboBox_1);
		
		JLabel lblmBo = new JLabel("Âm báo");
		lblmBo.setBounds(10, 101, 46, 14);
		panel_1.add(lblmBo);
		
		textField_1 = new JTextField();
		textField_1.setBounds(147, 98, 189, 20);
		panel_1.add(textField_1);
		textField_1.setColumns(10);
		
		//Chon file am thanh bao 
		JButton btnBrowse = new JButton("Browse");
		btnBrowse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame frame = new JFrame("File Browser");

	            JFileChooser fileChooser = new JFileChooser(".");
	            fileChooser.setControlButtonsAreShown(false);
	            frame.getContentPane().add(fileChooser, BorderLayout.CENTER);

	            ActionListener actionListener = new ActionListener() {
	                public void actionPerformed(ActionEvent actionEvent) {
	                    JFileChooser theFileChooser = (JFileChooser) actionEvent
	                            .getSource();
	                    String command = actionEvent.getActionCommand();
	                    if (command.equals(JFileChooser.APPROVE_SELECTION)) {
	                        File selectedFile = theFileChooser
	                                .getSelectedFile();
	                        System.out.println(selectedFile.getParent());
	                        System.out.println(selectedFile.getName());
	                    } else if (command
	                            .equals(JFileChooser.CANCEL_SELECTION)) {
	                        System.out.println(JFileChooser.CANCEL_SELECTION);
	                    }
	                }
	            };
	            fileChooser.addActionListener(actionListener);
	            frame.pack();
	            frame.setVisible(true);
			}
		});
		btnBrowse.setBounds(346, 97, 89, 23);
		panel_1.add(btnBrowse);
		


		
		
		JLabel lblMu = new JLabel("Màu");
		lblMu.setBounds(10, 140, 46, 14);
		panel_1.add(lblMu);
		
		ColorChooserButton btnChooseColor = new ColorChooserButton(Color.PINK);
		btnChooseColor.setBounds(147, 136, 89, 23);
		panel_1.add(btnChooseColor);
		
		JButton btnNewButton = new JButton("OK");
		btnNewButton.setBounds(258, 447, 89, 23);
		contentPane.add(btnNewButton);
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.setBounds(137, 447, 89, 23);
		contentPane.add(btnCancel);
	}
}
