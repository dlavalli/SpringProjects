package com.lavalliere.daniel.shell.demo.components;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.util.unit.DataSize;

import java.nio.file.FileSystem;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.FileAttributeView;

import static java.lang.StringTemplate.STR;


// https://docs.spring.io/spring-shell/reference/index.html
// https://docs.oracle.com/en/java/javase/21/core/disk-usage-file-nio-example.html
// https://docs.spring.io/spring-shell/reference/completion.html

@ShellComponent
public class FsCommands {

    //private final FileSystem fileSystem;

    //public FsCommands(FileSystem fileSystem) {
    //    this.fileSystem = fileSystem;
    //help}

    // Command will be available as: minimum-free-disk-space
    @ShellMethod("Display required free disk space")
    public long minimumFreeDiskSpace() {

        return 1_000_000;
    }

    // Command will be available as: minimum-free-disk-space
    @ShellMethod("Display free disk space")
    public String freeDiskSpace(String file) {
        //Path path = Path.of(diskName);
        //BasicFileAttributeView view = Files.getFileAttributeView(path, BasicFileAttributeView.class);
        //view.
        //fileSystem.
        // DataSize.ofBytes(1).toGigabytes()
        // System.out.println(STR."Path: \{diskName} has 1_000_000");
        // return DataSize.ofBytes(fs.getFreeDiskSpace()).toGigabytes()
        return "Unknown for now";
    }
}
