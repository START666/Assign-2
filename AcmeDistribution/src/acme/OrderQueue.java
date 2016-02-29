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

    private BlockingQueue<Order> queue;
    private final Integer capacity = 100000;

    private Date date;
    private Date time;
    private String orderNum;
    private String itemNum;
    private Number quantity;

    private boolean fileRead;

    public OrderQueue(){
        queue = new ArrayBlockingQueue<Order>(capacity);
    }

    public OrderQueue(ASCIIDataFile input){
        this.input = input;
        queue = new ArrayBlockingQueue<Order>(capacity);
        fileRead = readOrder();
    }

    public boolean isRead(){
        return fileRead;
    }

    private boolean readOrder(){
        try{
            while (true) {
                String tmp = input.readString();
                if(input.isEOF()) break;
                date = makeDate(tmp);
                time = makeTime(input.readString());
                orderNum = makeString(input.readString());
                itemNum = makeString(input.readString());
                quantity = makeInteger(input.readString());

                Order order = new Order(date,time,orderNum,itemNum,quantity);
                queue.add(order);
            }
            return true;
        }catch(ParseException e){
            System.err.println(e.getMessage());
            return false;
        }catch(IllegalStateException e2){
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
