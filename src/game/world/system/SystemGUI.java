package game.world.system;

import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import application.Vec2d;
import engine.world.GameSystem;
import engine.world.gameobject.gui.ComponentGUIDrawable;
import game.world.ATDWorld;
import game.world.gameobject.gui.GUITowerIcon;
import game.world.gameobject.gui.GUITowersPanel;
import game.world.gameobject.tower.TowerInfo;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;

public class SystemGUI extends GameSystem {

	private static final int GUI_Z = 10;

	private ATDWorld atdWorld;

	private GUITowersPanel towersPanel;

	private GUITowerIcon cinnamonTowerIcon;

	public SystemGUI(ATDWorld world) {
		super(world);
		this.atdWorld = world;

		this.towersPanel = new GUITowersPanel(this,
				world.getViewport().getScreenSize());
		this.cinnamonTowerIcon = new GUITowerIcon(this,
				(ComponentGUIDrawable) towersPanel.getComponent("GUI Drawable"),
				new Vec2d(5), world.getViewport().getScreenSize(),
				TowerInfo.CINNAMON);

		this.addGameObject(GUI_Z, towersPanel);
		this.addGameObject(GUI_Z, cinnamonTowerIcon);
	}
	
	@Override
	public Element writeXML(Document doc) throws ParserConfigurationException {
		return null;
	}

	@Override
	public void onDraw(GraphicsContext g) {
		this.drawGameObjects(g);
	}

	@Override
	public void onTick(long nanosSincePreviousTick) {
		this.tickGameObjects(nanosSincePreviousTick);
	}

	@Override
	public void onStartup() {
	}

	@Override
	public void onShutdown() {
	}

	@Override
	public void onWorldLoaded() {
	}

}
