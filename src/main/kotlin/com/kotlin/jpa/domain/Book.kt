package com.kotlin.jpa.domain

import javax.persistence.DiscriminatorValue
import javax.persistence.Entity

@Entity
@DiscriminatorValue("BOOK")
class Book(author: String) : Item() {
    private var author: String = author;
}