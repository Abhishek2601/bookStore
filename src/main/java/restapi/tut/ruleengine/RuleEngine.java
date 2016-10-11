package restapi.tut.ruleengine;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by root on 10/2/16.
 */


@AllArgsConstructor
@NoArgsConstructor
public class RuleEngine {
    private Map<String, Rule> ruleMap = new HashMap<String, Rule>() ;

    public boolean valiadate() throws Exception{
        for(Map.Entry<String, Rule> ruleEntry: ruleMap.entrySet()){
            ruleEntry.getValue().validate();
        }
        return true;
    }

    public void addRule(Rule rule){
        ruleMap.put(rule.getName(), rule);
    }

}
