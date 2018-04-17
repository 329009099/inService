package com.suyin.participator.service;

import java.util.List;
import java.util.Map;

import com.suyin.participator.model.Participator;

public interface ParticipatorService{   
    
    List<Map<String,Object>> findParticipatorList( Map<String,Object> condition);

    public Participator findParticipatorDetailById(int id);
    
    List<Map<String,Object>> findRanKingList( Map<String,Object> condition);
    
    public Participator getPoorVotes(int id);
    
    public Integer getRanking(int id);

}
