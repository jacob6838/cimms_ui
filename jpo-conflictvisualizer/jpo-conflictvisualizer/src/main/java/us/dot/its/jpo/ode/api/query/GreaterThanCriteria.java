package us.dot.its.jpo.ode.api.query;

public class GreaterThanCriteria <T> extends QueryCriteria<T>{
    public GreaterThanCriteria(String field, T value ){
        super(field, value);
    }

    @Override
    public String getAsMongoQuery(){
        return String.format("'%s': {$gte: %s}", field, value.toString());
    }
}


