import java.awt.Button;
import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.TextArea;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class Chat_Client extends JFrame {

	private JPanel contentPane;
	private static TextArea msg_area;
	private Button msg_send;
	private TextField msg_input;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Chat_Client frame = new Chat_Client();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		try {
			s = new Socket("192.168.0.18", 1201);
			din = new DataInputStream(s.getInputStream());
			dout = new DataOutputStream(s.getOutputStream());

			String msgin = "";
			while (!msgin.equals("exit")) {
				msgin = din.readUTF();
				msg_area.setText(msg_area.getText().trim() + "\n" + msgin);

			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the frame.
	 */
	static Socket s;
	static DataInputStream din;
	static DataOutputStream dout;

	public Chat_Client() {

		setup();
	}

	public void setup() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 496, 274);
		this.setTitle("Server Client");
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
		GridBagConstraints gbc_msg_area = new GridBagConstraints();
		gbc_msg_area.gridwidth = 2;
		gbc_msg_area.fill = GridBagConstraints.HORIZONTAL;
		gbc_msg_area.insets = new Insets(0, 0, 5, 0);
		gbc_msg_area.gridx = 0;
		gbc_msg_area.gridy = 0;
		contentPane.add(msg_area, gbc_msg_area);

		msg_send = new Button("send");
		msg_send.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String msgout = "";
				msgout = msg_input.getText().trim();
				msg_area.setText(msg_area.getText().trim() + "\n" + msgout);
				try {
					dout.writeUTF(msgout);
				} catch (IOException e) {
					e.printStackTrace();
				}

			}
		});
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

		this.setVisible(true);
	}
}
