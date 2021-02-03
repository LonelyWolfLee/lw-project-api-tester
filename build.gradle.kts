import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
  id("org.springframework.boot") version "2.4.2"
  id("io.spring.dependency-management") version "1.0.11.RELEASE"
  kotlin("jvm") version "1.4.21"
  kotlin("plugin.spring") version "1.4.21"
}

group = "pro.lonelywolf.project"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_1_8

repositories {
  mavenCentral()
  maven { url = uri("https://repo.spring.io/milestone") }
}

extra["springCloudVersion"] = "2020.0.0"

dependencies {
  implementation("org.springframework.boot:spring-boot-starter-actuator")
  implementation("org.springframework.boot:spring-boot-starter-webflux")
  implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
  implementation("io.projectreactor.kotlin:reactor-kotlin-extensions")
  implementation("org.jetbrains.kotlin:kotlin-reflect")
  implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
  implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor")
  implementation("org.springframework.cloud:spring-cloud-starter-sleuth")
  developmentOnly("org.springframework.boot:spring-boot-devtools")
  runtimeOnly("com.h2database:h2")
  testImplementation("org.springframework.boot:spring-boot-starter-test")
  testImplementation("io.projectreactor:reactor-test")
}

dependencyManagement {
  imports {
    mavenBom("org.springframework.cloud:spring-cloud-dependencies:${property("springCloudVersion")}")
  }
}

tasks.withType<KotlinCompile> {
  kotlinOptions {
    freeCompilerArgs = listOf("-Xjsr305=strict")
    jvmTarget = "1.8"
  }
}

tasks.withType<Test> {
  useJUnitPlatform()
}
