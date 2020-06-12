package businessLayer;

@SuppressWarnings("serial")
public class BaseProduct extends MenuItem{
	
	public BaseProduct(String description, float price) {
		super(description, price);
	}
	
	@Override
	public float computePrice() {
		// TODO Auto-generated method stub
		return price;
	}
	
	public String computeProducts() {
		// TODO Auto-generated method stub
		return description;
	}

	@Override
	public void generateBill() {
		// TODO Auto-generated method stub
	}

}