import java.awt.Rectangle;

abstract public class Mob {
	Rectangle mobRect;
	Rectangle bottomRect, topRect, leftRect, rightRect;
	
	public double xD,yD; //coordinates in pixels in double
	public double xVelDRight, xVelDLeft, xVelD, yVelD; //velocity in double
	public double xVelDefault;
	public double yVelDefault;
	public double gravAccel = 540;
	public boolean xMoveLeft, xMoveRight, yMove; //determines whether or not Mario is moving
	public boolean onGround, onCeiling; //determines whether or not Mario is touching the ground or ceiling
	public boolean leftBlocked, rightBlocked;
	
	abstract public void moveVert();
	abstract public void moveHori();
	
	public int checkIntersect(Map map)
	{
		//int blockType = 0; //returns type of block Mario touches
		boolean onGroundTemp = false; //to prevent player.onGround switching with fast interval which leads to bug
		boolean onCeilingTemp = false;
		boolean leftBlockedTemp = false;
		boolean rightBlockedTemp = false;
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
				//blockType = 1;
			}
			if(block.intersects(rightRect))
			{
				rightBlockedTemp = true;
				mobRect.x = block.x - mobRect.width - 1;
				xD = mobRect.x;
				//blockType = 1;
			}
			if(block.intersects(bottomRect))
			{
				onGroundTemp = true;
				mobRect.y = block.y - mobRect.height - 1;
				yD = mobRect.y;
				//g.setColor(Color.red); //check, g is from repaint
				//g.fillRect(10,10,10,10); //check
				//blockType = 1;
			}
			if(block.intersects(topRect))
			{
				onCeilingTemp = true;
				//blockType = 1;
			}
		}
		leftBlocked = leftBlockedTemp;
		rightBlocked = rightBlockedTemp;
		onGround = onGroundTemp;
		onCeiling = onCeilingTemp;
		
		return 0;
	}
}
