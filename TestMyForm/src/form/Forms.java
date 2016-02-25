package form;

import BasicIO.BasicForm;

/**
 * Created by START_Eric on 16/2/25.
 */
public class Forms extends BasicForm{
    private BasicForm basic;
    public Forms(){
        basic = new BasicForm();
    }
    public Forms(String...buttons){
        basic = new BasicForm(buttons);
    }
    @Override
    public int accept(){
        return basic.accept();
    }
    @Override
    public void close(){
        basic.close();
    }
}
