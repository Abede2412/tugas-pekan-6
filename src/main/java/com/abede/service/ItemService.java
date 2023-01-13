package com.abede.service;

import com.abede.model.Item;
import io.vertx.core.json.JsonObject;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Map;

@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@ApplicationScoped
public class ItemService {

    public Item setItem(JsonObject request,Item item){
        String name = request.getString("name");
        Integer count = request.getInteger("count");
        Double price = request.getDouble("price");
        String type = request.getString("type");
        String desc = request.getString("description");

        item.setName(name);
        item.setCount(count);
        item.setPrice(price);
        item.setType(type);
        item.setDescription(desc);

        return item;
    }

    public boolean isValid(Item item) {
        return item.getName() != null && item.getCount() != null && item.getPrice() != null;
    }

    @Transactional
    public Response createService(JsonObject request){

        Item item = new Item();
        item = setItem(request, item);
        if (isValid(item)){
            item.persist();
            return Response.ok().entity(Map.of("message","sukses create "+request)).build();
        }
        return Response.status(Response.Status.BAD_REQUEST)
                .entity(Map.of("message","name, count, atau price Tidak boleh null"))
                .build();
    }

    public Response getService(){
        return Response.ok().entity(Item.listAll()).build();
    }

    public Response getByIdService(Integer id){
        Item item = Item.findById(id);
        if(item == null){
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(Map.of("message", "Item not found"))
                    .build();
        }
        return Response.ok().entity(item).build();
    }
    @Transactional
    public Response updateService(Integer id, JsonObject request){
        Item item = Item.findById(id);
        if(item == null){
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(Map.of("message", "Item not found"))
                    .build();
        }
        item = setItem(request, item);
        if (isValid(item)){
            item.persist();
            return Response.ok()
                    .entity(item)
                    .build();
        }
        return Response.status(Response.Status.BAD_REQUEST)
                .entity(Map.of("message","name, count, atau price Tidak boleh null"))
                .build();
    }

    public Response deleteService(Integer id){
        Item item = Item.findById(id);
        if(item == null){
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(Map.of("message", "Item not found"))
                    .build();
        }
        item.delete();
        return Response.ok().entity(Map.of("message", "item "+id+" deleted")).build();
    }
}
