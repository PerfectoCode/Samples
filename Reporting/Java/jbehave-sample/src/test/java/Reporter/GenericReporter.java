package Reporter;

import org.jbehave.core.model.*;
import org.jbehave.core.reporters.StoryReporter;

import java.util.List;
import java.util.Map;

public class GenericReporter implements StoryReporter {

    /**
     * String represent unwanted story names such as BeforeStories and AfterStories.
     * Both of this are two stories which runs before and after all other stories.
     */
    private final static String DoNotInclude = "BeforeStories AfterStories";

    /**
     * Saving the current story object as this reporting instance created for each story.
     */
    private Story delegate;

    @Override
    public void storyNotAllowed(Story story, String s) {
        System.out.println(new Object() {
        }.getClass().getEnclosingMethod().getName());
    }

    @Override
    public void storyCancelled(Story story, StoryDuration storyDuration) {
        System.out.println(new Object() {
        }.getClass().getEnclosingMethod().getName());
    }

    @Override
    public void beforeStory(Story story, boolean b) {
        this.delegate = story;
        if (!DoNotInclude.contains(delegate.getName())) {
            System.out.println();
            System.out.println(new Object() {
            }.getClass().getEnclosingMethod().getName());
        }
    }

    @Override
    public void afterStory(boolean b) {
        if (!DoNotInclude.contains(delegate.getName())) {
            System.out.println();
            System.out.println(new Object() {
            }.getClass().getEnclosingMethod().getName());
        }
    }

    @Override
    public void narrative(Narrative narrative) {
        System.out.println(new Object() {
        }.getClass().getEnclosingMethod().getName());
    }

    @Override
    public void lifecyle(Lifecycle lifecycle) {
        System.out.println(new Object() {
        }.getClass().getEnclosingMethod().getName());
    }

    @Override
    public void scenarioNotAllowed(Scenario scenario, String s) {
        System.out.println(new Object() {
        }.getClass().getEnclosingMethod().getName());
    }

    @Override
    public void beforeScenario(String s) {
        System.out.println();
        System.out.println("########### Scenario: " + s + " ###########");
    }

    @Override
    public void scenarioMeta(Meta meta) {
        System.out.println(new Object() {
        }.getClass().getEnclosingMethod().getName());
    }

    @Override
    public void afterScenario() {
        System.out.println("########### End Scenario ###########");
    }

    @Override
    public void givenStories(GivenStories givenStories) {
        System.out.println(new Object() {
        }.getClass().getEnclosingMethod().getName());
    }

    @Override
    public void givenStories(List<String> list) {
        System.out.println(new Object() {
        }.getClass().getEnclosingMethod().getName());
    }

    @Override
    public void beforeExamples(List<String> list, ExamplesTable examplesTable) {
        System.out.println(new Object() {
        }.getClass().getEnclosingMethod().getName());
    }

    @Override
    public void example(Map<String, String> map) {
        System.out.println(new Object() {
        }.getClass().getEnclosingMethod().getName());
    }

    @Override
    public void afterExamples() {
        System.out.println(new Object() {
        }.getClass().getEnclosingMethod().getName());
    }

    @Override
    public void beforeStep(String s) {
        System.out.println(new Object() {
        }.getClass().getEnclosingMethod().getName());
    }

    @Override
    public void successful(String s) {
        System.out.println(new Object() {
        }.getClass().getEnclosingMethod().getName());
    }

    @Override
    public void ignorable(String s) {
        System.out.println(new Object() {
        }.getClass().getEnclosingMethod().getName());
    }

    @Override
    public void pending(String s) {
        System.out.println(new Object() {
        }.getClass().getEnclosingMethod().getName());
    }

    @Override
    public void notPerformed(String s) {
        System.out.println(new Object() {
        }.getClass().getEnclosingMethod().getName());
    }

    @Override
    public void failed(String s, Throwable throwable) {
        System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx FAILED : " + s);
    }

    @Override
    public void failedOutcomes(String s, OutcomesTable outcomesTable) {
        System.out.println(new Object() {
        }.getClass().getEnclosingMethod().getName());
    }

    @Override
    public void restarted(String s, Throwable throwable) {
        System.out.println(new Object() {
        }.getClass().getEnclosingMethod().getName());
    }

    @Override
    public void restartedStory(Story story, Throwable throwable) {
        System.out.println(new Object() {
        }.getClass().getEnclosingMethod().getName());
    }

    @Override
    public void dryRun() {
        System.out.println(new Object() {
        }.getClass().getEnclosingMethod().getName());
    }

    @Override
    public void pendingMethods(List<String> list) {
        System.out.println(new Object() {
        }.getClass().getEnclosingMethod().getName());
    }
}
