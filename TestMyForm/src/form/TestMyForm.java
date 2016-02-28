package form;

/**
 * This is a test of Assignment #2 of COSC 2P05
 * Created by START_Eric on 16/2/25.
 */

public class TestMyForm {
    public static void main(String[] args){new TestMyForm();}
    public TestMyForm(){

        CheckedForm form = new CheckedForm();
        form.addField("date","Date: ", CheckedForm.Type.DATE);
        form.addField("time","Time:", CheckedForm.Type.TIME);
        form.addField("currency","Currency:", CheckedForm.Type.CURRENCY);
        form.addField("decimal","Decimal", CheckedForm.Type.DECIMAL);
        form.addField("integer","Integer", CheckedForm.Type.INTEGER);
        form.addField("percent","Percent", CheckedForm.Type.PERCENT);
        form.addField("string","String", CheckedForm.Type.STRING);
        form.addText("text","Text");
        int button;
        while(true){
            button = form.accept();
            System.out.println(button);
            if(button==1) break;
            if(button==-1) continue;
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
