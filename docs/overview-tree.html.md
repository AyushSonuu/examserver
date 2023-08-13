---
dc.created: 2023-08-13
description: class tree
generator: javadoc/TreeWriter
lang: en
title: Class Hierarchy
viewport: width=device-width, initial-scale=1
---

<div>

JavaScript is disabled on your browser.

</div>

::: flex-box
::: {.flex-header role="banner"}
::: {#navbar-top .top-nav}
[ ]{.nav-bar-toggle-icon}[ ]{.nav-bar-toggle-icon}[ ]{.nav-bar-toggle-icon}

::: skip-nav
[Skip navigation links](#skip-navbar-top "Skip navigation links")
:::

-   [Overview](index.html)
-   Package
-   Class
-   Use
-   Tree
-   [Index](index-files/index-1.html)
-   [Help](help-doc.html#tree)
:::

::: sub-nav
::: {#navbar-sub-list}
:::

::: nav-list-search
[SEARCH](search.html)
:::
:::

[]{#skip-navbar-top .skip-nav}
:::

::: flex-content
::: {role="main"}
::: header
# Hierarchy For All Packages {#hierarchy-for-all-packages .title}
:::

[Package Hierarchies:]{.package-hierarchy-label}

-   [com.exam](com/exam/package-tree.html),
-   [com.exam.config](com/exam/config/package-tree.html),
-   [com.exam.controller](com/exam/controller/package-tree.html),
-   [com.exam.email](com/exam/email/package-tree.html),
-   [com.exam.email.validator](com/exam/email/validator/package-tree.html),
-   [com.exam.exception](com/exam/exception/package-tree.html),
-   [com.exam.model.quiz](com/exam/model/quiz/package-tree.html),
-   [com.exam.model.token](com/exam/model/token/package-tree.html),
-   [com.exam.model.user](com/exam/model/user/package-tree.html),
-   [com.exam.repo.quiz](com/exam/repo/quiz/package-tree.html),
-   [com.exam.repo.user](com/exam/repo/user/package-tree.html),
-   [com.exam.service](com/exam/service/package-tree.html),
-   [com.exam.service.impl](com/exam/service/impl/package-tree.html),
-   [com.exam.service.quizimpl](com/exam/service/quizimpl/package-tree.html)

::: {.section .hierarchy}
## Class Hierarchy {#class-hierarchy title="Class Hierarchy"}

-   java.lang.[Object](https://docs.oracle.com/en/java/javase/20/docs/api/java.base/java/lang/Object.html "class or interface in java.lang"){.type-name-link
    .external-link}
    -   com.exam.controller.[AuthenticatController](com/exam/controller/AuthenticatController.html "class in com.exam.controller"){.type-name-link}
    -   com.exam.model.user.[Authority](com/exam/model/user/Authority.html "class in com.exam.model.user"){.type-name-link}
        (implements org.springframework.security.core.GrantedAuthority)
    -   com.exam.model.quiz.[Category](com/exam/model/quiz/Category.html "class in com.exam.model.quiz"){.type-name-link}
    -   com.exam.controller.[CategoryController](com/exam/controller/CategoryController.html "class in com.exam.controller"){.type-name-link}
    -   com.exam.service.quizimpl.[CategoryServiceImpl](com/exam/service/quizimpl/CategoryServiceImpl.html "class in com.exam.service.quizimpl"){.type-name-link}
        (implements
        com.exam.service.[CategoryService](com/exam/service/CategoryService.html "interface in com.exam.service"))
    -   com.exam.model.token.[ConfirmationToken](com/exam/model/token/ConfirmationToken.html "class in com.exam.model.token"){.type-name-link}
    -   com.exam.service.impl.[ConfirmationTokenService](com/exam/service/impl/ConfirmationTokenService.html "class in com.exam.service.impl"){.type-name-link}
    -   com.exam.model.user.[CustomResponse](com/exam/model/user/CustomResponse.html "class in com.exam.model.user"){.type-name-link}
    -   com.exam.email.validator.[EmailBuilder](com/exam/email/validator/EmailBuilder.html "class in com.exam.email.validator"){.type-name-link}
    -   com.exam.email.[EmailService](com/exam/email/EmailService.html "class in com.exam.email"){.type-name-link}
        (implements
        com.exam.email.[EmailSender](com/exam/email/EmailSender.html "interface in com.exam.email"))
    -   com.exam.email.validator.[EmailValidator](com/exam/email/validator/EmailValidator.html "class in com.exam.email.validator"){.type-name-link}
        (implements
        java.util.function.[Predicate](https://docs.oracle.com/en/java/javase/20/docs/api/java.base/java/util/function/Predicate.html "class or interface in java.util.function"){.external-link}\<T\>)
    -   com.exam.exception.[ErrorDetails](com/exam/exception/ErrorDetails.html "class in com.exam.exception"){.type-name-link}
    -   com.exam.[ExamserverApplication](com/exam/ExamserverApplication.html "class in com.exam"){.type-name-link}
        (implements org.springframework.boot.CommandLineRunner)
    -   org.springframework.web.filter.GenericFilterBean (implements
        org.springframework.beans.factory.BeanNameAware,
        org.springframework.beans.factory.DisposableBean,
        org.springframework.context.EnvironmentAware,
        org.springframework.core.env.EnvironmentCapable,
        jakarta.servlet.Filter,
        org.springframework.beans.factory.InitializingBean,
        org.springframework.web.context.ServletContextAware)
        -   org.springframework.web.filter.OncePerRequestFilter
            -   com.exam.config.[JwtAuthenticationFilter](com/exam/config/JwtAuthenticationFilter.html "class in com.exam.config"){.type-name-link}
    -   com.exam.config.[JwtAuthenticationEntryPoint](com/exam/config/JwtAuthenticationEntryPoint.html "class in com.exam.config"){.type-name-link}
        (implements
        org.springframework.security.web.AuthenticationEntryPoint)
    -   com.exam.model.user.[JwtRequest](com/exam/model/user/JwtRequest.html "class in com.exam.model.user"){.type-name-link}
    -   com.exam.model.user.[JwtResponse](com/exam/model/user/JwtResponse.html "class in com.exam.model.user"){.type-name-link}
    -   com.exam.config.[JwtUtil](com/exam/config/JwtUtil.html "class in com.exam.config"){.type-name-link}
    -   com.exam.config.[MySecurityConfig](com/exam/config/MySecurityConfig.html "class in com.exam.config"){.type-name-link}
    -   com.exam.model.quiz.[Question](com/exam/model/quiz/Question.html "class in com.exam.model.quiz"){.type-name-link}
    -   com.exam.controller.[QuestionController](com/exam/controller/QuestionController.html "class in com.exam.controller"){.type-name-link}
    -   com.exam.service.quizimpl.[QuestionServiceImpl](com/exam/service/quizimpl/QuestionServiceImpl.html "class in com.exam.service.quizimpl"){.type-name-link}
        (implements
        com.exam.service.[QuestionService](com/exam/service/QuestionService.html "interface in com.exam.service"))
    -   com.exam.model.quiz.[Quiz](com/exam/model/quiz/Quiz.html "class in com.exam.model.quiz"){.type-name-link}
    -   com.exam.controller.[QuizController](com/exam/controller/QuizController.html "class in com.exam.controller"){.type-name-link}
    -   com.exam.service.quizimpl.[QuizServiceImpl](com/exam/service/quizimpl/QuizServiceImpl.html "class in com.exam.service.quizimpl"){.type-name-link}
        (implements
        com.exam.service.[QuizService](com/exam/service/QuizService.html "interface in com.exam.service"))
    -   org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler
        (implements org.springframework.context.MessageSourceAware)
        -   com.exam.exception.[CustomizedResponseEntityExceptionHandler](com/exam/exception/CustomizedResponseEntityExceptionHandler.html "class in com.exam.exception"){.type-name-link}
    -   com.exam.model.user.[Role](com/exam/model/user/Role.html "class in com.exam.model.user"){.type-name-link}
    -   java.lang.[Throwable](https://docs.oracle.com/en/java/javase/20/docs/api/java.base/java/lang/Throwable.html "class or interface in java.lang"){.type-name-link
        .external-link} (implements
        java.io.[Serializable](https://docs.oracle.com/en/java/javase/20/docs/api/java.base/java/io/Serializable.html "class or interface in java.io"){.external-link})
        -   java.lang.[Exception](https://docs.oracle.com/en/java/javase/20/docs/api/java.base/java/lang/Exception.html "class or interface in java.lang"){.type-name-link
            .external-link}
            -   java.lang.[RuntimeException](https://docs.oracle.com/en/java/javase/20/docs/api/java.base/java/lang/RuntimeException.html "class or interface in java.lang"){.type-name-link
                .external-link}
                -   com.exam.exception.[UserNotFoundException](com/exam/exception/UserNotFoundException.html "class in com.exam.exception"){.type-name-link}
    -   com.exam.model.user.[User](com/exam/model/user/User.html "class in com.exam.model.user"){.type-name-link}
        (implements
        org.springframework.security.core.userdetails.UserDetails)
    -   com.exam.controller.[UserController](com/exam/controller/UserController.html "class in com.exam.controller"){.type-name-link}
    -   com.exam.service.impl.[UserDetailsServiceImpl](com/exam/service/impl/UserDetailsServiceImpl.html "class in com.exam.service.impl"){.type-name-link}
        (implements
        org.springframework.security.core.userdetails.UserDetailsService)
    -   com.exam.model.user.[UserRole](com/exam/model/user/UserRole.html "class in com.exam.model.user"){.type-name-link}
    -   com.exam.service.impl.[UserServiceImpl](com/exam/service/impl/UserServiceImpl.html "class in com.exam.service.impl"){.type-name-link}
        (implements
        com.exam.service.[UserService](com/exam/service/UserService.html "interface in com.exam.service"))
:::

::: {.section .hierarchy}
## Interface Hierarchy {#interface-hierarchy title="Interface Hierarchy"}

-   com.exam.service.[CategoryService](com/exam/service/CategoryService.html "interface in com.exam.service"){.type-name-link}
-   com.exam.email.[EmailSender](com/exam/email/EmailSender.html "interface in com.exam.email"){.type-name-link}
-   org.springframework.data.repository.query.QueryByExampleExecutor\<T\>
    -   org.springframework.data.jpa.repository.JpaRepository\<T,ID\>
        (also extends
        org.springframework.data.repository.ListCrudRepository\<T,ID\>,
        org.springframework.data.repository.ListPagingAndSortingRepository\<T,ID\>)
        -   com.exam.repo.quiz.[CategoryRepository](com/exam/repo/quiz/CategoryRepository.html "interface in com.exam.repo.quiz"){.type-name-link}
        -   com.exam.repo.user.[ConfirmationTokenRepository](com/exam/repo/user/ConfirmationTokenRepository.html "interface in com.exam.repo.user"){.type-name-link}
        -   com.exam.repo.quiz.[QuestionRepository](com/exam/repo/quiz/QuestionRepository.html "interface in com.exam.repo.quiz"){.type-name-link}
        -   com.exam.repo.quiz.[QuizRepository](com/exam/repo/quiz/QuizRepository.html "interface in com.exam.repo.quiz"){.type-name-link}
        -   com.exam.repo.user.[RoleRepository](com/exam/repo/user/RoleRepository.html "interface in com.exam.repo.user"){.type-name-link}
        -   com.exam.repo.user.[UserRepository](com/exam/repo/user/UserRepository.html "interface in com.exam.repo.user"){.type-name-link}
-   com.exam.service.[QuestionService](com/exam/service/QuestionService.html "interface in com.exam.service"){.type-name-link}
-   com.exam.service.[QuizService](com/exam/service/QuizService.html "interface in com.exam.service"){.type-name-link}
-   org.springframework.data.repository.Repository\<T,ID\>
    -   org.springframework.data.repository.CrudRepository\<T,ID\>
        -   org.springframework.data.repository.ListCrudRepository\<T,ID\>
            -   org.springframework.data.jpa.repository.JpaRepository\<T,ID\>
                (also extends
                org.springframework.data.repository.ListPagingAndSortingRepository\<T,ID\>,
                org.springframework.data.repository.query.QueryByExampleExecutor\<T\>)
                -   com.exam.repo.quiz.[CategoryRepository](com/exam/repo/quiz/CategoryRepository.html "interface in com.exam.repo.quiz"){.type-name-link}
                -   com.exam.repo.user.[ConfirmationTokenRepository](com/exam/repo/user/ConfirmationTokenRepository.html "interface in com.exam.repo.user"){.type-name-link}
                -   com.exam.repo.quiz.[QuestionRepository](com/exam/repo/quiz/QuestionRepository.html "interface in com.exam.repo.quiz"){.type-name-link}
                -   com.exam.repo.quiz.[QuizRepository](com/exam/repo/quiz/QuizRepository.html "interface in com.exam.repo.quiz"){.type-name-link}
                -   com.exam.repo.user.[RoleRepository](com/exam/repo/user/RoleRepository.html "interface in com.exam.repo.user"){.type-name-link}
                -   com.exam.repo.user.[UserRepository](com/exam/repo/user/UserRepository.html "interface in com.exam.repo.user"){.type-name-link}
    -   org.springframework.data.repository.PagingAndSortingRepository\<T,ID\>
        -   org.springframework.data.repository.ListPagingAndSortingRepository\<T,ID\>
            -   org.springframework.data.jpa.repository.JpaRepository\<T,ID\>
                (also extends
                org.springframework.data.repository.ListCrudRepository\<T,ID\>,
                org.springframework.data.repository.query.QueryByExampleExecutor\<T\>)
                -   com.exam.repo.quiz.[CategoryRepository](com/exam/repo/quiz/CategoryRepository.html "interface in com.exam.repo.quiz"){.type-name-link}
                -   com.exam.repo.user.[ConfirmationTokenRepository](com/exam/repo/user/ConfirmationTokenRepository.html "interface in com.exam.repo.user"){.type-name-link}
                -   com.exam.repo.quiz.[QuestionRepository](com/exam/repo/quiz/QuestionRepository.html "interface in com.exam.repo.quiz"){.type-name-link}
                -   com.exam.repo.quiz.[QuizRepository](com/exam/repo/quiz/QuizRepository.html "interface in com.exam.repo.quiz"){.type-name-link}
                -   com.exam.repo.user.[RoleRepository](com/exam/repo/user/RoleRepository.html "interface in com.exam.repo.user"){.type-name-link}
                -   com.exam.repo.user.[UserRepository](com/exam/repo/user/UserRepository.html "interface in com.exam.repo.user"){.type-name-link}
-   com.exam.service.[UserService](com/exam/service/UserService.html "interface in com.exam.service"){.type-name-link}
:::
:::
:::
:::
