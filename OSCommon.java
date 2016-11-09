/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jp.co.tsdv.ossimulator;

/**
 *
 * @author thinkpad
 */
public class OSCommon {
    /**
     * memory size
     */
    public static final int MEMORY_SIZE = 240;
    /**
     * invalid access message 
     */
    public static final String INVALID_ACCESS_MESSAGE = "Invalid access to memory";
    
    /**
     * index of range out message
     */
    public static final String INDEX_OUT_OF_RANGE = "Index out of range";
    
    /**
     * top of timer instruction stack
     */
    public static final int TOP_TIMER = 1000;
    
    /**
     * top of system instruction stack
     */
    public static final int TOP_SYSTEM = 1500;
    
    /**
     * bottom of user stack
     */
    public static final int BOTTOM_USER = 999;
    
    /**
     * bottom of system stack
     */
    public static final int BOTTOM_SYSTEM = 1999;
    
    /**
     * user mode
     */
    public static final int USER_MODE = 0;
    
    /**
     * timer mode
     */
    public static final int TIMER_MODE = 1;
    
    /**
     * system mode
     */
    public static final int SYSTEM_MODE = 2;
    
    /**
     * when empty instruction set
     */
    public static final int EMPTY_INSTRUCTION_SET = 0;
    
    /**
     * comment interrupt
     */
    public static final String INTERRUPT_COMMENT = ".1000";
     
}
