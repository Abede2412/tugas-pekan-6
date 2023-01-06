package com.abede.controller;

import com.abede.model.Item;
import io.vertx.core.json.JsonObject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.Map;

@Path("/items")
public class ItemController {

    @POST
    @Transactional
    public Response create(JsonObject request){
        Item item = new Item();
        item.name = request.getString("name");
        item.count = request.getInteger("count");
        item.price= request.getInteger("price");
        item.description = request.getString("description");
        item.type = request.getString("type");

        item.persist();

        return Response.ok().entity(new HashMap<>()).build();
    }

    @GET
    public Response list(){

        return Response.ok().entity(Item.listAll()).build();
    }

    @GET
    @Path("/{id}")
    public Response getById(@PathParam("id") Integer id){
        Item item = Item.findById(id);
        if(item == null){
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(Map.of("message", "Item not found"))
                    .build();
        }

        return Response.ok().entity(item).build();
    }

    @PUT
    @Path("/{id}")
    @Transactional
    public Response update(@PathParam("id") Integer id, JsonObject request){
        Item item = Item.findById(id);
        item.name = request.getString("name");
        item.count = request.getInteger("count");
        item.price= request.getInteger("price");
        item.description = request.getString("description");
        item.type = request.getString("type");

        item.persist();

        return Response.ok().entity(new HashMap<>()).build();
    }


    @DELETE
    @Path("/{id}")
    @Transactional
    public Response delete(@PathParam("id") Integer id){
        Item.deleteById(id);
        return Response.ok().entity(new HashMap<>()).build();
    }
}
