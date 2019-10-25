package engine.ui;

import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;

/**
 * A class for creating gradients, which are nicer than solid colors :)
 * 
 * @author jdemanch
 */
public class GradientFactory {

	public static LinearGradient createButtonGradient(Color topLeft, Color bottomRight) {
		Stop[] stops = new Stop[] { new Stop(0, topLeft), new Stop(1, bottomRight)};
		return new LinearGradient(0, 0, 1, 1, true, CycleMethod.NO_CYCLE, stops);
	}
	
}
