package com.liosh.scanner;

import com.liosh.model.Severity;

import java.util.List;
import java.util.regex.Pattern;

public class RuleRegistry {

    public static List<Rule> getRules() {
        return List.of(
                new Rule("AWS_KEY", "AWS access key", Severity.CRITICAL,
                        Pattern.compile("AKIA[0-9A-Z]{16}"),
                        "Rotate the key and load it from the environment."),
                new Rule("GH_TOKEN", "GitHub token", Severity.CRITICAL,
                        Pattern.compile("ghp_[A-Za-z0-9]{36}"),
                        "Revoke the token in GitHub settings."),
                new Rule("PRIVATE_KEY", "Private key in repo", Severity.CRITICAL,
                        Pattern.compile("-----BEGIN (RSA |EC |OPENSSH )?PRIVATE KEY-----"),
                        "Remove it and generate a new key pair."),
                new Rule("HARDCODED_PASSWORD", "Hardcoded password", Severity.HIGH,
                        Pattern.compile("(?i)(password|passwd|pwd)\\s*[=:]\\s*[\"'][^\"']{4,}[\"']"),
                        "Read the password from an environment variable."),
                new Rule("DB_URL_CREDENTIALS", "Database URL with credentials", Severity.HIGH,
                        Pattern.compile("(?i)(mysql|postgresql|mongodb)://[^\\s:/@]+:[^\\s@]+@"),
                        "Remove credentials from the URL and use environment variables.")
        );
    }
}