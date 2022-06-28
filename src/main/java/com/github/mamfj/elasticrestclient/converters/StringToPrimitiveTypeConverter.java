package com.github.mamfj.elasticrestclient.converters;

import java.beans.PropertyEditor;
import java.beans.PropertyEditorManager;
import java.util.Date;

public class StringToPrimitiveTypeConverter {
    public static <T> T convert(String valueStr, Class<T> tClass){
        if(tClass == Date.class){
            return (T)StringToDateConverter.toDate(valueStr);
        }else {
            PropertyEditor editor = PropertyEditorManager.findEditor(tClass);
            editor.setAsText(valueStr);
            return (T)editor.getValue();
        }
    }
}
