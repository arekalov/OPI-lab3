package com.arekalov.jsfgraph.tasks

import com.arekalov.jsfgraph.Tasks
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction
import org.gradle.process.internal.ExecException
import java.io.ByteArrayOutputStream

open class HistoryTask : DefaultTask() {

    @TaskAction
    open fun execute() {
        try {
            println("Running build task...")
            val baos = ByteArrayOutputStream()
            project.exec {
                commandLine("./gradlew", "build")
                standardOutput = baos
                errorOutput = baos
            }

            println("Build was successful or already up to date. No rollback needed.")
        } catch (e: Exception) {
            println("Build failed. Attempting to rollback...")
            rollbackAndBuild()
        } finally {

            println("Task 'history' completed successfully in reporting.")
        }
    }

    private fun rollbackAndBuild() {
        val commits = gitCommand("rev-list HEAD")?.lines() ?: return
        var lastSuccessfulCommit: String? = null
        val currentlyCheckedOutCommit = gitCommand("rev-parse HEAD")!!.trim()

        gitCommand("add .")
        gitCommand("commit -m temp")

        for (commit in commits) {
            gitCommand("checkout $commit")
            if (tryBuild()) {
                lastSuccessfulCommit = commit
                println("Successful build at commit: $commit")
                break
            } else {
                println("Failed build at commit: $commit")
            }
        }

        if (lastSuccessfulCommit == null) {
            println("No successful build found.")
        } else {
            val diff = gitCommand("diff $currentlyCheckedOutCommit $lastSuccessfulCommit")
            val diffFile = project.file("build/successful_build_diff.txt")
            diffFile.writeText(diff ?: "No diff available.")
            println("Diff between $currentlyCheckedOutCommit and $lastSuccessfulCommit has been saved to ${diffFile.path}")
        }
    }

    private fun gitCommand(command: String): String? {
        val output = ByteArrayOutputStream()
        try {
            project.exec {
                commandLine("git", *command.split(" ").toTypedArray())
                standardOutput = output
            }
        } catch (e: Exception) {
            logger.error("Failed to execute git command: $command", e)
            return null
        }
        return output.toString()
    }

    private fun tryBuild(): Boolean {
        return try {
            val baos = ByteArrayOutputStream()
            project.exec {
                commandLine("./gradlew", "clean", "build")
                standardOutput = baos
                errorOutput = baos
            }
            true
        } catch (e: ExecException) {
            false
        }
    }
}