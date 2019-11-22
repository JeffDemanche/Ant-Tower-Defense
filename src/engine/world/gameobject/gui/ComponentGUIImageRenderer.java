package engine.world.gameobject.gui;

import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import application.Vec2d;
import engine.world.gameobject.Component;
import engine.world.gameobject.Drawable;
import engine.world.gameobject.GameObject;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class ComponentGUIImageRenderer extends Component {

	private Vec2d screenPos;
	private Vec2d screenSize;

	private Drawable drawable;

	private double cropX;
	private double cropY;

	private Image image;

	public ComponentGUIImageRenderer(GameObject object, Vec2d screenPos,
			Vec2d screenSize, double sampleMultiplier, String imagePath) {
		super("GUI Element", object);
		this.screenPos = screenPos;
		this.screenSize = screenSize;

		Image lowRes = new Image(imagePath);
		this.image = new Image(imagePath, lowRes.getWidth() * sampleMultiplier,
				lowRes.getHeight() * sampleMultiplier, true, false);

		this.cropX = this.image.getWidth();
		this.cropY = this.image.getHeight();
	}

	public ComponentGUIImageRenderer(GameObject object, Drawable drawable,
			double sampleMultiplier, String imagePath) {
		super("GUI Element", object);
		this.drawable = drawable;

		Image lowRes = new Image(imagePath);
		this.image = new Image(imagePath, lowRes.getWidth() * sampleMultiplier,
				lowRes.getHeight() * sampleMultiplier, true, false);
	}

	public void setScreenPosition(Vec2d pos) {
		this.screenPos = pos;
	}
	
	public void setScreenSize(Vec2d size) {
		this.screenSize = size;
	}
	
	public void setCropPixels(double cropX, double cropY) {
		this.cropX = cropX;
		this.cropY = cropY;
	}

	/**
	 * 1 = no crop, 0.5 = crop half of image.
	 */
	public void setCropPercent(double decimalX, double decimalY) {
		decimalX = Math.max(0, Math.min(1, decimalX));
		decimalY = Math.max(0, Math.min(1, decimalY));

		this.cropX = image.getWidth() * decimalX;
		this.cropY = image.getHeight() * decimalY;
	}

	@Override
	public void onDraw(GraphicsContext g) {
		if (drawable == null) {
			double croppedWidth = screenSize.x
					* (this.cropX / this.image.getWidth());
			double croppedHeight = screenSize.y
					* (this.cropY / this.image.getHeight());

			g.drawImage(this.image, 0, 0, this.cropX, this.cropY, screenPos.x,
					screenPos.y, croppedWidth, croppedHeight);
		} else {
			g.drawImage(this.image, 0, 0, this.image.getWidth(),
					this.image.getHeight(), drawable.getScreenPosition().x,
					drawable.getScreenPosition().y, drawable.getScreenSize().x,
					drawable.getScreenSize().y);
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

		Element componentGUIImageRenderer = doc
				.createElement("ComponentGUIImageRenderer");
		return componentGUIImageRenderer;
	}

}
