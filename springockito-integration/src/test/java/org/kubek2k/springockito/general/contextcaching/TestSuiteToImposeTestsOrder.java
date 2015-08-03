package org.kubek2k.springockito.general.contextcaching;

import org.junit.ClassRule;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.kubek2k.springockito.InsideSuiteRule;

@RunWith(Suite.class)
@Suite.SuiteClasses({WithSpringockito_1_Test.class, WithoutSpringockito_2_Test.class, WithSpringockito_3_Test.class})
public class TestSuiteToImposeTestsOrder {
    @ClassRule
    public static InsideSuiteRule insideSuite = new InsideSuiteRule();
}
