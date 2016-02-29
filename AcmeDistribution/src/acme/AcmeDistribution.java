package acme;


import BasicIO.ASCIIDataFile;
import BasicIO.ASCIIOutputFile;
import Forms.CheckedForm;
import Forms.CheckedForm.Type;
import Media.UnableToLoadFileException;

import java.util.Date;
import java.util.NoSuchElementException;

import static javax.swing.JOptionPane.*;

/**
 * COSC 2P05 Assignment #2 Part B
 * Reference:
 *     1. BasicIO: From Brock package, used to read and write file
 *     2. Media: From Brock package, used to get UnableToLoadFileException
 *     3. Forms: From Assignment #2 Part A, used to make Form
 *     4. java.util: From Java Library, used to get Date Type and NoSuchElementException
 *     5. javax.swing.JOptionPane: From Javax Swing, used to prompt alert message.
 * Created by Xuhao Chen on 16/2/28.
 */

public class AcmeDistribution {

    public static void main(String[] args){new AcmeDistribution();}
    private CheckedForm form;

    private OrderQueue queue;
    private OrderQueue totalOrder = new OrderQueue();

    public AcmeDistribution(){
        form = new CheckedForm();

        buildForm();
        while (true) {
            int button=-2;
            try{
                queue = new OrderQueue(new ASCIIDataFile());   //make a new OrderQueue from ASCIIDataFile
                if(!queue.isRead()) throw new UnableToLoadFileException("UnableToLoadFileException: Unable to read File - invalid type.");
            }catch(NullPointerException e){              // user press cancel when opening an ASCIIDataFile, save and quit
                System.err.println("NullPointerException: User press cancel when reading file");
                break;
            }catch (UnableToLoadFileException e2){       // the data type read from ASCIIDataFile is invalid, re-open a file
                System.err.println(e2.getMessage());
                continue;
            }catch(IllegalStateException e3){            //order queue is full, save and quit
                System.err.println("IllegalStateException: The order Queue is full, please save and exit.");
                break;
            }

            Order order=null;
            while(true){
                try{
                    if(button != -1){
                        if(queue.isEmpty()) break;
                        order = queue.pollOrder();
                        fillForm(order);
                    }
                }catch(NoSuchElementException e){
                    System.err.println("NoSuchElementException: Unexpected Exception - The Order Queue is empty unintentionally, please save and quit");
                    button=1;
                    break;
                }catch(Exception e2){
                    System.err.println("Unexpected Exception, please save and quit");
                    button=1;
                    break;
                }


                button = form.accept();
                if (button==-1) continue;  //if form input is invalid
                if (button==1) break;  //when user press Quit

                //if form input is valid
                try{
                    order.date = (Date) form.readField("date");
                    order.time = (Date) form.readField("time");
                    order.orderNum = (String) form.readField("order");
                    order.itemNum = (String) form.readField("item");
                    order.quantity = (Number) form.readField("quantity");

                    totalOrder.addOrder(order);
                }catch(Exception e){
                    System.err.println("Unexpected Exception, please save and quit!");
                    button=1;
                    break;
                }

            }
            if (button==-2) continue; //when queue is empty, which means the Type read from file is invalid
            if(button==1) break;  //when user press Quit, or program quit due to Unexpected exception appears

        }

        int count=0;
        form.hide();
        while(true){

            try {
                writeResult();   //save queue to file
                break;
            }catch(NullPointerException e){   //user press cancel when saving file
                int confirm = showConfirmDialog(null, "Are you sure want to close without saving?","Close without saving",YES_NO_OPTION);  //Prompt a confirm dialog
                if(confirm==1) continue;  //  if user choose No, let user to save the file again
                else break;
            }catch(Exception e2){   //Other Unexpected Exception Appears when saving file
                count++;
                if(count<=5){
                    System.err.println("Unexpected Exception, Please re-save file.");
                    showMessageDialog(null,"Unexpected Exception Appears when saving file, Please re-save file.");
                    continue;
                }else{
                    System.err.println("Unexpected Exception: Fail to save file more than 5 times.");
                    int fail = showConfirmDialog(null,"Sorry, fail to save file. Program cannot resolve this problem. Press Ok to force quit, cancel to retry","Fail to save",OK_CANCEL_OPTION);
                    if(fail==1) continue;
                    else break;
                }

            }
        }

        form.close();
        System.exit(-1);

    }

    /**
     * saving total orders from queue to disk
     */
    private void writeResult(){
        ASCIIOutputFile output = new ASCIIOutputFile();
        while(!totalOrder.isEmpty()){
            Order order = totalOrder.pollOrder();
            output.writeString(order.getDateString());
            output.writeString(order.getTimeString());
            output.writeString(order.getOrderNum());
            output.writeString(order.getItemNum());
            output.writeString(order.getQuantityString());
            output.newLine();

        }
    }

    /**
     * fill the form with the specific Order
     * @param order used to fill form
     */
    private void fillForm(Order order){
        form.writeField("date", order.getDateString());
        form.writeField("time", order.getTimeString());
        form.writeField("order", order.getOrderNum());
        form.writeField("item", order.getItemNum());
        form.writeField("quantity", order.getQuantityString());
    }

    private void buildForm(){
        form.addField("date","Date", Type.DATE);
        form.addField("time","Time",Type.TIME);
        form.addField("order","Order #",Type.STRING);
        form.addField("item","Item #",Type.STRING);
        form.addField("quantity","Quantity",Type.INTEGER);
        form.addText("address","Address");
    }
}
