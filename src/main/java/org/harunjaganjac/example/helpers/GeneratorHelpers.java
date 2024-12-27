package org.harunjaganjac.example.helpers;

public final class GeneratorHelpers
{
    public static String generateId() {
        return java.util.UUID.randomUUID().toString();
    }
}
