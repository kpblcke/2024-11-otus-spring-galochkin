package ru.otus.hw.commands;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@ShellComponent
@RequiredArgsConstructor
public class BatchCommands {

    private final JobLauncher jobLauncher;

    private final Job migrateMongoToRelationDbJob;

    @ShellMethod(value = "startMigration", key = "sm")
    public void startMigration() throws Exception {
        jobLauncher.run(migrateMongoToRelationDbJob, new JobParameters());
    }

}
