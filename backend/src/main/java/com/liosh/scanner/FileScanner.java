package com.liosh.scanner;

import com.liosh.model.Finding;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.stream.Stream;

public class FileScanner {

    public List<Finding> scanFile(Path file, List<Rule> rules) {
        List<Finding> findings = new ArrayList<>();
        List<String> lines;
        try {
            lines = Files.readAllLines(file);
        } catch (IOException e) {
            return findings; // unreadable or binary file: skip it
        }

        for (int i = 0; i < lines.size(); i++) {
            for (Rule rule : rules) {
                Matcher m = rule.pattern().matcher(lines.get(i));
                if (m.find()) {
                    findings.add(new Finding(rule.id(), rule.name(), rule.severity(),
                            file.toString(), i + 1, mask(m.group())));
                }
            }
        }
        return findings;
    }

    public List<Finding> scanFolder(Path root, List<Rule> rules) {
        List<Finding> findings = new ArrayList<>();
        try (Stream<Path> paths = Files.walk(root)) {
            paths.filter(Files::isRegularFile)
                    .forEach(p -> findings.addAll(scanFile(p, rules)));
        } catch (IOException e) {
            System.out.println("Could not read folder: " + root);
        }
        return findings;
    }

    static String mask(String s) {
        return s.length() <= 4 ? "****" : s.substring(0, 4) + "****";
    }
}