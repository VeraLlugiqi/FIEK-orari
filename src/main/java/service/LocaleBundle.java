package service;


import java.util.Locale;
import java.util.ResourceBundle;

public class LocaleBundle {


    public static ResourceBundle bundle(String type) {
        Locale currentLocale = Locale.getDefault();
        Locale locale = new Locale(type);
        ResourceBundle resources = ResourceBundle.getBundle("translations/content" ,locale);
        return resources;
    }

}
