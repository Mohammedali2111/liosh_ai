package com.liosh.scanner;

import com.liosh.model.Finding;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class FileScannerTest {

    private final FileScanner scanner = new FileScanner();

    @Test
    void plantedFolderFindsAllFiveSecrets() {
        List<Finding> findings = scanner.scanFolder(
                Path.of("src/test/resources/planted"), RuleRegistry.getRules());

        assertEquals(5, findings.size());
        List<String> ids = findings.stream().map(Finding::ruleId).toList();
        assertTrue(ids.contains("AWS_KEY"));
        assertTrue(ids.contains("GH_TOKEN"));
        assertTrue(ids.contains("PRIVATE_KEY"));
        assertTrue(ids.contains("HARDCODED_PASSWORD"));
        assertTrue(ids.contains("DB_URL_CREDENTIALS"));
    }

    @Test
    void cleanFolderGivesZeroFindings() {
        List<Finding> findings = scanner.scanFolder(
                Path.of("src/test/resources/clean"), RuleRegistry.getRules());

        assertEquals(0, findings.size());
    }

    @Test
    void maskHidesTheSecret() {
        assertEquals("AKIA****", FileScanner.mask("AKIAABCDEFGHIJKLMNOP"));
        assertEquals("****", FileScanner.mask("abc"));
    }
}