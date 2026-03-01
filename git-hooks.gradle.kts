fun isLinuxOrMacOs(): Boolean {
    val osName = System.getProperty("os.name").lowercase()
    return osName.contains("linux") || osName.contains("mac os") || osName.contains("macos")
}

tasks.register<Copy>("copyGitHooks") {
    description = "Copies the git hooks from scripts/git-hooks to the .git folder."
    from("${rootDir}/scripts/git-hooks/") {
        include("**/*.sh")
        rename("(.*).sh", "$1")
    }
    into("${rootDir}/.git/hooks")
    onlyIf { isLinuxOrMacOs() }
}

tasks.register<Exec>("installGitHooks") {
    description = "Installs the git hooks from scripts/git-hooks."
    group = "git hooks"
    workingDir(rootDir)
    commandLine("chmod")
    args("-R", "+x", ".git/hooks/")
    dependsOn("copyGitHooks")
    onlyIf { isLinuxOrMacOs() }
    doLast {
        logger.info("Git hooks installed successfully.")
    }
}

afterEvaluate {
    tasks.findByName("clean")?.dependsOn("installGitHooks")
}
