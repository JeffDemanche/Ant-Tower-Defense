package engine.ui;

import application.Vec2d;
import engine.world.Viewport;
import engine.world.World;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.paint.Color;

/**
 * A viewport into a gameworld which supports in-world view affine
 * transformations.
 * 
 * @author jdemanch
 */
public class UIViewport extends UIElement {

	/** Using for debugging. */
	private boolean drawGrid = true;

	private World gameWorld;

	public UIViewport(Vec2d position, Vec2d size, World gameWorld) {
		super(position, size);
		this.gameWorld = gameWorld;
	}

	public World getGameWorld() {
		return this.gameWorld;
	}
	
	public void setDrawGrid(boolean draw) {
		this.drawGrid = draw;
	}

	@Override
	public void onResize(Vec2d newSize) {
		super.onResize(newSize);
		gameWorld.onResize(newSize);
	}

	@Override
	public void onKeyTyped(KeyEvent e) {
		super.onKeyTyped(e);
		gameWorld.onKeyTyped(e);
	}

	@Override
	public void onKeyPressed(KeyEvent e) {
		super.onKeyPressed(e);
		gameWorld.onKeyPressed(e);
	}

	@Override
	public void onKeyReleased(KeyEvent e) {
		super.onKeyReleased(e);
		gameWorld.onKeyReleased(e);
	}

	@Override
	public void onMouseClicked(MouseEvent e) {
		super.onMouseClicked(e);
		gameWorld.onMouseClicked(e);
	}

	@Override
	public void onMouseMoved(MouseEvent e) {
		super.onMouseMoved(e);
		gameWorld.onMouseMoved(e);
	}

	@Override
	public void onMouseWheelMoved(ScrollEvent e) {
		super.onMouseWheelMoved(e);
		gameWorld.onMouseWheelMoved(e);
	}

	@Override
	public void onMousePressed(MouseEvent e) {
		super.onMousePressed(e);
		gameWorld.onMousePressed(e);
	}

	@Override
	public void onMouseDragged(MouseEvent e) {
		super.onMouseDragged(e);
		gameWorld.onMouseDragged(e);
	}

	@Override
	public void onMouseReleased(MouseEvent e) {
		super.onMouseReleased(e);
		gameWorld.onMouseReleased(e);
	}

	@Override
	public void onDraw(GraphicsContext g) {
		Vec2d pos = this.getAbsoutePosition();

		g.setFill(Color.BLACK);
		g.fillRect(pos.x, pos.y, this.getSize().x, this.getSize().y);

		if (drawGrid) {
			this.drawGrid(g);
		}

		this.gameWorld.onDraw(g);

		double largeNumber = 20000;

		g.setFill(Color.BLACK);

		// Bottom right
		g.clearRect(this.getSize().x, this.getSize().y, largeNumber,
				largeNumber);
		// Top left
		g.clearRect(0, 0, -largeNumber, -largeNumber);
		// Bottom left
		g.clearRect(0, this.getSize().y, -largeNumber, largeNumber);
		// Top right
		g.clearRect(this.getSize().x, 0, largeNumber, -largeNumber);
		// Bottom
		g.clearRect(0, this.getSize().y, this.getSize().x, largeNumber);
		// Right
		g.clearRect(this.getSize().x, 0, largeNumber, this.getSize().y);
		// Top
		g.clearRect(0, 0, this.getSize().x, -largeNumber);
		// Bottom
		g.clearRect(0, this.getSize().y, this.getSize().x, largeNumber);

		super.onDraw(g);
	}

	public void drawGrid(GraphicsContext g) {
		Viewport v = gameWorld.getViewport();

		Vec2d originScreen = v.toScreenSpace(new Vec2d(0), false);

		g.setStroke(Color.AQUAMARINE);
		g.setLineWidth(3.0);
		g.strokeLine(originScreen.x, 0, originScreen.x, this.getSize().y);
		g.strokeLine(0, originScreen.y, this.getSize().x, originScreen.y);

		int gridLineXPos = 0;
		int gridLineXNeg = 0;
		int gridLineYPos = 0;
		int gridLineYNeg = 0;

		double gridLineSpacingGS = 1;

		g.setStroke(Color.WHITE);
		g.setLineWidth(1.0);

		// Positive vertical grid lines.
		while (v.toScreenSpace(new Vec2d(gridLineXPos * gridLineSpacingGS, 0),
				false).x <= this.getSize().x) {
			double screenX = v.toScreenSpace(
					new Vec2d(gridLineXPos * gridLineSpacingGS, 0), false).x;
			if (gridLineXPos != 0)
				g.strokeLine(screenX, 0, screenX, this.getSize().y);

			gridLineXPos += gridLineSpacingGS;
		}
		// Negative vertical grid lines.
		while (v.toScreenSpace(new Vec2d(-gridLineXNeg * gridLineSpacingGS, 0),
				false).x >= 0) {
			double screenX = v.toScreenSpace(
					new Vec2d(-gridLineXNeg * gridLineSpacingGS, 0), false).x;
			if (gridLineXNeg != 0)
				g.strokeLine(screenX, 0, screenX, this.getSize().y);

			gridLineXNeg += gridLineSpacingGS;
		}
		// Positive horizontal grid lines.
		while (v.toScreenSpace(new Vec2d(0, -gridLineYPos * gridLineSpacingGS),
				false).y >= 0) {
			double screenY = v.toScreenSpace(
					new Vec2d(0, -gridLineYPos * gridLineSpacingGS), false).y;
			if (gridLineYPos != 0)
				g.strokeLine(0, screenY, this.getSize().x, screenY);

			gridLineYPos += gridLineSpacingGS;
		}
		// Negative horizontal grid lines.
		while (v.toScreenSpace(new Vec2d(0, gridLineYNeg * gridLineSpacingGS),
				false).y <= this.getSize().y) {
			double screenY = v.toScreenSpace(
					new Vec2d(0, gridLineYNeg * gridLineSpacingGS), false).y;
			if (gridLineYNeg != 0)
				g.strokeLine(0, screenY, this.getSize().x, screenY);

			gridLineYNeg += gridLineSpacingGS;
		}
	}

	@Override
	public void onTick(long nanosSincePreviousTick) {
		super.onTick(nanosSincePreviousTick);
		this.gameWorld.onTick(nanosSincePreviousTick);
	}

	@Override
	public void onFocusChanged(boolean newVal) {
		super.onFocusChanged(newVal);
		gameWorld.onFocusChanged(newVal);
	}

	@Override
	public void onShutdown() {
		super.onShutdown();
		gameWorld.onShutdown();
	}

	@Override
	public void onStartup() {
		super.onStartup();
		gameWorld.onStartup();
	}

}
