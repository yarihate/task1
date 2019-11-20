package main.java.com.gelasimova;
import java.util.*;

public abstract class Piece {
    private Colour colour;
    private Space currentSpace;
    private Player player;
    protected Space candidateSpace;
    protected ArrayList<Space> availableSpaces = new ArrayList<>();
    protected boolean cannotMoveFurther = false;

    public Piece(Colour colour, Space space) {
        this.colour = colour;
        this.currentSpace = space;
    }

    public Colour getColour() {
        return colour;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    private void setCurrentSpace(Space currentSpace) {
        this.currentSpace = currentSpace;
    }

    protected int getCurrentX() {
        return currentSpace.getX();
    } //public?

    protected char getCurrentY() {
        return currentSpace.getY();
    } //public?

    public String getCoordinates() {
        return getCurrentX() + "" + getCurrentY();
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "-" + colour;
    }

    public abstract ArrayList<Space> getAvailableMoves(Board board);

    protected Space findCandidateSpaceOnTheBoard(Space candidateSpace, Board board) {
        if (candidateSpace.isSpaceOnTheBoard()) {
            for (Space spaceOnTheBoard : board.getBoard()) {
                if (spaceOnTheBoard.equals(candidateSpace)) {
                    return spaceOnTheBoard;
                }
            }
        }
        return null;
    }

    protected void addIfMoveIsAvailable(Space candidateSpace, Board board) {
        candidateSpace = findCandidateSpaceOnTheBoard(candidateSpace, board);
        if (candidateSpace != null) {
            if (candidateSpace.getPiece() == null) {
                availableSpaces.add(candidateSpace);
            } else if (candidateSpace.getPiece().colour != colour) {
                availableSpaces.add(candidateSpace);
                cannotMoveFurther = true;
            } else cannotMoveFurther = true;
        }
    }

    protected void move(Space availableSpace, Board board) {
        Piece enemyPiece = availableSpace.getPiece();
        if (enemyPiece != null) {
            System.out.println(enemyPiece + " " + enemyPiece.getCoordinates() + " was beaten.");
            enemyPiece.player.getListOfPieces().remove(enemyPiece);
        } else System.out.println(this + " moves on " + availableSpace.getSpaceCoordinates());
        currentSpace = findCandidateSpaceOnTheBoard(currentSpace, board);
        this.currentSpace.setPiece(null);
        availableSpace.setPiece(this);
        setCurrentSpace(availableSpace);
    }
}

class Pawn extends Piece {
    Pawn(Colour colour, Space space) {
        super(colour, space);
    }

    @Override
    public ArrayList<Space> getAvailableMoves(Board board) {
        availableSpaces = new ArrayList<>();
        ArrayList<Space> candidateSpaces;
        if (getColour() == Colour.BLACK) {
            candidateSpace = findCandidateSpaceOnTheBoard(new Space(getCurrentX() + 1, getCurrentY()), board);
            candidateSpaces = new ArrayList<>(Arrays.asList(
                    new Space(getCurrentX() + 1, (char) (getCurrentY() - 1)),
                    new Space(getCurrentX() + 1, (char) (getCurrentY() + 1)),
                    new Space(getCurrentX() - 1, (char) (getCurrentY() - 1)),
                    new Space(getCurrentX() - 1, (char) (getCurrentY() + 1))));
        } else {
            candidateSpace = findCandidateSpaceOnTheBoard(new Space(getCurrentX() - 1, getCurrentY()), board);
            candidateSpaces = new ArrayList<>(Arrays.asList(
                    new Space(getCurrentX() - 1, (char) (getCurrentY() - 1)),
                    new Space(getCurrentX() - 1, (char) (getCurrentY() + 1)),
                    new Space(getCurrentX() + 1, (char) (getCurrentY() - 1)),
                    new Space(getCurrentX() + 1, (char) (getCurrentY() + 1))));
        }
        if (candidateSpace != null && candidateSpace.getPiece() == null) {
            availableSpaces.add(candidateSpace);
        }
        //extra check (findCandidateSpaceOnTheBoard/candidateSpace.getPiece() != null) because of pawn`s move logic
        for (Space space : candidateSpaces) {
            candidateSpace = findCandidateSpaceOnTheBoard(space, board);
            if (candidateSpace != null && candidateSpace.getPiece() != null) {
                addIfMoveIsAvailable(candidateSpace, board);
            }
        }
        return availableSpaces;
    }
}

class Rook extends Piece {
    Rook(Colour colour, Space space) {
        super(colour, space);
    }

    @Override
    public ArrayList<Space> getAvailableMoves(Board board) {
        availableSpaces = new ArrayList<>();
        //←
        for (char y = (char) (getCurrentY() - 1); y >= 'a'; y--) {
            candidateSpace = new Space(getCurrentX(), y);
            if (!cannotMoveFurther) {
                addIfMoveIsAvailable(candidateSpace, board);
            }
        }
        cannotMoveFurther = false;
        //→
        for (char y = (char) (getCurrentY() + 1); y <= 'h'; y++) {
            candidateSpace = new Space(getCurrentX(), y);
            if (!cannotMoveFurther) {
                addIfMoveIsAvailable(candidateSpace, board);
            }
        }
        cannotMoveFurther = false;
        //↓
        for (int x = getCurrentX() - 1; x >= 1; x--) {
            candidateSpace = new Space(x, getCurrentY());
            if (!cannotMoveFurther) {
                addIfMoveIsAvailable(candidateSpace, board);
            }
        }
        cannotMoveFurther = false;
        //↑
        for (int x = getCurrentX() + 1; x <= 8; x++) {
            candidateSpace = new Space(x, getCurrentY());
            if (!cannotMoveFurther) {
                addIfMoveIsAvailable(candidateSpace, board);
            }
        }
        return availableSpaces;
    }
}

class Knight extends Piece {
    Knight(Colour colour, Space space) {
        super(colour, space);
    }

    @Override
    public ArrayList<Space> getAvailableMoves(Board board) {
        availableSpaces = new ArrayList<>();
        ArrayList<Space> candidateSpaces = new ArrayList<>(Arrays.asList(
                new Space(getCurrentX() - 1, (char) (getCurrentY() + 2)),
                new Space(getCurrentX() - 1, (char) (getCurrentY() - 2)),
                new Space(getCurrentX() + 2, (char) (getCurrentY() - 1)),
                new Space(getCurrentX() + 2, (char) (getCurrentY() + 1)),
                new Space(getCurrentX() - 2, (char) (getCurrentY() + 1)),
                new Space(getCurrentX() - 2, (char) (getCurrentY() - 1)),
                new Space(getCurrentX() + 1, (char) (getCurrentY() - 2)),
                new Space(getCurrentX() + 1, (char) (getCurrentY() + 2))));
        candidateSpaces.forEach(space -> addIfMoveIsAvailable(space, board));
        return availableSpaces;
    }
}

class Bishop extends Piece {
    Bishop(Colour colour, Space space) {
        super(colour, space);
    }

    @Override
    public ArrayList<Space> getAvailableMoves(Board board) {
        availableSpaces = new ArrayList<>();
        //←↓
        for (int x = getCurrentX() - 1; x >= 1; x--) {
           char y = (char) (getCurrentY()- 1);
            candidateSpace = new Space(x, y);
            if (!cannotMoveFurther) {
                addIfMoveIsAvailable(candidateSpace, board);
            }
        }
        cannotMoveFurther = false;
        //→↓
        for (int x = getCurrentX() - 1; x >= 1; x--) {
            char y = (char) (getCurrentY() + 1);
            candidateSpace = new Space(x, y);
            if (!cannotMoveFurther) {
                addIfMoveIsAvailable(candidateSpace, board);
            }
        }
        cannotMoveFurther = false;
        //←↑
        for (int x = getCurrentX() + 1; x <= 8; x++) {
            char y = (char) (getCurrentY() - 1);
            candidateSpace = new Space(x, y);
            if (!cannotMoveFurther) {
                addIfMoveIsAvailable(candidateSpace, board);
            }
        }
        cannotMoveFurther = false;
        //→↑
        for (int x = getCurrentX() + 1; x <= 8; x++) {
            char y = (char) (getCurrentY() + 1);
            candidateSpace = new Space(x, y);
            if (!cannotMoveFurther) {
                addIfMoveIsAvailable(candidateSpace, board);
            }
        }
        return availableSpaces;
    }
}

class Queen extends Piece {
    Queen(Colour colour, Space space) {
        super(colour, space);
    }

    @Override
    public ArrayList<Space> getAvailableMoves(Board board) {
        availableSpaces = new ArrayList<>();
        //←
        for (char y = (char) (getCurrentY() - 1); y >= 'a'; y--) {
            candidateSpace = new Space(getCurrentX(), y);
            if (!cannotMoveFurther) {
                addIfMoveIsAvailable(candidateSpace, board);
            }
        }
        cannotMoveFurther = false;
        //→
        for (char y = (char) (getCurrentY() + 1); y <= 'h'; y++) {
            candidateSpace = new Space(getCurrentX(), y);
            if (!cannotMoveFurther) {
                addIfMoveIsAvailable(candidateSpace, board);
            }
        }
        cannotMoveFurther = false;
        //↓
        for (int x = getCurrentX() - 1; x >= 1; x--) {
            candidateSpace = new Space(x, getCurrentY());
            if (!cannotMoveFurther) {
                addIfMoveIsAvailable(candidateSpace, board);
            }
        }
        cannotMoveFurther = false;
        //↑
        for (int x = getCurrentX() + 1; x <= 8; x++) {
            candidateSpace = new Space(x, getCurrentY());
            if (!cannotMoveFurther) {
                addIfMoveIsAvailable(candidateSpace, board);
            }
        }
        cannotMoveFurther = false;
        //←↓
        for (int x = getCurrentX() - 1; x >= 1; x--) {
            char y = (char) (getCurrentY() - 1);
            candidateSpace = new Space(x, y);
            if (!cannotMoveFurther) {
                addIfMoveIsAvailable(candidateSpace, board);
            }
        }
        cannotMoveFurther = false;
        //→↓
        for (int x = getCurrentX() - 1; x >= 1; x--) {
            char y = (char) (getCurrentY() + 1);
            candidateSpace = new Space(x, y);
            if (!cannotMoveFurther) {
                addIfMoveIsAvailable(candidateSpace, board);
            }
        }
        cannotMoveFurther = false;
        //←↑
        for (int x = getCurrentX() + 1; x <= 8; x++) {
            char y = (char) (getCurrentY() - 1);
            candidateSpace = new Space(x, y);
            if (!cannotMoveFurther) {
                addIfMoveIsAvailable(candidateSpace, board);
            }
        }
        cannotMoveFurther = false;
        //→↑
        for (int x = getCurrentX() + 1; x <= 8; x++) {
            char y = (char) (getCurrentY() + 1);
            candidateSpace = new Space(x, y);
            if (!cannotMoveFurther) {
                addIfMoveIsAvailable(candidateSpace, board);
            }
        }
        return availableSpaces;
    }
}

class King extends Piece {
    King(Colour colour, Space space) {
        super(colour, space);
    }

    @Override
    public ArrayList<Space> getAvailableMoves(Board board) {
        availableSpaces = new ArrayList<>();
        for (int x = getCurrentX() - 1; x <= getCurrentX() + 1; x++) {
            for (char y = (char) (getCurrentY() - 1); y <= (char) (getCurrentY() + 1); y++) {
                if (x == getCurrentX() && y == getCurrentY()) {
                    continue;
                }
                candidateSpace = new Space(x, y);
                addIfMoveIsAvailable(candidateSpace, board);
            }
        }
        return availableSpaces;
    }
}


