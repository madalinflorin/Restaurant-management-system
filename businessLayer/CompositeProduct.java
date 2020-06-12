package businessLayer;

import java.util.ArrayList;


@SuppressWarnings("serial")
public class CompositeProduct extends MenuItem{

	private ArrayList<MenuItem> menuItems;
	
	public CompositeProduct() {
		
		super("Composite Product: ", 0.0f);
		menuItems = new ArrayList<>();
	}
	
	@Override
	public float computePrice() {
		// TODO Auto-generated method stub
		float price = 0.0f;
	
		for(MenuItem menuItem: menuItems) {
			price += menuItem.computePrice();
		}
		return price;
	}
	
	public String computeProducts() {
	String sir="";
	for(MenuItem menuItem: menuItems) {
		sir += menuItem.computeProducts()+"- x1\n";
	}
	sir+="Total: ";
	
	return sir;
	}
	
	public void setMenuItem(ArrayList<MenuItem> newMenuItem) {
		menuItems=newMenuItem;
	}

	@Override
	public void generateBill() {
		// TODO Auto-generated method stub
		
	}
	
	

}