package cn.sirenia.text;

import cn.sirenia.text.fmt.GenericTokenParser;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;

public class TextUtil {
    /**
     * 删除xml格式字符串中的注释。
     * 处理xml字符串可以用dom解析，sax解析，pull解析，但是有时候需要用正则处理，需要先去掉注释。
     * */
    public static String removeXmlComment(String xml){
        //正则要使用懒惰模式，不能用默认的贪婪模式。
        return xml.replaceAll("(?s)<!--.*?-->", "");
    }
    /**
     * 删除java代码字符串中的注释。
     * 可以用来在json中支持多行注释。
     * */
    public static String removeJavaComment(String code){
        return code.replaceAll("(?s)/\\*.*?\\*/", "");
    }
    public static String render(String template,String openToken,String closeToken,Object... variables){
        if(variables==null){
            return template;
        }
        Iterator<?> iter = Arrays.asList(variables).iterator();
        return new GenericTokenParser(openToken,closeToken,token->{
            if(iter.hasNext()){
                return String.valueOf(iter.next().toString());
            }else{
                return "";
            }
        }).parse(template);
    }
    public static String renderWithMap(String template, String openToken, String closeToken, Map<String,Object> variables){
        if(variables==null){
            return template;
        }
        Iterator<Map.Entry<String,Object>> iter = variables.entrySet().iterator();
        return new GenericTokenParser(openToken,closeToken,token->{
            if(iter.hasNext()){
                return String.valueOf(iter.next().getValue());
            }else{
                return "";
            }
        }).parse(template);
    }
}
