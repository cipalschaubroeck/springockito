package org.kubek2k.springockito.core.internal.mock;

import org.kubek2k.springockito.core.SpringockitoResettable;
import org.mockito.MockSettings;
import org.mockito.Mockito;
import org.springframework.beans.factory.FactoryBean;

public class MockitoMockFactory<T> implements FactoryBean<T>, SpringockitoResettable {
    private Class<T> mockClass;
    private MockitoMockSettings mockitoMockSettings;
    private T thisInstance;

    public Class<? extends T> getObjectType() {
        return mockClass;
    }

    public boolean isSingleton() {
        return true;
    }

    public T getObject() {
        if (thisInstance == null) {
            thisInstance = Mockito.mock(mockClass, getMockitoMockSettings());
        }
        return thisInstance;
    }

    public void setMockClass(final Class<T> mockClass) {
        this.mockClass = mockClass;
    }

    public void setMockitoMockSettings(final MockitoMockSettings mockitoMockSettings) {
        this.mockitoMockSettings = mockitoMockSettings;
    }

    private MockSettings getMockitoMockSettings() {
        if(mockitoMockSettings == null){
            mockitoMockSettings = MockitoMockSettings.DEFAULT;
        }
        return mockitoMockSettings.getMockSettings();
    }

    public void reset() {
        try {
            Mockito.<Object>reset(getObject());
        } catch (Exception e) {
            throw new RuntimeException("Unable to reset a mock", e);
        }
    }
}