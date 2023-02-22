
import os

endpoints = [
    # {'name':'ConnectionOfTravelEvent'}
    # {'name':'IntersectionReferenceAlignmentEvent'},
    # {'name':'LaneDirectionOfTravelEvent'},
    # {'name':'SignalGroupAlignmentEvent'},
    # {'name':'SignalStateConflictEvent'},
    # {'name':'SignalStateStopEvent'},
    # {'name':'TimeChangeDetailsEvent'},
    # {'name':'ConnectionOfTravelEvent'},
    {'name':'SignalStateEvent'}
]

repository_file = '''
    package us.dot.its.jpo.ode.api.accessors.events.{name};

    import java.util.List;

    import org.springframework.data.mongodb.core.query.Query;
    import us.dot.its.jpo.conflictmonitor.monitor.models.events.{name};

    public interface {name}Repository{{
        Query getQuery(Integer intersectionID, Long startTime, Long endTime);

        long getQueryResultCount(Query query);
        
        List<{name}> find(Query query);  
    }}

'''

repository_impl_file = '''
    package us.dot.its.jpo.ode.api.accessors.events.{name};

    import java.time.Instant;
    import java.util.List;

    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.data.mongodb.core.MongoTemplate;
    import org.springframework.data.mongodb.core.query.Criteria;
    import org.springframework.data.mongodb.core.query.Query;
    import org.springframework.stereotype.Component;
    import us.dot.its.jpo.conflictmonitor.monitor.models.events.{name};

    @Component
    public class {name}RepositoryImpl implements {name}Repository{{
        
        @Autowired
        private MongoTemplate mongoTemplate;

        public Query getQuery(Integer intersectionID, Long startTime, Long endTime){{
            Query query = new Query();

            if(intersectionID != null){{
                query.addCriteria(Criteria.where("properties.intersectionId").is(intersectionID));
            }}

            String startTimeString = Instant.ofEpochMilli(0).toString();
            String endTimeString = Instant.now().toString();

            if(startTime != null){{
                startTimeString = Instant.ofEpochMilli(startTime).toString(); 
            }}
            if(endTime != null){{
                endTimeString = Instant.ofEpochMilli(endTime).toString();
            }}

            query.addCriteria(Criteria.where("eventGeneratedAt").gte(startTimeString).lte(endTimeString));
            return query;
        }}

        public long getQueryResultCount(Query query){{
            return mongoTemplate.count(query, {name}.class);
        }}

        public List<{name}> find(Query query) {{
            return mongoTemplate.find(query, {name}.class);
        }}

    }}
'''



for endpoint in endpoints:
    name = endpoint.get('name','')

    if not os.path.isdir(name):
        os.mkdir(name)

    with open(f'{name}/{name}Repository.java','w+') as f:
        f.write(repository_file.format(name = name))

    with open(f'{name}/{name}RepositoryImpl.java','w+') as f:
        f.write(repository_impl_file.format(name = name))
    

    

    
