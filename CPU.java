/*
 * Simulate operation of OS
 * Developed by Team 2: TamNN, LyLV, ChinhPd
 * and open the template in the editor.
 */
package jp.co.tsdv.ossimulator;

import java.io.IOException;
import java.util.EmptyStackException;
import java.util.Random;

/**
 * class described CPU with 6 register: PC, AC, SP, X, Y, IR 
 * @author thinkpad
 */
public class CPU {
    /**
     * PC register
     */
    private int pcRegister;
    /**
     * AC register
     */
    private int acRegister;
    /**
     * SP register
     */
    private int spRegister;
    /**
     * X register
     */
    private int xRegister;
    /**
     * Y register
     */
    private int yRegister;
    /**
     * IR register
     */
    private int irRegister;
    /**
     * Local stack
     */
    private int[] stack;
    /**
     * Memory
     */
    private Memory memory;
    
    /**
     * status of CPU is running or not
     */
    private boolean isRunning;
    
    /**
     * interrupt time
     */
    private int interruptTime;
    
    /**
     * counter
     */
    private int counter;
    /**
     * CPU mode
     */
    private int mode;
    
    /**
     * constructor
     * @param interruptTime time for interrupt
     */
    public CPU(int interruptTime) {
        this.interruptTime = interruptTime;
    }
    
    /**
     * Initialize
     * @param filePath stored instruction set
     * @throws IOException when read file
     */
    private void initialize(String filePath) throws IOException, IndexOutOfBoundsException {
        memory = new Memory();
        memory.initialize(filePath); 
        //set mode is user
        mode = OSCommon.USER_MODE;
        //set status is running
        this.isRunning = true;
    }
    
    /**
     * CPU runs
     * @param filePath stored instruction set
     */
    public void runs(String filePath) {
        try {
            //initialize with file
            this.initialize(filePath);
            
            //while status is running do 
            while (this.isRunning) {
                callFunction();
                interrupt();
            }
        } catch (IOException exp) {
            System.out.print("file is not good");
        } catch (IndexOutOfBoundsException ex) {
            System.out.print("file is too big");
        }

    }
    
    /**
     * CPU fetch instruction from memory to IR register
     * 
     */
    private void fetchFromMemmoryToIR(){
        irRegister = readFromMemoryByPC();
    }
    
    /**
     * CPU call function with decoding instruction
     * 
     */
    private void callFunction () {
        try {
            fetchFromMemmoryToIR();
            //read instruction from IR
            int instruction = irRegister;
                switch (instruction) {
                case 1:     loadValue();            break;
                case 2:     loadAddr();             break;
                case 3:     loadIndAddrr();         break;
                case 4:     loadIdXAddrr();         break;
                case 5:     loadIdYAddrr();         break;
                case 6:     loadSpXAddrr();         break;
                case 7:     storeAddrr();           break;
                case 8:     get();                  break;
                case 9:     putPort();              break;
                case 10:    addX();                 break;
                case 11:    addY();                 break;
                case 12:    subX();                 break;
                case 13:    subY();                 break;
                case 14:    copyToX();              break;
                case 15:    copyFromX();            break;
                case 16:    copyToY();              break;
                case 17:    copyFromY();            break;
                case 18:    copyToSP();             break;
                case 19:    copyFromSP();           break;
                case 20:    jumpAddrr();            break;
                case 21:    jumpIfEqual();          break;
                case 22:    jumpIfNotEqual();       break;
                case 23:    call();                 break;
                case 24:    ret();                  break;
                case 25:    incX();                 break;
                case 26:    decX();                 break;
                case 27:    push();                 break;
                case 28:    pop();                  break;
                case 29:    systemInterrupt();      break;
                case 30:    iRet();                 break;
                case 50:    end();                  break;
                default:    solveWrong(instruction);break;
            }
        } catch (IndexOutOfBoundsException exp) {
            throwError("out of index");
        } catch (EmptyStackException stackExp) {
            throwError("Stack is empty");
        } catch (StackOverflowError stackOverExp) {
            throwError("Stack is full");
        }
        //call function
    }
    
    /**
     * interrupt function 
     */
    private void interrupt () {
        //check timme is interupt time
        if (mode == OSCommon.USER_MODE && this.isRunning) {
            timerInterrupt();
        }
    }
    
    /**
     * timer interrupt function 
     */
    private void timerInterrupt() {
        if (this.counter == this.interruptTime) {
            //change to system mode
            mode = OSCommon.TIMER_MODE;
            //stored SP
            int temp = spRegister;
            //turn sp to point system stack
            spRegister = 0;
            pushValue(temp);
            //stored PC
            pushValue(pcRegister);
            pcRegister = 0;
            //stored AC, X, Y
            pushValue(acRegister);
            pushValue(xRegister);
            pushValue(yRegister);
            counter = 0;
        } else {
            counter ++;
        }
    }
    
    
    /**
     * turn back from system mode to user mode 
     */
    private int readFromMemoryByPC () {
        int address = getAddressInMemoryViaPC();
        pcRegister++;
        return memory.read(address);
    }
    
    /**
     * get value from memory via PC
     * @return value
     */
    private int getAddressInMemoryViaPC() {
        int address;
        switch(mode) {
            case OSCommon.USER_MODE: 
                address = pcRegister;
                break;
            case OSCommon.TIMER_MODE: 
                address = OSCommon.TOP_TIMER + pcRegister; 
                break;
            default: 
                address = OSCommon.TOP_SYSTEM + pcRegister; 
                break;
        }
        return address;
    }
    
    /**
     * push value to stack
     *
     * @param value is pushed
     */
    private void pushValue(int value) {
        if (isMemoryFull()) {
            throw new StackOverflowError();
        } else {
            int address;
            if (mode == OSCommon.USER_MODE) {
                address = OSCommon.BOTTOM_USER - spRegister;
            } else {
                address = OSCommon.BOTTOM_SYSTEM - spRegister;
            }
            spRegister++;
            memory.write(address, value);
        }
    }
    
    /**
     * check memory is full or not
     * return true if memory is full
     */
    private boolean isMemoryFull() {
        boolean ret = false;
        int size = pcRegister + spRegister;
        if (mode == OSCommon.USER_MODE && size > 119) ret = true;
        if (mode == OSCommon.SYSTEM_MODE && size > 499) ret = true;
        if (mode == OSCommon.SYSTEM_MODE && spRegister > 499) ret = true;
        return ret;
    }
    /**
     * pop value from stack
     * @return value
     */
    private int popValueFromStack () {
        int address;
        if (spRegister == 0) {
            throw new EmptyStackException();
        }
        spRegister--;
        if (mode == OSCommon.USER_MODE) {
           address =  OSCommon.BOTTOM_USER - spRegister;
        } else {
           address =  OSCommon.BOTTOM_SYSTEM - spRegister;
        }
        
        return memory.read(address);
    }
    /**
     * throw error to user
     * @param error is thrown
     */
    private void throwError (String error) {
        memory.clear();
        int address = 0;
        char[] errorCharacters = error.toCharArray();
        for (char character : errorCharacters) {
            //load character to ac
            memory.write(address++, 1);
            memory.write(address++, (int) character);
            //print character
            memory.write(address++, 9);
            memory.write(address++, 2);
        }
        memory.write(address++, 2);
        pcRegister = 0;
        mode = OSCommon.USER_MODE;
        
    }
    /*************Function of CPU **************/
    /**
     * load value to AC
     */
    private void loadValue () {
        try {
            int value = readFromMemoryByPC();
            acRegister = value;
        } catch (IndexOutOfBoundsException exp) {
            throwError("error in command id 1: " + OSCommon.INDEX_OUT_OF_RANGE);
        }
    }
    
    /**
     * load value of address to AC
     */
    private void loadAddr () {
        try {
            int address = readFromMemoryByPC();
            if (mode == OSCommon.USER_MODE && address > OSCommon.BOTTOM_USER) 
                throwError("error in command id 2: " + OSCommon.INVALID_ACCESS_MESSAGE);
            else acRegister = memory.read(address);
        } catch (IndexOutOfBoundsException exp) {
            throwError("error in command id 2: " + OSCommon.INDEX_OUT_OF_RANGE);
        }
    }
    
    /**
     * load value of address to AC
     */
    private void loadIndAddrr () {
        try {
            int address = readFromMemoryByPC();
            if (mode == OSCommon.USER_MODE && address > OSCommon.BOTTOM_USER) 
                throwError("error in command id 2: " + OSCommon.INVALID_ACCESS_MESSAGE);
            else {
                int innerAddress = memory.read(address);
                if (mode == OSCommon.USER_MODE && innerAddress > OSCommon.BOTTOM_USER) 
                    throwError("error in command id 2: " + OSCommon.INVALID_ACCESS_MESSAGE);
                acRegister = memory.read(innerAddress);
            }
        } catch (IndexOutOfBoundsException exp) {
            throwError("error in command id 3: " + OSCommon.INDEX_OUT_OF_RANGE);
        }
    }
    
    /**
     * load value of address to AC
     */
    private void loadIdXAddrr () {
        try {
            int address = readFromMemoryByPC();
            address = address + xRegister;
            if (mode == OSCommon.USER_MODE && address > OSCommon.BOTTOM_USER) 
                throwError("error in command id 2: " + OSCommon.INVALID_ACCESS_MESSAGE);
            else acRegister = memory.read(address);
        } catch (IndexOutOfBoundsException exp) {
            throwError("error in command id 4: " + OSCommon.INDEX_OUT_OF_RANGE);
        }
    }
    
    /**
     * load value of address to AC
     */
    private void loadIdYAddrr () {
        try {
            int address = readFromMemoryByPC();
            address = address + yRegister;
            if (mode == OSCommon.USER_MODE && address > OSCommon.BOTTOM_USER) 
                throwError("error in command id 2: " + OSCommon.INVALID_ACCESS_MESSAGE);
            else acRegister = memory.read(address);
        } catch (IndexOutOfBoundsException exp) {
            throwError("error in command id 5: " + OSCommon.INDEX_OUT_OF_RANGE);
        }
    }
    
    /**
     * load value of address to AC
     */
    private void loadSpXAddrr () {
        try {
            int address = spRegister + xRegister;
            if (mode == OSCommon.USER_MODE && address > OSCommon.BOTTOM_USER) 
                throwError("error in command id 2: " + OSCommon.INVALID_ACCESS_MESSAGE);
            else acRegister = memory.read(address);
        } catch (IndexOutOfBoundsException exp) {
            throwError("error in command id 6: " + OSCommon.INDEX_OUT_OF_RANGE);
        }
    }
    
    /**
     * store value of ac to address
     */
    private void storeAddrr () {
        try {
            int address = readFromMemoryByPC();
            if (mode == OSCommon.USER_MODE && address > OSCommon.BOTTOM_USER) 
                throwError("error in command id 2: " + OSCommon.INVALID_ACCESS_MESSAGE);
            else memory.write(address, acRegister);
        } catch (IndexOutOfBoundsException exp) {
            throwError("error in command id 7: " + OSCommon.INDEX_OUT_OF_RANGE);
        }
    }
    
    /**
     * get random to AC
     */
    private void get () {
        Random random = new Random();
        acRegister = random.nextInt(10);
    }
    
    /**
     * port: 1 print integer, 2 print char
     */
    private void putPort () {
        try {
            int  port = readFromMemoryByPC();
            if (port == 1) {
                System.out.println(acRegister);
            } else {
                System.out.print((char) acRegister);
            }
        } catch (IndexOutOfBoundsException exp) {
            throwError("error in command id 9: " + OSCommon.INDEX_OUT_OF_RANGE);
        }
    }
    
    /**
     * Add value X to AC
     */
    private void addX () {
        acRegister = acRegister + xRegister;
    }
    
    /**
     * Add value Y to AC
     */
    private void addY () {
        acRegister = acRegister + yRegister;
    }
    
    /**
     * sub value X from AC
     */
    private void subX () {
        acRegister = acRegister - xRegister;
    }
    
    /**
     * sub value Y from AC
     */
    private void subY () {
        acRegister = acRegister - yRegister;
    }
    
    /**
     * copy to X from AC
     */
    private void copyToX () {
        xRegister = acRegister;
    }
    
    /**
     * copy to Y from AC
     */
    private void copyToY () {
        yRegister = acRegister;
    }
    
    /**
     * copy to AC from X
     */
    private void copyFromX () {
        acRegister = xRegister;
    }
    
    /**
     * copy to AC from Y
     */
    private void copyFromY () {
        acRegister = yRegister;
    }
    
    /**
     * copy to SP from AC
     */
    private void copyToSP () {
        spRegister = acRegister;
    }
    
    /**
     * copy to AC from SP
     */
    private void copyFromSP () {
        acRegister = spRegister;
    }
    
    /**
     * jump to addrr
     */
    private void jumpAddrr () {
        try {
            int  address = readFromMemoryByPC();
            pcRegister = address;
        } catch (IndexOutOfBoundsException exp) {
            throwError("error in command id 20: " + OSCommon.INDEX_OUT_OF_RANGE);
        }
    }
    
    /**
     * jump if ac = 0
     */
    private void jumpIfEqual () {
        try {
            int  address = readFromMemoryByPC();
            if (acRegister == 0) pcRegister = address;
        } catch (IndexOutOfBoundsException exp) {
            throwError("error in command id 21: " + OSCommon.INDEX_OUT_OF_RANGE);
        }
    }
    
    /**
     * jump if ac != 0
     */
    private void jumpIfNotEqual () {
        try {
            int  address = readFromMemoryByPC();
            if (acRegister != 0) pcRegister = address;
        } catch (IndexOutOfBoundsException exp) {
            throwError("error in command id 22: " + OSCommon.INDEX_OUT_OF_RANGE);
        }
    }
    
    /**
     * push return address to stack, jump to address
     */
    private void call () {
        int  address = readFromMemoryByPC();
        pushValue(pcRegister);
        pcRegister = address;
    }
    
    /**
     * Pop return address from the stack, jump to the address
     */
    private void ret () {
        int address = popValueFromStack();
        pcRegister = address;
    }
    
    /**
     * increment X
     */
    private void incX () {
        xRegister ++ ;
    }
    /**
     * decrement X
     */
    private void decX () {
        xRegister --;
    }
    
    /**
     * push AC to Stack
     */
    private void push () {
        pushValue(acRegister);
        
    }
    
    /**
     * pop to AC from Stack
     */
    private void pop () {
        acRegister = popValueFromStack();
    }
    
    /**
     * system interrupt function 
     */
    private void systemInterrupt() {
        //change to system mode
        mode = OSCommon.SYSTEM_MODE;
        //stored SP
        int temp = spRegister;
        //turn sp to point system stack
        spRegister = 0;
        pushValue(temp);
        //stored PC
        pushValue(pcRegister);
        pcRegister = 0;
        //stored AC, X, Y
        pushValue(acRegister);
        pushValue(xRegister);
        pushValue(yRegister);
    }
    
    /**
     * return to user mode
     */
    private void iRet() {
        if (mode == OSCommon.USER_MODE) {
            throwError("Can not call IRet when use rmode");
        } else {
            spRegister = 5;
            //restored AC, X, Y
            yRegister = popValueFromStack();
            xRegister = popValueFromStack();
            acRegister = popValueFromStack();
            //restored PC
            pcRegister = popValueFromStack();
            //restored SP
            int temp = popValueFromStack();
            spRegister = temp;
            mode = OSCommon.USER_MODE;
        }
    }
    
    /**
     * end application
     */
    private void end() {
        isRunning = false;
    }
    /**
     * solve wrong instruction
     * @param instruction is wrong
     */
    private void solveWrong(int instruction) {
        if (instruction == OSCommon.EMPTY_INSTRUCTION_SET && mode == OSCommon.USER_MODE) {
            end();
        } else if (instruction == OSCommon.EMPTY_INSTRUCTION_SET) {
            iRet();
        }
    }
}
