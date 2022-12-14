package com.revature.utils;


import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.File;
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

        Resource resource1 = new ClassPathResource("blacklist.txt");
        Resource resource2 = new ClassPathResource("whitelist.txt");
        File file1 = resource1.getFile();
        File file2 = resource2.getFile();

        try {
            blacklist = new HashSet<>(Files.readAllLines(Path.of(file1.getAbsolutePath())));
            whitelist = new HashSet<>(Files.readAllLines(Path.of(file2.getAbsolutePath())));
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
