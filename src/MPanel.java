import static com.teamdev.jxbrowser.engine.RenderingMode.HARDWARE_ACCELERATED;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import com.teamdev.jxbrowser.browser.Browser;
import com.teamdev.jxbrowser.engine.Engine;
import com.teamdev.jxbrowser.engine.EngineOptions;
import com.teamdev.jxbrowser.view.swing.BrowserView;

public class MPanel extends JPanel implements WindowListener{
	private JLabel listLabel;
	private JComboBox<String>listCombo;
	private JScrollPane scrollPane;
	private JPanel listPanel;
	private JButton formBtn;
	private JPanel mapPanel;
	private JTextField addressBar;
	private JPanel panel;
	private RManage manager;
	private ExecSQL ex;
	private EngineOptions options;
	public MPanel(RManage manager) {
		options =EngineOptions.newBuilder(HARDWARE_ACCELERATED).licenseKey("6P830J66YB642EDCAJKTAWM59CLFOHIXVXPBHNPUXQJE3FSXALLSMGMM3NSA2WU5DIH3").build();
		this.manager=manager;
		createList();
		createBtn();
		createPanel();
	}
	public void createList() {
		listLabel=new JLabel("Restaurant List:");
		Font font=new Font(listLabel.getFont().getName(),Font.BOLD,16);
		listLabel.setFont(font);
		listCombo=new JComboBox<String>();
		scrollPane = new JScrollPane(listCombo);
	}
	public JComboBox<String>setComboBox(ArrayList<Restaurant>restaurants) throws SQLException {
		listCombo.removeAllItems();
		for(int i=0;i<restaurants.size();i++) {
			listCombo.addItem(restaurants.get(i).getName());
		}
		createMap(listCombo);
		return listCombo;
	}
	public void createBtn() {
		formBtn=new JButton("Perform on map");
		formBtn.setBackground(new Color(29,98,30));
		formBtn.setForeground(Color.WHITE);
	}
	public void createMap(JComboBox<String>listCombo) {
		addressBar=new JTextField("https://www.google.com/maps/d/edit?mid=1eeJUX_qcIgk_48UXdaYzKHluFNFNIcwl&ll=24.987909365128758%2C121.57600009424775&z=17");
        Engine engine = Engine.newInstance(options);
        Browser browser = engine.newBrowser();
        
        SwingUtilities.invokeLater(() -> {
        BrowserView view = BrowserView.newInstance(browser);
            addressBar.addActionListener(e ->
                    browser.navigation().loadUrl(addressBar.getText()));
            setSize(500, 300);
            setVisible(true);
            mapPanel.add(view, BorderLayout.EAST);
            browser.navigation().loadUrl(addressBar.getText()) ;    });
        class ClickListener implements ActionListener{
        public void actionPerformed(ActionEvent e1) {
        	String url = "";
        	try {
        		ex = new ExecSQL(manager);
        		url = ex.getAddress(String.valueOf(listCombo.getSelectedItem()));
		
        	} catch (SQLException e) {
        		// TODO Auto-generated catch block
        		e.printStackTrace();
        	}
        	EngineOptions option =EngineOptions.newBuilder(HARDWARE_ACCELERATED).licenseKey("6P830J66YB642EDCAJKTAWM59CLFOHIXVXPBHNPUXQJE3FSXALLSMGMM3NSA2WU5DIH3").build();
        	JTextField newadd=new JTextField();
        	newadd.setText(url);
        	Engine engine2 = Engine.newInstance(option);
        	Browser browser2= engine2.newBrowser();
        	SwingUtilities.invokeLater(() -> {
        		BrowserView view2 = BrowserView.newInstance(browser2);
        		newadd.addActionListener(e2 ->
                      	browser2.navigation().loadUrl(newadd.getText()));
        		setSize(500, 300);
        		setVisible(true);
        		mapPanel.removeAll();
        		mapPanel.add(view2, BorderLayout.EAST);
        		mapPanel.updateUI();
        		browser2.navigation().loadUrl(newadd.getText()) ;    });
        		
   			}
        }
        ActionListener listener=new ClickListener();
        formBtn.addActionListener(listener);
	}
	public void createPanel() {
		listPanel=new JPanel(new BorderLayout());
		listPanel.add(listLabel,BorderLayout.NORTH);
		listPanel.add(scrollPane,BorderLayout.CENTER);
		listPanel.add(formBtn,BorderLayout.SOUTH);
		mapPanel=new JPanel(new BorderLayout());
		panel=new JPanel(new BorderLayout());
		panel.add(listPanel,BorderLayout.WEST);
		panel.add(mapPanel,BorderLayout.EAST);
		add(panel);
	}
	@Override
	public void windowOpened(WindowEvent e1) {
		// TODO Auto-generated method stub
	
	}
	@Override
	public void windowClosing(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void windowClosed(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void windowIconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void windowDeiconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void windowActivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void windowDeactivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}
}
