package com.f2d.event_planner.feign;

import com.f2d.event_planner.domain.AppConstants;
import com.f2d.event_planner.domain.F2DGroupSearchResponse;
import com.f2d.event_planner.domain.UriConstants;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

@FeignClient(name = AppConstants.F2D_GROUP_SERVICE, url = UriConstants.LOCALHOST_URL)
public interface F2DGroupServiceFeignClient {

    @GetMapping(UriConstants.F2D_GROUP_SERVICE_RETRIEVE_BY_ID_RELATIVE_PATH)
    ResponseEntity<F2DGroupSearchResponse> retrieveGroupById(@PathVariable(AppConstants.PATHVARIABLE_GROUP_ID) UUID groupId);
}
