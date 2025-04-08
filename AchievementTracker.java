package org.example.achievement;

import org.example.FlashCard;
import java.util.*;

public class AchievementTracker {
    private final Set<AchievementType> unlocked = new HashSet<>();

    public void evaluate(List<FlashCard> cards) {
        boolean allCorrect = cards.stream().allMatch(c -> c.getWrongCount() == 0 && c.getCorrectCount() > 0);
        boolean hasRepeat = cards.stream().anyMatch(c -> c.getWrongCount() + c.getCorrectCount() > 5);
        boolean hasConfident = cards.stream().anyMatch(c -> c.getCorrectCount() >= 3);

        if (allCorrect) unlocked.add(AchievementType.CORRECT);
        if (hasRepeat) unlocked.add(AchievementType.REPEAT);
        if (hasConfident) unlocked.add(AchievementType.CONFIDENT);
    }

    public Set<AchievementType> getUnlocked() {
        return unlocked;
    }
}


