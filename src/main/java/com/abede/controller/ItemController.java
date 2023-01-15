package com.abede.controller;


import com.abede.service.ItemService;
import io.vertx.core.json.JsonObject;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;



@Path("/items")
public class ItemController {

    @Inject
    ItemService itemService;



    @POST
    public Response create(JsonObject request){
        return itemService.createService(request);
    }

    @GET
    public Response list(){
        return itemService.getService();
    }
    @GET
    @Path("/{id}")
    public Response getByID(@PathParam("id") Integer id){
        return itemService.getByIdService(id);
    }

    @PUT
    @Path("/{id}")
    @Transactional
    public Response update(@PathParam("id") Integer id, JsonObject request){
       return itemService.updateService(id,request);
    }
    @DELETE
    @Path("/{id}")
    @Transactional
    public Response delete(@PathParam("id") Integer id){
        return itemService.deleteService(id);
    }
}
