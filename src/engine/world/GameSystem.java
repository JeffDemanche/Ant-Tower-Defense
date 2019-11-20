package engine.world;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import application.Vec2d;
import engine.world.gameobject.EventHandler;
import engine.world.gameobject.GameObject;
import engine.world.serialization.XMLSerializable;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;

/**
 * Systems organize GameObjects and act using them.
 * 
 * @author jdemanch
 */
public abstract class GameSystem implements XMLSerializable {

	private World world;
	private static final int MAX_Z = 1000;
	private ConcurrentHashMap<Integer, Set<GameObject>> gameObjects;

	public GameSystem(World world) {
		this.world = world;
		this.gameObjects = new ConcurrentHashMap<>();
	}

	public void addGameObject(int zIndex, GameObject object) {
		if (this.gameObjects.containsKey(zIndex)) {
			this.gameObjects.get(zIndex).add(object);
		} else {
			Set<GameObject> newList = ConcurrentHashMap.newKeySet();
			newList.add(object);
			this.gameObjects.put(zIndex, newList);
		}
	}

	public int getObjectCount() {
		int count = 0;
		int z = 0;
		while (z <= MAX_Z) {
			if (this.gameObjects.containsKey(z))
				count += this.gameObjects.get(z).size();
			z++;
		}
		return count;
	}

	public void removeGameObject(GameObject obj) {
		for (int key : gameObjects.keySet()) {
			Iterator<GameObject> iter = gameObjects.get(key).iterator();
			while (iter.hasNext()) {
				GameObject next = iter.next();
				if (next == obj) {
					iter.remove();
					break;
				}
			}
		}
	}

	public boolean doesGameObjectExist(GameObject obj) {
		for (int key : gameObjects.keySet()) {
			Iterator<GameObject> iter = gameObjects.get(key).iterator();
			while (iter.hasNext()) {
				GameObject next = iter.next();
				if (next == obj) {
					return true;
				}
			}
		}
		return false;
	}

	public ArrayList<Element> writeGameObjectsXML(Document doc) {
		ArrayList<Element> elements = new ArrayList<>();
		for (int key : gameObjects.keySet()) {
			Iterator<GameObject> iter = gameObjects.get(key).iterator();
			while (iter.hasNext()) {
				GameObject next = iter.next();
				try {
					Element objectXml = next.writeXML(doc);
					objectXml.setAttribute("layer",
							new Integer(key).toString());
					elements.add(next.writeXML(doc));
				} catch (ParserConfigurationException e) {
					e.printStackTrace();
				}
			}
		}
		return elements;
	}

	public EventHandler getEventHandler() {
		return this.world.getEventHandler();
	}

	public ArrayList<GameObject> getCollidableObjects() {
		ArrayList<GameObject> objects = new ArrayList<>();
		for (int key : gameObjects.keySet()) {
			for (GameObject obj : gameObjects.get(key)) {
				if (obj.isCollidable()) {
					objects.add(obj);
				}
			}
		}
		return objects;
	}

	public World getWorld() {
		return this.world;
	}

	public abstract void onDraw(GraphicsContext g);

	public abstract void onTick(long nanosSincePreviousTick);

	public abstract void onStartup();

	public abstract void onShutdown();

	public abstract void onWorldLoaded();

	public void onResize(Vec2d newSize) {
		for (int key : gameObjects.keySet()) {
			Iterator<GameObject> iter = gameObjects.get(key).iterator();
			while (iter.hasNext()) {
				GameObject next = iter.next();
				next.onResize(newSize);
			}
		}
	}

	public void onMousePressed(MouseEvent e) {
		for (int key : gameObjects.keySet()) {
			Iterator<GameObject> iter = gameObjects.get(key).iterator();
			while (iter.hasNext()) {
				GameObject next = iter.next();
				next.onMousePressed(e);
			}
		}
	}

	public void onMouseDragged(MouseEvent e) {
		for (int key : gameObjects.keySet()) {
			Iterator<GameObject> iter = gameObjects.get(key).iterator();
			while (iter.hasNext()) {
				GameObject next = iter.next();
				next.onMouseDragged(e);
			}
		}
	}

	public void onMouseReleased(MouseEvent e) {
		for (int key : gameObjects.keySet()) {
			Iterator<GameObject> iter = gameObjects.get(key).iterator();
			while (iter.hasNext()) {
				GameObject next = iter.next();
				next.onMouseReleased(e);
			}
		}
	}

	/**
	 * Gives screen space coords corresponding to a given pair of game space
	 * coords.
	 * 
	 * @param gameCoords
	 *            The game space coords.
	 * @param size
	 *            True if converting size vector.
	 * @return The screen space coords.
	 */
	public Vec2d gameToScreen(Vec2d gameCoords, boolean size) {
		return this.world.getViewport().toScreenSpace(gameCoords, size);
	}

	public Vec2d screenToGame(Vec2d screenCoords, boolean size) {
		return this.world.getViewport().toGameSpace(screenCoords, size);
	}

	public void drawGameObjects(GraphicsContext g) {
		int z = 0;
		while (z <= MAX_Z) {
			if (this.gameObjects.containsKey(z))
				this.gameObjects.get(z).forEach(object -> object.onDraw(g));
			z++;
		}
	}

	public void tickGameObjects(long nanosSincePreviousTick) {
		int z = 0;
		while (z <= MAX_Z) {
			if (this.gameObjects.containsKey(z))
				this.gameObjects.get(z).forEach(
						object -> object.onTick(nanosSincePreviousTick));
			z++;
		}
	}
}
