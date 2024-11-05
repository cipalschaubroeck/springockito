package org.kubek2k.springockito.core;

import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.isA;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.Date;

import javax.annotation.Resource;

import org.kubek2k.springockito.core.testbeans.BeanToBeInjectedWithSpy;
import org.kubek2k.springockito.core.testbeans.BeanToBeSpiedOrMockedAndStubbed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

@ContextConfiguration(locations = {"classpath*:/spring/parentContext.xml", "classpath*:/spring/mockitoSpyContext.xml"})
public class MockitoSpyHandlerIntegrationTest extends AbstractTestNGSpringContextTests {

    @Autowired
    private Date dateToBeSpied;

    @Resource
    private BeanToBeSpiedOrMockedAndStubbed beanToBeSpiedAndStubbed;


    @Resource
    BeanToBeInjectedWithSpy beanToBeInjectedWithSpy;


    @Test
    public void shouldLoadMockitoSpy() {
        dateToBeSpied.compareTo(new Date());
        verify(dateToBeSpied).compareTo(isA(Date.class));
    }

    @Test
    public void shouldAllowForSpiesTeBeStubbedUsingMockitoMatchers() {
        //given
        String fixedReturnValue = "fixedReturnValue";
        given(beanToBeSpiedAndStubbed.methodWithArgument(anyString()))
                .willReturn(fixedReturnValue);

        //when
        String returnedString = beanToBeSpiedAndStubbed.methodWithArgument("someString");

        //then
        assertThat(returnedString)
                .isEqualTo(fixedReturnValue);
    }

    @Test
    public void shouldInjectSpyOfTheBeanInsteadOfOriginalOneEverywhere() {
        //given
        int expectedAmountOfSpyMethodInvocations = 2;

        //when
        beanToBeInjectedWithSpy.callMethodWithoutArgumentsOnInjectedBeanTwice();

        //then
        verify(beanToBeSpiedAndStubbed, times(expectedAmountOfSpyMethodInvocations))
                .methodWithoutArgument();
    }
}
