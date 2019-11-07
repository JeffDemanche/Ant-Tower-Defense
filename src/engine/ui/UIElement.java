package engine.ui;

import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

import application.Vec2d;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;

/**
 * Defines any drawable element within a screen. Can have child and parent
 * elements. Also defines a notion of position and bounding box.
 * 
 * @author jdemanch
 */
public abstract class UIElement {

	private UIElement parent;
	private CopyOnWriteArrayList<UIElement> children;

	private Vec2d position;
	private Vec2d size;

	private boolean mouseOver;

	public UIElement() {
		this(new Vec2d(0), new Vec2d(0));
	}

	public UIElement(Vec2d position, Vec2d size) {
		this.children = new CopyOnWriteArrayList<UIElement>();
		this.position = position;
		this.size = size;
		this.mouseOver = false;
	}

	/**
	 * Determines if an event location is within a given bounding box of an
	 * element.
	 * 
	 * @param boxPosition
	 *            The element position.
	 * @param boxSize
	 *            The element size.
	 * @param location
	 *            The location of the event.
	 * @return Whether the event occurred within the box.
	 */
	protected static boolean inBox(Vec2d boxPosition, Vec2d boxSize,
			Vec2d location) {
		return location.x >= boxPosition.x
				&& location.x <= boxPosition.x + boxSize.x
				&& location.y >= boxPosition.y
				&& location.y <= boxPosition.y + boxSize.y;
	}

	protected void setMouseOver(boolean mouseOver) {
		this.mouseOver = mouseOver;
	}

	/**
	 * Adds a child element to this element.
	 * 
	 * @param child
	 *            The child.
	 */
	public void add(UIElement child) {
		if (child.getParent() != null) {
			throw new UIException("Can't add element to multiple parents.");
		}
		child.setParent(this);
		this.children.add(child);
	}

	public void remove(UIElement child) {
		child.setParent(null);
		this.children.remove(child);
	}

	/**
	 * Gets the immediate descendants of this parent element.
	 * 
	 * @return An array list of children (empty if there are none).
	 */
	public CopyOnWriteArrayList<UIElement> getChildren() {
		return children;
	}

	/**
	 * Gets all children for the element, using a depth-first strategy.
	 * 
	 * @return An array list of UI children.
	 */
	public ArrayList<UIElement> getAllChildren() {
		ArrayList<UIElement> c = new ArrayList<UIElement>();
		for (UIElement element : children) {
			c.add(element);
			c.addAll(element.getAllChildren());
		}
		return c;
	}

	public Vec2d getPosition() {
		return this.position;
	}

	/**
	 * Gets the absolute position of the element.
	 * 
	 * @return The position, absolute to the screen.
	 */
	public Vec2d getAbsoutePosition() {
		double x = 0;
		double y = 0;
		UIElement parent = this;
		while (parent != null) {
			x += parent.getPosition().x;
			y += parent.getPosition().y;
			parent = parent.parent;
		}
		return new Vec2d(x, y);
	}

	public Vec2d getSize() {
		return this.size;
	}

	public void setPosition(Vec2d position) {
		this.position = position;
	}

	public void setSize(Vec2d size) {
		this.size = size;
		this.onResize(this.size);
	}

	public void onResize(Vec2d newSize) {
		children.forEach(child -> {
			child.onResize(newSize);
		});
	}

	/**
	 * Gets this element's parent element.
	 * 
	 * @return The parent object, or null if no parent.
	 */
	public UIElement getParent() {
		return this.parent;
	}

	/**
	 * Sets a new parent for this UIElement
	 * 
	 * @param parent
	 *            The new parent to apply.
	 */
	public void setParent(UIElement parent) {
		this.parent = parent;
	}

	public boolean isMouseOver() {
		return this.mouseOver;
	}

	public void onTick(long nanosSincePreviousTick) {
		children.forEach(child -> {
			child.onTick(nanosSincePreviousTick);
		});
	}

	public void onDraw(GraphicsContext g) {
		children.forEach(child -> {
			child.onDraw(g);
		});
	}

	public void onKeyTyped(KeyEvent e) {
		this.getChildren().forEach(child -> {
			child.onKeyTyped(e);
		});
	}

	public void onKeyPressed(KeyEvent e) {
		this.getChildren().forEach(child -> {
			child.onKeyPressed(e);
		});
	}

	public void onKeyReleased(KeyEvent e) {
		this.getChildren().forEach(child -> {
			child.onKeyReleased(e);
		});
	}

	public void onMouseClicked(MouseEvent e) {
		this.getChildren().forEach(child -> {
			if (inBox(child.getAbsoutePosition(), child.getSize(),
					new Vec2d(e.getSceneX(), e.getSceneY()))) {
				child.onMouseClicked(e);
			}
		});
	}

	public void onMousePressed(MouseEvent e) {
		this.getChildren().forEach(child -> {
			if (inBox(child.getAbsoutePosition(), child.getSize(),
					new Vec2d(e.getSceneX(), e.getSceneY()))) {
				child.onMousePressed(e);
			}
		});
	}

	public void onMouseReleased(MouseEvent e) {
		this.getChildren().forEach(child -> {
			if (inBox(child.getAbsoutePosition(), child.getSize(),
					new Vec2d(e.getSceneX(), e.getSceneY()))) {
				child.onMouseReleased(e);
			}
		});
	}

	public void onMouseDragged(MouseEvent e) {
		this.getChildren().forEach(child -> {
			if (inBox(child.getAbsoutePosition(), child.getSize(),
					new Vec2d(e.getSceneX(), e.getSceneY()))) {
				child.onMouseDragged(e);
			}
		});
	}

	public void onMouseMoved(MouseEvent e) {
		this.getChildren().forEach(child -> {
			if (inBox(child.getAbsoutePosition(), child.getSize(),
					new Vec2d(e.getSceneX(), e.getSceneY()))) {
				child.onMouseMoved(e);
			}
		});
	}

	public void onMouseWheelMoved(ScrollEvent e) {
		this.getChildren().forEach(child -> {
			if (inBox(child.getAbsoutePosition(), child.getSize(),
					new Vec2d(e.getSceneX(), e.getSceneY()))) {
				child.onMouseWheelMoved(e);
			}
		});
	}

	public void onFocusChanged(boolean newVal) {
		this.getChildren().forEach(child -> {
			child.onFocusChanged(newVal);
		});
	}

	public void onShutdown() {
		this.getChildren().forEach(child -> {
			child.onShutdown();
		});
	}

	public void onStartup() {
		this.getChildren().forEach(child -> {
			child.onStartup();
		});
	}
}
