package form;

/**
 * Created by START_Eric on 16/2/25.
 */
public class CheckedForm extends Forms{
    private Forms basic;
    public CheckedForm(){
        basic = new Forms();
    }
    public CheckedForm(String...buttons){
        basic = new Forms(buttons);
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
