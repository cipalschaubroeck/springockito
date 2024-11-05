package org.kubek2k.springockito.annotations.experimental;

import java.lang.reflect.Method;
import java.util.Map;

import org.kubek2k.springockito.core.SpringockitoResettable;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.TestContext;
import org.springframework.test.context.support.AbstractTestExecutionListener;

public class DirtiesMocksTestContextListener extends AbstractTestExecutionListener {

    @Override
    public void afterTestMethod(final TestContext testContext) {
        Method testMethod = testContext.getTestMethod();
        Class<?> testClass = testContext.getTestClass();
        if (testMethod.isAnnotationPresent(DirtiesMocks.class) || afterEveryMethodModeSet(testClass)) {
            resetMocks(testContext);
        }
    }

    private boolean afterEveryMethodModeSet(final Class<?> testClass) {
        return testClass.isAnnotationPresent(DirtiesMocks.class)
                && testClass.getAnnotation(DirtiesMocks.class).classMode() == DirtiesMocks.ClassMode.AFTER_EACH_TEST_METHOD;
    }

    @Override
    public void afterTestClass(final TestContext testContext) {
        Class<?> testClass = testContext.getTestClass();
        if (afterClassModeSet(testClass)) {
            resetMocks(testContext);
        }
    }

    private boolean afterClassModeSet(final Class<?> testClass) {
        return testClass.isAnnotationPresent(DirtiesMocks.class) && testClass.getAnnotation(DirtiesMocks.class).classMode() == DirtiesMocks.ClassMode.AFTER_CLASS;
    }

    private void resetMocks(final TestContext testContext) {
        ApplicationContext applicationContext = testContext.getApplicationContext();
        Map<String, SpringockitoResettable> beansOfType = applicationContext.getBeansOfType(SpringockitoResettable.class);
        for (SpringockitoResettable resettableSpringockito : beansOfType.values()) {
            resettableSpringockito.reset();
        }
    }
}
