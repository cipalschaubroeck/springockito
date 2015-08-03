package org.kubek2k.springockito.annotations.internal.definer;

import org.kubek2k.springockito.annotations.internal.definitions.MockDefinition;
import org.kubek2k.springockito.core.internal.mock.MockitoMockFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;

public class MockDefiner extends Definer<MockDefinition>{

    private static final MockDefiner INSTANCE = new MockDefiner();

    private MockDefiner(){}

    public static MockDefiner getInstance(){
        return INSTANCE;
    }

    @Override
    protected String getSpringBeanName(final MockDefinition mockDefinition) {
        return mockDefinition.getTargetBeanName();
    }

    @Override
    protected BeanDefinition getSpringBeanDefinition(final MockDefinition mockDefinition) {
        AbstractBeanDefinition beanDefinition = BeanDefinitionBuilder.genericBeanDefinition(MockitoMockFactory.class.getCanonicalName())
                .addPropertyValue("mockClass",mockDefinition.getMockClass())
                .addPropertyValue("mockitoMockSettings",mockDefinition.getMockitoMockSettings())
                .getBeanDefinition();
        beanDefinition.setPrimary(true);
        return beanDefinition;
    }
}
