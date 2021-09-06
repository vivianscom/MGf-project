import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.*;
public class HPanel extends JPanel{
	private static final int FIELD_WIDTH=20;
	private JLabel iconLabel;
	private JPanel pricePanel;
	private JTextField priceField;
	private JLabel priceLabel;
	private JPanel preferPanel;
	private JCheckBox pcb1;
	private JCheckBox pcb2;
	private JCheckBox pcb3;
	private JCheckBox pcb4;
	private JLabel preferLabel;
	private JPanel timePanel;
	private JLabel timeLabel;
	private JComboBox<String>timeCombo;
	 private JComboBox<String>priceCombo;
	private JPanel OPanel;
	private JPanel addPanel;
	private ExecSQL e;
	private JButton nextB;
	private RManage manager;
	public HPanel(){
		createIcon();
		createPrice();
		createCheck();
		createTimeComp();
		createNextButton();
		createPanel();
	}
	public void createIcon() {
		ImageIcon icon=new ImageIcon("hp_logo.png");
		iconLabel=new JLabel(icon);
		iconLabel.setSize(400,200);
	}
	public void createPrice() {
		priceLabel=new JLabel("Price:");
		priceField=new JTextField(FIELD_WIDTH);
		priceCombo=new JComboBox<String>();
		priceCombo.addItem("0-50");
		priceCombo.addItem("51-120");
		priceCombo.addItem("121-250");
		priceCombo.addItem("251-");
	}
	public void createCheck() {
		preferLabel=new JLabel("Preference:");
		pcb1=new JCheckBox("Taiwanese");
		pcb2=new JCheckBox("Japanese");
		pcb3=new JCheckBox("Western");
		pcb4=new JCheckBox("Korean");
	}
	public void createTimeComp() {
		timeLabel=new JLabel("Distance:");
		timeCombo=new JComboBox<String>();
		timeCombo.addItem("2min");
		timeCombo.addItem("4min");
		timeCombo.addItem("6min");
	}
	public void createNextButton() {
		nextB=new JButton("Next¡÷");
		nextB.setBackground(new Color(29,98,30));
		nextB.setForeground(Color.WHITE);
	}
	public void addButtonListener(JPanel panel1, RManage manager, JMenuBar mb) throws SQLException {
		this.manager=manager;
		CardLayout cardlayout=(CardLayout)panel1.getLayout();
		class ClickListener implements ActionListener{
			public void actionPerformed(ActionEvent e1) {
				String typeChoice="";
				if(pcb1.isSelected()) {
					typeChoice+="T";
				}
				if(pcb2.isSelected()) {
					typeChoice+="J";
				}
				if(pcb3.isSelected()) {
					typeChoice+="W";
				}
				if(pcb4.isSelected()) {
					typeChoice+="K";
				}
				if(priceCombo.getSelectedItem()!=null&&!typeChoice.equals("")&&timeCombo.getSelectedItem()!=null) {
					int price=0;
					if(priceCombo.getSelectedItem().equals("0-50")) {
					      price=50;
					     }else if(priceCombo.getSelectedItem().equals("51-120")){
					      price=120;
					     }else if(priceCombo.getSelectedItem().equals("121-250")) {
					      price=250;
					     }else if(priceCombo.getSelectedItem().equals("251-")) {
					      price=251;
					     }
					manager.addDemand(price, typeChoice, timeCombo.getSelectedItem().toString().substring(0,1));
					try {		
						e=new ExecSQL(manager);
						e.execute();
					}catch(SQLException e2){
						e2.getMessage();
					}
					cardlayout.show(panel1,"mappage");
					mb.setVisible(true);
				}else {
					JOptionPane.showMessageDialog(null,"Please fill in all the options.","Oops!",JOptionPane.ERROR_MESSAGE);
				}
			}
		}
		ActionListener listener=new ClickListener();
		nextB.addActionListener(listener);
	}
	public void createPanel() {
		pricePanel=new JPanel();
		pricePanel.add(priceLabel);
		pricePanel.add(priceCombo);
		preferPanel=new JPanel();
		preferPanel.add(preferLabel);
		preferPanel.add(pcb1);
		preferPanel.add(pcb2);
		preferPanel.add(pcb3);
		preferPanel.add(pcb4);
		timePanel=new JPanel();
		timePanel.add(timeLabel);
		timePanel.add(timeCombo);
		BorderLayout borderlayout1=new BorderLayout();
		OPanel=new JPanel();
		OPanel.setLayout(borderlayout1);
		OPanel.add(pricePanel,BorderLayout.NORTH);
		OPanel.add(preferPanel,BorderLayout.CENTER);
		OPanel.add(timePanel,BorderLayout.SOUTH);
		BorderLayout borderlayout2=new BorderLayout();
		addPanel=new JPanel();
		addPanel.setLayout(borderlayout2);
		addPanel.add(iconLabel,BorderLayout.NORTH);
		addPanel.add(OPanel,BorderLayout.CENTER);
		addPanel.add(nextB,BorderLayout.SOUTH);
		add(addPanel);
	}
}
