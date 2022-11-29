package com.uni.services;

import com.uni.entities.GameRequest;
import java.util.*;

public interface GameRequestService {

    GameRequest createRequest(GameRequest gameRequest);

    GameRequest deleteRequest(int gameId, int userId);
    List<GameRequest> getAllGamesAndReferees();
}
