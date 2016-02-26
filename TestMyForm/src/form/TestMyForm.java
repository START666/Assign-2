package form;

/**
 * This is a test of Assignment #2 of COSC 2P05
 * Created by START_Eric on 16/2/25.
 */
public class TestMyForm {
    public static void main(String[] args){new TestMyForm();}
    public TestMyForm(){

        System.out.println("Hello Git");

        CheckedForm form = new CheckedForm();
        form.addField("date","Date: ",Type.DATE);
        form.addField("time","Time:",Type.TIME);
        form.addField("currency","Currency:",Type.CURRENCY);
        form.addField("decimal","Decimal",Type.DECIMAL);
        form.addField("integer","Integer",Type.INTEGER);
        form.addField("percent","Percent",Type.PERCENT);
        form.addField("string","String",Type.STRING);
        form.addText("text","Text");
        int button;
        while(true){
            button = form.accept();
            System.out.println(button);
            if(button==1) break;
        }
        form.close();
        System.exit(-1);

    }
}
