package org.kubek2k.springockito.core.internal.mock;

import org.mockito.Answers;
import org.mockito.MockSettings;
import org.mockito.Mockito;
import org.mockito.stubbing.Answer;

public class MockitoMockSettings {

    public static final MockitoMockSettings DEFAULT = new MockitoMockSettings();
    private Class<?>[] extraInterfaces;
    private String mockName;
    private Answer<?> defaultAnswer;

    public MockSettings getMockSettings() {
        return createMockSettings();
    }

    private MockSettings createMockSettings() {
        MockSettings mockSettings = Mockito.withSettings();

        if (extraInterfaces != null && extraInterfaces.length > 0) {
            mockSettings.extraInterfaces(extraInterfaces);
        }

        if (defaultAnswer != null) {
            mockSettings.defaultAnswer(defaultAnswer);
        } else {
            mockSettings.defaultAnswer(Answers.RETURNS_DEFAULTS);
        }

        if (mockName != null) {
            mockSettings.name(mockName);
        }
        return mockSettings;
    }

    public MockitoMockSettings withExtraInterfaces(final Class<?>[] extraInterfaces) {
        this.extraInterfaces = extraInterfaces;
        return this;
    }

    public MockitoMockSettings withMockName(final String mockName) {
        this.mockName = mockName;
        return this;
    }

    public MockitoMockSettings withDefaultAnswer(final Answer<?> defaultAnswer) {
        this.defaultAnswer = defaultAnswer;
        return this;
    }
}
