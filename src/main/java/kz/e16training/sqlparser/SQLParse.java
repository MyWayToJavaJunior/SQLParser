package kz.e16training.sqlparser;


import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Parse of SQL file.
 *
 */
public class SQLParse {
    private User user;
    private String fileName;
    private String tableName;
    private String firstField;
    private String secondField;
    private String thirdField;
    private int indexFirstField;
    private int indexSecondField;
    private int indexThirdField;
    private String textEncoding;

    public SQLParse(String fileName, String tableName, String firstField, String secondField, String thirdField) {
        this.fileName = fileName;
        this.tableName = tableName;
        this.firstField = firstField;
        this.secondField = secondField;
        this.thirdField = thirdField;
        this.user = new User();
        this.textEncoding = "UTF8";
    }

    public void parsing() {
        String textFromFile = getTextFromFile();
        String tableBlock = getBlockByRegex("CREATE TABLE" + ".{0,5}" + tableName + ".*?" + "CREATE TABLE", textFromFile);
        String titleBlock = getBlockByRegex("INSERT INTO" + ".*?" + "VALUES", tableBlock);
        titleBlock = getBlockByRegex("\\(`.*?`\\)", titleBlock);
        List<String> titles = getBlockListByRegex("`(\\w+)`", titleBlock);
        indexFirstField = getIndex(firstField, titles);
        indexSecondField = getIndex(secondField, titles);
        indexThirdField = getIndex(thirdField, titles);
        String usersBlock = getBlockByRegex("VALUES"  + ".*?" + "CREATE TABLE", tableBlock);
        System.out.println(usersBlock);
        getAllUsers(usersBlock);
        System.out.println(titles);
    }

    private void getAllUsers(String userBlock) {
        Pattern pBlock = Pattern.compile("\\(`.*?`\\)");
        Matcher mBlock = pBlock.matcher(userBlock);
        List<String> lines;
        while (mBlock.find()) {
            System.out.println(mBlock.group());
            /*lines = getBlockListByRegex("`(\\w*)`", mBlock.group());
            System.out.println(lines);
            user.addUser(new User(lines.get(indexFirstField), lines.get(indexSecondField), lines.get(indexThirdField)));*/
        }
    }

    private int getIndex(String text, List<String> titles) {
        int result = 0;
        for (int i = 0; i < titles.size(); i++) {
            if (titles.get(i).equals(text)) result = i;
        }
        return result;
    }

    private List<String> getBlockListByRegex(String regex, String textForParse) {
        List<String> result = new ArrayList<>();
        Pattern pBlock = Pattern.compile(regex);
        Matcher mBlock = pBlock.matcher(textForParse);
        while (mBlock.find()) {
            result.add(mBlock.group(1));
        }
        return result;
    }

    private String getTextFromFile() {
        StringBuilder result = new StringBuilder(1024);
        try  (BufferedReader reader = new BufferedReader(
                new InputStreamReader(
                        new FileInputStream(fileName), textEncoding))) {
            String line;
            while ((line = reader.readLine()) != null) {
                result.append(line);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result.toString();
    }

    private String getBlockByRegex(String regex, String textForParse) {
        String result = "";
        Pattern pBlock = Pattern.compile(regex);
        Matcher mBlock = pBlock.matcher(textForParse);
        if (mBlock.find()) result = mBlock.group();
        return result;
    }
}
