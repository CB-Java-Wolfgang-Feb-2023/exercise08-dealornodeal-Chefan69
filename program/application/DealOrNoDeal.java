
package application;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class DealOrNoDeal {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Ask if the user wants to start the game normally or in debug mode
        System.out.print("Do you want to start the game in debug mode? (Y/N): ");
        boolean debugMode = scanner.next().toUpperCase().equals("Y");

        // Create a list of suitcases with their values
        List<Suitcase> suitcases = createSuitcases(debugMode);

        // Ask the user to pick a suitcase
        System.out.print("Pick a suitcase number (1-26): ");
        int userSuitcaseNumber = scanner.nextInt();

        // Validate user input
        if (userSuitcaseNumber < 1 || userSuitcaseNumber > 26) {
            System.out.println("Invalid input. Please pick a number between 1 and 26.");
            return;
        }

        // Remove the user's chosen suitcase from the list
        Suitcase userSuitcase = suitcases.remove(userSuitcaseNumber - 1);

        // Start the game
        playGame(scanner, suitcases, userSuitcase);
    }

    private static void playGame(Scanner scanner, List<Suitcase> suitcases, Suitcase userSuitcase) {
        // Play 9 rounds
        for (int round = 1; round <= 9; round++) {
            System.out.println("\nRound " + round);

            // Display the remaining suitcases without revealing their values
            System.out.println("Remaining suitcases:");
            displaySuitcases(suitcases);

            // Ask the user to pick a certain number of suitcases to eliminate
            int eliminatedCases = getEliminatedCases(round);
            for (int i = 0; i < eliminatedCases; i++) {
                // Ask the user to pick a suitcase to eliminate
                System.out.print("Pick a suitcase to eliminate: ");
                int chosenSuitcaseNumber = scanner.nextInt();

                // Validate user input
                if (chosenSuitcaseNumber < 1 || chosenSuitcaseNumber > suitcases.size()) {
                    System.out.println("Invalid input. Please pick a valid suitcase number.");
                    return;
                }

                // Remove the chosen suitcase from the list
                Suitcase chosenSuitcase = suitcases.stream()
                        .filter(s -> s.getNumber() == chosenSuitcaseNumber)
                        .findFirst()
                        .orElse(null);

                if (chosenSuitcase != null) {
                    suitcases.remove(chosenSuitcase);
                    // Display that a suitcase was eliminated along with its value
                    System.out.println("You eliminated suitcase " + chosenSuitcase.getNumber() +
                            " containing $" + chosenSuitcase.getValue());
                }
            }

            // Calculate and display the bank's offer
            double bankOffer = calculateBankOffer(suitcases, round);
            System.out.println("Bank offer: $" + bankOffer);

            // Ask the user to deal or continue
            System.out.print("Deal or No Deal? (D/N): ");
            String decision = scanner.next().toUpperCase();

            if (decision.equals("D")) {
                // Player chose to deal
                System.out.println("Congratulations! You won $" + bankOffer);
                return;
            }
        }

        // Final round - offer to switch suitcases
        System.out.println("\nFinal Round");

        // Display the value of the user's chosen suitcase
        System.out.println("Your chosen suitcase contains $" + userSuitcase.getValue());

        // Ask the user to switch suitcases
        System.out.print("Do you want to switch your suitcase? (Y/N): ");
        String switchDecision = scanner.next().toUpperCase();

        if (switchDecision.equals("Y")) {
            // Switch the user's suitcase with the remaining one
            Suitcase remainingSuitcase = suitcases.get(0);
            System.out.println("You switched to another suitcase.");
            userSuitcase = remainingSuitcase;
        }

        // Player wins the content of the chosen suitcase
        System.out.println("Congratulations! You won the contents of your chosen suitcase.");
    }

    private static List<Suitcase> createSuitcases(boolean debugMode) {
        List<Suitcase> suitcases = new ArrayList<>();
        List<Integer> values = new ArrayList<>();

        if (debugMode) {
            // In debug mode, assign values from 1¢ to 1,000,000 in order
            for (int i = 1; i <= 26; i++) {
                values.add(i);
            }
        } else {
            // In normal mode, shuffle the values
            for (int i = 1; i <= 26; i++) {
                values.add(i);
            }
            Collections.shuffle(values);
        }

        // Create suitcases with values
        for (int i = 0; i < values.size(); i++) {
            Suitcase suitcase = new Suitcase(i + 1, getAmountForCase(values.get(i)));
            suitcases.add(suitcase);
        }

        return suitcases;
    }

    private static void displaySuitcases(List<Suitcase> suitcases) {
        for (Suitcase suitcase : suitcases) {
            System.out.println("Suitcase " + suitcase.getNumber());
        }
    }

    private static int getEliminatedCases(int round) {
        // Define the number of eliminated cases for each round
        int[] eliminatedCases = {6, 5, 4, 3, 2, 1, 1, 1, 1};

        // Ensure round is within bounds
        round = Math.min(Math.max(round, 1), eliminatedCases.length);

        return eliminatedCases[round - 1];
    }

    private static double calculateBankOffer(List<Suitcase> suitcases, int round) {
        // Calculate the total value of the remaining suitcases, including the one picked by the player
        double totalValue = suitcases.stream()
                .mapToDouble(Suitcase::getValue)
                .sum();

        // Calculate the expected value based on the current round
        double expectedValue = totalValue / suitcases.size();

        // Calculate the bank's offer based on the expected value
        return (expectedValue * round) / 10.0;
    }


    private static int getAmountForCase(int caseValue) {
        switch (caseValue) {
            case 1: return 1;
            case 2: return 50_000;
            case 3: return 400;
            case 4: return 25_000;
            case 5: return 500;
            case 6: return 1_000;
            case 7: return 75_000;
            case 8: return 10;
            case 9: return 10_000;
            case 10: return 200_000;
            case 11: return 1_000_000;
            case 12: return 500_000;
            case 13: return 200;
            case 14: return 5_000;
            case 15: return 5;
            case 16: return 75;
            case 17: return 100;
            case 18: return 300;
            case 19: return 75_000;
            case 20: return 50;
            case 21: return 100_000;
            case 22: return 300_000;
            case 23: return 400_000;
            case 24: return 750;
            case 25: return 1;
            case 26: return 1; // 1 cent
            default: return 0; // default to 0
        }
    }
}

