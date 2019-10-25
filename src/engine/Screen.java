package engine;

import application.Vec2d;
import engine.ui.UIElement;
import engine.ui.UIException;
import engine.ui.UIScreen;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.paint.Paint;

public abstract class Screen {

	private Paint background;
	private Vec2d currentSize;

	private UIScreen uiScreen;

	public Screen(Paint background, Vec2d initialSize) {
		this.background = background;
		this.currentSize = initialSize;
		this.uiScreen = new UIScreen();
	}

	/**
	 * Adds an element to the root UI for this screen.
	 * 
	 * @param element
	 *            The UI element to add.
	 */
	public void add(UIElement element) {
		try {
			this.uiScreen.add(element);
		} catch (UIException e) {
			e.printStackTrace();
		}
	}

	public Vec2d getCurrentSize() {
		return this.currentSize;
	}

	public void onTick(long nanosSincePreviousTick) {
		uiScreen.onTick(nanosSincePreviousTick);
	}

	public void onDraw(GraphicsContext g) {
		g.setFill(background);
		g.fillRect(0, 0, currentSize.x, currentSize.y);

		uiScreen.onDraw(g);
	}

	public void onKeyTyped(KeyEvent e) {
		uiScreen.onKeyTyped(e);
	}

	public void onKeyPressed(KeyEvent e) {
		uiScreen.onKeyPressed(e);
	}

	public void onKeyReleased(KeyEvent e) {
		uiScreen.onKeyReleased(e);
	}

	public void onMouseClicked(MouseEvent e) {
		uiScreen.onMouseClicked(e);
	}

	public void onMousePressed(MouseEvent e) {
		uiScreen.onMousePressed(e);
	}

	public void onMouseReleased(MouseEvent e) {
		uiScreen.onMouseReleased(e);
	}

	public void onMouseDragged(MouseEvent e) {
		uiScreen.onMouseDragged(e);
	}

	public void onMouseMoved(MouseEvent e) {
		uiScreen.onMouseMoved(e);
	}

	public void onMouseWheelMoved(ScrollEvent e) {
		uiScreen.onMouseWheelMoved(e);
	}

	public void onFocusChanged(boolean newVal) {
		uiScreen.onFocusChanged(newVal);
	}

	public void onResize(Vec2d newSize) {
		this.currentSize = newSize;
		// uiScreen.onResize(newSize);
	}

	public void onShutdown() {
		uiScreen.onShutdown();
	}

	public void onStartup() {
		uiScreen.onStartup();
	}
}
