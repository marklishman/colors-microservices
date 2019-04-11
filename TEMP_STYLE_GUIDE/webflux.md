# Spring Webflux

__Avoid__ Spring Webflux unless there is a real measurable benefit to using it.

__Avoid__ Spring Webflux if traditional MVC provides the required performance.

__Why?__ Reactive, non-blocking, asynchronous system are more difficult to understand, develop and debug.
The learning curve can be steep.

> If you have a Spring MVC application that works fine, there is no need to change. Imperative programming is the 
easiest way to write, understand, and debug code. You have maximum choice of libraries, since, historically, 
most are blocking.

__Avoid__ the assumption that Spring Webflux will automatically make the application faster.

__Why?__ Reactive and non-blocking generally do not make applications run faster. 

> The key expected benefit of reactive and non-blocking is the ability to scale with a small, fixed number of 
threads and less memory. That makes applications more resilient under load, because they scale in a more 
predictable way. In order to observe those benefits, however, you need to have some latency 
(including a mix of slow and unpredictable network I/O). 
That is where the reactive stack begins to show its strengths, and the differences can be dramatic.


__Avoid__ Spring Webflux if part of the stack is not reactive. For example, the JDBC driver.

__Why?__ this will tie up a thread and negate the benefits of a reactive system, worse, it could crash the system.

> If you have blocking persistence APIs (JPA, JDBC) or networking APIs to use, Spring MVC is the best choice for 
common architectures at least. It is technically feasible with both Reactor and RxJava to perform blocking calls 
on a separate thread but you would not be making the most of a non-blocking web stack.

__Consider__ using Spring Webflux but _only_ if it makes sense to do so.

__Why?__ It _can_ allow an application to scale with less resources, but be careful.

__Do__ use Annotated Controllers rather than Functional Endpoints

__Why?__ It is a more familiar model to most developers as it mirrors traditional Spring MVC.


# Reference

The quotes are from [Spring Framework Reference](https://docs.spring.io/spring/docs/5.1.5.RELEASE/spring-framework-reference/web-reactive.html)

See [Applicability](https://docs.spring.io/spring/docs/5.1.5.RELEASE/spring-framework-reference/web-reactive.html#webflux-framework-choice)
to determine if Webflux should be used.
