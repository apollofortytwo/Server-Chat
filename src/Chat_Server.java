import java.awt.Button;
import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.TextArea;
import java.awt.TextComponent;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Inet4Address;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Chat_Server extends JFrame {

	private JPanel contentPane;
	private TextField msg_input;
	private Button msg_send;
	protected static TextComponent msg_area;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Chat_Server frame = new Chat_Server();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		String msgin = "";
		try {
			ss = new ServerSocket(1201);
			s = ss.accept();
			din = new DataInputStream(s.getInputStream());
			dout = new DataOutputStream(s.getOutputStream());

			while (!msgin.equals("exit")) {
				msgin = din.readUTF(); // reads the input and assigns it to
										// variable msgin
				msg_area.setText(msg_area.getText().trim() + "\n" + msgin);
			}

		} catch (Exception e) {

		}

	}

	/**
	 * Create the frame.
	 */
	static ServerSocket ss;
	static Socket s;
	static DataOutputStream dout;
	static DataInputStream din;
	static String ip;
	
	public Chat_Server() {
		setup();
		try {
			ip = Inet4Address.getLocalHost().getHostAddress();
		} catch (UnknownHostException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	public void setup() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("Server Host");
		setBounds(100, 100, 496, 274);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[] { 380, 0, 0 };
		gbl_contentPane.rowHeights = new int[] { 160, 0, 0, 0 };
		gbl_contentPane.columnWeights = new double[] { 1.0, 1.0, Double.MIN_VALUE };
		gbl_contentPane.rowWeights = new double[] { 0.0, 1.0, 0.0, Double.MIN_VALUE };
		contentPane.setLayout(gbl_contentPane);

		msg_area = new TextArea();
		msg_area.setEditable(false);
		GridBagConstraints gbc_msg_area = new GridBagConstraints();
		gbc_msg_area.gridwidth = 2;
		gbc_msg_area.fill = GridBagConstraints.HORIZONTAL;
		gbc_msg_area.insets = new Insets(0, 0, 5, 0);
		gbc_msg_area.gridx = 0;
		gbc_msg_area.gridy = 0;
		contentPane.add(msg_area, gbc_msg_area);

		msg_send = new Button("send");

		GridBagConstraints gbc_msg_send = new GridBagConstraints();
		gbc_msg_send.fill = GridBagConstraints.BOTH;
		gbc_msg_send.gridheight = 2;
		gbc_msg_send.insets = new Insets(0, 0, 5, 0);
		gbc_msg_send.gridx = 1;
		gbc_msg_send.gridy = 1;
		contentPane.add(msg_send, gbc_msg_send);

		msg_input = new TextField();
		GridBagConstraints gbc_msg_input = new GridBagConstraints();
		gbc_msg_input.gridheight = 2;
		gbc_msg_input.insets = new Insets(0, 0, 0, 5);
		gbc_msg_input.fill = GridBagConstraints.BOTH;
		gbc_msg_input.gridx = 0;
		gbc_msg_input.gridy = 1;
		contentPane.add(msg_input, gbc_msg_input);

		msg_send.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				send();
			}
		});
		msg_input.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent arg0) {
				if (arg0.getKeyCode() == 10) {
					send();
				}
			}
		});
	}

	private void send() {
		String msgout = "";
		System.out.println(msg_input.getText().trim());
		msgout = msg_input.getText().trim();
		msg_area.setText(msg_area.getText().trim() + "\n" + ip + ": "+ msgout);
		msg_input.setText("");
		try {
			dout.writeUTF(ip + ": " + msgout);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
