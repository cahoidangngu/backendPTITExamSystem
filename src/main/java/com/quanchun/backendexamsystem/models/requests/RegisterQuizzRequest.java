package com.quanchun.backendexamsystem.models.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterQuizzRequest {
    private List<Long> listUserId;
    private Date beginTime;
    private Date endTime;
}