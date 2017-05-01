package gravity;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JFrame;
import javax.swing.Timer;

//-----------------------------------------------------
// Team Goose: Matt Stillwell, Chris Good, Max Coplan
// Gravity Simulator
// UMBC Hackathon 
// 4/29/2017
//-----------------------------------------------------

public class Frame extends JFrame
{

	private static final long serialVersionUID = -8008200310107126788L;

	private Panel panel;

	private Timer timer;
	private int delay = 25;

	//====================================
	// Constructor
	//====================================
	public Frame()
	{
		setTitle("Gravity Simulator");
		setIconImage(Toolkit.getDefaultToolkit().createImage("res/earth.png"));
		panel = new Panel();
		timer = new Timer(delay, panel);

		panel.setInitialVelocity();

		setLocation(GameData.FRAMEX, GameData.FRAMEY);
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		setSize(GameData.WIDTH, GameData.HEIGHT);
		setVisible(true);

		getContentPane().add(panel);
		addMouseListener(new MouseListener()
		{
			public static final int RIGHT_CLICK = 3;

			public void mouseReleased(MouseEvent e)
			{
				if(panel.isDragging())
				{
					panel.setInitialVelocity();
					panel.setState(Planet.FREE);
					timer.start();
				}
				//starts max code timer

			}

			public void mousePressed(MouseEvent e)
			{
				if(e.getButton() == RIGHT_CLICK)
				{
					movePlanet2(e);
				}
			}

			public void mouseExited(MouseEvent e)
			{}

			public void mouseEntered(MouseEvent e)
			{}

			public void mouseClicked(MouseEvent e)
			{

				if(e.getX() > GameData.WIDTH - 90  &&
						e.getY() < 100)
				{
					if(panel.isSandwichVisible())
						panel.setSandwichVisible(false);
					else
						panel.setSandwichVisible(true);
					repaint();
				}
				else if(panel.isSandwichVisible())
				{
					if(e.getX() > GameData.WIDTH - 160 && e.getY() < 60)
						panel.chooseSelected(true);
					else if(e.getX() > GameData.WIDTH-170 && e.getX() < GameData.WIDTH-105 &&
							e.getY() > 80 && e.getY() < GameData.HEIGHT - 305)
					{
						changePlanet(e);
					}
					else if(e.getX() > GameData.WIDTH - 175 && e.getX() < GameData.WIDTH - 125 &&
							e.getY() > GameData.HEIGHT - 85 && e.getY() < GameData.HEIGHT - 65)
					{
						resetPlanets(e);
					}
				}
			}

		});

		addMouseMotionListener(new MouseMotionListener()
		{

			public void mouseMoved(MouseEvent e)
			{

				if(panel.isDragging())
				{
					panel.setState(Planet.FREE);
					timer.start();
					panel.setDragPoint(0, 0);
				}
				else
				{
					if(panel.isMoving())
					{
						movePlanet1(e);
					}
					else
					{
						panel.setState(Planet.FREE);
						timer.start();
					}
				}
			}

			public void mouseDragged(MouseEvent e)
			{				
				panel.setState(Planet.DRAG);
				timer.stop();
				panel.setDragPoint(e.getX(), e.getY());
				panel.repaint();
			}

		});

	}

	//====================================
	// Moves 1st planet
	//====================================
	public void movePlanet1(MouseEvent e)
	{
		int calibrateX = 12;
		int calibrateY = 36;

		panel.moveP1(e.getX() - calibrateX, e.getY() - calibrateY);
		panel.repaint();
	}

	//====================================
	// Moves 2nd planet
	//====================================
	public void movePlanet2(MouseEvent e)
	{
		int calibrateX = 12;
		int calibrateY = 36;

		panel.moveP2(e.getX() - calibrateX, e.getY() - calibrateY);
		panel.repaint();
	}

	//====================================
	// Displays choices
	//====================================
	public void changePlanet(MouseEvent e)
	{
		int height = 105;
		int mouseHeight = e.getY();
		for(String name:GameData.NAMES)
		{
			if(mouseHeight > height && mouseHeight < height + 20)
			{
				java.util.Random rand=new java.util.Random();
				panel.setP(new Planet(10, 10, 2, rand.nextDouble() + .001, .3, name));
				panel.chooseSelected(false);
			}
			height += 30;
		}
	}

	//====================================
	// Resets planets
	//====================================
	public void resetPlanets(MouseEvent e)
	{
		panel.setP1Visible();
		panel.setState(Planet.MOVE);
		panel.moveP2(GameData.WIDTH/2, GameData.HEIGHT/2);
		timer.stop();
		movePlanet1(e);
	}

}
