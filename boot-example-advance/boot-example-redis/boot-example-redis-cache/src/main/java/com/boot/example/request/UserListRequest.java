package com.boot.example.request;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

/**
 * com.boot.example.request.UserListRequest
 *
 * @author lipeng
 * @date 2021/6/9 4:38 PM
 */
@Data
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class UserListRequest {

    private Integer pageIndex;

    private Integer pageSize;
}
