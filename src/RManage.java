import java.util.ArrayList;

public class RManage {
	private ArrayList<Restaurant>Rs;
	private int inputPrice;
	private String prefer;
	private String time;
	public RManage() {
		Rs=new ArrayList<Restaurant>();
	}
	public void addRestaurant(String name, int price) {
		Restaurant resultR=new Restaurant(name,price);
		Rs.add(resultR);
	}
	public ArrayList<Restaurant>getRestaurant(){
		return Rs;
	}
	public void addDemand(int price, String prefer, String time) {
		this.inputPrice=price;
		this.prefer=prefer;
		this.time=time;
	}
	public int getInputPrice() {
		return inputPrice;
	}
	public String getPreference() {
		return prefer;
	}
	public String getTime() {
		return time;
	}
}
