package form;

import BasicIO.BasicForm;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * This is a CheckedForm which would be in Forms library package
 * Created by START_Eric on 16/2/25.
 */
public class CheckedForm extends BasicForm{

    private BasicForm basic;
    private ArrayList<Field> fieldList = new ArrayList<Field>();   //record name and Type pairs

    public CheckedForm(){
        basic = new BasicForm("OK","Quit");
    }

    @Override
    public int accept(){
        int button = basic.accept();
        if(button == 1) return 1;
        else{
            if(checkValidation()) {
                basic.clearAll();
                return button;
            }else return -1;
        }
    }

    @Override
    public void close(){
        basic.close();
    }

    private boolean checkValidation(){
        for (Field tmp : fieldList) {
            String name = tmp.getName();
            Type type = tmp.getType();
            switch (type) {
                case DATE:
                    if (!checkDateField(name)) {
                        showInvalid(name);
                        return false;
                    }
                    clearOutput(name);
                    return true;
                default:
                    break;
            }
        }


        return false;
    }

    private void showInvalid(String name){
        basic.writeString(name+"Output","Bad Format, re-enter");
    }
    private void clearOutput(String name){
        basic.setLabel(name+"Output","");
    }

    private boolean checkDateField(String name){
        boolean nameFound = false;
        boolean typeChecked = false;
        boolean valid;
        String data;
        for(Field tmp : fieldList) {
            if (tmp.getName().equalsIgnoreCase(name)) {
                nameFound = true;
                if (tmp.getType().equals(Type.DATE)) {
                    typeChecked = true;
                    break;
                }
            }
        }
        if(!nameFound) throw new RuntimeException("Cannot find name "+name+" in the form");
        else if(!typeChecked) throw new RuntimeException("The field named: "+name+" does not match Type.DATE");
        else{
            data = basic.readString(name+"Input");
            DateFormat format = new SimpleDateFormat("dd/MM/yy");
            try{
                format.parse(data);
                valid = true;
            }catch(ParseException e){
                valid = false;
                System.out.println("The input of "+name+" does not match date format dd/MM/yy");
            }
        }


        return valid;
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
            default:
                break;
        }
    }

    public void addText(String name,String label){
        basic.addTextArea(name,label,5,60,10,10+70*line);
        line += 6;
        if(numField%2!=0) numField++;
    }

    private int numField=1;
    private int line=0;

    private class Field{
        private Type type;
        private String name;
        public Field(String name, Type type){
            this.name = name;
            this.type = type;
        }
        public Type getType(){return type;}
        public String getName(){return name;}
    }


    private void addEmptyField(String name,String label){
        int x;
        if(numField%2==0) x=260;
        else {
            x=10;
            line++;
        }
        basic.addTextField(name+"Input",label,20,x,10+70*(line-1));
        basic.addLabel(name+"Output",x,10+70*(line-1)+30);
        numField++;
    }

    private void addDateField(String name,String label){
        addEmptyField(name,label);
        fieldList.add(new Field(name,Type.DATE));
    }
    private void addTimeField(String name,String label){
        addEmptyField(name,label);
        fieldList.add(new Field(name,Type.TIME));
    }
    private void addCurrencyField(String name,String label){
        addEmptyField(name,label);
        fieldList.add(new Field(name,Type.CURRENCY));
    }
    private void addDecimalField(String name,String label){
        addEmptyField(name,label);
        fieldList.add(new Field(name,Type.DECIMAL));
    }
    private void addIntegerField(String name,String label){
        addEmptyField(name,label);
        fieldList.add(new Field(name,Type.INTEGER));
    }
    private void addPercentField(String name,String label){
        addEmptyField(name,label);
        fieldList.add(new Field(name,Type.PERCENT));
    }
    private void addStringField(String name,String label){
        addEmptyField(name,label);
        fieldList.add(new Field(name,Type.STRING));
    }


}
