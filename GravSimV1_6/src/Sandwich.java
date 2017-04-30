package gravsimv1_3;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

public class Sandwich extends JPanel
{

	public boolean visible = false;
	
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
