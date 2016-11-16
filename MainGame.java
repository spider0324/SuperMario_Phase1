
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.Timer;

public class MainGame implements ActionListener, MouseListener, KeyListener
{
	public static MainGame maingame;	
	public Scanner sc;
	public final int WIDTH = 800, HEIGHT = 800;
	public Renderer renderer;
	public Player player = new Player();
	public Map map;
	public Map map0 = new Map("map0");
	public static int ticks;
	public boolean gameOver = false, started;
	public static final int dt = 10; //timer unit in miliseconds
	
	public int blockType; //type of block Mario touches
	
	public MainGame()
	{
		JFrame jf = new JFrame();
		
		Timer timer = new Timer(dt, this);

		//timer.start();
		map0.loadMap();
		
		renderer = new Renderer();

		jf.add(renderer);
		jf.setTitle("Super Mario");
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jf.setSize(WIDTH, HEIGHT);
		jf.addMouseListener(this);
		jf.addKeyListener(this);
		jf.setResizable(false);
		jf.setVisible(true);

		timer.start();
		//map0.loadMap();
		//renderer.repaint();
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		/*
		if(player.yMove || !player.yMove)
		{
			player.yD = player.yBefJump -player.yVelDefault * player.yJumpTicks * 0.001 * dt + 0.5 * 10 * Math.pow(player.yJumpTicks * 0.001 * dt, 2.0);
			player.yJumpTicks++;
			player.y = (int) player.yD;
		}
		*/
		
		renderer.repaint();
		
		ticks++;
	}

	public void repaint(Graphics g)
	{
		g.setFont(new Font("Times New Roman", 1, 20));
		map = map0;
		/*
		for(int i = 0; i < map.xDisMax; i++)
		{
			for(int j = 0; j < map.yDisMax; j++)
			{
				//g.drawString("" + map.block[i][j], i * 20 + 20, j * 20 + 20);
				switch(map.block[i][j])
				{
				case 1 :
					g.fillRect(20 * i + 1, 20 * j + 1, 18, 18);
					break;
				default :
					break;
				}
			}
		}
		*/
		
		g.setFont(new Font("Arial", 1, 15));
		g.drawString("coin : " + player.coin, 100, 10);
		g.drawString("health : " + player.health, 200, 10);
		
		for(Rectangle block : map.solidBlocks)
		{
			g.setColor(Color.black);
			g.fillRect(block.x, block.y, block.width, block.height);
		}
		for(Rectangle block : map.itemBlocksCoin)
		{
			g.setColor(new Color(165, 42, 42));
			g.fillRect(block.x, block.y, block.width, block.height);
			g.setColor(Color.black);
			g.drawString("?", block.x + block.width / 2, block.y + block.height / 2);
		}
		for(Rectangle block : map.itemBlocksPlusHealth)
		{
			g.setColor(new Color(165, 42, 42));
			g.fillRect(block.x, block.y, block.width, block.height);
			g.setColor(Color.black);
			g.drawString("?", block.x + block.width / 2, block.y + block.height / 2);
		}
		for(Rectangle block : map.coins)
		{
			g.setColor(Color.yellow);
			g.fillOval(block.x + block.width / 3 + 1, block.y + 1, block.width * 2 / 3, block.height - 2);
			g.setColor(Color.orange);
			g.drawOval(block.x + block.width / 3 + 1, block.y + 1, block.width * 2 / 3, block.height - 2);
			g.drawOval(block.x + block.width / 3 + 3, block.y + 3, block.width * 2 / 3 - 4, block.height - 6);
		}
		for(Goomba block : map.goombas)
		{
			g.setColor(Color.red);
			g.fillRect(block.mobRect.x, block.mobRect.y, block.mobRect.width, block.mobRect.height);
			g.setColor(Color.black);
			g.drawRect(block.mobRect.x, block.mobRect.y, block.mobRect.width, block.mobRect.height);
		}
		for(Koopa block : map.koopas)
		{ 
			g.setColor(Color.green);
			g.fillRect(block.mobRect.x, block.mobRect.y, block.mobRect.width, block.mobRect.height);
			g.setColor(Color.black);
			g.drawRect(block.mobRect.x, block.mobRect.y, block.mobRect.width, block.mobRect.height);
		}
		for(KoopaShell block : map.koopashells)
		{
			g.setColor(Color.green);
			g.fillRect(block.mobRect.x, block.mobRect.y, block.mobRect.width, block.mobRect.height);
			g.setColor(Color.black);
			g.drawRect(block.mobRect.x, block.mobRect.y, block.mobRect.width, block.mobRect.height);
		}

		
		//player.mobRect = new Rectangle(player.mobRect.x, player.mobRect.y, 18, 18);

		
		if(player.visibility)
		{
			g.setColor(Color.blue);
			g.fillRect(player.mobRect.x, player.mobRect.y, player.mobRect.width, player.mobRect.height);
		}
		else if(!player.visibility)
		{
			if(MainGame.ticks >= player.invisStartTick && MainGame.ticks < player.invisStartTick + player.invisBleepPeriod / MainGame.dt
					|| MainGame.ticks >= player.invisStartTick + 2 * player.invisBleepPeriod / MainGame.dt && MainGame.ticks < player.invisStartTick + 3 * player.invisBleepPeriod / MainGame.dt
					|| MainGame.ticks >= player.invisStartTick + 4 * player.invisBleepPeriod / MainGame.dt && MainGame.ticks < player.invisStartTick + 5 * player.invisBleepPeriod / MainGame.dt)
			{
				g.setColor(Color.cyan);
				g.fillRect(player.mobRect.x, player.mobRect.y, player.mobRect.width, player.mobRect.height);
			}
			else
			{
				g.setColor(Color.blue);
				g.fillRect(player.mobRect.x, player.mobRect.y, player.mobRect.width, player.mobRect.height);
			}
		}
		
		switch(player.checkIntersect(map))
		{
		case 1 :
			g.setColor(Color.green);
			g.fillRect(60, 100, 10, 10);
			break;
		case 2 :
			g.setColor(Color.cyan);
			g.fillRect(80, 100, 10, 10);
			break;
		case 20 :
			g.setColor(Color.red);
			g.fillRect(100, 100, 10, 10);
			break;
		case 21 :
			g.setColor(Color.gray);
			g.fillRect(100, 110, 10, 10);
		default :
			g.setColor(Color.yellow);
			g.fillRect(120, 100, 10, 10);
			break;
		}
		//blockType = player.checkIntersect(map);
		for(Goomba block : map.goombas)
		{
			block.checkIntersect(map);
		}
		for(Koopa block : map.koopas)
		{
			block.checkIntersect(map);
		}
		for(KoopaShell block : map.koopashells)
		{
			block.checkIntersect(map);
		}
		
		player.moveVert();
		player.moveHori();
		for(Goomba block : map.goombas)
		{
			block.moveVert();
			block.moveHori();
		}
		for(Koopa block : map.koopas)
		{
			block.moveVert();
			block.moveHori();
		}
		for(KoopaShell block : map.koopashells)
		{
			block.moveVert();
			block.moveHori();
		}
		
	}

	public static void main(String[] args)
	{
		maingame = new MainGame();
	}

	@Override
	public void mouseClicked(MouseEvent e)
	{	
	}
	
	@Override
	public void mousePressed(MouseEvent e)
	{
	}

	@Override
	public void mouseReleased(MouseEvent e)
	{
	}

	@Override
	public void mouseEntered(MouseEvent e)
	{
	}

	@Override
	public void mouseExited(MouseEvent e)
	{
	}

	@Override
	public void keyTyped(KeyEvent e)
	{
		
	}
	
	@Override
	public void keyReleased(KeyEvent e)
	{
		if(e.getKeyCode() == KeyEvent.VK_RIGHT)
		{
			//player.xVelD = 0.0;
			player.xMoveRight = false;
		}
		if(e.getKeyCode() == KeyEvent.VK_LEFT)
		{
			//player.xVelD = 0.0;
			player.xMoveLeft = false;
		}
		if(e.getKeyCode() == KeyEvent.VK_DOWN)
		{
			//player.yVelD = 0.0;
			
		}
		if(e.getKeyCode() == KeyEvent.VK_UP)
		{
			//player.yVelD = 0.0;
			player.yMove = false;
		}
	}
	
	@Override
	public void keyPressed(KeyEvent e)
	{
		//double defaultVel = 100.0; //pixels per second
		if(e.getKeyCode() == KeyEvent.VK_RIGHT)
		{
			//player.xVelD = defaultVel;
			player.xMoveRight = true;
		}
		
		if(e.getKeyCode() == KeyEvent.VK_LEFT)
		{
			//player.xVelD = -defaultVel;
			player.xMoveLeft = true;
		}
		
		if(e.getKeyCode() == KeyEvent.VK_DOWN)
		{
			//player.yVelD = defaultVel;
		}
		
		if(e.getKeyCode() == KeyEvent.VK_UP)
		{
			//player.yVelD = -defaultVel;
			player.yMove = true;
		}
	}

}