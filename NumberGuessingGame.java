import java.util.Random;
import java.util.Scanner;

public class NumberGuessingGame {
    private static final int MAX_ATTEMPTS = 10;
    private static final int MIN_NUMBER = 1;
    private static final int MAX_NUMBER = 100;
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();
        int totalScore = 0;
        int roundsPlayed = 0;
        
        System.out.println("Welcome to the Number Guessing Game!");
        boolean playAgain = true;
        
        while (playAgain) {
            int targetNumber = random.nextInt(MAX_NUMBER - MIN_NUMBER + 1) + MIN_NUMBER;
            int attempts = 0;
            boolean hasGuessedCorrectly = false;
            roundsPlayed++;
            
            System.out.println("\nRound " + roundsPlayed);
            System.out.println("I'm thinking of a number between " + MIN_NUMBER + " and " + MAX_NUMBER);
            System.out.println("You have " + MAX_ATTEMPTS + " attempts to guess it.");
            
            while (attempts < MAX_ATTEMPTS && !hasGuessedCorrectly) {
                System.out.print("\nEnter your guess (Attempt " + (attempts + 1) + "/" + MAX_ATTEMPTS + "): ");
                
                // Input validation
                while (!scanner.hasNextInt()) {
                    System.out.println("Please enter a valid number!");
                    scanner.next(); // Clear invalid input
                }
                
                int guess = scanner.nextInt();
                attempts++;
                
                if (guess == targetNumber) {
                    hasGuessedCorrectly = true;
                    int roundScore = MAX_ATTEMPTS - attempts + 1;
                    totalScore += roundScore;
                    System.out.println("Congratulations! You've guessed the number correctly!");
                    System.out.println("Round Score: " + roundScore + " points");
                } else if (guess < targetNumber) {
                    System.out.println("Too low! Try again.");
                } else {
                    System.out.println("Too high! Try again.");
                }
            }
            
            if (!hasGuessedCorrectly) {
                System.out.println("\nGame Over! The number was: " + targetNumber);
            }
            
            // Display current statistics
            System.out.println("\nCurrent Statistics:");
            System.out.println("Rounds Played: " + roundsPlayed);
            System.out.println("Total Score: " + totalScore);
            System.out.println("Average Score: " + String.format("%.2f", (double)totalScore/roundsPlayed));
            
            // Ask to play again
            System.out.print("\nWould you like to play again? (yes/no): ");
            String playAgainResponse = scanner.next().toLowerCase();
            playAgain = playAgainResponse.startsWith("y");
        }
        
        // Display final statistics
        System.out.println("\nFinal Statistics:");
        System.out.println("Total Rounds Played: " + roundsPlayed);
        System.out.println("Final Score: " + totalScore);
        System.out.println("Average Score per Round: " + String.format("%.2f", (double)totalScore/roundsPlayed));
        System.out.println("Thanks for playing!");
        
        scanner.close();
    }
}