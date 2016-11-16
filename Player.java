import java.awt.Rectangle;

public class Player extends Mob
{
	/*
	public double xD,yD; //coordinates in pixels in double
	public Rectangle playerRect; //in order to use intersect method with ArryaList in Map.java
	public Rectangle bottomRect, topRect, leftRect, rightRect;
	//public int x, y; //coordinates in pixels casted by xD and yD
	
	public double xVelDRight, xVelDLeft, xVelD, yVelD; //velocity in double
	public double xVelDefault = 100;
	public double yVelDefault = 270;
	public double gravAccel = 540;
	//public int xVel, yVel; //velocity casted by xVelD
	public boolean xMoveLeft, xMoveRight, yMove; //determines whether or not Mario is moving
	public boolean onGround, onCeiling; //determines whether or not Mario is touching the ground or ceiling
	public boolean leftBlocked, rightBlocked;
	//public double yBefJump; //y coordinate before Mario jumped
	//public int yJumpTicks; //counts ticks after Mario jumped
	*/
	
	public static int health = 1;
	public static int coin = 0;
	
	public static double xVelDefault = 100;
	public static double yVelDefault = 270;
	public boolean monsterJumped = false; //checks whether monster was jumped; after jumping on the monster Mario jumps
	public boolean visibility = true; //turns false temporarily after Mario touches a Monster
	public static int invisBleepPeriod = 500; //in milliseconds
	public static int invisBleepTimes = 3; //times to bleep
	public int invisStartTick = -(2 * invisBleepTimes + 1) * invisBleepPeriod / MainGame.dt; //to make Mario visible at initial point
	
	public Player()
	{
		xD = 200.0;
		yD = 200.0;
		mobRect = new Rectangle((int)xD, (int)yD, Map.defaultBlockSize - 4, Map.defaultBlockSize - 2);
		//x = 200;
		//y = 200;
	}
	
	/*
	public void moveRight()
	{
		if(xMoveRight)
		{
			xVelD = xVelDefault;
			xD += xVelD * 0.001 * MainGame.dt;
			playerRect.x = (int) xD;
		}
	}
	
	public void moveLeft()
	{
		if(xMoveLeft && leftBlocked)
		{
			xVelDLeft = 0;
		}
		{
			xVelD = -xVelDefault;
			xD += xVelD * 0.001 * MainGame.dt;
			playerRect.x = (int) xD;
		}
	}
	*/
	
	public void moveHori()
	{
		if(!xMoveLeft || (xMoveLeft && leftBlocked))
		{
			xVelDLeft = 0;
		}
		else if(xMoveLeft && !leftBlocked)
		{
			xVelDLeft = xVelDefault;
		}
		if(!xMoveRight || (xMoveRight && rightBlocked))
		{
			xVelDRight = 0;
		}
		else if(xMoveRight && !rightBlocked)
		{
			xVelDRight = xVelDefault;
		}
		xVelD = xVelDRight - xVelDLeft;
		xD += xVelD * 0.001 * MainGame.dt;
		mobRect.x = (int) xD;
	}
	
	public void moveVert()
	{
		if(!monsterJumped)
		{
			if((!yMove && onGround && !onCeiling) || (onGround && onCeiling))
			{
				yVelD = 0;
				yD += 0;
			}
			if(yMove && onGround && !onCeiling)
			{
				yVelD = -yVelDefault;
				yD += yVelD * 0.001 * MainGame.dt;
			}
			if(!onCeiling && !onGround)
			{
				yVelD += gravAccel * 0.001 * MainGame.dt;
				yD += yVelD * 0.001 * MainGame.dt;
			}
			if(!yMove && !onGround && onCeiling || yMove && !onGround && onCeiling)
			{
				yVelD = -yVelD;
				yD += yVelD * 0.001 * MainGame.dt;
			}
		}
		else
		{
			yD += yVelD * 0.001 * MainGame.dt;
		}
		mobRect.y = (int) yD;
	}
	
	@Override
	public int checkIntersect(Map map)
	{
		int blockType = 0; //returns type of block Mario touches
		boolean onGroundTemp = false; //to prevent player.onGround switching with fast interval which leads to bug
		boolean onCeilingTemp = false;
		boolean leftBlockedTemp = false;
		boolean rightBlockedTemp = false;
		boolean monsterJumpedTemp = false;
		bottomRect = new Rectangle(mobRect.x + 1, mobRect.y + mobRect.height + 1, mobRect.width - 2, 1);
		topRect = new Rectangle(mobRect.x + 1, mobRect.y - 1, mobRect.width - 2, 1);
		leftRect = new Rectangle(mobRect.x - 1, mobRect.y + 1, 1, mobRect.height - 2);
		rightRect = new Rectangle(mobRect.x + mobRect.width, mobRect.y + 1, 1, mobRect.height - 2); 
		
		for(Rectangle block : map.solidBlocks)
		{
			if(block.intersects(leftRect))
			{
				leftBlockedTemp = true;
				mobRect.x = block.x + block.width + 1;
				xD = mobRect.x;
				blockType = 2;
			}
			if(block.intersects(rightRect))
			{
				rightBlockedTemp = true;
				mobRect.x = block.x - mobRect.width - 1;
				xD = mobRect.x;
				blockType = 2;
			}
			else if(block.intersects(bottomRect))
			{
				onGroundTemp = true;
				mobRect.y = block.y - mobRect.height - 1;
				yD = mobRect.y;
				//g.setColor(Color.red); //check, g is from repaint
				//g.fillRect(10,10,10,10); //check
				blockType = 1;
			}
			if(block.intersects(topRect))
			{
				onCeilingTemp = true;
				mobRect.y = block.y + block.height + 1;
				yD = mobRect.y;
				blockType = 1;
			}
		}
		
		for(Rectangle block : map.itemBlocksCoin)
		{
			if(block.intersects(leftRect))
			{
				leftBlockedTemp = true;
				mobRect.x = block.x + block.width + 1;
				xD = mobRect.x;
				blockType = 2;
			}
			if(block.intersects(rightRect))
			{
				rightBlockedTemp = true;
				mobRect.x = block.x - mobRect.width - 1;
				xD = mobRect.x;
				blockType = 2;
			}
			else if(block.intersects(bottomRect))
			{
				onGroundTemp = true;
				mobRect.y = block.y - mobRect.height - 1;
				yD = mobRect.y;
				//g.setColor(Color.red); //check, g is from repaint
				//g.fillRect(10,10,10,10); //check
				blockType = 1;
			}
			if(block.intersects(topRect))
			{
				onCeilingTemp = true;
				mobRect.y = block.y + block.height + 1;
				yD = mobRect.y;
				coin++;
				
				map.solidBlocks.add(block);
				map.itemBlocksCoin.remove(block);
				blockType = 1;
			}
		}
		
		for(Rectangle block : map.coins)
		{
			if(block.intersects(mobRect))
			{
				coin++;
				map.coins.remove(block);
				blockType = 10;
			}
		}
		
		for(Rectangle block : map.coins)
		{
			if(block.intersects(mobRect))
			{
				coin++;
				map.coins.remove(block);
				blockType = 10;
			}
		}
		
		for(Goomba block : map.goombas)
		{
			if(block.mobRect.intersects(bottomRect))              
			{
				map.goombas.remove(block);
				monsterJumpedTemp = true;
				mobRect.y = block.mobRect.y - mobRect.height - 1;
				yD = mobRect.y;
				yVelD = -yVelDefault;
				blockType = 20;
			}
			else if(visibility && (block.mobRect.intersects(leftRect) || block.mobRect.intersects(rightRect) || block.mobRect.intersects(topRect)))
			{
				health--;
				invisStartTick = MainGame.ticks;
				blockType = 20;
			}
		}
		
		for(Koopa block : map.koopas)
		{
			if(block.mobRect.intersects(bottomRect))              
			{
				map.koopas.remove(block);
				map.koopashells.add(new KoopaShell(block.mobRect.x, block.mobRect.y, Map.defaultBlockSize - 2, Map.defaultBlockSize - 2));
				monsterJumpedTemp = true;
				mobRect.y = block.mobRect.y - mobRect.height - 1;
				yD = mobRect.y;
				yVelD = -yVelDefault;
				blockType = 21;
			}
			else if(visibility && (block.mobRect.intersects(leftRect) || block.mobRect.intersects(rightRect) || block.mobRect.intersects(topRect)))
			{
				health--;
				invisStartTick = MainGame.ticks;
				blockType = 21;
			}
		}
		
		for(KoopaShell block : map.koopashells)
		{
			if(block.mobRect.intersects(bottomRect))              
			{
				monsterJumpedTemp = true;
				mobRect.y = block.mobRect.y - mobRect.height - 1;
				yD = mobRect.y;
				yVelD = -yVelDefault;
				if(!xMoveRight && !xMoveLeft)
				{
					double SchrodingerMonsterMove = Math.random();
					if(SchrodingerMonsterMove < 0.5)
					{
						block.xMoveLeft = true;
						block.xMoveRight = false;
					}
					else
					{
						block.xMoveLeft= false;
						block.xMoveRight = true;
					}
				}
				else
				{
					block.xMoveLeft = false;
					block.xMoveRight = false;
				}
				blockType = 22;
			}
			else if(visibility && (block.xMoveLeft || block.xMoveRight) && (block.mobRect.intersects(leftRect) || block.mobRect.intersects(rightRect) || block.mobRect.intersects(topRect)))
			{
				health--;
				invisStartTick = MainGame.ticks;
				blockType = 22;
			}
			else if(!block.xMoveLeft && !block.xMoveRight)
			{
				if(block.mobRect.intersects(leftRect))
				{
					block.xMoveLeft = true;
					block.xMoveRight = false;
				}
				if(block.mobRect.intersects(rightRect))
				{
					block.xMoveLeft = false;
					block.xMoveRight = true;
				}
			}
		}
		
		leftBlocked = leftBlockedTemp;
		rightBlocked = rightBlockedTemp;
		onGround = onGroundTemp;
		onCeiling = onCeilingTemp;
		monsterJumped = monsterJumpedTemp;
		
		/*
		if(MainGame.ticks >= invisStartTick && MainGame.ticks < invisStartTick + invisBleepPeriod / MainGame.dt
				|| MainGame.ticks >= invisStartTick + 2 * invisBleepPeriod / MainGame.dt && MainGame.ticks < invisStartTick + 3 * invisBleepPeriod / MainGame.dt
				|| MainGame.ticks >= invisStartTick + 4 * invisBleepPeriod / MainGame.dt && MainGame.ticks < invisStartTick + 5 *invisBleepPeriod / MainGame.dt)
			visibility = false;
		else
			visibility = true;
			*/
		
		if(MainGame.ticks >= invisStartTick && MainGame.ticks < invisStartTick + 2 * invisBleepTimes * invisBleepPeriod / MainGame.dt)
			visibility = false;
		else
			visibility = true;
		
		return blockType;
	}
}
