package Forms;

import BasicIO.BasicForm;

import java.text.Format;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

/**
 * COSC 2P05 Assignment #2 Part A
 * Reference:
 *     1. java.text.*: From Java Library, used to get each format and ParseException
 *     2. java.util.*: From Java Library, used to create ArrayList and set Locale
 *     3. BasicIO.*: From Brock package, used to create BasicForm
 * Created by Xuhao Chen on 16/2/25.
 */

public class CheckedForm extends BasicForm{

    private BasicForm basic;
    private ArrayList<Field> fieldList = new ArrayList<Field>();   //record name and Type pairs
    private ArrayList<String> textList = new ArrayList<String>();   //record name of textList

    private int numField=1;
    private int line=0;

    private Boolean nameFound;
    private Boolean typeChecked;

    public CheckedForm(){
        basic = new BasicForm("OK","Quit");
        Locale.setDefault(Locale.CANADA);   //set Default Locale to CANADA
    }    //constructor

    public enum Type {

        DATE(new SimpleDateFormat("MM/dd/yy")),
        TIME(new SimpleDateFormat("h:mm a")),
        CURRENCY(NumberFormat.getCurrencyInstance()),
        DECIMAL(NumberFormat.getInstance()),
        INTEGER(NumberFormat.getIntegerInstance()),
        PERCENT(NumberFormat.getPercentInstance()),
        STRING(null),  //no need to check String Type
        TEXT(null);    //no need to check Text Area

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

    @Override
    public void show(){
        basic.show();
    }

    @Override
    public void hide(){
        basic.hide();
    }

    /**
     * Override accept()
     * @return -1 when at least 1 field is invalid,
     *          0 when all field is valid,
     *          1 when quit pressed.
     */
    @Override
    public int accept(){
        int button = basic.accept();
        if(button == 1) return 1;
        else{
            if(checkValidation()) {
                return button;
            }else return -1;
        }
    }

    @Override
    public void close(){
        basic.close();
    }

    @Override
    public void clearAll(){
        basic.clearAll();
    }

    @Override
    public void writeString(String name, String text){
        basic.writeString(name,text);
    }

    public void writeField(String name,String text){
        basic.writeString(name+"Input",text);
    }

    public void writeArea(String name,String text){
        basic.writeString(name,text);
    }

    public void showMessage(String name,String message){
        basic.clear(name);
        basic.writeString(name,message);
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
                addEmptyField(name,label, Type.DATE);
                break;
            case TIME:
                addEmptyField(name,label, Type.TIME);
                break;
            case CURRENCY:
                addEmptyField(name,label, Type.CURRENCY);
                break;
            case DECIMAL:
                addEmptyField(name,label, Type.DECIMAL);
                break;
            case INTEGER:
                addEmptyField(name,label, Type.INTEGER);
                break;
            case PERCENT:
                addEmptyField(name,label, Type.PERCENT);
                break;
            case STRING:
                addEmptyField(name,label, Type.STRING);
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
        if(checkExists(name)==null){
            basic.addTextArea(name,label,5,60,10,10+70*line);
            textList.add(name);
            line += 6;
            if(numField%2!=0) numField++;
        }else{
            throw new RuntimeException("Text Area Name: "+name+" has exist.");
        }

    }

    public Object readField(String name) throws RuntimeException{
        Type type=checkExists(name);
        Boolean nameFound=true;
        if(type==null) nameFound=false;
        if(!nameFound) throw new RuntimeException("Name "+name+" not found.");
        String data = basic.readString(name+"Input");
        if(checkFormat(name,type)){
            try{
                switch(type){
                    case DATE:case TIME:
                        return ((SimpleDateFormat)type.getFormat()).parse(data);
                    case INTEGER:case CURRENCY:case DECIMAL:case PERCENT:
                        return ((NumberFormat)type.getFormat()).parse(data);
                    case STRING:
                        return data;
                    default:
                        throw new RuntimeException("Type "+type.toString()+" does not defined");
                }
            }catch(ParseException e){
                System.err.println(e.getMessage());
            }
        }
        return null;
    }

    public String readText(String name){
        if(checkValidation()){
            if(checkExists(name)== Type.TEXT){
                String result="";
                while(true){
                    Character c = basic.readC(name);
                    if(basic.isEOF()) break;
                    result += c;

                }
                basic.clear(name);
                return result;
            }
        }

        return null;
    }

    /**
     * Get the Type of specific TextField or TextArea
     * @param name: name of TextField or TextArea
     * @return Type if exist, else null
     */
    public Type getType(String name){
        return checkExists(name);
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
    public boolean checkValidation(){
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

    /**
     * Check whether the name is in the form
     * @param name: field name
     * @return field Type if exists, else return null
     */
    private Type checkExists(String name){
        nameFound = null;
        typeChecked = null; //initialization

        for(Field tmp:fieldList){
            if(name.equals(tmp.getName())) return tmp.getType();
        }
        for(String tmp:textList){
            if(name.equals(tmp)) return Type.TEXT;
        }
        return null;
    }


    /**
     * Check whether the name and type pair exists in the basic form
     * @param name: name of field
     * @param type: Type of field
     * @return true if exists and pair correctly,else false
     */
    private boolean checkExists(String name,Type type) {    //this will set bool to nameFound, and typeChecked
        nameFound = null;
        typeChecked = null;   //initialization
        for (Field tmp : fieldList) {
            if (tmp.getName().equalsIgnoreCase(name)) {
                nameFound = true;
                if (tmp.getType().equals(type)) {
                    typeChecked = true;
                    return true;
                }
            }
        }

        if(nameFound == null) nameFound=false;
        if(typeChecked == null) typeChecked=false;
        return false;
    }

    /**
     * add an empty text field to basic form
     * @param name: field name
     * @param label: field label
     * @param type: field type
     */
    private void addEmptyField(String name,String label,Type type){
        if(checkExists(name)==null){
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
        }else throw new RuntimeException("Field Name: "+name+" has exists.");

    }
}
