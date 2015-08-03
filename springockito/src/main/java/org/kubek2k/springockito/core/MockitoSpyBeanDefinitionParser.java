package org.kubek2k.springockito.core;

import org.kubek2k.springockito.core.internal.spy.MockitoSpyBeanPostProcessor;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.AbstractSingleBeanDefinitionParser;
import org.w3c.dom.Element;

public class MockitoSpyBeanDefinitionParser extends AbstractSingleBeanDefinitionParser {

    @Override
    protected String getBeanClassName(final Element element) {
        return MockitoSpyBeanPostProcessor.class.getCanonicalName();
    }

    @Override
    protected void doParse(final Element element, final BeanDefinitionBuilder beanBuilder) {
        beanBuilder.addPropertyValue("beanName", element.getAttribute("beanName"));
    }

    @Override
    protected boolean shouldGenerateIdAsFallback() {
        return true;
    }
}
