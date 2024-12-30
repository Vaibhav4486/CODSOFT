import java.util.Scanner;

// Class to represent Bank Account
class BankAccount {
    private double balance;
    private final String accountNumber;
    private final String pin;

    public BankAccount(String accountNumber, String pin, double initialBalance) {
        this.accountNumber = accountNumber;
        this.pin = pin;
        this.balance = initialBalance;
    }

    public boolean validatePin(String inputPin) {
        return pin.equals(inputPin);
    }

    public double getBalance() {
        return balance;
    }

    public boolean withdraw(double amount) {
        if (amount > 0 && amount <= balance) {
            balance -= amount;
            return true;
        }
        return false;
    }

    public boolean deposit(double amount) {
        if (amount > 0) {
            balance += amount;
            return true;
        }
        return false;
    }

    public String getAccountNumber() {
        return accountNumber;
    }
}

// Class to represent ATM Machine
class ATM {
    private final BankAccount account;
    private final Scanner scanner;
    private boolean isAuthenticated;

    public ATM(BankAccount account) {
        this.account = account;
        this.scanner = new Scanner(System.in);
        this.isAuthenticated = false;
    }

    public void start() {
        System.out.println("Welcome to the ATM");
        if (authenticate()) {
            while (true) {
                displayMenu();
                int choice = getIntInput("Enter your choice: ");
                
                if (choice == 5) {
                    System.out.println("Thank you for using our ATM. Goodbye!");
                    break;
                }
                
                processChoice(choice);
            }
        } else {
            System.out.println("Authentication failed. Please try again later.");
        }
    }

    private boolean authenticate() {
        int attempts = 3;
        while (attempts > 0) {
            System.out.print("Enter your PIN: ");
            String pin = scanner.nextLine();
            
            if (account.validatePin(pin)) {
                isAuthenticated = true;
                return true;
            }
            
            attempts--;
            System.out.println("Invalid PIN. " + attempts + " attempts remaining.");
        }
        return false;
    }

    private void displayMenu() {
        System.out.println("\n=== ATM Menu ===");
        System.out.println("1. Check Balance");
        System.out.println("2. Withdraw Money");
        System.out.println("3. Deposit Money");
        System.out.println("4. View Account Details");
        System.out.println("5. Exit");
    }

    private void processChoice(int choice) {
        switch (choice) {
            case 1:
                checkBalance();
                break;
            case 2:
                withdrawMoney();
                break;
            case 3:
                depositMoney();
                break;
            case 4:
                viewAccountDetails();
                break;
            default:
                System.out.println("Invalid choice. Please try again.");
        }
    }

    private void checkBalance() {
        System.out.printf("Current Balance: $%.2f\n", account.getBalance());
    }

    private void withdrawMoney() {
        double amount = getDoubleInput("Enter amount to withdraw: $");
        if (amount <= 0) {
            System.out.println("Invalid amount. Please enter a positive value.");
            return;
        }

        if (account.withdraw(amount)) {
            System.out.printf("Successfully withdrew $%.2f\n", amount);
            System.out.printf("New balance: $%.2f\n", account.getBalance());
        } else {
            System.out.println("Withdrawal failed. Insufficient balance or invalid amount.");
        }
    }

    private void depositMoney() {
        double amount = getDoubleInput("Enter amount to deposit: $");
        if (amount <= 0) {
            System.out.println("Invalid amount. Please enter a positive value.");
            return;
        }

        if (account.deposit(amount)) {
            System.out.printf("Successfully deposited $%.2f\n", amount);
            System.out.printf("New balance: $%.2f\n", account.getBalance());
        } else {
            System.out.println("Deposit failed. Please enter a valid amount.");
        }
    }

    private void viewAccountDetails() {
        System.out.println("\n=== Account Details ===");
        System.out.println("Account Number: " + account.getAccountNumber());
        System.out.printf("Current Balance: $%.2f\n", account.getBalance());
    }

    private double getDoubleInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return Double.parseDouble(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number.");
            }
        }
    }

    private int getIntInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number.");
            }
        }
    }
}

// Main class to run the ATM program
public class ATMInterface {
    public static void main(String[] args) {
        // Create a bank account with initial balance
        BankAccount account = new BankAccount("1234567890", "1234", 1000.00);
        
        // Create and start ATM
        ATM atm = new ATM(account);
        atm.start();
    }
}