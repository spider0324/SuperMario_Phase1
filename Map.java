
//Map Type
//0 : bedrock, 1 : Solid Block, 2 : ? with coin, 3 : ? with plusHealth mushroom/flower, 4 : ? with plusLife mushroom, 5 : ? with gigantic mushroom
//10 : coin, 11 : fireBall, 12 : plusHealth mushroom, 13 : plusHealth flower, 14 : plusLife mushroom
//20 : Goomba, 21 : Koopa, 22 : Koopa Shell, 

import java.awt.Rectangle;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Map
{
	public String mapTitle;
	//"Dis" indicates Discrete(Quantized) unit by blocks (in Block class)
	public int xDisMax, yDisMax; //Maximum number of blocks per row and column
	public int[][] block;
	public static int defaultBlockSize = 20;
	//@SuppressWarnings("unchecked")
	//public ArrayList<Rectangle> blocks = new ArrayList<Rectangle>();
	public ArrayList<Rectangle> bedrocks = new ArrayList<Rectangle>();
	public ArrayList<Rectangle> solidBlocks = new ArrayList<Rectangle>(); //COMPLETE!!!
	public ArrayList<Rectangle> itemBlocksCoin = new ArrayList<Rectangle>(); //COMPLETE!!!
	public ArrayList<Rectangle> itemBlocksPlusHealth = new ArrayList<Rectangle>();
	public ArrayList<Rectangle> itemBlocksPlusLife = new ArrayList<Rectangle>();
	public ArrayList<Rectangle> itemBlocksGigMush = new ArrayList<Rectangle>();
	public ArrayList<Rectangle> coins = new ArrayList<Rectangle>(); //COMPLETE!!!
	//public ArrayList<FireBall> fireBalls = new ArrayList<FireBall>();
	public ArrayList<Goomba> goombas = new ArrayList<Goomba>(); //COMPLETE!!!
	public ArrayList<Koopa> koopas = new ArrayList<Koopa>(); //COMPLETE!!!
	public ArrayList<KoopaShell> koopashells = new ArrayList<KoopaShell>(); //COMPLETE!!!
	public Scanner sc;
	
	Map(String mapTitle)
	{
		this.mapTitle = mapTitle;
	}
	
	public void loadMap()
	{
		try
		{
			sc = new Scanner(new File(mapTitle + ".txt"));
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
		
		sc.next();
		sc.next();
		sc.next();
		xDisMax = sc.nextInt();
		yDisMax = sc.nextInt();
		
		block = new int[xDisMax][yDisMax];
		int xDis, yDis;
		int blockType;
		
		while(sc.hasNext())
		{
			xDis = sc.nextInt();
			yDis = sc.nextInt();
			blockType = sc.nextInt();
			block[xDis][yDis] = blockType;
			switch(blockType)
			{
			case 1 :
				solidBlocks.add(new Rectangle(defaultBlockSize + defaultBlockSize * xDis + 1, 400 - defaultBlockSize * yDis + 1, defaultBlockSize - 2, defaultBlockSize - 2));
				break;
			case 2:
				itemBlocksCoin.add(new Rectangle(defaultBlockSize + defaultBlockSize * xDis + 1, 400 - defaultBlockSize * yDis + 1, defaultBlockSize - 2, defaultBlockSize - 2));
				break;
			case 3:
				itemBlocksPlusHealth.add(new Rectangle(defaultBlockSize + defaultBlockSize * xDis + 1, 400 - defaultBlockSize * yDis + 1, defaultBlockSize - 2, defaultBlockSize - 2));
				break;
			case 4:
				itemBlocksPlusLife.add(new Rectangle(defaultBlockSize + defaultBlockSize * xDis + 1, 400 - defaultBlockSize * yDis + 1, defaultBlockSize - 2, defaultBlockSize - 2));
				break;
			case 5:
				itemBlocksGigMush.add(new Rectangle(defaultBlockSize + defaultBlockSize * xDis + 1, 400 - defaultBlockSize * yDis + 1, defaultBlockSize - 2, defaultBlockSize - 2));
				break;
			case 10 :
				coins.add(new Rectangle(defaultBlockSize + defaultBlockSize * xDis + 1, 400 - defaultBlockSize * yDis + 1, defaultBlockSize - 2, defaultBlockSize - 2));
				break;
			case 20 :
				goombas.add(new Goomba(xDis, yDis, defaultBlockSize - 2, defaultBlockSize - 2));
				break;
			case 21 :
				koopas.add(new Koopa(xDis, yDis, defaultBlockSize - 2, 2 * defaultBlockSize - 2));
			default :
				break;
			}
			//blocks.add(new Rectangle(20 + 20 * xDis + 1, 400 - 20 * yDis + 1, 18, 18));
		}
	}
}
