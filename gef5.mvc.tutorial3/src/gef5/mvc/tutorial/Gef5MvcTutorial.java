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
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class Gef5MvcTutorial extends Application {

	private Model model;

	public static void main(String[] args) {
		Application.launch(args);
	}

	@Override
	public void start(final Stage primaryStage) throws Exception {

		Injector injector = Guice.createInjector(createGuiceModule());

		IDomain domain = injector.getInstance(IDomain.class);

		IViewer viewer = domain.getAdapter(IViewer.class);

		AnchorPane paneCtrl = new AnchorPane();
		AnchorPane paneDraw = new AnchorPane();
		VBox vbox = new VBox(paneCtrl, paneDraw);
		vbox.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);

		Button btnUpdateModel = new Button("update model");
		btnUpdateModel.setOnAction(e -> model.doChanges());
		btnUpdateModel.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
		paneCtrl.getChildren().add(btnUpdateModel);
		AnchorPane.setTopAnchor(btnUpdateModel, 10d);
		AnchorPane.setLeftAnchor(btnUpdateModel, 10d);
		AnchorPane.setRightAnchor(btnUpdateModel, 10d);

		Parent drawingPane = viewer.getCanvas();
		paneDraw.getChildren().add(drawingPane);
		paneDraw.setPrefHeight(2000);
		AnchorPane.setTopAnchor(drawingPane, 10d);
		AnchorPane.setLeftAnchor(drawingPane, 10d);
		AnchorPane.setRightAnchor(drawingPane, 10d);
		AnchorPane.setBottomAnchor(drawingPane, 10d);

		primaryStage.setScene(new Scene(vbox));

		primaryStage.setResizable(true);
		primaryStage.setWidth(640);
		primaryStage.setHeight(480);
		primaryStage.setTitle("GEF4 MVC Tutorial 3 - 2 level model");
		primaryStage.show();

		domain.activate();

		viewer.getContents().setAll(createContents());
	}

	protected List<? extends Object> createContents() {
		model = new Model();
		return Collections.singletonList(model);
	}

	protected Module createGuiceModule() {
		return new MvcFxModule() {
			@Override
			protected void configure() {
				super.configure();

				binder().bind(new TypeLiteral<IContentPartFactory>() {
				}).toInstance(new ModelPartFactory());

			}
		};
	}
}
