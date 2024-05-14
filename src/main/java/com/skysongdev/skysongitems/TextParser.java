package com.skysongdev.skysongitems;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.NamespacedKey;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


import static net.md_5.bungee.api.ChatColor.COLOR_CHAR;

public class TextParser {
    private static final Map.Entry<Integer, String> PHYSCOLOR = new AbstractMap.SimpleEntry<>(0, ChatColor.of("GRAY").toString());
    private static final Map.Entry<Integer, String> FUNCCOLOR = new AbstractMap.SimpleEntry<>(1, ChatColor.of("#424242").toString());
    private static final Map.Entry<Integer, String> SKILLCOLOR = new AbstractMap.SimpleEntry<>(2, ChatColor.of("#81a871").toString());

    private static final Map<Integer, String> COLOR_MAP = Map.ofEntries(PHYSCOLOR, FUNCCOLOR, SKILLCOLOR);


    private static final Map.Entry<Integer, String> PREPHYSCOLOR = new AbstractMap.SimpleEntry<>(0, "&7");
    private static final Map.Entry<Integer, String> PREFUNCCOLOR = new AbstractMap.SimpleEntry<>(1, "&#424242");
    private static final Map.Entry<Integer, String> PRESKILLCOLOR = new AbstractMap.SimpleEntry<>(2, "&#81A871");

    private static final Map<Integer, String> PRE_COLOR_MAP = Map.ofEntries(PREPHYSCOLOR, PREFUNCCOLOR, PRESKILLCOLOR);

    public static NamespacedKey deletedKey = new NamespacedKey(Main.getPlugin(), "deleted_lines");
    public static List<String> parseText(List<String> inputlore, String deletedLines) {
        List<String> parsedList = new ArrayList<String>();
        int index = 0;
        String buffer = "";
        StringBuilder interpreter = new StringBuilder(deletedLines);
        parsedList.add(COLOR_MAP.get(0) + "[Not Set]");
        parsedList.add(COLOR_MAP.get(1) + "[Not Set]");
        parsedList.add(COLOR_MAP.get(2) + "[Not Set]");
        if(inputlore == null){
            return parsedList;
        }
        if(interpreter.charAt(0) == '1'){
            index++;
            if(interpreter.charAt(1) == '1'){
                index++;
                if(interpreter.charAt(2) == '1'){
                    return parsedList;
                }
            }
        }
        for(int i = 0; i < inputlore.size(); i++){
            if(Objects.equals(inputlore.get(i), ChatColor.of("#190898").toString())){
                if(buffer.isEmpty())
                    buffer = COLOR_MAP.get(i) + "[Not Set]\n";
                parsedList.set(index, buffer.substring(0, buffer.length()-1));
                index++;
                if(index < 2 && interpreter.charAt(index) == '1')
                    index++;
                buffer = "";
                continue;
            }
            if(interpreter.charAt(index) == '1'){
                continue;
            }
            buffer = buffer.concat(inputlore.get(i) + "\n");
        }
        if(interpreter.charAt(index) == '0'){
            parsedList.set(index, buffer.substring(0, buffer.length()-1));
        }

        return parsedList;
    }

    public static List<String> parseText(List<String> inputlore) {
        List<String> parsedList = new ArrayList<String>();
        int index = 0;
        String buffer = "";

        parsedList.add(COLOR_MAP.get(0) + "[Not Set]");
        parsedList.add(COLOR_MAP.get(1) + "[Not Set]");
        parsedList.add(COLOR_MAP.get(2) + "[Not Set]");
        if(inputlore == null){
            return parsedList;
        }

        for(int i = 0; i < inputlore.size(); i++){
            if(Objects.equals(inputlore.get(i), ChatColor.of("#190898").toString())){
                if(buffer.equals(""))
                    buffer = COLOR_MAP.get(i) + "[Not Set]";
                parsedList.set(index, buffer.substring(0, buffer.length()-1));
                buffer = "";
                index++;
                continue;
            }
            buffer = buffer.concat(inputlore.get(i) + "\n");
        }
        parsedList.set(index, buffer.substring(0, buffer.length()-1));



        return parsedList;
    }

    public static List<String> translateToLore(List<String> inputParsedText, String deletedLines) {
        List<String> translatedLore = new ArrayList<String>();
        List<String> buffer;
        int index = 0;
        StringBuilder interpreter = new StringBuilder(deletedLines);

        for (int i = 0; i < inputParsedText.size(); i++) {
            if(interpreter.charAt(i) == '0'){
                buffer = Arrays.asList(inputParsedText.get(i).split("\n"));
                for (String s : buffer) {
                    translatedLore.add(ChatColor.translateAlternateColorCodes('&', s));
                }
                translatedLore.add(ChatColor.of("#190898").toString());
            }
        }
        if(!translatedLore.isEmpty()){
            translatedLore.remove(translatedLore.size() - 1);
        }

        return translatedLore;
    }

    public static List<String> translateToLore(List<String> inputParsedText) {
        List<String> translatedLore = new ArrayList<String>();
        List<String> buffer;
        for (int i = 0; i < inputParsedText.size(); i++) {
            buffer = Arrays.asList(inputParsedText.get(i).split("\n"));
            for (int j = 0; j < buffer.size(); j++) {
                buffer.set(j, TextParser.translateHexColorCodes("&#", "", buffer.get(j))) ;
                translatedLore.add(ChatColor.translateAlternateColorCodes('&', buffer.get(j)));
            }
            translatedLore.add(ChatColor.of("#190898").toString());
        }
        translatedLore.remove(translatedLore.size() - 1);

        return translatedLore;
    }

    public static List<String> manipulateSection(List<String> startingList, String[] inputLore, int blockIndex, boolean amIReplacing){
        String buffer = "";
        int wordCount = 0;
        String colorBuffer = null;
        final Pattern clearPattern = Pattern.compile("&-");

        for(String word : inputLore){
            if(clearPattern.matcher(word).find()){
                word = word.replaceAll("&-", PRE_COLOR_MAP.get(blockIndex));
            }
            if(CheckForColor(word) != null){
                colorBuffer = CheckForColor(word);
                colorBuffer = translateHexColorCodes("&#", "", colorBuffer);
                colorBuffer = ChatColor.translateAlternateColorCodes('&', colorBuffer);
            }
            String hexedWord = TextParser.translateHexColorCodes("&#", "", word);
            String wordcopy = hexedWord;
            if((wordCount + ChatColor.stripColor(wordcopy).length() + 1) > 45 || buffer.isEmpty()){
                buffer = buffer.concat("\n" + COLOR_MAP.get(blockIndex));
                if(colorBuffer != null){
                    buffer = buffer.concat(colorBuffer);
                }
                wordCount = 0;
            }
            else{
                buffer = buffer.concat(" ");
                wordCount++;
            }
            buffer = buffer.concat(ChatColor.translateAlternateColorCodes('&', hexedWord));
            wordCount += ChatColor.stripColor(wordcopy).length();
        }
        if(amIReplacing) {
            startingList.set(blockIndex, COLOR_MAP.get(blockIndex) + buffer.substring(1));
        } else {
            startingList.set(blockIndex, startingList.get(blockIndex).concat(buffer));
        }

        return startingList;
    }

    public static List<String> manipulateSectionWithConcat(List<String> startingList, String[] inputLore, int blockIndex){
        List<String> test = Arrays.asList(startingList.get(blockIndex).split("\n"));
        String tempword;
        String buffer = test.get(test.size()-1);
        String colorBuffer = null;
        final Pattern clearPattern = Pattern.compile("&-");

        List<String> newInput = new ArrayList<String>();
        boolean isBufferFull = false;
        for(String word : inputLore){
            if(clearPattern.matcher(word).find()) {
                word = word.replaceAll("&-", PRE_COLOR_MAP.get(blockIndex));
            }
            tempword = TextParser.translateHexColorCodes("&#", "", word);
            tempword = ChatColor.translateAlternateColorCodes('&', word);
            String strippedBuffer = ChatColor.stripColor(buffer);
            String strippedWord = ChatColor.stripColor(tempword);
            if(strippedBuffer.length() + strippedWord.length() + 1 <= 45 && !isBufferFull) {
                if(CheckForColor(word) != null){
                    colorBuffer = CheckForColor(word);
                }
                word = TextParser.translateHexColorCodes("&#", "", word);
                word = ChatColor.translateAlternateColorCodes('&', word);
                buffer = buffer.concat(" " + word);
            }
            else{
                isBufferFull = true;
                newInput.add(word);
            }
                
        }
        test.set(test.size()-1, buffer);
        startingList.set(blockIndex, String.join("\n", test));

        if(colorBuffer != null && !newInput.isEmpty()){
            newInput.set(0, colorBuffer + newInput.get(0));
        }

        String[] arrayToSend = new String[newInput.size()];
        arrayToSend = newInput.toArray(arrayToSend);

        return manipulateSection(startingList, arrayToSend, blockIndex, false);
    }

    public static String translateHexColorCodes(String startTag, String endTag, String message)
    {
        final Pattern hexPattern = Pattern.compile(startTag + "([A-Fa-f0-9]{6})" + endTag);
        Matcher matcher = hexPattern.matcher(message);
        StringBuilder buffer = new StringBuilder(message.length() + 4 * 8);
        while (matcher.find())
        {
            String group = matcher.group(1);
            if(Objects.equals(group, "190898")){
                group = "190899";
            }
            matcher.appendReplacement(buffer, COLOR_CHAR + "x"
                    + COLOR_CHAR + group.charAt(0) + COLOR_CHAR + group.charAt(1)
                    + COLOR_CHAR + group.charAt(2) + COLOR_CHAR + group.charAt(3)
                    + COLOR_CHAR + group.charAt(4) + COLOR_CHAR + group.charAt(5)
            );
        }
        return matcher.appendTail(buffer).toString();
    }

    public static List<String> deleteLine(int blockIndex, int lineToDelete, List<String> inputLore){
        String[] buffer = inputLore.get(blockIndex).split("\n");
        String[] toBuffer = new String[buffer.length-1];
        if(lineToDelete < 1 || lineToDelete > buffer.length){
            return inputLore;
        }

        for(int i = 0; i < buffer.length-1 ;i++){
            if(i >= lineToDelete-1){
                toBuffer[i] = buffer[i+1];
            } else{
                toBuffer[i] = buffer[i];
            }
        }

        inputLore.set(blockIndex, String.join("\n", toBuffer));

        return inputLore;
    }

    public static int countValidCharacters(String input){
        String buffer = input.replaceAll("&#" + "([A-Fa-f0-9]{6})", "$1");
        ChatColor.stripColor(buffer);
        int count = input.length();

        return count;
    }

    public static String CheckForColor(String word) {
        final Pattern colorPattern = Pattern.compile("(&#([A-Fa-f0-9]{6})|&([A-Fa-fK-Rk-r0-9]))(&([K-Rk-r]))?");

        if (colorPattern.matcher(word).find()) {
            Matcher matcher = colorPattern.matcher(word);
            String group = null;
            while (matcher.find()) {
                group = matcher.group();
            }
            return group;
        }
        return null;
    }

}
