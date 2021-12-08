import org.gradle.kotlin.dsl.support.listFilesOrdered

plugins {
    kotlin("jvm") version "1.6.0"
}

repositories {
    mavenCentral()
}

tasks {
    sourceSets {
        main {
            java.srcDirs("src")
        }
    }

    wrapper {
        gradleVersion = "7.3"
    }
}

abstract class NewDay : DefaultTask() {
    private val lastDay = File("src").listFilesOrdered().filter { it.isDirectory }.map { it.name.substringAfter("day").toInt() }.last()
    private var day: String = ""
    private var recreate: Boolean = false

    @Option(option = "day", description = "Day number <1 to 25>")
    open fun setDay(day: String) {
        when {
            day.toIntOrNull() == null -> {
                error("Parameter 'day' should be numeric")
            }
            day.toInt() !in (1..25) -> {
                error("Parameter 'day' should has value between 1 and 25")
            }
            day.toInt() > lastDay + 1 -> {
                error("Did you mean --day=${lastDay + 1}? Last day number is $lastDay")
            }
            else -> this.day = day
        }
    }

    @Option(option = "recreate", description = "If true, removes day from parameter --day")
    open fun setRecreate(recreate: Boolean) {
        this.recreate = recreate
    }

    @Input
    open fun getRecreate(): Boolean {
        return recreate
    }

    @Input
    open fun getDay(): String {
        return day
    }

    private fun createNewDay(path: String) {
        File("template").copyRecursively(File(path))
        val solutionFile = File("$path/solution.kt")
        val solutionSketchFile = File("$path/solution_sketch.kt")
        val textReplaced = solutionFile.readText().replace("{DAY}", day)
        solutionFile.writeText(textReplaced)
        solutionSketchFile.writeText(textReplaced)
    }

    private fun recreateDay(path: String) {
        File(path).deleteRecursively()
        createNewDay(path)
    }

    @TaskAction
    open fun createDay() {
        when {
            recreate -> {
                logger.quiet("Recreating day$day")
                recreateDay("src/day$day")
            }
            File("src/day$day").exists() -> {
                logger.quiet("day$day already exists. Add --recreate if you want to recreate day$day")
            }
            else -> {
                logger.quiet("Creating day$day")
                createNewDay("src/day$day")
            }
        }
    }
}

tasks.register<NewDay>("newDay")
