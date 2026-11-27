package com.liosh.scanner;

import com.liosh.model.Severity;

import java.util.regex.Pattern;

public record Rule(
        String id,
        String name,
        Severity severity,
        Pattern pattern,
        String advice
) {}
