package form;

import BasicIO.BasicForm;

/**
 * Created by START_Eric on 16/2/25.
 */
public class CheckedForm extends BasicForm{
    private BasicForm basic;
    public CheckedForm(){
        basic = new BasicForm("OK","Quit");
    }
    public CheckedForm(String...buttons){
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

    public void addField(String name, String label, Type kind){
        switch(kind){
            case DATE:
                addDateField(name,label);
                break;
            case TIME:
                addTimeField(name,label);
                break;
            case CURRENCY:
                addCurrencyField(name,label);
                break;
            case DECIMAL:
                addDecimalField(name,label);
                break;
            case INTEGER:
                addIntegerField(name,label);
                break;
            case PERCENT:
                addPercentField(name,label);
                break;
            case STRING:
                addStringField(name,label);
                break;
        }
    }

    private int numField=0;

    private void addDateField(String name,String label){
        basic.addTextField(name,label,20,10,10);

    }
    private void addTimeField(String name,String label){
        basic.addTextField(name,label,20,240,10);

    }
    private void addCurrencyField(String name,String label){
        basic.addTextField(name,label,20,10,70);

    }
    private void addDecimalField(String name,String label){
//        basic.addTextField(name,label,size,x,y);

    }
    private void addIntegerField(String name,String label){
//        basic.addTextField(name,label,size,x,y);

    }
    private void addPercentField(String name,String label){
//        basic.addTextField(name,label,size,x,y);

    }
    private void addStringField(String name,String label){
//        basic.addTextField(name,label,size,x,y);

    }
}
