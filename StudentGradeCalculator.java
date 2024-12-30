import java.util.Scanner;

public class StudentGradeCalculator {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        // Get number of subjects
        System.out.print("Enter the number of subjects: ");
        int numSubjects = validateIntInput(scanner);
        
        // Array to store marks
        int[] marks = new int[numSubjects];
        int totalMarks = 0;
        
        // Input marks for each subject with validation
        for (int i = 0; i < numSubjects; i++) {
            do {
                System.out.print("Enter marks for Subject " + (i + 1) + " (out of 100): ");
                marks[i] = validateIntInput(scanner);
                
                if (marks[i] < 0 || marks[i] > 100) {
                    System.out.println("Invalid marks! Please enter marks between 0 and 100.");
                }
            } while (marks[i] < 0 || marks[i] > 100);
            
            totalMarks += marks[i];
        }
        
        // Calculate average percentage
        double averagePercentage = (double) totalMarks / numSubjects;
        
        // Calculate grade
        String grade = calculateGrade(averagePercentage);
        
        // Display results
        System.out.println("\n--- Result ---");
        System.out.println("Marks obtained in each subject:");
        for (int i = 0; i < numSubjects; i++) {
            System.out.println("Subject " + (i + 1) + ": " + marks[i]);
        }
        System.out.println("\nTotal Marks: " + totalMarks + " out of " + (numSubjects * 100));
        System.out.printf("Average Percentage: %.2f%%\n", averagePercentage);
        System.out.println("Grade: " + grade);
        System.out.println("\nRemarks: " + getRemarks(grade));
        
        scanner.close();
    }
    
    // Method to validate integer input
    private static int validateIntInput(Scanner scanner) {
        while (!scanner.hasNextInt()) {
            System.out.println("Please enter a valid number!");
            scanner.next(); // Clear invalid input
        }
        return scanner.nextInt();
    }
    
    // Method to calculate grade based on percentage
    private static String calculateGrade(double percentage) {
        if (percentage >= 90) {
            return "A+";
        } else if (percentage >= 80) {
            return "A";
        } else if (percentage >= 70) {
            return "B";
        } else if (percentage >= 60) {
            return "C";
        } else if (percentage >= 50) {
            return "D";
        } else {
            return "F";
        }
    }
    
    // Method to provide remarks based on grade
    private static String getRemarks(String grade) {
        switch (grade) {
            case "A+":
                return "Outstanding performance! Keep up the excellent work!";
            case "A":
                return "Excellent performance! Well done!";
            case "B":
                return "Good performance! Keep improving!";
            case "C":
                return "Average performance. Work harder!";
            case "D":
                return "Below average. Need significant improvement!";
            default:
                return "Failed. Please seek additional help and support.";
        }
    }
}