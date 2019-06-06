package com.example.cinema.controller.management;

import com.example.cinema.bl.management.HallService;
import com.example.cinema.vo.HallForm;
import com.example.cinema.vo.ResponseVO;
import com.example.cinema.vo.TicketForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**影厅管理
 * @author fjj
 * @date 2019/4/12 1:59 PM
 */
@RestController
public class HallController {
    @Autowired
    private HallService hallService;

    @RequestMapping(value = "hall/all", method = RequestMethod.GET)
    public ResponseVO searchAllHall(){
        return hallService.searchAllHall();
    }

    @RequestMapping(value = "hall/add", method = RequestMethod.POST)
    public ResponseVO addHall(@RequestBody HallForm hallForm){
        return hallService.addHall(hallForm);
    }

    @RequestMapping(value = "hall/change", method = RequestMethod.POST)
    public ResponseVO changeHall(@RequestBody HallForm hallForm){

        return hallService.changeHall(hallForm);
    }

    @RequestMapping(value = "hall/delete", method = RequestMethod.DELETE)
    public ResponseVO deleteHall(@RequestBody HallForm hallForm){

        return hallService.deleteHall(hallForm);
    }

}
