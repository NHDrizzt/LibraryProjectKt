package com.mercadolivro.mercadolivro.events

import com.mercadolivro.mercadolivro.model.PurchaseModel
import org.springframework.context.ApplicationEvent
import org.springframework.context.ApplicationEventPublisher

class PurchaseEvent (
    source: Any,
    val purchaseModel: PurchaseModel
    ): ApplicationEvent(source)