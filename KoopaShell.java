
public class KoopaShell extends Monster {
	public KoopaShell(int x, int y, int width, int height)
	{
		super(0, 0, width, height); //actually has no meaning
		xD = x;
		yD = y + height;
		xVelD = 0;
		xVelDefault = 200;
		xMoveLeft = false;
		xMoveRight = false;
	}
	
	@Override
	public void moveHori() {
		
		if(xMoveLeft)
		{
			xVelD = -xVelDefault;
		}
		if(xMoveRight)
		{
			xVelD = xVelDefault;
		}
		if(!xMoveLeft && !xMoveRight)
		{
			xVelD = 0;
		}
		if((leftBlocked && !rightBlocked) || (rightBlocked && !leftBlocked))
		{
			xVelD = -xVelD;
			if(leftBlocked)
			{
				xMoveLeft = false;
				xMoveRight = true;
			}
			if(rightBlocked)
			{
				xMoveLeft = true;
				xMoveRight = false;
			}
		}
		xD += xVelD * 0.001 * MainGame.dt;
		mobRect.x = (int) xD;
	}
}