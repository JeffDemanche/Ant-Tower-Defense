package game.world.system;

import java.util.HashMap;

import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import application.Vec2d;
import engine.world.GameSystem;
import engine.world.gameobject.gui.ComponentGUIDrawable;
import game.world.ATDWorld;
import game.world.gameobject.gui.GUITowerIcon;
import game.world.gameobject.gui.GUITowerIconDragged;
import game.world.gameobject.gui.GUITowersPanel;
import game.world.gameobject.tower.TowerInfo;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;

public class SystemGUI extends GameSystem {

	private static final int GUI_Z = 10;

	private static final double DRAGGED_ICON_SIZE = 25;

	private ATDWorld atdWorld;

	private GUITowersPanel towersPanel;

	private TowerInfo towerBeingDragged;
	private GUITowerIconDragged draggedTower;
	private HashMap<String, GUITowerIcon> towerIcons;

	private SystemTowers towers;

	public SystemGUI(ATDWorld world, SystemTowers towers) {
		super(world);
		this.atdWorld = world;
		this.towers = towers;

		this.towerIcons = new HashMap<>();
		this.towerBeingDragged = null;
		this.draggedTower = null;

		this.towersPanel = new GUITowersPanel(this,
				world.getViewport().getScreenSize());

		initializeTowerIcons();

		this.addGameObject(GUI_Z, towersPanel);
	}

	private void initializeTowerIcons() {
		towerIcons.put("cinnamon", new GUITowerIcon(this,
				(ComponentGUIDrawable) towersPanel.getComponent("GUI Drawable"),
				new Vec2d(5), atdWorld.getViewport().getScreenSize(),
				TowerInfo.CINNAMON));

		this.addGameObject(GUI_Z + 1, towerIcons.get("cinnamon"));
	}

	public void setTowerBeingDragged(TowerInfo tower) {
		this.towerBeingDragged = tower;
	}

	public TowerInfo getTowerBeingDragged() {
		return this.towerBeingDragged;
	}

	@Override
	public void onMousePressed(MouseEvent e) {
		super.onMousePressed(e);

		Vec2d point = new Vec2d(e.getSceneX(), e.getSceneY());

		for (String towerIconKey : towerIcons.keySet()) {
			GUITowerIcon icon = towerIcons.get(towerIconKey);

			if (icon.getBound().insideBB(point)) {
				towerBeingDragged = icon.getTowerInfo();
				draggedTower = new GUITowerIconDragged(this, towerBeingDragged,
						point.minus(new Vec2d(DRAGGED_ICON_SIZE / 2)),
						new Vec2d(DRAGGED_ICON_SIZE));
				this.addGameObject(GUI_Z, draggedTower);
			}
		}
	}

	@Override
	public void onMouseDragged(MouseEvent e) {
		super.onMouseDragged(e);

		Vec2d point = new Vec2d(e.getSceneX(), e.getSceneY());

		if (towerBeingDragged != null) {
			draggedTower
					.setPosition(point.minus(new Vec2d(DRAGGED_ICON_SIZE / 2)));
			e.consume();
		}
	}

	@Override
	public void onMouseReleased(MouseEvent e) {
		super.onMouseReleased(e);

		Vec2d point = new Vec2d(e.getSceneX(), e.getSceneY());

		if (towerBeingDragged != null) {
			towers.addTowerToWorld(
					atdWorld.getViewport().toGameSpace(point, false),
					towerBeingDragged);
			draggedTower.remove();
			towerBeingDragged = null;
		} else {
		}
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
