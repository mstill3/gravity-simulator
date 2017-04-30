package gravsimv1_3;
import java.awt.Color;
//import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
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

public class Planet extends JPanel
{

	private static final long serialVersionUID = -1170310317190051853L;

	public static final int MOVE = 0;  // follows mouse
	public static final int DRAG = 1;  // draws drag line
	public static final int FREE = 2;  // moves with gravity

	private int x, y, length;
	private int state;                 // what the planet is doing

	private String name;
	protected Image image;
	private double mass;
        private double scale;

	//====================================
	// Constructor
	//====================================
	public Planet(int x, int y, int length, double mass, double scale, String name)
	{
		setX(x);
		setY(y);
                setScale(scale);
                setLength(length);
		//		setColor(color);
		setName(name);
                setMass(mass);
                
	}

	//====================================
	// Returns length
	//====================================
	public int getLength()
	{
		return length;
	}

	//====================================
	// Sets length
	//====================================
	public void setLength(int length)
	{
		this.length = length;
	}

	//====================================
	// Returns x
	//====================================
	public int getX()
	{
		return x;
	}

	//====================================
	// Returns y
	//====================================
	public int getY()
	{
		return y;
	}

	//====================================
	// Sets x
	//====================================
	public void setX(int x)
	{
		this.x = x;
	}

	//====================================
	// Sets y
	//====================================
	public void setY(int y)
	{
		this.y = y;
	}
        
        public void setScale(double scale){
            this.scale=scale;
        }

	//====================================
	// Returns state
	//====================================
	public int getState()
	{
		return state;
	}

	//====================================
	// Sets state
	//====================================
	public void setState(int state)
	{
		this.state = state;
	}

	//====================================
	// Sets name
	//====================================
	public void setName(String name)
	{
		this.name = name;
		setImage(name);
	}

	private void setImage(String name)
	{
		image = null;
		try {                
			image = ImageIO.read(new File(name + ".png"));
			image=resize(image,scale);
		} catch (IOException ex) {
			System.out.println("image not found");
		}
                setLength(image.getHeight(null));
	}
 
	public Image resize(Image img, double scale)
	{
            return img.getScaledInstance((int)(img.getWidth(null) * scale), (int)(img.getHeight(null) * scale), Image.SCALE_DEFAULT);
	}

	//====================================
	// Returns name
	//====================================
	public String getName()
	{
		return name;
	}

	//====================================
	// Returns shape
	//====================================
	public Rectangle getShape()
	{
            int r=length/2;
            r+=r/Math.sqrt(2);
            return new Rectangle(getX(), getY(), r, r);
	}
	
	public double getMass()
	{
		return mass;
	}
	
	public void setMass(double mass)
	{
		this.mass = mass;
	}
	
	public double getR()
	{
		return Math.pow(Math.pow(x,2)+Math.pow(y, 2), (1/2));
	}

	//====================================
	// Paints planet
	//====================================
	public void paint(Graphics g)
	{
		g.drawImage(image, x, y, null);
	}

}
