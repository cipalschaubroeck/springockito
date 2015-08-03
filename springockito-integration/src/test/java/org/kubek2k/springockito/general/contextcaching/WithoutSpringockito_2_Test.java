package org.kubek2k.springockito.general.contextcaching;

import static org.fest.assertions.Assertions.assertThat;
import static org.kubek2k.tools.TestUtil.isMock;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring/general/contextcaching/context.xml")
public class WithoutSpringockito_2_Test {

    @Autowired
    private BeanTestingContextCaching bean;

    @Test
    public void shouldGetNoMock() {
        TestSuiteToImposeTestsOrder.insideSuite.assume();

        //given
        //bean injected

        //when
        bean.incrementState();

        //then
        assertThat(bean.getState())
                .isEqualTo(1);
        assertThat(isMock(bean))
                .isFalse();
    }

}