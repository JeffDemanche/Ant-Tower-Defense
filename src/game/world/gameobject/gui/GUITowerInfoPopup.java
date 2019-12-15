package game.world.gameobject.gui;

import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import application.Vec2d;
import engine.world.gameobject.ComponentSolidColorSprite;
import engine.world.gameobject.GameObject;
import engine.world.gameobject.gui.ComponentGUIDrawable;
import engine.world.gameobject.gui.ComponentGUITextRenderer;
import game.world.gameobject.tower.Tower;
import game.world.system.HexCoordinates;
import game.world.system.SystemTowers;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class GUITowerInfoPopup extends GameObject {

	private static final int POPUP_WIDTH = 50;
	private static final int POPUP_HEIGHT = 30;

	private HexCoordinates coordinates;
	private SystemTowers system;
	private Tower tower;

	private ComponentGUIDrawable bound;
	private ComponentSolidColorSprite background;
	private ComponentGUITextRenderer sell;

	public GUITowerInfoPopup(SystemTowers system, HexCoordinates coordinates,
			Tower tower) {
		super(system, "Sell Tower Popup");
		
		this.system = system;
		this.coordinates = coordinates;
		this.tower = tower;

		this.bound = new ComponentGUIDrawable(this, getScreenSpacePos(),
				new Vec2d(POPUP_WIDTH, POPUP_HEIGHT));
		this.background = new ComponentSolidColorSprite(this,
				new Color(0, 0, 0, 0.4), bound);
		this.sell = new ComponentGUITextRenderer(this, "Sell",
				getScreenSpacePos().plus(new Vec2d(10, 15)),
				new Font("Arial", 14));

		this.addComponent(bound);
		this.addComponent(background);
		this.addComponent(sell);
	}

	@Override
	public void onTick(long nanosSincePreviousTick) {
		super.onTick(nanosSincePreviousTick);

		this.bound.setPosition(getScreenSpacePos());
		this.sell.setScreenPos(getScreenSpacePos().plus(new Vec2d(12, 20)));
	}

	private Vec2d getScreenSpacePos() {
		return this.getSystem().getWorld().getViewport()
				.toScreenSpace(coordinates.toGameSpaceCentered(), false)
				.minus(new Vec2d(POPUP_WIDTH / 2, POPUP_HEIGHT * 2));
	}

	@Override
	public void onMouseClicked(MouseEvent e) {
		super.onMouseClicked(e);

		if (bound.insideBB(new Vec2d(e.getSceneX(), e.getSceneY()))) {
			this.system.sellTower(tower);
			e.consume();
			this.remove();
		}
	}

	@Override
	public void onDraw(GraphicsContext g) {
		super.onDraw(g);
	}

	@Override
	public Element writeXML(Document doc) throws ParserConfigurationException {
		return null;
	}

}
