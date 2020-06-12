package businessLayer;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

@SuppressWarnings("serial")
public class Restaurant implements Serializable{
	
	private  HashMap<Order, Collection<MenuItem>> orders;
	private ArrayList<MenuItem> menuItems;
	
	public Restaurant() {
		
		orders = new HashMap<Order, Collection<MenuItem>>();
		menuItems = new ArrayList<>();
	}

	public HashMap<Order, Collection<MenuItem>> getOrders() {
		return orders;
	}

	public void setOrders(HashMap<Order, Collection<MenuItem>> orders) {
		this.orders = orders;
	}

	public ArrayList<MenuItem> getMenuItems() {
		return menuItems;
	}

	public void setMenuItems(ArrayList<MenuItem> menuItems) {
		this.menuItems = menuItems;
	}
	
	public void addNewMenuItem(MenuItem newMenuItem) {
	    int count=menuItems.size();
		this.menuItems.add(newMenuItem);
		assert (menuItems.size()==count+1):"Cannot add menu!";
	}
	
	public void deleteMenuItem(MenuItem menuItem) {
		assert isWellFormed();
		this.menuItems.remove(menuItem);
	}
	
	public void editMenuItem(MenuItem menuItem, MenuItem menuItemModified) {
		assert isWellFormed();
		assert (menuItem!=null):"Cannot edit menu if he doesn't exist!";
		this.menuItems.remove(menuItem);
		this.menuItems.add(menuItemModified);
	}
	
	public void addNewOrderItem(Order order,ArrayList<MenuItem> meniu) {
		int count=orders.size();
		this.orders.put(order, meniu);
		assert (orders.size()==count+1):"Cannot add order";
	}
	
	private  boolean isWellFormed() {
		if (menuItems == null)
			return false;
		return true;
	}


}