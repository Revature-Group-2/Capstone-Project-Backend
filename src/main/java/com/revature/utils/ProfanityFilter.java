package com.revature.utils;


import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Set;
@Service
public class ProfanityFilter {
    public boolean hasProfanity(String text) throws IOException {
        String[] textArr = text.replaceAll("\\n", " ").split(" ");

        Set<String> whitelist = new HashSet<>();
        Set<String> blacklist = new HashSet<>();
        try {
            blacklist = new HashSet<>(Files.readAllLines(Path.of("src/main/resources/blacklist.txt")));
            whitelist = new HashSet<>(Files.readAllLines(Path.of("src/main/resources/whitelist.txt")));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        Object[] blacklistArr = blacklist.toArray();
        for (String word : textArr) {
            if (whitelist.contains(word)) {
                continue;
            }
            for (Object profanity : blacklistArr) {
                if (word.contains((String) profanity)) {
                    return true;
                }
            }
        }
        return false;
    }
}
