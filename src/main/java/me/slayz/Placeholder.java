package me.slayz;

import lombok.Getter;

@Getter
public class Placeholder {

    private String key;
    private String value;

    public Placeholder(String key, String value){
        this.key = key;
        this.value = value;
    }

    public String apply(String string){
        return string.replace(key, value);
    }

    public static String apply(String string, Placeholder... placeholders){
        for(Placeholder placeholder: placeholders){
            string = placeholder.apply(string);
        }
        return string;
    }

}
