package restapi.tut.ruleengine;

/**
 * Created by root on 10/2/16.
 */
public class DemoRule implements Rule{

    public boolean validate() throws Exception {
        System.out.print(getName());
        return true;
    }

    public String getName() {
        return "DemoRule";
    }
}
