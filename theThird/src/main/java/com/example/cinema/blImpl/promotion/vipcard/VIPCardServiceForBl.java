package com.example.cinema.blImpl.promotion.vipcard;

import com.example.cinema.po.VIPCard;

public interface VIPCardServiceForBl {

	void updateVIPCardByIdAndBanlance(int id, double balance);
	
	VIPCard getVIPCardByUserId(int userId);

}
