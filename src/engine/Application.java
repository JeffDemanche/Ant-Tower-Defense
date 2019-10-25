package engine;

import application.FXFrontEnd;
import application.Vec2d;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;

/**
 * Class defining methods on an application.
 * 
 * @author jdemanch
 */
public abstract class Application extends FXFrontEnd {
	
	private Screen currentScreen;

	public Application(String title, Vec2d screenSize, boolean fullscreen) {
		super(title, screenSize, true, fullscreen);
	}

	public void setScreen(Screen screen) {
		this.currentScreen = screen;
	}
	
	public Screen getScreen() {
		return this.currentScreen;
	}
	
	@Override
	protected void onTick(long nanosSincePreviousTick) {
		this.currentScreen.onTick(nanosSincePreviousTick);
	}

	@Override
	protected void onDraw(GraphicsContext g) {
		this.currentScreen.onDraw(g);
	}

	@Override
	protected void onKeyTyped(KeyEvent e) {
		this.currentScreen.onKeyTyped(e);
	}

	@Override
	protected void onKeyPressed(KeyEvent e) {
		this.currentScreen.onKeyPressed(e);
	}

	@Override
	protected void onKeyReleased(KeyEvent e) {
		this.currentScreen.onKeyReleased(e);
	}

	@Override
	protected void onMouseClicked(MouseEvent e) {
		this.currentScreen.onMouseClicked(e);
	}

	@Override
	protected void onMousePressed(MouseEvent e) {
		this.currentScreen.onMousePressed(e);
	}

	@Override
	protected void onMouseReleased(MouseEvent e) {
		this.currentScreen.onMouseReleased(e);
	}

	@Override
	protected void onMouseDragged(MouseEvent e) {
		this.currentScreen.onMouseDragged(e);
	}

	@Override
	protected void onMouseMoved(MouseEvent e) {
		this.currentScreen.onMouseMoved(e);
	}

	@Override
	protected void onMouseWheelMoved(ScrollEvent e) {
		this.currentScreen.onMouseWheelMoved(e);
	}

	@Override
	protected void onFocusChanged(boolean newVal) {
		this.currentScreen.onFocusChanged(newVal);
	}

	@Override
	protected void onResize(Vec2d newSize) {
		this.currentScreen.onResize(newSize);
	}

	@Override
	protected void onShutdown() {
		this.currentScreen.onShutdown();
	}

	@Override
	protected void onStartup() {
		this.currentScreen.onStartup();
	}
}
