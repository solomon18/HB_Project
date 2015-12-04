package Doubt_Server;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;

import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class UI {

	private JFrame frame;
	private JTextField textField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UI window = new UI();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public UI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setTitle("\uC811\uC18D\uC790 \uC218 : ");
		frame.setBounds(100, 100, 659, 419);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel label = new JLabel("\uC811\uC18D\uC790\uC218 : ");
		label.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
			}
		});
		label.setForeground(new Color(255, 0, 0));
		label.setBackground(Color.PINK);
		label.setBounds(22, 10, 116, 15);
		frame.getContentPane().add(label);
		
		textField = new JTextField();
		textField.setBackground(Color.WHITE);
		textField.setBounds(12, 35, 619, 299);
		frame.getContentPane().add(textField);
		textField.setColumns(10);
		
		JButton button = new JButton("\uC885\uB8CC");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		});
		button.setBounds(534, 347, 97, 23);
		frame.getContentPane().add(button);
	}
}
