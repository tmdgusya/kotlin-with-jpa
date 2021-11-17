package com.kotlin.jpa.domain

import javax.persistence.*

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "DTYPE")
class Item() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private var id: Long? = null;

    @Column(nullable = false)
    private var name: String? = null;

    @Column(nullable = false)
    private var price: Int? = null;

    constructor(name: String, price: Int) : this() {
        this.name = name;
        this.price = price;
    }
}