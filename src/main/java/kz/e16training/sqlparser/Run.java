package kz.e16training.sqlparser;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author DK
 * @version 1.0.0
 *
 */
public class Run {
    static Logger log = LogManager.getLogger(Run.class.getName());
    public static void main(String[] args) {
        log.debug("program started");
        SQLParse parse = new SQLParse("izvorite_smf-complete_2008-09-20.sql", "smf_members", "memberName", "passwd", "emailAddress");
        parse.parsing();
        log.debug("program ended");
    }
}
