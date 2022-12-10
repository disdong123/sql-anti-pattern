dependencies {

    implementation("org.springframework.boot:spring-boot-starter-webflux:3.0.0")

    // m2 error
    implementation("io.netty:netty-resolver-dns-native-macos:4.1.68.Final:osx-aarch_64")

    implementation(project(":subprojects:core:domain-r2dbc"))

    testImplementation(project(":subprojects:core:domain-r2dbc"))

}

tasks.getByName("bootJar") {
    enabled = true
}

tasks.getByName("jar") {
    enabled = false
}