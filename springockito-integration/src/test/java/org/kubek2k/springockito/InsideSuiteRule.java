package org.kubek2k.springockito;
import org.junit.Assume;
import org.junit.rules.ExternalResource;

public class InsideSuiteRule extends ExternalResource {
    private boolean insideSuite = false;

    public boolean isInsideSuite() {
        return insideSuite;
    }

    public void assume() {
        Assume.assumeTrue("Test can only be executed inside a suite", insideSuite);
    }


    @Override
    protected void before() throws Throwable {
        insideSuite = true;
    }

    @Override
    protected void after() {
        insideSuite = false;
    }
}
