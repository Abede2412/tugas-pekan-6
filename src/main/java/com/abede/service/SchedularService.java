package com.abede.service;

import com.abede.model.Item;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.scheduler.Scheduled;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.List;

@ApplicationScoped
public class SchedularService {

    Logger logger = LoggerFactory.getLogger(SchedularService.class);

    @Inject
    EntityManager em;

    @Scheduled(every = "1h")
    @Transactional
    public void deleteItemWithZeroCount (){
        String query = "select * from tugas_pekan_6.item where \"count\" = 0";
        List<Item> itemList = em.createNativeQuery(query, Item.class).getResultList();

        for(Item item : itemList){
            item.delete();
        }
        logger.info("deleted "+itemList.size()+" item");
    }
}
