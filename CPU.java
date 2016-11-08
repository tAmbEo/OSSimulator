/*
 * Simulate operation of OS
 * Developed by Team 2: TamNN, LyLV, ChinhPd
 * and open the template in the editor.
 */
package jp.co.tsdv.ossimulator;

import java.io.File;
import java.io.IOException;

/**
 * class described CPU with 6 register: PC, AC, SP, X, Y, IR 
 * @author thinkpad
 */
public class CPU {
    /**
     * PC register
     */
    
    /**
     * AC register
     */
    
    /**
     * SP register
     */
    
    /**
     * X register
     */
    
    /**
     * Y register
     */
    
    /**
     * IR register
     */
    
    /**
     * Local stack
     */
    
    /**
     * Memory
     */
    
    /**
     * status of CPU is running or not
     */
    
    /**
     * interrupt time
     */
    
    /**
     * counter
     */
    
    /**
     * CPU mode
     */
    
    /**
     * Initialize
     * @param file stored instruction set
     * @throws IOException when read file
     */
    private void initialize(File file) throws IOException {
        //initialize new memory
    }
    
    /**
     * CPU runs
     * @param file stored instruction set
     */
    public void runs(File file) {
        //initialize with file
        //set status is running
        //while status is running do 
        //fetch from memory to IR
        //call function with decoding instruction
        //interrupt 
    }
    
    /**
     * CPU fetch instruction from memory to IR register
     * 
     */
    private void fetchFromMemmoryToIR(){
        //read instruction from memmory
        //fetch to IR
    }
    
    /**
     * CPU call function with decoding instruction
     * 
     */
    private void callFunctionWithDecodingInstruction () {
        //read instruction from IR
        //call function
    }
    
    /**
     * interrupt function 
     */
    private void interrupt () {
        //check timme is interupt time
        //stores PC
        //stores SP
        //stores AC
        //Stores  X
        //stores Y
        //call timer interupt
        //if interrupt is finished (all instruction is run) turn to user mode
    }
    
    /**
     * timer interrupt function 
     */
    private void timerInterrupt() {
        //disable counter
        //change to system mode
        //set pc to system stack address 1000
        
    }
    
   
    
    /**
     * turn back from system mode to user mode 
     */
    private void turnBackToUserMode () {
        //restore all register
        //set mode is user mode
    }
    
    /**
     * turn back from system mode to user mode 
     */
    private void readFromMemory () {
        //restore all register
        //set mode is user mode
    }
    /*************Function of CPU **************/
    /**
     * load value to AC
     */
    private void loadValue () {
        //read from memory
        //load to AC
    }
    
    /**
     * load value of address to AC
     */
    private void loadAddrr () {
        //read from memory
        //load value of addrr to AC
    }
    
    /**
     * load value of address to AC
     */
    private void loadIndAddrr () {
        //read from memory
        //load value of inner addrr to AC
    }
    
    /**
     * load value of address to AC
     */
    private void loadIdXAddrr () {
        //read from memory
        //load value of inner addrr to AC
    }
    
    /**
     * load value of address to AC
     */
    private void loadIdYAddrr () {
        //read from memory
        //load value of inner addrr to AC
    }
    
    /**
     * load value of address to AC
     */
    private void loadSpXAddrr () {
        //read from memory
        //load value of (SP + X) addrr to AC
    }
    
    /**
     * store value of ac to address
     */
    private void storeAddrr () {
        //read from memory
        //write value of AC to addrr
    }
    
    /**
     * get random to AC
     */
    private void get () {
        //get random to AC
    }
    
    /**
     * port: 1 print int, 2 print char
     */
    private void putPort () {
        //read from memory
        //load value of (SP + X) addrr to AC
    }
    
    /**
     * Add value X to AC
     */
    private void addX () {
        
    }
    
    /**
     * Add value Y to AC
     */
    private void AddY () {
        
    }
    
    /**
     * sub value X from AC
     */
    private void subX () {
        
    }
    
    /**
     * sub value Y from AC
     */
    private void subY () {
        
    }
    
    /**
     * copy to X from AC
     */
    private void copyToX () {
        
    }
    
    /**
     * copy to Y from AC
     */
    private void copyToY () {
        
    }
    
    /**
     * copy to AC from X
     */
    private void copyFromX () {
        
    }
    
    /**
     * copy to AC from Y
     */
    private void copyFromY () {
        
    }
    
    /**
     * copy to SP from AC
     */
    private void copyToSP () {
        
    }
    
    /**
     * copy to AC from SP
     */
    private void copyFromSP () {
        
    }
    
    /**
     * jump to addrr
     */
    private void jumpAddrr () {
        
    }
    
    /**
     * jump if ac = 0
     */
    private void jumpIfEqual () {
        
    }
    
    /**
     * jump if ac != 0
     */
    private void jumpIfNotEqual () {
        
    }
    
    /**
     * push return address to stack, jump to address
     */
    private void call () {
        
    }
    
    /**
     * Pop return address from the stack, jump to the address
     */
    private void ret () {
        
    }
    
    /**
     * increment X
     */
    private void incX () {
        
    }
    /**
     * decrement X
     */
    private void decX () {
        
    }
    
    /**
     * push AC to Stack
     */
    private void push () {
        
    }
    
    /**
     * pop to AC from Stack
     */
    private void pop () {
        
    }
    
     /**
     * system interrupt function 
     */
    private void systemInterrupt() {
        //disable counter
        //change to system mode
        //set pc to system stack address 1500
        
    }
    /**
     * return to user mode
     */
    private void iRet() {
        
    }
    
    /**
     * end application
     */
    private void end() {
        
    }
}
