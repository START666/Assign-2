package form;

import BasicIO.BasicForm;

import java.text.Format;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

/**
 * This is a CheckedForm which would be in Forms library package
 * Created by START_Eric on 16/2/25.
 */
public class CheckedForm extends BasicForm{

    private final static Locale LOCALE = Locale.CANADA;   //set Locale to Canada

    public CheckedForm(){
        basic = new BasicForm("OK","Quit");
    }    //constructor

    public enum Type {

        DATE(new SimpleDateFormat("dd/MM/yy", LOCALE)),
        TIME(new SimpleDateFormat("h:mm a", LOCALE)),
        CURRENCY(NumberFormat.getCurrencyInstance(LOCALE)),
        DECIMAL(NumberFormat.getInstance(LOCALE)),
        INTEGER(NumberFormat.getIntegerInstance(LOCALE)),
        PERCENT(NumberFormat.getPercentInstance(LOCALE)),
        STRING(null);  //no need to check String Type

        private Format format;

        Type (Format format){
            this.format = format;
        }

        /**
         *
         * @return Format Object of specific Type
         */
        public Object getFormat(){
            return format;
        }
    }

    /**
     * Field class is used to pair name and type
     */
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

    private BasicForm basic;
    private ArrayList<Field> fieldList = new ArrayList<Field>();   //record name and Type pairs

    /**
     * numField and line are used to find the location of the field
     */
    private int numField=1;
    private int line=0;

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

    /**
     * Add a Text Field to the basic form
     * @param name: name of the Text Field
     * @param label: label shown on the Text Field
     * @param kind: input type of the Text Field
     */
    public void addField(String name, String label, Type kind){
        switch(kind){
            case DATE:
                addEmptyField(name,label,Type.DATE);
                break;
            case TIME:
                addEmptyField(name,label,Type.TIME);
                break;
            case CURRENCY:
                addEmptyField(name,label,Type.CURRENCY);
                break;
            case DECIMAL:
                addEmptyField(name,label,Type.DECIMAL);
                break;
            case INTEGER:
                addEmptyField(name,label,Type.INTEGER);
                break;
            case PERCENT:
                addEmptyField(name,label,Type.PERCENT);
                break;
            case STRING:
                addEmptyField(name,label,Type.STRING);
                break;
            default:
                break;
        }
    }

    /**
     * Add a Text Area to basic form
     * @param name: name of the Text Area
     * @param label: label shown on the Text Area
     */
    public void addText(String name,String label){
        basic.addTextArea(name,label,5,60,10,10+70*line);
        line += 6;
        if(numField%2!=0) numField++;
    }


    /**
     * Check the input format's validation
     * @param name: specific field name
     * @param type: the type of the field should be
     * @return true if the name and type pairs exists and the input is valid
     * @throws RuntimeException: When input name does not exists, or name does not match type
     */
    private boolean checkFormat(String name,Type type) throws RuntimeException{
        boolean valid;
        boolean exists = checkExists(name, type);
        String data;

        if(!exists){
            if (!nameFound) throw new RuntimeException("Cannot find name " + name + " in the form");
            else if (!typeChecked) throw new RuntimeException("The field named: " + name + " does not match Type."+type.toString());
            else throw new RuntimeException("Unknown: exists == false, but name and type match");
        }else{
            data = basic.readString(name+"Input");
            Object format = type.getFormat();
            try{
                switch(type){
                    case DATE:case TIME:
                        ((SimpleDateFormat)format).parse(data);
                        break;
                    case INTEGER:case CURRENCY:case DECIMAL:case PERCENT:
                        ((NumberFormat)format).parse(data);
                        break;
                    case STRING:
                        break;
                    default:
                        throw new RuntimeException("Type "+type.toString()+" does not defined");
                }
                valid=true;
            }catch(ParseException e){
                valid=false;
                System.out.println("The input of "+name+" does not match "+type.toString()+" format");
            }catch(RuntimeException e2){
                valid=false;
                System.err.println(e2.getMessage());
            }
        }
        if(!valid){
            showInvalid(name);
        }else{
            clearOutput(name);
        }

        return valid;
    }

    /**
     *
     * @return true if all fields are valid
     */
    private boolean checkValidation(){
        ArrayList<Boolean> validation = new ArrayList<Boolean>();
        boolean valid;
        for (Field tmp : fieldList) {
            String name = tmp.getName();
            Type type = tmp.getType();
            valid = checkFormat(name,type);
            validation.add(valid);
        }
        for(Boolean v:validation){
            if(!v) return false;
        }
        return true;

    }

    /**
     * Show invalid message in specific output label
     * @param name: name of an output label
     */
    private void showInvalid(String name){
        basic.writeString(name+"Output","Bad Format, re-enter");
    }

    /**
     * Clear all text of a specific output label
     * @param name: name of an output label
     */
    private void clearOutput(String name){
        basic.setLabel(name+"Output","");
    }

    private boolean nameFound;
    private boolean typeChecked;

    private boolean checkExists(String name,Type type) {    //this will set bool to nameFound, and typeChecked
        nameFound = false;
        typeChecked = false;   //initialization
        for (Field tmp : fieldList) {
            if (tmp.getName().equalsIgnoreCase(name)) {
                nameFound = true;
                if (tmp.getType().equals(type)) {
                    typeChecked = true;
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * add an empty text field to basic form
     * @param name: field name
     * @param label: field label
     * @param type: field type
     */
    private void addEmptyField(String name,String label,Type type){
        int x;
        if(numField%2==0) x=260;
        else {
            x=10;
            line++;
        }
        basic.addTextField(name+"Input",label,20,x,10+70*(line-1));
        basic.addLabel(name+"Output","                                 ",x,10+70*(line-1)+30);
        numField++;
        fieldList.add(new Field(name,type));
    }
}
