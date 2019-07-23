package controllers;

import databaseAccess.ArtifactsDAO;
import databaseAccess.BackpacksDAO;
import databaseAccess.CodecoolersDAO;
import services.CodecoolerService;

import com.sun.net.httpserver.HttpExchange;
import sessionData.SessionHandler;

public class CodecoolerController {
    private CodecoolerService codecoolerService = new CodecoolerService(new CodecoolersDAO(), new ArtifactsDAO(), new BackpacksDAO());


    public String getIndexPage(int accountId) {
        return codecoolerService.getIndexPageRender(accountId);
    }

    public String getIndexPage(HttpExchange httpExchange) {
        return codecoolerService.getIndexPageRender(httpExchange);
    }

    private String getArtifactStorePage() {
        return codecoolerService.getArtifactStorePageRender();
    }

    private String getBackpackPage(HttpExchange httpExchange) {
        return codecoolerService.getBackpackPageRender(httpExchange);
    }

    public String getCodecoolerResponse(HttpExchange httpExchange, String userPageRequest) {
        String response = "";

        switch (userPageRequest) {
            case "index":
                response = getIndexPage(httpExchange);
                break;

            case "artifactStore":
                response = getArtifactStorePage();
                break;

            case "backpack":
                response = getBackpackPage(httpExchange);
                break;

            case "logout":
                SessionHandler.getInstance().removeActiveSessionWithCookie(httpExchange);
                response = new LoginController().getLoginPage();
                break;

            case "buy":
                codecoolerService.buyArtifact(httpExchange);
                response = getArtifactStorePage();
                break;

        }
        return response;
    }
}
