package MainGame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class MenuUI extends JFrame {
	private Container contents;

	private JLabel gameImage;

	// top buttons
	private JButton btnRule;
	private JButton btnQuit;
	private JButton btnInfo;
	private JButton btnStatistics;

	// center buttons
	private JButton btnStart;

	// bottom buttons
	private JButton btnStore;
	private JButton btnDraw;
	private JButton btnBackage;

	private String ID;
	private ArrayList<User> users;
	private User user;

	public MenuUI(String ID) {
		super("Menu");
		contents = getContentPane();
		contents.setLayout(null); // I didn't use BorderLayout since I want to resize buttons

		// Top Pane:
		btnRule = new JButton("");
		btnStatistics = new JButton("");
		btnInfo = new JButton("");
		btnQuit = new JButton("");

		btnRule.setIcon(new ImageIcon(getClass().getResource("Picture/RuleButton.png")));
		btnStatistics.setIcon(new ImageIcon(getClass().getResource("Picture/StatisticsButton.png")));
		btnInfo.setIcon(new ImageIcon(getClass().getResource("Picture/InfoButton.png")));
		btnQuit.setIcon(new ImageIcon(getClass().getResource("Picture/QuitButton.png")));

		contents.add(btnRule);
		contents.add(btnInfo);
		contents.add(btnStatistics);
		contents.add(btnQuit);

		btnRule.setBounds(5, 5, 40, 40);
		btnInfo.setBounds(50, 5, 40, 40);
		btnStatistics.setBounds(95, 5, 40, 40);
		btnQuit.setBounds(370, 5, 40, 40);

		// Center Pane:
		gameImage = new JLabel(new ImageIcon(getClass().getResource("Picture/Game.png")));
		contents.add(gameImage);
		gameImage.setBounds(0, 100, 420, 160);

		btnStart = new JButton("");
		btnStart.setIcon(new ImageIcon(getClass().getResource("Picture/StartButton.png")));
		contents.add(btnStart);
		btnStart.setBounds(120, 300, 180, 80);

		// Bottom Pane:
		btnStore = new JButton("");
		btnBackage = new JButton("");
		btnDraw = new JButton("");

		btnStore.setIcon(new ImageIcon(getClass().getResource("Picture/StoreButton.png")));
		btnBackage.setIcon(new ImageIcon(getClass().getResource("Picture/BackageButton.png")));
		btnDraw.setIcon(new ImageIcon(getClass().getResource("Picture/DrawButton.png")));

		contents.add(btnStore);
		contents.add(btnBackage);
		contents.add(btnDraw);

		btnStore.setBounds(20, 450, 90, 90);
		btnBackage.setBounds(165, 460, 90, 90);
		btnDraw.setBounds(300, 450, 100, 100);

		// ActionListener:
		ButtonHandler bh = new ButtonHandler();
		btnRule.addActionListener(bh);
		btnInfo.addActionListener(bh);
		btnStatistics.addActionListener(bh);
		btnQuit.addActionListener(bh);
		btnStart.addActionListener(bh);
		btnStore.addActionListener(bh);
		btnBackage.addActionListener(bh);
		btnDraw.addActionListener(bh);

		// The container:
		Color menuColor = new Color(57, 19, 0); // RGB of background color
		this.setBackground(menuColor); // color of the top Frame
		this.getContentPane().setBackground(menuColor); // real menu background color
		setSize(420, 600);
		this.setLocationRelativeTo(null);
		setResizable(false);
		setVisible(true);

		this.ID = ID;
		findUser();
	}

	public void login() {
		FileIO io = new FileIO();
		ArrayList<User> users = io.readObjFile("Users.dat");

		String userName = JOptionPane.showInputDialog("Enter ID:");
		boolean found = false;
		int index = 0;
		for (int i = 0; i < users.size(); i++) {
			if (ID.equals(userName)) {
				JOptionPane.showMessageDialog(null, "This is P1's ID", "Message", JOptionPane.INFORMATION_MESSAGE);
				return;
			} else if ((users.get(i).getUserName()).equals(userName)) { // username is found
				index = i;
				found = true; // ID is existed
			}
		}

		if (found) {
			String password = JOptionPane.showInputDialog("Enter password:");
			if ((users.get(index).getPassword()).equals(password)) {
				GameUI game = new GameUI(ID, userName);
				game.setDefaultCloseOperation(EXIT_ON_CLOSE);
				dispose();
			} else {
				JOptionPane.showMessageDialog(null, "Incorrect password", "Message", JOptionPane.INFORMATION_MESSAGE);
			}
		} else {
			JOptionPane.showMessageDialog(null, "Incorrect username", "Message", JOptionPane.INFORMATION_MESSAGE);
		}
	}

	public JFrame getUI() {
		return this;
	}

	public void findUser() {
		FileIO io = new FileIO();
		users = io.readObjFile("Users.dat");
		for (int i = 0; i < users.size(); i++) {
			if (ID.equals(users.get(i).getUserName())) {
				user = users.get(i);
			}
		}
	}

	private class ButtonHandler implements ActionListener {
		public void actionPerformed(ActionEvent ae) {
			if (ae.getSource() == btnInfo) {
				Info info = new Info();
				info.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
			} else if (ae.getSource() == btnRule) {
				Rule rule = new Rule();
				rule.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
			} else if (ae.getSource() == btnStatistics) {
				Statistics s = new Statistics();
				s.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
			} else if (ae.getSource() == btnQuit) {
				dispose();
				LoginUI login = new LoginUI(); // return to login UI
				login.setDefaultCloseOperation(EXIT_ON_CLOSE);
			} else if (ae.getSource() == btnStart) {
				int ans = JOptionPane.showConfirmDialog(null, "Does Player 2 wants to login?", "Confirm",
						JOptionPane.YES_NO_OPTION);
				if (ans == JOptionPane.YES_OPTION) {
					login();
				} else if (ans == JOptionPane.NO_OPTION) {
					GameUI game = new GameUI(ID, "Guest");
					game.setDefaultCloseOperation(EXIT_ON_CLOSE);
					dispose();
				}
			} else if (ae.getSource() == btnStore) {
				StoreUI store = new StoreUI(getUI(), ID);
				store.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
			} else if (ae.getSource() == btnBackage) {
				PackageUI bag = new PackageUI(getUI(), ID);
				bag.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
			} else if (ae.getSource() == btnDraw) {
				DrawUI draw = new DrawUI(getUI(), ID);
				draw.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
			}

		}
	}

	private class Statistics extends JFrame {
		private int XMAX = 420;
		private int YMAX = 600;

		private int[] data = new int[8];
		private int barSize = 50;
		private int xStart;

		public Statistics() {
			super("Statistics of chesses in terms of alive times");
			data = user.getAliveTimes();

			setSize(420, 600);
			this.setLocationRelativeTo(null);
			setResizable(false);
			setVisible(true);
		}

		@Override
		public void paint(Graphics g) {
			super.paint(g);
			xStart = 10;
			g.setColor(Color.BLACK);
			for (int i = 0; i < data.length; i++) {
				g.fillRect(xStart, YMAX - 25 - data[i], barSize - 5, data[i]);
				g.drawString(String.valueOf(data[i]), xStart + barSize / 3, YMAX - 5);
				xStart += barSize;
			}
		}
	}

	private class Info extends JDialog {
		private Container contents;
		private JTextField txtID, txtCoins, txtGame, txtWin, txtWinRate;

		public Info() {
			super(getUI(), "Info", true);
			contents = getContentPane();
			contents.setLayout(new GridLayout(5, 1));

			findUser();

			txtID = new JTextField("ID: " + ID);
			txtCoins = new JTextField("Coins: " + user.getCoins());
			txtGame = new JTextField("Total Fields: " + user.getGameTimes());
			txtWin = new JTextField("Winning Times: " + user.getWinnerTimes());
			txtWinRate = new JTextField("Winning Rate: " + user.getWinningRate() + "%");

			txtID.setEditable(false);
			txtCoins.setEditable(false);
			txtGame.setEditable(false);
			txtWin.setEditable(false);
			txtWinRate.setEditable(false);

			txtID.setBackground(null);
			txtCoins.setBackground(null);
			txtGame.setBackground(null);
			txtWin.setBackground(null);
			txtWinRate.setBackground(null);

			Font mf = new Font("Helvetica", Font.BOLD, 25);

			txtID.setFont(mf);
			txtCoins.setFont(mf);
			txtGame.setFont(mf);
			txtWin.setFont(mf);
			txtWinRate.setFont(mf);

			contents.add(txtID);
			contents.add(txtCoins);
			contents.add(txtGame);
			contents.add(txtWin);
			contents.add(txtWinRate);

			setSize(420, 600);
			this.setLocationRelativeTo(null);
			setResizable(false);
			setVisible(true);
		}
	}

	private class Rule extends JDialog {
		private Container contents;
		private JTextArea textA;
		private String text;

		public Rule() {
			super(getUI(), "Rule", true);
			contents = getContentPane();
			contents.setLayout(new FlowLayout());
			textA = new JTextArea("");
			text = "This is an animal battle chess game. In the game, there is a 4 x 4 chess board with 16 covered checkers.\n"
					+ "Players need to uncover checkers and kill all checkers of the opponent to win the game.\n"
					+ "Two players move alternatively. In each turn, they have 30 seconds to consider.\n"
					+ "Each player has 8 animal checkers: Elephant, Lion, Tiger, Leopard, Wolf, Dog, Cat, Rat.\n"
					+ "In usual, the relationship between animals is: Elephant > Lion > Tiger > Leopard > Wolf > Dog > Cat > Rat.\n"
					+ "Animals on the left can kill all animals on the right. For example, dog can kill cat and rat when touching.\n"
					+ "An exception is: when the rat and the elephant touches, the rat will kill the elephant.\n"
					+ "Magma will spread after 20 turns, that can kill any checker.\n"
					+ "To avoid the negative competition, if no chekcer is killed in 10 turns, magma will immediately begin spreading.";

			textA.setText(text);
			textA.setEditable(false);
			textA.setBackground(null);

			contents.add(textA);

			setSize(730, 180);
			this.setLocationRelativeTo(null);
			setResizable(false);
			setVisible(true);
		}

	}
}
