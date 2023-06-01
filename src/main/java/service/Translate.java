package service;

        import javafx.beans.property.ObjectProperty;
        import javafx.beans.property.SimpleObjectProperty;
        import java.text.MessageFormat;
        import java.util.ArrayList;
        import java.util.Arrays;
        import java.util.List;
        import java.util.Locale;
        import java.util.ResourceBundle;

public final class Translate {

    private static final ObjectProperty<Locale> locale;

    static {
        locale = new SimpleObjectProperty<>(getDefaultLocale());
        locale.addListener((observable, oldValue, newValue) -> Locale.setDefault(newValue));
    }

    public static List<Locale> getSupportedLocales() {
        return new ArrayList<>(Arrays.asList(Locale.ENGLISH, new Locale("sq", "AL")));
    }

    public static Locale getDefaultLocale() {
        Locale sysDefault = Locale.getDefault();
        return getSupportedLocales().contains(sysDefault) ? sysDefault : new Locale("sq", "AL");
    }

    public static Locale getLocale() {
        return locale.get();
    }

    public static void setLocale(Locale locale) {
        localeProperty().set(locale);
        Locale.setDefault(locale);
    }

    public static ObjectProperty<Locale> localeProperty() {
        return locale;
    }

    public static String get(final String key, final Object... args) {
        ResourceBundle bundle = ResourceBundle.getBundle("translations/content", getLocale());
        return MessageFormat.format(bundle.getString(key), args);
    }




}