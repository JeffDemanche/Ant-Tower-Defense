package engine.world.gameobject;

import java.util.ArrayList;
import java.util.HashMap;

import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import application.Vec2d;
import engine.world.WorldError;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

/**
 * Allows for an animated sprite from a spritesheet.
 * 
 * @author jdemanch
 */
public class ComponentDynamicSprite extends Component {

	private class SpritePhase {
		int translateX;
		int translateY;
		int sizeX;
		int sizeY;

		SpritePhase(int translateX, int translateY, int sizeX, int sizeY) {
			this.translateX = translateX;
			this.translateY = translateY;
			this.sizeX = sizeX;
			this.sizeY = sizeY;
		}
	}

	private boolean flipX;

	private Image spriteSheet;
	private Drawable drawable;
	private int sampleMultiplier;

	private HashMap<String, ArrayList<SpritePhase>> phases;
	private String currentAnimation;
	private String nextAnimation;
	private int currentPhase;

	/** In milliseconds. */
	private int phaseDuration;

	private long phaseNanosCounter;

	/**
	 * Legacy constructor.
	 */
	public ComponentDynamicSprite(String tag, GameObject object,
			String spriteSheetPath, Drawable drawable, int sampleMultiplier,
			int phaseDuration) {
		this(object, spriteSheetPath, drawable, sampleMultiplier,
				phaseDuration);
	}

	public ComponentDynamicSprite(GameObject object, String spriteSheetPath,
			Drawable drawable, int sampleMultiplier, int phaseDuration) {
		super("Dynamic Sprite", object);
		this.flipX = false;

		Image lowRes = new Image(spriteSheetPath);
		this.spriteSheet = new Image(spriteSheetPath,
				lowRes.getWidth() * sampleMultiplier,
				lowRes.getHeight() * sampleMultiplier, true, false);
		this.drawable = drawable;
		this.sampleMultiplier = sampleMultiplier;

		this.phaseDuration = phaseDuration;
		this.phases = new HashMap<String, ArrayList<SpritePhase>>();
		this.currentPhase = 0;
		this.phaseNanosCounter = 0;
	}

	public void setFlipX(boolean flipX) {
		this.flipX = flipX;
	}

	/**
	 * Adds a phase to one of this sprite's animation, which will be cycled
	 * through during animation. Parameters to this determine pixel bounds of
	 * the sheet are rendered.
	 */
	public void addPhaseToAnimation(String animation, int spriteX, int spriteY,
			int cropX, int cropY) {
		if (this.phases.keySet().size() == 0) {
			this.currentAnimation = animation;
		}

		if (!this.phases.containsKey(animation)) {
			this.phases.put(animation, new ArrayList<>());
		}
		this.phases.get(animation)
				.add(new SpritePhase(spriteX, spriteY, cropX, cropY));
	}

	public void setAnimation(String animation) {
		if (phases.containsKey(animation)) {
			if (currentAnimation == null || nextAnimation == null) {
				currentAnimation = animation;
				nextAnimation = animation;
				currentPhase = 0;
			} else if (!currentAnimation.equals(animation)) {
				currentAnimation = animation;
				nextAnimation = animation;
				currentPhase = 0;
			}
		} else {
			throw new WorldError("Animation " + animation + " doesn't exist.");
		}
	}

	public void playAnimation(String animation) {
		if (phases.containsKey(animation)) {
			if (!currentAnimation.equals(animation)) {
				nextAnimation = currentAnimation;
				currentAnimation = animation;
				currentPhase = 0;
			}
		} else {
			throw new WorldError("Animation " + animation + " doesn't exist.");
		}
	}

	public String getAnimation() {
		return this.currentAnimation;
	}

	@Override
	public void onDraw(GraphicsContext g) {
		Vec2d screenPos = this.drawable.getScreenPosition();
		Vec2d screenSize = this.drawable.getScreenSize();

		if (phases.size() > 0) {
			SpritePhase phase = this.phases.get(currentAnimation)
					.get(currentPhase);

			if (flipX) {
				g.drawImage(this.spriteSheet,
						(phase.translateX + phase.sizeX) * sampleMultiplier,
						phase.translateY * sampleMultiplier,
						-phase.sizeX * sampleMultiplier,
						phase.sizeY * sampleMultiplier, screenPos.x,
						screenPos.y, screenSize.x, screenSize.y);
			} else
				g.drawImage(this.spriteSheet,
						phase.translateX * sampleMultiplier,
						phase.translateY * sampleMultiplier,
						phase.sizeX * sampleMultiplier,
						phase.sizeY * sampleMultiplier, screenPos.x,
						screenPos.y, screenSize.x, screenSize.y);
		}
	}

	@Override
	public void onTick(long nanosSincePreviousTick) {
		long phaseDurationNanos = ((long) phaseDuration) * 1000000;

		if (phaseNanosCounter + nanosSincePreviousTick > phaseDurationNanos) {
			if (currentPhase >= this.phases.get(currentAnimation).size() - 1) {
				currentPhase = 0;
				currentAnimation = nextAnimation;
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

	@Override
	public Element writeXML(Document doc) throws ParserConfigurationException {
		Element componentDynamicSprite = doc.createElement("ComponentDynamicSprite");
		return componentDynamicSprite;
	}

}
