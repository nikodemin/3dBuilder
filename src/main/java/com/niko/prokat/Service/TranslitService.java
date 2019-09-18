package com.niko.prokat.Service;

public class TranslitService {
    private static String[][] tripleSyms = {
            {"Щ", "JSH"}
    };
    private static String[][] doubleSyms = {
            {"Ё", "JE"},
            {"Ж", "ZH"},
            {"Х", "KH"},
            {"Ч", "CH"},
            {"Ш", "SH"},
            {"Ъ", "HH"},
            {"Ы", "IH"},
            {"Ь", "JH"},
            {"Э", "EH"},
            {"Ю", "JU"},
            {"Я", "JA"},

    };
    private static String[][] singleSyms = {
            {"А", "A"},
            {"В", "B"},
            {"Б", "V"},
            {"Г", "G"},
            {"Д", "D"},
            {"Е", "E"},
            {"З", "Z"},
            {"И", "I"},
            {"Й", "Y"},
            {"К", "K"},
            {"Л", "L"},
            {"М", "M"},
            {"Н", "N"},
            {"О", "O"},
            {"П", "P"},
            {"Р", "R"},
            {"С", "S"},
            {"Т", "T"},
            {"У", "U"},
            {"Ф", "F"},
            {"Ц", "C"},

    };

    public static String toEng(String str) {
        str = str.toUpperCase();
        for (String[] sym : singleSyms) {
            str = str.replaceAll(sym[0], sym[1]);
        }

        for (String[] sym : doubleSyms) {
            str = str.replaceAll(sym[0], sym[1]);
        }

        for (String[] sym : tripleSyms) {
            str = str.replaceAll(sym[0], sym[1]);
        }
        return str.toLowerCase();
    }

    public static String toRus(String str) {
        str = str.toUpperCase();
        for (String[] sym : tripleSyms) {
            str = str.replaceAll(sym[1], sym[0]);
        }

        for (String[] sym : doubleSyms) {
            str = str.replaceAll(sym[1], sym[0]);
        }

        for (String[] sym : singleSyms) {
            str = str.replaceAll(sym[1], sym[0]);
        }
        return str.toLowerCase();
    }
}






















