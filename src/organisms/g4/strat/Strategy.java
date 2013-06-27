package organisms.g4.strat;

import organisms.Move;
import organisms.g4.MoveInfo;

public interface Strategy {
	Move reproduce(MoveInfo moveData);

	Move makeMove(MoveInfo moveData);
}
