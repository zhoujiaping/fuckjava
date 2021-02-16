package cn.sirenia.json;

import cn.sirenia.io.FileUtil;

import java.util.*;

/**
 * todo
 * 1：支持注释，单行注释，多行注释
 * 2：支持单引号字符串
 * 3：支持key无引号
 * 4：支持循环引用
 *
 * 也可以用 有限确定状态机 实现
 * 定义状态，每个状态接收各个字符后的动作，动作中根据上下文（栈，map等数据结构）中的数据更新上下文。
 * 比如
 * context = ...
 * dfa=[
 *  'begin':[
 *      '{':()->{parseObject()},
 *      '[':()->{parseArray()},
 *      ...
 *  ]
 * ]
 */
public class JSONParser {
    static Map<Character,Character> specials = new HashMap<>();
    static {
        specials.put('t','\t');
        specials.put('r','\r');
        specials.put('n','\n');
        specials.put('b','\b');
        specials.put('f','\f');
        specials.put('"','\"');
        specials.put('\\','\\');
        //any else?
    }

    int idx;
    int line;
    int column;
    char[] chs;
    Set<Character> blanks = new HashSet<>();
    Set<Character> numbers = new HashSet<>();

    JSONParser() {
        char[] blackChars = " \b\n\f\r\t".toCharArray();
        for (char c : blackChars) {
            blanks.add(c);
        }
        char[] numberChars = "-0123456789.".toCharArray();
        for (char c : numberChars) {
            numbers.add(c);
        }
    }

    public Object parse(String text) {
        chs = text.toCharArray();
        ignoreBlanks();
        return parseValue();
    }

    public Map<String, Object> parseObject(String text) {
        chs = text.toCharArray();
        ignoreBlanks();
        if (chs[idx] != '{') {
            error("expect {");
        }
        return parseObject();
    }

    public List<Object> parseArray(String text) {
        chs = text.toCharArray();
        ignoreBlanks();
        if (chs[idx] != '[') {
            error("expect [");
        }
        return parseArray();
    }

    private void next() {
        idx++;
        column++;
    }

    private void blankNext() {
        idx++;
        if (chs[idx] == '\n') {
            column = 0;
            line++;
        } else {
            column++;
        }
    }

    private void next(int num) {
        idx += num;
        column += num;
    }

    private <T> T error(String msg) {
        throw new RuntimeException(msg + "(at line " + line + ",column " + column + ")");
    }

    //
    private Map<String, Object> parseObject() {
        next();//pass the char '{'
        Map<String, Object> obj = new HashMap<>();
        ignoreBlanks();
        while (chs[idx] != '}') {
            String key = parseString();
            ignoreBlanks();
            kvSeparator();
            ignoreBlanks();
            Object value = parseValue();
            obj.put(key, value);
            ignoreBlanks();
            valueSeparator();
            ignoreBlanks();
        }
        next();//pass the char '}'
        return obj;
    }

    private boolean valueSeparator() {
        if (chs[idx] == ',') {
            next();
            return true;
        }
        return false;
    }

    private void kvSeparator() {
        if (chs[idx] != ':') {
            error("expect :" + idx);
        }
        next();
    }
    private String parseString() {
        if (chs[idx] != '\"') {
            error("expect \"");
        }
        next();//the key start char '"'
        StringBuilder sb = new StringBuilder();
        while (chs[idx] != '\"') {
            if(chs[idx]=='\\'){
                next();//the char '\'
                Character nextChar = specials.get(chs[idx]);
                sb.append(nextChar);
            }else{
                sb.append(chs[idx]);
            }
            next();
        }
        next();//the key end char '"'
        return sb.toString();
    }

    private void ignoreBlanks() {
        while (blanks.contains(chs[idx])) {
            blankNext();
        }
    }

    private List<Object> parseArray() {
        if (chs[idx] != '[') {
            error("expect {");
        }
        next();//[
        List<Object> array = new ArrayList<>();
        ignoreBlanks();
        while (chs[idx] != ']') {
            array.add(parseValue());
            ignoreBlanks();
            valueSeparator();
            ignoreBlanks();
        }
        next();//]
        //stack.pop();
        return array;
    }

    private Object parseValue() {
        if (chs[idx] == '{') {
            return parseObject();
        } else if (chs[idx] == '[') {
            return parseArray();
        } else if (chs[idx] == 't') {
            return parseTrue();
        } else if (chs[idx] == 'f') {
            return parseFalse();
        } else if (chs[idx] == 'n') {
            return parseNull();
        } else if (chs[idx] == '\"') {
            return parseString();
        } else {//number
            return parseNumber();
        }
    }

    private Object parseNumber() {
        int begin = idx;
        boolean isFloat = false;
        while (numbers.contains(chs[idx])) {
            if (chs[idx] == '.') {
                isFloat = true;
            }
            next();
        }
        if (isFloat) {
            return Double.parseDouble(new String(chs, begin, idx - begin));
        } else {
            return Long.parseLong(new String(chs, begin, idx - begin));
        }
    }

    private Object parseNull() {
        if (idx + 4 >= chs.length || !new String(chs, idx, 4).equals("null")) {
            error("illegal value");
        }
        next(4);
        return null;
    }

    private Object parseFalse() {
        if (idx + 5 >= chs.length || !new String(chs, idx, 5).equals("false")) {
            error("illegal value");
        }
        next(5);
        return false;
    }

    private Object parseTrue() {
        if (idx + 4 >= chs.length || !new String(chs, idx, 4).equals("true")) {
            error("illegal value");
        }
        next(4);
        return true;
    }

    public static void main(String[] args) {
        JSONParser jsonParser = new JSONParser();
        try {
            Object res = jsonParser.parse(FileUtil.readText("d:/test.json"));
            System.out.println(res);
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(jsonParser.line + ":" + jsonParser.column);
        }
    }
}
