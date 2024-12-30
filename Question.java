import java.util.*;
import java.util.concurrent.*;

// Class to represent a quiz question
class Question {
    private String questionText;
    private List<String> options;
    private int correctOptionIndex;

    public Question(String questionText, List<String> options, int correctOptionIndex) {
        this.questionText = questionText;
        this.options = options;
        this.correctOptionIndex = correctOptionIndex;
    }

    public String getQuestionText() {
        return questionText;
    }

    public List<String> getOptions() {
        return options;
    }

    public boolean isCorrect(int selectedOption) {
        return selectedOption == correctOptionIndex;
    }
}

// Class to manage the quiz
class QuizManager {
    private List<Question> questions;
    private int score;
    private List<Boolean> answers;
    private static final int TIME_LIMIT_SECONDS = 15;
    private Scanner scanner;

    public QuizManager() {
        this.questions = initializeQuestions();
        this.score = 0;
        this.answers = new ArrayList<>();
        this.scanner = new Scanner(System.in);
    }

    private List<Question> initializeQuestions() {
        List<Question> questions = new ArrayList<>();
        
        // Add sample questions
        questions.add(new Question(
            "What is the capital of France?",
            Arrays.asList("London", "Berlin", "Paris", "Madrid"),
            2
        ));
        
        questions.add(new Question(
            "Which planet is known as the Red Planet?",
            Arrays.asList("Venus", "Mars", "Jupiter", "Saturn"),
            1
        ));
        
        questions.add(new Question(
            "What is 2 + 2 * 2?",
            Arrays.asList("6", "8", "4", "10"),
            0
        ));
        
        questions.add(new Question(
            "Who painted the Mona Lisa?",
            Arrays.asList("Van Gogh", "Da Vinci", "Picasso", "Rembrandt"),
            1
        ));
        
        questions.add(new Question(
            "What is the largest ocean on Earth?",
            Arrays.asList("Atlantic", "Indian", "Arctic", "Pacific"),
            3
        ));
        
        return questions;
    }

    public void startQuiz() {
        System.out.println("Welcome to the Quiz!");
        System.out.println("You have " + TIME_LIMIT_SECONDS + " seconds for each question.");
        System.out.println("Press Enter to start...");
        scanner.nextLine();

        for (int i = 0; i < questions.size(); i++) {
            if (!presentQuestion(i)) {
                answers.add(false); // Time out counts as wrong answer
            }
        }

        displayResults();
    }

    private boolean presentQuestion(int questionIndex) {
        Question question = questions.get(questionIndex);
        System.out.println("\nQuestion " + (questionIndex + 1) + ":");
        System.out.println(question.getQuestionText());
        
        List<String> options = question.getOptions();
        for (int i = 0; i < options.size(); i++) {
            System.out.println((i + 1) + ") " + options.get(i));
        }

        ExecutorService executor = Executors.newSingleThreadExecutor();
        Future<Integer> future = executor.submit(new AnswerTask(scanner));

        try {
            System.out.println("\nTimer started: " + TIME_LIMIT_SECONDS + " seconds...");
            int answer = future.get(TIME_LIMIT_SECONDS, TimeUnit.SECONDS);
            
            if (answer >= 1 && answer <= options.size()) {
                boolean isCorrect = question.isCorrect(answer - 1);
                if (isCorrect) {
                    score++;
                }
                answers.add(isCorrect);
                System.out.println(isCorrect ? "Correct!" : "Incorrect!");
                return true;
            } else {
                System.out.println("Invalid option selected!");
                return false;
            }
        } catch (TimeoutException e) {
            System.out.println("\nTime's up!");
            return false;
        } catch (Exception e) {
            System.out.println("Error occurred!");
            return false;
        } finally {
            executor.shutdownNow();
        }
    }

    private void displayResults() {
        System.out.println("\n=== Quiz Results ===");
        System.out.println("Total Questions: " + questions.size());
        System.out.println("Correct Answers: " + score);
        System.out.println("Incorrect Answers: " + (questions.size() - score));
        System.out.printf("Final Score: %.1f%%\n", (score * 100.0 / questions.size()));
        
        System.out.println("\nQuestion Summary:");
        for (int i = 0; i < questions.size(); i++) {
            System.out.printf("Question %d: %s\n", 
                (i + 1), 
                answers.size() > i ? (answers.get(i) ? "Correct" : "Incorrect") : "Not Answered"
            );
        }
    }
}

// Class to handle answer input with timer
class AnswerTask implements Callable<Integer> {
    private Scanner scanner;

    public AnswerTask(Scanner scanner) {
        this.scanner = scanner;
    }

    @Override
    public Integer call() {
        try {
            System.out.print("Enter your answer (1-4): ");
            return Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            return -1;
        }
    }
}

// Main class to run the quiz
public class QuizApplication {
    public static void main(String[] args) {
        QuizManager quizManager = new QuizManager();
        quizManager.startQuiz();
    }
}