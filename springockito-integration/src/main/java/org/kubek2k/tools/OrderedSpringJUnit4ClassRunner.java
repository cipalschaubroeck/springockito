package org.kubek2k.tools;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.InitializationError;

public class OrderedSpringJUnit4ClassRunner extends org.springframework.test.context.junit4.SpringJUnit4ClassRunner {

    public OrderedSpringJUnit4ClassRunner(final Class<?> clazz) throws InitializationError {
        super(clazz);
    }

    @Override
    protected List<FrameworkMethod> computeTestMethods() {
        // Create a modifiable copy of the list returned by super.computeTestMethods()
        List<FrameworkMethod> frameworkMethods = new ArrayList<>(super.computeTestMethods());
        frameworkMethods.sort((o1, o2) -> {
            Integer i1 = getOrder(o1);
            Integer i2 = getOrder(o2);
            return i1.compareTo(i2);
        });
        return frameworkMethods;
    }

    private int getOrder(final FrameworkMethod frameworkMethod) {
        Method method = frameworkMethod.getMethod();
        if (method.isAnnotationPresent(Ordered.class)) {
            return method.getAnnotation(Ordered.class).value();
        } else {
            return Ordered.DEFAULT;
        }
    }
}
