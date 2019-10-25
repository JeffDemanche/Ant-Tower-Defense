package engine.world.gameobject;

import java.util.ArrayList;
import java.util.Iterator;

import engine.world.WorldError;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;

/**
 * This class manages dispatching input events for game objects and their
 * components.
 * 
 * @author jdemanch
 */
public class EventHandler {

	public interface MouseListener {
		public void onMouseClicked(MouseEvent e);

		public void onMousePressed(MouseEvent e);

		public void onMouseReleased(MouseEvent e);

		public void onMouseDragged(MouseEvent e);

		public void onMouseMoved(MouseEvent e);
	}

	public interface KeyListener {
		public void onKeyTyped(KeyEvent e);

		public void onKeyPressed(KeyEvent e);

		public void onKeyReleased(KeyEvent e);
	}

	interface ScrollListener {
		public void onMouseWheelMoved(ScrollEvent e);
	}

	private ArrayList<MouseListener> mouseListeners;
	private ArrayList<KeyListener> keyListeners;
	private ArrayList<ScrollListener> scrollListeners;

	public EventHandler() {
		this.mouseListeners = new ArrayList<EventHandler.MouseListener>();
		this.keyListeners = new ArrayList<EventHandler.KeyListener>();
		this.scrollListeners = new ArrayList<EventHandler.ScrollListener>();
	}

	public void registerMouseListener(MouseListener l) {
		this.mouseListeners.add(l);
	}

	public void removeMouseLister(MouseListener l) {
		Iterator<MouseListener> iter = this.mouseListeners.iterator();
		while (iter.hasNext()) {
			if (iter.next() == l) {
				iter.remove();
				break;
			}
		}
	}

	public void registerKeyListener(KeyListener l) {
		this.keyListeners.add(l);
	}

	public void removeKeyListener(KeyListener l) {
		Iterator<KeyListener> iter = this.keyListeners.iterator();
		while (iter.hasNext()) {
			if (iter.next() == l) {
				iter.remove();
				break;
			}
		}
	}

	public void registerScrollListener(ScrollListener l) {
		this.scrollListeners.add(l);
	}

	public void dispatchMouseEvent(String event, MouseEvent e) {
		switch (event) {
		case "MouseClick":
			this.mouseListeners.forEach(listener -> listener.onMouseClicked(e));
			break;
		case "MousePress":
			this.mouseListeners.forEach(listener -> listener.onMousePressed(e));
			break;
		case "MouseRelease":
			this.mouseListeners
					.forEach(listener -> listener.onMouseReleased(e));
			break;
		case "MouseDrag":
			this.mouseListeners.forEach(listener -> listener.onMouseDragged(e));
			break;
		case "MouseMove":
			this.mouseListeners.forEach(listener -> listener.onMouseMoved(e));
			break;
		default:
			throw new WorldError("Invalid mouse event.");
		}
	}

	public void dispatchKeyEvent(String event, KeyEvent e) {
		switch (event) {
		case "KeyType":
			this.keyListeners.forEach(listener -> listener.onKeyTyped(e));
			break;
		case "KeyPress":
			this.keyListeners.forEach(listener -> listener.onKeyPressed(e));
			break;
		case "KeyRelease":
			this.keyListeners.forEach(listener -> listener.onKeyReleased(e));
			break;
		default:
			throw new WorldError("Invalid mouse event.");
		}
	}

}
