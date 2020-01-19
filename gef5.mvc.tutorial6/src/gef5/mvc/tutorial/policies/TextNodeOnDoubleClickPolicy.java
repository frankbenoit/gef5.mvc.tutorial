package gef5.mvc.tutorial.policies;

import org.eclipse.gef.mvc.fx.handlers.*;

import gef5.mvc.tutorial.parts.*;
import javafx.scene.input.*;

// only applicable for NodeContentPart (see #getHost())
public class TextNodeOnDoubleClickPolicy extends AbstractHandler implements IOnClickHandler {

	@Override
	public void click(MouseEvent e) {
		if (e.getClickCount() == 2 && e.isPrimaryButtonDown()) {
			getHost().editModeStart();
		}
	}

	@Override
	public TextNodePart getHost() {
		return (TextNodePart) super.getHost();
	}

}
