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
import game.world.gameobject.gui.GUITowerInfoPanel;
import game.world.gameobject.gui.GUITowersPanel;
import game.world.gameobject.gui.GUIWorldInfo;
import game.world.gameobject.tower.TowerInfo;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;

public class SystemGUI extends GameSystem {

	private static final int GUI_Z = 10;

	public static final double TOWERS_PANEL_HEIGHT = 170;
	public static final double DRAGGED_ICON_SIZE = 25;
	public static final double INFO_PANEL_WIDTH = 200;
	public static final double WORLD_INFO_TOP = 150;
	public static final double WORLD_INFO_WIDTH = 200;
	public static final double WORLD_INFO_HEIGHT = 300;

	private ATDWorld atdWorld;

	private GUITowersPanel towersPanel;
	private GUITowerInfoPanel infoPanel;
	private GUIWorldInfo worldInfo;

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

		Vec2d screenSize = world.getViewport().getScreenSize();

		this.towersPanel = new GUITowersPanel(this, screenSize);
		this.infoPanel = new GUITowerInfoPanel(this);
		this.worldInfo = new GUIWorldInfo(this, screenSize);

		initializeTowerIcons();

		this.addGameObject(GUI_Z, towersPanel);
		this.addGameObject(GUI_Z, infoPanel);
		this.addGameObject(GUI_Z, worldInfo);
	}

	private void initializeTowerIcons() {
		towerIcons.put("cinnamon", new GUITowerIcon(this,
				(ComponentGUIDrawable) towersPanel.getComponent("GUI Drawable"),
				new Vec2d(5), atdWorld.getViewport().getScreenSize(),
				TowerInfo.CINNAMON));
		
		towerIcons.put("honey", new GUITowerIcon(this,
				(ComponentGUIDrawable) towersPanel.getComponent("GUI Drawable"),
				new Vec2d(50,5), atdWorld.getViewport().getScreenSize(),
				TowerInfo.HONEY));
		
		towerIcons.put("water", new GUITowerIcon(this,
				(ComponentGUIDrawable) towersPanel.getComponent("GUI Drawable"),
				new Vec2d(90,5), atdWorld.getViewport().getScreenSize(),
				TowerInfo.WATER));
		
		towerIcons.put("spiderweb", new GUITowerIcon(this,
				(ComponentGUIDrawable) towersPanel.getComponent("GUI Drawable"),
				new Vec2d(130,5), atdWorld.getViewport().getScreenSize(),
				TowerInfo.SPIDERWEB));

		this.addGameObject(GUI_Z + 1, towerIcons.get("cinnamon"));
		this.addGameObject(GUI_Z + 1, towerIcons.get("honey"));
		this.addGameObject(GUI_Z + 1, towerIcons.get("water"));
		this.addGameObject(GUI_Z + 1, towerIcons.get("spiderweb"));
	}

	public void setInfoDisplay(TowerInfo info) {
		infoPanel.setTower(info);
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

		if (towerBeingDragged != null
				&& !towersPanel.getDrawable().insideBB(point)) {
			towers.addTowerToWorld(
					atdWorld.getViewport().toGameSpace(point, false),
					towerBeingDragged);
		}
		if (draggedTower != null)
			draggedTower.remove();
		setTowerBeingDragged(null);
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

		worldInfo.setSugarRemaining(atdWorld.getRemainingSugar());
		worldInfo.setCash(atdWorld.getCash());
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

	public static String wordWrap(String text, int charsPerLine) {
		StringBuilder builder = new StringBuilder();
		String[] words = text.split(" ");

		int currLineLength = 0;
		for (String word : words) {
			if (currLineLength + word.length() > charsPerLine) {
				builder.append("\n");
				builder.append(word + " ");
				currLineLength = word.length() + 1;
			} else {
				builder.append(word + " ");
				currLineLength += word.length() + 1;
			}
		}

		return builder.toString();
	}

}
