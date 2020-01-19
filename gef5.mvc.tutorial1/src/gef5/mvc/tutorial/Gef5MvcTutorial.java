package gef5.mvc.tutorial;

import java.util.*;

import org.eclipse.gef.mvc.fx.*;
import org.eclipse.gef.mvc.fx.domain.*;
import org.eclipse.gef.mvc.fx.parts.*;
import org.eclipse.gef.mvc.fx.viewer.*;

import com.google.inject.*;

import gef5.mvc.tutorial.model.*;
import gef5.mvc.tutorial.parts.*;
import javafx.application.*;
import javafx.scene.*;
import javafx.stage.Stage;

public class Gef5MvcTutorial extends Application {

	public static void main(String[] args) {
		Application.launch(args);
	}

	@Override
	public void start(final Stage primaryStage) throws Exception {

		Injector injector = Guice.createInjector(createGuiceModule());

		IDomain domain = injector.getInstance(IDomain.class);

		// hook the (single) viewer into the stage
		IViewer viewer = domain.getAdapter(IViewer.class);
		primaryStage.setScene(new Scene(viewer.getCanvas()));

		primaryStage.setResizable(true);
		primaryStage.setWidth(640);
		primaryStage.setHeight(480);
		primaryStage.setTitle("GEF4 MVC Tutorial 1 - minimal MVC");
		primaryStage.sizeToScene();
		primaryStage.show();

		// activate domain only after viewers have been hooked
		domain.activate();

		// set viewer contents
		viewer.getContents().setAll(createContents());
	}

	protected List<? extends Object> createContents() {
		return Collections.singletonList(new Model());
	}

	protected Module createGuiceModule() {
		return new MvcFxModule() {
			@Override
			protected void configure() {
				super.configure();

				binder().bind(IContentPartFactory.class).to(ModelPartFactory.class);

			}
		};
	}
}
