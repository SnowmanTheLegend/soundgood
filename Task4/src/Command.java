
	public enum Command {
		LISTINSTRUMENTS,
		
		NEW,
		
		RENTINSTRUMENT,
	    /**
	     * Creates a new account.
	     */
		TERMINATERENTAL,
	    /**
	     * Lists all existing accounts.
	     */
	    LIST,
	    /**
	     * Deletes the specified account.
	     */
	    DELETE,
	    /**
	     * Deposits the specified amount to the specified account
	     */
	    DEPOSIT,
	    /**
	     * Withdraws the specified amount from the specified account
	     */
	    WITHDRAW,
	    /**
	     * Lists the balance of the specified account.
	     */
	    BALANCE,
	    /**
	     * Lists all commands.
	     */
	    HELP,
	    /**
	     * Leave the chat application.
	     */
	    QUIT,
	    /**
	     * None of the valid commands above was specified.
	     */
	    ILLEGAL_COMMAND
}
