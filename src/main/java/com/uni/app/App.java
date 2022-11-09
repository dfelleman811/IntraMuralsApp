package com.uni.app;

import com.uni.controllers.SchedulingController;
import com.uni.controllers.TeamController;
import com.uni.controllers.TeamRequestController;
import com.uni.controllers.UserController;
import com.uni.daos.*;
import com.uni.services.RegistrationService;
import com.uni.services.RegistrationServiceImpl;
import com.uni.services.SchedulingService;
import com.uni.services.SchedulingServiceImpl;
import io.javalin.Javalin;
import java.io.IOException;

public class App {

    public static void main(String[] args) throws IOException {
        Javalin app = Javalin.create(config -> {
            config.enableCorsForAllOrigins();
        });


        //DAOs
        GameDAO gameDAO = GameDAO.getSingleton();
        SeasonDAO seasonDAO = SeasonDAO.getSingleton();
        TeamDAO teamDAO = TeamDAO.getSingleton();
        TeamRequestDAO teamRequestDAO = TeamRequestDAO.getSingleton();
        UserDAO userDAO = UserDAO.getSingleton();
        VenueDAO venueDAO = VenueDAO.getSingleton();

        //Services
        RegistrationService registrationService = new RegistrationServiceImpl(teamDAO,userDAO,teamRequestDAO);
        SchedulingService schedulingService = new SchedulingServiceImpl(venueDAO,gameDAO,seasonDAO);


        //Controllers
        SchedulingController schedulingController = new SchedulingController(schedulingService);
        TeamController teamController = new TeamController(registrationService);
        UserController userController = new UserController(registrationService);
        TeamRequestController teamRequestController = new TeamRequestController(registrationService);


        app.post("/login", userController::login);

        app.post("/teams", teamController::registerTeam);
        app.get("/teams",teamController::retrieveAllTeams);

        app.get("/venues", schedulingController::getAllVenues);

        app.post("/games",schedulingController::scheduleGame);
        app.get("/games",schedulingController::getAllGames);

        app.get("/seasons",schedulingController::getAllSeasons);

        app.get("/teamrequests",teamRequestController::getAllTeamRequests);
        app.post("/teamrequests",teamRequestController::createTeamRequest);
        app.patch("/teamrequests/{id}/approve",teamRequestController::approveRequest);
        app.patch("/teamrequests/{id}/deny",teamRequestController::denyRequest);


        app.start();

    }
}
