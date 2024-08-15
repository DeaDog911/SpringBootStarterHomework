package org.deadog.springbootstarterhomework.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.deadog.springbootstarterhomework.model.dto.OrderDTO;
import org.deadog.springbootstarterhomework.exceptions.ApplicationException;
import org.deadog.springbootstarterhomework.model.Order;
import org.deadog.springbootstarterhomework.model.User;
import org.deadog.springbootstarterhomework.repository.OrderRepository;
import org.deadog.springbootstarterhomework.repository.UserRepository;
import org.deadog.springbootstarterhomework.util.OrderMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;

    private final UserRepository userRepository;

    public OrderDTO create(Long userId, OrderDTO orderDTO) {
        Order order = OrderMapper.convertToOrder(orderDTO);
        User user = userRepository.findById(userId).orElse(null);

        if (user == null)
            throw new ApplicationException("User not found");

        order.setUser(user);
        return OrderMapper.convertToOrderDTO(orderRepository.save(order));
    }

    @Transactional
    public List<OrderDTO> getAllOrders() {
        return orderRepository.findAll().stream()
                .map(OrderMapper::convertToOrderDTO)
                .toList();
    }

    public OrderDTO getOrderById(Long id) {
        Order order = orderRepository.findById(id).orElse(null);
        return OrderMapper.convertToOrderDTO(order);
    }

    public OrderDTO updateOrder(Long id, OrderDTO orderDetails) {
        Order order = orderRepository.findById(id).orElse(null);
        if (order != null) {
            order.setDescription(orderDetails.getDescription());
            order.setStatus(orderDetails.getStatus());
            return OrderMapper.convertToOrderDTO(orderRepository.save(order));
        }
        return null;
    }

    public void deleteOrder(Long id) {
        orderRepository.deleteById(id);
    }

}
