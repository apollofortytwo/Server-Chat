import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Inet4Address;
import java.net.Socket;

public class Chat_Client extends Chat_Server {

	Chat_Client() {
		super.setup();
		this.setTitle("Server Client");
		this.setVisible(true);
		try {
			ip = Inet4Address.getLocalHost().getHostAddress();
			s = new Socket("10.194.15.157", 1201);
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

	public static void main(String[] args) {
		new Chat_Client();

	}

}
