package org.jahia.modules.testESCluster.api;

import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.index.query.QueryBuilders;
import org.jahia.modules.elasticsearchconnector.connection.ElasticSearchConnection;
import org.jahia.modules.elasticsearchconnector.connection.ElasticSearchConnectionRegistry;
import org.jahia.modules.elasticsearchconnector.http.ElasticSearchTransportClient;
import org.jahia.osgi.BundleUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/testesc")
@Produces({"application/hal+json"})
public class TestAPI {
    private static final Logger logger = LoggerFactory.getLogger(TestAPI.class);

    @GET
    @Path("/test")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getHello() {
        return Response.status(Response.Status.OK).entity("{\"success\":\"Successfully setup TestDCApi\"}").build();
    }

    @POST
    @Path("/test-connection/{connectionName}/{indexName}/{type}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response testConnection(@PathParam("connectionName") String connectionName,
                                   @PathParam("indexName") String indexName,
                                   @PathParam("type") String type) {

        try {
            ElasticSearchConnectionRegistry elasticSearchConnectionRegistry = BundleUtils.getOsgiService(ElasticSearchConnectionRegistry.class, null);
            ElasticSearchTransportClient esClient = (ElasticSearchTransportClient) elasticSearchConnectionRegistry.getConnectionService(ElasticSearchConnection.DATABASE_TYPE, connectionName);
            SearchRequestBuilder searchRequestBuilder = esClient.prepareSearch();
            searchRequestBuilder.setTypes(type).setIndices(indexName);
            searchRequestBuilder.setQuery(QueryBuilders.matchAllQuery());
            SearchResponse response = searchRequestBuilder.get();
            int numberOfHits  = (int) response.getHits().totalHits;
            return Response.status(Response.Status.OK).entity("{\"success\":\"Got: " + numberOfHits + " hits!\"}").build();
        }
        catch (Exception e) {
            logger.error(e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("{\"success\":\"Things did not go as planned! See console.\"}").build();
        }
    }

}
