package engine.world;

import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import application.Vec2d;
import engine.world.gameobject.Component;
import engine.world.gameobject.Drawable;
import engine.world.gameobject.GameObject;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

/**
 * Similar to ComponentStaticSprite but applies an alpha mask over textures.
 * 
 * @author jdemanch
 */
public class ComponentMaskedSprite extends Component {

	private Image sprite;
	private Image mask;

	private WritableImage composite;

	private Drawable drawable;

	public ComponentMaskedSprite(GameObject object, String spritePath,
			String maskPath, Drawable drawable, int sampleMultiplier) {
		super("Hex Sprite", object);

		this.sprite = new Image(spritePath);
		this.sprite = new Image(spritePath,
				sprite.getWidth() * sampleMultiplier,
				sprite.getHeight() * sampleMultiplier, true, false);
		this.mask = new Image(maskPath);
		this.mask = new Image(maskPath, mask.getWidth() * sampleMultiplier,
				mask.getHeight() * sampleMultiplier, true, false);

		this.composite = new WritableImage((int) this.sprite.getWidth(),
				(int) this.sprite.getHeight());

		applyMask();

		this.drawable = drawable;
	}

	private void applyMask() {
		PixelWriter pw = composite.getPixelWriter();
		for (int x = 0; x < sprite.getWidth(); x++) {
			for (int y = 0; y < sprite.getHeight(); y++) {
				Color originalColor = sprite.getPixelReader().getColor(x, y);
				double alpha = mask.getPixelReader().getColor(x, y)
						.getOpacity();
				pw.setColor(x, y,
						new Color(originalColor.getRed(),
								originalColor.getGreen(),
								originalColor.getBlue(), alpha));
			}
		}
	}

	@Override
	public Element writeXML(Document doc) throws ParserConfigurationException {
		return null;
	}

	@Override
	public void onDraw(GraphicsContext g) {
		Vec2d screenPos = this.drawable.getScreenPosition();
		Vec2d screenSize = this.drawable.getScreenSize();

		g.drawImage(composite, screenPos.x, screenPos.y, screenSize.x,
				screenSize.y);

	}

	@Override
	public void onTick(long nanosSincePreviousTick) {
	}

	@Override
	public void onGameObjectRemoved() {
	}

}
