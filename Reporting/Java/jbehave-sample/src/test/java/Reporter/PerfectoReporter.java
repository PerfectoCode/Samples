package Reporter;

import Objects.DriverProvider;
import com.perfecto.reportium.client.ReportiumClient;
import com.perfecto.reportium.client.ReportiumClientFactory;
import com.perfecto.reportium.model.Job;
import com.perfecto.reportium.model.PerfectoExecutionContext;
import com.perfecto.reportium.model.Project;
import com.perfecto.reportium.test.TestContext;
import com.perfecto.reportium.test.result.TestResultFactory;
import org.jbehave.core.model.*;
import org.jbehave.core.reporters.StoryReporter;

import java.util.*;

public class PerfectoReporter implements StoryReporter {

    /**
     * String represent unwanted story names such as BeforeStories and AfterStories.
     * Both of this are two stories which runs before and after all other stories.
     */
    private final static String DoNotInclude = "BeforeStories AfterStories";

    /**
     * Saving the current story object as this reporting instance created for each story.
     */
    private Story delegate;
    private Iterator<Scenario> scenarioIterator;

    /**
     * Indicator on failed step
     */
    private boolean isFailed = false;
    private String FailureMessage;
    private Throwable throwable;

    private ReportiumClient reportiumClient;
    private DriverProvider provider;

    public PerfectoReporter(DriverProvider provider) {
        this.provider = provider;
    }

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
            reportiumClient = new ReportiumClientFactory().createPerfectoReportiumClient(
                    new PerfectoExecutionContext.PerfectoExecutionContextBuilder()
                            .withWebDriver(provider.get())
                            .withContextTags(delegate.getMeta().getProperty("PerfectoTags").split(","))
                            .withProject(new Project("Project Name", "Project Version"))
                            .withJob(new Job("Job Name", 12345))
                            .build());
        }

        this.scenarioIterator = this.delegate.getScenarios().iterator();
    }

    @Override
    public void afterStory(boolean b) {
        if (!DoNotInclude.contains(delegate.getName())) {
            System.out.println();
            System.out.println("report-url: " + reportiumClient.getReportUrl());
            System.out.println();
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
        Scenario scenario = scenarioIterator.next();
        reportiumClient.testStart(s, new TestContext(scenario.getMeta().getProperty("PerfectoTags").split(",")));
    }

    @Override
    public void scenarioMeta(Meta meta) {
        System.out.println(new Object() {
        }.getClass().getEnclosingMethod().getName());
    }

    @Override
    public void afterScenario() {
        if (isFailed) {
            reportiumClient.testStop(TestResultFactory.createFailure(FailureMessage, throwable));
        } else {
            reportiumClient.testStop(TestResultFactory.createSuccess());
        }
        //getting ready for the next scenario
        isFailed = false;
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
        reportiumClient.testStep(s);
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
        this.isFailed = true;
        this.FailureMessage = s;
        this.throwable = throwable;
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
