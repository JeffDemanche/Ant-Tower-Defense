package game.world.gameobject.projectile;

import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import application.Vec2d;
import game.world.gameobject.tile.Tile;
import game.world.gameobject.tile.TileHoney;
import game.world.system.HexCoordinates;
import game.world.system.SystemTowers;

public class HoneyProjectile extends Projectile{

	public HoneyProjectile(SystemTowers towerSystem, HexCoordinates hex, ProjectileInfo info) {
		super(towerSystem, hex, info);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Element writeXML(Document doc) throws ParserConfigurationException {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public void onTick(long nanosSincePreviousTick) {
		
		if(this.hit)
		{
			HexCoordinates hexTarget = HexCoordinates.fromGameSpace(this.target);
			Tile tile = (Tile) ((SystemTowers) this.getSystem()).getLevel().getTileAt(hexTarget.getX(),
					hexTarget.getY());

			if(tile.getType() != Tile.Type.Honey)
			{
				tile.remove();
				
				((SystemTowers) this.getSystem()).getLevel().setTile((hexTarget),
						new TileHoney(((SystemTowers) this.getSystem()).getLevel(), hexTarget));
			}
			
			
			
			this.remove();
		}
		
		super.onTick(nanosSincePreviousTick);
	}

}
