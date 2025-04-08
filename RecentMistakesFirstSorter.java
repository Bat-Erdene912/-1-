package org.example.organizer;

import org.example.FlashCard;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class RecentMistakesFirstSorter implements CardOrganizer {
    @Override
    public List<FlashCard> organize(List<FlashCard> cards) {
        return cards.stream()
            .sorted(Comparator.comparingInt(FlashCard::getWrongCount).reversed())
            .collect(Collectors.toList());
    }
}
