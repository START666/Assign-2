package acme;

import Forms.CheckedForm;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Reference:
 *     1.java.text: From Java Library, used to get Date Format
 *     2.java.util: From Java Library, used to get Date Object
 * Created by Xuhao Chen on 16/2/28.
 */

public class Order {
    public Date date;
    public Date time;
    public String orderNum;
    public String itemNum;
    public Number quantity;
    public String address;

    public Order(Date date,Date time,String orderNum,String itemNum,Number quantity,String address){
        this.date = date;
        this.time = time;
        this.orderNum = orderNum;
        this.itemNum = itemNum;
        this.quantity = quantity;
        this.address = address;
    }

    /**
     * All methods below will return a String in Format of each Type
     */

    public String getDateString(){
        return ((SimpleDateFormat) CheckedForm.Type.DATE.getFormat()).format(date);
    }

    public String getTimeString(){
        return ((SimpleDateFormat) CheckedForm.Type.TIME.getFormat()).format(time);
    }

    public String getOrderNum(){
        return orderNum;
    }

    public String getItemNum(){
        return itemNum;
    }

    public String getQuantityString(){
        return quantity.toString();
    }

    public String getAddress(){
        return address;
    }


}
