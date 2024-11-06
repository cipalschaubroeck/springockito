package org.kubek2k.mockito.spring;

import org.kubek2k.mockito.spring.testbeans.BeanToBeSpiedOrMockedAndStubbed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

import javax.annotation.Resource;

import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Mockito.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ContextConfiguration(locations = {"classpath*:/spring/mockitoContext.xml"})
public class MockitoMockHandlerIntegrationTest extends AbstractTestNGSpringContextTests {

    @Autowired
    private SomeFancyClass someFancyClass;

    @Autowired
    @Qualifier("someFancyClass")
    private SomeFancyClass someFancyClass2;

    @Resource
    private BeanToBeSpiedOrMockedAndStubbed beanToBeMockedAndStubbed;

    // FIXME
    @Test
    public void shouldLoadMockitoMock() {
        assertThat(someFancyClass).isNotNull();
        assertThat(someFancyClass2).isNotNull();
    }

    @Test
    public void shouldAllowForMocksTeBeStubbedUsingMockitoMatchers() {
        //given
        String fixedReturnValue = "fixedReturnValue";
        given(beanToBeMockedAndStubbed.methodWithArgument(anyString())).willReturn(fixedReturnValue);

        //when
        String returnedString = beanToBeMockedAndStubbed.methodWithArgument("someString");

        //then
        assertThat(returnedString).isEqualTo(fixedReturnValue);

        verify(beanToBeMockedAndStubbed).methodWithArgument("someString");
    }
}
