package gef5.mvc.tutorial.policies;

import java.util.*;

import org.eclipse.core.commands.*;
import org.eclipse.core.commands.operations.*;
import org.eclipse.gef.mvc.fx.handlers.*;
import org.eclipse.gef.mvc.fx.handlers.AbstractHandler;

import com.google.inject.*;

import javafx.scene.input.*;

public class GlobalOnTypePolicy extends AbstractHandler implements IOnTypeHandler {

	private static final KeyCodeCombination ctrlY = new KeyCodeCombination(KeyCode.Y, KeyCombination.CONTROL_DOWN);
	private static final KeyCodeCombination ctrlZ = new KeyCodeCombination(KeyCode.Z, KeyCombination.CONTROL_DOWN);

	@Inject
	IOperationHistory history;
	@Inject
	IUndoContext undoContext;

	@Override
	public void type(KeyEvent event, Set<KeyCode> pressedKeys) {
		if (ctrlZ.match(event)) {
			try {
				history.undo(undoContext, null, null);
			} catch (ExecutionException e) {
				throw new RuntimeException(e);
			}
		}
		if (ctrlY.match(event)) {
			try {
				history.redo(undoContext, null, null);
			} catch (ExecutionException e) {
				throw new RuntimeException(e);
			}
		}
	}

}
