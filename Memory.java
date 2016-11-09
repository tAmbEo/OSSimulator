/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jp.co.tsdv.ossimulator;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

/**
 *
 * @author thinkpad
 */
public class Memory {
    /**
     * entries
     */ 
    private int[] entries;
    
    /**
     * initialize
     * @param filePath store instruction
     * @throws IOException when read file
     * @throws IndexOutOfBoundsException when memory is full
     */
    public void initialize(String filePath) throws IOException, IndexOutOfBoundsException {
        this.entries = new int[OSCommon.MEMORY_SIZE];
        File file = new File(filePath); 
        int address = 0;
        boolean isReadForUserMode = true;
        Scanner scanner = new Scanner(file);
        while(scanner.hasNext()) {
            String line = scanner.nextLine();
            line = line.trim();
            if (isInterruptComment(line) && isReadForUserMode) {
                address = OSCommon.TOP_TIMER;
            } else if (!line.isEmpty() && isNotCommentLine(line)) {
                checkMemoryIsFullForAddress(isReadForUserMode, address);
                int instruction = getInstructionFromLine(line);
                write(address ++ , instruction);
            }  
        }
    }
    /**
     * Check memory if full or not, if full throw IndexofBound Erro 
     */
    private void checkMemoryIsFullForAddress(boolean isReadForUserMode,int address){
        if (isReadForUserMode && address == 999) throw new IndexOutOfBoundsException();
        else if (address == 1499) throw new IndexOutOfBoundsException();
    }
    /**
     * check line is comment line or not
     * @param line is check
     * return true if line is comment
     */
    private boolean isNotCommentLine(String line) {
        boolean ret;
        char character = line.charAt(0);
        if (isMinus(character) && line.length() < 2) {
            ret = false;
        } else if (isMinus(character)) {
            character = line.charAt(1);
            ret = isDigit(character);
        } else {
            ret = isDigit(character);
        }
        return ret;
    } 
    /**
     * check character is digit or not
     * @param character is check
     * return true if character is digit
     */
    private boolean isDigit(char character) {
        return (character >= '0' && character <= '9');
    }
    
    /**
     * check character is minus or not
     * @param character is check
     * return true if character is minus
     */
    private boolean isMinus(char character) {
        return character == '-';
    }
    /**
     * write
     * @param address of memory
     * @param value is written
     */
    public void write (int address, int value) {
        this.entries[address] = value;
    }
    /**
     * clear memory
     */
    public void clear() {
        this.entries = new int[OSCommon.MEMORY_SIZE];
    }
    /**
     * read
     * @param address of memory
     * @return value
     */
    public int read (int address) throws IndexOutOfBoundsException{
        return this.entries[address];
    }
    /**
     * get instruction from line
     * @param line
     * return instruction
     */
    private int getInstructionFromLine(String line) {
        int endOfInstruction = 1;
        boolean hasNextDigit = true;
        for (int i = 1; i < line.length() && hasNextDigit ; i++) {
            char character = line.charAt(i);
            if (isDigit(character)) endOfInstruction++;
            else hasNextDigit = false;
        }
        String instructionString = line.substring(0, endOfInstruction);
        int instruction = Integer.parseInt(instructionString);
        return instruction;
    }
    
    /**
     * check line is interrupt comment or not
     * @param line is check
     * return true if line is interrupt comment
     */
    private boolean isInterruptComment(String line) {
        if (line.length() < 6) {
            return line.equals(OSCommon.INTERRUPT_COMMENT);
        } else {
            return line.substring(0,5).equals(OSCommon.INTERRUPT_COMMENT);
        }
    }
}
