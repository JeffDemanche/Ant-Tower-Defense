package engine.world.gameobject;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class ComponentSolidColorPolygon extends Component {

	private Color color;
	private ComponentPolygon polygon;

	public ComponentSolidColorPolygon(GameObject object, Color color,
			ComponentPolygon polygon) {
		super("Solid Color Polygon", object);
		this.color = color;
		this.polygon = polygon;
	}

	@Override
	public void onDraw(GraphicsContext g) {
		double[] xPoints = new double[this.polygon.getNumPoints()];
		double[] yPoints = new double[this.polygon.getNumPoints()];

		for (int i = 0; i < this.polygon.getNumPoints(); i++) {
			xPoints[i] = this.polygon.getScreenPoint(i).x;
			yPoints[i] = this.polygon.getScreenPoint(i).y;
		}

		g.save();
		g.setFill(this.color);
		g.fillPolygon(xPoints, yPoints, polygon.getNumPoints());
		g.restore();
	}

	@Override
	public void onTick(long nanosSincePreviousTick) {
	}

	@Override
	public void onGameObjectRemoved() {
	}

}
