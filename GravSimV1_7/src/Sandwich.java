package gravity;

//-----------------------------------------------------
// Team Goose: Matt Stillwell, Chris Good, Max Coplan
// Gravity Simulator
// UMBC Hackathon 
// 4/29/2017
//-----------------------------------------------------

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

public class Sandwich extends JPanel
{

	private static final long serialVersionUID = 7729198643413133296L;
	private boolean visible;

	//====================================
	// Constructor
	//====================================
	public Sandwich()
	{
		visible = false;
	}

	//====================================
	// Sets visible
	//====================================
	public void setVisible(boolean b)
	{
		visible = b;
	}

	//====================================
	// Returns visibility
	//====================================
	public boolean isVisible()
	{
		return visible;
	}

	//====================================
	// Paints sandwich
	//====================================
	public void paint(Graphics gc)
	{

		gc.setColor(Color.gray);
		if(visible)
		{
			gc.fillRect(GameData.WIDTH - 200, 0, 200, GameData.HEIGHT);
		}

		gc.setColor(new Color(192, 192, 192));
		gc.fillRect(GameData.WIDTH - 90, 10, 65, 20);
		gc.setColor(new Color(100, 100, 100));
		gc.fillRect(GameData.WIDTH - 89, 11, 63, 18);
		gc.setColor(Color.white);
		gc.drawString("Settings", GameData.WIDTH - 80, 23);
	}
}
