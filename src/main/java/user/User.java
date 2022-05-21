package user;

import java.io.Serializable;

/**
 * 
 * user interface for UserImpl.
 *
 */
public interface User extends Serializable {
    /**
     * 
     * @return the user name
     */
    String getName();

    /**
     * set user as winner.
     */
    void haveWon();

    /**
     * 
     * @return if the user won the game
     */
    boolean isGameWinner();
}
