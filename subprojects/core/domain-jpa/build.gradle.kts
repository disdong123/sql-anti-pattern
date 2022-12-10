plugins {
    // issue: Class 'Post' should have [public, protected] no-arg constructor
    // hibernate 는 reflection 을 이용하여 entity 를 생성하므로 기본생성자가 필요합니다.
    // 아래 플러그인은 @Entity, @MappedSuperClass, @Embeddable 클래스의 default constructor 를 자동으로 생성합니다.
    id("org.jetbrains.kotlin.plugin.jpa") version "1.3.61"
}

// queryDsl version
val querydslVersion: String by project

/**
 * 기본적으로 @Bean, @Configuration 등 어노테이션을 이용하는 방식은 CGLIB 로 프록시 객체를 만들고 해당 객체를 등록합니다.
 * 즉, 프록시 객체를 만들수 있어야하므로 상속이 가능해야합니다.
 * 코틀린은 기본적으로 final class 입니다.
 * allOpen 을 이용하면 open 을 default 로 할 수 있습니다.
 */
allOpen {
    annotation("javax.persistence.Entity")
    annotation("javax.persistence.MappedSuperclass")
    annotation("javax.persistence.Embeddable")
}

dependencies {
    // querydsl
    // querydsl 은 @Entity 어노테이션을 가진 클래스를 탐색하고 JPA annotation processor 로 QClass 를 생성합니다.
    kapt("com.querydsl:querydsl-apt:$querydslVersion:jpa")
    api("com.querydsl:querydsl-jpa:$querydslVersion")

    // jpa & mysql
    api("org.springframework.boot:spring-boot-starter-data-jpa")
    runtimeOnly("mysql:mysql-connector-java")
}