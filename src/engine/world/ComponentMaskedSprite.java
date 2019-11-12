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

	private WritableImage[] composite;

	private Drawable drawable;

	private int sampleMultiplier;

	/** -1 for non-animated sprites. */
	private int phaseDuration;
	private int numPhases;
	private int frameWidth;

	private int currentPhase;
	private long phaseNanosCounter;

	/**
	 * Constructor for single-frame sprites.
	 */
	public ComponentMaskedSprite(GameObject object, String spritePath,
			String maskPath, Drawable drawable, int sampleMultiplier) {
		super("Masked Sprite", object);

		this.sprite = new Image(spritePath);
		this.frameWidth = (int) this.sprite.getWidth();
		this.sprite = new Image(spritePath,
				sprite.getWidth() * sampleMultiplier,
				sprite.getHeight() * sampleMultiplier, true, false);
		this.mask = new Image(maskPath);
		this.mask = new Image(maskPath, mask.getWidth() * sampleMultiplier,
				mask.getHeight() * sampleMultiplier, true, false);

		this.composite = new WritableImage[] { new WritableImage(
				(int) this.sprite.getWidth(), (int) this.sprite.getHeight()) };

		this.sampleMultiplier = sampleMultiplier;

		this.phaseDuration = -1;
		this.currentPhase = 0;
		this.phaseNanosCounter = 0;

		applyMask(0);

		this.drawable = drawable;
	}

	public ComponentMaskedSprite(GameObject object, String spritePath,
			String maskPath, Drawable drawable, int sampleMultiplier,
			int phaseDuration, int numPhases, int frameWidth) {
		super("Masked Sprite", object);

		this.sprite = new Image(spritePath);
		this.frameWidth = (int) this.sprite.getWidth();
		this.sprite = new Image(spritePath,
				sprite.getWidth() * sampleMultiplier,
				sprite.getHeight() * sampleMultiplier, true, false);
		this.mask = new Image(maskPath);
		this.mask = new Image(maskPath, mask.getWidth() * sampleMultiplier,
				mask.getHeight() * sampleMultiplier, true, false);

		this.sampleMultiplier = sampleMultiplier;

		this.phaseDuration = phaseDuration;
		this.currentPhase = 0;
		this.phaseNanosCounter = 0;
		this.frameWidth = frameWidth;
		this.numPhases = numPhases;

		this.drawable = drawable;
		
		this.composite = new WritableImage[numPhases];
		for (int i = 0; i < numPhases; i++) {
			this.composite[i] = new WritableImage(frameWidth * sampleMultiplier,
					(int) sprite.getHeight());
			applyMask(i);
		}

	}

	private void applyMask(int phase) {
		int offset = phase * frameWidth * sampleMultiplier;
		PixelWriter pw = composite[phase].getPixelWriter();
		for (int x = 0; x < mask.getWidth(); x++) {
			for (int y = 0; y < mask.getHeight(); y++) {
				Color originalColor = sprite.getPixelReader()
						.getColor(x + offset, y);
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
		
		g.drawImage(composite[currentPhase], screenPos.x, screenPos.y,
				screenSize.x, screenSize.y);

	}

	@Override
	public void onTick(long nanosSincePreviousTick) {
		long phaseDurationNanos = ((long) phaseDuration) * 1000000;

		if (phaseNanosCounter + nanosSincePreviousTick > phaseDurationNanos) {
			if (currentPhase >= numPhases - 1) {
				currentPhase = 0;
			} else {
				currentPhase++;
			}
			phaseNanosCounter = nanosSincePreviousTick - phaseDurationNanos;
		} else {
			phaseNanosCounter += nanosSincePreviousTick;
		}
	}

	@Override
	public void onGameObjectRemoved() {
	}

}
