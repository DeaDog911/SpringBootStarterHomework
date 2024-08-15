package org.deadog.springbootstarterhomework.util;

import org.deadog.springbootstarterhomework.model.dto.OrderDTO;
import org.deadog.springbootstarterhomework.model.Order;
import org.deadog.springbootstarterhomework.model.User;

public class OrderMapper {
    public static OrderDTO convertToOrderDTO(Order order) {
        if (order == null)
            return null;

        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setId(order.getId());
        orderDTO.setDescription(order.getDescription());
        orderDTO.setStatus(order.getStatus());
        if (order.getUser() != null)
            orderDTO.setUserId(order.getUser().getId());
        return orderDTO;
    }

    public static Order convertToOrder(OrderDTO orderDTO) {
        if (orderDTO == null)
            return null;

        Order order = new Order();
        order.setId(orderDTO.getId());
        order.setDescription(orderDTO.getDescription());
        order.setStatus(orderDTO.getStatus());

        if (orderDTO.getUserId() != null) {
            User user = new User();
            user.setId(orderDTO.getUserId());
            order.setUser(user);
        }
        return order;
    }
}
