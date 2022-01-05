import java.util.List;
import java.util.Scanner;
public class BlockingInterpreter {
	private static final String PROMPT = "> ";
	private boolean keepReceivingCmds = false;
	 private final Scanner console = new Scanner(System.in);
	 private Controller ctrl;

	 public BlockingInterpreter(Controller ctrl) {
	        this.ctrl = ctrl;
	    }
	 
	 public void stop() {
         keepReceivingCmds = false;
     }
	 
	 public void handleCmds() {
		 
	        keepReceivingCmds = true;
	        
	        
	        
	        while (keepReceivingCmds) {
	            try {
	                CmdLine cmdLine = new CmdLine(readNextLine());
	                switch (cmdLine.getCmd()) {
	                    case HELP:
	                        for (Command command : Command.values()) {
	                            if (command == Command.ILLEGAL_COMMAND) {
	                                continue;
	                            }
	                            System.out.println(command.toString().toLowerCase());
	                        }
	                        break;
	                    case QUIT:
	                        keepReceivingCmds = false;
	                        break;
	                    case LISTINSTRUMENTS:
	                    	ctrl.listinstruments();
	                        break;
	                    case RENTINSTRUMENT:
	                    	ctrl.rentinstruments(cmdLine.getParameter(0), cmdLine.getParameter(1));
	                    	break;
	                    case TERMINATERENTAL:
	                    	ctrl.terminaterental(cmdLine.getParameter(0));
	                        break;
	                    
	                    default:
	                        System.out.println("illegal command");
	                }
	            } catch (Exception e) {
	                System.out.println("Operation failed");
	                System.out.println(e.getMessage());
	                e.printStackTrace();
	            }
	        }
	    }
	 private String readNextLine() {
	        System.out.print(PROMPT);
	        return console.nextLine();
	    }
}
