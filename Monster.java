import java.awt.Rectangle;

public class Monster extends Mob {
	public float xVelDefault;
	
	public Monster(int xDis, int yDis, int width, int height)
	{
		mobRect = new Rectangle(Map.defaultBlockSize + Map.defaultBlockSize * xDis + 1, 400 - Map.defaultBlockSize * yDis + 1, width, height);
		xD = mobRect.x;
		yD = mobRect.y;
	}

	@Override
	public void moveHori() {
		if((leftBlocked && !rightBlocked) || (rightBlocked && !leftBlocked))
		{
			xVelD = -xVelD;
		}
		xD += xVelD * 0.001 * MainGame.dt;
		mobRect.x = (int) xD;
	}

	@Override
	public void moveVert() {
		if(onGround)
		{
			yVelD = 0;
			yD += 0;
		}
		else
		{
			yVelD += gravAccel * 0.001 * MainGame.dt;
			yD += yVelD * 0.001 * MainGame.dt;
		}
		mobRect.y = (int) yD;
	}
	
}
