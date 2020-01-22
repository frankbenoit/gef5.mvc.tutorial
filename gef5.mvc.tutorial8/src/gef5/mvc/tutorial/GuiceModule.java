package gef5.mvc.tutorial;

import org.eclipse.gef.common.adapt.*;
import org.eclipse.gef.common.adapt.inject.*;
import org.eclipse.gef.mvc.fx.*;
import org.eclipse.gef.mvc.fx.handlers.*;
import org.eclipse.gef.mvc.fx.parts.*;
import org.eclipse.gef.mvc.fx.providers.*;

import com.google.inject.*;
import com.google.inject.multibindings.*;

import gef5.mvc.tutorial.parts.*;
import gef5.mvc.tutorial.parts.SideAnchorProvider;
import gef5.mvc.tutorial.policies.*;

@SuppressWarnings("serial")
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

		// interaction policies to relocate on drag (including anchored
		// elements, which are linked)
		adapterMapBinder.addBinding(AdapterKey.defaultRole()).to(TranslateSelectedOnDragHandler.class);

		adapterMapBinder.addBinding(AdapterKey.defaultRole()).to(SideAnchorProvider.class);

	}

	private void bindTextNodePartRelationAdapters(MapBinder<AdapterKey<?>, Object> adapterMapBinder) {
	}

	@Override
	protected void bindAbstractRootPartAdapters(MapBinder<AdapterKey<?>, Object> adapterMapBinder) {
		super.bindAbstractRootPartAdapters(adapterMapBinder);

		adapterMapBinder.addBinding(AdapterKey.defaultRole()).to(GlobalOnTypePolicy.class);

	}

	@Override
	protected void configure() {
		super.configure();

		binder().bind(GlobalOnTypePolicy.class).in(Scopes.SINGLETON);

		binder().bind(new TypeLiteral<IContentPartFactory>() {
		}).toInstance(new ContentPartFactory());

		bindTextNodePartAdapters(AdapterMaps.getAdapterMapBinder(binder(), TextNodePart.class));

		bindTextNodePartRelationAdapters(AdapterMaps.getAdapterMapBinder(binder(), TextNodeRelationPart.class));

	}

}