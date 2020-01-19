package gef5.mvc.tutorial;

import java.io.*;
import java.nio.file.*;
import java.util.*;

import javax.xml.bind.*;

import org.eclipse.gef.common.adapt.*;
import org.eclipse.gef.common.adapt.inject.*;
import org.eclipse.gef.mvc.fx.*;
import org.eclipse.gef.mvc.fx.domain.*;
import org.eclipse.gef.mvc.fx.handlers.*;
import org.eclipse.gef.mvc.fx.parts.*;
import org.eclipse.gef.mvc.fx.providers.*;
import org.eclipse.gef.mvc.fx.viewer.*;

import com.google.inject.*;
import com.google.inject.multibindings.*;

import gef5.mvc.tutorial.model.*;
import gef5.mvc.tutorial.parts.*;
import gef5.mvc.tutorial.policies.*;
import javafx.application.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class Gef5MvcTutorial extends Application {

	private Model model;
	private JAXBContext jaxbContext;

	public static void main(String[] args) {
		Application.launch(args);
	}

	@Override
	public void start(final Stage primaryStage) throws Exception {
		jaxbContext = JAXBContext.newInstance(Model.class, TextNode.class);

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
		primaryStage.setTitle("GEF4 MVC Tutorial 4 - Drag and persist");

		primaryStage.show();

		domain.activate();

		viewer.getContents().setAll(createContents());
	}

	@Override
	public void stop() throws Exception {
		super.stop();
		try {
			Marshaller marshaller = jaxbContext.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
			marshaller.marshal(model, new File("model.xml"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected List<? extends Object> createContents() {
		if (Files.isReadable(Paths.get("model.xml"))) {
			try {
				Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
				model = (Model) jaxbUnmarshaller.unmarshal(new File("model.xml"));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		if (model == null) {
			model = new Model();
			model.addNode(new TextNode(20, 20, "First"));
			model.addNode(new TextNode(20, 120, "Second"));
		}

		return Collections.singletonList(model);
	}

	protected Module createGuiceModule() {
		return new MvcFxModule() {

			@Override
			protected void bindAbstractContentPartAdapters(MapBinder<AdapterKey<?>, Object> adapterMapBinder) {
				super.bindAbstractContentPartAdapters(adapterMapBinder);
				// register (default) interaction policies (which are based on
				// viewer
				// models and do not depend on transaction policies)

				adapterMapBinder.addBinding(AdapterKey.defaultRole()).to(FocusAndSelectOnClickHandler.class);

				adapterMapBinder.addBinding(AdapterKey.defaultRole()).to(HoverOnHoverHandler.class);

				// geometry provider for selection feedback
				adapterMapBinder.addBinding(AdapterKey.defaultRole()).to(GeometricBoundsProvider.class);

				// geometry provider for hover feedback
				adapterMapBinder.addBinding(AdapterKey.role("4")).to(GeometricBoundsProvider.class);
			}

			protected void bindTextNodePartAdapters(MapBinder<AdapterKey<?>, Object> adapterMapBinder) {
				adapterMapBinder
						.addBinding(AdapterKey
								.role(DefaultSelectionFeedbackPartFactory.SELECTION_FEEDBACK_GEOMETRY_PROVIDER))
						.to(ShapeOutlineProvider.class);

				// geometry provider for selection handles
				adapterMapBinder
						.addBinding(
								AdapterKey.role(DefaultSelectionHandlePartFactory.SELECTION_HANDLES_GEOMETRY_PROVIDER))
						.to(ShapeOutlineProvider.class);

				adapterMapBinder
						.addBinding(AdapterKey
								.role(DefaultSelectionFeedbackPartFactory.SELECTION_LINK_FEEDBACK_GEOMETRY_PROVIDER))
						.to(ShapeOutlineProvider.class);

				// geometry provider for hover feedback
				adapterMapBinder
						.addBinding(AdapterKey.role(DefaultHoverFeedbackPartFactory.HOVER_FEEDBACK_GEOMETRY_PROVIDER))
						.to(ShapeOutlineProvider.class);

				// register resize/transform policies (writing changes also to
				// model)
				adapterMapBinder.addBinding(AdapterKey.defaultRole()).to(TextNodeTransformPolicy.class);
				// .to(FXTransformPolicy.class);

				// interaction policies to relocate on drag (including anchored
				// elements, which are linked)
				adapterMapBinder.addBinding(AdapterKey.defaultRole()).to(TranslateSelectedOnDragHandler.class);
			}

			@Override
			protected void configure() {
				super.configure();

				binder().bind(new TypeLiteral<IContentPartFactory>() {
				}).toInstance(new ModelPartFactory());

				bindTextNodePartAdapters(AdapterMaps.getAdapterMapBinder(binder(), TextNodePart.class));

			}
		};
	}
}
