package services;

import com.sun.net.httpserver.HttpExchange;
import databaseAccess.AccountsDAO;
import databaseAccess.ClassesDAO;
import databaseAccess.LevelsDAO;
import databaseAccess.MentorsDAO;
import models.Account;
import models.Class;
import models.Level;
import models.Mentor;
import views.AdminResponseCreator;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AdminService {
    private AdminResponseCreator adminResponseCreator = new AdminResponseCreator();

    // GET METHODS
    public String getIndexPageRender() {
        return adminResponseCreator.renderIndexPage();
    }

    public String getMentorPageRender() {
        List<Mentor> mentors = new MentorsDAO().getAll();
        return adminResponseCreator.renderMentorPage(mentors);
    }

    public String getClassPageRender() {
        List<Class> classes = new ClassesDAO().getAll();
        return adminResponseCreator.renderClassPage(classes);
    }

    public String getLevelPageRender() {
        List<Level> levels = new LevelsDAO().getAll();
        return adminResponseCreator.renderLevelPage(levels);
    }

    public String getEditMentorPageRender(HttpExchange httpExchange) {
        int id = getIdByURL(httpExchange);
        List<Mentor> mentor = new ArrayList<>();
        mentor.add(new MentorsDAO().get(id));
        List<Class> possibleClasses = new ClassesDAO().getAll();

        for (int i = 0; i < possibleClasses.size(); i++) {
            if (possibleClasses.get(i).getClassId() == mentor.get(0).getClassId()) {
                possibleClasses.remove(i);
            }
        }
        return adminResponseCreator.renderEditMentorPage(mentor, possibleClasses);
    }

    public String getAddMentorPageRender() {
        List<Class> possibleClasses = new ClassesDAO().getAll();
        return adminResponseCreator.renderAddMentorPage(possibleClasses);
    }

    public String getEditLevelPageRender(HttpExchange httpExchange) {
        int id = getIdByURL(httpExchange);
        List<Level> level = new ArrayList<>();
        level.add(new LevelsDAO().get(id));

        return adminResponseCreator.renderEditLevelPage(level);
    }

    public String getAddLevelPageRender() {
        return adminResponseCreator.renderAddLevelPage();
    }

    public String getEditClassPageRender(HttpExchange httpExchange) {
        int id = getIdByURL(httpExchange);
        List<Class> classes = new ArrayList<>();
        classes.add(new ClassesDAO().get(id));

        return adminResponseCreator.renderEditClassPage(classes);
    }

    public String getAddClassPageRender() {
        return adminResponseCreator.renderAddClassPage();
    }

    private int getIdByURL(HttpExchange httpExchange) {
        return UrlIdService.getIdByUrl(httpExchange, 4);
    }

    // POST METHODS
    public void addMentor(HttpExchange httpExchange) throws IOException {
        Map<String, String> inputs = getFormInputsMap(httpExchange);

        Account account = getMentorAccountFromForm(inputs);
        AccountsDAO accountsDAO = new AccountsDAO();
        accountsDAO.add(account);
        Account currentAccount = accountsDAO.getAccountFromDbByAccountWithoutId(account);

        Mentor mentor = getMentorFromFormAndAccount(inputs, currentAccount);
        new MentorsDAO().add(mentor);
    }

    public void updateMentor(HttpExchange httpExchange, int requestedItemId) throws IOException {
        Map<String, String> inputs = getFormInputsMap(httpExchange);

        Account account = new AccountsDAO().get(requestedItemId);
        Mentor mentor = getMentorFromFormAndAccount(inputs, account);

        new MentorsDAO().update(requestedItemId, mentor);
    }

    public void deleteMentor(int requestedItemId) {
        new AccountsDAO().delete(requestedItemId);
        new MentorsDAO().delete(requestedItemId);
    }

    public void addLevel(HttpExchange httpExchange) throws IOException {
        Level level = getLevelFromForm(httpExchange);
        new LevelsDAO().add(level);
    }

    public void updateLevel(HttpExchange httpExchange, int requestedItemId) throws IOException {
        Level level = getLevelFromForm(httpExchange);
        LevelsDAO levelsDAO = new LevelsDAO();
        levelsDAO.delete(requestedItemId);
        levelsDAO.add(level);
    }

    public void deleteLevel(int requestedItemId) throws IOException {
        new LevelsDAO().delete(requestedItemId);
    }

    public void addClass(HttpExchange httpExchange) throws IOException {
        Class classToAdd = getClassFromForm(httpExchange);
        new ClassesDAO().add(classToAdd);
    }

    public void updateClass(HttpExchange httpExchange, int requestedItemId) throws IOException {
        Class classToUpdate = getClassFromForm(httpExchange);
        new ClassesDAO().update(requestedItemId, classToUpdate);
    }

    public void deleteClass(HttpExchange httpExchange, int requestedItemId) {
        new ClassesDAO().delete(requestedItemId);
    }

    private Account getMentorAccountFromForm(Map<String, String> inputs) throws IOException {

        return new AccountFactory().getMentorAccountFromForm(inputs);
    }

    private Mentor getMentorFromFormAndAccount(Map<String, String> inputs, Account account) throws IOException {

        return new Mentor(
                account.getAccountId(),
                inputs.get("fullName"),
                inputs.get("email"),
                new ClassesDAO().getClassIdByName(String.valueOf(inputs.get("assignedClass"))),
                inputs.get("about"),
                "emptyAvatar404.jpg" // none for now
        );
    }

    private Class getClassFromForm(HttpExchange httpExchange) throws IOException {
        Map<String, String> inputs = getFormInputsMap(httpExchange);
        return new Class(
                inputs.get("className")
        );
    }

    private Level getLevelFromForm(HttpExchange httpExchange) throws IOException {
        Map<String, String> inputs = getFormInputsMap(httpExchange);
        return new Level(
                Integer.parseInt(inputs.get("level")),
                Integer.parseInt(inputs.get("coolcoinGap")),
                inputs.get("levelTitle"),
                inputs.get("levelDescription")
        );
    }

    private Map<String, String> getFormInputsMap(HttpExchange httpExchange) throws IOException {
        return FormService.getInputsStringMap(httpExchange);
    }
}
