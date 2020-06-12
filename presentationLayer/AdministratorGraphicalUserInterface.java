package presentationLayer;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import businessLayer.BaseProduct;
import businessLayer.MenuItem;
import businessLayer.Restaurant;
import businessLayer.RestaurantProcessingAdministrator;
import dataLayer.RestaurantSerializator;

@SuppressWarnings("serial")
public class AdministratorGraphicalUserInterface extends JFrame implements RestaurantProcessingAdministrator {

	private static final String filename = "restaurant.ser";
	
	private Restaurant restaurant;
	private JTable menuItemsTable;
	private JButton addNewItem;
	private JButton editItem;
	private JButton deleteItem;
	private MenuItem selectedMenuItem;

	public AdministratorGraphicalUserInterface() {
		
		selectedMenuItem = null;

		JPanel mainPanel = new JPanel();
		BoxLayout layout = new BoxLayout(mainPanel, BoxLayout.Y_AXIS);
		mainPanel.setLayout(layout);

		restaurant=RestaurantSerializator.deserialize(restaurant);

		menuItemsTable = createJTable();

		addNewItem = new JButton("Add New Item");
		editItem = new JButton("Edit Item");
		deleteItem = new JButton("Delete Item");

		addActionListeners();

		mainPanel.add(menuItemsTable);
		mainPanel.add(addNewItem);
		mainPanel.add(editItem);
		mainPanel.add(deleteItem);
		
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
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.revalidate();
		this.setVisible(true);
	}

	private JTable createJTable() {

		JTable menuItemsJTable;

		ArrayList<MenuItem> menuItems = restaurant.getMenuItems();

		String[] columns = { "Description", "Price" };
		Object[][] rows = new Object[menuItems.size()][columns.length];

		for (int i = 0; i < menuItems.size(); i++) {

			rows[i][0] = menuItems.get(i).getDescription();
			rows[i][1] = menuItems.get(i).computePrice();
		}

		menuItemsJTable = new JTable(rows, columns);

		return menuItemsJTable;
	}

	private void addActionListeners() {

		addNewItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				JFrame addNewItemJFrame = new JFrame();
				JPanel addNewItemJPanel = new JPanel();
				BoxLayout layout = new BoxLayout(addNewItemJPanel, BoxLayout.Y_AXIS);
				addNewItemJPanel.setLayout(layout);
				JButton addNewItemJButton = new JButton("Add New Item");
				JTextField descriptionJTF = new JTextField("Description");
				JTextField priceJTF = new JTextField("Price");

				addNewItemJButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {

						String description = descriptionJTF.getText();
						float price = Float.parseFloat(priceJTF.getText());
						MenuItem newMenuItem = new BaseProduct(description, price);
						addNewMenuItem(newMenuItem);
						menuItemsTable = createJTable();
						revalidateView();
						addNewItemJFrame.setVisible(false);
					}
				});
				addNewItemJPanel.add(descriptionJTF);
				addNewItemJPanel.add(priceJTF);
				addNewItemJPanel.add(addNewItemJButton);

				addNewItemJFrame.setContentPane(addNewItemJPanel);
				addNewItemJFrame.setSize(320, 200);
				addNewItemJFrame.setTitle("Add New Item");
				addNewItemJFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				addNewItemJFrame.setVisible(true);
				RestaurantSerializator.serialize(restaurant);
			}
		});
		menuItemsTable.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {

				// i = the index of the selected row
				int i = menuItemsTable.getSelectedRow();
				selectedMenuItem = restaurant.getMenuItems().get(i);
			}
		});
		editItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if (selectedMenuItem == null)
					JOptionPane.showMessageDialog(null, "No Item Selected", "Error", JOptionPane.ERROR_MESSAGE);
				else {
					JFrame editItemJFrame = new JFrame();
					JPanel editItemJPanel = new JPanel();
					BoxLayout layout = new BoxLayout(editItemJPanel, BoxLayout.Y_AXIS);
					editItemJPanel.setLayout(layout);
					JButton editItemJButton = new JButton("Add New Item Name");
					JTextField descriptionJTF = new JTextField("Description");
					JTextField priceJTF = new JTextField("Price");
					editItemJButton.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {

							String description = descriptionJTF.getText();
							float price = Float.parseFloat(priceJTF.getText());
							MenuItem newMenuItem = new BaseProduct(description, price);
							addNewMenuItem(newMenuItem);
							deleteMenuItem(selectedMenuItem);
							menuItemsTable = createJTable();
							menuItemsTable.repaint();
							revalidateView();
							JOptionPane.showMessageDialog(null, "Item Edited", "Message", JOptionPane.DEFAULT_OPTION);		
							editItemJFrame.setVisible(false);
						}
					});
					editItemJPanel.add(descriptionJTF);
					editItemJPanel.add(priceJTF);
					editItemJPanel.add(editItemJButton);

					editItemJFrame.setContentPane(editItemJPanel);
					editItemJFrame.setSize(320, 200);
					editItemJFrame.setTitle("Edit new item");
					editItemJFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
					editItemJFrame.setVisible(true);
					deleteMenuItem(selectedMenuItem);
					selectedMenuItem = null;
					RestaurantSerializator.serialize(restaurant);
					
					
				}
			}

		});
		deleteItem.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				if (selectedMenuItem == null)
					JOptionPane.showMessageDialog(null, "No Item Selected", "Error", JOptionPane.ERROR_MESSAGE);
				else {
					deleteMenuItem(selectedMenuItem);
					JOptionPane.showMessageDialog(null, "Item Deleted", "Message", JOptionPane.DEFAULT_OPTION);
					selectedMenuItem = null;
					menuItemsTable = createJTable();
					menuItemsTable.revalidate();
					RestaurantSerializator.serialize(restaurant);
					revalidateView();
					
				}
			}

		});
		
	}

	@Override
	public void addNewMenuItem(MenuItem menuItem) {
		// TODO Auto-generated method stub
		restaurant.addNewMenuItem(menuItem);

	}

	@Override
	public void editMenuItem(MenuItem menuItem, MenuItem menuItemModified) {
		// TODO Auto-generated method stub
		restaurant.editMenuItem(menuItem, menuItemModified);

	}

	@Override
	public void deleteMenuItem(MenuItem menuItem) {
		// TODO Auto-generated method stub
		restaurant.deleteMenuItem(menuItem);

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
		new AdministratorGraphicalUserInterface();
	}
	


}