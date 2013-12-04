package logic;

import java.util.ArrayList;

import datastructures.BetLimit;
import datastructures.BlindStructure;
import datastructures.Player;

public class Tournament {
	
	ArrayList<PokerSimulation> tournamentTables;
	ArrayList<Player> tournamentPlayers;
	BlindStructure blindStructure;
	BetLimit betLimit;
	
	protected Tournament(int numberOfTables, int numberOfPlayersPerTable, BetLimit limit) {
		tournamentTables = new ArrayList<>();
		betLimit = limit;
		for (int tableNumber = 0; tableNumber < numberOfTables; tableNumber++) {
			PokerSimulation pokerSim = new PokerSimulation(numberOfPlayersPerTable, limit);
			tournamentTables.add(pokerSim);
			tournamentPlayers.addAll(pokerSim.players);
		}
	}
}
