package org.example.organizer;

import org.example.FlashCard;
import java.util.List;

public interface CardOrganizer {
    List<FlashCard> organize(List<FlashCard> cards);
}
