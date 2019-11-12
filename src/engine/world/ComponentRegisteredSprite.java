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

/**
 * Can make a static or dynamic sprite from a pregregistered image (such as from
 * SpriteRegistry).
 * 
 * @author jdemanch
 */
public class ComponentRegisteredSprite extends Component {

	private Image[] composite;

	private Drawable drawable;

	/** -1 for non-animated sprites. */
	private int phaseDuration;
	private int numPhases;

	private int currentPhase;
	private long phaseNanosCounter;

	/**
	 * Constructor for single-frame sprites.
	 */
	public ComponentRegisteredSprite(GameObject object, Image sprite,
			Drawable drawable) {
		super("Masked Sprite", object);

		this.composite = new Image[] { sprite };

		this.phaseDuration = -1;
		this.currentPhase = 0;
		this.phaseNanosCounter = 0;
		this.numPhases = 0;

		this.drawable = drawable;
	}

	public ComponentRegisteredSprite(GameObject object, Image[] sprites,
			Drawable drawable, int phaseDuration, int numPhases) {
		super("Masked Sprite", object);

		this.composite = sprites;

		this.phaseDuration = phaseDuration;
		this.currentPhase = 0;
		this.phaseNanosCounter = 0;
		this.numPhases = numPhases;

		this.drawable = drawable;
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
