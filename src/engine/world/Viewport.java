package engine.world;

import application.Vec2d;
import engine.world.serialization.XMLSerializable;
import javafx.geometry.Point2D;
import javafx.scene.transform.Affine;
import javafx.scene.transform.NonInvertibleTransformException;

/**
 * The viewport is an interaction between the UI engine and the game world and
 * also handles view transform stuff.
 * 
 * @author jdemanch
 */
public abstract class Viewport implements XMLSerializable {

	private double initialScale;
	private double scale;

	private double zoomSpeed;
	private boolean mouseBasedScrolling;

	private Vec2d screenSize;

	/**
	 * This is necessary to get the origin at the center of the screen when the
	 * viewport's initialized.
	 */
	private Vec2d initialTransform;
	private double centerPosX;
	private double centerPosY;

	public Viewport(Vec2d initialScreenSize) {
		this(initialScreenSize, 0.05);
	}

	public Viewport(Vec2d initialScreenSize, double zoomSpeed) {
		this.initialScale = 1 / 40.0;
		this.screenSize = initialScreenSize;
		this.scale = 1;
		this.initialTransform = new Vec2d(-initialScreenSize.x / 2,
				-initialScreenSize.y / 2);
		this.centerPosX = 0;
		this.centerPosY = 0;
		this.zoomSpeed = zoomSpeed;

		this.mouseBasedScrolling = false;
	}

	public void setMouseBasedScrolling(boolean mouseBased) {
		this.mouseBasedScrolling = mouseBased;
	}

	public void setViewportCenter(Vec2d centerPos) {
		this.centerPosX = centerPos.x;
		this.centerPosY = centerPos.y;
	}

	public Vec2d getViewportCenter() {
		return new Vec2d(centerPosX, centerPosY);
	}

	public void setScale(double scale) {
		this.scale = scale;
	}

	public double getScale() {
		return this.scale;
	}

	public Vec2d getScreenSize() {
		return this.screenSize;
	}

	public void onResize(Vec2d newSize) {
		this.screenSize = newSize;
	}

	public void translateViewport(double x, double y) {
		centerPosX += x;
		centerPosY += y;
	}

	public void tickTrackedViewport(double x, double y) {
		Vec2d newCoords = toScreenSpace(new Vec2d(x, y), false);
		centerPosX += newCoords.x - screenSize.x / 2;
		centerPosY += newCoords.y - screenSize.y / 2;
	}

	public void scaleViewport(double scale) {
		this.scale *= scale;
	}

	public void zoomViewport(double mouseDelta, Vec2d mousePosition) {
		Vec2d gsMousePos = toGameSpace(mousePosition, false);

		double mouseOffsetX = mouseBasedScrolling ? gsMousePos.x - centerPosX
				: 0;
		double mouseOffsetY = mouseBasedScrolling ? gsMousePos.y : 0;

		if (mouseDelta == 0) {
			return;
		} else if (mouseDelta > 0) {
			double delta = 1 - zoomSpeed;
			this.scale *= delta;
			this.centerPosX += this.centerPosX * zoomSpeed;
			this.centerPosY += this.centerPosY * zoomSpeed;
		} else {
			double delta = 1 + zoomSpeed;
			this.scale *= delta;
			this.centerPosX -= this.centerPosX * zoomSpeed;
			this.centerPosY -= this.centerPosY * zoomSpeed;
		}
	}

	/**
	 * Gives a screen coordinate based on a game position.
	 * 
	 * @param gamePos
	 *            The position in game space to convert to screen space.
	 * @param size
	 *            If true, ignores translations, giving an accurate
	 *            transformation for size vectors.
	 * @return The position in screen space, relative to the viewport origin.
	 */
	public Vec2d toScreenSpace(Vec2d gamePos, boolean size) {
		Affine affine = new Affine();

		affine.appendScale(initialScale, initialScale);
		affine.appendScale(scale, scale);

		if (!size) {
			affine.appendTranslation(initialTransform.x, initialTransform.y);
			affine.appendTranslation(centerPosX, centerPosY);
		}

		try {
			affine = affine.createInverse();

			Point2D transformed = affine
					.transform(new Point2D(gamePos.x, gamePos.y));

			return new Vec2d(transformed.getX(), transformed.getY());
		} catch (NonInvertibleTransformException e) {
			e.printStackTrace();
		}

		return null;
	}

	public Vec2d toGameSpace(Vec2d screenPos, boolean size) {
		Affine affine = new Affine();

		affine.appendScale(initialScale, initialScale);
		affine.appendScale(scale, scale);
		affine.appendTranslation(initialTransform.x, initialTransform.y);
		affine.appendTranslation(centerPosX, centerPosY);

		Point2D transformed = affine
				.transform(new Point2D(screenPos.x, screenPos.y));

		return new Vec2d(transformed.getX(), transformed.getY());
	}

}
