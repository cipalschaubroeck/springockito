package org.kubek2k.springockito.core.testbeans;

import org.springframework.stereotype.Component;

@Component
public class BeanToBeSpiedOrMockedAndStubbed {

    public String methodWithArgument(String string) {
        return string;
    }

    public void methodWithoutArgument() {
    }

}
