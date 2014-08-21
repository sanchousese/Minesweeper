package com.company;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
	    PlayerField playerField = new PlayerField(10, 11, 10);
        int x, y;
        Scanner scanner = new Scanner(System.in);
        while (true) {
            x = scanner.nextInt();
            y = scanner.nextInt();

            playerField.setFlag(x, y);

            if (!playerField.playerMove(x, y)){
                System.out.println("You lose!");
                break;
            }

            System.out.println(playerField);
        }
    }
}
