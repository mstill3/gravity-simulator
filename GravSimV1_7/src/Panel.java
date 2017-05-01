package gravity;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Shape;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Area;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

//-----------------------------------------------------
// Team Goose: Matt Stillwell, Chris Good, Max Coplan
// Gravity Simulator
// UMBC Hackathon 
// 4/29/2017
//-----------------------------------------------------

public class Panel extends JPanel implements ActionListener
{
	public final double GRAVITY = 6.674;
	private double increment = 0.05;
	private static final long serialVersionUID = -8990664072317920212L;

	private Planet p1;
	private Planet p2;
	private int lineX, lineY;
	private Image back;
	private Sandwich sand;

	private Point dragPoint;

	//====================================
	// Constructor
	//====================================
	public Panel()
	{
		p1 = new Planet(10, 10, 10, .00569, .5, "earth");

		int iX=GameData.WIDTH/2;
		int iY=GameData.HEIGHT/2;
		p2 = new Planet(iX, iY, 20, 56.9, 1.5, "sun");

		sand = new Sandwich();

		dragPoint = new Point();
		back = null;
	}

	//====================================
	// Sets planet state
	//====================================
	public void setState(int state)
	{
		p1.setState(state);
	}

	//====================================
	// Returns planet state
	//====================================
	private int getState()
	{
		return p1.getState();
	}

	//====================================
	// Returns whether state is free
	//====================================
	public boolean isFree()
	{
		return getState() == Planet.FREE;
	}

	//====================================
	// Returns whether state is moving
	//====================================
	public boolean isMoving()
	{
		return getState() == Planet.MOVE;
	}

	//====================================
	// Returns whether state is dragging
	//====================================
	public boolean isDragging()
	{
		return getState() == Planet.DRAG;
	}

	//====================================
	// Sets drag line
	//====================================
	public void setDragPoint(int x, int y)
	{
		dragPoint = new Point(x, y);
	}

	//====================================
	// Moves 1st planet
	//====================================
	public void moveP1(int x, int y)
	{
		p1.setX(x);
		p1.setY(y);
	}

	//====================================
	// Moves 2nd planet
	//====================================
	public void moveP2(int x, int y)
	{
		p2.setX(x);
		p2.setY(y);
	}

	//====================================
	// Changes P1 to visible
	//====================================
	public void setP1Visible()
	{
		if(p2.getName().equals("newearth"))
			p2.setName("earth");
		p1.setVisible(true);
	}

	//====================================
	// Changes Sandwich to visible
	//====================================
	public void setSandwichVisible(boolean b)
	{
		sand.setVisible(b);
	}

	//====================================
	// Returns whether sandwich is visible
	//====================================
	public boolean isSandwichVisible()
	{
		return sand.isVisible();
	}

	//====================================
	// Sets current planet
	//====================================
	public void setP(Planet p)
	{
		p1 = p;
	}

	//====================================
	// Paints everything
	//====================================
	public void paint(Graphics gc)
	{

		if(intersection(p1.getShape(), p2.getShape()) && isFree())
		{
			p1.setVisible(false);
			//			p2.setName("newearth");
			p2.setMass(p2.getMass() + p1.getMass());
		}

		paintBackground(gc);
		//		paintGrid(gc);

		if(isDragging())
			paintDragLine(gc);

		if(p1.isVisible())
			p1.paint(gc);
		p2.paint(gc);

		sand.paint(gc);

		if(sand.isVisible())
		{
			paintReset(gc);
			paintVariables(gc);
			paintChoose(gc);
			paintCredits(gc);
			paintChooseSelected(gc);
		}

	}

	//====================================
	// Paints reset button
	//====================================
	public void paintReset(Graphics gc)
	{
		gc.setColor(new Color(192, 192, 192));
		gc.fillRect(GameData.WIDTH - 180, GameData.HEIGHT-115, 45, 20);
		gc.setColor(new Color(100, 100, 100));
		gc.fillRect(GameData.WIDTH - 179, GameData.HEIGHT-114, 43, 18);
		gc.setColor(Color.white);
		gc.drawString("Reset", GameData.WIDTH - 175, GameData.HEIGHT-100);
	}

	//====================================
	// Paints variables
	//====================================
	public void paintVariables(Graphics gc)
	{
		int difference = (int) hypotenuseDifference(p2, p1) - 20;
		gc.setColor(Color.white);
		if(p1.isVisible())
			gc.drawString(p1.getName().substring(0, 1).toUpperCase() + p1.getName().substring(1) + " (" + p1.getX() + ", " + p1.getY() + ")", GameData.WIDTH-190, GameData.HEIGHT - 45);
		else
			difference = 0;
		gc.drawString(p2.getName().substring(0, 1).toUpperCase() + p2.getName().substring(1) + " (" + p2.getX() + ", " + p2.getY() + ")", GameData.WIDTH-190, GameData.HEIGHT - 60);
		gc.drawString("Distance Apart: " + difference, GameData.WIDTH-190, GameData.HEIGHT - 75);

	}

	//====================================
	// Paints choose button
	//====================================
	public void paintChoose(Graphics gc)
	{
		gc.setColor(new Color(192, 192, 192));
		gc.fillRect(GameData.WIDTH - 180, 10, 65, 20);
		gc.setColor(new Color(100, 100, 100));
		gc.fillRect(GameData.WIDTH - 179, 11, 63, 18);
		gc.setColor(Color.white);
		gc.drawString("Choose", GameData.WIDTH - 170, 23);
	}

	//====================================
	// Paints credits
	//====================================
	public void paintCredits(Graphics gc)
	{
		gc.setColor(Color.white);
		gc.drawString("2017", 5, GameData.HEIGHT - 90);
		gc.drawString("Chris Good", 5, GameData.HEIGHT - 75);
		gc.drawString("Max Coplan", 5, GameData.HEIGHT - 60);
		gc.drawString("Matt Stillwell", 5, GameData.HEIGHT - 45);
	}

	//====================================
	// Paints background
	//====================================
	public void paintBackground(Graphics gc)
	{	
		try {                
			back = ImageIO.read(new File("res/sky.png"));
			back = back.getScaledInstance(GameData.WIDTH, GameData.HEIGHT,Image.SCALE_DEFAULT);
		} catch (IOException ex) {
			// handle exception...
			System.out.println("goose");
		}
		gc.drawImage(back, 0, 0, null);
	}

	private boolean chooseSelected = false;
	//====================================
	// Paints choices
	//====================================
	public void paintChooseSelected(Graphics gc)
	{
		if(!chooseSelected)
			return;

		int height = 80;

		String[] names = GameData.NAMES;
		for(String name : names)
		{
			gc.setColor(new Color(192, 192, 192));
			gc.fillRect(GameData.WIDTH - 180, height, 65, 20);
			gc.setColor(new Color(100, 100, 100));
			gc.fillRect(GameData.WIDTH - 179, height-1, 63, 18);
			gc.setColor(Color.white);
			gc.drawString(name, GameData.WIDTH - 170, height+12);
			height += 30;
		}
	}

	//====================================
	// Sets choice
	//====================================
	public void chooseSelected(boolean b)
	{
		chooseSelected = b;
	}

	//====================================
	// Paints grid
	//====================================
	public void paintGrid(Graphics gc)
	{
		gc.setColor(Color.black);

		for(int row = 5; row < GameData.WIDTH; row+=5)
			gc.drawLine(row, 0, row, GameData.HEIGHT);

		for(int col = 5; col < GameData.HEIGHT; col+=5)
			gc.drawLine(0, col, GameData.WIDTH, col);
	}

	//====================================
	// Paints drag line
	//====================================
	public void paintDragLine(Graphics gc)
	{
		int calibrateXDelta = 8;
		int calibrateYDelta = 30;

		lineX = (p1.getX() + p1.getLength()/2) - (dragPoint.getX() - calibrateXDelta);
		lineY = (p1.getY() + p1.getLength()/2) - (dragPoint.getY() - calibrateYDelta);

		if(p1.isVisible())
		{
			gc.setColor(Color.white);
			gc.drawLine(p1.getX() + p1.getLength()/2, p1.getY() + p1.getLength()/2,
					dragPoint.getX() - calibrateXDelta, dragPoint.getY() - calibrateYDelta);
		}
	}

	//====================================
	// Runs engine
	//====================================
	public void actionPerformed(ActionEvent e)
	{
		if(isFree() && p1.isVisible())
		{
			movePlanet();
			repaint();
		}
	}

	//====================================
	// Returns whether collision happened
	//====================================
	public boolean intersection(Shape shapeA, Shape shapeB)
	{
		Area areaA = new Area(shapeA);
		areaA.intersect(new Area(shapeB));
		return !areaA.isEmpty();
	}

	//====================================
	// Moves planets
	//====================================
	private void movePlanet()
	{
		p1.setX(p1x());
		p1.setY(p1y());
		p2.setX(p2x());
		p2.setY(p2y());
	}

	//====================================
	// Sets initial velocity
	//====================================
	public void setInitialVelocity()
	{
		this.v1x = lineX * 2;
		this.v1y = lineY * 2;
		this.v2x = 0;
		this.v2y = 0;
	}

	private double v1x = 0;
	private double v1y = 0;
	private double v2x = 0;
	private double v2y = 0;

	//====================================
	// Solves for x of 1st planet
	//====================================
	private int p1x()
	{
		double M = p2.getMass();
		double accelerationP1X;     // m/s^2

		accelerationP1X = -GRAVITY * M * xDifference(p2, p1) / (Math.pow(hypotenuseDifference(p2, p1), 3/2));		
		v1x += accelerationP1X * increment;

		return (int) (p1.getX() + v1x * increment + .5);
	}

	//====================================
	// Solves for y of 1st planet
	//====================================
	private int p1y()
	{
		double M = p2.getMass();
		double a1y;     //m/s^2

		a1y = -GRAVITY * M * yDifference(p2, p1) / (Math.pow(hypotenuseDifference(p2, p1), 3/2));
		v1y += a1y * increment;

		return (int) (p1.getY() + v1y * increment +.5);
	}

	//====================================
	// Solves for x of 2nd planet
	//====================================
	private int p2x()
	{
		double m = p1.getMass();
		double ay;      //m/s^2

		ay = -GRAVITY * m * xDifference(p1, p2) / (Math.pow(hypotenuseDifference(p1, p2), 3/2));
		v2x += ay * increment;

		return (int) (p2.getX() + v2x * increment +.5);
	}

	//====================================
	// Solves for y of 2nd planet
	//====================================
	private int p2y()
	{
		double m = p1.getMass();
		double ay;      //m/s^2

		ay = -GRAVITY * m * yDifference(p1, p2) / (Math.pow(hypotenuseDifference(p1, p2), 3/2));
		v2y += ay * increment;

		return (int) (p2.getY() + v2y * increment +.5);

	}

	//====================================
	// Solves for x change between planets
	//====================================
	public static double xDifference(Planet p2, Planet p1)
	{
		return (p1.getX() + p1.getLength()/2) - (p2.getX() + p2.getLength()/2);
	}

	//====================================
	// Solves for y change between planets
	//====================================
	public static double yDifference(Planet p2, Planet p1)
	{
		return (p1.getY() + p1.getLength()/2) - (p2.getY() + p2.getLength()/2);
	}

	//====================================
	// Solves for h change between planets
	//====================================
	public static double hypotenuseDifference(Planet p2, Planet p1)
	{
		return Math.sqrt(Math.pow(xDifference(p2, p1), 2) + Math.pow(yDifference(p2, p1), 2));
	}

}
