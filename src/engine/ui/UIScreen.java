package engine.ui;

import application.Vec2d;
import javafx.scene.input.MouseEvent;

/**
 * A class which contains all UI elements in a screen (and is therefore the
 * parent of all screen elements). Can be used to distribute events to all
 * children in the scene (like mouse hovering).
 * 
 * @author jdemanch
 */
public class UIScreen extends UIElement {

	@Override
	public void onMouseMoved(MouseEvent e) {
		super.onMouseMoved(e);

		this.getAllChildren().forEach(child -> {
			if (inBox(child.getAbsoutePosition(), child.getSize(),
					new Vec2d(e.getSceneX(), e.getSceneY()))) {
				child.setMouseOver(true);
			} else {
				child.setMouseOver(false);
			}
		});
	}

}
