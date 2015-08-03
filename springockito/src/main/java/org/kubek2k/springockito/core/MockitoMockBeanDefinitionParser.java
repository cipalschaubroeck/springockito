package org.kubek2k.springockito.core;

import org.kubek2k.springockito.core.internal.mock.MockitoMockFactory;
import org.springframework.beans.factory.parsing.BeanComponentDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.AbstractSingleBeanDefinitionParser;
import org.w3c.dom.Element;

public class MockitoMockBeanDefinitionParser extends AbstractSingleBeanDefinitionParser {
    @Override
    protected String getBeanClassName(final Element element) {
        return MockitoMockFactory.class.getCanonicalName();
    }

    @Override
    protected void doParse(final Element element, final BeanDefinitionBuilder bean) {
        bean.addPropertyValue("mockClass", element.getAttribute("class"));
    }

    @Override
    protected void postProcessComponentDefinition(final BeanComponentDefinition componentDefinition) {
        super.postProcessComponentDefinition(componentDefinition);
        componentDefinition.getBeanDefinition().setPrimary(true);
    }
}
