package com.capgemini.fooddelivery.service;

import com.capgemini.fooddelivery.entity.Delivery;
import com.capgemini.fooddelivery.entity.Shipping;
import com.capgemini.fooddelivery.repository.DeliveryRepository;
import com.capgemini.fooddelivery.repository.ShippingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShippingService {

    @Autowired
    private ShippingRepository shippingRepository;

    @Autowired
    private DeliveryRepository deliveryRepository;

    public String createShipping(Long deliveryId, Shipping shipping) {
        Delivery delivery = deliveryRepository.findById(deliveryId).orElse(null);
        if (delivery == null) {
            return "ERROR: Delivery not found with id " + deliveryId;
        }
        if (shippingRepository.findByDeliveryId(deliveryId).isPresent()) {
            return "ERROR: Shipping already exists for this delivery";
        }
        shipping.setDelivery(delivery);
        shippingRepository.save(shipping);
        return "Shipping details added successfully";
    }

    public List<Shipping> getAllShippings() {
        return shippingRepository.findAll();
    }

    public Shipping getShippingByDeliveryId(Long deliveryId) {
        return shippingRepository.findByDeliveryId(deliveryId).orElse(null);
    }
}
