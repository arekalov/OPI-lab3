import com.arekalov.jsfgraph.BuildParams
import com.arekalov.jsfgraph.Tasks
import com.arekalov.jsfgraph.tasks.HistoryTask

plugins {
    `java-library`
    `maven-publish`
    war
}

repositories {
    mavenLocal()
    maven {
        url = uri("https://repo.maven.apache.org/maven2/")
    }
}
val lab3TasksGroup = properties[BuildParams.TASKS_GROUP_NAME] as String

tasks.register(Tasks.COMPILE) {
    group = lab3TasksGroup
    dependsOn(tasks.compileJava)
}

tasks.register<Jar>(Tasks.CUSTOM_JAR) {
    group = lab3TasksGroup
    from(sourceSets.main.get().output)

    manifest {
        attributes(
            "Implementation-Title" to properties[BuildParams.IMPL_TITLE],
            "Implementation-Version" to properties[BuildParams.IMPL_VERSION],
            "Main-Class" to "com.arekalov.jsfgrap.CoordinateHandlerBean"
        )
    }

    archiveBaseName.set("my-application")
    archiveVersion.set(project.version.toString())
    archiveClassifier.set("custom")
}

tasks.named(Tasks.BUILD) {
    group = lab3TasksGroup
    dependsOn(Tasks.CUSTOM_JAR)
}

tasks.war {
    group = lab3TasksGroup
    from(tasks.named(Tasks.CUSTOM_JAR).map { it.outputs.files }) {
        into("WEB-INF/lib")
    }
}

tasks.clean.configure {
    group = lab3TasksGroup
}

tasks.test {
    group = lab3TasksGroup
    useJUnitPlatform()
}

tasks.register<MusicTask>(Tasks.MUSIC) {
    group = lab3TasksGroup
    dependsOn(tasks.build)
}

tasks.register<HistoryTask>(Tasks.HISTORY) {
    group = lab3TasksGroup
}

dependencies {
    implementation(libs.org.eclipse.persistence.org.eclipse.persistence.jpa)
    implementation(libs.org.postgresql.postgresql)
    implementation(libs.org.primefaces.primefaces)
    implementation(libs.ch.qos.logback.logback.classic)
    implementation(libs.jakarta.platform.jakarta.jakartaee.web.api)
    implementation(libs.org.projectlombok.lombok)

    annotationProcessor(libs.org.projectlombok.lombok)

    testImplementation(libs.org.junit.jupiter.junit.jupiter.api)
    testImplementation(libs.org.junit.jupiter.junit.jupiter.engine)
}

group = "com.arekalov.jsfgraph"
version = "1.0-SNAPSHOT"
description = "interactive-graph-ui"
java.sourceCompatibility = JavaVersion.VERSION_17

publishing {
    publications.create<MavenPublication>("maven") {
        from(components["java"])
    }
}

tasks.withType<JavaCompile>() {
    options.encoding = "UTF-8"
}

tasks.withType<Javadoc>() {
    options.encoding = "UTF-8"
}
