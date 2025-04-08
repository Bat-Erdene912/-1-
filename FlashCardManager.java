package org.example;

import java.io.*;
import java.util.*;

public class FlashCardManager {
    public List<FlashCard> loadCards(String filePath) throws IOException {
        List<FlashCard> cards = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("::");
                if (parts.length == 2) {
                    cards.add(new FlashCard(parts[0].trim(), parts[1].trim()));
                }
            }
        }
        return cards;
    }
}
