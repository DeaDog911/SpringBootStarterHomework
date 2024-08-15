package org.deadog.springbootstarterhomework.model.dto;

import lombok.Data;

@Data
public class OrderDTO {
    private Long id;
    private String description;
    private String status;
    private Long userId;
}