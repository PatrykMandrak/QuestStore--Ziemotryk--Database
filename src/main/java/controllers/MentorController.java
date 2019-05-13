package controllers;

import services.FileUploaderService;
import services.MentorService;

import com.sun.net.httpserver.HttpExchange;
import sessionData.SessionHandler;

import java.io.IOException;

public class MentorController {
    private MentorService mentorService = new MentorService();

    public String getIndexPage(HttpExchange httpExchange) {
        return this.mentorService.getIndexPageRender(httpExchange);
    }

    public String getIndexPage(int id) {
        return this.mentorService.getIndexPageRender(id);
    }

    public String getCodecoolerPage() {
        return this.mentorService.getCodecoolerPageRender();
    }

    public String getArtifactPage() {
        return this.mentorService.getArtifactPageRender();
    }

    public String getQuestPage() {
        return this.mentorService.getQuestPageRender();
    }

    public String getEditCodecoolerPage(HttpExchange httpExchange) {
        return this.mentorService.getEditCodecoolerPageRender(httpExchange);
    }

    public String getAddCodecooler() {
        return this.mentorService.getAddCodecoolerPageRender();
    }

    public String getEditArtifactPage(HttpExchange httpExchange) {
        return this.mentorService.getEditArtifactPageRender(httpExchange);
    }

    public String getAddArtifactPage() {
        return this.mentorService.getAddArtifactPageRender();
    }

    public String getEditQuestPage(HttpExchange httpExchange) {
        return this.mentorService.getEditQuestPageRender(httpExchange);
    }

    public String getAddQuestPage() {
        return this.mentorService.getAddQuestPageRender();
    }

    public String getPendingArtifactsPage() {
        return this.mentorService.getPendingArtifactsPageRender();
    }

    public String getMentorResponse(HttpExchange httpExchange, String userPageRequest) throws IOException {
        int requestedItemId = getItemIdRequestIfExists(httpExchange);
        String response = "";

        switch (userPageRequest) {
            case "index": // GET: index
                response = getIndexPage(httpExchange); // Finish parsing personalized index
                break;

            case "codecoolers": // GET: codecoolers
                response = mentorService.getCodecoolerPageRender();
                break;

            case "quests": // GET: quests
                response = mentorService.getQuestPageRender();
                break;

            case "artifacts": // GET: logout
                response = mentorService.getArtifactPageRender();
                break;

            case "transactions": // GET: transactions
                response = mentorService.getTransactionsHistoryPageRender();
                break;

            case "logout": // GET: logout
                SessionHandler.getInstance().removeActiveSessionWithCookie(httpExchange);
                response = new LoginController().getLoginPage();
                break;

            case "editCodecoolerPage": // GET: edit codecooler
                response = getEditCodecoolerPage(httpExchange);
                break;

            case "addCodecoolerPage": // GET: add codecooler
                response = getAddCodecooler();
                break;

            case "editQuestPage": // GET: edit quest
                response = getEditQuestPage(httpExchange);
                break;

            case "addQuestPage": // GET: add quest
                response = getAddQuestPage();
                break;

            case "editArtifactPage": // GET: edit artifact
                response = getEditArtifactPage(httpExchange);
                break;

            case "addArtifactPage": // GET: add artifact
                response = getAddArtifactPage();
                break;

            case "addCodecooler": // POST: add codecooler
                addItemByHttpExchange(httpExchange, "codecoolers");
                mentorService.addCodecooler(httpExchange);
                response = getCodecoolerPage();
                break;

            case "updateCodecooler": // POST: update codecooler
                addItemByHttpExchange(httpExchange, "codecoolers");
                mentorService.updateCodecooler(httpExchange, requestedItemId);
                response = getCodecoolerPage();
                break;

            case "deleteCodecooler": // POST: delete codecooler
                mentorService.deleteCodecooler(requestedItemId);
                response = getCodecoolerPage();
                break;

            case "addQuest": // POST: delete quest
                mentorService.addQuest(httpExchange);
                response = getQuestPage();
                break;

            case "updateQuest": // POST: update quest
                mentorService.updateQuest(httpExchange, requestedItemId);
                response = getQuestPage();
                break;

            case "deleteQuest": // POST: delete quest
                mentorService.deleteQuest(requestedItemId);
                response = getQuestPage();
                break;

            case "addArtifact": // POST: addQuest: add artifact
                mentorService.addArtifact(httpExchange);
                response = getArtifactPage();
                break;

            case "updateArtifact": // POST: update artifact
                mentorService.updateArtficat(httpExchange, requestedItemId);
                response = getArtifactPage();
                break;

            case "deleteArtifact": // POST: delete artifact
                mentorService.deleteArtifact(requestedItemId);
                response = getArtifactPage();
                break;
            case "pendingArtifacts": // GET: pending artifacts
                response = getPendingArtifactsPage();

                break;
            case "useArtifact": // POST: pending artifacts
                mentorService.useBackpackItem(httpExchange, requestedItemId);
                response = getPendingArtifactsPage();

                break;
        }

        return response;
    }

    private int getItemIdRequestIfExists(HttpExchange httpExchange) {
        int id = -1;
        String[] requestUrlArray = httpExchange.getRequestURI().toString().split("/");
        System.out.println("3");
        if (requestUrlArray.length >= 5) {
            System.out.println(requestUrlArray[4]);
            id = Integer.parseInt(requestUrlArray[4]);
        }
        return id;
    }

    public void addItemByHttpExchange(HttpExchange httpExchange, String imagesSubFolderName) throws IOException {
        new FileUploaderService().upload(httpExchange, imagesSubFolderName);
    }
}
