package us.dot.its.jpo.ode.api.query;

import java.util.ArrayList;

public class QueryBuilder {
    
    private ArrayList<QueryCriteria> criteria = new ArrayList<>();

    public void addCriteria(QueryCriteria crt){
        criteria.add(crt);
    }

    public String getQueryString(){
        String query = "{";

        for(QueryCriteria crt : criteria){
            query += crt.getAsMongoQuery();
            query += ", ";
        }
        query += "}";

        return query;
    }
}
