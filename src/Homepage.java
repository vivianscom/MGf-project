import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;


public class Homepage {
	private static final int FRAME_WIDTH=1050;
	private static final int FRAME_HEIGHT=700;
	static RManage manager=new RManage();
	static MPanel mappage=new MPanel(manager);
	private static boolean again=true;
	public static void main(String[] args) throws SQLException {
		JFrame frame=new JFrame();
		JPanel panel=new JPanel();
		HPanel homepage=new HPanel();
		
		JMenuBar mb=new JMenuBar();
		JMenu menu=new JMenu("Menu");
		JMenuItem home=new JMenuItem("Home");
		home.setActionCommand("Home");
		JMenuItem exit=new JMenuItem("Exit");
		exit.setActionCommand("Exit");
		CardLayout cardlayout=new CardLayout();
		panel.setLayout(cardlayout);
		panel.add(homepage,"homepage");
		panel.add(mappage,"mappage");
		cardlayout.show(panel,"homepage");
		class MenuItemListener1 implements ActionListener{
			public void actionPerformed(ActionEvent event) {
				frame.setVisible(false);
				mappage.removeAll();
				try {
					mappage=new MPanel(manager);
					main(args);
				}catch(SQLException e){
					e.printStackTrace();
				}
				cardlayout.show(panel,"homepage");
				mb.setVisible(false);
			}
		}
		MenuItemListener1 mil1=new MenuItemListener1();
		home.addActionListener(mil1);
		class MenuItemListener2 implements ActionListener{
			public void actionPerformed(ActionEvent event) {
				System.exit(0);
			}
		}
		MenuItemListener2 mil2=new MenuItemListener2();
		exit.addActionListener(mil2);
		menu.add(home);
		menu.add(exit);
		mb.add(menu);
		mb.setVisible(false);
		homepage.addButtonListener(panel, manager, mb);
		frame.add(panel);
		frame.setTitle("MoneyGrabber");
		frame.setJMenuBar(mb);
		frame.setSize(FRAME_WIDTH,FRAME_HEIGHT);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		frame.setLayout(null);
	}
	public MPanel getMPanel() {
		return mappage;
	}
}
	
