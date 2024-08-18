package org.deadog.springbootstarterhomework.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.deadog.springbootstarterhomework.model.dto.OrderDTO;
import org.deadog.springbootstarterhomework.model.dto.UserDTO;
import org.deadog.springbootstarterhomework.model.Order;
import org.deadog.springbootstarterhomework.model.User;
import org.deadog.springbootstarterhomework.repository.OrderRepository;
import org.deadog.springbootstarterhomework.repository.UserRepository;
import org.deadog.springbootstarterhomework.util.OrderMapper;
import org.deadog.springbootstarterhomework.util.UserMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    private final OrderRepository orderRepository;

    public UserDTO createUser(UserDTO userDTO) {
        User user = UserMapper.convertToUser(userDTO);
        return UserMapper.convertToUserDTO(userRepository.save(user));
    }

    public List<UserDTO> getAllUsers() {
        return userRepository.findAll().stream()
                .map(UserMapper::convertToUserDTO)
                .toList();
    }

    public UserDTO getUserById(Long id) {
        User user = userRepository.findById(id).orElse(null);
        return UserMapper.convertToUserDTO(user);
    }

    public UserDTO updateUser(Long id, UserDTO userDetails) {
        User user = userRepository.findById(id).orElse(null);
        if (user != null) {
            user.setName(userDetails.getName());
            user.setEmail(userDetails.getEmail());
            return UserMapper.convertToUserDTO(userRepository.save(user));
        }
        return null;
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

}
