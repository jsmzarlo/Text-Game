package cs222.Fall2012.JH.YouJustLostTheGame.TeamGoku.GUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.plaf.basic.BasicArrowButton;

import cs222.Fall2012.JH.YouJustLostTheGame.TeamGoku.CartesianPoint;
import cs222.Fall2012.JH.YouJustLostTheGame.TeamGoku.CommandParser;
import cs222.Fall2012.JH.YouJustLostTheGame.TeamGoku.GameLog;
import cs222.Fall2012.JH.YouJustLostTheGame.TeamGoku.GameMap;
import cs222.Fall2012.JH.YouJustLostTheGame.TeamGoku.Player;

public class GameGUI {
	
	private static JTextField commandField;
	private static JTextArea textArea;
	private static JLabel healthLabel;
	private static JLabel attackLabel;
	private static JLabel defenseLabel;
	public static Player player;
	private static JTextArea invArea;
	public static String filePath;
	
	public static void main(String [] args){
		CartesianPoint.setCoordinates(0, 0);
		JFrame gameFrame = new JFrame();
		gameFrame.setVisible(true);
		JPanel bigPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		bigPanel.add(leftSide());
		bigPanel.add(rightSide());
		gameFrame.add(bigPanel);
		gameFrame.setSize(800, 575);
		gameFrame.setResizable(false);
		gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		chooseFile();
		GameMap.start(filePath);
	}
	private static void chooseFile(){
		JFileChooser chooser = new JFileChooser();
	    chooser.setFileFilter(new FileNameExtensionFilter("XML Files", "xml"));
	    int returnVal = chooser.showOpenDialog(null);
	    if(returnVal == JFileChooser.APPROVE_OPTION) {
	       filePath = chooser.getSelectedFile().getAbsolutePath();
	    }
	    else{
	    	JOptionPane.showMessageDialog(null, "You did not choose a file. Reverting to default.");
	    	filePath = "story2.xml";
	    }
	}
	private static JPanel leftSide(){
		JPanel leftPanel = new JPanel();
		leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
		leftPanel.add(console());
		leftPanel.add(command());
		return leftPanel;
	}
	
	
	private static JScrollPane console(){
		textArea = new JTextArea("");
		resetConsole();
		JScrollPane consoleScroll = new JScrollPane(textArea);
		textArea.setEditable(false);
		textArea.setLineWrap(true);
		textArea.setWrapStyleWord(true);
		textArea.setBackground(Color.BLACK);
		textArea.setForeground(Color.WHITE);
		textArea.setFont(new Font("Monospaced",Font.PLAIN,14));
		consoleScroll.setPreferredSize(new Dimension(500, 500));
		consoleScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		return consoleScroll;
	}
	
	private static JPanel command(){
		JPanel commandPanel = new JPanel();
		commandPanel.setLayout(new BoxLayout(commandPanel, BoxLayout.X_AXIS));
		commandField = new JTextField(25);
		commandField.setBackground(Color.BLACK);
		commandField.setForeground(Color.WHITE);
		commandField.setCaretColor(Color.WHITE);
		commandField.setFont(new Font("Monospaced",Font.PLAIN,14));
		commandPanel.add(commandField);
		commandField.addActionListener(new ButtonActionListener());
		JButton goButton = new JButton("Go");
		goButton.addActionListener(new ButtonActionListener());
		commandPanel.add(goButton);
		return commandPanel;
	}
	private static JPanel rightSide(){
		JPanel rightPanel = new JPanel(new GridLayout(3, 1));
		rightPanel.add(stats());
		rightPanel.add(navigation());
		rightPanel.add(inventory());
		return rightPanel;
	}
	private static JPanel stats(){
		JPanel bigStatsPanel = new JPanel();
		bigStatsPanel.setLayout(new FlowLayout());
		JPanel statsPanel = new JPanel();
		statsPanel.setLayout(new BoxLayout(statsPanel, BoxLayout.Y_AXIS));
		statsPanel.setPreferredSize(new Dimension(150,150));
		statsPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK), "Stats"));
		healthLabel = new JLabel("Health: 0/0");
		statsPanel.add(healthLabel);
		attackLabel = new JLabel("Attack: 0");
		statsPanel.add(attackLabel);
		defenseLabel = new JLabel("Defense: 0");
		statsPanel.add(defenseLabel);
		bigStatsPanel.add(statsPanel);
		return bigStatsPanel;
	}
	private static JPanel navigation(){
		JPanel navPanel = new JPanel(new GridLayout(3,3));
		ButtonActionListener bal = new ButtonActionListener();
		navPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK), "Navigation"));
		BasicArrowButton up = new BasicArrowButton(BasicArrowButton.NORTH);
		up.addActionListener(bal);
		BasicArrowButton left = new BasicArrowButton(BasicArrowButton.WEST);
		left.addActionListener(bal);
		JButton lookButton = new JButton("Look");
		lookButton.addActionListener(bal);
		BasicArrowButton right = new BasicArrowButton(BasicArrowButton.EAST);
		right.addActionListener(bal);
		BasicArrowButton down = new BasicArrowButton(BasicArrowButton.SOUTH);
		down.addActionListener(bal);
		JButton restartButton = new JButton("Restart");
		restartButton.addActionListener(bal);
		JButton helpButton = new JButton("Help");
		helpButton.addActionListener(bal);
		navPanel.add(restartButton);
		navPanel.add(up);
		navPanel.add(helpButton);
		navPanel.add(left);
		navPanel.add(lookButton);
		navPanel.add(right);
		navPanel.add(new JLabel());
		navPanel.add(down);
		return navPanel;
	}
	private static JScrollPane inventory(){ 
		invArea = new JTextArea();
		invArea.setEditable(false);
		invArea.setWrapStyleWord(true);
		invArea.setFont(new Font("Monospaced",Font.PLAIN,14));
		JScrollPane scrollInv = new JScrollPane(invArea);
		scrollInv.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK), "Inventory"));
		scrollInv.setPreferredSize(new Dimension(150,150));
		scrollInv.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		return scrollInv;
	}
	public static String getCommand(){
		String text = commandField.getText();
		commandField.setText("");
		return text;
	}
	public static void addToConsole(String add){
		textArea.append("\n" + add);
		GameLog.info(add);
		textArea.setCaretPosition(textArea.getText().length());
	}
	public static void updateStats(){
		healthLabel.setText("Health: " + player.getHealth() + "/" + player.getMaxHealth());
		attackLabel.setText("Attack: " + player.getAttack());
		defenseLabel.setText("Defense: " + player.getDefense());
	}
	public static void updateInventory(){
		invArea.setText(player.getInventoryDescription());
	}
	public static void resetConsole(){
		textArea.setText("                    _||_\n                   |    |\n           _||_    |    |    _||_\n,___,     |    |   |    |   |    |    ,___,\n[O.o]     |    |   |    |   |    |    [O.o]\n/)__)     |    |   |    |   |    |    (__(\\\n\"--\"-     |    |   |    |   |    |    -\"--\"-\n<<<<<<<<<<<<<<<<<<<<<<>>>>>>>>>>>>>>>>>>>>>>\n||                                        ||\n||         YOU JUST LOST THE GAME!        ||\n||                                        ||\n|| Prepare to get lost in a world full of ||\n|| fun and excitement as you take on      ||\n|| adventure unlike anything you have     ||\n|| ever played before!                    ||\n||                                        ||\n||----------------------------------------||\n||                                        ||\n|| TEAM GOKU:                             ||\n|| - Mike Henderson (MagicMike)           ||\n|| - Joseph Leffler (ChickenScissors)     ||\n|| - David Alexander (TheLonelyGhost)     ||\n|| - Jeffrey McDaniel (Zarlo)             ||\n||                                        ||\n<<<<<<<<<<<<<<<<<<<<<<>>>>>>>>>>>>>>>>>>>>>>\n");
	}
	public static void updateGUI() {
		updateInventory();
		updateStats();
	}
}
class ButtonActionListener implements ActionListener{
	public void actionPerformed(ActionEvent e){
		String command = GameGUI.getCommand();
		if(e.getSource() instanceof BasicArrowButton){
			BasicArrowButton arrow = (BasicArrowButton)e.getSource();
			if(arrow.getDirection() == BasicArrowButton.NORTH)
				CommandParser.parse("north");
			else if(arrow.getDirection() == BasicArrowButton.WEST)
				CommandParser.parse("west");
			else if(arrow.getDirection() == BasicArrowButton.EAST)
				CommandParser.parse("east");
			else if(arrow.getDirection() == BasicArrowButton.SOUTH)
				CommandParser.parse("south");
		}
		else
			if(e.getSource() instanceof JButton)
				if(((JButton)e.getSource()).getText().equals("Look"))
					CommandParser.parse("look");
				else if(((JButton)e.getSource()).getText().equals("Restart"))
					GameMap.restart();
				else if(((JButton)e.getSource()).getText().equals("Help"))
					CommandParser.parse("help");
				else
					CommandParser.parse(command);
			else
				CommandParser.parse(command);
	}
}