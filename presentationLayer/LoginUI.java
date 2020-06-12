package presentationLayer;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class LoginUI extends JFrame {
	
	private JButton loginAdmin;
	private JButton loginWaiter;

	public LoginUI() {

		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new GridLayout(1,2));

		loginAdmin = new JButton("Login as Admin");
		loginWaiter = new JButton("Login as Waiter");

		mainPanel.add(loginAdmin);
		mainPanel.add(loginWaiter);
		addActionListeners();
		
		this.setContentPane(mainPanel);
		this.setSize(400, 150);
		this.setTitle("LoginView Window");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
	}

	private void addActionListeners() {

		loginAdmin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				new AdministratorGraphicalUserInterface();
			}
		});
		loginWaiter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				new WaiterGraphicalUserInterface();
			}
		});
	}

}
