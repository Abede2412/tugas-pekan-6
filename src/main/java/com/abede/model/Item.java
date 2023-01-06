package com.abede.model;

import javax.persistence.*;

@Entity
@Table(name="item")
public class Item extends AuditItem{

    @Id
    @Column(name="id")
    @SequenceGenerator(
            name = "itemSequence",
            sequenceName = "item_sequence",
            initialValue = 1,
            allocationSize = 1
    )
    @GeneratedValue(generator = "itemSequence", strategy = GenerationType.SEQUENCE)
    public Integer id;

    @Column(name="name")
    public String name;

    @Column(name="count")
    public Integer count;

    @Column(name="price")
    public Integer price;

    @Column(name="type")
    public String type;

    @Column(name="description")
    public String description;

}
