package kz.e16training.sqlparser;


import java.io.*;
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
    private String userNameFieldName;
    private String userHashFieldName;
    private String userMailFieldName;
    private String textEncoding;

    public SQLParse(String fileName, String tableName, String userNameFieldName, String userHashFieldName, String userMailFieldName) {
        this.fileName = fileName;
        this.tableName = tableName;
        this.userNameFieldName = userNameFieldName;
        this.userHashFieldName = userHashFieldName;
        this.userMailFieldName = userMailFieldName;
        this.user = new User();
        this.textEncoding = "UTF8";
    }

    public void parsing() {
        String usersBlock = getUserBlock();
        System.out.println(usersBlock);
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

    private String getUserBlock() {
        String result = "";
        //Pattern pUserBlock = Pattern.compile("CREATE TABLE" + "\\.*" + tableName + "\\.*" + "CREATE TABLE");
        Pattern pUserBlock = Pattern.compile("CREATE TABLE" + ".." + tableName);
        String textForParse = getTextFromFile();
        Matcher mUserBlock = pUserBlock.matcher(textForParse);
        if (mUserBlock.find()) result = mUserBlock.group();
        System.out.println(result);
        return result;
    }
}
