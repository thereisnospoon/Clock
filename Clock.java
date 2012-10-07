package clock;

import java.awt.geom.AffineTransform;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.RenderingHints;
import java.awt.BasicStroke;
import java.awt.Font;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import java.util.GregorianCalendar;

import javax.swing.JPanel;
import javax.swing.Timer;

/**
  * Clock class implements all necessary functionality
  *
  * @author thereisnospoon
  *
  */

public class Clock extends JPanel implements ActionListener {
	
	//Arrow radiuses
	private final int secRad = 230;
	private final int hourRad = 100;
	private final int minRad = 180;					

	private final int centerX = 300;				//Coordinates of center
	private final int centerY = 290;				// of center

	//current time
	private int currentSec;
	private int currentMin;
	private int currentHour;

	private AffineTransform defaultTransform = new AffineTransform();
	private Timer timer; 

	private RenderingHints rh;
	private BasicStroke
						secArr,
						minArr,
						hourArr,
						strokeMarks;

	private Font font;

	public Clock() {

		rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		rh.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		rh.put(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

		defaultTransform.translate(centerX, centerY);
		defaultTransform.rotate(-Math.PI/2);

		secArr = new BasicStroke(1, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);
		minArr = new BasicStroke(3, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);
		hourArr = new BasicStroke(5, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);
		strokeMarks = new BasicStroke(8, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);

		font = new Font("Ubuntu Mono", Font.PLAIN, 20);

		timer = new Timer(1000, this);
		timer.start();
	}

	private void drawSecondArrow(Graphics2D g2d) {
		g2d.setStroke(secArr);
		g2d.setTransform(defaultTransform);
		GregorianCalendar calendar = new GregorianCalendar();
		g2d.rotate(
			currentSec*2*Math.PI/60);
		g2d.drawLine(0, 0, secRad, 0);
	}

	private void drawHourArrow(Graphics2D g2d) {
		g2d.setStroke(hourArr);
		g2d.setTransform(defaultTransform);
		GregorianCalendar calendar = new GregorianCalendar();
		g2d.rotate(
			currentHour*2*Math.PI/12);
		g2d.drawLine(0, 0, hourRad, 0);
	}

	private void drawMinArrow(Graphics2D g2d) {
		g2d.setStroke(minArr);
		g2d.setTransform(defaultTransform);
		GregorianCalendar calendar = new GregorianCalendar();
		g2d.rotate(
			currentMin*2*Math.PI/60);
		g2d.drawLine(0, 0, minRad, 0);
	}

	private void drawClockFace(Graphics2D g2d) {
		g2d.setTransform(defaultTransform);
		g2d.setStroke(hourArr);
		g2d.drawOval(-3, -3, 6, 6);
		g2d.drawOval(-270, -270, 540, 540);

		g2d.setStroke(strokeMarks);
		g2d.setTransform(defaultTransform);
		for (int i = 0; i < 12; i++) {
			if (i % 3 == 0) {
				g2d.drawLine(200, 0, 260, 0);
			} else {
				g2d.drawLine(225, 0, 260, 0);
			}
			g2d.rotate(Math.PI/6);
		}
	}

	private void drawNumbTime(Graphics2D g2d) {
		g2d.setTransform(defaultTransform);
		g2d.setFont(font);
		String
			hour,
			second,
			min;
		
		if (currentHour < 10) {
			hour = "0" + new Integer(currentHour).toString();
		} else {
			hour = new Integer(currentHour).toString();
		}

		if (currentMin < 10) {
			min = "0" + new Integer(currentMin).toString();
		} else {
			min = new Integer(currentMin).toString();
		}
		
		if (currentSec < 10) {
			second = "0" + new Integer(currentSec).toString();
		} else {
			second = new Integer(currentSec).toString();
		}

		g2d.rotate(Math.PI/2);
		g2d.drawString(hour + ":" + min + ":" + second, -40, 35);

	}

	public void paint(Graphics g) {
		super.paint(g);

		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHints(rh);

		GregorianCalendar calendar = new GregorianCalendar();
		currentSec = calendar.get(calendar.SECOND);
		currentMin = calendar.get(calendar.MINUTE);
		currentHour = calendar.get(calendar.HOUR_OF_DAY);

		drawClockFace(g2d);
		drawHourArrow(g2d);
		drawSecondArrow(g2d);
		drawMinArrow(g2d);
		drawNumbTime(g2d);
	}

	public void actionPerformed(ActionEvent e) {
		repaint();
	}
}