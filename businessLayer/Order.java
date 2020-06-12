package businessLayer;

import java.util.Date;

@SuppressWarnings("serial")
public class Order implements java.io.Serializable {
	
	private long orderId;
	private Date date;
	private int table;
	
	public Order() {}
	
	public Order(long a,Date b,int c) {
		this.orderId=a;
		this.date=b;
		this.table=c;
	}
	
	public long getOrderId() {
		return orderId;
	}

	public void setOrderId(long orderId) {
		this.orderId = orderId;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
	
	
	public int hashCode() {return Long.hashCode(orderId);}

	public int getTable() {
		return table;
	}

	public void setTable(int table) {
		this.table = table;
	} 
}

