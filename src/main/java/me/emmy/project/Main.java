package me.emmy.project;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/**
 * @author Emmy
 * @project Hangman
 * @date 19/01/2025 - 23:03
 */
public class Main {
    private static final String AUTHOR = "Emmy";
    private static final String VERSION = "1.0";
    private static final int MAX_ATTEMPTS = 6;

    private static int attemptsLeft = MAX_ATTEMPTS;

    public static final List<String> words = Arrays.asList(
            "emmy", "flower", "hangman", "lisa", "keyboard", "science", "guitar", "programming", "java"
    );

    /**
     * Main method for the Hangman game.
     *
     * @param args Command line arguments
     */
    public static void main(String[] args) throws InterruptedException {
        String word = words.get((int) (Math.random() * words.size()));
        List<Character> wrongGuesses = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);

        char[] guessedWord = new char[word.length()];
        Arrays.fill(guessedWord, '_');

        for (String line : WELCOME_MESSAGE) {
            System.out.println(line);
        }

        Thread.sleep(2000);
        printWord(guessedWord);

        while (attemptsLeft > 0 && !correctGuess(guessedWord)) {
            Arrays.asList(
                    "🕐 Attempts left: " + attemptsLeft,
                    "❌ Wrong guesses so far: " + wrongGuesses,
                    "",
                    getState(attemptsLeft),
                    "",
                    "🔤 What's your guess? Enter a letter: "
            ).forEach(System.out::println);

            final char guess = scanner.nextLine().toLowerCase().charAt(0);

            if (word.contains(String.valueOf(guess))) {
                for (int i = 0; i < word.length(); i++) {
                    if (word.charAt(i) == guess) {
                        guessedWord[i] = guess;
                    }
                }

                System.out.println("✅ Correct!");
                Thread.sleep(1000);
            } else {
                if (!wrongGuesses.contains(guess)) {
                    wrongGuesses.add(guess);

                    attemptsLeft--;
                } else {
                    System.out.println("⚠️ You already guessed that letter!");
                }
            }

            printWord(guessedWord);
        }

        if (correctGuess(guessedWord)) {
            System.out.println("\n🎉 Congratulations! You guessed the word: " + word + " 🎉");
        } else {
            System.out.println("\n💀 Game over! The word was: " + word);
        }

        scanner.close();
    }

    private static final String[] WELCOME_MESSAGE = {
            "",
            "🎮 Welcome to Hangman! 🎮",
            "",
            "📜 Author: " + AUTHOR,
            "📦 Version: " + VERSION,
            "",
            "📝 Goal is to guess the word and avoid losing all attempts!",
            "",
    };

    /**
     * Print the word with underscores for letters that have not been guessed.
     *
     * @param guessedWord The word that has been guessed
     */
    private static void printWord(char[] guessedWord) {
        System.out.print(" \n🔠 Word: ");
        for (char c : guessedWord) {
            System.out.print(c + " ");
        }

        System.out.println();
    }

    /**
     * Check if the word has been guessed.
     *
     * @param guessedWord The word that has been guessed
     * @return True if the word has been guessed, false otherwise
     */
    private static boolean correctGuess(char[] guessedWord) {
        for (char c : guessedWord) if (c == '_') return false;

        return true;
    }

    /**
     * Get the Hangman narrative based on the number of attempts left.
     *
     * @param attemptsLeft The number of attempts left
     * @return The corresponding Hangman narrative
     */
    private static String getState(int attemptsLeft) {
        switch (attemptsLeft) {
            case 6: return "😄 You're in great shape!";
            case 5: return "🙂 You're doing well! Just a few more guesses.";
            case 4: return "😟 Getting a little tricky... stay focused!";
            case 3: return "😧 Uh oh! Don't panic, you're almost there!";
            case 2: return "😨 Things are getting tense for the man! Hurry up!";
            case 1: return "😱 One last chance!";
            default: return "💀 It's over... You failed to save the man!";
        }
    }
}
