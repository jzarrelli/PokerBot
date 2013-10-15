package logic;

import java.util.ArrayList;

import datastructures.BlindStructure;
import datastructures.Player;

public class Tournament {
	
	ArrayList<PokerSimulation> tournamentTables;
	ArrayList<Player> tournamentPlayers;
	BlindStructure blindStructure;
	
	protected Tournament(int numberOfTables, int numberOfPlayersPerTable) {
		tournamentTables = new ArrayList<>();
		for (int tableNumber = 0; tableNumber < numberOfTables; tableNumber++) {
			PokerSimulation pokerSim = new PokerSimulation(numberOfPlayersPerTable);
			tournamentTables.add(pokerSim);
			tournamentPlayers.addAll(pokerSim.players);
		}
	}
}
