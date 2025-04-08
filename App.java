package org.example;

import org.example.achievement.AchievementTracker;
import org.example.organizer.*;
import java.util.*;
import java.io.*;

public class App {
    public static void main(String[] args) throws IOException {
        if (args.length == 0 || Arrays.asList(args).contains("--help")) {
            printHelp();
            return;
        }

        String path = args[0];
        String order = "random";
        int repetitions = 1;
        boolean invert = false;

        for (int i = 1; i < args.length; i++) {
            switch (args[i]) {
                case "--order": order = args[++i]; break;
                case "--repetitions": repetitions = Integer.parseInt(args[++i]); break;
                case "--invertCards": invert = true; break;
            }
        }

        FlashCardManager manager = new FlashCardManager();
        List<FlashCard> cards = manager.loadCards(path);

        CardOrganizer organizer = switch (order) {
            case "worst-first" -> (list) -> list.stream()
                    .sorted(Comparator.comparingInt(FlashCard::getWrongCount).reversed())
                    .toList();
            case "recent-mistakes-first" -> new RecentMistakesFirstSorter();
            default -> (list) -> {
                List<FlashCard> shuffled = new ArrayList<>(list);
                Collections.shuffle(shuffled);
                return shuffled;
            };
        };

        cards = organizer.organize(cards);
        Scanner sc = new Scanner(System.in);
        long startTime = System.currentTimeMillis();

        for (FlashCard card : cards) {
            int correctStreak = 0;
            while (correctStreak < repetitions) {
                System.out.println((invert ? card.getAnswer() : card.getQuestion()) + " ?");
                String input = sc.nextLine();
                String expected = invert ? card.getQuestion() : card.getAnswer();
                if (input.equalsIgnoreCase(expected)) {
                    System.out.println("✅ Зөв!");
                    card.markCorrect();
                    correctStreak++;
                } else {
                    System.out.println("❌ Буруу! Зөв хариулт: " + expected);
                    card.markWrong();
                }
            }
        }

        long duration = System.currentTimeMillis() - startTime;
        System.out.println("⏱ Нийт хугацаа: " + duration / 1000.0 + " секунд");

        AchievementTracker tracker = new AchievementTracker();
        tracker.evaluate(cards);
        tracker.getUnlocked().forEach(a -> System.out.println("🎉 Амжилт: " + a));
    }

    private static void printHelp() {
        System.out.println("""
            Ашиглалт: flashcard <cards-file> [options]

            Options:
              --help                    Тусламжийн мэдээлэл харуулах
              --order <order>          Картын дараалал [random|worst-first|recent-mistakes-first]
              --repetitions <num>      Нэг картыг хэдэн удаа зөв хариулах шаардлагатай (default=1)
              --invertCards            Асуулт, хариултыг сольж үзүүлэх
            """);
    }
}
