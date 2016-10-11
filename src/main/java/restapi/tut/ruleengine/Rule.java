package restapi.tut.ruleengine;

/**
 * Created by root on 10/2/16.
 */
public interface Rule {
    boolean validate() throws Exception;
    String getName();
}
