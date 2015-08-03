  package org.kubek2k.springockito.core.internal.spy;

import org.kubek2k.springockito.core.SpringockitoResettable;
import org.mockito.Mockito;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

public class MockitoSpyBeanPostProcessor implements BeanPostProcessor, SpringockitoResettable {

    private String beanName;
    private Object spy;

    public Object postProcessBeforeInitialization(final Object bean, final String beanName) throws BeansException {
        return bean;
    }

    public Object postProcessAfterInitialization(final Object bean, final String beanName) throws BeansException {
        if (this.beanName.equals(beanName)) {
            spy = Mockito.spy(bean);
            return spy;
        } else {
            return bean;
        }
    }
    public void setBeanName(final String matchingName) {
        beanName = matchingName;
    }

    public void reset() {
        try {
            Mockito.reset(spy);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
