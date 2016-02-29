package acme;

import BasicIO.ASCIIDataFile;
import Forms.CheckedForm.Type;

import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * Reference:
 *     1. BasicIO: From Brock package, used to read file
 *     2. Forms.CheckedForm: From Part A, used to get format of Type
 *     3. java.text: From Java Library, used to get each format and ParseException
 *     4. java.util.concurrent: From Java Library, used to get Date Object and create BlockingQueue
 * Created by Xuhao Chen on 16/2/28.
 */

public class OrderQueue {

    private ASCIIDataFile input;

    private BlockingQueue<Order> queue;  //store order of each file
    private final Integer capacity = 100000;  //set maximum capacity of each file

    private boolean fileRead;  //whether file read successfully

    public OrderQueue(){   //create an empty OrderQueue
        queue = new ArrayBlockingQueue<Order>(capacity);
    }

    public OrderQueue(ASCIIDataFile input){   //create an OrderQueue from ASCIIDataFile
        this.input = input;
        queue = new ArrayBlockingQueue<Order>(capacity);
        fileRead = readOrder();
    }

    /**
     * get status of reading file
     * @return true when reading file successfully, false when input is invalid
     */
    public boolean isRead(){
        return fileRead;
    }

    /**
     * read each order from ASCIIDataFile and store them into queue
     * @return true when read successfully, false when input is invalid
     */
    private boolean readOrder(){
        Date date;
        Date time;
        String orderNum;
        String itemNum;
        Number quantity;
        try{
            while (true) {
                String tmp = input.readString();
                if(input.isEOF()) break;
                date = makeDate(tmp);
                time = makeTime(input.readString());
                orderNum = makeString(input.readString());
                itemNum = makeString(input.readString());
                quantity = makeInteger(input.readString());

                queue.add(new Order(date,time,orderNum,itemNum,quantity));
            }
            return true;
        }catch(ParseException e){            //when Format is invalid
            System.err.println(e.getMessage());
            return false;
        }catch(IllegalStateException e2){    //when queue is full
            System.err.println("IllegalStateException: Queue is full");
            throw e2;
        }
    }

    public Order pollOrder(){
        return queue.remove();
    }
    public void addOrder(Order order){
        queue.offer(order);
    }

    public boolean isEmpty(){
        return queue.isEmpty();
    }

    private Date makeDate(String date) throws ParseException {
        try{
            return ((SimpleDateFormat) Type.DATE.getFormat()).parse(date);
        }catch(ParseException e){
            throw new ParseException("ParseException: Fail to make Date: Invalid format",0);
        }

    }

    private Date makeTime(String time) throws ParseException {
        try{
            return ((SimpleDateFormat) Type.TIME.getFormat()).parse(time);
        }catch(ParseException e){
            throw new ParseException("ParseException: Fail to make Time: Invalid format",0);

        }

    }

    private String makeString(String string) throws ParseException {
        if(string !=null) return string;
        else{
            throw new ParseException("ParseException: Fail to make String: input string is null",0);
        }
    }

    private Number makeInteger(String integer) throws ParseException {
        try{
            return ((NumberFormat) Type.INTEGER.getFormat()).parse(integer);
        }catch(ParseException e){
            throw new ParseException("ParseException: Fail to make Integer: Invalid format",0);
        }
    }

}
