package engine.ui;

import application.Vec2d;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

/**
 * A text-rendering UI element.
 * 
 * @author jdemanch
 */
public class UIImage extends UIElement {

	private String imagePath;
	private int sampleMultiplier;
	private Image image;

	public UIImage(Vec2d position, Vec2d size, String imagePath,
			int sampleMultiplier) {
		super(position, size);

		this.imagePath = imagePath;
		this.sampleMultiplier = sampleMultiplier;
	}

	@Override
	public void onStartup() {
		super.onStartup();
		
		Image lowRes = new Image(imagePath);
		this.image = new Image(imagePath, lowRes.getWidth() * sampleMultiplier,
				lowRes.getHeight() * sampleMultiplier, true, false);
	}
	
	@Override
	public void onDraw(GraphicsContext g) {
		super.onDraw(g);

		if (this.getSize() != null)
			g.drawImage(image, this.getAbsoutePosition().x,
					this.getAbsoutePosition().y, this.getSize().x,
					this.getSize().y);
		else
			g.drawImage(image, this.getAbsoutePosition().x,
					this.getAbsoutePosition().y);

	}

}
