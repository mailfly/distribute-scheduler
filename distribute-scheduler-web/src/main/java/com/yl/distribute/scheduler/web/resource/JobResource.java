package com.yl.distribute.scheduler.web.resource;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import javax.enterprise.context.RequestScoped;
import com.yl.distribute.scheduler.common.bean.JobResponse;
import com.yl.distribute.scheduler.web.service.JobServiceImpl;
import javax.ws.rs.core.Response;
/**
 * JAX-RS resource class that provides operations for jobs.
 *
 */
@RequestScoped
@Path("jobs")
public class JobResource {

    @Context
    private UriInfo uriInfo;

    @Inject
    private JobServiceImpl jobService;

    @GET
    @Path("getAllJobs")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllJobs() { 
        return Response.ok().build();
    }
    
    @PUT
    @Path("updateJob")
    public Response updateJob(JobResponse response) {
        jobService.updateJob(response);
        return Response.ok().build();
    }
    
    @GET
    @Path("{jobId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getJobById(@PathParam("jobId") String jobId) {
       return Response.ok(jobService.getJobById(jobId)).build();
    }
    
    @GET
    @Path("getLastFailedServer/{jobId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getLastFailedServer(@PathParam("jobId") String jobId) {
        return Response.ok(jobService.getLastFailedServer(jobId)).build();
    }
    
    @GET
    @Path("getErrorLog/{jobId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getErrorLog(@PathParam("jobId") String jobId) {
        return Response.ok(jobService.getErrorLog(jobId)).build();
    }
    
    @GET
    @Path("getOutputLog/{jobId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getOutputLog(@PathParam("jobId") String jobId) {
        return Response.ok(jobService.getOutputLog(jobId)).build();
    }
}