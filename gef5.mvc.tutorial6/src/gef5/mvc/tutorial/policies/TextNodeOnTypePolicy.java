package gef5.mvc.tutorial.policies;

import java.util.*;

import org.eclipse.gef.mvc.fx.handlers.*;

import gef5.mvc.tutorial.parts.*;
import javafx.scene.input.*;

//only applicable for NodeContentPart (see #getHost())
public class TextNodeOnTypePolicy extends AbstractHandler implements IOnTypeHandler {

	@Override
	public TextNodePart getHost() {
		return (TextNodePart) super.getHost();
	}

	@Override
	public void type(KeyEvent event, Set<KeyCode> pressedKeys) {
		if (KeyCode.F2.equals(event.getCode()) && !getHost().isEditing()) {
			System.out.println("ExitEditingNodeLabelOnEnterPolicy.pressed() 1");
			getHost().editModeStart();
		} else if (KeyCode.ENTER.equals(event.getCode())) {
			if (getHost().isEditing()) {
				System.out.println("ExitEditingNodeLabelOnEnterPolicy.pressed() 2");
				event.consume();
				getHost().editModeEnd(true);
			} else {
				System.out.println("ExitEditingNodeLabelOnEnterPolicy.pressed() 3");
				event.consume();
				getHost().editModeStart();
			}
		} else if (KeyCode.ESCAPE.equals(event.getCode())) {
			event.consume();
			getHost().editModeEnd(false);
		}
	}
}
