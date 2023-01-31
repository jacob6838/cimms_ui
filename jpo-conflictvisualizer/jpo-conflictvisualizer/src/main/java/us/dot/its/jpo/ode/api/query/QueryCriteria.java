package us.dot.its.jpo.ode.api.query;

public abstract class QueryCriteria<T> {
    
    public T value;
    public String field;

    public QueryCriteria(String field, T value ){
        this.value = value;
        this.field = field;
    }

    public abstract String getAsMongoQuery();

}
