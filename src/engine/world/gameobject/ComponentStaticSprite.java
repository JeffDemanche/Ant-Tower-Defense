package engine.world.gameobject;

import java.util.HashMap;

import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import application.Vec2d;
import engine.world.WorldError;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

/**
 * A ComponentStaticSprite belongs to a GameObject which draws using a single
 * image.
 * 
 * @author jdemanch
 */
public class ComponentStaticSprite extends Component {

	private String currentPath;
	private HashMap<String, Image> spriteImages;

	private boolean flipX;
	private boolean flipY;
	private Vec2d scale;

	private Drawable drawable;

	private int sampleMultiplier;

	/**
	 * Legacy constructor.
	 */
	public ComponentStaticSprite(String tag, GameObject object,
			String spritePath, Drawable drawable) {
		this(object, spritePath, drawable);
	}

	public ComponentStaticSprite(GameObject object, String spritePath,
			Drawable drawable) {
		super("Static Sprite", object);
		this.currentPath = spritePath;
		this.spriteImages = new HashMap<String, Image>();
		this.spriteImages.put(spritePath, new Image(spritePath));
		this.drawable = drawable;
		this.sampleMultiplier = 1;

		this.flipX = false;
		this.flipY = false;
		this.scale = new Vec2d(1);
	}

	/**
	 * Upsamples sprite to avoid blurry smoothing (for pixel art textures).
	 */
	public void setSampleMultiplier(String path) {
		if (!this.spriteImages.containsKey(path)) {
			throw new WorldError("No sprite path: " + path);
		}
		this.spriteImages.put(path,
				new Image(path,
						spriteImages.get(path).getWidth() * sampleMultiplier,
						spriteImages.get(path).getHeight() * sampleMultiplier,
						true, false));
	}

	public void registerNewImage(String path) {
		this.spriteImages.put(path, new Image(path));
	}

	public void setCurrentImage(String path) {
		this.currentPath = path;
	}

	public void setFlip(boolean x, boolean y) {
		this.flipX = x;
		this.flipY = y;
	}

	@Override
	public void onDraw(GraphicsContext g) {
		Vec2d screenPos = this.drawable.getScreenPosition();
		Vec2d screenSize = this.drawable.getScreenSize();

		if (!flipX && !flipY) {
			g.drawImage(this.spriteImages.get(currentPath), screenPos.x,
					screenPos.y, screenSize.x * this.scale.x,
					screenSize.y * this.scale.y);
		} else if (flipX && flipY) {
			g.drawImage(this.spriteImages.get(currentPath),
					screenPos.x + screenSize.x, screenPos.y + screenSize.y,
					-screenSize.x * this.scale.x, -screenSize.y * this.scale.y);
		} else if (flipX) {
			g.drawImage(this.spriteImages.get(currentPath),
					screenPos.x + screenSize.x, screenPos.y,
					-screenSize.x * this.scale.x, screenSize.y * this.scale.y);
		} else if (flipY) {
			g.drawImage(this.spriteImages.get(currentPath), screenPos.x,
					screenPos.y + screenSize.y, screenSize.x * this.scale.x,
					-screenSize.y * this.scale.y);
		}
	}

	@Override
	public void onTick(long nanosSincePreviousTick) {
	}

	@Override
	public void onGameObjectRemoved() {
	}

	@Override
	public Element writeXML(Document doc) throws ParserConfigurationException {
		Element componentStaticSprite = doc
				.createElement("ComponentStaticSprite");
		return componentStaticSprite;
	}

}
