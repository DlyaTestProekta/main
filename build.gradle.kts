val springBootStarterVer: String by project // 3.3.1-SNAPSHOT
val springSecurityVer: String by project // 6.2.3
val springAdminVer: String by project // 3.2.3
val postgreSQLVer: String by project // 42.7.3
val liquibaseVer: String by project // 4.26.0
//val jacksonVer: String by project // 2.17.0
val openApiVer: String by project // 2.4.0
val javaJwtVer: String by project // 4.4.0
//val jsonVer: String by project // 20240303
val micrometerPrometheusVer: String by project // 1.12.4
val lombokVer: String by project // 1.18.32
val kafkaVer: String by project // 3.2.0
val junitVer: String by project // 1.11.0-M2
val protoCommonVer: String by project // 0.0.1
val grpcVer: String by project // 3.1.0.RELEASE
val jpamodelgenVer: String by project // 6.4.4.Final

plugins {
	java
	id("org.springframework.boot") version "3.3.1-SNAPSHOT"
	id("io.spring.dependency-management") version "1.1.5"
}

group = "ru.pachan"

java {
	sourceCompatibility = JavaVersion.VERSION_21
}

repositories {
	mavenCentral()
	mavenLocal()
	maven { url = uri("https://repo.spring.io/snapshot") }
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-data-jpa:$springBootStarterVer")
	implementation("org.springframework.boot:spring-boot-starter-security:$springBootStarterVer")
	implementation("org.springframework.boot:spring-boot-starter-web:$springBootStarterVer")
	implementation("org.springframework.boot:spring-boot-starter-actuator:$springBootStarterVer")
	implementation("org.springframework.boot:spring-boot-starter-data-redis:$springBootStarterVer")
	implementation("de.codecentric:spring-boot-admin-starter-client:$springAdminVer")
	implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:$openApiVer")
	implementation("io.micrometer:micrometer-registry-prometheus:$micrometerPrometheusVer")
	implementation("org.liquibase:liquibase-core:$liquibaseVer")
	implementation("com.auth0:java-jwt:$javaJwtVer")
	implementation("org.springframework.kafka:spring-kafka:$kafkaVer")
	implementation("ru.pachan:proto-common:$protoCommonVer")
	implementation("net.devh:grpc-client-spring-boot-starter:$grpcVer")
	runtimeOnly("org.postgresql:postgresql:$postgreSQLVer")
	testImplementation("org.springframework.boot:spring-boot-starter-test:$springBootStarterVer")
	testImplementation("org.springframework.kafka:spring-kafka-test:$kafkaVer")
	testImplementation("org.springframework.security:spring-security-test:$springSecurityVer")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher:$junitVer")
	compileOnly("org.projectlombok:lombok:$lombokVer")
	annotationProcessor("org.projectlombok:lombok:$lombokVer")
	// EXPLAIN_V Генерация класса Entity с полями для Criteria
	compileOnly("org.hibernate:hibernate-jpamodelgen:$jpamodelgenVer")
	annotationProcessor("org.hibernate:hibernate-jpamodelgen:$jpamodelgenVer")
}

tasks.withType<Test> {
	useJUnitPlatform()
}
