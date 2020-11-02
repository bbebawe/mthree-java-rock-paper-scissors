package com.mthree.rockpaperscissors;
// imoprt random and scanner form java.util 

import java.util.Random;
import java.util.Scanner;

/**
 * A program that plays the game Rock, Paper, Scissors.
 *
 * @author Beshoy Bebawe
 */
public class RockPaperScissors {

    // begin main method to start program execution
    public static void main(String[] args) {
        // program variables
        byte menuOption;
        byte minRounds = 1;
        byte maxRounds = 10;
        byte gameRounds;
        String userChoice;
        String computerChoice;
        String roundWinner;
        String gameWinner;
        String[] roundsResults;
        byte numberOfTies = 0;
        byte numberOfUserWins = 0;
        byte numberOfComputerWins = 0;
        boolean continueGame = true;

        // display menu to the user till user request to exit game
        do {
            // dispaly menu options
            menuOption = getMenuCoice(minRounds, maxRounds);
            // if input is 0 exit game
            if (menuOption == 0) {
                continueGame = false;
                printGoodbyeMessage();
            } else {
                gameRounds = menuOption;
                // intialise roundResults array to hold result for each round
                roundsResults = new String[gameRounds];
                // play the game till game rounds are at max
                for (int i = 0; i < gameRounds; i++) {
                    System.out.println("===== Starting Round: " + (i + 1) + " =====");
                    userChoice = getUserChoice();
                    System.out.println("You chose: " + userChoice);
                    computerChoice = getComputerChoice();
                    System.out.println("Computer chose: " + computerChoice);
                    roundWinner = getRoundWinner(userChoice, computerChoice);
                    displayRoundWinner(roundWinner);
                    // keep track of each round winner
                    switch (roundWinner) {
                        case "User":
                            roundsResults[i] = "User";
                            numberOfUserWins++;
                            break;
                        case "Computer":
                            roundsResults[i] = "Computer";
                            numberOfComputerWins++;
                            break;
                        default:
                            roundsResults[i] = "Tie";
                            numberOfTies++;
                    } // end switch
                }
                // calculate game winner
                gameWinner = findGameWinner(numberOfUserWins, numberOfComputerWins);
                displayFinalGameResults(gameWinner, gameRounds, numberOfTies, numberOfUserWins, numberOfComputerWins);
                // prompt user to exit game or play again
                continueGame = endGame();
                if (continueGame == false) {
                    printGoodbyeMessage();
                } else { // rest game variables for a new game
                    numberOfTies = 0;
                    numberOfUserWins = 0;
                    numberOfComputerWins = 0;
                }
            }
        } while (continueGame);
    } // end main()

    /**
     * Return user choice form menu. 0 to end game or range between 1 and 10 for
     * number of game rounds.
     *
     * @param minRounds minimum number of allowed game rounds
     * @param maxRounds maximum number of allowed game rounds
     * @return user choice from options menu
     */
    public static byte getMenuCoice(int minRounds, int maxRounds) {
        Scanner input = new Scanner(System.in);
        String userInput = null;
        byte parsedUserInput;
        byte menuChoice = 0;
        boolean isValidInput = false;

        do {
            try {
                System.out.println("Please enter how many rounds do you want to play the game?\nMin rounds = 1 \nMax rounds = 10 \nExit game = 0");
                userInput = input.nextLine();
                parsedUserInput = Byte.parseByte(userInput);
                if (parsedUserInput == 0 | parsedUserInput >= minRounds && parsedUserInput <= maxRounds) {
                    isValidInput = true;
                    // this will also be used to represent number of game rounds if input > 0
                    menuChoice = parsedUserInput;
                } else {
                    System.out.println(userInput + " is out of range, game rounds can be from 1 to 10");
                }
            } catch (NumberFormatException ex) {
                System.out.println("Please enter a valid number, " + userInput + " is not a valid number");
            } // end catch block 
        } while (!isValidInput);
        return menuChoice;
    } // end getMenuCoice()

    /**
     * Prompts user to choose from Rock, Paper, Scissors user input is validated
     * to accept only valid options (Rock, Paper, Scissors)
     *
     * @return user choice (Rock, Paper, Scissors)
     */
    public static String getUserChoice() {
        Scanner input = new Scanner(System.in);
        String userInput = null;
        byte parsedUserInput = 0;
        String userChoice = null;
        boolean validInput = false;

        do {
            try {
                System.out.println("What is your choice?");
                System.out.print("1 = Rock \n2 = Paper\n3 = Scissors\n");
                userInput = input.nextLine();
                parsedUserInput = Byte.parseByte(userInput);
                if (parsedUserInput >= 1 && parsedUserInput <= 3) {
                    validInput = true;
                    switch (parsedUserInput) {
                        case 1:
                            userChoice = "Rock";
                            break;
                        case 2:
                            userChoice = "Paper";
                            break;
                        case 3:
                            userChoice = "Scissors";
                    } // end switch 
                } else {
                    System.out.println(userInput + " is not a valid choice, choice must be 1, 2 or 3");
                }
            } catch (NumberFormatException ex) {
                System.out.println("Please enter a valid number, " + userInput + " is not a valid number");
            } // end catch block 
        } while (!validInput);
        return userChoice;
    } // end getUserChoice()

    /**
     * Uses a built in Random class to generate random input that represents
     * computer choice
     *
     * @return computer choice (Rock, Paper, Scissors)
     */
    public static String getComputerChoice() {
        Random random = new Random();
        String computerChoice = null;
        int randomChoice = random.nextInt(3) + 1;
        switch (randomChoice) {
            case 1:
                computerChoice = "Rock";
                break;
            case 2:
                computerChoice = "Paper";
                break;
            case 3:
                computerChoice = "Scissors";
        } // end switch 
        return computerChoice;
    } // end getComputerChoice()

    /**
     * Uses game rules to find round winner.
     *
     * @param userChoice user choice (Rock, Paper, Scissors)
     * @param computerChoice computer choice (Rock, Paper, Scissors)
     * @return round winner (User, Computer, Tie)
     */
    public static String getRoundWinner(String userChoice, String computerChoice) {
        String winner;
        if (userChoice.equals(computerChoice)) {
            winner = "Tie";
        } else if (userChoice.equals("Rock") && computerChoice.equals("Scissors")) {
            winner = "User";
        } else if (userChoice.equals("Scissors") && computerChoice.equals("Paper")) {
            winner = "User";
        } else if (userChoice.equals("Paper") && computerChoice.equals("Rock")) {
            winner = "User";
        } else {
            winner = "Computer";
        }
        return winner;
    } // end getRoundWinner()

    /**
     * Outputs round result to console.
     *
     * @param roundWinner round winner (User, Computer, Tie)
     */
    public static void displayRoundWinner(String roundWinner) {
        if (roundWinner.equals("Tie")) {
            System.out.println("No winner this round, it is a Tie");
        } else {
            System.out.println("Round Winner: " + roundWinner);
        }
        System.out.println("=============================");
    } // end displayRoundWinner()

    /**
     * Calculates game winner according to the number of rounds won.
     *
     * @param numberOfUserWins number of rounds won by user
     * @param numberOfComputerWins number of round won by computer
     * @return final game result (User, Computer, Tie)
     */
    public static String findGameWinner(int numberOfUserWins, int numberOfComputerWins) {
        String winner;
        if (numberOfUserWins > numberOfComputerWins) {
            winner = "User";
        } else if (numberOfUserWins < numberOfComputerWins) {
            winner = "Computer";
        } else {
            winner = "Tie";
        }
        return winner;
    } // end findGameWinner()

    /**
     * Outputs final game results to console.
     *
     * @param gameWinner game winner (User, Computer, Tie)
     * @param gameRounds total number of rounds played
     * @param numberOfTies number of tie rounds
     * @param numberOfUserWins number of rounds won by user
     * @param numberOfComputerWins number of rounds won by computer
     */
    public static void displayFinalGameResults(String gameWinner, int gameRounds, int numberOfTies, int numberOfUserWins, int numberOfComputerWins) {
        if (gameWinner.equals("Tie")) {
            System.out.println("No winner today, it is a Tie, ");
        } else {
            System.out.println("Game Winner Is " + gameWinner);
        }
        System.out.println("Total rounds played: " + gameRounds);
        System.out.println("Number of Tie rounds: " + numberOfTies);
        System.out.println("Number of Rounds user won: " + numberOfUserWins);
        System.out.println("Number of rounds computer won: " + numberOfComputerWins);
        System.out.println("=============================");

    } // end displayFinalGameResults()

    /**
     * Prompts the user to continue or exit the game after playing all game
     * rounds
     *
     * @return 1 to play game again, 2 to exit game
     */
    public static boolean endGame() {
        Scanner input = new Scanner(System.in);
        String userInput = null;
        byte parsedUserInput;
        boolean exitGame = false;
        boolean validInput = false;
        do {
            try {
                System.out.println("Do you want to play again?\n1 = Yes \n2 = No");
                userInput = input.nextLine();
                parsedUserInput = Byte.parseByte(userInput);
                if (parsedUserInput == 1 | parsedUserInput == 2) {
                    validInput = true;
                    if (parsedUserInput == 1) {
                        exitGame = true;
                    } else {
                        exitGame = false;
                    }
                } else {
                    System.out.println("Please choose 1 or 2");
                }
            } catch (NumberFormatException ex) {
                System.out.println("Please enter a valid number, " + userInput + " is not a valid number");
            } // end catch block 
        } while (!validInput);

        return exitGame;
    } // end endGame()

    /**
     * Outputs goodbye message when user exits the game
     */
    public static void printGoodbyeMessage() {
        System.out.println("===== Exiting Game ====");
        System.out.println("===== GoodBye =====");
    } // end printGoodbyeMessage()

}
