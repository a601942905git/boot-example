package com.boot.example.request;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

/**
 * com.boot.example.request.UserListRequest
 *
 * @author lipeng
 * @date 2021/2/4 10:26 AM
 */
@Data
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class UserListRequest {

    public String dynamicDataSourceKey;
}
