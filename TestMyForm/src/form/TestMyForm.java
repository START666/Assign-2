package form;

import Forms.CheckedForm;
import Forms.CheckedForm.Type;
/**
 * This is a test of Assignment #2 Part A of COSC 2P05
 * Created by Xuhao Chen on 16/2/25.
 */

public class TestMyForm {
    public static void main(String[] args){new TestMyForm();}
    public TestMyForm(){

        CheckedForm form = new CheckedForm();
        // build form
        form.addField("date","Date: ", Type.CURRENCY);
        form.addField("time","Time:", Type.TIME);
        form.addField("currency","Currency:", Type.CURRENCY);
        form.addField("decimal","Decimal", Type.DECIMAL);
        form.addField("integer","Integer", Type.INTEGER);
        form.addField("percent","Percent", Type.PERCENT);
        form.addField("string","String", Type.STRING);
        form.addText("text","Text");
        int button;
        while(true){
            button = form.accept();

            if(button==1) break;  //quit
            if(button==-1) continue;  //input invalid
            // read form
            System.out.println("Date: "+form.readField("date"));
            System.out.println("Time: "+form.readField("time"));
            System.out.println("Currency: "+form.readField("currency"));
            System.out.println("Decimal: "+form.readField("decimal"));
            System.out.println("Integer: "+form.readField("integer"));
            System.out.println("Percent: "+form.readField("percent"));
            System.out.println("String: "+form.readField("string"));
            System.out.println("Text: "+form.readText("text"));
            form.clearAll();
        }
        form.close();
        System.exit(-1);

    }
}
