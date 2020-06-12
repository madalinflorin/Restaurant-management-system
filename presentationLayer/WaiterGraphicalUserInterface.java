package presentationLayer;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Set;
import java.util.logging.Level;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import businessLayer.CompositeProduct;
import dataLayer.FileWriter;
import dataLayer.RestaurantSerializator;
import businessLayer.MenuItem;
import businessLayer.Restaurant;
import businessLayer.RestaurantProcessingWaiter;
import businessLayer.Subject;
import businessLayer.Order;


@SuppressWarnings("serial")
public class WaiterGraphicalUserInterface extends JFrame implements RestaurantProcessingWaiter {
	private static final String filename = "restaurant.ser";

	private Restaurant restaurant;
	private JTable ordersTable;
	private JButton addNewOrder;
	private JButton viewAllOrders;
	private JButton createBill;
	private String selectedMenuItem;
	private ArrayList<MenuItem> ordersProduct;
	private ArrayList<String> bon=new ArrayList<>();
	private int contor=1;
	
	public WaiterGraphicalUserInterface() {

		selectedMenuItem = null;

		JPanel mainPanel = new JPanel();
		BoxLayout layout = new BoxLayout(mainPanel, BoxLayout.Y_AXIS);
		mainPanel.setLayout(layout);

		restaurant=RestaurantSerializator.deserialize(restaurant);

		ordersTable = createJTableOrders();

		addNewOrder = new JButton("Add New Order");
		viewAllOrders = new JButton("View all orders");
		createBill = new JButton("Create bill for an order");

		addActionListeners();

		mainPanel.add(ordersTable);
		mainPanel.add(addNewOrder);
		mainPanel.add(viewAllOrders);
		mainPanel.add(createBill);

		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				try {
					FileOutputStream fileOut = new FileOutputStream(filename);
					ObjectOutputStream out = new ObjectOutputStream(fileOut);
					out.writeObject(restaurant);
					out.close();
					fileOut.close();
					System.out.printf("Serialized data is saved in " + filename + "\n");
				} catch (IOException i) {
					i.printStackTrace();
				}
				e.getWindow().dispose();
			}
		});

		this.setContentPane(mainPanel);
		this.setSize(250, 150);
		this.setTitle("Administrator Window");
		this.revalidate();
		this.setVisible(true);
	}


	
	private JTable createJTableOrders() {

		JTable ordersItemsJTable;

		HashMap<Order, Collection<MenuItem>> orderItems = restaurant.getOrders();
		String[] columns = { "OrderID", "Date","Table","Products" };
		Object[][] rows = new Object[orderItems.size()][columns.length];

		Set<Order> orders = orderItems.keySet();
		
		int i = 0;
		
		for(Order order : orders) {
			
			Collection<MenuItem> menuItem = orderItems.get(order);
			CompositeProduct p=new CompositeProduct();
			p.setMenuItem((ArrayList<MenuItem>) menuItem);
			float pret=p.computePrice();
			String sir="Bill for order "+(order.getOrderId()+i+1)+"\n"+p.computeProducts();
			sir=sir+pret;
            bon.add(sir);
			
			rows[i][0] = order.getOrderId()+i+1;
			rows[i][1] = order.getDate();
			rows[i][2] = order.getTable();
			rows[i][3] = menuItem.size();
			i++;
		}
		
		
		ordersItemsJTable = new JTable(rows, columns);

		return ordersItemsJTable;
	}

	private void addActionListeners() {

		addNewOrder.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ordersProduct=new ArrayList<>();
				JFrame addNewOrderJFrame = new JFrame();
				JPanel addNewOrderJPanel = new JPanel();
				BoxLayout layout = new BoxLayout(addNewOrderJPanel, BoxLayout.Y_AXIS);
				addNewOrderJPanel.setLayout(layout);
				JButton addNewOrderJButton = new JButton("Order");
				JButton addProduct = new JButton("Add product");
				JTextField descriptionJTF = new JTextField("Product");
				JTextField productJTF = new JTextField("Table");

				addNewOrderJButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						int table = Integer.parseInt(productJTF.getText());
						Date date = new Date(System.currentTimeMillis());
						Order newOrder=new Order(0,date,table);
						addNewOrderItem(newOrder,ordersProduct);
						Subject subject = new Subject();

					      new ChefGraphicalUserInterface(subject);
					      subject.setState(1);
						ordersTable=createJTableOrders();
						ordersProduct=new ArrayList<>();
						JFrame afisOrdersJFrame = new JFrame();
						JPanel afisOrdersJPanel = new JPanel();
						BoxLayout layout1 = new BoxLayout(afisOrdersJPanel, BoxLayout.Y_AXIS);
						afisOrdersJPanel.setLayout(layout1);
						afisOrdersJPanel.add(ordersTable);
						afisOrdersJFrame.setContentPane(afisOrdersJPanel);
						afisOrdersJFrame.setSize(320, 200);
						afisOrdersJFrame.setTitle("Orders");
						afisOrdersJFrame.setVisible(true);
						revalidateView();
						
						
					}
				});
				addProduct.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						int ok=0;
						String description = descriptionJTF.getText();
						ArrayList<MenuItem> menuItems = restaurant.getMenuItems();
						for(int i=0;i<menuItems.size();i++) if(description.equals(menuItems.get(i).getDescription())) {ordersProduct.add(menuItems.get(i));ok=1;}
						if(ok==0) JOptionPane.showMessageDialog(null, "No Item with this name", "Error", JOptionPane.ERROR_MESSAGE);
						addNewOrderJFrame.setVisible(true);
					}
				});
				addNewOrderJPanel.add(descriptionJTF);
				addNewOrderJPanel.add(productJTF);
				addNewOrderJPanel.add(addNewOrderJButton);
				addNewOrderJPanel.add(addProduct);

				addNewOrderJFrame.setContentPane(addNewOrderJPanel);
				addNewOrderJFrame.setSize(320, 200);
				addNewOrderJFrame.setTitle("Add New Order");
				addNewOrderJFrame.setVisible(true);

			}
		});
		
		ordersTable.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {

				// i = the index of the selected row
				int i = ordersTable.getSelectedRow();
				selectedMenuItem = bon.get(i);
			}
		});
		
		viewAllOrders.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				ordersTable=createJTableOrders();
				JFrame viewAllOrdersJFrame = new JFrame();
				JPanel viewAllOrdersJPanel = new JPanel();
				BoxLayout layout1 = new BoxLayout(viewAllOrdersJPanel, BoxLayout.Y_AXIS);
				viewAllOrdersJPanel.setLayout(layout1);
				viewAllOrdersJPanel.add(ordersTable);
				viewAllOrdersJFrame.setContentPane(viewAllOrdersJPanel);
				viewAllOrdersJFrame.setSize(320, 200);
				viewAllOrdersJFrame.setTitle("Orders");
				viewAllOrdersJFrame.setVisible(true);
				

			}

		});
		createBill.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				if (selectedMenuItem == null)
					JOptionPane.showMessageDialog(null, "No Item Selected", "Error", JOptionPane.ERROR_MESSAGE);
				else {
					FileWriter.LOGGER.log(Level.INFO,selectedMenuItem);
					try {
						printFile(selectedMenuItem,contor);
						contor++;
					} catch (FileNotFoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					JOptionPane.showMessageDialog(null, "SUCCES", "Message", JOptionPane.DEFAULT_OPTION);
					selectedMenuItem = null;
					
				}
			}

		});
	}
	
	public static void printFile(String sir,int i) throws FileNotFoundException {
        
		PrintWriter writerFile = new PrintWriter("bill"+i+".txt");
		

		writerFile.println(sir);

		writerFile.close();

	}
	@Override
	public void addNewOrderItem(Order a,ArrayList<MenuItem> b) {
		// TODO Auto-generated method stub
		restaurant.addNewOrderItem(a, b);

	}
	public void revalidateView() {
		try {
			FileOutputStream fileOut = new FileOutputStream(filename);
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
			out.writeObject(restaurant);
			out.close();
			fileOut.close();
			System.out.printf("Serialized data is saved in " + filename + "\n");
		} catch (IOException i) {
			i.printStackTrace();
		}
		dispose();
		new WaiterGraphicalUserInterface();
	}


}
