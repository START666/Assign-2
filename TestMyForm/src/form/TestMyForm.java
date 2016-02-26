package form;

/**
 * Created by START_Eric on 16/2/25.
 */
public class TestMyForm {
    public static void main(String[] args){new TestMyForm();}
    public TestMyForm(){

        System.out.println("Hello Git");

        CheckedForm form = new CheckedForm("a","b","Quit");
        form.addField("date","Date: ",Type.DATE);
        form.addField("time","Time:",Type.TIME);
        form.addField("currency","Currency:",Type.CURRENCY);
        int button=-1;
        while(true){
            button = form.accept();
            System.out.println(button);
            if(button==2) break;
        }
        form.close();
        System.exit(-1);

    }
}
