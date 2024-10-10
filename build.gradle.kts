plugins {
    id("com.github.johnrengelman.shadow") version "8.1.1"
    id("io.micronaut.application") version "4.4.3"
    id("gg.jte.gradle") version "3.1.12"
    id("io.micronaut.test-resources") version "4.4.3"
    id("io.micronaut.aot") version "4.4.3"
    id("org.sonatype.gradle.plugins.scan") version "2.8.3"
    jacoco
}

version = "0.1"
group = "com.micronauttodo"

repositories {
    mavenCentral()
}

dependencies {
    annotationProcessor("io.micronaut.data:micronaut-data-processor")
    annotationProcessor("io.micronaut:micronaut-http-validation")
    annotationProcessor("io.micronaut.openapi:micronaut-openapi")
    annotationProcessor("io.micronaut.security:micronaut-security-annotations")
    annotationProcessor("io.micronaut.serde:micronaut-serde-processor")
    implementation("io.micronaut.data:micronaut-data-jdbc")
    implementation("io.micronaut.security:micronaut-security")
    implementation("io.micronaut.serde:micronaut-serde-jackson")
    implementation("io.micronaut.sql:micronaut-jdbc-hikari")
    implementation("io.micronaut.views:micronaut-views-fieldset")
    implementation("io.micronaut.views:micronaut-views-jte")

    implementation("io.micronaut.multitenancy:micronaut-multitenancy")

    implementation("io.micronaut.flyway:micronaut-flyway")
    runtimeOnly("org.flywaydb:flyway-mysql")

    compileOnly("io.micronaut:micronaut-http-client")
    compileOnly("io.micronaut.openapi:micronaut-openapi-annotations")
    runtimeOnly("ch.qos.logback:logback-classic")
    runtimeOnly("com.mysql:mysql-connector-j")
    testImplementation("io.micronaut:micronaut-http-client")

    jteGenerate("gg.jte:jte-native-resources:3.1.12")
}

configurations.all {
    resolutionStrategy {
        // mysql-connector-j depends on a vulnerable version of protobuf
        force("com.google.protobuf:protobuf-java:4.28.2")
    }
}

application {
    mainClass = "com.micronauttodo.Application"
}
java {
    sourceCompatibility = JavaVersion.toVersion("21")
    targetCompatibility = JavaVersion.toVersion("21")
}

graalvmNative.toolchainDetection = true

micronaut {
    runtime("netty")
    testRuntime("junit5")
    processing {
        incremental(true)
        annotations("com.micronauttodo.*")
    }
    aot {
        // Please review carefully the optimizations enabled below
        // Check https://micronaut-projects.github.io/micronaut-aot/latest/guide/ for more details
        optimizeServiceLoading = false
        convertYamlToJava = false
        precomputeOperations = true
        cacheEnvironment = true
        optimizeClassLoading = true
        deduceEnvironment = true
        optimizeNetty = true
        replaceLogbackXml = true
    }
}

jte {
    sourceDirectory = file("src/main/jte").toPath()
    generate()
    jteExtension("gg.jte.nativeimage.NativeResourcesExtension")
}

// Gradle requires that generateJte is run before some tasks
tasks.configureEach {
    if (name == "inspectRuntimeClasspath") {
        mustRunAfter("generateJte")
    }
}

tasks.jacocoTestReport {
    dependsOn(tasks.test) // tests are required to run before generating the report
    reports {
        xml.required = false
        csv.required = false
        html.outputLocation.set(layout.buildDirectory.dir("jacocoHtml"))
    }
}
tasks.jacocoTestCoverageVerification {
    enabled = true
    violationRules {
        rule {
            limit {
                minimum = "0".toBigDecimal()
            }
        }
    }
}

tasks.check {
    dependsOn(tasks.jacocoTestCoverageVerification)
}

ossIndexAudit {
    username = project.properties["ossIndexUsername"].toString()
    password = project.properties["ossIndexPassword"].toString()
}
tasks.named("build") {
    dependsOn(tasks.ossIndexAudit)
}

