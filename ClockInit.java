package clock;

import javax.swing.JFrame;

public class ClockInit extends JFrame {

	public ClockInit(String name) {
		super(name);
	}
	
	public static void main(String[] args) {
		JFrame clockFrame = new ClockInit("Clock");
		clockFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		clockFrame.setSize(600, 600);
		clockFrame.add(new Clock());
		clockFrame.setVisible(true);
		clockFrame.setResizable(false);
	}
}