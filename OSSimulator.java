/*
 * Simulate operation of OS
 * Developed by Team 2: TamNN, LyLV, ChinhPd
 * and open the template in the editor.
 */
package jp.co.tsdv.ossimulator;

/**
 * Main function to run application
 *
 * @author thinkpad
 */
public class OSSimulator {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            //if option is help notice guideline 
            if (args[0].equals("-help")) {
                System.out.println("command: java OSSimulator [file_path] [interrupt_time]");
                System.out.println("[file_path]: file path of storaged instruction file");
                System.out.println("[interrupt_time]: type number, time for interrupt");
            } else if (args.length == 2) {
                int timeInterrupt = Integer.parseInt(args[1]);
                if (timeInterrupt < 0) {
                    System.out.println("Wrong option, please type java OSSimulator -help for guideline");
                } else {
                    CPU cpu = new CPU(timeInterrupt);
                    cpu.runs(args[0]);
                }
            } else {
                System.out.println("Wrong option, please type java OSSimulator -help for guideline");
            }
        } catch (NumberFormatException exp) {
            System.out.println("Wrong option, please type java OSSimulator -help for guideline");
        }
        //check length of aregument if failed, notice error        
    }
    
}
