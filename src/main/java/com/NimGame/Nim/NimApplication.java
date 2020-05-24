package com.NimGame.Nim;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;
import java.util.Scanner;


@SpringBootApplication
public class NimApplication {

	public static void main(String[] args) {
		SpringApplication.run(NimApplication.class, args);
		Scanner sc = new Scanner(System.in);
		System.out.println("How many stones do you want to start with ?");
		int stones = sc.nextInt();
		NimGame game = new NimGame(stones);
		MiniMaxSearch<List<Integer>, Integer, Integer> minimaxSearch =
				MiniMaxSearch.createFor(game);
		List<Integer> state = game.getInitialState();
		while (!game.isTerminal(state)) {
			System.out.println("======================");
			System.out.println(state);
			int action = -1;
			if (state.get(0) == 0) {
				//human
				List<Integer> actions = game.getActions(state);
				Scanner in = new Scanner(System.in);
				while (!actions.contains(action)) {
					System.out.println("Puny human, what is your choice ?");
					action = in.nextInt();
				}
			} else {
				//ultron
				System.out.println("Ultron's turn");
				action = minimaxSearch.makeDecision(state);
				System.out.println("Metrics for Minimax : " +
						minimaxSearch.getMetrics());
			}
			System.out.println("Chosen action is ==> " + action);
			state = game.getResult(state, action);
			StringBuilder stonesLeft = new StringBuilder();
			for(int i = 1; i <= state.get(1); i++) {
				stonesLeft.append("*");
			}
			System.out.println("Stones left ==> " + stonesLeft.toString());
		}
		System.out.print("GAME OVER: ");
		if (state.get(0) == 0)
			System.out.println("Human wins!");
		else
			System.out.println("Ultron wins!");
	}


}
