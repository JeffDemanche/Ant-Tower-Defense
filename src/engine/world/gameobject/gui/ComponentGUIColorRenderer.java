package engine.world.gameobject.gui;

import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import application.Vec2d;
import engine.world.gameobject.Component;
import engine.world.gameobject.GameObject;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class ComponentGUIColorRenderer extends Component {

	private Vec2d screenPos;
	private Vec2d screenSize;

	private double cropX;
	private double cropY;

	private Image image;

	public ComponentGUIColorRenderer(GameObject object, ComponentGUIDrawable drawable, String imagePath) {
		super("GUI Element", object);
		this.screenPos = drawable.getScreenPosition();
		this.screenSize = drawable.getSize();
		
		this.cropX = this.image.getWidth();
		this.cropY = this.image.getHeight();
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
		double croppedWidth = screenSize.x
				* (this.cropX / this.image.getWidth());
		double croppedHeight = screenSize.y
				* (this.cropY / this.image.getHeight());

		g.drawImage(this.image, 0, 0, this.cropX, this.cropY, screenPos.x,
				screenPos.y, croppedWidth, croppedHeight);
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
