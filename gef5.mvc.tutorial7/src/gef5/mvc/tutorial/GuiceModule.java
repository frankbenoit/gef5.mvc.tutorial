package gef5.mvc.tutorial;

import org.eclipse.gef.common.adapt.*;
import org.eclipse.gef.common.adapt.inject.*;
import org.eclipse.gef.mvc.fx.*;
import org.eclipse.gef.mvc.fx.behaviors.*;
import org.eclipse.gef.mvc.fx.handlers.*;
import org.eclipse.gef.mvc.fx.parts.*;
import org.eclipse.gef.mvc.fx.providers.*;

import com.google.inject.*;
import com.google.inject.multibindings.*;

import gef5.mvc.tutorial.parts.*;
import gef5.mvc.tutorial.policies.*;

public final class GuiceModule extends MvcFxModule {

	@Override
	protected void bindAbstractContentPartAdapters(MapBinder<AdapterKey<?>, Object> adapterMapBinder) {
		super.bindAbstractContentPartAdapters(adapterMapBinder);

		adapterMapBinder.addBinding(AdapterKey.defaultRole()).to(FocusAndSelectOnClickHandler.class);

		adapterMapBinder.addBinding(AdapterKey.defaultRole()).to(HoverOnHoverHandler.class);

	}

	protected void bindTextNodePartAdapters(MapBinder<AdapterKey<?>, Object> adapterMapBinder) {
		adapterMapBinder
				.addBinding(AdapterKey.role(DefaultSelectionFeedbackPartFactory.SELECTION_FEEDBACK_GEOMETRY_PROVIDER))
				.to(ShapeOutlineProvider.class);

		// geometry provider for selection handles
		adapterMapBinder
				.addBinding(AdapterKey.role(DefaultSelectionHandlePartFactory.SELECTION_HANDLES_GEOMETRY_PROVIDER))
				.to(ShapeOutlineProvider.class);

		adapterMapBinder
				.addBinding(
						AdapterKey.role(DefaultSelectionFeedbackPartFactory.SELECTION_LINK_FEEDBACK_GEOMETRY_PROVIDER))
				.to(ShapeOutlineProvider.class);

		// geometry provider for hover feedback
		adapterMapBinder.addBinding(AdapterKey.role(DefaultHoverFeedbackPartFactory.HOVER_FEEDBACK_GEOMETRY_PROVIDER))
				.to(ShapeOutlineProvider.class);

		// register resize/transform policies (writing changes also to model)
		adapterMapBinder.addBinding(AdapterKey.defaultRole()).to(TextNodeTransformPolicy.class);

		// interaction policies to relocate on drag (including anchored
		// elements, which are linked)
		adapterMapBinder.addBinding(AdapterKey.defaultRole()).to(TranslateSelectedOnDragHandler.class);

		// edit node label policies
		adapterMapBinder.addBinding(AdapterKey.defaultRole()).to(TextNodeOnDoubleClickPolicy.class);

		adapterMapBinder.addBinding(AdapterKey.defaultRole()).to(TextNodeOnTypePolicy.class);

		adapterMapBinder.addBinding(AdapterKey.defaultRole()).to(GlobalOnTypePolicy.class);

		// interaction policy to delete on key type
		adapterMapBinder.addBinding(AdapterKey.defaultRole()).to(DeleteSelectedOnTypeHandler.class);
	}

	@Override
	protected void bindIRootPartAdaptersForContentViewer(MapBinder<AdapterKey<?>, Object> adapterMapBinder) {
		super.bindIRootPartAdaptersForContentViewer(adapterMapBinder);

		adapterMapBinder.addBinding(AdapterKey.defaultRole()).to(GlobalOnTypePolicy.class);

		adapterMapBinder.addBinding(AdapterKey.defaultRole()).to(CreationMenuOnClickPolicy.class);
	}

	@Override
	protected void bindHoverHandlePartFactoryAsContentViewerAdapter(MapBinder<AdapterKey<?>, Object> adapterMapBinder) {

		adapterMapBinder.addBinding(AdapterKey.role(HoverBehavior.HOVER_HANDLE_PART_FACTORY))
				.to(HandlePartFactory.class);
	}

	protected void bindDeleteHoverHandlePartAdapters(MapBinder<AdapterKey<?>, Object> adapterMapBinder) {
		adapterMapBinder.addBinding(AdapterKey.role("0")).to(DeleteOnClickPolicy.class);
	}

	@Override
	protected void configure() {
		super.configure();

		binder().bind(GlobalOnTypePolicy.class).in(Scopes.SINGLETON);

		binder().bind(IContentPartFactory.class).toInstance(new ContentPartFactory());

		bindTextNodePartAdapters(AdapterMaps.getAdapterMapBinder(binder(), TextNodePart.class));

		bindDeleteHoverHandlePartAdapters(AdapterMaps.getAdapterMapBinder(binder(), DeleteHoverHandlePart.class));

	}
}