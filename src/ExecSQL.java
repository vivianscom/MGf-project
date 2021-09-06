
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ExecSQL {
	private static String url;
	private static String username;
	private static String password;
	private static Connection conn;
	private static Statement st;
	//private static ResultSet rs;
	private String query;
	private String price;
	private String distance;
	private String pre1;
	private String pre2;
	private String pre3;
	private RManage manager;
	private Homepage h;
	public ExecSQL(RManage manager) throws SQLException {
		String server="jdbc:mysql://140.119.19.73:9306/";
		String database="MG07";
		String config="?useUnicode=true&characterEncoding=utf8";
		url=server+database+config;
		username="MG07";
		password="D4NrtF";
		conn=DriverManager.getConnection(url,username,password);
		st = conn.createStatement();
		distance="";
		price="";
		pre1="";
		pre2="";
		pre3="";
		this.manager=manager;
		query="";
	}
	public void findPrice() throws SQLException {
		query="SELECT * FROM Test WHERE ";
		if(manager.getInputPrice()>=0) {
		  if(manager.getInputPrice()==50) {
				price="PriceLevel <= 0";
			}else if(manager.getInputPrice()==120) {
				price="PriceLevel <= 1";
			}else if(manager.getInputPrice()==250) {
				price="PriceLevel <= 2";
			}else {
				price="PriceLevel <= 3";
			}
		}
	}
	public void findDistance() throws SQLException {
		if(Integer.parseInt(manager.getTime())<=2) {
			distance="AND Distance <= 2 ";
		}else if(Integer.parseInt(manager.getTime())<=4) {
			distance="AND Distance <=4 ";
		}else if(Integer.parseInt(manager.getTime())>6) {
			distance="AND Distance <= 6 ";
		}
	}
	public void findPrefer() throws SQLException {
		if(manager.getPreference().length()>=1) {
			if(String.valueOf(manager.getPreference().charAt(0)).equals("T")) {
				pre1=" AND Preference ='Taiwanese' ";
			}
			if(String.valueOf(manager.getPreference().charAt(0)).equals("J")) {
				pre1=" AND Preference ='Japanese' ";
			}
			if(String.valueOf(manager.getPreference().charAt(0)).equals("K")) {
				pre1=" AND Preference ='Korean' ";
			}
			if(String.valueOf(manager.getPreference().charAt(0)).equals("W")) {
				pre1=" AND Preference ='Western' ";
			}
			query=query+price+pre1+distance;
		}
		if(manager.getPreference().length()>=2) {
			if(String.valueOf(manager.getPreference().charAt(1)).equals("T")) {
				pre2=" AND Preference ='Taiwanese' ";
			}
			if(String.valueOf(manager.getPreference().charAt(1)).equals("J")) {
				pre2=" AND Preference ='Japanese' ";
			}
			if(String.valueOf(manager.getPreference().charAt(1)).equals("K")) {
				pre2=" AND Preference ='Korean' ";
			}
			if(String.valueOf(manager.getPreference().charAt(1)).equals("W")) {
				pre2=" AND Preference ='Western' ";
			}
			query+="OR "+price+pre2+distance;
		}
		if(manager.getPreference().length()>=3) {
			if(String.valueOf(manager.getPreference().charAt(2)).equals("T")) {
				pre3=" AND Preference ='Taiwanese' ";
			}
			if(String.valueOf(manager.getPreference().charAt(2)).equals("J")) {
				pre3=" AND Preference ='Japanese' ";
			}
			if(String.valueOf(manager.getPreference().charAt(2)).equals("K")) {
				pre3=" AND Preference ='Korean' ";
			}
			if(String.valueOf(manager.getPreference().charAt(2)).equals("W")) {
				pre3=" AND Preference ='Western' ";
			}
			query+="OR "+price+pre3+distance;
		}
		if(manager.getPreference().length()==4) {
			query=query+distance;
		}
	}
	public void execute() throws SQLException {
		h=new Homepage();
		manager.getRestaurant().clear();
		findPrice();
		findDistance();
		findPrefer();
		//System.out.println(query);
		ResultSet rs=st.executeQuery(query);
		while(rs.next()) {
			manager.addRestaurant(rs.getString(1), manager.getInputPrice());
		}
		h.getMPanel().setComboBox(manager.getRestaurant());
	    rs.close();
	}
	public String getAddress(String selectedItem) throws SQLException {
		String q="SELECT * FROM Test WHERE Name=?";
		PreparedStatement findAddStat=conn.prepareStatement(q);
		findAddStat.setString(1,selectedItem);
		ResultSet rs=findAddStat.executeQuery();
		while(rs.next()) {
			return rs.getString("Address");
		}
		return null;
	}
}
