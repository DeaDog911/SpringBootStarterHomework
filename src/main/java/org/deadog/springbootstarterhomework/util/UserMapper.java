package org.deadog.springbootstarterhomework.util;

import org.deadog.springbootstarterhomework.model.dto.UserDTO;
import org.deadog.springbootstarterhomework.model.User;

import java.util.stream.Collectors;

public class UserMapper {

    public static UserDTO convertToUserDTO(User user) {
        if (user == null)
            return null;

        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setEmail(user.getEmail());
        if (user.getOrders() != null)
            userDTO.setOrders(user.getOrders().stream()
                    .map(OrderMapper::convertToOrderDTO).collect(Collectors.toList()));
        return userDTO;
    }

    public static User convertToUser(UserDTO userDTO) {
        if (userDTO == null)
            return null;

        User user = new User();
        user.setId(userDTO.getId());
        user.setEmail(userDTO.getEmail());
        if (userDTO.getOrders() != null)
            user.setOrders(userDTO.getOrders().stream()
                    .map(OrderMapper::convertToOrder).collect(Collectors.toList()));
        return user;
    }
}
