package presentationLayer;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

import businessLayer.Subject;

public class ChefGraphicalUserInterface extends Observer{
	private JTextField text;
	private JPanel mainPanel;
	private JFrame frame;
	
	public ChefGraphicalUserInterface(Subject subject){
	      this.subject = subject;
	      this.subject.attach(this);
	        
	   }

	   @Override
	   public void update() {
		    frame=new JFrame();
	        mainPanel = new JPanel();
			BoxLayout layout = new BoxLayout(mainPanel, BoxLayout.Y_AXIS);
			mainPanel.setLayout(layout);
			text= new JTextField("Am primit comanda!"
					+ "Ma apuc de gatit acum!");
			mainPanel.add(text);
			frame.setContentPane(mainPanel);
			frame.setSize(260, 150);
			frame.setTitle("Chef Window");
			frame.revalidate();
			frame.setVisible(true);
	   }

}
