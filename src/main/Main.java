package main;


import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try {
            BlackJackUI ui;
            Scanner scanner = new Scanner(System.in);

            System.out.println("Initial money amount: ");
            int initial = scanner.nextInt();

            ui = new BlackJackUI(initial);
            System.out.println("Bet: ");

            System.out.println(ui.getBlackJack().getPlayer());
            int bet = scanner.nextInt();
            ui.start(bet);

            int choice = 0;
            while (choice != BlackJackAction.SPLIT.ordinal()) {
                System.out.println(ui.getMenu());
                choice = scanner.nextInt();
                System.out.println(ui.doAction(choice));
            }
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            System.out.println("Ooops, something went wrong!");
            System.exit(1);
        }

    }
}
