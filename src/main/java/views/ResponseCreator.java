package views;

import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;

import java.util.ArrayList;
import java.util.List;

public class ResponseCreator {

    public ResponseCreator() {

    }

    public String renderLoginPage() {
        String loginPage = renderPage("login.twig");
        return loginPage;
    }

    public <E> String renderPageWith(String fileName, List<E> listToFillWith) {
        if("templates/mentor/artifactPage.twig".equals(fileName)) System.out.println("IN"); // DELL LATER
        String response;
        JtwigTemplate template = JtwigTemplate.classpathTemplate("templates/" + fileName);
        if("templates/mentor/artifactPage.twig".equals(fileName)) System.out.println("OUT 1"); // dell later
        JtwigModel model;
        model = JtwigModel.newModel().with("list", listToFillWith);
        if("templates/mentor/artifactPage.twig".equals(fileName)) System.out.println("OUT 2"); // dell later
        response = template.render(model);
        if("templates/mentor/artifactPage.twig".equals(fileName)) System.out.println("OUT 3"); // DELL LATER
        return response;
    }

    public String renderPage(String fileName) {
        return renderPageWith(fileName, new ArrayList<>());
    }

    public <E, F> String renderPageWith(String fileName, List<E> firstList, List<F> secondList) {
        String response;
        JtwigTemplate template = JtwigTemplate.classpathTemplate("templates/" + fileName);
        JtwigModel model;
        model = JtwigModel.newModel().with("list", firstList);
        model.with("list2", secondList);
        response = template.render(model);
        return response;
    }
}
