package org.kubek2k.springockito.general.reset.annotation.mock.afterclass;

import org.junit.ClassRule;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.kubek2k.springockito.InsideSuiteRule;

@RunWith(Suite.class)
@Suite.SuiteClasses({DirtiesMocks1_Test.class, DirtiesMocks2_Test.class})
public class TestSuiteToImposeTestsOrder {
    @ClassRule
    public static InsideSuiteRule insideSuite = new InsideSuiteRule();
}
