package org.kubek2k.springockito.annotations.internal.definer;

import org.kubek2k.springockito.annotations.internal.definitions.SpyDefinition;
import org.kubek2k.springockito.core.internal.spy.MockitoSpyBeanPostProcessor;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;

public class SpyDefiner extends Definer<SpyDefinition> {

    public static final SpyDefiner INSTANCE = new SpyDefiner();

    private SpyDefiner(){}

    public static SpyDefiner getInstance(){
        return INSTANCE;
    }

    @Override
    protected String getSpringBeanName(final SpyDefinition springockitoDefinition) {
        return springockitoDefinition.getTargetBeanName() + "$$POST_PROCESSOR_SPY";
    }

    @Override
    protected BeanDefinition getSpringBeanDefinition(final SpyDefinition spyDefinition) {
        return BeanDefinitionBuilder
                .rootBeanDefinition(MockitoSpyBeanPostProcessor.class.getCanonicalName())
                .addPropertyValue("beanName", spyDefinition.getTargetBeanName())
                .getBeanDefinition();
    }
}
