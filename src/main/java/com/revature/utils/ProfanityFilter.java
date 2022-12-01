package com.revature.utils;

import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
@Service
public class ProfanityFilter {
    public boolean hasProfanity(String text) {
        Set<String> wordlist = new HashSet<>(Arrays.asList(
           "fuck", "bitch", "shit", "piss", "cunt", "cock", "tits", "motherfucker"
        ));
        String[] textArr = text.split(" ");
        for (String word: textArr) {
            if (wordlist.contains(word)) {
                return true;
            }
        }
        return false;
    }
}
