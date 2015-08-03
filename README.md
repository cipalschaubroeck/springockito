# Springockito
*This project is a fork of the original work from kubek2k located at https://bitbucket.org/kubek2k/springockito/wiki/Home*
This is a small extension to spring that simplifies way of creation mockito mocks in the intergation tests' related context xml files.

## Release Notes
### 1.1.0 (unreleased)
* Initial forked release
* Springockito-Core: remove support for the static map, this should not be needed anymore as documented by the author of the original pull request.
* Springockito-Annotations: remove custom creation of mocks/spys, rely on Springockito-Core instead.

## Springockito-Core - Usage
### Mocking
Having a definition in context definition file, such as:
``` xml
<beans xmlns="http://www.springframework.org/schema/beans"
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
    xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans-3.0.xsd">
...
   <bean id="accountService" class="org.kubek2k.account.DefaultAccountService" />
   <bean id="bank" class="org.kubek2k.bank.Bank">
      <property name="accountService" ref="accountService" />
   </bean>
...
</beans>
```
you can simply override it by mock with the definition in file that is loaded only within tests, ex:
``` xml
<beans xmlns="http://www.springframework.org/schema/beans"
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
    xmlns:mockito="http://www.mockito.org/spring/mockito"
    xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans-3.0.xsd
    http://www.mockito.org/spring/mockito http://www.mockito.org/spring/mockito.xsd">
...
    <mockito:mock id="accountService" class="org.kubek2k.account.DefaultAccountService" />
..
</beans>
```
### Spying
Sometimes You are in need of plugin into the established application context to check some action happened on some beans under certain conditions, but you don't want bother recording the actual behavior of beans. Here the spying comes in handy:
Having definition:
``` xml
<beans xmlns="http://www.springframework.org/schema/beans"
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
    xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans-3.0.xsd">
...
    <bean id="accountService" class="DefaultAccountService" />
...
</beans>
```
we can spy the service within integration test using definition:
``` xml
<beans xmlns="http://www.springframework.org/schema/beans"
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
    xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans-3.0.xsd
    http://www.mockito.org/spring/mockito http://www.mockito.org/spring/mockito.xsd">
...
     <mockito:spy beanName="accountService" />
...
</beans>
```
and in test we can do something like:
``` java
@Autowired
private AccountService accountService;

@Test
public void shouldCountInterestForDeposit() {
    // given
    Deposit deposit = new Deposit(12);
    bank.addDeposit(deposit);

    // when
    bank.endOfTheMonth();
    
    // then
    verify(accountService).countInterestFor(deposit);
}
```

## Springockito-Annotations - Usage
Tired of writing custom xml files to override beans with mocks? - springockito-annotations is created for You. After adding to classpath, You only have to change integration test classes a little bit:
``` java
@ContextConfiguration(loader = SpringockitoContextLoader.class,
locations = <xml_contexts_you_used_before>)
public class SpringockitoAnnotationsMocksIntegrationTest extends AbstractJUnit4SpringContextTests {
...
}
```
and from now on.. :
###Mocks
Replacing beans with mocks is simple as adding an annotated field to your test class:
``` java
@ContextConfiguration(loader = SpringockitoContextLoader.class,
locations = "classpath:/context.xml")
public class SpringockitoAnnotationsMocksIntegrationTest extends AbstractJUnit4SpringContextTests {
    
    @ReplaceWithMock
    @Autowired
    private InnerBean innerBean;

...
}
```
the bean named 'innerBean' will be replaced with mock in your tests. If You want to verify some interactions, You can add autowired annotation to the field - so it gets injected.
### Spies
Wrapping bean instances with spies is similar:
``` java
@ContextConfiguration(loader = SpringockitoContextLoader.class,
locations = "classpath:/context.xml")
public class SpringockitoAnnotationsMocksIntegrationTest extends AbstractJUnit4SpringContextTests {    

   @WrapWithSpy
   private InnerBean innerBean;

...
}
```
- and 'innerBean' is spied.
### Spies/Mocks naming
You can now specify, in a number of ways, the name of bean that will be spied or mocked. Following examples are using @ReplaceWithMock, but they work equally well with @WrapWithSpy.
``` java
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = SpringockitoContextLoader.class, locations = {"classpath:context.xml"})
public class NamingExample {

    //bean name, as in previous versions, defaults to field name  
    @ReplaceWithMock
    private SomeBean myBean; // bean name: "myBean"

    @ReplaceWithMock(beanNameStrategy = FIELD_NAME)
    private SomeBean myBean; //bean name: "myBean"

    @ReplaceWithMock(beanNameStrategy = FIELD_TYPE_NAME) 
    private SomeBean myBean; //bean name: "a.b.c.SomeBean"

    @ReplaceWithMock(beanName = "arbitraryName")
    private SomeBean myBean; //bean name: "arbitraryName"

    @Resource(name = "resourceBean")
    @ReplaceWithMock
    private SomeBean myBean; //bean name: "resourceBean"

    @Autowired
    @Qualifier("autowiredQualifierBean")
    @ReplaceWithMock
    private SomeBean myBean; //bean name: "autowiredQualifierBean"
}
```
In case of ambiguity the explicitly specified name/strategy wins, also springockito declarations win over these of Resource/autowired&@Qualifier.
Reseting mocks/spies (in 'experimental' package, hope you'll like it)
Using DirtiesMocksTestContextListener you can now declaratively reset mocks/spies defined with @ReplaceWithMock/@WrapWithSpy annotations. Just use @DirtiesMocks annotations in a similar way to Spring's @DirtiesContext.
``` java
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = SpringockitoContextLoader.class, locations = {"classpath:context.xml"})
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class, DirtiesContextTestExecutionListener.class, DirtiesMocksTestContextListener.class})
public class ResetMocksSpiesExample{
    
    @DirtiesMocks
    public void shouldXYZ() {
        ...
    }
}
```
You can also declare @DirtiesMocks at class level as @DirtiesMocks(classMode = DirtiesMocks.ClassMode.AFTER_CLASS) or @DirtiesMocks(classMode = DirtiesMocks.ClassMode.AFTER_EACH_TEST_METHOD).
To save you from redeclaring standard Spring listeners you can reuse abstract org.kubek2k.springockito.annotations.experimental.junit.AbstractJUnit4SpringockitoContextTests class.
