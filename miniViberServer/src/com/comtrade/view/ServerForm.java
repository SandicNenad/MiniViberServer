package com.comtrade.view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.comtrade.controllerserver.ControllerServer;
import com.comtrade.server.Server;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;

public class ServerForm extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JButton btnStartServer;
	private JLabel lblServer;
	private JTextArea taServer;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ServerForm frame = new ServerForm();
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
	public ServerForm() {
		setTitle("Server");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 529, 504);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		btnStartServer = new JButton("Start Server");
		btnStartServer.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 13));
		btnStartServer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Server server = new Server();
				server.start();
				btnStartServer.setVisible(false);
				lblServer.setText("Server started and ready to accept clients");
				ControllerServer.getInstance().setTextArea(taServer);
			}
		});
		btnStartServer.setBounds(172, 11, 149, 38);
		contentPane.add(btnStartServer);
		
		lblServer = new JLabel("");
		lblServer.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 13));
		lblServer.setForeground(Color.BLACK);
		lblServer.setHorizontalAlignment(SwingConstants.CENTER);
		lblServer.setBounds(100, 11, 296, 28);
		contentPane.add(lblServer);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(45, 88, 422, 328);
		contentPane.add(scrollPane);
		
		taServer = new JTextArea();
		scrollPane.setViewportView(taServer);
	}
}
