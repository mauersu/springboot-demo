package com.mauersu.demo.oauth2.server.mapper;


import com.mauersu.demo.oauth2.server.entity.Resource;

import java.util.List;

public interface ResourceMapper {

    List<Resource> listResourceByUserId(Long userId);

}
