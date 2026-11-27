package com.liosh.model;

public record Finding(
        String ruleId,
        String name,
        Severity severity,
        String file,
        int line,
        String maskedSnippet
) {}
