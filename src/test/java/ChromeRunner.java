import org.jbehave.core.Embeddable;
import org.jbehave.core.configuration.Configuration;
import org.jbehave.core.configuration.MostUsefulConfiguration;
import org.jbehave.core.embedder.EmbedderControls;
import org.jbehave.core.i18n.LocalizedKeywords;
import org.jbehave.core.io.CodeLocations;
import org.jbehave.core.io.LoadFromClasspath;
import org.jbehave.core.io.LoadFromURL;
import org.jbehave.core.io.StoryFinder;
import org.jbehave.core.junit.JUnitStories;
import org.jbehave.core.model.ExamplesTableFactory;
import org.jbehave.core.model.TableTransformers;
import org.jbehave.core.parsers.RegexStoryParser;
import org.jbehave.core.reporters.FilePrintStreamFactory;
import org.jbehave.core.reporters.Format;
import org.jbehave.core.reporters.StoryReporterBuilder;
import org.jbehave.core.steps.InjectableStepsFactory;
import org.jbehave.core.steps.InstanceStepsFactory;
import org.jbehave.core.steps.ParameterConverters;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.jbehave.core.io.CodeLocations.codeLocationFromClass;
import static org.jbehave.core.reporters.Format.*;

public class ChromeRunner extends JUnitStories {
//    public ChromeRunner() {
//        EmbedderControls useStoryTimeoutInSecs = configuredEmbedder().embedderControls().doGenerateViewAfterStories(true).doIgnoreFailureInStories(true)
//                .doIgnoreFailureInView(true).useThreads(2).useStoryTimeoutInSecs(60);
//    }

    @Override
    public Configuration configuration(){
        return new MostUsefulConfiguration().useStoryReporterBuilder(getReporter()).
                useStoryLoader(new LoadFromURL());
    }

//    @Override
//    public Configuration configuration() {
//        Class<? extends Embeddable> embeddableClass = this.getClass();
//        // Start from default ParameterConverters instance
//        ParameterConverters parameterConverters = new ParameterConverters();
//        // factory to allow parameter conversion and loading from external resources (used by StoryParser too)
//        ExamplesTableFactory examplesTableFactory = new ExamplesTableFactory(new LocalizedKeywords(), new LoadFromClasspath(embeddableClass), parameterConverters, null, new TableTransformers());
//        // add custom converters
//        parameterConverters.addConverters(new ParameterConverters.DateConverter(new SimpleDateFormat("yyyy-MM-dd")),
//                new ParameterConverters.ExamplesTableConverter(examplesTableFactory));
//        return new MostUsefulConfiguration()
//                .useStoryLoader(new LoadFromClasspath(embeddableClass))
//                .useStoryParser(new RegexStoryParser(examplesTableFactory))
//                .useStoryReporterBuilder(new StoryReporterBuilder()
//                        .withCodeLocation(CodeLocations.codeLocationFromClass(embeddableClass))
//                        .withDefaultFormats()
//                        .withFormats(CONSOLE, TXT, HTML, XML))
//                .useParameterConverters(parameterConverters);
//    }

    @Override
    public InjectableStepsFactory stepsFactory() {
        return new InstanceStepsFactory(configuration(), new ChromeSteps());
    }

    @Override
    protected List<String> storyPaths() {
        String path = "stories/**/*.story";
        return new StoryFinder().findPaths(CodeLocations.codeLocationFromPath("").getFile(),
                Collections.singletonList(path),
                new ArrayList<String>(), "file:");
    }

    private StoryReporterBuilder getReporter(){
        return new StoryReporterBuilder().withPathResolver(new FilePrintStreamFactory.ResolveToSimpleName()
        ).withDefaultFormats().withFormats(Format.CONSOLE, Format.HTML);
    }
}
