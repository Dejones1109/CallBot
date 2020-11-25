package com.its.sanve.api.communication.anvui;

import com.its.sanve.api.entities.Point;
import com.its.sanve.api.repositories.PointRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Log4j2
@Component
public class AnVuiClient {
    @Autowired
    AnVuiCommunication anVuiCommunication;
    @Autowired
    PointRepository listPointRepository;


    public Object getRoute(String pointUp, String pointDown) {
        try {
            int status = 0;
//
            List<Point> listPointsUp = listPointRepository.searchPoint(pointUp);
            List<Point> listPointsDown = listPointRepository.searchPoint(pointDown);
            if (listPointsUp.isEmpty() || listPointsUp.isEmpty()) {
                status = 0;
            }else {
              for(int i=0;i<listPointsUp.size();i++){
                  for(int j=  )
              }



            }


        } catch (Exception e) {
            log.info("error:", e.getMessage());
            return null;
        }
        return null;
    }

    private Boolean isCheckPoint(String point) {
        if (!listPointRepository.searchPoint(point).isEmpty()) {
            return true;
        } else {
            return false;
        }

    }
}
