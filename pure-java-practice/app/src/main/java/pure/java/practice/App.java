/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package pure.java.practice;

import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        try(Scanner scanner = new Scanner(System.in)){
            String input = scanner.nextLine();
            System.out.println(input);
        }
    }
}