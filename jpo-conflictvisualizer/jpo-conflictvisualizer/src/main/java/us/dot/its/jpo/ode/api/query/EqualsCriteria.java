package us.dot.its.jpo.ode.api.query;

public class EqualsCriteria<T> extends QueryCriteria<T>{
    
    public EqualsCriteria(String field, T value ){
        super(field, value);
    }

    @Override
    public String getAsMongoQuery(){
        return String.format("'%s': %s", field, value.toString());
    }
}
