package businessLayer;

import java.io.Serializable;

@SuppressWarnings("serial")
public abstract class MenuItem implements Serializable{
	
	protected String description;
	protected float price;
	
	public MenuItem(String description, float price) {
		
		this.description = description;
		this.price= price;
	}
	
	public abstract float computePrice();
	public abstract String computeProducts();
	
	public abstract void generateBill();
	
	public String getDescription() {
		return description;
	}
	
	public String toString() {
		return description+" "+price;
	}

}