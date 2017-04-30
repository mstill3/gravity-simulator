package gravity;

//-----------------------------------------------------
// Team Goose: Matt Stillwell, Chris Good, Max Coplan
// Gravity Simulator
// UMBC Hackathon 
// 4/29/2017
//-----------------------------------------------------

public class Point
{

	private int x, y;

	//====================================
	// No-args constructor
	//====================================
	public Point()
	{
		setX(0);
		setY(0);
	}

	//====================================
	// Constructor
	//====================================
	public Point(int x, int y)
	{
		setX(x);
		setY(y);
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
	// Returns String Representation
	//====================================
	public String toString()
	{
		return "(" + getX() + ", " + getY() + ")";
	}

}
