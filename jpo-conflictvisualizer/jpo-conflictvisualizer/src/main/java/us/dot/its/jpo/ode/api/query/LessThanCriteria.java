package us.dot.its.jpo.ode.api.query;

public class LessThanCriteria <T> extends QueryCriteria<T>{
    public LessThanCriteria(String field, T value ){
        super(field, value);
    }

    @Override
    public String getAsMongoQuery(){
        return String.format("'%s': {$lte: %s}", field, value.toString());
    }
}


