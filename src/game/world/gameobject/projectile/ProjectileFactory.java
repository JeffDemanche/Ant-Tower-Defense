package game.world.gameobject.projectile;

import game.world.system.HexCoordinates;
import game.world.system.SystemTowers;

public class ProjectileFactory {

	
	private static ProjectileFactory instance = null;
	
	private ProjectileFactory()
	{
		
	}
	
	public static ProjectileFactory getInstance()
	{
		 if(instance == null)
		   {
			   instance = new ProjectileFactory();
		   }
		   
		return instance;
	}
	
	
	public Projectile createProjectile(SystemTowers system, HexCoordinates hex,
			ProjectileInfo projectileInfo)
	{
		if(projectileInfo.type == ProjectileConstants.HONEY)
		{
			return new HoneyProjectile(system,hex,projectileInfo);
		}
		if(projectileInfo.type == ProjectileConstants.WATER)
		{
			return new WaterProjectile(system,hex,projectileInfo);
		}
		if(projectileInfo.type == ProjectileConstants.SPIDER)
		{
			return new SpiderProjectile(system,hex,projectileInfo);
		}
		return null;
	}
}
