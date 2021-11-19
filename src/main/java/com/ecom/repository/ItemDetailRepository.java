package com.ecom.repository;

import com.ecom.entity.ItemDetail;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Repository
public class ItemDetailRepository {
    private List<ItemDetail> items;

    @PostConstruct
    private void init(){
        items = new ArrayList<>();
        items.add(ItemDetail.builder().skuId("A").price(50).build());
        items.add(ItemDetail.builder().skuId("B").price(30).build());
        items.add(ItemDetail.builder().skuId("C").price(20).build());
        items.add(ItemDetail.builder().skuId("D").price(15).build());
    }

    public List<ItemDetail> findAll(){
        return items;
    }
    public ItemDetail findById(String id){
        Optional<ItemDetail> itemDetail = this.findAll().stream().filter(item -> item.getSkuId().equals(id))
                .findFirst();
        if(itemDetail.isPresent()){
            return itemDetail.get();
        }
        return null;
    }

    public ItemDetail add(ItemDetail itemDetail){
        items.add(itemDetail);
        return itemDetail;
    }

}
